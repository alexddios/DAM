package engine.controller.mappers;

import engine.utils.pooling.PoolMDTO;
import engine.utils.pooling.PoolableMDTO;

/**
 * Abstract base class for mappers that use object pools to reduce allocations.
 * 
 * Concrete mappers extend this class and implement mapToDTO() to populate
 * pooled DTO instances without creating new ones on every call.
 * 
 * @param <T> the type of DTO managed by this mapper (must implement DTOPoolable)
 */
public abstract class DTOPooledMapper<T extends PoolableMDTO> {

    protected final PoolMDTO<T> pool;

    /**
     * Creates a new pooled mapper with the given pool.
     * 
     * @param pool the DTO pool to use for acquiring/releasing instances
     */
    public DTOPooledMapper(PoolMDTO<T> pool) {
        if (pool == null) {
            throw new IllegalArgumentException("Pool cannot be null");
        }
        this.pool = pool;
    }

    /**
     * Acquires a DTO from the pool and populates it with mapped data.
     * Subclasses should override mapToDTO() to define the mapping logic.
     * 
     * @param source the source object to map from
     * @return a pooled DTO populated with data from the source
     */
    protected T map(Object source) {
        T dto = this.pool.acquire();
        return mapToDTO(source, dto) ? dto : null;
    }

    /**
     * Maps data from source to the given DTO.
     * Subclasses must implement this to define how data is transformed.
     * 
     * @param source the source object to map from
     * @param target the DTO to populate
     * @return true if mapping was successful, false otherwise
     */
    protected abstract boolean mapToDTO(Object source, T target);

    /**
     * Returns the pool used by this mapper.
     * 
     * @return the pool
     */
    public PoolMDTO<T> getPool() {
        return this.pool;
    }
}
