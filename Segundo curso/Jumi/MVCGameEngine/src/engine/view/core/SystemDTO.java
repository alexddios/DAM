package engine.view.core;

public class SystemDTO {
    public final int fps;
    public final double renderTimeInMs;
    public final int imagesCached;
    public final float imagesCacheHitRate;
    public final int entityAliveQuantity;
    public final int entityDeadQuantity;

    public SystemDTO(int fps, double renderTimeInMs, int imagesCached,
            float imagesCacheHitRate, int entityAliveQuantity, int entityDeadQuantity) {

        this.renderTimeInMs = renderTimeInMs;
        this.fps = fps;
        this.imagesCached = imagesCached;
        this.imagesCacheHitRate = imagesCacheHitRate;
        this.entityAliveQuantity = entityAliveQuantity;
        this.entityDeadQuantity = entityDeadQuantity;
    }
}
