package view;

import presenter.IPresenter;

import javax.swing.*;
import java.awt.*;

/**
 * Vista: la ventana. Contiene el lienzo (ViewPanel) arriba y los botones (ControlPanel) abajo.
 * Tiene su propio hilo, que cada FRAME_MS pide las imágenes al Presenter y se las pasa al lienzo.
 */
public class View implements IView,Runnable {
    private IPresenter presenter;
    private final JFrame frame;
    private final ViewPanel viewer;              // lienzo donde se pintan las animaciones
    private final ControlPanel controlPanel;     // botones Iniciar / Parar

    private final int FRAME_MS = 16;             // ~60 repintados por segundo (1000 / 60)

    public View() {
        viewer = new ViewPanel();
        viewer.setPreferredSize(new Dimension(1000, 500));
        controlPanel = new ControlPanel();

        frame = new JFrame("Animaciones Sprites");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // Lienzo: arriba, se queda con todo el espacio extra
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0;
        gc.weighty = 1.0;      // crece hacia abajo al agrandar la ventana
        gc.fill = GridBagConstraints.BOTH;
        p.add(viewer, gc);

        // Botones: abajo, solo la altura que necesitan
        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 1.0;
        gc.weighty = 0.0;      // no crece en altura
        gc.fill = GridBagConstraints.HORIZONTAL;
        p.add(controlPanel, gc);

        frame.add(p);
    }

    /** Hilo de la vista: pide los frames actuales y repinta, sin parar, mientras la ventana esté abierta. */
    @Override
    public void run() {
        while (true){
            viewer.setImages(presenter.getImages());
            try {
                Thread.sleep(FRAME_MS);
            } catch (InterruptedException e) {
                return; // si interrumpen el hilo, simplemente termina
            }
        }
    }

    /** Guarda el presenter y se lo pasa al panel de botones para sus listeners. */
    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter=presenter;
        controlPanel.setPresenter(presenter);
    }

    /** Muestra la ventana y arranca el hilo de repintado. */
    @Override
    public void start() {
        frame.pack();
        frame.setVisible(true);
        Thread thread = new Thread(this,"sprites-view");
        thread.setDaemon(true);   // daemon: no impide que el programa termine al cerrar la ventana
        thread.start();
    }
}
