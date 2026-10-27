package view;

import presenter.IPresenter;


public interface IView {
    void setPresenter(IPresenter presenter); // la vista necesita al presenter para pedirle la imagen
    void start();                            // muestra la ventana y arranca el Viewer
}
