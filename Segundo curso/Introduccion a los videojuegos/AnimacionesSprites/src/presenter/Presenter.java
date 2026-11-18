package presenter;

import model.IModel;
import model.SpriteAnimation;
import view.IView;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter: coordina modelo y vista. Es el único que habla con los dos.
 * Del modelo saca números (qué hoja usa cada animación y en qué frame va)
 * y se los pasa a la vista como AnimacionDTO; la vista es quien tiene las imágenes y pinta.
 */
public class Presenter implements IPresenter{
    private final IModel model;
    private final IView view;

    /** Recibe modelo y vista ya creados (los crea Main): no los crea él. */
    public Presenter(IModel model, IView view) {
        this.model = model;
        this.view = view;
    }

    /** Arranque del programa: conecta la vista, crea las animaciones y pone todo en marcha. */
    @Override
    public void start(){
        // 1. La vista necesita al presenter para pedirle qué pintar y para avisarle de los botones
        view.setPresenter(this);

        // 2. Crear las 10 animaciones: id de la hoja (registrada en el Catalogo) + ms entre frames.
        //    Varias usan la misma hoja con distinta velocidad: cada una va por su cuenta.

        // Pato x3
        model.crearAnimacion("Pato", 80);
        model.crearAnimacion("Pato", 120);
        model.crearAnimacion("Pato", 200);

        // Explosión x2
        model.crearAnimacion("Explosion", 100);
        model.crearAnimacion("Explosion", 150);

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

    /**
     * Lo que la vista tiene que pintar ahora: por cada animación, un DTO con su hoja y su frame actual.
     * Lee las SpriteAnimation del modelo y construye una lista nueva de DTOs; las animaciones no salen de aquí.
     */
    @Override
    public List<AnimacionDTO> getAnimaciones() {
        List<AnimacionDTO> dtos = new ArrayList<>();                    // la lista que se le da a la vista

        for (SpriteAnimation animacion : model.getAnimaciones()) {      // la lista del modelo
            dtos.add(new AnimacionDTO(animacion.getIdHoja(), animacion.getFrameActual()));
        }

        return dtos;
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
