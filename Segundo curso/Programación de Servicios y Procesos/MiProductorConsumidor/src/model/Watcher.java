package model;

import config.Estado;

/**
 * Hilo vigilante. Espera con join() a que terminen todos los productores y
 * consumidores, y entonces pone el estado en FINALIZADO.
 * Va en un hilo aparte para que quien pulsa Iniciar (el hilo de Swing)
 * no se quede bloqueado esperando y la ventana no se congele.
 */
public class Watcher implements Runnable {
    private final Model model;

    public Watcher(Model model){
        this.model=model;
    }


    @Override
    public void run() {
        try {
            // join() de todos los hilos: vuelve cuando han terminado todos.
            model.esperar();
            // join() no sabe POR QUÉ han terminado. Si alguien pulsó Detener,
            // el estado ya es DETENIDO y no se toca; si no, han acabado su trabajo.
            if (model.getEstado() != Estado.DETENIDO){
                model.setEstado(Estado.FINALIZADO);
                System.out.println("Simulación finalizada");
            }
        } catch (InterruptedException e) {
            // Si interrumpen al vigilante, simplemente deja de vigilar.
            System.out.printf("%s ha terminado %n",Thread.currentThread().getName());
        }
    }
}
