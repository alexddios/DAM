package engine.model.physics.ports;

public interface PhysicsEngine {

        public void angularAccelerationInc(double angularAcc);

        public PhysicsValuesDTO calcNewPhysicsValues();

        public PhysicsValuesDTO getPhysicsValues();

        public boolean isThrusting();

        public void reboundInEast(
                        PhysicsValuesDTO phyValues, double worldDim_x, double worldDim_y);

        public void reboundInWest(
                        PhysicsValuesDTO phyValues, double worldDim_x, double worldDim_y);

        public void reboundInNorth(
                        PhysicsValuesDTO phyValues, double worldDim_x, double worldDim_y);

        public void reboundInSouth(
                        PhysicsValuesDTO phyValues, double worldDim_x, double worldDim_y);

        public void resetAcceleration();

        public void setAngularAcceleration(double angularAcceleration);

        public void setAngularSpeed(double angularSpeed);

        public void setPhysicsValues(PhysicsValuesDTO phyValues);

        public void setThrust(double thrust);

        public void stopPushing();
}
