package engine.model.impl;

import engine.model.bodies.core.AbstractBody;
import engine.model.bodies.ports.BodyState;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Executes multiple bodies sequentially on one thread.
 * 
 * Batches N bodies per runner to reduce thread count from O(bodies) to O(bodies/N).
 * Runner persists when empty for reuse when new bodies are added.
 */
public class MultiBodyRunner implements Runnable {

    // region Constants
    private static final int SLEEP_TIME_MS = 12;
    // endregion

    // region Fields
    private final List<AbstractBody> bodies;
    private volatile boolean isAcceptingBodies = true;
    private final int maxBodiesPerRunner;
    private final BodyBatchManager ownerManager;
    private volatile boolean isTerminated = false;
    private volatile boolean shouldStop = false;
    // endregion

    // region Constructors
    /**
     * Create a runner for batch execution.
     * 
     * @param maxBodiesPerRunner max bodies per cycle (must be > 0)
     * @param ownerManager manager reference for self-removal when terminated
     * @throws IllegalArgumentException if maxBodiesPerRunner <= 0
     * @throws NullPointerException if ownerManager is null
     */
    public MultiBodyRunner(int maxBodiesPerRunner, BodyBatchManager ownerManager) {
        if (maxBodiesPerRunner <= 0) {
            throw new IllegalArgumentException("maxBodiesPerRunner must be > 0, got: " + maxBodiesPerRunner);
        }
        if (ownerManager == null) {
            throw new NullPointerException("ownerManager cannot be null");
        }
        this.bodies = new CopyOnWriteArrayList<>();
        this.isAcceptingBodies = true;
        this.maxBodiesPerRunner = maxBodiesPerRunner;
        this.ownerManager = ownerManager;
        this.isTerminated = false;
        this.shouldStop = false;
    }
    // endregion

    // *** PUBLICS ***

    /**
     * Add body to this runner. Returns false if full or terminated (early fail validates parameters).
     */
    public synchronized boolean addBody(AbstractBody body) {
        if (body == null) {
            throw new NullPointerException("Body cannot be null");
        }
        if (!this.isAcceptingBodies || this.isTerminated) {
            return false;
        }
        if (this.bodies.size() >= this.maxBodiesPerRunner) {
            return false;
        }
        this.bodies.add(body);
        return true;
    }

    /**
     * Get max bodies per cycle.
     */
    public int getBatchSize() {
        return this.maxBodiesPerRunner;
    }
    
    /**
     * Get current body count in this runner.
     */
    public int getBodyCount() {
        return this.bodies.size();
    }

    /**
     * Check if runner has reached max capacity.
     */
    public boolean isFull() {
        return this.bodies.size() >= this.maxBodiesPerRunner;
    }

    /**
     * Check if runner has terminated.
     */
    public boolean isTerminated() {
        return this.isTerminated;
    }

    /**
     * Signal runner to stop gracefully.
     */
    public void requestStop() {
        this.shouldStop = true;
    }

    // *** INTERFACE IMPLEMENTATIONS ***

    /**
     * Execute bodies in loop until stopped. Removes dead bodies each cycle.
     */
    @Override
    public void run() {
        while (!this.shouldStop) {
            for (AbstractBody body : this.bodies) {
                if (body.getBodyState() == BodyState.DEAD) {
                    continue;
                }
                if (body.getBodyState() == BodyState.ALIVE) {
                    try {
                        body.onTick();
                    } catch (Exception ex) {
                        throw new RuntimeException("MultiBodyRunner: Error processing body " + body.getBodyId(), ex);
                    }
                }
            }

            this.bodies.removeIf(body -> body.getBodyState() == BodyState.DEAD);

            try {
                Thread.sleep(SLEEP_TIME_MS);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        this.isAcceptingBodies = false;
        this.isTerminated = true;

        if (this.ownerManager != null) {
            this.ownerManager.removeRunner(this);
        }
    }
}
