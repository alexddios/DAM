package gameai;

import java.util.ArrayList;
import java.util.Random;

import engine.controller.ports.WorldManager;
import engine.generators.AbstractIAGenerator;
import engine.world.ports.DefItem;
import engine.world.ports.DefItemDTO;
import engine.world.ports.WorldDefinition;

public class AIBasicSpawner extends AbstractIAGenerator {

    // region Fields
    private final ArrayList<DefItem> asteroidDefs;
    private final Random rnd = new Random();
    // endregion

    // *** CONSTRUCTORS ***

    public AIBasicSpawner(
            WorldManager worldEvolver, WorldDefinition worldDefinition,
            int maxCreationDelay) {

        super(worldEvolver, worldDefinition, maxCreationDelay);

        this.asteroidDefs = this.worldDefinition.asteroids;
    }

    // *** PROTECTED (alphabetical order) ***

    @Override
    protected String getThreadName() {
        return "AIBasicSpawner";
    }

    @Override
    protected void onActivate() {
        // At this place you can initialize any resource
        // needed for your AI spawner
        // ... or do nothing.
    }

    @Override
    protected void onTick() {
        // Select a random asteroid definition
        DefItem defItem = this.asteroidDefs.get(
                this.rnd.nextInt(this.asteroidDefs.size()));

        this.addDynamic(defItem);
    }

    // *** PRIVATE (alphabetic order) ***

    private void addDynamic(DefItem defItem) {
        // If defItem is a prototype, we need to convert it to a DTO
        // to resolve range-based properties ...
        DefItemDTO bodyDef = this.defItemToDTO(defItem);

        // At this place you can modify position,
        // speed, thrust, etc. as needed
        // or you can accept definition values
        // as they came form world definition.
        // ... or do nothing.

        // Injecting dynamic body into the game
        this.addDynamicIntoTheGame(bodyDef);
    }
}
