package presenter;

import java.awt.image.BufferedImage;
import java.util.List;

/** Lo que la vista puede pedirle al Presenter. */
public interface IPresenter {
    /** Carga los recursos, crea las animaciones y muestra la ventana. */
    void start();

    /** Frame actual de cada animación, en orden. La vista lo pide en cada repintado. */
    List<BufferedImage> getImages();

    /** Arranca (o reanuda) todas las animaciones. */
    void play();

    /** Para todas las animaciones. */
    void stop();
}
