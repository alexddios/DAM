package engine.view.renderables.ports;

public class PlayerRenderDTO {
    public final String entityId;
    public final String playerName;
    public final double damage;
    public final double energy;
    public final double shield;
    public final int temperature;
    public final int activeWeapon;
    public final double primaryAmmoStatus;
    public final double secondaryAmmoStatus;
    public final double minesStatus;
    public final double missilesStatus;

    public PlayerRenderDTO(
            String entityId,
            String playerName,
            double damage,
            double energy,
            double shield,
            int temperature,
            int activeWeapon,
            double primaryAmmoStatus,
            double secondaryAmmoStatus,
            double minesStatus,
            double missilesStatus) {

        this.entityId = entityId;
        this.playerName = playerName;
        this.damage = damage;
        this.energy = energy;
        this.shield = shield;
        this.temperature = temperature;
        this.activeWeapon = activeWeapon;
        this.primaryAmmoStatus = primaryAmmoStatus;
        this.secondaryAmmoStatus = secondaryAmmoStatus;
        this.minesStatus = minesStatus;
        this.missilesStatus = missilesStatus;
    }

    public Object[] toObjectArray() {
        return new Object[] {
                this.entityId,
                this.playerName,
                this.damage,
                this.energy,
                this.shield,
                this.temperature,
                this.activeWeapon,
                this.primaryAmmoStatus,
                this.secondaryAmmoStatus,
                this.minesStatus,
                this.missilesStatus
        };
    }
}
