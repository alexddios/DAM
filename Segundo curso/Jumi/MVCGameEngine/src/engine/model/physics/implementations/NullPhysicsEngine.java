package engine.model.physics.implementations;

import engine.model.physics.core.AbstractPhysicsEngine;
import engine.model.physics.ports.PhysicsValuesDTO;

public class NullPhysicsEngine extends AbstractPhysicsEngine {

    // region Constructors
    public NullPhysicsEngine(PhysicsValuesDTO dto1, PhysicsValuesDTO dto2, PhysicsValuesDTO dto3) {
        super(dto1, dto2, dto3);
    }

    public NullPhysicsEngine(double size, double posX, double posY, double angle) {
        super(size, posX, posY, angle);
    }
    // endregion

    // *** PUBLIC ***

    @Override
    public void angularAccelerationInc(double angularAcc) {
    }

    @Override
    public PhysicsValuesDTO calcNewPhysicsValues() {
        return this.getPhysicsValues();
    }

    @Override
    public boolean isThrusting() {
        return false;
    }

    @Override
    public void reboundInEast(PhysicsValuesDTO phyValues, double worldDim_x, double worldDim_y) {
    }

    @Override
    public void reboundInWest(PhysicsValuesDTO phyValues, double worldDim_x, double worldDim_y) {
    }

    @Override
    public void reboundInNorth(PhysicsValuesDTO phyValues, double worldDim_x, double worldDim_y) {
    }

    @Override
    public void reboundInSouth(PhysicsValuesDTO phyValues, double worldDim_x, double worldDim_y) {
    }

    @Override
    public void setAngularSpeed(double angularSpeed) {
    }
}
