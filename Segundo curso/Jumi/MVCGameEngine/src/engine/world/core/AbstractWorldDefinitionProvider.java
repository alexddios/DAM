package engine.world.core;

import java.util.ArrayList;
import java.util.Random;

import engine.assets.core.AssetCatalog;
import engine.assets.ports.AssetType;
import engine.model.bodies.ports.BodyType;
import engine.utils.helpers.DoubleVector;
import engine.world.ports.DefBackgroundDTO;
import engine.world.ports.DefEmitterDTO;
import engine.world.ports.DefItem;
import engine.world.ports.DefItemDTO;
import engine.world.ports.DefItemPrototypeDTO;
import engine.world.ports.WorldDefinition;
import engine.world.ports.WorldDefinitionProvider;
import gameworld.ProjectAssets;

public abstract class AbstractWorldDefinitionProvider implements WorldDefinitionProvider {

    // region Constants
    private static final String ASSET_PATH = "src/resources/images/";
    private static final double WORLD_MIN = 0.0;
    private static final double ANY_HEADING_MIN_DEG = 0.0;
    private static final double ANY_HEADING_MAX_DEG = 359.999;
    private static final double DEFAULT_DENSITY = 100;

    private static final double NO_ANGULAR_ACCEL = 0.0;
    private static final double NO_ANGULAR_SPEED = 0.0;
    private static final double NO_THRUST = 0.0;
    private static final double NO_THRUST_DURATION = 0.0;
    private static final double DEFAULT_TRAIL_MASS = 10.0;
    private static final double TRAIL_LIFETIME = 1.5;
    private static final double NO_SPEED = 0.0;
    private static final boolean NOT_ADD_EMITTER_SPEED = true; 
    private static final double NO_BURST_RATE = 0.0;
    private static final int ZERO_BURST_SIZE = 0;
    private static final double NO_RELOAD_TIME = 0.0;
    private static final boolean UNLIMITED_BODIES = true;
    private static final int ZERO_MAX_BODIES = 0;
    // endregion

    // region Fields
    private static final Random rnd = new Random();
    private final ProjectAssets projectAssets;
    public final double worldWidth;
    public final double worldHeight;
    public final AssetCatalog gameAssets = new AssetCatalog(ASSET_PATH);
    private final WorldAssetsRegister assetsRegister;

    public DefBackgroundDTO background;

    public final ArrayList<DefItem> decorators = new ArrayList<>();
    public final ArrayList<DefItem> gravityBodies = new ArrayList<>();
    public final ArrayList<DefItem> asteroids = new ArrayList<>();
    public final ArrayList<DefItem> spaceships = new ArrayList<>();
    public final ArrayList<DefEmitterDTO> trailEmitters = new ArrayList<>();
    public final ArrayList<DefEmitterDTO> weapons = new ArrayList<>();
    // endregion

    // *** CONSTRUCTORS ***

    public AbstractWorldDefinitionProvider(DoubleVector worldDimension, ProjectAssets assets) {
        if (assets == null) {
            throw new IllegalArgumentException("ProjectAssets cannot be null.");
        }
        if (worldDimension == null) {
            throw new IllegalArgumentException("WorldDimension cannot be null.");
        }   
        if (worldDimension.x <= 0 || worldDimension.y <= 0) {
            throw new IllegalArgumentException("World dimensions must be positive values.");
        }

        this.worldWidth = worldDimension.x;
        this.worldHeight = worldDimension.y;
        this.projectAssets = assets;
        this.assetsRegister = new WorldAssetsRegister(this.projectAssets, this.gameAssets);
    }

    // *** PROTECTED ***

    // region Asteroid adders (addAsteroid ***)
    protected final void addAsteroidRandomAsset(
            int num, AssetType assetType,
            double angle, double density, double size, double posX, double posY) {

        this.addRandomAsset(this.asteroids, num, assetType, angle, density, size, posX, posY);
    }

    protected final void addAsteroidAnywhereRandomAsset(
            int num, AssetType assetType, double density, double minSize, double maxSize) {

        requirePositiveInt(num, "num must be a positive integer.");

        for (int i = 0; i < num; i++) {
            double angle = randomDouble(ANY_HEADING_MIN_DEG, ANY_HEADING_MAX_DEG);
            double posX = randomDouble(WORLD_MIN, this.worldWidth);
            double posY = randomDouble(WORLD_MIN, this.worldHeight);
            double size = randomDouble(minSize, maxSize);

            this.addAsteroidRandomAsset(
                    1, assetType, angle, density, size, posX, posY);
        }
    }

    protected final void addAsteroidPrototypeRandomAsset(
            int num, AssetType assetType, double density,
            double minAngle, double maxAngle,
            double minSize, double maxSize,
            double posMinX, double posMaxX,
            double posMinY, double posMaxY,
            double speedMin, double speedMax,
            double angularSpeedMin, double angularSpeedMax) {

        this.addRandomPrototypeRandomAsset(this.asteroids, num, assetType, density,
                minAngle, maxAngle, minSize, maxSize,
                posMinX, posMaxX, posMinY, posMaxY,
                speedMin, speedMax,
                0, 0,
                angularSpeedMin, angularSpeedMax);
    }

    protected final void addAsteroidPrototypeAnywhereRandomAsset(
            int num, AssetType assetType, double minSize, double maxSize,
            double speedMin, double speedMax,
            double angularSpeedMin, double angularSpeedMax) {

        this.addAsteroidPrototypeRandomAsset(
                num, assetType, DEFAULT_DENSITY,
                ANY_HEADING_MIN_DEG, ANY_HEADING_MAX_DEG,
                minSize, maxSize,
                WORLD_MIN, this.worldWidth,
                WORLD_MIN, this.worldHeight,
                speedMin, speedMax,
                angularSpeedMin, angularSpeedMax);
    }
    // endregion

    // region Decorator adders (addDecorator ***)
    protected final void addDecorator(
            String assetId, double posX, double posY,
            double size, double angle, double density) {

        requireNotNull(assetId, "assetId cannot be null");
        this.assetsRegister.registerAssetId(assetId);
        this.decorators.add(new DefItemDTO(
                assetId, size, angle, posX, posY, density));
    }

    protected final void addDecorator(
            String assetId, double posX, double posY,
            double size) {

        addDecorator(assetId, posX, posY, size, randomAngle(), DEFAULT_DENSITY);
    }

    protected final void addDecoratorRandomAsset(
            int num, AssetType assetType,
            double angle, double density, double size, double posX, double posY) {

        this.addRandomAsset(this.decorators, num, assetType, angle, density, size, posX, posY);
    }

    protected final void addDecoratorAnywhereRandomAsset(
            int num, AssetType assetType, double density, double minSize, double maxSize) {

        requirePositiveInt(num, "num must be a positive integer.");

        for (int i = 0; i < num; i++) {

            double angle = randomDouble(ANY_HEADING_MIN_DEG, ANY_HEADING_MAX_DEG);
            double posX = randomDouble(WORLD_MIN, this.worldWidth);
            double posY = randomDouble(WORLD_MIN, this.worldHeight);
            double size = randomDouble(minSize, maxSize);

            this.addDecoratorRandomAsset(1, assetType, angle, density, size, posX, posY);
        }
    }

    protected final void addDecoratorPrototypeRandomAsset(
            int num, AssetType assetType, double density,
            double minAngle, double maxAngle,
            double minSize, double maxSize,
            double posMinX, double posMaxX,
            double posMinY, double posMaxY) {

        this.addRandomPrototypeRandomAsset(this.decorators, num, assetType, density,
                minAngle, maxAngle, minSize, maxSize,
                posMinX, posMaxX, posMinY, posMaxY);
    }

    protected final void addDecoratorPrototypeAnywhereRandomAsset(
            int num, AssetType assetType, double density, double minSize, double maxSize) {

        this.addDecoratorPrototypeRandomAsset(
                num, assetType, density,
                ANY_HEADING_MIN_DEG, ANY_HEADING_MAX_DEG,
                minSize, maxSize,
                WORLD_MIN, this.worldWidth,
                WORLD_MIN, this.worldHeight);
    }
    // endregion

    // region GravityBody adders (addGravityBody ***)
    protected final void addGravityBody(
            String assetId, double posX, double posY,
            double size, double angle, double density) {

        requireNotNull(assetId, "assetId cannot be null");
        this.assetsRegister.registerAssetId(assetId);
        this.gravityBodies.add(new DefItemDTO(assetId, size, angle, posX, posY, density));
    }

    protected final void addGravityBody(
            String assetId, double posX, double posY, double size) {

        addGravityBody(
                assetId, posX, posY, size, randomAngle(), DEFAULT_DENSITY);
    }

    protected final void addGravityBodyRandomAsset(
            int num, AssetType assetType,
            double angle, double density, double size, double posX, double posY) {

        this.addRandomAsset(this.gravityBodies, num, assetType, angle, density, size, posX, posY);
    }

    protected final void addGravityBodyAnywhereRandomAsset(
            int num, AssetType assetType, double density, double minSize, double maxSize) {

        requirePositiveInt(num, "num must be a positive integer.");

        for (int i = 0; i < num; i++) {

            double angle = randomDouble(ANY_HEADING_MIN_DEG, ANY_HEADING_MAX_DEG);
            double posX = randomDouble(WORLD_MIN, this.worldWidth);
            double posY = randomDouble(WORLD_MIN, this.worldHeight);
            double size = randomDouble(minSize, maxSize);

            this.addGravityBodyRandomAsset(1, assetType, angle, density, size, posX, posY);
        }
    }

    protected final void addGravityBodyPrototypeRandomAsset(
            int num, AssetType assetType, double density,
            double minAngle, double maxAngle,
            double minSize, double maxSize,
            double posMinX, double posMaxX,
            double posMinY, double posMaxY) {

        this.addRandomPrototypeRandomAsset(this.gravityBodies, num, assetType, density,
                minAngle, maxAngle, minSize, maxSize,
                posMinX, posMaxX, posMinY, posMaxY);
    }

    protected final void addGravityBodyPrototypeAnywhereRandomAsset(
            int num, AssetType assetType, double density, double minSize, double maxSize) {

        this.addGravityBodyPrototypeRandomAsset(
                num, assetType, density,
                ANY_HEADING_MIN_DEG, ANY_HEADING_MAX_DEG,
                minSize, maxSize,
                WORLD_MIN, this.worldWidth,
                WORLD_MIN, this.worldHeight);
    }
    // endregion

    // region Spaceship adders (addSpaceship ***)
    protected final void addSpaceship(
            String assetId, double posX, double posY,
            double size, double angle, double density) {

        requireNotNull(assetId, "assetId cannot be null");
        this.assetsRegister.registerAssetId(assetId);
        this.spaceships.add(new DefItemDTO(assetId, size, angle, posX, posY, density));
    }

    protected final void addSpaceship(
            String assetId, double posX, double posY, double size) {

        addSpaceship(
                assetId, posX, posY, size, randomAngle(), DEFAULT_DENSITY);
    }

    protected final void addSpaceshipRandomAsset(
            int num, AssetType assetType,
            double angle, double density, double size, double posX, double posY) {

        this.addRandomAsset(this.spaceships, num, assetType, angle, density, size, posX, posY);
    }

    protected final void addSpaceshipAnywhereRandomAsset(
            int num, AssetType assetType, double density, double minSize, double maxSize) {

        requirePositiveInt(num, "num must be a positive integer.");

        for (int i = 0; i < num; i++) {
            this.addSpaceshipRandomAsset(
                    1, assetType, randomAngle(), density,
                    randomSize(minSize, maxSize),
                    randomDouble(WORLD_MIN, this.worldWidth), randomDouble(WORLD_MIN, this.worldHeight));
        }
    }

    protected final void addSpaceshipPrototypeRandomAsset(
            int num, AssetType assetType, double density,
            double minAngle, double maxAngle,
            double minSize, double maxSize,
            double posMinX, double posMaxX,
            double posMinY, double posMaxY, double speedMin, double speedMax,
            double thrustMin, double thrustMax,
            double angularSpeedMin, double angularSpeedMax) {

        this.addRandomPrototypeRandomAsset(this.spaceships, num, assetType, density,
                minAngle, maxAngle, minSize, maxSize,
                posMinX, posMaxX, posMinY, posMaxY);
    }

    protected final void addSpaceshipPrototypeAnywhereRandomAsset(
            int num, AssetType assetType, double density, double minSize, double maxSize) {

        this.addSpaceshipPrototypeRandomAsset(
                num, assetType, density,
                ANY_HEADING_MIN_DEG, ANY_HEADING_MAX_DEG,
                minSize, maxSize,
                WORLD_MIN, this.worldWidth,
                WORLD_MIN, this.worldHeight,
                maxSize, maxSize, maxSize, maxSize, maxSize, maxSize);
    }
    // endregion

    // region TrailEmitter adders (addTrailEmitter ***)
    protected final void addTrailEmitter(DefEmitterDTO emitter) {
        requireNotNull(emitter, "emitter cannot be null");

        this.assetsRegister.registerAssetId(emitter.bodyAssetId);
        this.trailEmitters.add(emitter);
    }

    protected final void addTrailEmitterCosmetic(
            String assetId, double spriteSize, BodyType bodyType,
            double emissionRate,
            double offsetHorizontal, double offsetVertical,
            boolean randomizeInitialAngle, boolean randomizeSize) {

        DefEmitterDTO emitter = cosmeticTrailEmitter(
                assetId, spriteSize, bodyType,
                emissionRate,
                offsetHorizontal, offsetVertical,
                randomizeInitialAngle, randomizeSize);

        this.addTrailEmitter(emitter); // also registers assetId (as per your latest AbstractWorldDefinitionProvider)
    }

    protected final void addTrailEmitterCosmetic(
            String assetId, double spriteSize,
            BodyType bodyType, double emissionRate) {

        this.addTrailEmitterCosmetic(
                assetId, spriteSize, bodyType, emissionRate,
                0.0,
                0.0,
                true,
                false);
    }
    // endregion

    // region Weapon adders (addWeapon ***)
    protected final void addWeapon(DefEmitterDTO weapon) {
        // Add every weapon type into its respective list
        this.assetsRegister.registerAssetId(weapon.bodyAssetId);
        this.weapons.add(weapon);
    }

    protected final void addWeaponPresetBulletRandomAsset(AssetType assetType) {
        String assetId = this.assetsRegister.pickRandomAssetId(assetType);
        this.addWeapon(WeaponDefFactory.createPresetedBulletBasic(assetId));
    }

    protected final void addWeaponPresetBurstRandomAsset(AssetType assetType) {
        String assetId = this.assetsRegister.pickRandomAssetId(assetType);
        this.addWeapon(WeaponDefFactory.createPresetedBurst(assetId));
    }

    protected final void addWeaponPresetMineLauncherRandomAsset(AssetType assetType) {
        String assetId = this.assetsRegister.pickRandomAssetId(assetType);
        this.addWeapon(WeaponDefFactory.createPresetedMineLauncher(assetId));
    }

    protected final void addWeaponPresetMissileLauncherRandomAsset(AssetType assetType) {
        String assetId = this.assetsRegister.pickRandomAssetId(assetType);
        this.addWeapon(WeaponDefFactory.createPresetedMissileLauncher(assetId));
    }
    // endregion

    protected abstract void define();

    // region setBackground (setBackground***)
    protected final void setBackground(DefBackgroundDTO background) {
        if (background == null)
            throw new IllegalArgumentException("background cannot be null");

        this.assetsRegister.registerAssetId(background.assetId);

        this.background = background;
    }

    protected final void setBackgroundStatic(String assetId) {
        // Uses a static background: No scrolling, No tiling, No offsets
        this.setBackground(new DefBackgroundDTO(assetId, WORLD_MIN, WORLD_MIN));
    }
    // endregion

    // *** PRIVATES ***

    // region private adders (private addRandom***)
    private final void addRandomAsset(
            ArrayList<DefItem> defItems, int num, AssetType assetType,
            double angle, double density, double size, double posX, double posY) {

        requireNotNull(defItems, "defItems cannot be null.");
        requirePositiveInt(num, "num must be a positive integer.");
        requireNotNull(assetType, "assetType cannot be null.");
        requireAngle(angle, "angle must be in range [0, 360).");
        requirePositive(density, "density must be a positive value.");
        requirePositive(size, "size must be a positive value.");
        requireNonNegative(posX, "posX cannot be negative.");
        requireNonNegative(posY, "posY cannot be negative.");

        String randomId;
        for (int i = 0; i < num; i++) {
            randomId = this.assetsRegister.pickAndRegisterRandomAssetId(assetType);

            defItems.add(new DefItemDTO(randomId, size, angle, posX, posY, density));
        }
    }

    private final void addRandomPrototypeRandomAsset(
            ArrayList<DefItem> defItems, int num,
            AssetType assetType, double density,
            double minAngle, double maxAngle,
            double minSize, double maxSize,
            double posMinX, double posMaxX,
            double posMinY, double posMaxY,
            double speedMin, double speedMax,
            double thrustMin, double thrustMax,
            double angularSpeedMin, double angularSpeedMax) {

        requireNotNull(defItems, "defItems cannot be null.");
        requirePositiveInt(num, "num must be a positive integer.");
        requireNotNull(assetType, "assetType cannot be null.");
        requirePositive(density, "density must be a positive value.");
        requireAngleRange(minAngle, maxAngle,
                "minAngle and maxAngle must be in range [0, 360), and minAngle <= maxAngle.");
        requireRangePositive(minSize, maxSize,
                "minSize and maxSize must be positive values, and minSize <= maxSize.");
        requireRangeNonNegative(posMinX, posMaxX,
                "posMinX and posMaxX cannot be negative, and posMinX <= posMaxX.");
        requireRangeNonNegative(posMinY, posMaxY,
                "posMinY and posMaxY cannot be negative, and posMinY <= posMaxY.");

        String randomAssetId;
        for (int i = 0; i < num; i++) {
            randomAssetId = this.assetsRegister.pickAndRegisterRandomAssetId(assetType);

            defItems.add(new DefItemPrototypeDTO(
                    randomAssetId, density, minAngle, maxAngle, minSize, maxSize,
                    posMinX, posMaxX, posMinY, posMaxY, speedMin, speedMax,
                    thrustMin, thrustMax, angularSpeedMin, angularSpeedMax));
        }
    }

    private final void addRandomPrototypeRandomAsset(
            ArrayList<DefItem> defItems, int num,
            AssetType assetType, double density,
            double minAngle, double maxAngle,
            double minSize, double maxSize,
            double posMinX, double posMaxX,
            double posMinY, double posMaxY) {

        this.addRandomPrototypeRandomAsset(
                defItems, num,
                assetType, density,
                minAngle, maxAngle,
                minSize, maxSize,
                posMinX, posMaxX,
                posMinY, posMaxY,
                0.0, 0.0,
                0.0, 0.0,
                0.0, 0.0);

    }
    // endregion

    protected final DefEmitterDTO cosmeticTrailEmitter(
            String assetId,
            double spriteSize,
            BodyType bodyType,
            double emissionRate,
            double offsetHorizontal,
            double offsetVertical,
            boolean randomizeInitialAngle,
            boolean randomizeSize) {

        return new DefEmitterDTO(
                assetId, 
                NO_ANGULAR_ACCEL, 
                NO_ANGULAR_SPEED,
                NOT_ADD_EMITTER_SPEED, 
                NO_THRUST,
                NO_THRUST_DURATION, 
                DEFAULT_TRAIL_MASS,
                TRAIL_LIFETIME, 
                spriteSize, 
                NO_SPEED, 
                bodyType,
                NO_BURST_RATE, 
                ZERO_BURST_SIZE, 
                emissionRate,
                offsetHorizontal, 
                offsetVertical, 
                NO_RELOAD_TIME,
                UNLIMITED_BODIES,
                ZERO_MAX_BODIES, 
                randomizeInitialAngle, 
                randomizeSize);
    }

    private final void reset() {
        // Resets asset catalog to avoid cross-world leakage between provide() calls
        this.gameAssets.reset();

        this.background = null;
        this.decorators.clear();
        this.gravityBodies.clear();
        this.asteroids.clear();
        this.spaceships.clear();
        this.trailEmitters.clear();
        this.weapons.clear();
    }

    private final void validateDefinition() {
        // Root cause first: no entities => no world
        if (this.decorators.isEmpty() &&
                this.gravityBodies.isEmpty() &&
                this.asteroids.isEmpty() &&
                this.spaceships.isEmpty()) {

            throw new IllegalStateException(
                    "WorldDefinition must define at least one item " +
                            "(decorators/gravityBodies/asteroids/spaceships).");
        }

        // Background is mandatory: no background => no rendering
        if (this.background == null) {
            throw new IllegalStateException(
                    "WorldDefinition requires a background. Renderer assumes a background is always present.");
        }

        // Assets are mandatory: no assets => no way to render anything
        if (this.gameAssets.isEmpty()) {
            throw new IllegalStateException(
                    "WorldDefinition must register at least one asset in the AssetCatalog.");
        }
    }

    // *** INTERFACE IMPLEMENTATIONS ***

    // region WorldDefinitionProvider
    @Override
    public final WorldDefinition provide() {
        this.reset();
        this.define();
        this.validateDefinition();

        return new WorldDefinition(
                this.worldWidth, this.worldHeight, this.gameAssets, this.background,
                new ArrayList<>(this.decorators),
                new ArrayList<>(this.gravityBodies),
                new ArrayList<>(this.asteroids),
                new ArrayList<>(this.spaceships),
                new ArrayList<>(this.trailEmitters),
                new ArrayList<>(this.weapons));
    }
    // endregion

    // *** STATICS ***

    // region Random helpers (random***)
    protected static double randomAngle() {
        return randomDouble(ANY_HEADING_MIN_DEG, ANY_HEADING_MAX_DEG);
    }

    protected static double randomDouble(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("Invalid range: [" + min + "," + max + "]");
        }
        if (min == max) {
            return min;
        }

        return rnd.nextDouble(min, max);
    }

    protected static double randomSize(double minSize, double maxSize) {
        // Mantengo tu patrón: en el mundo usas doubles. Si quieres ints, cámbialo aquí.
        if (minSize <= 0 || maxSize <= 0 || minSize > maxSize) {
            throw new IllegalArgumentException("Invalid size range: [" + minSize + "," + maxSize + "]");
        }
        if (minSize == maxSize)
            return minSize;

        return rnd.nextDouble(minSize, maxSize);
    }
    // endregion

    // region Validation helpers (require***)
    private static void requireNotNull(Object v, String msg) {
        if (v == null)
            throw new IllegalArgumentException(msg);
    }

    private static void requirePositive(double v, String msg) {
        if (v <= 0)
            throw new IllegalArgumentException(msg);
    }

    private static void requireNonNegative(double v, String msg) {
        if (v < 0)
            throw new IllegalArgumentException(msg);
    }

    private static void requirePositiveInt(int v, String msg) {
        if (v <= 0)
            throw new IllegalArgumentException(msg);
    }

    private static void requireAngle(double angle, String msg) {
        if (angle < 0 || angle >= 360)
            throw new IllegalArgumentException(msg);
    }

    private static void requireAngleRange(double minAngle, double maxAngle, String msg) {
        requireAngle(minAngle, msg);
        requireAngle(maxAngle, msg);
        if (minAngle > maxAngle)
            throw new IllegalArgumentException(msg);
    }

    private static void requireRangePositive(double min, double max, String msg) {
        if (min <= 0 || max <= 0 || min > max)
            throw new IllegalArgumentException(msg);
    }

    private static void requireRangeNonNegative(double min, double max, String msg) {
        if (min < 0 || max < 0 || min > max)
            throw new IllegalArgumentException(msg);
    }
    // endregion
}
