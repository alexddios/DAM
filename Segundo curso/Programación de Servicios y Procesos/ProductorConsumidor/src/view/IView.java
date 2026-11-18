package view;

import presenter.IPresenter;

/** Contrato de la vista. */
public interface IView {

    void setPresenter(IPresenter presenter);

    void start();
}
