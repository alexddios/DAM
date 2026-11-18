package model;

import java.util.List;

/**
 * Hilo vigilante: hace join() de todos los productores y consumidores.
 * Cuando join() ha vuelto para todos, es que todos han terminado su run(),
 * y avisa al Model para que ponga el estado "Finalizado".
 */
public class Watcher implements Runnable {

    private final Model model;
    private final List<Thread> threads;

    public Watcher(Model model, List<Thread> threads) {
        this.model = model;
        this.threads = threads;
    }

    @Override
    public void run() {
        try {
            for (Thread t : threads) {
                t.join();          // espero a que este hilo acabe su run()
            }
            model.onAllFinished();
        } catch (InterruptedException e) {
            // Nos han interrumpido desde stop(): no hacemos nada más
        }
    }
}
