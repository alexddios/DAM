package engine.view.renderables.ports;

/**
 * RenderMetricsDTO
 * ----------------
 * 
 * Contains rendering performance metrics broken down by rendering phase.
 * Provides detailed timing information for each rendering stage.
 */
public class RenderMetricsDTO {
    
    public final double backgroundMs;
    public final double staticMs;
    public final double dynamicMs;
    public final double queryDynamicMs;
    public final double paintDynamicMs;
    public final double hudsMs;
    public final double totalDrawMs;
    public final double updateMs;
    public final double frameMs;
    
    public RenderMetricsDTO(
            double backgroundMs,
            double staticMs,
            double dynamicMs,
            double queryDynamicMs,
            double paintDynamicMs,
            double hudsMs,
            double totalDrawMs,
            double updateMs,
            double frameMs) {
        
        this.backgroundMs = backgroundMs;
        this.staticMs = staticMs;
        this.dynamicMs = dynamicMs;
        this.queryDynamicMs = queryDynamicMs;
        this.paintDynamicMs = paintDynamicMs;
        this.hudsMs = hudsMs;
        this.totalDrawMs = totalDrawMs;
        this.updateMs = updateMs;
        this.frameMs = frameMs;
    }
    
    /**
     * Convert to Object array for HUD rendering
     */
    public Object[] toObjectArray() {
        return new Object[] {
            String.format("%.1f", this.backgroundMs),
            String.format("%.1f", this.staticMs),
            String.format("%.1f", this.queryDynamicMs),
            String.format("%.1f", this.paintDynamicMs),
            String.format("%.1f", this.dynamicMs),
            String.format("%.1f", this.hudsMs),
            String.format("%.1f", this.totalDrawMs),
            String.format("%.1f", this.updateMs),
            String.format("%.1f", this.frameMs)
        };
    }
}
