package engine.utils.profiling.core;

/**
 * Defines the behavior of metrics in the profiler.
 * 
 * Each metric can be one of four types, determining how it accumulates
 * and resets values over time.
 */
public enum MetricType {
    /**
     * Instantaneous value. setValue() overwrites the previous value.
     * No accumulation. Example: FPS counter.
     */
    INSTANT,

    /**
     * Interval-based value measured between start/stop marks.
     * Accumulates per period and resets every reporting period.
     */
    INTERVAL,

    /**
     * Accumulates indefinitely. Never resets.
     * Total since profiler creation. Example: total frames rendered.
     */
    TOTAL,

    /**
     * Accumulates within the current reporting period.
     * Resets every period (e.g., every second). 
     * Example: total time spent in PHYSICS_DT this second.
     */
    TOTAL_PERIOD,

    /**
     * Average calculated at the end of period.
     * Computed as: TOTAL_PERIOD / sample_count.
     * Resets with period. Example: average physics time in this period.
     */
    AVG_PERIOD
}
