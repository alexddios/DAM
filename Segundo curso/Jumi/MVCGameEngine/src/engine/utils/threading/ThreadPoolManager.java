package engine.utils.threading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * Generic thread pool executor for task execution.
 * 
 * - Fixed-size core pool with fair queue-based work distribution.
 * - No knowledge of specific task types (completely decoupled from domain logic).
 * - Reusable by any component needing threaded execution.
 * - Early fail: validates pool size at constructor.
 */
public final class ThreadPoolManager {

    // region Constants
    private static final int DEFAULT_POOL_SIZE = 250;
    private static final int SHUTDOWN_TIMEOUT_SECONDS = 30;
    // endregion

    // region Fields
    private final ThreadPoolExecutor executor;
    private volatile boolean isShutdown = false;
    // endregion

    // region Constructors
    /**
     * Create pool with default size (250 threads).
     */
    public ThreadPoolManager() {
        this(DEFAULT_POOL_SIZE);
    }

    /**
     * Create pool with specified size.
     * 
     * @param poolSize number of core threads (must be > 0)
     * @throws IllegalArgumentException if poolSize <= 0 (early fail)
     */
    public ThreadPoolManager(int poolSize) {
        if (poolSize <= 0) {
            throw new IllegalArgumentException("poolSize must be > 0, got: " + poolSize);
        }

        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        RejectedExecutionHandler handler = (r, e) -> {
            throw new RejectedExecutionException("Task rejected - pool is shutdown");
        };

        this.executor = new ThreadPoolExecutor(
                poolSize, poolSize,  // Fixed size
                0L, TimeUnit.MILLISECONDS,
                queue,
                r -> {
                    Thread t = new Thread(r);
                    t.setName("PoolThread-" + System.nanoTime());
                    t.setPriority(Thread.NORM_PRIORITY - 1);
                    t.setDaemon(false);
                    t.setUncaughtExceptionHandler((thread, throwable) -> {
                        throwable.printStackTrace();
                    });
                    return t;
                },
                handler);
    }
    // endregion

    // *** PUBLICS ***

    /**
     * Pre-create all core threads to avoid lazy startup.
     */
    public void prestartAllCoreThreads() {
        this.executor.prestartAllCoreThreads();
    }

    /**
     * Submit task for execution.
     * 
     * @param task the Runnable to execute (cannot be null)
     * @throws NullPointerException if task is null (early fail)
     * @throws RejectedExecutionException if pool is shutdown
     */
    public void submit(Runnable task) {
        if (task == null) {
            throw new NullPointerException("Task cannot be null");
        }
        this.executor.submit(task);
    }

    /**
     * Gracefully shutdown: stop accepting tasks, wait for running tasks.
     * 
     * @return true if all tasks completed within timeout, false otherwise
     */
    public boolean shutdown() {
        if (this.isShutdown) {
            return true;
        }

        this.isShutdown = true;
        this.executor.shutdown();

        try {
            boolean terminated = this.executor.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            if (!terminated) {
                this.executor.shutdownNow();
            }
            return terminated;
        } catch (InterruptedException e) {
            this.executor.shutdownNow();
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * Check if pool is shutdown.
     */
    boolean isShutdown() {
        return this.isShutdown;
    }
}
