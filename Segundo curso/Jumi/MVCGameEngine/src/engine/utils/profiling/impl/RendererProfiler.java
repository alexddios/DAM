package engine.utils.profiling.impl;

import engine.utils.profiling.core.AbstractProfiler;
import engine.utils.profiling.core.MetricType;

/**
 * Specialized profiler for render loop timing and FPS metrics.
 *
 * Tracks update/draw/frame durations and computes FPS per reporting period.
 */
public class RendererProfiler extends AbstractProfiler {

    // region Constants
    public static final String METRIC_DRAW = "RENDER_DRAW";
    public static final String METRIC_FRAME = "RENDER_FRAME";
    public static final String METRIC_UPDATE = "RENDER_UPDATE";
    
    // Draw breakdown metrics
    public static final String METRIC_DRAW_BACKGROUND = "RENDER_DRAW_BACKGROUND";
    public static final String METRIC_DRAW_STATIC = "RENDER_DRAW_STATIC";
    public static final String METRIC_DRAW_DYNAMIC = "RENDER_DRAW_DYNAMIC";
    public static final String METRIC_DRAW_HUDS = "RENDER_DRAW_HUDS";
    
    // Dynamic breakdown metrics
    public static final String METRIC_QUERY_DYNAMIC = "RENDER_QUERY_DYNAMIC";
    public static final String METRIC_PAINT_DYNAMIC = "RENDER_PAINT_DYNAMIC";
    // endregion Constants

    // region Fields
    private long framesInPeriod = 0L;
    private volatile long lastFps = 0L;
    // endregion Fields

    // region Constructors
    public RendererProfiler(long reportIntervalNanos) {
        super(reportIntervalNanos);
    }
    // endregion Constructors

    // *** PUBLICS ***

    // region Add
    public void addFrame() {
        this.framesInPeriod++;
    }
    // endregion Add

    // region Get
    public double getAvgDrawMs() {
        return getAvgMs(METRIC_DRAW);
    }

    public double getAvgFrameMs() {
        return getAvgMs(METRIC_FRAME);
    }

    public double getAvgUpdateMs() {
        return getAvgMs(METRIC_UPDATE);
    }
    
    // Draw breakdown getters
    public double getAvgDrawBackgroundMs() {
        return getAvgMs(METRIC_DRAW_BACKGROUND);
    }
    
    public double getAvgDrawStaticMs() {
        return getAvgMs(METRIC_DRAW_STATIC);
    }
    
    public double getAvgDrawDynamicMs() {
        return getAvgMs(METRIC_DRAW_DYNAMIC);
    }
    
    public double getAvgDrawHudsMs() {
        return getAvgMs(METRIC_DRAW_HUDS);
    }
    
    public double getAvgQueryDynamicMs() {
        return getAvgMs(METRIC_QUERY_DYNAMIC);
    }
    
    public double getAvgPaintDynamicMs() {
        return getAvgMs(METRIC_PAINT_DYNAMIC);
    }

    public long getLastFps() {
        return this.lastFps;
    }
    // endregion Get

    // *** INTERFACE IMPLEMENTATIONS ***

    // region AbstractProfiler
    @Override
    protected void configureMetrics() {
        addMetric(METRIC_DRAW, MetricType.INTERVAL);
        addMetric(METRIC_FRAME, MetricType.INTERVAL);
        addMetric(METRIC_UPDATE, MetricType.INTERVAL);
        
        // Draw breakdown
        addMetric(METRIC_DRAW_BACKGROUND, MetricType.INTERVAL);
        addMetric(METRIC_DRAW_STATIC, MetricType.INTERVAL);
        addMetric(METRIC_DRAW_DYNAMIC, MetricType.INTERVAL);
        addMetric(METRIC_DRAW_HUDS, MetricType.INTERVAL);
        
        // Dynamic breakdown
        addMetric(METRIC_QUERY_DYNAMIC, MetricType.INTERVAL);
        addMetric(METRIC_PAINT_DYNAMIC, MetricType.INTERVAL);
    }

    @Override
    protected void customReport() {
        // No console output by default
    }

    @Override
    protected void onPeriodEnd(long elapsedNanos) {
        if (elapsedNanos > 0L) {
            this.lastFps = Math.round(this.framesInPeriod * (1_000_000_000.0 / elapsedNanos));
        } else {
            this.lastFps = 0L;
        }

        this.framesInPeriod = 0L;
    }
    // endregion AbstractProfiler
}
