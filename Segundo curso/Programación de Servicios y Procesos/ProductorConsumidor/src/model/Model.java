package model;

import config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/** El director de orquesta: crea los recursos y los hilos y los controla. */
public class Model implements IModel {

    private final List<Producer> producers = new CopyOnWriteArrayList<>();
    private final List<Consumer> consumers = new CopyOnWriteArrayList<>();
    private final List<Thread> threads = new CopyOnWriteArrayList<>();

    private volatile Resource[] resources = new Resource[0];
    private volatile Thread watcher;          // hilo que espera con join() a que acaben todos

    private volatile String state = "Parado";
    private volatile boolean active = false;
    private volatile boolean paused = false;

    // =====================================================================
    // CONTROL
    // =====================================================================

    @Override
    public synchronized void start(Config c) {
        if (active) return;   // ya hay una simulación

        producers.clear();
        consumers.clear();
        threads.clear();

        // 1. Un Resource por cada tipo de recurso
        Resource[] res = new Resource[c.resourceTypes()];
        for (int i = 0; i < res.length; i++) {
            res[i] = new Resource("Recurso " + (i + 1), c);
        }
        resources = res;

        // 2. Los productores, cada uno con su objetivo (o ilimitado)
        for (int i = 1; i <= c.producers(); i++) {
            int target = c.produceUnlimited() ? -1 : randomBetween(c.produceMin(), c.produceMax());
            // Fijo: se reparten en orden (Productor 1 -> Recurso 1, 2 -> 2...; al acabar, vuelta a empezar)
            int fixedType = c.producerFixedType() ? (i - 1) % res.length : -1;
            Producer p = new Producer("Productor " + i, res, c.produceTimeMin(), c.produceTimeMax(),
                    target, fixedType);
            producers.add(p);
            threads.add(newThread(p, p.getName()));
        }

        // 3. Los consumidores
        for (int i = 1; i <= c.consumers(); i++) {
            int target = c.consumeUnlimited() ? -1 : randomBetween(c.consumeMin(), c.consumeMax());
            int fixedType = c.consumerFixedType() ? (i - 1) % res.length : -1;
            Consumer co = new Consumer("Consumidor " + i, res, c.consumeTimeMin(), c.consumeTimeMax(),
                    target, fixedType);
            consumers.add(co);
            threads.add(newThread(co, co.getName()));
        }

        active = true;
        paused = false;
        state = "En marcha";

        // 4. Arrancamos todos los hilos
        for (Thread t : threads) {
            t.start();
        }

        // 5. Hilo vigilante: hace join() de todos. Cuando join() vuelve para todos,
        //    sabemos que han terminado su trabajo (si tenían recursos limitados).
        watcher = newThread(new Watcher(this, List.copyOf(threads)), "Vigilante");
        watcher.start();
    }

    /** Lo llama el Watcher cuando todos los hilos han terminado. */
    void onAllFinished() {
        active = false;
        paused = false;
        state = "Finalizado";
    }

    @Override
    public void pause() {
        if (!active) return;
        paused = true;
        state = "En pausa";
        for (Producer p : producers) p.setPaused(true);
        for (Consumer c : consumers) c.setPaused(true);
    }

    @Override
    public void resume() {
        if (!active) return;
        paused = false;
        state = "En marcha";
        for (Producer p : producers) p.setPaused(false);
        for (Consumer c : consumers) c.setPaused(false);
    }

    @Override
    public synchronized void stop() {
        if (!active) return;

        Thread w = watcher;
        if (w != null) w.interrupt();          // primero el vigilante, para que no ponga "Finalizado"

        for (Thread t : threads) {
            t.interrupt();                     // salta InterruptedException en su sleep / wait
        }

        // join(): esperamos a que cada hilo termine de verdad (máx. 500 ms cada uno
        // para no congelar la ventana, que es quien llama a este método)
        try {
            for (Thread t : threads) {
                t.join(500);
            }
            if (w != null) w.join(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        active = false;
        paused = false;
        state = "Detenido";
        // No borramos las listas: así los datos finales siguen en pantalla
    }

    private Thread newThread(Runnable r, String name) {
        Thread t = new Thread(r, name);
        t.setDaemon(true);   // al cerrar la ventana el programa termina
        return t;
    }

    private static int randomBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    // =====================================================================
    // CONSULTAS
    // =====================================================================

    @Override public String getState() { return state; }
    @Override public boolean isActive() { return active; }
    @Override public boolean isPaused() { return paused; }

    @Override
    public int getTotalStock() {
        int total = 0;
        for (Resource r : resources) total += r.getStock();
        return total;
    }

    @Override
    public int getTotalProduced() {
        int total = 0;
        for (Producer p : producers) total += p.getProduced();
        return total;
    }

    @Override
    public int getTotalConsumed() {
        int total = 0;
        for (Consumer c : consumers) total += c.getConsumed();
        return total;
    }

    @Override
    public List<String> getResourcesInfo() {
        List<String> info = new ArrayList<>();
        for (Resource r : resources) info.add(r.getInfo());
        return info;
    }

    @Override
    public List<String> getThreadsInfo() {
        List<String> info = new ArrayList<>();
        for (Producer p : producers) {
            info.add(String.format("%-14s producidos: %-9s %s",
                    p.getName(), progress(p.getProduced(), p.getTarget()), p.getState()));
        }
        for (Consumer c : consumers) {
            info.add(String.format("%-14s consumidos: %-9s %s",
                    c.getName(), progress(c.getConsumed(), c.getTarget()), c.getState()));
        }
        return info;
    }

    /** "12" si es ilimitado, "12 / 20" si tiene objetivo. */
    private static String progress(int done, int target) {
        return target < 0 ? String.valueOf(done) : done + " / " + target;
    }
}
