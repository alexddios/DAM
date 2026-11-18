package view;

import presenter.IPresenter;

/** Lo que el Presenter puede pedirle a la vista. */
public interface IView {
    void setPresenter(IPresenter presenter); // la vista necesita al presenter para preguntarle qué pintar y avisarle de los botones
    void start();                            // muestra la ventana y arranca el hilo de repintado
}
