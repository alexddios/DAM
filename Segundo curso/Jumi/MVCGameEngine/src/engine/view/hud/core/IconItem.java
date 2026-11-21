package engine.view.hud.core;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class IconItem extends Item {

    // regions Fields
    private final BufferedImage icon;
    private final int iconWidth;
    private final int iconHeight;
    private final int textPadding;
    // endregion

    // region Constructors
    IconItem(BufferedImage icon, int iconWidth, int iconHeight, int textPadding, Color dataColor) {
        super("", null, dataColor, true);
        if (icon == null) {
            throw new IllegalArgumentException("IconItem requires a non-null icon image");
        }
        if (iconWidth <= 0 || iconHeight <= 0) {
            throw new IllegalArgumentException("IconItem icon dimensions must be positive");
        }
        if (textPadding < 0) {
            throw new IllegalArgumentException("IconItem text padding cannot be negative");
        }

        this.icon = icon;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.textPadding = textPadding;
    }
    // endregion

    // *** INTERFACE IMPLEMENTATIONS ***

    @Override // Item
    public void draw(Graphics2D g, FontMetrics fm, int posX, int posY, Object value) {
        final int textTop = posY - fm.getAscent();
        final int textHeight = fm.getHeight();
        final int iconY = textTop + (textHeight - this.iconHeight) / 2;

        g.drawImage(this.icon, posX, iconY, this.iconWidth, this.iconHeight, null);

        final String valueText = String.valueOf(value);
        final int valueX = posX + this.iconWidth + this.textPadding;
        g.setColor(getDataColor());
        g.drawString(valueText, valueX, posY);
    }
}
