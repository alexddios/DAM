import model.IModel;
import model.Model;
import presenter.IPresenter;
import presenter.Presenter;
import view.IView;
import view.View;

/**
 * Punto de entrada del cliente. Crea el modelo y la vista y se los inyecta al presentador:
 * ninguna clase crea sus propias dependencias.
 * Antes hay que arrancar ScoreServer o ThreadScoreServer.
 * La IP del servidor se pasa como argumento; sin argumento se usa localhost.
 */
public class Main {
    public static void main(String[] args) {
        String host = "localhost";
        if (args.length > 0) {
            host = args[0];
        }
        IModel model = new Model(host);
        IView view = new View();
        IPresenter presenter = new Presenter(model,view);
    }
}
