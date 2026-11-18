package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import contract.SpriteView;
import contract.PresenterContract;
import contract.ViewContract;

/**
 * La vista de MVP: es la ventana. Pinta lo que le da el presentador y le avisa de lo que hace el usuario.
 * No toma decisiones. De cada sprite solo recibe un SpriteView: lo que tiene que enseñar.
 *
 * La usan tres hilos: el de main, que la construye; el de pintado (renderLoop);
 * y el de eventos de Swing, que trae el ratón y el cierre de la ventana.
 */
public class View extends JFrame implements ViewContract {

    private static final Color BACKGROUND = new Color(0x1e2a30);

    private final Viewer viewer;
    private final Hud hud = new Hud();   // el recuadro de las velocidades: lo pinta él
    private final MouseHandler mouse = new MouseHandler(this.hud);   // el que escucha el ratón
    private final Target target = new Target();   // la diana sobre el sprite bajo el ratón
    private PresenterContract presenter;

    // Lo escribe el hilo de eventos (rueda) y lo lee el de pintado: volatile.
    private volatile int renderFps = 60;

    public View() {
        super("SpriteAnimation");   // el título de la ventana
        this.viewer = new Viewer();   // parte interna de la vista: la crea ella
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);   // al pulsar la X, termina la aplicación
        this.add(this.viewer);
        this.setResizable(false);

        this.viewer.addMouseListener(this.mouse);
        this.viewer.addMouseMotionListener(this.mouse);
        this.viewer.addMouseWheelListener(this.mouse);

        // El tamaño y la velocidad de pintado los pone main, con setCanvasSize y setRenderFps.
        // La ventana aún no se ve: la muestra start(), cuando todo está conectado.
    }

    /** El tamaño del lienzo. No es del contrato: solo lo usa main, al montar la aplicación. */
    public void setCanvasSize(int width, int height) {
        this.viewer.setPreferredSize(new Dimension(width, height));
        this.pack();                        // la ventana se ajusta al lienzo
        this.setLocationRelativeTo(null);   // centrada en la pantalla
    }

    @Override
    public void setPresenter(PresenterContract presenter) {
        this.presenter = presenter;   // solo guarda la referencia
        this.mouse.setPresenter(presenter);   // y se la pasa al que escucha el ratón
    }

    @Override
    public int renderFps() {
        return this.renderFps;
    }

    @Override
    public void setRenderFps(int fps) {
        this.renderFps = fps;
    }

    @Override
    public void start() {
        // Lo que toca la ventana debe hacerlo el hilo de eventos de Swing.
        // invokeLater le pasa el método showWindow; él lo hará en cuanto pueda.
        SwingUtilities.invokeLater(this::showWindow);
    }

    /** Lo hace el hilo de eventos de Swing: enseña la ventana y arranca el hilo de pintado. */
    private void showWindow() {
        this.setVisible(true);
        this.viewer.createBufferStrategy(2);   // dos búferes: se pinta en uno mientras se ve el otro

        // Ya hay donde pintar: arranca el hilo de pintado.
        Thread renderThread = new Thread(this::renderLoop, "Render");
        renderThread.start();
    }

    // ---------------------------------------------------------------- pintado

    /** El hilo de pintado: en cada vuelta pide qué pintar, pinta y espera hasta la siguiente. */
    private void renderLoop() {
        BufferStrategy buffers = this.viewer.getBufferStrategy();

        while (true) {
            long start = System.nanoTime();

            List<SpriteView> spriteViews = this.presenter.spriteViews();     // qué enseñar
            Graphics2D g = (Graphics2D) buffers.getDrawGraphics();   // el búfer que no se ve
            this.paintScene(g, spriteViews);         // las usa y las suelta: no las guarda
            g.dispose();
            buffers.show();                       // ahora se ve lo que acabamos de pintar
            Toolkit.getDefaultToolkit().sync();   // en algunos sistemas, que se vea ya

            // Espera lo que falte para que cada vuelta dure 1/renderFps segundos.
            long periodNs = 1_000_000_000L / this.renderFps;
            long elapsedNs = System.nanoTime() - start;
            long waitMs = (periodNs - elapsedNs) / 1_000_000;
            if (waitMs > 0) {
                try {
                    Thread.sleep(waitMs);
                } catch (InterruptedException e) {
                    return;   // nadie interrumpe este hilo; si lo hiciera, terminaría
                }
            }
        }
    }

    private void paintScene(Graphics2D g, List<SpriteView> spriteViews) {
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, this.viewer.getWidth(), this.viewer.getHeight());

        for (SpriteView spriteView : spriteViews) {
            g.drawImage(spriteView.image(), spriteView.x(), spriteView.y(), null);
        }

        // La diana, encima del sprite que hay bajo el ratón (si el ratón está en el lienzo y hay alguno).
        int mouseX = this.mouse.mouseX();
        int mouseY = this.mouse.mouseY();
        if (mouseX >= 0) {
            int hovered = this.mouse.spriteAt(spriteViews, mouseX, mouseY);
            if (hovered >= 0) {
                this.target.paint(g, spriteViews.get(hovered));
            }
        }

        this.hud.paint(g, this.renderFps, spriteViews, mouseX, mouseY);
    }
}
