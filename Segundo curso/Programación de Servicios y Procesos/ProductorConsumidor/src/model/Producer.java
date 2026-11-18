package model;

import java.util.concurrent.ThreadLocalRandom;

/** Un productor: su run() es lo que ejecuta el hilo. */
public class Producer implements Runnable {

    private final String name;
    private final Resource[] resources;   // todos los tipos de recurso
    private final int timeMin;            // tiempo que tarda en producir (ms)
    private final int timeMax;
    private final int target;             // cuántos tiene que producir (-1 = ilimitados)
    private final int fixedType;          // recurso fijo (posición en el array) o -1 = variable

    // volatile: los cambia un hilo y los lee otro (la pantalla o el Model)
    private volatile boolean paused = false;
    private volatile int produced = 0;
    private volatile String state = "Preparado";

    public Producer(String name, Resource[] resources, int timeMin, int timeMax, int target, int fixedType) {
        this.name = name;
        this.resources = resources;
        this.timeMin = timeMin;
        this.timeMax = timeMax;
        this.target = target;
        this.fixedType = fixedType;
    }

    /** Fijo: siempre el mismo recurso. Variable: uno al azar cada vez. */
    private Resource chooseResource() {
        if (fixedType >= 0) {
            return resources[fixedType];
        }
        return resources[ThreadLocalRandom.current().nextInt(resources.length)];
    }

    @Override
    public void run() {
        try {
            while (target < 0 || produced < target) {
                while (paused) {
                    state = "En pausa";
                    Thread.sleep(50);
                }

                state = "Produciendo";
                Thread.sleep(randomBetween(timeMin, timeMax));   // lo que tarda en producir

                Resource r = chooseResource();
                state = "Dejando en " + r.getName();
                r.produce();                                     // aquí puede quedarse esperando
                produced++;
            }
            state = "Terminado";
        } catch (InterruptedException e) {
            state = "Detenido";   // nos han pedido parar (botón Detener)
        }
    }

    private static int randomBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public void setPaused(boolean paused) { this.paused = paused; }

    public String getName() { return name; }
    public int getProduced() { return produced; }
    public int getTarget() { return target; }
    public String getState() { return state; }
}
