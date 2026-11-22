package engine.world.ports;

public class DefWeaponDTO {

    // region Fields
    public final String assetId;
    public final int burstSize; // Number of shots per burst
    public final int burstFireRate; // Fire rate within a burst (shots per second)
    public final int fireRate; // Fire rate (shots per second)
    public final int maxAmmo; // Maximum ammunition capacity
    public final double reloadTime; // Reload time (seconds)
    public final DefWeaponType weaponType; // Weapon type

    public final double projectileThrust; // Projectile acceleration (if applicable)
    public final double projectileThrustDuration; // Time during which the acceleration applies
    public final double projectileMass; // Mass of the projectile (kilograms)
    public final double projectileMaxLifetime; // Maximum lifetime of the projectile (seconds)
    public final double projectileSize; // Size of the projectile
    public final double projectileSpeed; // Projectile initial speed
    // endregion

    // *** CONSTRUCTOR ***

    public DefWeaponDTO(String assetId, double projectileSize, DefWeaponType weaponType,
            double projectileSpeed, double projectileAcc, double projectileAccDuration,
            int burstSize, int burstFireRate, int fireRate, int maxAmmo, double reloadTime,
            double projectileMass, double projectileMaxLifetime) {

        this.assetId = assetId;

        this.burstSize = burstSize;

        // NO burst condition
        if (this.burstSize <= 1) {
            this.burstFireRate = 0;
        } else {
            this.burstFireRate = burstFireRate;
        }

        this.fireRate = fireRate;
        this.maxAmmo = maxAmmo;
        this.reloadTime = reloadTime;
        this.weaponType = weaponType;

        this.projectileThrust = projectileAcc;
        this.projectileThrustDuration = projectileAccDuration;
        this.projectileMass = projectileMass;
        this.projectileMaxLifetime = projectileMaxLifetime;
        this.projectileSize = projectileSize;
        this.projectileSpeed = projectileSpeed;
    }

    public String toString() {
        return "DefWeaponDTO{" +
                "assetId='" + assetId + '\n' +
                "\t burstSize=" + burstSize + "\n" +
                "\t burstFireRate=" + burstFireRate + "\n" +
                "\t fireRate=" + fireRate + "\n" +
                "\t maxAmmo=" + maxAmmo + "\n" +
                "\t reloadTime=" + reloadTime + "\n" +
                "\t weaponType=" + weaponType + "\n" +
                "\t projectileThrust=" + projectileThrust + "\n" +
                "\t projectileThrustDuration=" + projectileThrustDuration + "\n" +
                "\t projectileMass=" + projectileMass + "\n" +
                "\t projectileMaxLifetime=" + projectileMaxLifetime + "\n" +
                "\t projectileSize=" + projectileSize + "\n" +
                "\t projectileSpeed=" + projectileSpeed +
                '}';
    }
}
