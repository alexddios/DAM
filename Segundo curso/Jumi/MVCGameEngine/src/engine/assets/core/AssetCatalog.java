package engine.assets.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import engine.assets.ports.AssetInfoDTO;
import engine.assets.ports.AssetIntensity;
import engine.assets.ports.AssetType;

public class AssetCatalog {

    // region Fields
    private final String path;
    private final Map<String, AssetInfoDTO> assetsById = new HashMap<>();
    private Random rnd = new Random();
    // endregion

    // *** CONSTRUCTORS ***

    public AssetCatalog(String path) {
        this.path = path;
    }

    // *** PUBLIC ***

    public boolean exists(String assetId) {
        return assetsById.containsKey(assetId);
    }

    // region getters (get***)
    public AssetInfoDTO get(String assetId) {
        AssetInfoDTO aInfo = assetsById.get(assetId);
        return aInfo;
    }

    public ArrayList<String> getAssetIds() {
        return new ArrayList<String>(this.assetsById.keySet());
    }

    public String getPath() {
        return this.path;
    }
    // endregion

    public boolean isEmpty() {
        return this.assetsById.isEmpty();
    }

    public String randomId(AssetType type) {
        if (type == null) {
            throw new IllegalStateException("Asset type is null!");
        }

        // Filtrar solo los ids del tipo solicitado
        List<String> filtered = new ArrayList<>();

        for (Map.Entry<String, AssetInfoDTO> entry : assetsById.entrySet()) {
            if (entry.getValue().type == type) {
                filtered.add(entry.getKey());
            }
        }

        if (filtered.isEmpty()) {
            throw new IllegalStateException("There isn't any asset <" + type + ">");
        }

        return filtered.get(rnd.nextInt(filtered.size()));
    }

    public String randomId(AssetType type, AssetIntensity intensity) {
        if (type == null) {
            throw new IllegalStateException("Asset type is null!");
        }

        List<String> filtered = new ArrayList<>();

        for (AssetInfoDTO info : assetsById.values()) {
            if (info.type == type && info.intensity == intensity) {
                filtered.add(info.assetId);
            }
        }

        if (filtered.isEmpty()) {
            return randomId(type); // fallback
        }

        return filtered.get(rnd.nextInt(filtered.size()));
    }

    public void register(String assetId, String fileName,
            AssetType type, AssetIntensity intensity) {

        this.assetsById.put(
                assetId,
                new AssetInfoDTO(assetId, fileName, type, intensity));
    }

    public void register(AssetInfoDTO assetInfo) {
        this.assetsById.put(
                assetInfo.assetId,
                new AssetInfoDTO(
                        assetInfo.assetId,
                        assetInfo.fileName,
                        assetInfo.type,
                        assetInfo.intensity));
    }

    public void reset() {
        this.assetsById.clear();
    }
}
