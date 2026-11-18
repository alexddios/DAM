package model;

import java.awt.image.BufferedImage;

public class Model implements IModel,Runnable{
    private final int width = 320;
    private final int height = 200;
    private final int frameMS = 33;

    private final Fire fire;
    private final Render render;

    public Model(){
        fire = new Fire(width,height);
        render = new Render(fire, Palette.createPalette());
    }
    @Override
    public void start() { // Inicia el hilo
        Thread thread = new Thread(this, "Model-Fire");
        thread.setDaemon(true); // el hilo se cierra solo al cerrar el programa
        thread.start(); // El hilo se ejecuta
    }
    @Override
    public void run() {
        while (true) {
            update();
            try {
                Thread.sleep(frameMS);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    @Override
    public void update() {
        synchronized (fire) { // Render no lee mientras se actualiza
            fire.sparksGeneration();
            fire.coolPointsGeneration();
            fire.updateFlames();
        }
    }

    @Override
    public BufferedImage getImage() {
        return render.getImage();
    }


}