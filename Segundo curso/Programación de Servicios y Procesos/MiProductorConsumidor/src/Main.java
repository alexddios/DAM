import model.IModel;
import model.Model;
import presenter.Presenter;
import view.IView;
import view.View;

void main(){
    IModel model = new Model();
    IView view = new View();
    Presenter presenter = new Presenter(view,model);
    view.setPresenter(presenter);
    view.start();
}