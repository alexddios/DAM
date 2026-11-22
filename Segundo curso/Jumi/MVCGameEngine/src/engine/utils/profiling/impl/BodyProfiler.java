package engine.utils.profiling.impl;

import engine.utils.profiling.core.AbstractProfiler;
import engine.utils.profiling.core.MetricType;
import engine.utils.profiling.core.ProfileMetricsDTO;
import engine.utils.profiling.core.ProfileSnapshot;

/**
 * Specialized profiler for DynamicBody update operations.
 * 
 * Extends AbstractProfiler to provide body physics profiling with these metrics:
 * - PHYSICS_DT, PHYSICS_THRUST, PHYSICS_LINEAR, PHYSICS_ANGULAR, PHYSICS_DTO
 * - SPATIAL_GRID, EVENTS_DETECT, EVENTS_DECIDE, EVENTS_EXECUTE, EMITTERS
 * 
 * This is an instantiable class. Can be used as singleton or created per-session.
 * 
 * Follows CODE_ORGANIZATION_STANDARD.md:
 * - Alphabetical method ordering
 * - Regions for functional grouping
 * - Interface implementations separated
 */
public class BodyProfiler extends AbstractProfiler {

    // region Constants
    private static final boolean CONSOLE_ENABLED = false;
    // endregion Constants

    // region Constructors
    /**
     * Create a new BodyProfiler with default 1-second reporting interval.
     */
    public BodyProfiler() {
        super(1_000_000_000L);  // 1 second
    }

    /**
     * Create a BodyProfiler with custom reporting interval.
     * 
     * @param reportIntervalNanos custom interval between reports
     */
    public BodyProfiler(long reportIntervalNanos) {
        super(reportIntervalNanos);
    }
    // endregion Constructors

    // *** PUBLICS ***

    /**
     * Get instrumentation HUD values as Object array.
     * Normalized to ms/frame format for display.
     * 
     * @param fps frames per second (for normalization)
     * @return Object array with [calculations, DTO, events, grid] or N/A placeholders
     */
    public Object[] getInstrumentationHUDValues(long fps) {
        ProfileSnapshot snapshot = getLastSnapshot();
        Object[] values = new Object[4];

        if (snapshot == null || fps == 0) {
            for (int i = 0; i < 4; i++) {
                values[i] = "N/A";
            }
            return values;
        }

        // Calculations (DT + THRUST + LINEAR + ANGULAR combined)
        double calcTotal = sumMetrics("PHYSICS_DT", "PHYSICS_THRUST", "PHYSICS_LINEAR", "PHYSICS_ANGULAR");
        values[0] = calcTotal > 0 ? String.format("%.0f", calcTotal / fps) : "N/A";

        // DTO creation - show as ms/frame
        ProfileMetricsDTO physicsDto = snapshot.getSectionMetrics("PHYSICS_DTO");
        values[1] = physicsDto != null ? String.format("%.0f", physicsDto.totalMs / fps) : "N/A";

        // Events - consolidated total
        double eventsTotal = sumMetrics("EVENTS_DETECT", "EVENTS_DECIDE", "EVENTS_EXECUTE");
        values[2] = eventsTotal > 0 ? String.format("%.0f", eventsTotal / fps) : "N/A";

        // Spatial Grid
        ProfileMetricsDTO spatialGrid = snapshot.getSectionMetrics("SPATIAL_GRID");
        values[3] = spatialGrid != null ? String.format("%.0f", spatialGrid.totalMs / fps) : "N/A";

        return values;
    }

    // *** INTERFACE IMPLEMENTATIONS ***

    // region AbstractProfiler
    @Override
    protected void configureMetrics() {
        // Physics metrics
        addMetric("PHYSICS_DT", MetricType.INTERVAL);
        addMetric("PHYSICS_THRUST", MetricType.INTERVAL);
        addMetric("PHYSICS_LINEAR", MetricType.INTERVAL);
        addMetric("PHYSICS_ANGULAR", MetricType.INTERVAL);
        addMetric("PHYSICS_DTO", MetricType.INTERVAL);

        // Other system metrics
        addMetric("SPATIAL_GRID", MetricType.INTERVAL);

        // Event metrics (nested in EVENTS)
        addMetric("EVENTS_DETECT", MetricType.INTERVAL);
        addMetric("EVENTS_DECIDE", MetricType.INTERVAL);
        addMetric("EVENTS_EXECUTE", MetricType.INTERVAL);

        // Emitters
        addMetric("EMITTERS", MetricType.INTERVAL);
    }

    @Override
    protected void customReport() {
        System.out.println("=== DynamicBody Profile ===");
        reportMetric("PHYSICS_DT", "  physics.dt");
        reportMetric("PHYSICS_THRUST", "  physics.thrust");
        reportMetric("PHYSICS_LINEAR", "  physics.linear");
        reportMetric("PHYSICS_ANGULAR", "  physics.angular");
        reportMetric("PHYSICS_DTO", "  physics.dto");
        reportMetric("SPATIAL_GRID", "  spatialGrid");
        reportMetric("EVENTS_DETECT", "  events.detect");
        reportMetric("EVENTS_DECIDE", "  events.decide");
        reportMetric("EVENTS_EXECUTE", "  events.execute");
        reportMetric("EMITTERS", "  emitters");
    }

    @Override
    protected void onPeriodEnd() {
        // Hook for subclasses if needed
    }
    // endregion AbstractProfiler
}
