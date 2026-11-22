package engine.model.bodies.ports;

import engine.model.bodies.core.AbstractBody;
import engine.model.bodies.impl.DynamicBody;
import engine.model.bodies.impl.PlayerBody;
import engine.model.bodies.impl.StaticBody;
import engine.model.physics.implementations.BasicPhysicsEngine;
import engine.model.physics.implementations.NullPhysicsEngine;
import engine.model.physics.ports.PhysicsEngine;
import engine.model.physics.ports.PhysicsValuesDTO;
import engine.utils.profiling.impl.BodyProfiler;
import engine.utils.spatial.core.SpatialGrid;

/**
 * Factory for creating bodies without threading concerns.
 * 
 * Responsible ONLY for body instantiation and physics engine setup.
 * Threading assignment is handled by BodyBatchManager and Model.
 */
public class BodyFactory {

    public static AbstractBody create(
            BodyEventProcessor bodyEventProcessor,
            SpatialGrid spatialGrid,
            PhysicsValuesDTO dto1,
            PhysicsValuesDTO dto2,
            PhysicsValuesDTO dto3,
            BodyType bodyType,
            double maxLifeTime,
            String emitterId,
            BodyProfiler profiler) {

        AbstractBody body = null;
        PhysicsEngine phyEngine = null;

        switch (bodyType) {
            case DYNAMIC:
                phyEngine = new BasicPhysicsEngine(dto1, dto2, dto3, profiler);
                body = new DynamicBody(
                        bodyEventProcessor, spatialGrid, phyEngine,
                        BodyType.DYNAMIC,
                        maxLifeTime, null, profiler);
                break;

            case PLAYER:
                phyEngine = new BasicPhysicsEngine(dto1, dto2, dto3, profiler);
                body = new PlayerBody(
                        bodyEventProcessor, spatialGrid, phyEngine,
                        maxLifeTime, null, profiler);
                break;

            case PROJECTILE:
                phyEngine = new BasicPhysicsEngine(dto1, dto2, dto3, profiler);
                body = new DynamicBody(
                        bodyEventProcessor, 
                        spatialGrid, 
                        phyEngine,
                        BodyType.PROJECTILE,
                        maxLifeTime,
                        emitterId,
                        profiler);
                break;

            case DECORATOR:
                phyEngine = new NullPhysicsEngine(dto1, dto2, dto3);
                body = new StaticBody(
                        bodyEventProcessor, null, phyEngine, bodyType,
                        maxLifeTime, null);
                break;

            case GRAVITY:
                phyEngine = new NullPhysicsEngine(dto1, dto2, dto3);
                body = new StaticBody(
                        bodyEventProcessor, spatialGrid, phyEngine, bodyType,
                        maxLifeTime, null);

                break;

            default:
                break;
        }

        return body;
    }

}
