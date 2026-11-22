package engine.generators;

import java.util.ArrayList;
import java.util.Random;

import engine.controller.ports.EngineState;
import engine.controller.ports.WorldManager;
import engine.utils.helpers.DoubleVector;
import engine.world.ports.DefEmitterDTO;
import engine.world.ports.DefItem;
import engine.world.ports.DefItemDTO;
import engine.world.ports.DefWeaponDTO;
import engine.world.ports.WorldDefinition;

public abstract class AbstractIAGenerator implements Runnable {

    // region Fields
    private final Random rnd = new Random();
    private final DefItemMaterializer defItemMaterializer;
    protected final WorldManager worldEvolver;
    protected final WorldDefinition worldDefinition;
    protected final int maxCreationDelay;
    private Thread thread;
    // endregion

    // region Constructors
    protected AbstractIAGenerator(
            WorldManager worldEvolver,
            WorldDefinition worldDefinition,
            int maxCreationDelay) {

        if (worldEvolver == null) {
            throw new IllegalArgumentException("WorldEvolver cannot be null.");
        }
        if (worldDefinition == null) {
            throw new IllegalArgumentException("WorldDefinition cannot be null.");
        }

        this.defItemMaterializer = new DefItemMaterializer();
        this.worldEvolver = worldEvolver;
        this.worldDefinition = worldDefinition;
        this.maxCreationDelay = maxCreationDelay;
    }
    // endregion

    // *** PUBLIC ***

    public void activate() {
        this.thread = new Thread(this);
        this.thread.setName(this.getThreadName());
        this.thread.setPriority(Thread.MIN_PRIORITY);
        this.thread.start();

        this.onActivate();

        System.out.println(this.getThreadName() + " activated!");
    }

    // *** PROTECTED (alphabetical order) ***

    // region adders (add***)
    protected void addDynamicIntoTheGame(DefItemDTO bodyDef) {
        this.worldEvolver.addDynamicBody(
                bodyDef.assetId, bodyDef.size,
                bodyDef.posX, bodyDef.posY,
                bodyDef.speedX, bodyDef.speedY,
                0, 0,
                bodyDef.angle,
                bodyDef.angularSpeed,
                0d,
                bodyDef.thrust);
    }

    protected String addLocalPlayerIntoTheGame(
            DefItemDTO bodyDef, ArrayList<DefEmitterDTO> weaponDefs,
            ArrayList<DefEmitterDTO> trailDefs) {

        String playerId = this.worldEvolver.addPlayer(
                bodyDef.assetId, bodyDef.size,
                bodyDef.posX, bodyDef.posY,
                bodyDef.speedX, bodyDef.speedY,
                0, 0,
                bodyDef.angle, bodyDef.angularSpeed,
                0,
                bodyDef.thrust);

        if (playerId == null) {
            throw new IllegalStateException("Failed to create local player.");
        }

        this.equipEmitters(playerId, trailDefs);
        this.equipWeapons(playerId, weaponDefs);

        this.worldEvolver.setLocalPlayer(playerId);
        return playerId;
    }
    // endregion

    // region equippers (equip***)
    protected void equipEmitters(String entityId, ArrayList<DefEmitterDTO> emitterDefs) {
        for (DefEmitterDTO emitterDef : emitterDefs) {
            this.worldEvolver.equipTrail(
                    entityId, emitterDef);
        }
    }

    protected void equipWeapons(String entityId, ArrayList<DefEmitterDTO> emitterDefs) {
        for (DefEmitterDTO weaponDef : emitterDefs) {
            this.worldEvolver.equipWeapon(
                    entityId, weaponDef, 0);
        }
    }
    // endregion

    // region getters (get***)
    protected String getThreadName() {
        return this.getClass().getSimpleName();
    }
    // endregion

    // Optional hook for subclasses (e.g., create players).
    protected void onActivate() {
        // no-op by default
    }

    // Override to implement logic for ALIVE tick
    protected abstract void onTick();

    protected final DoubleVector centerPosition() {
        DoubleVector worldDim = this.worldEvolver.getWorldDimension();
        double x = worldDim.x / 2.0;
        double y = worldDim.y / 2.0;
        return new DoubleVector(x, y);
    }

    protected final DefItemDTO defItemToDTO(DefItem defItem) {
        return this.defItemMaterializer.defItemToDTO(defItem);
    }

    // region Random helpers (random***)
    // protected DoubleVector randomAcceleration() {
    // return new DoubleVector(
    // this.rnd.nextGaussian(),
    // this.rnd.nextGaussian(),
    // this.rnd.nextFloat() * this.AIConfig.maxAccModule);
    // }

    protected double randomAngularSpeed(double maxAngularSpeed) {
        return this.rnd.nextFloat() * maxAngularSpeed - maxAngularSpeed / 2;
    }

    protected final double randomDoubleBetween(double minInclusive, double maxInclusive) {
        if (maxInclusive < minInclusive) {
            throw new IllegalArgumentException("maxInclusive must be >= minInclusive");
        }
        if (maxInclusive == minInclusive) {
            return minInclusive;
        }
        return minInclusive + (this.rnd.nextDouble() * (maxInclusive - minInclusive));
    }

    // protected DoubleVector randomSpeed() {
    // return new DoubleVector(
    // this.rnd.nextGaussian(),
    // this.rnd.nextGaussian(),
    // this.rnd.nextFloat() * this.AIConfig.maxSpeedModule);
    // }
    // endregion

    // *** INTERFACE IMPLEMENTATION ***

    // region Runnable
    @Override
    public final void run() {
        while (this.worldEvolver.getEngineState() != EngineState.STOPPED) {

            if (this.worldEvolver.getEngineState() == EngineState.ALIVE) {
                this.onTick();
            }

            try {
                Thread.sleep(this.rnd.nextInt(this.maxCreationDelay));
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    // endregion

}
