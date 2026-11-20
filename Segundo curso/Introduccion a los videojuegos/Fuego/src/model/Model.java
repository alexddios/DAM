package model;

public class Model implements IModel,Runnable{
    private final int width = 320;
    private final int height = 200;
    private final int frameMS = 33;

    private final Fire fire;

    public Model(){
        fire = new Fire(width,height);
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
        synchronized (fire) {
            fire.sparksGeneration();
            fire.coolPointsGeneration();
            fire.updateFlames();
        }
    }
    @Override
    public int[][] getTemperatures() {
        synchronized (fire){
            int [][] original = fire.getTemperatures();
            int[][] copia = new int[original.length][];
            for (int i = 0; i < original.length; i++) {
                copia[i] = original[i].clone();
            }
            return copia;
        }
    }

}