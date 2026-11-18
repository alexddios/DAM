package model;

import java.util.concurrent.ThreadLocalRandom;

/** Un consumidor: el espejo de Producer. */
public class Consumer implements Runnable {

    private final String name;
    private final Resource[] resources;
    private final int timeMin;            // tiempo que tarda en consumir (ms)
    private final int timeMax;
    private final int target;             // cuántos tiene que consumir (-1 = ilimitados)
    private final int fixedType;          // recurso fijo (posición en el array) o -1 = variable

    private volatile boolean paused = false;
    private volatile int consumed = 0;
    private volatile String state = "Preparado";

    public Consumer(String name, Resource[] resources, int timeMin, int timeMax, int target, int fixedType) {
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
            while (target < 0 || consumed < target) {
                while (paused) {
                    state = "En pausa";
                    Thread.sleep(50);
                }

                Resource r = chooseResource();
                state = "Cogiendo de " + r.getName();
                r.consume();                                     // aquí puede quedarse esperando
                consumed++;

                state = "Consumiendo";
                Thread.sleep(randomBetween(timeMin, timeMax));   // lo que tarda en consumir
            }
            state = "Terminado";
        } catch (InterruptedException e) {
            state = "Detenido";
        }
    }

    private static int randomBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public void setPaused(boolean paused) { this.paused = paused; }

    public String getName() { return name; }
    public int getConsumed() { return consumed; }
    public int getTarget() { return target; }
    public String getState() { return state; }
}
