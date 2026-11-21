package engine.utils.pooling;

/**
 * Marker interface for MDTOs that can be pooled and reused.
 * Poolable Mutable MDTOs must implement reset() to clear their 
 * state before returning to the pool.
 */
public interface PoolableMDTO {
    /**
     * Resets the MDTO to a clean state for reuse.
     * Called by the pool before the MDTO is made available for reacquisition.
     */
    void reset();
}
