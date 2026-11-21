import model.IModel;
import model.Model;
import presenter.IPresenter;
import presenter.Presenter;
import view.IView;
import view.Palette;
import view.View;

void main() {
    IModel model = new Model();
    IView view = new View(Palette.createPalette());
    IPresenter presenter = new Presenter(model, view);
    presenter.start();
}
