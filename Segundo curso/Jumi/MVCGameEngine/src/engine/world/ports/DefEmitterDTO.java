package engine.world.ports;

import engine.model.bodies.ports.BodyType;

public class DefEmitterDTO {

    // region Fields
    public final String bodyAssetId;
    public final double bodyAngularAcceleration;
    public final double bodyAngularSpeed;
    public final boolean bodyAddEmitterSpeedOnHeading;
    public final double bodyThrust;
    public final double bodyThrustDuration;
    public final double bodyMass;
    public final double bodyMaxLifetime;
    public final double bodySize;
    public final double bodyInitialSpeed;
    public final BodyType bodyType;

    public final double burstEmissionRate;
    public final int burstSize;
    public final double emissionRate;
    public final boolean unlimitedBodies;
    public final int maxBodiesEmitted;
    public final double emitterOffsetHorizontal;
    public final double emitterOffsetVertical;
    public final boolean randomizeInitialAngle;
    public final boolean randomizeSize;
    public final double emitterReloadTime;
    // endregion

    // *** CONSTRUCTOR ***

    public DefEmitterDTO(
            String bodyAssetId,
            double bodyAngularAcceleration,
            double bodyAngularSpeed,
            boolean bodyAddEmitterSpeedOnHeading,
            double bodyThrust,
            double bodyThrustDuration,
            double bodyMass,
            double bodyMaxLifetime,
            double bodySize,
            double bodyInitialSpeed,
            BodyType bodyType,

            double burstEmissionRate,
            int burstSize,
            double emissionRate,
            double emitterOffsetHorizontal,
            double emitterOffsetVertical,
            double emitterReloadTime,
            boolean unlimitedBodies,
            int maxBodiesEmitted,
            boolean randomizeInitialAngle,
            boolean randomizeSize) {

        if (bodyType == null) {
            throw new IllegalArgumentException("BodyType cannot be null");
        }

        // Ranges checking
        if (bodyMass <= 0.0)
            throw new IllegalArgumentException("bodyMass must be > 0");
        if (bodySize <= 0.0)
            throw new IllegalArgumentException("bodySize must be > 0");

        if (bodyMaxLifetime < -1 && bodyMaxLifetime == 0)
            throw new IllegalArgumentException("bodyMaxLifetime must be >= -1 and != 0");

        if (bodyThrust < 0.0)
            throw new IllegalArgumentException("bodyThrust must be >= 0");
        if (bodyThrustDuration < 0.0)
            throw new IllegalArgumentException("bodyThrustDuration must be >= 0");

        if (emitterReloadTime < 0.0)
            throw new IllegalArgumentException("emitterReloadTime must be >= 0");
        if (maxBodiesEmitted < 0)
            throw new IllegalArgumentException("maxBodiesEmitted must be >= 0");

        if (burstSize < 0)
            throw new IllegalArgumentException("burstSize must be >= 0");
        if (emissionRate < 0.0)
            throw new IllegalArgumentException("emissionRate must be >= 0");
        if (burstEmissionRate < 0.0)
            throw new IllegalArgumentException("burstEmissionRate must be >= 0");

        // Burst-only params must make sense together
        if (burstSize == 0 && burstEmissionRate > 0.0) {
            throw new IllegalArgumentException("burstEmissionRate > 0 requires burstSize > 0");
        }
        if (burstSize > 0 && burstEmissionRate <= 0.0) {
            throw new IllegalArgumentException("burstSize > 0 requires burstEmissionRate > 0");
        }

        // If thrust is 0, duration must be 0 (avoid confusing configs)
        if (bodyThrust == 0.0 && bodyThrustDuration > 0.0) {
            throw new IllegalArgumentException("bodyThrustDuration > 0 requires bodyThrust > 0");
        }

        this.bodyAddEmitterSpeedOnHeading = bodyAddEmitterSpeedOnHeading;
        this.bodyAngularAcceleration = bodyAngularAcceleration;
        this.bodyAngularSpeed = bodyAngularSpeed;
        this.bodyAssetId = bodyAssetId;
        this.bodyInitialSpeed = bodyInitialSpeed;
        this.bodyMass = bodyMass;
        this.bodyMaxLifetime = bodyMaxLifetime;
        this.bodySize = bodySize;
        this.bodyThrust = bodyThrust;
        this.bodyThrustDuration = bodyThrustDuration;
        this.bodyType = bodyType; // Body type to emit
        
        this.burstEmissionRate = burstEmissionRate;
        this.burstSize = burstSize;
        this.emissionRate = emissionRate;
        this.emitterOffsetHorizontal = emitterOffsetHorizontal;
        this.emitterOffsetVertical = emitterOffsetVertical;
        this.emitterReloadTime = emitterReloadTime;
        this.unlimitedBodies = unlimitedBodies;
        this.maxBodiesEmitted = maxBodiesEmitted;
        this.randomizeInitialAngle = randomizeInitialAngle;
        this.randomizeSize = randomizeSize;
    }
}
