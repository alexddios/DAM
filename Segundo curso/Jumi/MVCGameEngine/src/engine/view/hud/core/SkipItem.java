package engine.view.hud.core;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class SkipItem extends Item {

    // region Constructors
    public SkipItem() {
        super("", null, null, true);
    }
    // endregion

    // *** INTERFACE IMPLEMENTATIONS ***

    @Override
    public void draw(Graphics2D g, FontMetrics fm, int posX, int posY, Object value) {
        // No drawing needed for skip item
    }
}
