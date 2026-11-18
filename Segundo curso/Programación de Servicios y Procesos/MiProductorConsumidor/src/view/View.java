package view;

import config.Estado;
import presenter.IPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Vista. Monta la ventana con los dos paneles y además es un hilo:
 * su run() refresca los datos cada 100 ms.
 * Solo conoce al Presenter a través de IPresenter.
 */
public class View implements IView,Runnable, ActionListener {
    private IPresenter presenter;
    private JFrame jFrame;
    private final ControlPanel controlPanel;
    private final DetailPanel detailPanel;

    public View(){
        this.controlPanel = new ControlPanel();
        this.detailPanel = new DetailPanel();
    }
    @Override
    public void start() {
        jFrame = new JFrame();
        jFrame.setTitle("View");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        // Panel de control a la izquierda, con su tamaño natural.
        c.gridx = 0; c.gridy = 0;
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        jFrame.add(controlPanel,c);

        // Panel de datos a la derecha, se lleva todo el espacio sobrante.
        c.gridx = 1;
        c.weightx = 1;
        jFrame.add(detailPanel,c);

        jFrame.setSize(900,600);
        jFrame.setVisible(true);
        // Hilo de refresco: ejecuta el run() de esta misma clase.
        // daemon para que no impida cerrar el programa.
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(jFrame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setAjustesEditables(boolean editables) {
        controlPanel.setAjustesEditables(editables);
    }

    /** Recibe el presenter y se registra a sí misma como listener de los botones y checkbox. */
    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
        controlPanel.setListener(this);
    }

    /**
     * Bucle de refresco (corre en su propio hilo, NO en el de Swing).
     * Pide los datos al presenter y NO toca los componentes directamente:
     * Swing no es seguro entre hilos, así que el pintado se encarga al hilo
     * de Swing (EDT) con invokeLater y una PaintTask.
     */
    @Override
    public void run() {
        try {
            while (true){
                Thread.sleep(100);
                Estado estado = presenter.getEstado();
                SwingUtilities.invokeLater(new PaintTask(this.detailPanel,
                        controlPanel,
                        estado,
                        presenter.getStockTotal(),
                        presenter.getTotalProducido(),
                        presenter.getTotalConsumido(),
                        presenter.getResources(),
                        presenter.getHilos()));
            }
        } catch (InterruptedException e) {
            System.out.println("El View se ha interrumpido");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            // El Config se lee en el momento de pulsar, con los valores actuales.
            case "INICIAR": presenter.alIniciar(controlPanel.getConfig()); break;
            case "PAUSAR":  presenter.alPausar(); break;
            case "DETENER": presenter.alDetener(); break;
            // Lógica solo de interfaz: no hace falta pasar por el Presenter.
            case "CAMPOS": controlPanel.actualizarCampos();
        }
    }
}
