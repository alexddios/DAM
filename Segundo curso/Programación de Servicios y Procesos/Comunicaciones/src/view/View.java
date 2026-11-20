package view;

import presenter.IPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class View implements IView, WindowListener, ActionListener {
    private IPresenter presenter;
    private final JFrame jFrame;
    private final ControlPanel controlPanel;
    private final DataPanel dataPanel;

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

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
        controlPanel.setActionListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "enviar" : presenter.enviar(); break;
            case "get" : presenter.getScores(); break;
        }
    }
}
