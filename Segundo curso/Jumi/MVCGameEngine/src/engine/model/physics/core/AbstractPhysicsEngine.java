package engine.model.physics.core;

import static java.lang.System.nanoTime;

import java.util.concurrent.atomic.AtomicReference;

import engine.model.physics.ports.PhysicsEngine;
import engine.model.physics.ports.PhysicsValuesDTO;

public abstract class AbstractPhysicsEngine implements PhysicsEngine {

        private final AtomicReference<PhysicsValuesDTO> phyValues; // Current values (DTO#1)
        protected PhysicsValuesDTO nextPhyValues; // Next frame values (DTO#2)
        protected PhysicsValuesDTO snapshotDTO; // Snapshot for rendering (DTO#3)

        // region Constructors
        public AbstractPhysicsEngine(PhysicsValuesDTO dto1, PhysicsValuesDTO dto2, PhysicsValuesDTO dto3) {
                if (dto1 == null || dto2 == null || dto3 == null) {
                        throw new IllegalArgumentException("PhysicsValuesDTO cannot be null");
                }

                this.phyValues = new AtomicReference<>(dto1);
                this.nextPhyValues = dto2;
                this.snapshotDTO = dto3;
        }

        public AbstractPhysicsEngine(double size, double posX, double posY, double angle) {
                this.phyValues = new AtomicReference<>(
                                new PhysicsValuesDTO(nanoTime(), size, posX, posY, angle));
                this.nextPhyValues = new PhysicsValuesDTO(nanoTime(), size, posX, posY, angle);
                this.snapshotDTO = new PhysicsValuesDTO(nanoTime(), size, posX, posY, angle);
        }
        // endregion

        // *** PUBLIC ***

        public abstract PhysicsValuesDTO calcNewPhysicsValues();

        public abstract void angularAccelerationInc(double angularAcc);

        public final PhysicsValuesDTO getPhysicsValues() {
                return this.phyValues.get();
        }

        // region Rebound (reboundIn***)
        public abstract void reboundInEast(
                        PhysicsValuesDTO phyValues, double worldDim_x, double worldDim_y);

        public abstract void reboundInWest(
                        PhysicsValuesDTO phyValues, double worldDim_x, double worldDim_y);

        public abstract void reboundInNorth(
                        PhysicsValuesDTO phyValues, double worldDim_x, double worldDim_y);

        public abstract void reboundInSouth(
                        PhysicsValuesDTO phyValues, double worldDim_x, double worldDim_y);
        // endregion

        public void resetAcceleration() {
                PhysicsValuesDTO old = this.getPhysicsValues();
                
                // Update nextPhyValues instead of creating new DTO
                nextPhyValues.update(
                                old.timeStamp,
                                old.posX, old.posY, old.angle,
                                old.size,
                                old.speedX, old.speedY,
                                0, 0,
                                old.angularSpeed,
                                old.angularAcc,
                                old.thrust);
                
                this.setPhysicsValues(nextPhyValues);
        }

        // region Setters (set***)
        public final void setAngularAcceleration(double angularAcc) {
                PhysicsValuesDTO old = this.getPhysicsValues();
                
                // Update nextPhyValues instead of creating new DTO
                nextPhyValues.update(
                                old.timeStamp,
                                old.posX, old.posY, old.angle,
                                old.size,
                                old.speedX, old.speedY,
                                old.accX, old.accY,
                                old.angularSpeed,
                                angularAcc,
                                old.thrust);
                
                this.setPhysicsValues(nextPhyValues);
        }

        public abstract void setAngularSpeed(double angularSpeed);

        public final void setPhysicsValues(PhysicsValuesDTO phyValues) {
                if (phyValues == null) {
                        throw new IllegalArgumentException("PhysicsValuesDTO cannot be null");
                }

                // Doble buffer swap: phyValues becomes nextPhyValues
                this.nextPhyValues = this.phyValues.getAndSet(phyValues);
        }
        
        public final PhysicsValuesDTO getNextPhyValues() {
                return this.nextPhyValues;
        }
        
        public final PhysicsValuesDTO getSnapshotDTO() {
                return this.snapshotDTO;
        }

        public final void setThrust(double thrust) {
                PhysicsValuesDTO old = this.getPhysicsValues();
                
                // Update nextPhyValues instead of creating new DTO
                nextPhyValues.update(
                                old.timeStamp,
                                old.posX, old.posY, old.angle,
                                old.size,
                                old.speedX, old.speedY,
                                old.accX, old.accY,
                                old.angularSpeed,
                                old.angularAcc,
                                thrust);
                
                this.setPhysicsValues(nextPhyValues);
        }
        // endregion

        @Override
        public void stopPushing() {
                PhysicsValuesDTO old = this.getPhysicsValues();
                
                // Update nextPhyValues instead of creating new DTO
                nextPhyValues.update(
                                old.timeStamp,
                                old.posX, old.posY, old.angle,
                                old.size,
                                old.speedX, old.speedY,
                                0, 0, // Reset accelerations
                                old.angularSpeed,
                                old.angularAcc,
                                0.0d); // Reset thrust
                
                this.setPhysicsValues(nextPhyValues);
        }
}
