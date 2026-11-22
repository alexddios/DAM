package engine.utils.profiling.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Generic base profiler using Template Method pattern.
 * 
 * Provides a single global timer per instance with flexible metrics that can be
 * configured by subclasses. Each metric has a type (INSTANT, TOTAL,
 * TOTAL_PERIOD, AVG_PERIOD)
 * that determines how it accumulates and resets.
 * 
 * Subclasses implement configureMetrics() to define their specific metrics,
 * and customReport() to display results in their preferred format.
 * 
 * Follows CODE_ORGANIZATION_STANDARD.md:
 * - Alphabetical method ordering
 * - No inner classes
 * - Regions for functional grouping
 */
public abstract class AbstractProfiler {

    // region Constants
    protected static final boolean ENABLED = true;
    protected static final boolean CONSOLE_ENABLED = false;
    // endregion Constants

    // region Fields
    protected final long reportIntervalNanos;
    protected final ConcurrentHashMap<String, ProfileMetric> metricsMap = new ConcurrentHashMap<>();
    protected final Map<String, MetricType> metricTypes = new ConcurrentHashMap<>();
    protected final Map<String, ProfileMetricFormatter> formatters = new ConcurrentHashMap<>();
    protected volatile long lastReportNanos = System.nanoTime();
    protected volatile long lastSnapshotNanos = System.nanoTime();
    protected volatile AtomicReference<ProfileSnapshot> lastSnapshot = new AtomicReference<>(null);
    // endregion Fields

    // region Constructors
    /**
     * Initialize profiler with report interval in nanoseconds.
     * Automatically calls configureMetrics() to populate metrics.
     * 
     * @param reportIntervalNanos interval between reports (e.g., 1_000_000_000L for
     *                            1 second)
     */
    protected AbstractProfiler(long reportIntervalNanos) {
        this.reportIntervalNanos = reportIntervalNanos;
        configureMetrics(); // Hook for subclasses
    }
    // endregion Constructors

    // *** PUBLICS ***

    // region Add (add***)
    /**
     * Register a new metric with its type and default formatter.
     * Should be called from configureMetrics().
     * 
     * @param key  metric name
     * @param type how this metric behaves
     */
    public final void addMetric(String key, MetricType type) {
        addMetric(key, type, ProfileMetricFormatter.DEFAULT_MS);
    }

    /**
     * Register a new metric with its type and custom formatter.
     * Should be called from configureMetrics().
     * 
     * @param key       metric name
     * @param type      how this metric behaves
     * @param formatter custom formatter for this metric
     */
    public final void addMetric(String key, MetricType type, ProfileMetricFormatter formatter) {
        metricsMap.putIfAbsent(key, new ProfileMetric());
        metricTypes.put(key, type);
        formatters.put(key, formatter);
    }

    /**
     * Add value to a metric (accumulates).
     * Updates based on metric type.
     * 
     * @param key   metric name
     * @param value nanoseconds to add
     */
    public final void addValue(String key, long value) {
        if (!ENABLED) {
            return;
        }

        updateMetric(key, value);
    }
    // endregion Add

    // region Getters (get***)
    /**
     * Get all metrics as a map.
     * 
     * @return Map with all current metrics
     */
    public final Map<String, ProfileMetricsDTO> getAllMetrics() {
        Map<String, ProfileMetricsDTO> result = new HashMap<>();
        for (Map.Entry<String, ProfileMetric> entry : metricsMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getMetrics());
        }
        return result;
    }

    /**
     * Get average milliseconds for a metric key from last snapshot.
     * 
     * @param key metric name
     * @return average milliseconds or 0 if not available
     */
    protected final double getAvgMs(String key) {
        ProfileSnapshot snapshot = getLastSnapshot();
        if (snapshot == null) {
            return 0.0;
        }

        ProfileMetricsDTO metrics = snapshot.getSectionMetrics(key);
        return metrics != null ? metrics.avgMs : 0.0;
    }

    /**
     * Get last captured snapshot of all metrics.
     * 
     * @return ProfileSnapshot or null if none available
     */
    public final ProfileSnapshot getLastSnapshot() {
        return lastSnapshot.get();
    }

    /**
     * Get metrics for a specific metric key.
     * 
     * @param key metric name
     * @return ProfileMetricsDTO or null if metric not found
     */
    public final ProfileMetricsDTO getMetric(String key) {
        ProfileMetric metric = metricsMap.get(key);
        if (metric != null) {
            return metric.getMetrics();
        }
        return null;
    }

    /**
     * Get metric formatted as String using registered formatter.
     * 
     * @param key metric name
     * @return formatted string or "N/A" if not available
     */
    public final String getMetricString(String key) {
        ProfileMetricsDTO dto = getMetric(key);
        if (dto == null) {
            return "N/A";
        }

        ProfileMetricFormatter formatter = formatters.getOrDefault(key, ProfileMetricFormatter.DEFAULT_MS);
        return formatter.format(dto);
    }

    /**
     * Sum total milliseconds from multiple metrics.
     * Useful for aggregating related metrics (e.g., all physics sub-metrics).
     * 
     * @param metricKeys metric names to sum
     * @return sum of total milliseconds from all specified metrics
     */
    protected final double sumMetrics(String... metricKeys) {
        ProfileSnapshot snapshot = getLastSnapshot();
        if (snapshot == null) {
            return 0.0;
        }
        
        double sum = 0.0;
        for (String key : metricKeys) {
            ProfileMetricsDTO dto = snapshot.getSectionMetrics(key);
            if (dto != null) {
                sum += dto.totalMs;
            }
        }
        return sum;
    }
    // endregion Get

    // region Is (is***)
    /**
     * Check if profiler is enabled.
     * 
     * @return true if profiling is active
     */
    public final boolean isEnabled() {
        return ENABLED;
    }
    // endregion Is

    // region Set
    /**
     * Set instantaneous value for a metric (overwrites previous).
     * 
     * @param key   metric name
     * @param value nanoseconds or raw value
     */
    public final void setValue(String key, long value) {
        if (!ENABLED) {
            return;
        }

        ProfileMetric metric = metricsMap.computeIfAbsent(key, k -> new ProfileMetric());
        metric.setInstant(value);
    }
    // endregion Set

    // region Start (start***)
    /**
     * Start interval timer and return start timestamp.
     *
     * @return start timestamp in nanoseconds
     */
    public final long startInterval() {
        if (!ENABLED) {
            return 0L;
        }

        return System.nanoTime();
    }
    // endregion

    // region Stop (stop***)

    /**
     * Stop interval timer for a specific metric and record its elapsed time.
     *
     * @param key metric name
     * @param startNanos start timestamp returned by startInterval()
     */
    public final void stopInterval(String key, long startNanos) {
        if (!ENABLED || startNanos == 0L) {
            return;
        }

        long elapsed = System.nanoTime() - startNanos;
        updateMetric(key, elapsed);
        maybeReport();
    }
    // endregion

    // *** PRIVATE ***

    private ProfileSnapshot captureSnapshot() {
        Map<String, ProfileMetricsDTO> sections = new HashMap<>();
        for (Map.Entry<String, ProfileMetric> entry : metricsMap.entrySet()) {
            sections.put(entry.getKey(), entry.getValue().getMetrics());
        }

        return new ProfileSnapshot(null, sections);
    }

    /**
     * Hook for subclasses: define metrics using addMetric().
     * Called during construction.
     */
    protected abstract void configureMetrics();

    /**
     * Hook for subclasses: custom report format.
     * Called every reporting period.
     */
    protected abstract void customReport();

    private void maybeReport() {
        long now = System.nanoTime();
        if (now - lastReportNanos >= reportIntervalNanos) {
            reportIfDue(now);
        }
    }

    // region on (on***)
    /**
     * Hook for subclasses: called at end of reporting period.
     * Can be used for cleanup or special processing.
     *
     * @param elapsedNanos elapsed time since last report
     */
    protected void onPeriodEnd(long elapsedNanos) {
        // Default: do nothing
    }

    /**
     * Hook for subclasses: called at end of reporting period.
     * Can be used for cleanup or special processing.
     */
    protected void onPeriodEnd() {
        // Default: do nothing
    }
    // endregion

    // region Report (report***)
    private void reportIfDue(long now) {
        synchronized (this) {
            if (now - lastReportNanos < reportIntervalNanos) {
                return;
            }

            long elapsedNanos = now - lastReportNanos;
            lastReportNanos = now;

            // Capture snapshot
            ProfileSnapshot snapshot = captureSnapshot();
            lastSnapshot.set(snapshot);
            lastSnapshotNanos = now;

            // Reset period metrics
            resetPeriodMetrics();

            // Custom reporting
            if (CONSOLE_ENABLED) {
                customReport();
            }

            // Hook
            onPeriodEnd(elapsedNanos);
            onPeriodEnd();
        }
    }

    /**
     * Print a metric to console with label.
     * 
     * @param key   metric name
     * @param label display label
     */
    protected final void reportMetric(String key, String label) {
        ProfileMetric metric = metricsMap.get(key);
        if (metric != null) {
            metric.report(label);
        }
    }
    // endregion

    private void resetPeriodMetrics() {
        for (ProfileMetric metric : metricsMap.values()) {
            metric.resetPeriod();
        }
    }

    private void updateMetric(String key, long elapsed) {
        ProfileMetric metric = metricsMap.computeIfAbsent(key, k -> new ProfileMetric());
        MetricType type = metricTypes.getOrDefault(key, MetricType.TOTAL_PERIOD);

        metric.record(elapsed, type);
    }
}
