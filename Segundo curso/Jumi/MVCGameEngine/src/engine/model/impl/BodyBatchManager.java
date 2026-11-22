package engine.model.impl;

import engine.model.bodies.core.AbstractBody;
import engine.model.bodies.impl.PlayerBody;
import engine.utils.threading.ThreadPoolManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manages batching of bodies into MultiBodyRunners for efficient thread execution.
 * 
 * Owns:
 * - ThreadPoolManager: generic thread pool for executing runners
 * - MultiBodyRunner instances: each runs on a dedicated thread
 * 
 * Responsibilities:
 * - Determine optimal batch size per body (PlayerBody=1, others=10)
 * - Assign bodies to runners or create new ones
 * - Manage runner lifecycle and thread acquisition
 * 
 * Architecture: Model → BodyBatchManager → ThreadPoolManager
 */
public class BodyBatchManager {

    // region Constants
    private static final int DEFAULT_BATCH_SIZE = 20;
    private static final int PLAYER_BATCH_SIZE = 1;
    // endregion

    // region Fields
    private final ThreadPoolManager threadPoolManager;
    private final List<MultiBodyRunner> activeRunners = new CopyOnWriteArrayList<>();
    private final Object runnersLock = new Object();
    // endregion

    // region Constructors
    /**
     * Create a BodyBatchManager with its own thread pool.
     * 
     * @param threadPoolSize number of core threads in pool (must be > 0)
     * @throws IllegalArgumentException if threadPoolSize <= 0
     */
    public BodyBatchManager(int threadPoolSize) {
        if (threadPoolSize <= 0) {
            throw new IllegalArgumentException("threadPoolSize must be > 0, got: " + threadPoolSize);
        }
        this.threadPoolManager = new ThreadPoolManager(threadPoolSize);
    }
    // endregion

    // *** PUBLICS ***

    /**
     * Assign body to thread pool.
     * 
     * Determines batch size based on body type (PlayerBody=1, others=10).
     * Reuses compatible runner if available; creates new runner and thread if needed.
     * 
     * @param body the body to activate (cannot be null)
     * @throws NullPointerException if body is null
     */
    public void activateBody(AbstractBody body) {
        if (body == null) {
            throw new NullPointerException("Body cannot be null");
        }
        
        int batchSize = (body instanceof PlayerBody) ? PLAYER_BATCH_SIZE : DEFAULT_BATCH_SIZE;
        submitBatched(body, batchSize);
    }

    public void activate() {
        this.threadPoolManager.prestartAllCoreThreads();
    }

    /**
     * Graceful shutdown: stop all runners and thread pool.
     */
    public boolean shutdown() {
        synchronized (this.runnersLock) {
            for (MultiBodyRunner runner : this.activeRunners) {
                runner.requestStop();
            }
        }
        return this.threadPoolManager.shutdown();
    }

    // *** PRIVATE ***

    /**
     * Deregister a runner when it terminates.
     * Called by MultiBodyRunner when run() loop ends.
     */
    void removeRunner(MultiBodyRunner runner) {
        if (runner == null) {
            return;
        }
        synchronized (this.runnersLock) {
            this.activeRunners.remove(runner);
        }
    }

    /**
     * Internal: assign body to runner with specific batch size.
     * 
     * @param body the body to assign (early fail validates at method start)
     * @param batchSize the batch size for grouping (early fail validates at method start)
     * @throws IllegalArgumentException if batchSize <= 0
     */
    private void submitBatched(AbstractBody body, int batchSize) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("batchSize must be > 0, got: " + batchSize);
        }

        synchronized (this.runnersLock) {
            // Try reuse compatible runner
            for (MultiBodyRunner runner : this.activeRunners) {
                if (runner.getBatchSize() == batchSize
                        && !runner.isTerminated()
                        && !runner.isFull()
                        && runner.addBody(body)) {
                    return;
                }
            }

            // Create new runner
            MultiBodyRunner newRunner = new MultiBodyRunner(batchSize, this);
            if (!newRunner.addBody(body)) {
                throw new IllegalStateException("Failed to add body to new runner");
            }

            this.activeRunners.add(newRunner);
            this.threadPoolManager.submit(newRunner);
        }
    }
}
