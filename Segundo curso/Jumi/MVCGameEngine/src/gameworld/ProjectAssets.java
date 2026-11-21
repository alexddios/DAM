package gameworld;

import engine.assets.core.AssetCatalog;
import engine.assets.ports.AssetIntensity;
import engine.assets.ports.AssetType;

public final class ProjectAssets {

    public final AssetCatalog catalog;

    public ProjectAssets() {
        this.catalog = new AssetCatalog("src/resources/images/");

        // region asteroids (asteroid-***)
        this.catalog.register("asteroid_01", "asteroid-01-mini.png", AssetType.ASTEROID, AssetIntensity.HIGH);
        this.catalog.register("asteroid_02", "asteroid-02-mini.png", AssetType.ASTEROID, AssetIntensity.HIGH);
        this.catalog.register("asteroid_03", "asteroid-03-mini.png", AssetType.ASTEROID, AssetIntensity.HIGH);
        this.catalog.register("asteroid_04", "asteroid-04-mini.png", AssetType.ASTEROID, AssetIntensity.HIGH);
        this.catalog.register("asteroid_05", "asteroid-05-mini.png", AssetType.ASTEROID, AssetIntensity.HIGH);
        this.catalog.register("asteroid_06", "asteroid-06-mini.png", AssetType.ASTEROID, AssetIntensity.HIGH);
        this.catalog.register("asteroid_07", "asteroid-07.png", AssetType.ASTEROID, AssetIntensity.HIGH);
        this.catalog.register("asteroid_08", "asteroid-08.png", AssetType.ASTEROID, AssetIntensity.HIGH);
        // endregion

        // region backgrounds (bg-***)
        this.catalog.register("back_01", "bg-01-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_02", "bg-02-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_03", "bg-03-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_04", "bg-04-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_05", "bg-05-space.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_06", "bg-06-space.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_07", "bg-07-space.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_08", "bg-08-space.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_09", "bg-09-space.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_10", "bg-10-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_11", "bg-11-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_12", "bg-12-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_13", "bg-13-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_14", "bg-14-space.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_15", "bg-15-space.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_16", "bg-16-space.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_17", "bg-17-space.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_18", "bg-18-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_19", "bg-19-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_20", "bg-20-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_21", "bg-21-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_22", "bg-22-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_23", "bg-23-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_24", "bg-24-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        this.catalog.register("back_25", "bg-25-space-seamless.jpg", AssetType.BACKGROUND, AssetIntensity.LOW);
        // endregion

        // region black holes (black-hole-***)
        this.catalog.register("black_hole_01", "black-hole-01.png", AssetType.BLACK_HOLE, AssetIntensity.HIGH);
        this.catalog.register("black_hole_02", "black-hole-02.png", AssetType.BLACK_HOLE, AssetIntensity.HIGH);
        // endregion

        // region bubbles (bubbles-***)
        this.catalog.register("bubbles_01", "bubbles-01-mini.png", AssetType.BUBBLES, AssetIntensity.HIGH);
        this.catalog.register("bubbles_02", "bubbles-02-mini.png", AssetType.BUBBLES, AssetIntensity.HIGH);
        this.catalog.register("bubbles_03", "bubbles-03-mini.png", AssetType.BUBBLES, AssetIntensity.HIGH);
        this.catalog.register("bubbles_04", "bubbles-04-mini.png", AssetType.BUBBLES, AssetIntensity.HIGH);
        // endregion

        // region bullets (bullet-***)
        this.catalog.register("bullet_01", "bullet-01.png", AssetType.BULLET, AssetIntensity.LOW);
        // endregion

        // region bombs (bomb-***)
        this.catalog.register("bomb_01", "grenade-01.png", AssetType.MINE, AssetIntensity.MEDIUM);
        this.catalog.register("bomb_02", "grenade-02.png", AssetType.MINE, AssetIntensity.MEDIUM);
        // endregion

        // region cosmic portals (cosmic-portal-***)
        this.catalog.register("cosmic_portal_01", "cosmic-portal-01.png", AssetType.COSMIC_PORTAL, AssetIntensity.HIGH);
        // endregion

        // region cracks (cracks-***)
        this.catalog.register("cracks_01", "cracks-01.png", AssetType.CRACKS, AssetIntensity.HIGH);
        this.catalog.register("cracks_02", "cracks-02.png", AssetType.CRACKS, AssetIntensity.HIGH);
        this.catalog.register("cracks_03", "cracks-03.png", AssetType.CRACKS, AssetIntensity.HIGH);
        this.catalog.register("cracks_04", "cracks-04.png", AssetType.CRACKS, AssetIntensity.HIGH);
        this.catalog.register("cracks_05", "cracks-05.png", AssetType.CRACKS, AssetIntensity.HIGH);
        // endregion

        // region galaxies (galaxy-***)
        this.catalog.register("galaxy_01", "galaxy-01.png", AssetType.GALAXY, AssetIntensity.HIGH);
        this.catalog.register("galaxy_02", "galaxy-02.png", AssetType.GALAXY, AssetIntensity.HIGH);
        this.catalog.register("galaxy_03", "galaxy-03.png", AssetType.GALAXY, AssetIntensity.HIGH);
        this.catalog.register("galaxy_03", "galaxy-04.png", AssetType.GALAXY, AssetIntensity.HIGH);
        this.catalog.register("galaxy_04", "galaxy-05.png", AssetType.GALAXY, AssetIntensity.HIGH);
        this.catalog.register("galaxy_06", "galaxy-06.png", AssetType.GALAXY, AssetIntensity.HIGH);
        // endregion

        // region halos (halo-***))
        this.catalog.register("halo_01", "halo-01.png", AssetType.HALO, AssetIntensity.HIGH);
        this.catalog.register("halo_02", "halo-02.png", AssetType.HALO, AssetIntensity.HIGH);
        this.catalog.register("halo_03", "halo-03.png", AssetType.HALO, AssetIntensity.HIGH);
        this.catalog.register("halo_04", "halo-04.png", AssetType.HALO, AssetIntensity.HIGH);
        this.catalog.register("halo_05", "halo-05.png", AssetType.HALO, AssetIntensity.HIGH);
        this.catalog.register("halo_06", "halo-06.png", AssetType.HALO, AssetIntensity.HIGH);
        this.catalog.register("halo_07", "halo-07.png", AssetType.HALO, AssetIntensity.HIGH);
        this.catalog.register("halo_08", "halo-08.png", AssetType.HALO, AssetIntensity.HIGH);
        this.catalog.register("halo_09", "halo-09.png", AssetType.HALO, AssetIntensity.HIGH);
        this.catalog.register("halo_10", "halo-10.png", AssetType.HALO, AssetIntensity.HIGH);
        this.catalog.register("halo_11", "halo-11.png", AssetType.HALO, AssetIntensity.HIGH);
        this.catalog.register("halo_12", "halo-12.png", AssetType.HALO, AssetIntensity.HIGH);
        this.catalog.register("halo_13", "halo-13.png", AssetType.HALO, AssetIntensity.HIGH);
        // endregion

        // region labs (lab-***)
        this.catalog.register("lab_01", "lab-01.png", AssetType.LAB, AssetIntensity.HIGH);
        this.catalog.register("lab_02", "lab-02.png", AssetType.LAB, AssetIntensity.HIGH);
        this.catalog.register("lab_03", "lab-03.png", AssetType.LAB, AssetIntensity.HIGH);
        this.catalog.register("lab_04", "lab-04.png", AssetType.LAB, AssetIntensity.HIGH);
        // endregion

        // region lights (light-***)
        this.catalog.register("light_01", "light-01.png", AssetType.LIGHT, AssetIntensity.HIGH);
        this.catalog.register("light_02", "light-02.png", AssetType.LIGHT, AssetIntensity.HIGH);
        // endregion

        // region missiles (misil-***)
        this.catalog.register("misil_01", "misil-01-mini.png", AssetType.MISSILE, AssetIntensity.HIGH);
        this.catalog.register("misil_02", "misil-02-mini.png", AssetType.MISSILE, AssetIntensity.HIGH);
        this.catalog.register("misil_03", "misil-03-mini.png", AssetType.MISSILE, AssetIntensity.HIGH);
        this.catalog.register("misil_04", "misil-04.png", AssetType.MISSILE, AssetIntensity.HIGH);
        this.catalog.register("misil_05", "misil-05-mini.png", AssetType.MISSILE, AssetIntensity.HIGH);
        // endregion

        // region meteors (meteor-***)
        this.catalog.register("meteor_01", "meteor-01.png", AssetType.METEOR, AssetIntensity.HIGH);
        this.catalog.register("meteor_02", "meteor-02.png", AssetType.METEOR, AssetIntensity.HIGH);
        this.catalog.register("meteor_03", "meteor-03.png", AssetType.METEOR, AssetIntensity.HIGH);
        this.catalog.register("meteor_04", "meteor-04.png", AssetType.METEOR, AssetIntensity.HIGH);
        this.catalog.register("meteor_05", "meteor-05.png", AssetType.METEOR, AssetIntensity.HIGH);
        // endregion

        // region moons (moon-***)
        this.catalog.register("moon_01", "moon-01.png", AssetType.MOON, AssetIntensity.LOW);
        this.catalog.register("moon_02", "moon-02.png", AssetType.MOON, AssetIntensity.LOW);
        this.catalog.register("moon_03", "moon-03.png", AssetType.MOON, AssetIntensity.LOW);
        this.catalog.register("moon_04", "moon-04.png", AssetType.MOON, AssetIntensity.LOW);
        this.catalog.register("moon_05", "moon-05.png", AssetType.MOON, AssetIntensity.LOW);
        this.catalog.register("moon_06", "moon-06.png", AssetType.MOON, AssetIntensity.LOW);
        this.catalog.register("moon_07", "moon-07.png", AssetType.MOON, AssetIntensity.LOW);
        this.catalog.register("moon_08", "moon-08.png", AssetType.MOON, AssetIntensity.LOW);
        // endregion

        // region planets (planet-***)
        this.catalog.register("planet_01", "planet-01.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_02", "planet-02.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_03", "planet-03.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_04", "planet-04.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_05", "planet-05.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_06", "planet-06.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_07", "planet-07.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_08", "planet-08.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_09", "planet-09.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_10", "planet-10.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_11", "planet-11.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_12", "planet-12.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_13", "planet-13.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_14", "planet-14.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_15", "planet-15.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_16", "planet-16.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_17", "planet-17.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_18", "planet-18.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_19", "planet-19.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_20", "planet-20.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_21", "planet-21.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_22", "planet-22.png", AssetType.PLANET, AssetIntensity.HIGH);
        this.catalog.register("planet_23", "planet-23.png", AssetType.PLANET, AssetIntensity.HIGH);
        // endregion

        // region rainbows (rainbow-***)
        this.catalog.register("rainbow_01", "rainbow-01.png", AssetType.RAINBOW, AssetIntensity.HIGH);
        // endregion

        // region rockets (rocket-***)
        this.catalog.register("rocket_01", "rocket-01.png", AssetType.ROCKET, AssetIntensity.HIGH);
        this.catalog.register("rocket_02", "rocket-02.png", AssetType.ROCKET, AssetIntensity.HIGH);
        this.catalog.register("rocket_03", "rocket-03.png", AssetType.ROCKET, AssetIntensity.HIGH);
        this.catalog.register("rocket_04", "rocket-04.png", AssetType.ROCKET, AssetIntensity.HIGH);
        this.catalog.register("rocket_05", "rocket-05.png", AssetType.ROCKET, AssetIntensity.HIGH);
        this.catalog.register("rocket_06", "rocket-06.png", AssetType.ROCKET, AssetIntensity.HIGH);
        this.catalog.register("rocket_07", "rocket-07.png", AssetType.ROCKET, AssetIntensity.HIGH);
        // endregion

        // region shot holes (shot-hole-***)
        this.catalog.register("shot_hole_01", "shot-hole-01.png", AssetType.SHOT_HOLE, AssetIntensity.HIGH);
        this.catalog.register("shot_hole_02", "shot-hole-02.png", AssetType.SHOT_HOLE, AssetIntensity.HIGH);
        this.catalog.register("shot_hole_03", "shot-hole-03.png", AssetType.SHOT_HOLE, AssetIntensity.HIGH);
        // endregion

        // region stars (stars-***)
        this.catalog.register("stars_01", "stars-01.png", AssetType.STARS, AssetIntensity.HIGH);
        this.catalog.register("stars_02", "stars-02.png", AssetType.STARS, AssetIntensity.HIGH);
        this.catalog.register("stars_03", "stars-03.png", AssetType.STARS, AssetIntensity.HIGH);
        this.catalog.register("stars_04", "stars-04.png", AssetType.STARS, AssetIntensity.HIGH);
        this.catalog.register("stars_05", "stars-05.png", AssetType.STARS, AssetIntensity.HIGH);
        this.catalog.register("stars_06", "stars-06.png", AssetType.STARS, AssetIntensity.HIGH);
        this.catalog.register("stars_07", "stars-07.png", AssetType.STARS, AssetIntensity.HIGH);
        // endregion

        // region stardusts (stardust-***)
        this.catalog.register("stardust_01", "stardust-01.png", AssetType.STARDUST, AssetIntensity.HIGH);
        this.catalog.register("stardust_02", "stardust-02.png", AssetType.STARDUST, AssetIntensity.HIGH);

        // region spaceships (spaceship-***)
        this.catalog.register("spaceship_01", "spaceship-01.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        this.catalog.register("spaceship_02", "spaceship-02.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        this.catalog.register("spaceship_03", "spaceship-03.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        this.catalog.register("spaceship_04", "spaceship-04.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        this.catalog.register("spaceship_05", "spaceship-05.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        this.catalog.register("spaceship_06", "spaceship-06.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        this.catalog.register("spaceship_08", "spaceship-08.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        this.catalog.register("spaceship_09", "spaceship-09.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        this.catalog.register("spaceship_10", "spaceship-10.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        this.catalog.register("spaceship_11", "spaceship-11.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        this.catalog.register("spaceship_12", "spaceship-12.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        this.catalog.register("spaceship_13", "spaceship-13.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        this.catalog.register("spaceship_14", "spaceship-14.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        this.catalog.register("spaceship_15", "spaceship-15.png", AssetType.SPACESHIP, AssetIntensity.HIGH);
        // endregion

        // region suns (sun-***)
        this.catalog.register("sun_01", "sun-01.png", AssetType.SUN, AssetIntensity.HIGH);
        this.catalog.register("sun_02", "sun-02.png", AssetType.SUN, AssetIntensity.HIGH);
        this.catalog.register("sun_03", "sun-03.png", AssetType.SUN, AssetIntensity.HIGH);
        this.catalog.register("sun_04", "sun-04.png", AssetType.SUN, AssetIntensity.HIGH);
        // endregion

        // region ui signs (ui-signs-***)
        this.catalog.register("signs_01", "ui-signs-1.png", AssetType.UI_SIGN, AssetIntensity.HIGH);
        // endregion

    }
}
