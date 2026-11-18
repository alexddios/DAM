package view;

import presenter.IPresenter;

public interface IView {
    void start();
    void mostrarError(String mensaje);
    void setAjustesEditables(boolean editables);
    void setPresenter(IPresenter presenter);

}
