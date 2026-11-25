package presenter;

import java.util.List;

/** Lo que la vista puede pedirle al Presenter. */
public interface IPresenter {
    /** Crea las animaciones, muestra la ventana y las pone en marcha. */
    void start();

    /** Hoja y frame actual de cada animación, en orden. La vista lo pide en cada repintado. */
    List<AnimacionDTO> getAnimaciones();

    /** Id de la imagen y posición del objeto. La vista lo pide en cada repintado. */
    DinamicObjectDTO getDinamicObject();

    /** Arranca (o reanuda) todas las animaciones y el objeto. */
    void play();

    /** Para todas las animaciones y el objeto. */
    void stop();
}

