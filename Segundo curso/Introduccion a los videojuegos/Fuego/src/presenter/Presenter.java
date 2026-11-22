package presenter;

import config.Config;
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

    @Override
    public void aplicarConfig(Config config) {
        String error = validar(config);
        if (error != null){
            view.mostrarError(error);
            return;
        }
        model.aplicarConfig(config);
    }
    private String validar(Config c){
        if (c.chispas() < Config.CHISPAS_MIN || c.chispas()> Config.CHISPAS_MAX){
            return String.format("Las chispas no pueden ser menor a %d o mayor a %d",
                    Config.CHISPAS_MIN,Config.CHISPAS_MAX);
        }
        if (c.puntosFrios() < Config.PUNTO_FRIO_MIN || c.puntosFrios() > Config.PUNTO_FRIO_MAX){
            return String.format("Los puntos fríos no pueden ser menor a %d o mayor a %d",
                    Config.PUNTO_FRIO_MIN,Config.PUNTO_FRIO_MAX);
        }
        if (c.enfriamiento() < Config.ENFRIAMIENTO_MIN || c.enfriamiento()>Config.ENFRIAMIENTO_MAX){
            return String.format("El enfriamiento no puede ser menor a %d o mayor a %d",
                    Config.ENFRIAMIENTO_MIN,Config.ENFRIAMIENTO_MAX);
        }
        return null;
    }
}