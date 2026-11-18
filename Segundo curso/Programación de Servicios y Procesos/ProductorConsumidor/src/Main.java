import model.IModel;
import model.Model;
import presenter.IPresenter;
import presenter.Presenter;
import view.View;

/** Punto de entrada: construye Modelo, Vista y Presenter y arranca. */
public class Main {

    public static void main(String[] args) {
        IModel model = new Model();
        View view = new View();
        IPresenter presenter = new Presenter(model, view);
        presenter.start();
    }
}
