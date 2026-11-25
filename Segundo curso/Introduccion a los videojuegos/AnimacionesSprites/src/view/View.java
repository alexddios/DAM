package view;

import assets.Catalogo;
import assets.HojaInfo;
import presenter.AnimacionDTO;
import presenter.DinamicObjectDTO;
import presenter.IPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Vista: la ventana. Contiene el lienzo (ViewPanel) arriba y los botones (ControlPanel) abajo.
 * Es la única parte del programa que tiene imágenes: al crearse carga las hojas del Catalogo.
 * Tiene su propio hilo, que cada FRAME_MS pide al Presenter qué hoja y qué frame toca de cada
 * animación, saca esas imágenes de sus hojas y se las pasa al lienzo.
 * También escucha los botones (ActionListener) y el teclado (KeyListener) y avisa al Presenter:
 * ← y → giran el objeto y ↑ enciende el motor.
 */
public class View implements IView,Runnable,ActionListener, KeyListener {
    // Comandos de los botones (public static para usarlos desde ControlPanel)
    public static final String PARAR = "BTN_PARAR";
    public static final String INICIAR = "BTN_INICIAR";

    private IPresenter presenter;
    private final JFrame frame;
    private final ViewPanel viewer;              // lienzo donde se pintan las animaciones
    private final ControlPanel controlPanel;     // botones Iniciar / Parar

    private Map<String, SpriteSheet> hojas;              // id → hoja ya cargada y recortada

    private final int FRAME_MS = 16;             // ~60 repintados por segundo (1000 / 60)

    // Qué flechas de giro están pulsadas ahora mismo. Hacen falta para saber qué hacer al soltar una:
    // si la contraria sigue pulsada hay que girar hacia ella, y si no, dejar de girar.
    // Solo las toca el hilo de la ventana (los eventos de teclado), así que no necesitan volatile.
    private boolean izquierdaPulsada;
    private boolean derechaPulsada;

    /** @param catalogo catálogo de hojas ya rellenado; lo crea Main y se lo pasa a la vista */
    public View(Catalogo catalogo) {
        // 1. Cargar todas las hojas del catálogo (una SpriteSheet por id), una sola vez al arrancar
        this.hojas = new HashMap<>();

        for (String id : catalogo.getAssetIds()) {                     // todos los ids del catálogo
            HojaInfo info = catalogo.get(id);                           // sus datos
            SpriteSheet hoja = new SpriteSheet(info.ruta(), info.columnas(), info.filas(), info.pasos());
            hojas.put(id, hoja);
        }

        // 2. Montar la ventana

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

    /**
     * Hilo de la vista: sin parar, mientras la ventana esté abierta, convierte lo que dice el Presenter
     * (hoja y frame de cada animación) en imágenes y se las pasa al lienzo para que repinte.
     */
    @Override
    public void run() {
        while (true) {
            List<BufferedImage> imagenes = new ArrayList<>();
            for (AnimacionDTO dto : presenter.getAnimaciones()) {          // hoja y frame de cada animación
                SpriteSheet hoja = hojas.get(dto.idHoja());                 // la hoja de esta animación
                imagenes.add(hoja.getFrame(dto.frame()));                   // el frame que toca
            }

            DinamicObjectDTO a = presenter.getDinamicObject();                      // el objeto, encima de todo
            SpriteSheet hojaDinamicObject = hojas.get(a.idImagen());        // la hoja de la imagen del objeto
            BufferedImage imagenDinamicObject = hojaDinamicObject.getFrame(0);   // una imagen suelta: solo tiene el frame 0
            viewer.setDinamicObject(imagenDinamicObject, a.x(), a.y(), a.angle());
            viewer.setImages(imagenes);
            try {
                Thread.sleep(FRAME_MS);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    /** Guarda el presenter y conecta los botones y el teclado con esta vista como listener. */
    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter=presenter;
        controlPanel.setActionListener(this);
        viewer.addKeyListener(this);   // el teclado se escucha en el lienzo, que es quien recibe el foco
    }

    /** Mira el actionCommand del botón pulsado y avisa al presenter. */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case PARAR -> presenter.stop();
            case INICIAR -> presenter.play();
        }
    }

    /** Muestra la ventana, da el foco al lienzo para que reciba las teclas y arranca el hilo de repintado. */
    @Override
    public void start() {
        frame.pack();
        frame.setVisible(true);
        viewer.requestFocusInWindow();
        Thread thread = new Thread(this,"sprites-view");
        thread.setDaemon(true);   // daemon: no impide que el programa termine al cerrar la ventana
        thread.start();
    }

    /** No se usa: las flechas no generan "tecla escrita", así que se trabaja con keyPressed y keyReleased. */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /** Pulsar una tecla: ← y → giran (y se apunta que están pulsadas) y ↑ enciende el motor. */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                izquierdaPulsada = true;
                presenter.girarIzquierda();
            }
            case KeyEvent.VK_RIGHT -> {
               derechaPulsada = true;
               presenter.girarDerecha();
            }
            case KeyEvent.VK_UP -> presenter.encenderMotor();
        }
    }

    /**
     * Soltar una tecla: ↑ apaga el motor. Al soltar una flecha de giro se mira la contraria:
     * si sigue pulsada se gira hacia ella, y si no, se deja de girar.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                izquierdaPulsada = false;
                if (derechaPulsada) {
                    presenter.girarDerecha();    // la derecha sigue pulsada
                } else {
                    presenter.dejarGirar();      // no queda ninguna flecha pulsada
                }
            }
            case KeyEvent.VK_RIGHT -> {
                derechaPulsada = false;
                if (izquierdaPulsada){
                    presenter.girarIzquierda();
                }else{
                    presenter.dejarGirar();
                }
            }
            case KeyEvent.VK_UP -> presenter.apagarMotor();
        }
    }
}
