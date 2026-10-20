package presenter;

import model.IModel;
import view.IView;

// MyPresenter.java
public class MyPresenter implements IPresenter {
    private IView view;
    private IModel model;

    public MyPresenter(IView view, IModel model) {
        this.view = view;
        this.model = model;

        // Conectamos bidireccionalmente
        this.view.setPresenter(this);
        this.model.setPresenter(this);
    }

    @Override
    public void onSimulationStarted() {
        model.startSimulation();
    }

    @Override
    public void updateCounter(int value) {
        view.updateCount(value);
    }
}