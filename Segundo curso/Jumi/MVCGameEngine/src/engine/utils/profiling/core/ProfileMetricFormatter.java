package engine.utils.profiling.core;

/**
 * Functional interface for formatting ProfileMetricsDTO to String.
 * 
 * Allows custom formatting of metrics for different display contexts
 * (HUD, console, logs, etc.).
 * 
 * Examples:
 * - Simple totalMs: dto -> String.format("%.3f ms", dto.totalMs)
 * - With samples: dto -> String.format("%.1f ms [%d]", dto.totalMs, dto.samples)
 * - FPS format: dto -> String.format("%.0f fps", dto.totalMs)
 */
@FunctionalInterface
public interface ProfileMetricFormatter {
    
    /**
     * Format ProfileMetricsDTO to String.
     * 
     * @param dto the metrics to format
     * @return formatted string representation
     */
    String format(ProfileMetricsDTO dto);
    
    // region Default Formatters
    
    /**
     * Default formatter: totalMs with 3 decimals and "ms" suffix.
     */
    ProfileMetricFormatter DEFAULT_MS = dto -> String.format("%.3f ms", dto.totalMs);
    
    /**
     * Formatter: totalMs with 1 decimal and sample count.
     */
    ProfileMetricFormatter WITH_SAMPLES = dto -> 
        String.format("%.1f ms [%d samples]", dto.totalMs, dto.samples);
    
    /**
     * Formatter: avgMs with 2 decimals (average).
     */
    ProfileMetricFormatter AVG_MS = dto -> String.format("%.2f ms", dto.avgMs);
    
    /**
     * Formatter: FPS-style with no decimals.
     */
    ProfileMetricFormatter FPS = dto -> String.format("%.0f fps", dto.totalMs);
    
    /**
     * Formatter: Complete info (avg/max/total/samples).
     */
    ProfileMetricFormatter FULL = dto -> 
        String.format("avg=%.2f ms, max=%.2f ms, total=%.2f ms, samples=%d", 
            dto.avgMs, dto.maxMs, dto.totalMs, dto.samples);
    
    // endregion Default Formatters
}
