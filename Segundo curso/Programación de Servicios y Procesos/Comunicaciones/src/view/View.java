package view;

import presenter.IPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Ventana del cliente. Monta el panel de datos y el panel de control,
 * reparte cada petición del presentador al panel que corresponde y
 * avisa al presentador de lo que hace el usuario (botón, Enter y cierre de la ventana).
 * Solo conoce al presentador a través de IPresenter.
 */
public class View implements IView, WindowListener, ActionListener {
    private IPresenter presenter;
    private final JFrame jFrame;
    private final ControlPanel controlPanel;
    private final DataPanel dataPanel;

    /**
     * Crea y muestra la ventana en el constructor, para que ya exista cuando el presentador
     * intente conectarse y quizá tenga que mostrar un mensaje de error.
     * Layout: el panel de datos arriba ocupando todo el espacio, el de control abajo.
     * EXIT_ON_CLOSE cierra la aplicación después de ejecutar windowClosing (que envía el 'bye').
     */
    public View() {
        this.controlPanel = new ControlPanel();
        this.dataPanel = new DataPanel();
        jFrame = new JFrame();
        jFrame.setTitle("High Score Client");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.addWindowListener(this);
        jFrame.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        jFrame.add(dataPanel, c);

        c.gridy = 1;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        jFrame.add(controlPanel, c);
        jFrame.setSize(300, 450);
        jFrame.setVisible(true);
    }

    @Override
    public void mostrarPuntuaciones(String lista) {
        dataPanel.ponerTexto(lista);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        dataPanel.ponerTexto(mensaje);
    }

    @Override
    public String leerNombre() {
        return controlPanel.getNombre();
    }

    @Override
    public String leerPuntuacion() {
        return controlPanel.getPuntuacion();
    }

    /** Guarda el presentador y se registra como listener del botón y del campo de la puntuación. */
    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
        controlPanel.setActionListener(this);
    }

    // WindowListener obliga a implementar todos sus métodos; solo se usa windowClosing.
    @Override
    public void windowOpened(WindowEvent e) {

    }

    /** Al cerrar la ventana, el presentador se despide del servidor antes de que termine la aplicación. */
    @Override
    public void windowClosing(WindowEvent e) {
        presenter.cerrar();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    /** Decide qué avisar al presentador según el actionCommand que puso ControlPanel. */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "enviar" : presenter.enviar(); break;
            case "get" : presenter.getScores(); break;
        }
    }
}
