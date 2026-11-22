package engine.view.hud.core;

public class SeparatorItem extends Item {

    // region Constructors
    public SeparatorItem() {
        super("", null, null, false);
    }
    // endregion

    // *** INTERFACE IMPLEMENTATIONS ***

    @Override
    void draw(java.awt.Graphics2D g, java.awt.FontMetrics fm, int posX, int posY, Object value) {
        g.drawString(this.getLabel(), posX, posY);
    }
}
