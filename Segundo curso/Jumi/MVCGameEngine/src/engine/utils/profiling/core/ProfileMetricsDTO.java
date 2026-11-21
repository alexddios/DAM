package engine.utils.profiling.core;

/**
 * Snapshot of profiling metrics for a specific section.
 * Used to display instrumentation data in HUD.
 */
public class ProfileMetricsDTO {
    public final double avgMs;
    public final double maxMs;
    public final double totalMs;
    public final long samples;

    public ProfileMetricsDTO(double avgMs, double maxMs, double totalMs, long samples) {
        this.avgMs = avgMs;
        this.maxMs = maxMs;
        this.totalMs = totalMs;
        this.samples = samples;
    }

    @Override
    public String toString() {
        return String.format("avg=%.6f ms, max=%.6f ms, total=%.3f ms, samples=%d", 
            avgMs, maxMs, totalMs, samples);
    }

    /**
     * Format as a readable string for HUD display.
     * @return formatted string
     */
    public String toHUDString() {
        return String.format("%.3f ms (max: %.1f ms)", avgMs, maxMs);
    }

    /**
     * Format as total time per frame for comparison with single-threaded metrics.
     * @param fps frames per second to calculate frame time
     * @return formatted string showing ms/frame
     */
    public String toPerFrameString(long fps) {
        if (fps <= 0) {
            return "N/A";
        }
        double msPerFrame = this.totalMs / fps;
        // Show totalMs/s in brackets for reference
        return String.format("%.0f ms/frame (%.0f ms/s) [PARALLEL]", msPerFrame, this.totalMs);
    }
}
