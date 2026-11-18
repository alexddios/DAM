import model.IModel;
import model.Model;
import presenter.IPresenter;
import presenter.Presenter;
import view.IView;
import view.View;

import javax.swing.*;

void main() {
    SwingUtilities.invokeLater(
            () -> {
                IModel model = new Model();
                IView view = new View();
                IPresenter presenter = new Presenter(model,view);
                presenter.start();
            }
    );
}
