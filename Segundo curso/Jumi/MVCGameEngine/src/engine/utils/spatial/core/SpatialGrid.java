package engine.utils.spatial.core;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import engine.utils.spatial.ports.SpatialGridStatisticsDTO;

/**
 * SpatialGrid (NEUTRAL + TOPOLOGÍA FIJA + PREALLOC)
 * 
 * El caller aporta un buffer temporal (scratchIdxs) reutilizable.
 *
 * Estructuras:
 * - grid[idx] = ConcurrentHashMap<String, Boolean> (set de ids en esa celda)
 * - idToMembership[id] = celdas actuales del id (para remove/move O(1))
 *
 * Nota:
 * - queryCandidates puede devolver duplicados (si un body ocupa varias celdas).
 * Solución barata en colisiones: procesar solo si myId.compareTo(otherId) < 0.
 */
public final class SpatialGrid {

    // region Fields
    private final double cellSize;
    private final double invCellSize;
    private final int cellsX;
    private final int cellsY;
    private final int maxCellsPerBody;

    private final ConcurrentHashMap<String, Boolean>[] grid;
    private final ConcurrentHashMap<String, Cells> cellsPerEntity = new ConcurrentHashMap<>();
    // endregion

    // region Constructors
    @SuppressWarnings("unchecked")
    public SpatialGrid(double worldWidth, double worldHeight, int cellSize, int maxCellsPerBody) {
        if (cellSize <= 0)
            throw new IllegalArgumentException("cellSizePx must be > 0");
        if (worldWidth <= 0 || worldHeight <= 0)
            throw new IllegalArgumentException("world size must be > 0");
        if (maxCellsPerBody <= 0)
            throw new IllegalArgumentException("maxCellsPerBody must be > 0");

        this.cellSize = cellSize;
        this.invCellSize = 1.0d / cellSize;
        this.maxCellsPerBody = maxCellsPerBody;

        // Ceil div
        this.cellsX = (int) ((worldWidth + cellSize - 1) / cellSize);
        this.cellsY = (int) ((worldHeight + cellSize - 1) / cellSize);

        final int total = this.cellsX * this.cellsY;

        // Prealloc buckets (arranque más caro, runtime estable)
        this.grid = (ConcurrentHashMap<String, Boolean>[]) new ConcurrentHashMap[total];
        for (int i = 0; i < total; i++) {
            this.grid[i] = new ConcurrentHashMap<>(64);
        }
    }
    // endregion

    // *** PUBLIC ***

    // region getters (get***)
    public int getMaxCellsPerBody() {
        return maxCellsPerBody;
    }

    public double getCellSize() {
        return cellSize;
    }

    /**
     * Samples runtime statistics of the spatial grid for monitoring and tuning
     * purposes.
     *
     * This method performs a full scan of all grid buckets and computes aggregated
     * metrics describing the current spatial distribution of entities. It is
     * intended for diagnostics, profiling, and validation of grid parameters (cell
     * size, load balance), not for use in hot paths.
     *
     * Collected metrics include:
     * - Number of non-empty buckets (buckets containing at least one entity).
     * - Number of empty buckets.
     * - Average number of entities per non-empty bucket.
     * - Maximum number of entities found in any single bucket.
     * - Total number of potential collision pairs (sum of nC2 per bucket).
     *
     * Performance notes:
     * - Time complexity is O(cellsX * cellsY)
     * - No allocations are performed during the scan.
     * - Call frequency should be low (e.g., debug mode, periodic monitoring).
     *
     * Concurrency notes:
     * - Iteration over buckets is weakly consistent; concurrent modifications may
     * be observed, but the computed statistics are always safe and structurally
     * consistent.
     *
     * @return a {@link SpatialGridStatisticsDTO} snapshot containing aggregated
     *         grid
     *         statistics
     */
    public SpatialGridStatisticsDTO getStatistics() {
        int nonEmptyBuckets = 0; // buckets with >=1 keys
        int emptyBuckets = 0; // buckets with 0 keys
        int maxBucketKeys = 0; // max keys in a bucket
        long totalKeys = 0; // total keys in all buckets
        long sumPairs = 0; // sum of nC2 for each bucket

        for (ConcurrentHashMap<String, Boolean> bucket : grid) {
            final int bucketSize = bucket.size();
            if (bucketSize <= 0) {
                emptyBuckets++;
                continue;
            }

            nonEmptyBuckets++;
            totalKeys += bucketSize;
            if (bucketSize > maxBucketKeys)
                maxBucketKeys = bucketSize;

            sumPairs += (long) bucketSize * (bucketSize - 1) / 2;
        }

        final double avgKeysPerBucketNotEmpty = (nonEmptyBuckets == 0) ? 0.0
                : ((double) totalKeys / (double) nonEmptyBuckets);

        return new SpatialGridStatisticsDTO(
                nonEmptyBuckets, emptyBuckets, avgKeysPerBucketNotEmpty, maxBucketKeys, sumPairs,
                cellSize, cellsX, cellsY, maxCellsPerBody);
    }
    // endregion

    /**
     * Update cells useds by entityId according to posX,posY and size.
     * - Caller provides a reusable scratchIdxs buffer.
     * - Assumes no two threads are moving the same entityId at the same time.
     * - If the center is out of bounds, the entity is removed from the grid
     */
    public void upsert(
            String entityId, double minX, double maxX, double minY, double maxY, int[] scratchIdxs) {

        this.requireBuffer(scratchIdxs);

        if (entityId == null || entityId.isEmpty()) {
            throw new IllegalArgumentException("upsert: entityId is null or empty");
        }

        // Get old cells of entityId (from reverse mapping), or create new if not exists
        final Cells oldEntityCells = this.cellsPerEntity.computeIfAbsent(entityId, __ -> new Cells(maxCellsPerBody));

        // Contract: newCellIdxs[0..newCount) are valid, rest is garbage
        // alias for semantic clarity: scratch used as "new cells"
        final int[] newCellIdxs = scratchIdxs;

        // Compute overlapping cells for the query region
        final int newCount = computeCellIdxsClamped(minX, maxX, minY, maxY, newCellIdxs);

        if (this.sameCells(oldEntityCells, newCount, newCellIdxs))
            return;

        if (newCount < 3 && oldEntityCells.count < 3) {
            // If new cells are few, it's (probably) cheaper delete old cells
            // and insert new ones, without checks => maximum 2 removes + 2 inserts...
            this.upsertSmall(entityId, newCount, oldEntityCells, newCellIdxs);
        } else {
            this.upsertLarge(entityId, newCount, oldEntityCells, scratchIdxs);
        }
    }

    private boolean sameCells(Cells oldEntityCells, int newCount, int[] newCellIdxs) {
        if (oldEntityCells.count != newCount)
            return false;

        for (int i = 0; i < oldEntityCells.count; i++) {
            if (oldEntityCells.idxs[i] != newCellIdxs[i])
                return false;
        }

        return true;
    }

    private void upsertSmall(String entityId, int newCount, Cells oldEntityCells, int[] newCellIdxs) {
        // Remove
        for (int i = 0; i < oldEntityCells.count; i++)
            grid[oldEntityCells.idxs[i]].remove(entityId);

        // Insert
        for (int i = 0; i < newCount; i++)
            grid[newCellIdxs[i]].put(entityId, Boolean.TRUE);

        // New are now old
        oldEntityCells.updateFrom(newCellIdxs, newCount);
    }

    private void upsertLarge(String entityId, int newCount, Cells oldEntityCells, int[] newCellIdxs) {
        // Linear merge diff O(k) instead of O(k²) contains checks
        // Both oldEntityCells.idxs and newCellIdxs are ordered by computeCellIdxsClamped
        // Traverse both in parallel:
        // - Remove cells in old but not in new
        // - Add cells in new but not in old
        // - Keep cells that are in both

        int oldCount = oldEntityCells.count;
        int i = 0; // pointer to old cells
        int j = 0; // pointer to new cells

        // Merge phase: compare old and new cell indices
        while (i < oldCount && j < newCount) {
            int oldIdx = oldEntityCells.idxs[i];
            int newIdx = newCellIdxs[j];

            if (oldIdx == newIdx) {
                // Cell exists in both → keep it
                i++;
                j++;
            } else if (oldIdx < newIdx) {
                // Cell is in old but not in new → remove
                grid[oldIdx].remove(entityId);
                i++;
            } else {
                // Cell is in new but not in old → add
                grid[newIdx].put(entityId, Boolean.TRUE);
                j++;
            }
        }

        // Remove remaining old cells
        while (i < oldCount) {
            grid[oldEntityCells.idxs[i]].remove(entityId);
            i++;
        }

        // Add remaining new cells
        while (j < newCount) {
            grid[newCellIdxs[j]].put(entityId, Boolean.TRUE);
            j++;
        }

        // New are now old
        oldEntityCells.updateFrom(newCellIdxs, newCount);
    }

    public void remove(String entityId) {
        if (entityId == null || entityId.isEmpty())
            return;

        // Remove cells associated to entityId (from reverse mapping
        final Cells cells = this.cellsPerEntity.remove(entityId);
        if (cells == null)
            return;

        // Remove entityId from all associated cells (from grid)
        for (int i = 0; i < cells.count; i++) {
            grid[cells.idxs[i]].remove(entityId);
        }
    }

    // region queries (query***)
    /**
     * Returns collision candidates for the given entity.
     *
     * This method uses the entityId to retrieve the current cell membership
     * of the entity from the spatial grid (reverse mapping), and then iterates
     * all grid buckets corresponding to those cells to collect nearby entityIds.
     *
     * Important notes
     * ---------------
     * - Returned list may contain duplicates
     * - Iteration is weakly consistent: concurrent inserts/removals may be
     * observed, but the result is always safe and free of structural corruption.
     *
     * Performance characteristics
     * ---------------------------
     * - No spatial recomputation is performed (cell indices are reused).
     * - Time complexity is proportional to the number of occupied cells of the
     * entity and the number of entities in those cells.
     *
     * @param entityId the entity whose collision neighborhood is queried
     * @return the list of collision candidate entityIds (possibly empty) or null
     */
    public ArrayList<String> queryCollisionCandidates(String entityId, ArrayList<String> scratchCandidateIds) {
        if (entityId == null || entityId.isEmpty())
            return null;

        final Cells cells = this.cellsPerEntity.get(entityId);
        if (cells == null || cells.count <= 0)
            return null;

        scratchCandidateIds.clear();
        for (int i = 0; i < cells.count; i++) {
            final int cellIndex = cells.idxs[i];
            final ConcurrentHashMap<String, Boolean> bucket = this.grid[cellIndex];

            for (String id : bucket.keySet())
                if (!entityId.equals(id))
                    scratchCandidateIds.add(id);
        }

        return scratchCandidateIds;
    }

    /**
     * Returns all entity IDs within the specified rectangular region (AABB).
     * 
     * Use case: Frustum culling for camera viewport.
     * 
     * @param minX              left edge of query region (world coords)
     * @param maxX              right edge of query region (world coords)
     * @param minY              top edge of query region (world coords)
     * @param maxY              bottom edge of query region (world coords)
     * @param scratchIdxs       reusable buffer for cell indices (min size:
     *                          maxCellsPerBody)
     * @param scratchCandidates reusable list for results
     * @return list of entity IDs in region (may contain duplicates)
     */
    public ArrayList<String> queryRegion(
            double minX, double maxX, double minY, double maxY,
            int[] scratchIdxs, ArrayList<String> scratchCandidates) {

        this.requireBuffer(scratchIdxs);
        scratchCandidates.clear();

        // Compute overlapping cells for the query region
        final int cellCount = computeCellIdxsClamped(minX, maxX, minY, maxY, scratchIdxs);

        // Collect all entities in those cells
        for (int i = 0; i < cellCount; i++) {
            final int cellIdx = scratchIdxs[i];
            final ConcurrentHashMap<String, Boolean> bucket = this.grid[cellIdx];

            for (String entityId : bucket.keySet()) {
                scratchCandidates.add(entityId);
            }
        }

        return scratchCandidates;
    }
    // endregion

    // *** PRIVATE ***

    /**
     * Computes the set of grid cell indices overlapped by an axis-aligned bounding
     * box (AABB),clamped to the fixed grid topology.
     *
     * The method converts the AABB defined by (minX, maxX, minY, maxY) into
     * discrete grid cell coordinates using integer division by the cell size, then
     * clamps those coordinates to the valid grid range [0 .. cellsX-1] and [0 ..
     * cellsY-1].
     *
     * All overlapped cell indices are written sequentially into {@code outIdxs},
     * and the number of valid entries is returned.
     *
     * Important notes:
     * - The valid range in {@code outIdxs} is [0 .. returnValue).
     * - The contents of {@code outIdxs} beyond that range are undefined.
     * - If number of cells exceeds {@code maxCellsPerBody}, the result is truncated
     * and a warning is emitted. In that case, {@code maxCellsPerBody}
     * should be increased.
     * - No allocations are performed; the caller must provide a reusable buffer.
     *
     * @param minX    the minimum X coordinate of the AABB (world space)
     * @param maxX    the maximum X coordinate of the AABB (world space)
     * @param minY    the minimum Y coordinate of the AABB (world space)
     * @param maxY    the maximum Y coordinate of the AABB (world space)
     * @param outIdxs a preallocated buffer where cell indices will be written
     * @return the number of grid cell indices written into {@code outIdxs}
     */
    private int computeCellIdxsClamped(
            double minX, double maxX, double minY, double maxY, int[] outIdxs) {

        int minCx = (int) (minX * this.invCellSize);
        int maxCx = (int) (maxX * this.invCellSize);
        int minCy = (int) (minY * this.invCellSize);
        int maxCy = (int) (maxY * this.invCellSize);

        // Clamp to the fixed grid topology
        minCx = this.clamp0ToHi(minCx, this.cellsX - 1);
        maxCx = this.clamp0ToHi(maxCx, this.cellsX - 1);
        minCy = this.clamp0ToHi(minCy, this.cellsY - 1);
        maxCy = this.clamp0ToHi(maxCy, this.cellsY - 1);

        // Estimate how many cells we need to iterate (for validation before the loop)
        int cellCountNeeded = (maxCx - minCx + 1) * (maxCy - minCy + 1);

        // Validate BEFORE iterating
        if (cellCountNeeded > outIdxs.length) {
            throw new IllegalArgumentException(
                    "Query region requires " + cellCountNeeded + " cells, but buffer only has " + outIdxs.length);
        }

        // Generate cell indices without bounds checks inside the loop
        int idx = 0;
        for (int cx = minCx; cx <= maxCx; cx++) {
            for (int cy = minCy; cy <= maxCy; cy++) {
                outIdxs[idx++] = cellIdx(cx, cy);
            }
        }
        return idx;
    }

    private int computeCellIdxsClampedFromCenter(
            double centerX, double centerY, double radius, int[] outIdxs) {

        int minCx = (int) ((centerX - radius) * invCellSize);
        int maxCx = (int) ((centerX + radius) * invCellSize);
        int minCy = (int) ((centerY - radius) * invCellSize);
        int maxCy = (int) ((centerY + radius) * invCellSize);

        minCx = this.clamp0ToHi(minCx, cellsX - 1);
        maxCx = this.clamp0ToHi(maxCx, cellsX - 1);
        minCy = this.clamp0ToHi(minCy, cellsY - 1);
        maxCy = this.clamp0ToHi(maxCy, cellsY - 1);

        // Estimar cuántas celdas necesitamos
        int cellCountNeeded = (maxCx - minCx + 1) * (maxCy - minCy + 1);

        // Validar ANTES de iterar
        if (cellCountNeeded > outIdxs.length) {
            throw new IllegalArgumentException(
                    "Query region requires " + cellCountNeeded + " cells, but buffer only has " + outIdxs.length);
        }

        // Iterar sin checkeos de límite en el loop
        int idx = 0;
        for (int cx = minCx; cx <= maxCx; cx++) {
            for (int cy = minCy; cy <= maxCy; cy++) {
                outIdxs[idx++] = cellIdx(cx, cy);
            }
        }

        return idx;
    }

    private int cellIdx(int cx, int cy) {
        return cy * this.cellsX + cx;
    }

    private int clamp0ToHi(int value, int highLimit) {
        return (value < 0) ? 0 : (value > highLimit) ? highLimit : value;
    }

    private void requireBuffer(int[] buf) {
        if (buf == null || buf.length < maxCellsPerBody)
            throw new IllegalArgumentException(
                    "idxsBuffer length must be >= maxCellsPerBody (" + maxCellsPerBody + ")");
    }

    // *** PRIVATE STATIC ***

    private static boolean contains(int[] arr, int count, int value) {
        for (int i = 0; i < count; i++)
            if (arr[i] == value)
                return true;

        return false;
    }
}
