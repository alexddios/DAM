package view;

import assets.Catalogo;
import assets.HojaInfo;
import presenter.AnimacionDTO;
import presenter.IPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * También escucha los botones (ActionListener) y avisa al Presenter.
 */
public class View implements IView,Runnable,ActionListener {
    // Comandos de los botones (public static para usarlos desde ControlPanel)
    public static final String PARAR = "BTN_PARAR";
    public static final String INICIAR = "BTN_INICIAR";

    private IPresenter presenter;
    private final JFrame frame;
    private final ViewPanel viewer;              // lienzo donde se pintan las animaciones
    private final ControlPanel controlPanel;     // botones Iniciar / Parar

    Map<String, SpriteSheet> hojas;              // id → hoja ya cargada y recortada

    private final int FRAME_MS = 16;             // ~60 repintados por segundo (1000 / 60)

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

            viewer.setImages(imagenes);
            try {
                Thread.sleep(FRAME_MS);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    /** Guarda el presenter y conecta los botones con esta vista como listener. */
    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter=presenter;
        controlPanel.setActionListener(this);
    }

    /** Mira el actionCommand del botón pulsado y avisa al presenter. */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case PARAR -> presenter.stop();
            case INICIAR -> presenter.play();
        }
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
