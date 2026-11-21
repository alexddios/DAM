package engine.view.renderables.ports;

public class DynamicRenderDTO extends RenderDTO {

    public long timeStamp;
    public double speedX;
    public double speedY;
    public double accX;
    public double accY;

    public DynamicRenderDTO(
            String entityId,
            double posX, double posY,
            double angle,
            double size,
            long timeStamp,
            double speedX, double speedY,
            double accX, double accY,
            long timestamp) {

        super(entityId, posX, posY, angle, size, timestamp);

        this.timeStamp = timeStamp;
        this.speedX = speedX;
        this.speedY = speedY;
        this.accX = accX;
        this.accY = accY;
    }

    public void updateFrom(
            String entityId,
            double posX, double posY,
            double angle,
            double size,
            long timeStamp,
            double speedX, double speedY,
            double accX, double accY,
            long timestamp) {
        this.updateBase(entityId, posX, posY, angle, size, timestamp);
        this.timeStamp = timeStamp;
        this.speedX = speedX;
        this.speedY = speedY;
        this.accX = accX;
        this.accY = accY;
    }

    public void updateFrom(DynamicRenderDTO other) {
        if (other == null) {
            return;
        }

        this.updateFrom(
                other.entityId,
                other.posX, other.posY,
                other.angle,
                other.size,
                other.timeStamp,
                other.speedX, other.speedY,
                other.accX, other.accY,
                other.timestamp);
    }

    @Override
    public void reset() {
        super.reset();
        this.timeStamp = 0L;
        this.speedX = 0.0;
        this.speedY = 0.0;
        this.accX = 0.0;
        this.accY = 0.0;
    }
}
