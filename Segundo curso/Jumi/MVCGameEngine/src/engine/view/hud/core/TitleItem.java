package engine.view.hud.core;

import java.awt.Color;

public class TitleItem extends Item {

    // region Constructors
    public TitleItem(String title, Color titleColor) {
        super(title, titleColor, null, false);
    }
    // endregion

    // *** INTERFACE IMPLEMENTATIONS ***

    @Override
    void draw(java.awt.Graphics2D g, java.awt.FontMetrics fm, int posX, int posY, Object value) {

        // Label
        g.setColor(getLabelColor());
        g.drawString(this.getLabel(), posX, posY);
    }
}
