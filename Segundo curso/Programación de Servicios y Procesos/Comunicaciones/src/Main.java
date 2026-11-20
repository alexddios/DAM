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
 */
void main() {
    IModel model = new Model();
    IView view = new View();
    IPresenter presenter = new Presenter(model,view);

}
