package presenter;

import config.Estado;
import model.IModel;
import view.IView;

public class Presenter implements IPresenter{
    private final IModel model;
    private final IView view;

    public Presenter(IModel model, IView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void start(){
        view.setPresenter(this);
        model.start();
        view.start();
    }

    @Override
    public void iniciar() {
        model.iniciar();
    }

    @Override
    public void pausar() {
        if(model.getEstado() == Estado.PARADO){
            return;
        }
        model.pausar();
    }

    @Override
    public void parar() {
        model.parar();
    }

    @Override
    public int[][] getTemperatures() {
        return model.getTemperatures();
    }
}