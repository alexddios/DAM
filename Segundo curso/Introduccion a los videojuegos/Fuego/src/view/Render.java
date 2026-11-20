package view;

import java.awt.image.BufferedImage;

public class Render {
    private final Fire fire;
    private final Palette palette;
    private final BufferedImage image;

    public Render(Fire fire, Palette palette) {
        this.fire = fire;
        this.palette = palette;
        this.image = new BufferedImage(fire.getWidth(),fire.getHeight(),BufferedImage.TYPE_INT_ARGB);
    }

    public BufferedImage getImage() {
        int[][] temperatures = fire.getTemperatures();

        // fire: que el modelo no cambie las temperaturas mientras se leen
        // image: que el Viewer no pinte la imagen a medio escribir
        synchronized (fire) {
            synchronized (image) {
                for (int y = 0; y < fire.getHeight(); y++) {
                    for (int x = 0; x < fire.getWidth(); x++) {
                        image.setRGB(x,y,palette.color(temperatures[y][x]));
                    }
                }
            }
        }
        return image;
    }
}