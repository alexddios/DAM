package engine.controller.mappers;

import engine.model.ports.ProfilingStatisticsDTO;
import engine.utils.profiling.core.ProfileMetricsDTO;

/**
 * ProfilingStatisticsMapper
 * -------------------------
 * 
 * Maps ProfilingStatisticsDTO (from Model) to Object[] format for HUD rendering.
 * Handles formatting and aggregation of profiling metrics for display.
 * 
 * Follows MVC pattern: Model → Mapper → View
 * The mapper is responsible for presentation logic separation.
 */
public class ProfilingStatisticsMapper {

    // *** PUBLICS ***

    // region From
    public static Object[] fromProfilingStatistics(ProfilingStatisticsDTO statistics, long fps) {
        Object[] values = new Object[4];

        if (statistics == null || statistics.metrics.isEmpty() || fps == 0) {
            for (int i = 0; i < 4; i++) {
                values[i] = "N/A";
            }
            return values;
        }

        // Calculations (DT + THRUST + LINEAR + ANGULAR combined)
        double calcTotal = 0;
        calcTotal += getMetricTotalMs(statistics, "PHYSICS_DT");
        calcTotal += getMetricTotalMs(statistics, "PHYSICS_THRUST");
        calcTotal += getMetricTotalMs(statistics, "PHYSICS_LINEAR");
        calcTotal += getMetricTotalMs(statistics, "PHYSICS_ANGULAR");

        if (calcTotal > 0) {
            double msPerFrame = calcTotal / fps;
            values[0] = String.format("%.0f", msPerFrame);
        } else {
            values[0] = "N/A";
        }

        // DTO creation - show as ms/frame
        ProfileMetricsDTO physicsDto = statistics.getMetric("PHYSICS_DTO");
        if (physicsDto != null) {
            double msPerFrame = physicsDto.totalMs / fps;
            values[1] = String.format("%.0f", msPerFrame);
        } else {
            values[1] = "N/A";
        }

        // Events - consolidated total
        double eventsTotal = 0;
        eventsTotal += getMetricTotalMs(statistics, "EVENTS_DETECT");
        eventsTotal += getMetricTotalMs(statistics, "EVENTS_DECIDE");
        eventsTotal += getMetricTotalMs(statistics, "EVENTS_EXECUTE");

        if (eventsTotal > 0) {
            double msPerFrame = eventsTotal / fps;
            values[2] = String.format("%.0f", msPerFrame);
        } else {
            values[2] = "N/A";
        }

        // Spatial Grid
        ProfileMetricsDTO spatialGrid = statistics.getMetric("SPATIAL_GRID");
        if (spatialGrid != null) {
            double msPerFrame = spatialGrid.totalMs / fps;
            values[3] = String.format("%.0f", msPerFrame);
        } else {
            values[3] = "N/A";
        }

        return values;
    }
    // endregion From

    // *** PRIVATE ***

    // region Get
    private static double getMetricTotalMs(ProfilingStatisticsDTO statistics, String key) {
        ProfileMetricsDTO metric = statistics.getMetric(key);
        return metric != null ? metric.totalMs : 0;
    }
    // endregion Get
}
