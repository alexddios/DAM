package engine.utils.profiling.core;

import java.util.Map;

/**
 * Generic container for profiling snapshot.
 * Provides read-only access to profiling metrics for HUD rendering and analysis.
 * 
 * Works with any profiler type (BodyProfiler, RenderProfiler, etc.)
 * thanks to String-keyed sections map.
 */
public class ProfileSnapshot {

    // region Fields
    public final ProfileMetricsDTO total;
    public final Map<String, ProfileMetricsDTO> sections;
    private final long captureTimeNanos;
    // endregion Fields

    // region Constructors
    /**
     * Create a profiling snapshot.
     * 
     * @param total overall metrics (can be null)
     * @param sections map of metric_name -> ProfileMetricsDTO
     */
    public ProfileSnapshot(
            ProfileMetricsDTO total,
            Map<String, ProfileMetricsDTO> sections) {
        this.total = total;
        this.sections = sections != null ? sections : Map.of();
        this.captureTimeNanos = System.nanoTime();
    }
    // endregion Constructors

    // *** PUBLICS ***

    // region Get
    /**
     * Get metrics for a specific metric key.
     * Works with any profiler type.
     * 
     * @param key the metric key to query
     * @return metrics or null if not available
     */
    public ProfileMetricsDTO getSectionMetrics(String key) {
        return this.sections.get(key);
    }
    // endregion Get

    // region Is
    /**
     * Check if this snapshot is stale (older than threshold).
     * @param maxAgeNanos maximum age in nanoseconds
     * @return true if snapshot is older than threshold
     */
    public boolean isStale(long maxAgeNanos) {
        return (System.nanoTime() - this.captureTimeNanos) > maxAgeNanos;
    }
    // endregion Is
}
