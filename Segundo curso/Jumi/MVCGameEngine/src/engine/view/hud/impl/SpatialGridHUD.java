package engine.view.hud.impl;

import java.awt.Color;

import engine.view.hud.core.DataHUD;

public class SpatialGridHUD extends DataHUD  {
    public SpatialGridHUD() {
        super(
                new Color(255, 140, 0, 255 ), // Title color
                Color.GRAY, // Highlight color
                new Color(255, 255, 255, 150), // Label color
                new Color(255, 255, 255, 255), // Data color
                1200, 12, 35);

        this.addItems();
    }


    private void addItems() {
        this.addTitle("SPATIAL GRID ");
        this.addTextItem("Cell Size");
        this.addTextItem("Total Cells");
        this.addBarItem("Empties", 125, false);
        this.addTextItem("Avg Bodies");
        this.addTextItem("Max Bodies");
        this.addTextItem("Pair Checks");

        this.prepareHud();
    }
}
