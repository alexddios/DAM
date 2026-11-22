package engine.assets.ports;


public class AssetInfoDTO {

    public final String assetId;
    public final String fileName; 
    public final AssetType type;
    public final AssetIntensity intensity;


    public AssetInfoDTO(String assetId, String fileName, 
            AssetType type, AssetIntensity intensity) {
        
        this.assetId = assetId;
        this.fileName = fileName;
        this.type = type;
        this.intensity = intensity;
    }
}
