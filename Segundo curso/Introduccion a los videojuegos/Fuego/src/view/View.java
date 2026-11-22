package view;

import config.Config;
import presenter.IPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// La View es solo la ventana: contiene el Viewer (canvas) y hace de puente con el Presenter
public class View implements IView,Runnable, ActionListener {
    private final ControlPanel controlPanel;
    private IPresenter presenter;

    private final JFrame frame;
    private final Viewer viewer;

    private final Render render;

    private final int FRAME_MS = 16;

    public static final String INICIAR = "BTN_INICIAR";
    public static final String PAUSAR = "BTN_PAUSAR";
    public static final String PARAR = "BTN_PARAR";
    public static final String APLICAR = "BTN_APLICAR";

    public View(Palette palette){
        viewer = new Viewer();
        controlPanel = new ControlPanel();

        frame = new JFrame("Fire Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 1;
        gc.gridy = 0;
        gc.gridheight = 2;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        p.add(viewer,gc);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridheight = 1;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.NORTH;
        p.add(controlPanel,gc);

        frame.add(p);

        this.render = new Render(palette);
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter=presenter;
        controlPanel.setActionListener(this);
    }

    @Override
    public void start() {
        int [][] temperatures = presenter.getTemperatures();

        //usamos first para settear la ventana
        viewer.setPreferredSize(new Dimension(temperatures[0].length,temperatures.length));
        //Frame - ventana principal
        frame.pack();
        frame.setVisible(true);

        //Hilos
        Thread thread = new Thread(this,"fire-view");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void run() {
        while (true){
            viewer.setImage(render.getImage(presenter.getTemperatures()));
            try {
                Thread.sleep(FRAME_MS);
            } catch (InterruptedException e) {
                return; // si interrumpen el hilo, simplemente termina
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case INICIAR -> presenter.iniciar();
            case PAUSAR -> presenter.pausar();
            case PARAR -> presenter.parar();
            case APLICAR -> presenter.aplicarConfig(controlPanel.getConfig());
        }
    }

}
