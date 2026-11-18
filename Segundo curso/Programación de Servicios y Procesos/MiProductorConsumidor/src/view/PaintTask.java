package view;

import config.Estado;
import config.HiloInfo;
import config.ResourceInfo;

import java.util.List;

/**
 * Tarea de pintado. La crea el hilo de refresco de la Vista y se la pasa a
 * SwingUtilities.invokeLater(), que la mete en la cola del hilo de Swing (EDT).
 * Así los componentes solo los toca el EDT.
 * Es una clase y no una lambda porque el enunciado no permite lambdas.
 */
public class PaintTask implements Runnable{
    // Datos ya leídos por el hilo de refresco: la tarea solo los pinta.
    private final DetailPanel detailPanel;
    private final Estado estado;
    private final int stockTotal;
    private final int producido;
    private final int consumido;
    private final List<ResourceInfo> resources;
    private final  List<HiloInfo> hilos;
    private final ControlPanel controlPanel;


    public PaintTask(DetailPanel detailPanel,
                     ControlPanel controlPanel,
                     Estado estado,
                     int stockTotal,
                     int producido,
                     int consumido,
                     List<ResourceInfo> resources,
                     List<HiloInfo> hilos){
        this.detailPanel = detailPanel;
        this.controlPanel = controlPanel;
        this.estado = estado;
        this.stockTotal = stockTotal;
        this.producido = producido;
        this.consumido=consumido;
        this.resources = resources;
        this.hilos = hilos;
    }

    /** Se ejecuta en el hilo de Swing (EDT). */
    @Override
    public void run() {
        this.detailPanel.actualizar(this.estado,stockTotal,producido,consumido,resources,hilos);
        // Al finalizar nadie pulsa ningún botón, así que es el refresco quien
        // se entera y vuelve a dejar editar los ajustes.
        if(estado == Estado.FINALIZADO){
            controlPanel.setAjustesEditables(true);
        }

    }
}
