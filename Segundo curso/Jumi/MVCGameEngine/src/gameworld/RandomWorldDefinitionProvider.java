package gameworld;

import engine.assets.ports.AssetType;
import engine.model.bodies.ports.BodyType;
import engine.utils.helpers.DoubleVector;
import engine.world.core.AbstractWorldDefinitionProvider;

public final class RandomWorldDefinitionProvider extends AbstractWorldDefinitionProvider {

	// *** CONSTRUCTORS ***

	public RandomWorldDefinitionProvider(DoubleVector worldDimension, ProjectAssets assets) {
		super(worldDimension, assets);
	}

	// *** PROTECTED (alphabetical order) ***

	@Override
	protected void define() {
		double density = 100d;

		// region Background
		this.setBackgroundStatic("back_12");
		// endregion

		// region Decoration
		this.addDecoratorAnywhereRandomAsset(10, AssetType.STARS, density, 200, 400);
		this.addDecorator("cosmic_portal_01", 300, 1100, 400);

		this.addDecorator("stardust_01", 300, 1300, 800, -20, 1);
		this.addDecorator("stars_07", 8000, 8000, 2000, 0, 1);
		this.addDecoratorAnywhereRandomAsset(5, AssetType.GALAXY, density, 100, 300);
		this.addDecoratorAnywhereRandomAsset(10, AssetType.GALAXY, density, 50, 200);
		this.addDecoratorAnywhereRandomAsset(10, AssetType.HALO, density, 50, 200);
		// endregion

		// region Gravity bodies => Static bodies
		this.addGravityBody("planet_04", 4500, 4500, 1000);
		this.addGravityBody("sun_02", 32000, 2000, 2000);
		this.addGravityBody("moon_05", 20000, 20000, 1200);
		this.addGravityBody("lab_01", 12000, 24000, 400);
		this.addGravityBody("black_hole_02", 18000, 9000, 300);
		this.addGravityBody("black_hole_01", 32000, 30000, 600);

		this.addGravityBodyAnywhereRandomAsset(5, AssetType.PLANET, density, 50, 300);
		this.addGravityBodyAnywhereRandomAsset(5, AssetType.MOON, density, 100, 500);
		this.addGravityBodyAnywhereRandomAsset(10, AssetType.MINE, density, 50, 100);
		// endregion

		// region Dynamic bodies
		this.addAsteroidPrototypeAnywhereRandomAsset(
				6, AssetType.ASTEROID,
				10, 25,
				10, 750,
				0, 150);
		// endregion

		// region Players
		this.addSpaceshipRandomAsset(1, AssetType.SPACESHIP, density, 50, 55, 19000, 19500);
		this.addTrailEmitterCosmetic("stars_06", 150, BodyType.DECORATOR, 25);
		// endregion

		// region Weapons (addWeapon***)
		this.addWeaponPresetBulletRandomAsset(AssetType.BULLET);

		this.addWeaponPresetBurstRandomAsset(AssetType.BULLET);

		this.addWeaponPresetMineLauncherRandomAsset(AssetType.MINE);

		this.addWeaponPresetMissileLauncherRandomAsset(AssetType.MISSILE);
		// endregion
	}
}
