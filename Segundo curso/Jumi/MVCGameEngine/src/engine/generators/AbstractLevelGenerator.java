package engine.generators;

import java.util.ArrayList;
import java.util.Random;

import engine.controller.ports.WorldManager;
import engine.world.ports.DefEmitterDTO;
import engine.world.ports.DefItem;
import engine.world.ports.DefItemDTO;
import engine.world.ports.DefWeaponDTO;
import engine.world.ports.WorldDefinition;

public abstract class AbstractLevelGenerator {

    // region Fields
    private final Random rnd = new Random();
    private final DefItemMaterializer defItemMaterializer = new DefItemMaterializer();
    private final WorldManager worldManager;
    private final WorldDefinition worldDefinition;
    // endregion

    // region Constructors
    protected AbstractLevelGenerator(WorldManager worldManager, WorldDefinition worldDef) {
        if (worldManager == null) {
            throw new IllegalArgumentException("WorldInitializer cannot be null.");
        }
        if (worldDef == null) {
            throw new IllegalArgumentException("WorldDefinition cannot be null.");
        }

        this.worldManager = worldManager;
        this.worldDefinition = worldDef;

        this.createWorld();
    }
    // endregion

    // *** PROTECTED ABSTRACT ***

    // region creators (create***)
    protected abstract void createStatics();

    protected abstract void createDecorators();

    protected abstract void createPlayers();

    protected abstract void createDynamics();
    // endregion

    // *** PROTECTED ***

    // region adders (add***)
    protected void addDecoratorIntoTheGame(DefItemDTO deco) {
        this.worldManager.addDecorator(deco.assetId, deco.size, deco.posX, deco.posY, deco.angle);
    }

        protected void addDynamicIntoTheGame(DefItemDTO bodyDef) {
        this.worldManager.addDynamicBody(
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

        String playerId = this.worldManager.addPlayer(
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

        this.worldManager.setLocalPlayer(playerId);
        return playerId;
    }

    protected void addStaticIntoTheGame(DefItemDTO bodyDef) {
        this.worldManager.addStaticBody(
                bodyDef.assetId, bodyDef.size,
                bodyDef.posX, bodyDef.posY,
                bodyDef.angle);
    }
    // endregion

    // region equippers (equip***)
    protected void equipEmitters(String entityId, ArrayList<DefEmitterDTO> emitterDefs) {
        for (DefEmitterDTO emitterDef : emitterDefs) {
            this.worldManager.equipTrail(
                    entityId, emitterDef);
        }
    }

    protected void equipWeapons(String entityId, ArrayList<DefEmitterDTO> weaponDefs) {
        for (DefEmitterDTO weaponDef : weaponDefs) {
            this.worldManager.equipWeapon(
                    entityId, weaponDef, 0);
        }
    }
    // endregion

    protected final DefItemDTO defItemToDTO(DefItem defitem) {
        return this.defItemMaterializer.defItemToDTO(defitem);
    }

    // region getters (get***)
    protected WorldDefinition getWorldDefinition() {
        return this.worldDefinition;
    }

    protected WorldManager getWorldManager() {
        return this.worldManager;
    }
    // endregion

    protected final double randomDoubleBetween(double minInclusive, double maxInclusive) {
        if (maxInclusive < minInclusive) {
            throw new IllegalArgumentException("maxInclusive must be >= minInclusive");
        }
        if (maxInclusive == minInclusive) {
            return minInclusive;
        }
        return minInclusive + (this.rnd.nextDouble() * (maxInclusive - minInclusive));
    }

    // *** PRIVATE ***

    // Standard world creation pipeline.
    private final void createWorld() {
        this.worldManager.loadAssets(this.worldDefinition.gameAssets);

        this.createDecorators();
        this.createStatics();
        this.createPlayers();
        this.createDynamics();
    }
}
