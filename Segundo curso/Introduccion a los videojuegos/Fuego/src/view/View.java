package view;

import presenter.IPresenter;

import javax.swing.*;
import java.awt.Dimension;

// La View es solo la ventana: contiene el Viewer (canvas) y hace de puente con el Presenter
public class View implements IView,Runnable{
    private IPresenter presenter;

    private final JFrame frame;
    private final Viewer viewer;

    private final Render render;

    private final int FRAME_MS = 16;

    public View(Palette palette){
        viewer = new Viewer();

        frame = new JFrame("Fire Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(viewer); // mete el canvas dentro de la ventana
        this.render = new Render(palette);
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter=presenter;
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
}
