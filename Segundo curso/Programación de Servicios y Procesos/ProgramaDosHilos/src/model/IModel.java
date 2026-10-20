package model;

import presenter.IPresenter;

public interface IModel {
    void startSimulation();
    void setPresenter(IPresenter presenter);
}