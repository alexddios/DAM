package engine.utils.profiling.core;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * Thread-safe metric accumulator with support for different metric types.
 * 
 * Tracks samples, total time, max time, and period-based statistics.
 * Used internally by AbstractProfiler to store metric data.
 */
final class ProfileMetric {
    
    // region Fields
    private final LongAdder totalSamples = new LongAdder();
    private final LongAdder totalNanos = new LongAdder();
    private final LongAccumulator maxNanos = new LongAccumulator(Math::max, 0L);
    private final LongAdder periodSamples = new LongAdder();
    private final LongAdder periodNanos = new LongAdder();
    private volatile long periodAvgNanos = 0L;
    private volatile long instantValue = 0L;
    // endregion Fields
    
    // region Constructors
    ProfileMetric() {
        // Package-private constructor
    }
    // endregion Constructors
    
    // *** PUBLICS ***
    
    /**
     * Get current metrics without resetting values.
     * 
     * @return ProfileMetricsDTO snapshot
     */
    public ProfileMetricsDTO getMetrics() {
        long samples = Math.max(periodSamples.sum(), instantValue > 0 ? 1 : 0);
        long total = periodNanos.sum();
        long max = maxNanos.get();
        
        if (samples > 0) {
            double totalMs = total / 1_000_000.0d;
            double avgMs = periodAvgNanos / 1_000_000.0d;
            double maxMs = max / 1_000_000.0d;
            return new ProfileMetricsDTO(avgMs, maxMs, totalMs, samples);
        }
        
        return new ProfileMetricsDTO(0.0, 0.0, 0.0, 0L);
    }
    
    /**
     * Record a new measurement based on metric type.
     * 
     * @param elapsed time in nanoseconds
     * @param type how to accumulate this value
     */
    public void record(long elapsed, MetricType type) {
        switch (type) {
            case INSTANT:
                instantValue = elapsed;
                break;

            case INTERVAL:
                periodSamples.increment();
                periodNanos.add(elapsed);
                maxNanos.accumulate(elapsed);
                break;
            
            case TOTAL:
                totalSamples.increment();
                totalNanos.add(elapsed);
                maxNanos.accumulate(elapsed);
                break;
            
            case TOTAL_PERIOD:
                periodSamples.increment();
                periodNanos.add(elapsed);
                maxNanos.accumulate(elapsed);
                break;
            
            case AVG_PERIOD:
                periodSamples.increment();
                periodNanos.add(elapsed);
                maxNanos.accumulate(elapsed);
                break;
        }
    }
    
    /**
     * Report metric to console with label.
     * 
     * @param label display label for the metric
     */
    public void report(String label) {
        long samples = periodSamples.sum();
        long total = periodNanos.sum();
        long max = maxNanos.get();
        
        if (samples > 0) {
            double totalMs = total / 1_000_000.0d;
            double avgMs = totalMs / samples;
            double maxMs = max / 1_000_000.0d;
            System.out.println(label + " avg=" + String.format("%.6f", avgMs)
                    + " ms, max=" + String.format("%.6f", maxMs)
                    + " ms, total=" + String.format("%.3f", totalMs)
                    + " ms, samples=" + samples);
        }
    }
    
    /**
     * Reset period accumulators and calculate period average.
     * Called at end of reporting period.
     */
    public void resetPeriod() {
        long samples = periodSamples.sumThenReset();
        long total = periodNanos.sumThenReset();
        
        if (samples > 0) {
            periodAvgNanos = total / samples;
        }
    }
    
    /**
     * Set instantaneous value (INSTANT metric type).
     * 
     * @param value value to set
     */
    public void setInstant(long value) {
        instantValue = value;
    }
}
