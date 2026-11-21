package engine.events.domain.ports;

import engine.model.bodies.ports.BodyType;

/**
 * BodyToEmitDTO
 *
 * Immutable configuration describing a Body that will be emitted by an Emitter.
 *
 * This DTO defines *what* body is created and *how* it is emitted, including:
 * - initial spatial offsets relative to the emitter orientation
 * - initial linear and angular kinematics
 * - lifetime and physical properties
 * - emission-specific modifiers such as randomness and velocity inheritance
 *
 * IMPORTANT:
 * This is an emission-oriented spawn specification.
 * Some fields (e.g. addEmitterSpeed, forward/side offsets, randomization flags)
 * explicitly depend on the emitter context and should not be assumed to be
 * generic spawn parameters.
 *
 * This DTO is intentionally passive:
 * - it contains no logic
 * - it does not reference the emitter itself
 * - it can be safely transported across layers (events, rules, model execution)
 *
 * If a future use case requires generic or context-free spawning (e.g. level
 * initialization or respawn), a separate DTO should be introduced instead of
 * reusing this one.
 */
public class BodyToEmitDTO {
    public final BodyType type;
    public final String assetId;
    public final double size;
    public final double forwardOffset;
    public final double sideOffset;
    public final double speed;
    public final double acceleration;
    public final double accelerationTime;
    public final double angularSpeed;
    public final double angularAcc;
    public final double thrust;
    public final double bodyMass;
    public final double maxLifeTime;

    public final boolean randomAngle;
    public final boolean randomSize;
    public final boolean addEmitterSpeed;

    public BodyToEmitDTO(
            BodyType type,
            String assetId,
            double size,
            double forwardOffset,
            double sideOffset,
            double speed,
            double acceleration,
            double accelerationTime,
            double angularSpeed,
            double angularAcc,
            double thrust,
            double bodyMass,
            double maxLifeTime,
            boolean randomAngle,
            boolean randomSize,
            boolean addEmitterSpeed) {

        if (type == null)
            throw new IllegalArgumentException("type required");
        if (assetId == null || assetId.isBlank())
            throw new IllegalArgumentException("assetId required");
        if (!Double.isFinite(size) || size <= 0)
            throw new IllegalArgumentException("size must be finite and > 0");
        if (!Double.isFinite(bodyMass) || bodyMass < 0)
            throw new IllegalArgumentException("bodyMass must be finite and >= 0");
        if (!Double.isFinite(maxLifeTime) || (maxLifeTime <= 0 && maxLifeTime != -1D))
            throw new IllegalArgumentException("maxLifeTime must be -1 or finite and > 0");

        this.type = type;
        this.assetId = assetId;
        this.size = size;
        this.forwardOffset = forwardOffset;
        this.sideOffset = sideOffset;
        this.speed = speed;
        this.acceleration = acceleration;
        this.accelerationTime = accelerationTime;
        this.angularSpeed = angularSpeed;
        this.angularAcc = angularAcc;
        this.thrust = thrust;
        this.bodyMass = bodyMass;
        this.maxLifeTime = maxLifeTime;
        this.randomAngle = randomAngle;
        this.randomSize = randomSize;
        this.addEmitterSpeed = addEmitterSpeed;
    }
}
