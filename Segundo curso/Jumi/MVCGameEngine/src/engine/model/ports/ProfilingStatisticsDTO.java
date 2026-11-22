package engine.model.ports;

import java.util.Map;
import engine.utils.profiling.core.ProfileMetricsDTO;

/**
 * ProfilingStatisticsDTO
 * ----------------------
 * 
 * DTO containing performance profiling metrics for the current frame.
 * Flows from Model → Controller → View following standard MVC architecture.
 * 
 * Encapsulates metrics captured by BodyProfiler without exposing the profiler instance.
 * The Controller's responsibility is to format these metrics for HUD rendering.
 */
public class ProfilingStatisticsDTO {

    // region Fields
    public final Map<String, ProfileMetricsDTO> metrics;
    public final long captureTimeNanos;
    // endregion Fields

    // region Constructors
    public ProfilingStatisticsDTO(Map<String, ProfileMetricsDTO> metrics) {
        this.metrics = metrics != null ? metrics : Map.of();
        this.captureTimeNanos = System.nanoTime();
    }
    // endregion Constructors

    // *** PUBLICS ***

    // region Get
    public ProfileMetricsDTO getMetric(String key) {
        return this.metrics.get(key);
    }

    public java.util.Set<String> getMetricKeys() {
        return this.metrics.keySet();
    }
    // endregion Get
}
