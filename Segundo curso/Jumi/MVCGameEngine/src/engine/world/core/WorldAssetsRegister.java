package engine.world.core;

import engine.assets.core.AssetCatalog;
import engine.assets.ports.AssetInfoDTO;
import engine.assets.ports.AssetType;
import gameworld.ProjectAssets;

public final class WorldAssetsRegister {

    // endregion Fields
    private final ProjectAssets projectAssets;
    private final AssetCatalog gameAssets;
    // endregion

    // *** CONSTRUCTOR ***

    public WorldAssetsRegister(ProjectAssets projectAssets, AssetCatalog gameAssets) {
        if (projectAssets == null) {
            throw new IllegalArgumentException("ProjectAssets cannot be null.");
        }
        if (gameAssets == null) {
            throw new IllegalArgumentException("AssetCatalog (gameAssets) cannot be null.");
        }
        this.projectAssets = projectAssets;
        this.gameAssets = gameAssets;
    }

    // *** PUBLICS ***

    public String pickRandomAssetId(AssetType assetType) {
        if (assetType == null) {
            throw new IllegalArgumentException("AssetType cannot be null.");
        }

        String id = this.projectAssets.catalog.randomId(assetType);
        if (id == null || id.isBlank()) {
            throw new IllegalStateException("ProjectAssets.catalog.randomId(" + assetType + ") returned null/blank.");
        }

        return id;
    }

    public String pickAndRegisterRandomAssetId(AssetType assetType) {
        if (assetType == null) {
            throw new IllegalArgumentException("AssetType cannot be null.");
        }

        String id = this.projectAssets.catalog.randomId(assetType);
        if (id == null || id.isBlank()) {
            throw new IllegalStateException("ProjectAssets.catalog.randomId(" + assetType + ") returned null/blank.");
        }

        this.registerAssetId(id);
        return id;
    }

    public void registerAssetId(String assetId) {
        if (assetId == null || assetId.isBlank()) {
            throw new IllegalArgumentException("assetId cannot be null/blank.");
        }

        AssetInfoDTO info = this.projectAssets.catalog.get(assetId);
        if (info == null) {
            throw new IllegalArgumentException("assetId not found in ProjectAssets catalog: " + assetId);
        }

        this.gameAssets.register(info);
    }
}