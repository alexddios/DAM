package view;

import java.awt.*;
import java.awt.image.BufferedImage;

// El Viewer es el canvas: recibe la imagen desde el hilo de la View y la pinta
public class Viewer extends Canvas{
    private volatile BufferedImage image;

    public void setImage(BufferedImage image){
        this.image=image;
        repaint(); // Vuelve a dibujar -> Canvas
    }

    @Override
    public void paint(Graphics g){
        BufferedImage current = image;
        if (current == null || getWidth() == 0 || getHeight()==0) return;

        BufferedImage buffer = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics gb = buffer.getGraphics();

        gb.setColor(Color.WHITE);
        gb.fillRect(0,0,getWidth(),getHeight());

        gb.drawImage(current,0,0,getWidth(),getHeight(),null);

        gb.dispose();

        g.drawImage(buffer, 0, 0, null);
    }

    @Override
    public void update(Graphics g){
        paint(g);
    }
}
