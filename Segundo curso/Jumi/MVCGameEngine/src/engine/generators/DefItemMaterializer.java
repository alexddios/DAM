package engine.generators;

import java.util.Random;

import engine.world.ports.DefItem;
import engine.world.ports.DefItemDTO;
import engine.world.ports.DefItemPrototypeDTO;

public final class DefItemMaterializer {

    // region Fields
    private final Random rnd = new Random();
    // endregion

    // *** CONSTRUCTORS ***

    // *** PUBLIC (alphabetical) ***

    public DefItemDTO defItemToDTO(DefItem defItem) {
        if (defItem == null) {
            throw new IllegalArgumentException("DefItem cannot be null");
        }

        return switch (defItem) {
            case DefItemDTO dto -> dto;
            case DefItemPrototypeDTO proto -> this.materialize(proto);
        };
    }

    // *** PRIVATE (alphabetical) ***
    private double randomDoubleBetween(double min, double max) {
        if (max < min) {
            throw new IllegalArgumentException("max must be >= min");
        }
        if (min == max)
            return min;

        return min + rnd.nextDouble() * (max - min);
    }

    private DefItemDTO materialize(DefItemPrototypeDTO proto) {

        double size = this.randomDoubleBetween(proto.minSize, proto.maxSize);
        double angle = this.randomDoubleBetween(proto.minAngle, proto.maxAngle);
        double x = this.randomDoubleBetween(proto.posMinX, proto.posMaxX);
        double y = this.randomDoubleBetween(proto.posMinY, proto.posMaxY);

        double speed = this.randomDoubleBetween(proto.speedMin, proto.speedMax);
        double angularSpeed = this.randomDoubleBetween(proto.angularSpeedMin, proto.angularSpeedMax);
        double thrust = this.randomDoubleBetween(proto.thrustMin, proto.thrustMax);
        double angleRad = Math.toRadians(angle);

        double speedX = speed == 0.0 ? 0.0 : Math.cos(angleRad) * speed;
        double speedY = speed == 0.0 ? 0.0 : Math.sin(angleRad) * speed;

        return new DefItemDTO(
                proto.assetId, size, angle, x, y, proto.density,
                speedX, speedY, angularSpeed, thrust);
    }

}

// private DoubleVector radialSpeedFromCenter() {
// double angle = this.rnd.nextDouble() * Math.PI * 2.0;
// double module = this.AIConfig.fixedSpeed
// ? Math.sqrt(this.AIConfig.speedX * this.AIConfig.speedX
// + this.AIConfig.speedY * this.AIConfig.speedY)
// : this.AIConfig.maxSpeedModule * this.rnd.nextDouble();
// return new DoubleVector(
// Math.cos(angle), Math.sin(angle), module);
// }