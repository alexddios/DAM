package presenter;
import java.awt.image.BufferedImage;

public interface IPresenter {
    void start();
    // void play();
    //void puse();
//    void stop();
    BufferedImage getImage(); // devuelve imagen -> BufferedImage, clase que permite obtener la imagen
}