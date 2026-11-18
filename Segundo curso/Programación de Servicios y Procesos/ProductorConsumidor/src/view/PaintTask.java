package view;

import java.util.List;

/**
 * Una "foto" de los datos y la orden de pintarlos.
 * El hilo de la Vista la crea y se la pasa a SwingUtilities.invokeLater(),
 * que ejecuta su run() en el hilo de Swing (el único que puede tocar los componentes).
 */
public class PaintTask implements Runnable {

    private final ControlPanel controlPanel;
    private final DetailPanel detailPanel;

    private final boolean active;
    private final boolean paused;
    private final String state;
    private final int stock;
    private final int produced;
    private final int consumed;
    private final List<String> resources;
    private final List<String> threads;

    public PaintTask(ControlPanel controlPanel, DetailPanel detailPanel,
                     boolean active, boolean paused, String state,
                     int stock, int produced, int consumed,
                     List<String> resources, List<String> threads) {
        this.controlPanel = controlPanel;
        this.detailPanel = detailPanel;
        this.active = active;
        this.paused = paused;
        this.state = state;
        this.stock = stock;
        this.produced = produced;
        this.consumed = consumed;
        this.resources = resources;
        this.threads = threads;
    }

    @Override
    public void run() {
        controlPanel.setStatus(active, paused);
        detailPanel.update(state, stock, produced, consumed, resources, threads);
    }
}
