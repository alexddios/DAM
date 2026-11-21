package engine.controller.mappers;

import engine.model.emitter.ports.EmitterConfigDto;
import engine.world.ports.DefEmitterDTO;

public class EmitterMapper {

    public static EmitterConfigDto fromWorldDef(DefEmitterDTO emitterDef) {

        if (emitterDef == null) {
            return null;
        }

        return new EmitterConfigDto(
                // Spawn body config
                emitterDef.bodyType,
                emitterDef.bodyAssetId,
                emitterDef.bodySize,
                emitterDef.emitterOffsetVertical,
                emitterDef.emitterOffsetHorizontal,
                emitterDef.bodyInitialSpeed,
                0.0,
                emitterDef.bodyThrustDuration,
                emitterDef.bodyAngularSpeed,
                emitterDef.bodyAngularAcceleration,
                emitterDef.bodyThrust,
                emitterDef.randomizeInitialAngle,
                emitterDef.randomizeSize,
                emitterDef.bodyAddEmitterSpeedOnHeading,

                // Emitter config
                emitterDef.emissionRate,
                emitterDef.unlimitedBodies,
                emitterDef.maxBodiesEmitted,
                emitterDef.burstEmissionRate,
                emitterDef.burstSize,
                emitterDef.emitterReloadTime,
                emitterDef.bodyMass,
                emitterDef.bodyMaxLifetime);
    }
}
