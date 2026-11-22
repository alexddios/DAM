package engine.view.hud.impl;

import java.awt.Color;

import engine.view.hud.core.DataHUD;

public class SystemHUD extends DataHUD {
    public SystemHUD() {
        super(
                new Color(255, 140, 0, 255 ), // Title color
                Color.GRAY, // Highlight color
                new Color(255, 255, 255, 150), // Label color
                new Color(255, 255, 255, 255), // Data color
                1200, 400, 35);

        this.addItems();
    }

    private void addItems() {
        this.addTitle("SYSTEM STATUS");
        this.addTextItem("FPS");
        this.addTextItem("Draw Scene");
        this.addTextItem("Cache images");
        this.addTextItem("Cache hits");
        this.addTextItem("Entities Alive");
        this.addTextItem("Entities Dead");
        this.addTextItem("Curren Frame");

        this.prepareHud();
    }
}