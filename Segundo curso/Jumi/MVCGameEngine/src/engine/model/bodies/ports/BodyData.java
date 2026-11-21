package engine.model.bodies.ports;

import engine.model.physics.ports.PhysicsValuesDTO;

public class BodyData {

    // region Fields
    public final String entityId;
    public final BodyType type;
    private PhysicsValuesDTO physicsValues;
    // endregion

    // region Constructors
    public BodyData(String entityId, BodyType type, PhysicsValuesDTO phyValues) {
        this.entityId = entityId;
        this.type = type;
        this.physicsValues = phyValues;
    }
    // endregion

    // *** PUBLICS ***

    public PhysicsValuesDTO getPhysicsValues() {
        return physicsValues;
    }

    public void setPhysicsValues(PhysicsValuesDTO physicsValues) {
        this.physicsValues = physicsValues;
    }

}
