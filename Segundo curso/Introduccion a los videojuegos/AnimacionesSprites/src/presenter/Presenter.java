package presenter;

import model.IModel;
import model.SpriteAnimation;
import view.IView;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Presenter: coordina modelo y vista. Es el único que habla con el modelo;
 * la vista solo recibe imágenes ya recortadas.
 */
public class Presenter implements IPresenter{
    private final IModel model;
    private final IView view;

    public Presenter(IModel model, IView view) {
        this.model = model;
        this.view = view;
    }

    /** Arranque del programa: carga todos los assets al principio y pone todo en marcha. */
    @Override
    public void start(){
        // La vista necesita al presenter para pedirle imágenes y para los botones
        view.setPresenter(this);

        // 1. Cargar las 4 hojas: id, archivo, columnas, filas, pasos
        model.loadSS("Pajaro","res/Bird.png",4,3,10);
        model.loadSS("Vortice","res/ColorSwirl.png",4,2,7);
        model.loadSS("Fuego","res/Flame.png",4,4,14);
        model.loadSS("Mujer","res/mujer.png",12,1,12);

        // 2. Crear las 10 animaciones: id de la hoja + ms entre frames.
        //    Varias usan la misma hoja con distinta velocidad: cada una va por su cuenta.

        // Pájaro x3
        model.crearAnimacion("Pajaro", 80);
        model.crearAnimacion("Pajaro", 120);
        model.crearAnimacion("Pajaro", 200);

        // Vórtice x2
        model.crearAnimacion("Vortice", 100);
        model.crearAnimacion("Vortice", 150);

        // Fuego x3
        model.crearAnimacion("Fuego", 50);
        model.crearAnimacion("Fuego", 80);
        model.crearAnimacion("Fuego", 120);

        // Mujer x2
        model.crearAnimacion("Mujer", 150);
        model.crearAnimacion("Mujer", 250);

        // 3. Mostrar la ventana y arrancar las animaciones.
        //    Va después de crearlas: el hilo de la vista ya recorre la lista de animaciones.
        view.start();
        play();
    }

    /** Recorre las animaciones y devuelve una lista nueva con el frame actual de cada una. */
    @Override
    public List<BufferedImage> getImages() {
        List<BufferedImage> imagenes = new ArrayList<>();

        for (SpriteAnimation animacion : model.getAnimaciones()) {
            imagenes.add(animacion.getImage());
        }

        return imagenes;
    }

    /** Botón Iniciar: arranca el hilo de cada animación. */
    @Override
    public void play() {
        for (SpriteAnimation animacion : model.getAnimaciones()) {
            animacion.start();
        }
    }

    /** Botón Parar: detiene el hilo de cada animación (se quedan congeladas en su frame). */
    @Override
    public void stop() {
        for (SpriteAnimation animacion : model.getAnimaciones()) {
            animacion.stop();
        }
    }
}
