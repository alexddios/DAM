package view;

import java.awt.*;
import java.awt.image.BufferedImage;

// El Viewer es el canvas: recibe la imagen desde el hilo de la View y la pinta
public class Viewer extends Canvas{
    private BufferedImage image;

    public void setImage(BufferedImage image){
        this.image=image;
        repaint(); // Vuelve a dibujar -> Canvas
    }

    @Override
    public void paint(Graphics g){
        BufferedImage current = image;
        if (current == null) return;
        // Bloquea la imagen para no pintarla mientras Render la está escribiendo
        synchronized (current) {
            g.drawImage(current,0,0,getWidth(),getHeight(),null);
        }
    }

    @Override
    public void update(Graphics g){
        paint(g);
    }
}
