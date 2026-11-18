package view;

import presenter.IPresenter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * La ventana principal. También es un hilo (Runnable): su run() es un bucle
 * que cada 100 ms pide los datos al Presenter y los manda pintar.
 */
public class View extends JFrame implements IView, Runnable {

    private static final int REFRESH_MS = 100;

    private final ControlPanel controlPanel;
    private final DetailPanel detailPanel;

    // volatile: lo escribe el hilo de Swing y lo lee el hilo de la Vista
    private volatile IPresenter presenter;
    private Thread viewThread;

    public View() {
        super("Productor - Consumidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        controlPanel = new ControlPanel();
        detailPanel = new DetailPanel();
        detailPanel.setPreferredSize(new Dimension(620, 560));

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(6, 6, 6, 6);
        c.gridy = 0;
        c.weighty = 1;

        // Izquierda: panel de control (no se estira a lo ancho)
        c.gridx = 0;
        c.weightx = 0;
        add(controlPanel, c);

        // Derecha: datos (se lleva todo el espacio sobrante)
        c.gridx = 1;
        c.weightx = 1;
        add(detailPanel, c);
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
        controlPanel.setPresenter(presenter);
    }

    @Override
    public void start() {
        pack();
        setLocationRelativeTo(null);   // centrar
        setVisible(true);

        // Arrancamos el hilo de la Vista: ejecutará run()
        viewThread = new Thread(this, "Vista");
        viewThread.setDaemon(true);    // al cerrar la ventana el programa termina
        viewThread.start();
    }

    /** El hilo de la Vista: refresca la pantalla cada REFRESH_MS hasta que lo interrumpan. */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                refresh();
                Thread.sleep(REFRESH_MS);
            }
        } catch (InterruptedException e) {
            // Nos han pedido parar: termina el hilo
        }
    }

    /**
     * Se ejecuta en el hilo de la Vista.
     * 1. Pide los datos al Presenter (esto se puede hacer desde cualquier hilo).
     * 2. Los componentes de Swing solo se pueden tocar desde el hilo de Swing,
     *    así que el pintado se lo encargamos con SwingUtilities.invokeLater().
     */
    private void refresh() {
        IPresenter p = presenter;
        if (p == null) return;

        // 1. Recoger los datos (en el hilo de la Vista)
        boolean active = p.isActive();
        boolean paused = p.isPaused();
        String state = p.getState();
        int stock = p.getTotalStock();
        int produced = p.getTotalProduced();
        int consumed = p.getTotalConsumed();
        List<String> resources = p.getResourcesInfo();
        List<String> threads = p.getThreadsInfo();

        // 2. Pintarlos (en el hilo de Swing): PaintTask es un Runnable que hace el pintado
        SwingUtilities.invokeLater(new PaintTask(controlPanel, detailPanel,
                active, paused, state, stock, produced, consumed, resources, threads));
    }
}
