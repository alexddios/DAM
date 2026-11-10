package model;

import config.Estado;

public class Watcher implements Runnable {
    private final Model model;

    public Watcher(Model model){
        this.model=model;
    }


    @Override
    public void run() {
        try {
            model.esperar();
            if (model.getEstado() != Estado.DETENIDO){
                model.setEstado(Estado.FINALIZADO);
                System.out.println("Simulación finalizada");
            }
        } catch (InterruptedException e) {
            System.out.printf("%s ha terminado %n",Thread.currentThread().getName());
        }
    }
}
