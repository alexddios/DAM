package model;

import config.Config;
import config.Estado;

public class Model implements IModel,Runnable{
    private final int width = 320;
    private final int height = 200;
    private final int frameMS = 33;

    private volatile Estado estado;
    private volatile int pasos;
    private final Fire fire;

    public Model(){
        fire = new Fire(width,height);
        this.estado = Estado.PARADO;
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
            if (estado == Estado.EN_MARCHA){
                update();
                pasos++;
            }
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

    @Override
    public void iniciar() {
        estado = Estado.EN_MARCHA;
    }

    @Override
    public void pausar() {
        estado = Estado.EN_PAUSA;
    }

    @Override
    public void parar() {
        estado = Estado.PARADO;
        synchronized (fire){
            fire.restartTemperatures();
        }
        pasos=0;
    }

    @Override
    public Estado getEstado() {
        return estado;
    }

    @Override
    public int getPasos() {
        return pasos;
    }

    @Override
    public void aplicarConfig(Config config) {
        synchronized (fire){
            fire.setAtributos(config);
        }
    }

    @Override
    public Config getConfig() {
        synchronized (fire){
            return new Config(
                    fire.getSparksPercentage(),
                    fire.getCoolPontsPercentage(),
                    fire.getCooling()
            );
        }
    }

}