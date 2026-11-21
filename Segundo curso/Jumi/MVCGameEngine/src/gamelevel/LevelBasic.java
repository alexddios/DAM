package gamelevel;

import java.util.ArrayList;

import engine.controller.ports.WorldManager;
import engine.generators.AbstractLevelGenerator;
import engine.world.ports.DefEmitterDTO;
import engine.world.ports.DefItem;
import engine.world.ports.DefItemDTO;
import engine.world.ports.DefWeaponDTO;
import engine.world.ports.WorldDefinition;

public class LevelBasic extends AbstractLevelGenerator {

    // *** CONSTRUCTORS ***

    public LevelBasic(WorldManager worldManager, WorldDefinition worldDef) {
        super(worldManager, worldDef);
    }

    // *** PROTECTED (alphabetic order) ***

    @Override
    protected void createDecorators() {
        ArrayList<DefItem> decorators = this.getWorldDefinition().spaceDecorators;

        for (DefItem def : decorators) {
            DefItemDTO deco = this.defItemToDTO(def);
            this.addDecoratorIntoTheGame(deco);
        }
    }

    @Override
    protected void createDynamics() {
    }

    @Override
    protected void createStatics() {
        ArrayList<DefItem> bodyDefs = this.getWorldDefinition().gravityBodies;

        for (DefItem def : bodyDefs) {
            DefItemDTO body = this.defItemToDTO(def);
            this.addStaticIntoTheGame(body);
        }
    }

    @Override
    protected void createPlayers() {
        WorldDefinition worldDef = this.getWorldDefinition();
        ArrayList<DefItem> shipDefs = worldDef.spaceships;
        ArrayList<DefEmitterDTO> weaponDefs = worldDef.weapons;
        ArrayList<DefEmitterDTO> trailDefs = worldDef.trailEmitters;

        for (DefItem def : shipDefs) {
            DefItemDTO body = this.defItemToDTO(def);

            this.addLocalPlayerIntoTheGame(body, weaponDefs, trailDefs);
        }
    }
}
