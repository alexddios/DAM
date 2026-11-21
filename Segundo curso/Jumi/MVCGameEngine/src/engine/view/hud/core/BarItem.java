package engine.view.hud.core;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class BarItem extends Item {

    // region Fields
    final int barWidth;
    final boolean showPercentage;
    // endregion

    // region Constructors
    BarItem(String label, Color labelColor, Color dataColor, int barWidth, boolean showPercentage) {
        super(label, labelColor, dataColor, true);
        this.barWidth = barWidth;
        this.showPercentage = showPercentage;
    }

    BarItem(String label, Color labelColor, Color dataColor, int barWidth) {
        this(label, labelColor, dataColor, barWidth, true);
    }
    // endregion

    // *** INTERFACE IMPLEMENTATIONS ***

    // region Item
    @Override
    public void draw(Graphics2D g, FontMetrics fm, int posX, int posY, Object value) {
        if (!(value instanceof Double)) {
            throw new IllegalArgumentException("BarItem '" + this.getLabel() + "' expects a double value!");
        }

        g.setColor(this.getLabelColor());
        String label = this.getPaddedLabel();
        g.drawString(label, posX, posY);

        // X: after label
        final int barX = posX + fm.stringWidth(label);

        // Y: baseline is posY. Compute text box vertical span.
        final int textTop = posY - fm.getAscent();
        final int textHeight = fm.getHeight();

        // Bar geometry: height relative to text height (you can tweak factor)
        final int barHeight = Math.max(6, (int) Math.round(textHeight * 0.55));
        final int barY = textTop + (textHeight - barHeight) / 2;
        final int arc = barHeight / 2;

        // Draw Border
        g.setColor(this.getLabelColor());
        g.drawRoundRect(barX, barY, barWidth, barHeight, arc, arc);

        // Progess bar
        double progress = (Double) value;
        progress = Math.max(0.0, Math.min(1.0, progress));
        int fillWidth = (int) ((barWidth - 2) * progress);
        float hue = (float) (0.33 * progress); // Hue: 0.0 = red, ~0.33 = green
        Color baseColor = Color.getHSBColor(hue, 1.0f, 1.0f);
        Color fillColor = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 85);

        g.setColor(fillColor);
        g.fillRoundRect(barX + 1, barY + 1, fillWidth, barHeight - 2, arc, arc);

        // Draw percentage text
        if (this.showPercentage) {
            g.setColor(this.getDataColor());
            String percentText = String.format("%d%%", (int) (progress * 100));
            int textX = barX + barWidth + 15;
            g.drawString(percentText, textX, posY);
        }
    }
    // endregion
}