package engine.view.renderables.ports;

import engine.utils.pooling.PoolableMDTO;

public class RenderDTO implements PoolableMDTO {

    public String entityId;
    public double posX;
    public double posY;
    public double angle;
    public double size;
    public long timestamp;

    public RenderDTO(
            String entityId, double posX, double posY, double angle, double size, long timestamp) {

        this.entityId = entityId;
        this.posX = posX;
        this.posY = posY;
        this.angle = angle;
        this.size = size;
        this.timestamp = timestamp;
    }

    public void updateBase(
            String entityId, double posX, double posY, double angle, double size, long timestamp) {
        this.entityId = entityId;
        this.posX = posX;
        this.posY = posY;
        this.angle = angle;
        this.size = size;
        this.timestamp = timestamp;
    }

    public void reset() {
        this.entityId = null;
        this.posX = 0.0;
        this.posY = 0.0;
        this.angle = 0.0;
        this.size = 0.0;
        this.timestamp = 0L;
    }
}
