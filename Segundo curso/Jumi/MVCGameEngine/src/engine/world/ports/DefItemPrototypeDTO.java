package engine.world.ports;

public final class DefItemPrototypeDTO implements DefItem {

    // region Fields
    public final String assetId;
    public final double density;
    public final double minSize;
    public final double maxSize;
    public final double minAngle;
    public final double maxAngle;
    public final double posMinX;
    public final double posMaxX;
    public final double posMinY;
    public final double posMaxY;

    public final double speedMin;
    public final double speedMax;
    public final double thrustMin;
    public final double thrustMax;
    public final double angularSpeedMin;
    public final double angularSpeedMax;
    // endregion

    // *** CONSTRUCTOR ***

    public DefItemPrototypeDTO(
            String assetId,
            double density,
            double minAngle, double maxAngle,
            double minSize, double maxSize,
            double posMinX, double posMaxX,
            double posMinY, double posMaxY,
            double speedMin, double speedMax,
            double thrustMin, double thrustMax,
            double angularSpeedMin, double angularSpeedMax) {

        this.assetId = assetId;
        this.density = density;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
        this.posMinX = posMinX;
        this.posMaxX = posMaxX;
        this.posMinY = posMinY;
        this.posMaxY = posMaxY;
        this.speedMin = speedMin;
        this.speedMax = speedMax;
        this.angularSpeedMin = angularSpeedMin;
        this.angularSpeedMax = angularSpeedMax;
        this.thrustMin = thrustMin;
        this.thrustMax = thrustMax;

    }

    public DefItemPrototypeDTO(
            String assetId, double density,
            double minAngle, double maxAngle,
            double minSize, double maxSize,
            double posMinX, double posMaxX,
            double posMinY, double posMaxY) {

        this(assetId, density,
                minAngle, maxAngle,
                minSize, maxSize,
                posMinX, posMaxX,
                posMinY, posMaxY,
                0.0, 0.0,
                0.0, 0.0,
                0.0, 0.0);
    }
}