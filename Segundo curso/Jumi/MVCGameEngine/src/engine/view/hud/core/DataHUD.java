package engine.view.hud.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DataHUD {
    // region Fields
    public final int initRow;
    public final int initCol;
    public final int interline;
    public final Color highLightColor;
    public final Color titleColor;
    public final Color labelColor;
    public final Color dataColor;

    public Font font = new Font("Monospaced", Font.PLAIN, 28);
    public int maxLenLabel = 0;
    public final List<Item> items = new ArrayList<>(20);
    public int valuesExpected = 0;
    // endregion

    // region Constructors
    public DataHUD(Color titleColor, Color highLightColor, Color labelColor, Color dataColor, int initRow, int initCol,
            int interline) {
        this.initRow = initRow;
        this.initCol = initCol;
        this.interline = interline;
        this.highLightColor = highLightColor;
        this.titleColor = titleColor;
        this.labelColor = labelColor;
        this.dataColor = dataColor;
    }
    // endregion

    // *** PUBLICS ***

    // region add elements to HUD
    public void addBarItem(String label, int barWidth) {
        this.addItem(new BarItem(label, this.labelColor, this.dataColor, barWidth));
    }

    public void addBarItem(String label, int barWidth, boolean showPercentage) {
        this.addItem(new BarItem(label, this.labelColor, this.dataColor, barWidth, showPercentage));
    }

    public void addIconItem(BufferedImage icon, int iconWidth, int iconHeight, int textPadding) {
        this.addItem(new IconItem(icon, iconWidth, iconHeight, textPadding, this.dataColor));
    }

    public void addIconItem(BufferedImage icon, int iconSize, int textPadding) {
        this.addIconItem(icon, iconSize, iconSize, textPadding);
    }

    public void addSeparatorItem() {
        this.addItem(new SeparatorItem());
    }

    public void addSkipValue() {
        this.addItem(new SkipItem());
    }

    public void addTextItem(String label) {
        this.addItem(new TextItem(label, this.labelColor, this.dataColor));
    }

    public void addTitle(String title) {
        this.addItem(new TitleItem(title, this.titleColor));
    }
    // endregion

    public void draw(Graphics2D g, Object... values) {
        if (values.length != this.valuesExpected) {
            throw new IllegalArgumentException(
                    "Hud.draw: expected " + this.valuesExpected + " values but got " + values.length);
        }

        g.setFont(this.font);
        final FontMetrics fm = g.getFontMetrics();
        int valueIndex = 0;
        int row = 0;
        Object value = null;
        for (Item item : this.items) {
            if (item.isValueExpected()) {
                value = values[valueIndex];
                valueIndex++;
            }

            int posY = this.initRow + row * this.interline;
            item.draw(g, fm, this.initCol, posY, value);
            row = (item instanceof SkipItem) ? row : row + 1;
        }
    }

    public void prepareHud() {
        for (Item item : this.items) {
            item.updatePaddedLabel(maxLenLabel);
            if (item.isValueExpected()) {
                this.valuesExpected++;
            }
        }
    }

    // *** PRIVATES ***

    private void addItem(Item item) {
        items.add(item);

        if (item.isValueExpected()) {
            maxLenLabel = Math.max(maxLenLabel, item.getLabel().length());
        }
    }
}
