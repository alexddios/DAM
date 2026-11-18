import assets.Catalogo;
import model.IModel;
import model.Modelo;
import presenter.IPresenter;
import presenter.Presenter;
import view.IView;
import view.View;

import javax.swing.*;

/**
 * Punto de entrada: crea todas las piezas y se las pasa a quien las necesita (inyección de dependencias).
 * Es el único sitio donde se configura el programa: qué hojas hay y cómo son.
 */
public class Main {
    public static void main(String[] args) {
        // Swing se crea y se maneja en su propio hilo (Event Dispatch Thread)
        SwingUtilities.invokeLater(
                () -> {
                    // 1. El catálogo: qué hojas hay. Va primero porque modelo y vista lo necesitan al crearse.
                    //    id, ruta, columnas, filas, pasos
                    Catalogo catalogo = new Catalogo();
                    catalogo.registrar("Explosion", "res/explosion.png", 8, 1, 8);
                    catalogo.registrar("Pato", "res/pato.png", 1, 4, 4);
                    catalogo.registrar("Fuego", "res/Flame.png", 4, 4, 14);
                    catalogo.registrar("Mujer", "res/mujer.png", 12, 1, 12);

                    // 2. El mismo catálogo para los dos: el modelo saca los pasos y la vista carga las imágenes
                    IModel model = new Modelo(catalogo);
                    IView view = new View(catalogo);

                    // 3. El presenter recibe modelo y vista y lo pone todo en marcha
                    IPresenter presenter = new Presenter(model,view);
                    presenter.start();
                }
        );
    }
}
