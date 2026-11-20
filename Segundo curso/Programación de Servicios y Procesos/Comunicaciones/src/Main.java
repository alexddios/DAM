import model.IModel;
import model.Model;
import presenter.IPresenter;
import presenter.Presenter;
import view.IView;
import view.View;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    IModel model = new Model();
    IView view = new View();
    IPresenter presenter = new Presenter(model,view);

}
