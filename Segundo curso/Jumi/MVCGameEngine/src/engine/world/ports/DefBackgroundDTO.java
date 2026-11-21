package engine.world.ports;


public class DefBackgroundDTO  {

    // region Fields
    public final String assetId;
    public final double scrollSpeedX;
    public final double scrollSpeedY;
    // endregion

    // *** CONSTRUCTOR ***

    public DefBackgroundDTO(String assetId, double scrollSpeedX, double scrollSpeedY) {

        this.assetId = assetId;

        this.scrollSpeedX = scrollSpeedX;
        this.scrollSpeedY = scrollSpeedY;
    }
}
