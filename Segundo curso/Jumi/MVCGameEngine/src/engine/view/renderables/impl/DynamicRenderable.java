package engine.view.renderables.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import engine.utils.images.ImageCache;
import engine.view.renderables.ports.DynamicRenderDTO;

public class DynamicRenderable extends Renderable {

    private boolean debugMode = false;

    // region Constructors
    public DynamicRenderable(DynamicRenderDTO renderInfo, String assetId, ImageCache cache, long currentFrame) {
        super(renderInfo, assetId, cache, currentFrame);
    }

    public DynamicRenderable(String entityId, String assetId, ImageCache cache, long currentFrame) {
        super(entityId, assetId, cache, currentFrame);
    }
    // endregion

    public void update(DynamicRenderDTO renderInfo, long currentFrame) {
        DynamicRenderDTO current = (DynamicRenderDTO) this.getRenderData();
        if (current != null && renderInfo != null) {
            current.updateFrom(renderInfo);
            super.update(current, currentFrame);
            return;
        }

        super.update(renderInfo, currentFrame);
    }

    @Override
    public void paint(Graphics2D g, long currentFrame) {
        DynamicRenderDTO bodyInfo = (DynamicRenderDTO) this.getRenderData();

        super.paint(g, currentFrame);

        if (bodyInfo == null || !this.debugMode) {
            return;
        }

        int x = (int) bodyInfo.posX;
        int y = (int) bodyInfo.posY;

        // Speed
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.setColor(Color.YELLOW);
        g.drawString(
                String.format("S: %.1f ", Math.hypot(bodyInfo.speedX, bodyInfo.speedY)),
                x + 10,
                y - 90);

        // Acceleration
        g.setFont(new Font("Arial", Font.PLAIN, 24));   
        g.setColor(Color.RED);
        g.drawString(
                String.format("A: %.1f ", Math.hypot(bodyInfo.accX, bodyInfo.accY)),
                x + 10,
                y - 60);

        // Speed vector
        if ((bodyInfo.speedX != 0) || (bodyInfo.speedY != 0)) {
            g.setStroke(new BasicStroke(1f));
            g.setColor(Color.YELLOW);
            g.drawLine(x, y, x + (int) (bodyInfo.speedX / 6d), y + (int) (bodyInfo.speedY / 6d));
        }

        // Acc vector
        if ((bodyInfo.accX != 0) || (bodyInfo.accY != 0)) {
            g.setStroke(new BasicStroke(1.0f));
            g.setColor(Color.RED);
            g.drawLine(x, y, x + (int) (bodyInfo.accX) / 7, y + (int) (bodyInfo.accY) / 7);
        }
    }
}
