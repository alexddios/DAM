package engine.utils.pooling;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Supplier;

/**
 * Generic object pool for reusing DTOs.
 * 
 * This pool manages poolable & mutables DTO instances to reduce allocation
 * pressure.
 * When a MDTO (mutable DTO) is requested via acquire(), it either returns one
 * from
 * the pool or creates a new one using the provided factory if the pool is
 * empty.
 * 
 * When a MDTO is no longer needed, it can be returned via release(), which
 * resets the MDTO and adds it back to the pool for reuse.
 * 
 * @param <T> the type of MDTO managed by this pool (must implement
 *            PoolableMDTO)
 */
public class PoolMDTO<T extends PoolableMDTO> {

    // region Fields
    private final Supplier<T> factory;
    private final Deque<T> pool = new ArrayDeque<>();
    // endregion Fields

    // region Constructors

    // Creates a new Pool with a factory for creating new instances when needed.
    public PoolMDTO(Supplier<T> factory) {
        if (factory == null) {
            throw new IllegalArgumentException("Factory cannot be null");
        }
        this.factory = factory;
    }
    // endregion Constructors

    // *** PUBLICS ***

    /**
     * Acquires a MDTO from the pool.
     * If empty, a new is created using the factory.
     */
    public T acquire() {
        T mdto = this.pool.pollFirst();
        if (mdto == null) {
            mdto = this.factory.get();
        }
        return mdto;
    }

    public void clear() {
        this.pool.clear();
    }

    /**
     * Returns the current number of available DTOs in the pool.
     */
    public int getPoolSize() {
        return this.pool.size();
    }

    /**
     * Preallocates the specified number of MDTOs in the pool.
     * Creates new instances using the factory and adds them to the pool.
     * This is useful for warming up the pool during initialization to avoid
     * allocation overhead during runtime.
     */
    public void preallocate(int count) {
        if (count <= 0) {
            return;
        }
        for (int i = 0; i < count; i++) {
            this.pool.addLast(this.factory.get());
        }
    }

    /**
     * Releases a MDTO back to the pool.
     * The DTO is reset before being added to the pool.
     */
    public void release(T mdto) {
        if (mdto != null) {
            mdto.reset();
            this.pool.addLast(mdto);
        }
    }
}
