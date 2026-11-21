package engine.view.hud.impl;

import java.awt.Color;

import engine.view.hud.core.DataHUD;

/**
 * Instrumentation HUD for displaying profiling metrics.
 * 
 * Shows rendering, domain (physics/events), and spatial grid performance metrics
 * as ms/frame normalized values.
 */
public class InstrumentationHUD extends DataHUD {

    // region Constructors
    public InstrumentationHUD() {
        super(
                new Color(0, 200, 100, 255), // Title color (green)
                Color.GRAY, // Highlight color
                new Color(255, 255, 255, 150), // Label color
                new Color(255, 255, 255, 255), // Data color
                100, 800, 35);

        this.addItems();
    }
    // endregion

    private void addItems() {
        this.addTitle("RENDER BREAKDOWN (ms/frame)");
        
        // Render timing breakdown
        this.addTextItem("Background Draw");
        this.addTextItem("Static Objects");
        this.addTextItem("Query Dynamic");
        this.addTextItem("Paint Dynamic");
        this.addTextItem("Total Dynamic");
        this.addTextItem("HUD Draw");
        this.addTextItem("Total Draw");
        this.addTextItem("Update Phase");
        this.addTextItem("Full Frame");
        
        this.prepareHud();
    }

    public void draw(java.awt.Graphics2D g, Object... values) {
        super.draw(g, values);
    }
}

