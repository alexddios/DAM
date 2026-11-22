package engine.view.hud.core;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class TextItem extends Item {

    // region Constructors
    TextItem(String label, Color labelColor, Color dataColor) {
        super(label, labelColor, dataColor, true);
    }
    // endregion

    // *** INTERFACE IMPLEMENTATIONS ***

    @Override
    public void draw(Graphics2D g, FontMetrics fm, int posX, int posY, Object value) {
        final String labelText = getPaddedLabel();
        final String valueText = String.valueOf(value);

        // Label
        g.setColor(getLabelColor());
        g.drawString(labelText, posX, posY);

        // Value (justo después del label)
        int valueX = posX + fm.stringWidth(labelText);
        g.setColor(getDataColor());
        g.drawString(valueText, valueX, posY);
    }
}
