package view;

import java.awt.image.BufferedImage;

public class Render {
    private final Palette palette;

    public Render(Palette palette) {
        this.palette = palette;
    }

    public BufferedImage getImage(int[][] temperatures) {
        BufferedImage image = new BufferedImage(temperatures[0].length, temperatures.length, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < temperatures.length; y++) {
            for (int x = 0; x < temperatures[0].length; x++) {
                image.setRGB(x, y, palette.color(temperatures[y][x]));
            }
        }
        return image;
    }
}