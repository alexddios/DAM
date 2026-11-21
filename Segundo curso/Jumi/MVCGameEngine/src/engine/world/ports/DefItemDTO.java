package engine.world.ports;

public final class DefItemDTO implements DefItem {

    // region Fields
    public final String assetId;
    public final double size;
    public final double angle;
    public final double posX;
    public final double posY;
    public final double density;

    public final double speedX;
    public final double speedY;
    public final double angularSpeed;
    public final double thrust;

    // endregion

    // *** CONSTRUCTOR ***
    public DefItemDTO(
            String assetId, double size, double angle,
            double posX, double posY, double density,
            double speedX, double speedY,
            double angularSpeed, double thrust) {

        // Defensive validations
        if (assetId == null || assetId.isBlank()) {
            throw new IllegalArgumentException("AssetId cannot be null or blank.");
        }
        if (size <= 0d) {
            throw new IllegalArgumentException("Size must be greater than zero.");
        }
        if (density <= 0d) {
            throw new IllegalArgumentException("Density must be greater than zero.");
        }

        this.assetId = assetId;
        this.size = size;
        this.angle = angle;
        this.posX = posX;
        this.posY = posY;
        this.density = density;

        this.speedX = speedX;
        this.speedY = speedY;
        this.angularSpeed = angularSpeed;
        this.thrust = thrust;
    }

    public DefItemDTO(
            String assetId, double size, double angle,
            double posX, double posY, double density) {

        this(assetId, size, angle, 
            posX, posY, density, 
            0.0, 0.0, 
            0.0, 0.0);
    }
}
