package engine.model.bodies.ports;

import engine.model.bodies.core.AbstractBody;
import engine.model.physics.ports.PhysicsValuesDTO;

public interface BodyEventProcessor {

    public void processBodyEvents(AbstractBody body, PhysicsValuesDTO newPhyValues, PhysicsValuesDTO oldPhyValues);

}
