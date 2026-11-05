import model.IModel;
import model.Modelo;
import presenter.IPresenter;
import presenter.Presenter;
import view.IView;
import view.View;

import javax.swing.*;

/** Punto de entrada: crea modelo, vista y presenter (MVP) y arranca el programa. */
public class Main {
    public static void main(String[] args) {
        // Swing se crea y se maneja en su propio hilo (Event Dispatch Thread)
        SwingUtilities.invokeLater(
                () -> {
                    IModel model = new Modelo();
                    IView view = new View();
                    IPresenter presenter = new Presenter(model,view);
                    presenter.start();
                }
        );
    }
}
