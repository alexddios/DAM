package engine.view.hud.impl;

import java.awt.Color;

import engine.view.hud.core.DataHUD;

public class PlayerHUD extends DataHUD {
    public PlayerHUD() {
        super(
                new Color(255, 140, 0, 255), // Title color
                Color.GRAY, // Highlight color
                new Color(255, 255, 255, 150), // Label color
                new Color(255, 255, 255, 255), // Data color
                50, 12, 35);

        this.addItems();
    }

    private void addItems() {
        this.addTitle("PLAYER STATUS");
        this.addSkipValue(); // Entity ID
        this.addSkipValue(); // Player name
        this.addBarItem("Damage", 125, false);
        this.addBarItem("Energy", 125, false);
        this.addBarItem("Shield", 125, false);
        this.addTextItem("Temperature");
        this.addTitle("Weapons");
        this.addSkipValue(); // Active weapon
        this.addBarItem("Guns", 125, false);
        this.addBarItem("Burst", 125, false);
        this.addBarItem("Mines", 125, false);
        this.addBarItem("Missiles", 125, false);
        this.prepareHud();
    }
}
