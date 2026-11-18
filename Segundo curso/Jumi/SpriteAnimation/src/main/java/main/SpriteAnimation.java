package main;

import model.Model;
import presenter.Presenter;
import view.View;

/**
 * La clase principal: monta la aplicación y la arranca. Es el único sitio que crea las capas,
 * y donde se ve su configuración: los sprites, el lienzo, la velocidad de pintado, los límites de la rueda y cuántos sprites caben.
 */
public class SpriteAnimation {

    public static void main(String[] args) {
        Model model = new Model();
        // nombre, hoja, ancho y alto del fotograma, escala, fotogramas por segundo, x, y
        model.addSprite("Pato", "/sprites/pato.png", 16, 16, 4, 8, 120, 220);
        model.addSprite("Explosión", "/sprites/explosion.png", 48, 48, 3, 12, 360, 160);

        View view = new View();
        view.setCanvasSize(640, 400);         // ancho y alto del lienzo, en píxeles
        view.setRenderFps(60);                // al empezar, pinta 60 veces por segundo

        Presenter presenter = new Presenter(model, view);
        presenter.setFpsLimits(1, 120);       // la rueda no baja de 1 ni pasa de 120 por segundo
        presenter.setScaleLimits(1, 8);       // ni encoge un sprite por debajo de 1 ni lo amplía más de 8 veces
        presenter.setMaxSprites(12);          // Ctrl+clic crea patos hasta que haya 12 sprites

        presenter.start();
        // main termina aquí; la aplicación sigue en los hilos que ha arrancado start()
    }
}
