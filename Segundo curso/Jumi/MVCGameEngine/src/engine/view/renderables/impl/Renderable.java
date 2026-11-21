package engine.view.renderables.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.utils.images.ImageCache;
import engine.view.renderables.ports.RenderDTO;

public class Renderable {

    private final String entityId;
    private final String assetId;
    private final ImageCache cache;

    private long lastFrameSeen;
    private RenderDTO renderData = null;
    private BufferedImage image = null;
    private String lastImageAssetId = null;
    private int lastImageAngle = Integer.MIN_VALUE;
    private int lastImageSize = -1;

    public Renderable(RenderDTO renderData, String assetId, ImageCache cache, long currentFrame) {
        if (assetId == null || assetId.isEmpty()) {
            throw new IllegalArgumentException("Asset ID not set");
        }
        if (cache == null) {
            throw new IllegalArgumentException("Image cache not set");
        }

        this.entityId = renderData.entityId;
        this.assetId = assetId;
        this.lastFrameSeen = currentFrame;
        this.renderData = renderData;
        this.cache = cache;
        this.updateImageFromCache(this.assetId, (int) renderData.size, renderData.angle);
    }

    public Renderable(String entityId, String assetId, ImageCache cache, long currentFrame) {
        if (entityId == null || entityId.isEmpty()) {
            throw new IllegalArgumentException("Entity ID not set");
        }
        if (assetId == null || assetId.isEmpty()) {
            throw new IllegalArgumentException("Asset ID not set");
        }
        if (cache == null) {
            throw new IllegalArgumentException("Image cache not set");
        }

        this.entityId = entityId;
        this.assetId = assetId;
        this.lastFrameSeen = currentFrame;
        this.cache = cache;
        this.image = null;
        this.renderData = null;
    }

    /**
     * PUBLICS
     */
    public long getLastFrameSeen() {
        return this.lastFrameSeen;
    }

    public String getAssetId() {
        return this.assetId;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public RenderDTO getRenderData() {
        return this.renderData;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void update(RenderDTO renderInfo, long currentFrame) {
        this.updateImageFromCache(this.assetId, (int) renderInfo.size, renderInfo.angle);
        this.lastFrameSeen = currentFrame;
        this.renderData = renderInfo;
    }

    public void paint(Graphics2D g, long currentFrame) {

        if (this.image == null) {
            return;
        }

        final double posX = this.renderData.posX;
        final double posY = this.renderData.posY;

        // Using the REAL size of the sprite for the offset
        final double halfW = this.image.getWidth(null) * 0.5;
        final double halfH = this.image.getHeight(null) * 0.5;

        final int drawX = (int) (posX - halfW);
        final int drawY = (int) (posY - halfH);

        g.drawImage(this.image, drawX, drawY, null);
    }

    public void updateImageFromCache(RenderDTO entityInfo) {
        this.updateImageFromCache(this.assetId, (int) entityInfo.size, entityInfo.angle);
    }

    private boolean updateImageFromCache(String assetId, int size, double angle) {
        int normalizedAngle = ((int) angle % 360 + 360) % 360;
        boolean imageNeedsUpdate = this.image == null
            || this.lastImageAssetId == null
            || !this.lastImageAssetId.equals(assetId)
            || this.lastImageSize != size
            || this.lastImageAngle != normalizedAngle;

        if (imageNeedsUpdate) {
            this.image = this.cache.getImage(normalizedAngle, assetId, size);
            this.lastImageAssetId = assetId;
            this.lastImageAngle = normalizedAngle;
            this.lastImageSize = size;

            return true; // ====
        }

        return false;
    }
}
