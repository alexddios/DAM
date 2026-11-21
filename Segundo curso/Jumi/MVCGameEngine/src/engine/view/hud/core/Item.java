package engine.view.hud.core;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

abstract class Item {

    // region Fields
    private final String label;
    private final boolean valueExpected;
    private Color labelColor;
    private Color dataColor;
    private String paddedLabel;
    private boolean highlighted = false;
    // endregion

    // region Constructors
    Item(String label, Color labelColor, Color dataColor, boolean valueExpected, boolean highlighted) {
        if (label == null)
            label = "";

        this.label = label;
        this.valueExpected = valueExpected;
        this.labelColor = labelColor;
        this.dataColor = dataColor;
        this.highlighted = highlighted;
    }

    Item(String label, Color labelColor, Color dataColor, boolean valueExpected) {
        this(label, labelColor, dataColor, valueExpected, false);
    }
    // endregion

    // *** PUBLICS ***

    // region Getters (get***)
    public String getLabel() {
        return label;
    }

    public Color getLabelColor() {
        return labelColor;
    }

    public Color getDataColor() {
        return dataColor;
    }

    public String getPaddedLabel() {
        return paddedLabel;
    }
    // endregion

    // region boolean checkers (is***)
    public boolean isValueExpected() {
        return valueExpected;
    }

    public boolean isHighlighted() {
        return highlighted;
    }
    // endregion


    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public void updatePaddedLabel(int maxLenLabel) {
        if (!this.isValueExpected())
            return; // Only items expecting values need padded labels

        if (maxLenLabel < 0 || maxLenLabel < label.length()) {
            throw new IllegalArgumentException("maxLenLabel cannot be less than label length");
        }

        this.paddedLabel = String.format("%-" + (maxLenLabel + 1) + "s", label);
    }

    abstract void draw(Graphics2D g, FontMetrics fm, int posX, int posY, Object value);
}