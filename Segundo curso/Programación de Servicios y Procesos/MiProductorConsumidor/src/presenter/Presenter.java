package presenter;

import config.Config;
import config.Estado;
import config.HiloInfo;
import config.ResourceInfo;
import model.IModel;
import view.IView;

import java.util.List;

/**
 * Presenter: hace de puente entre la Vista y el Modelo.
 * Solo conoce las interfaces IView e IModel, nunca las clases concretas.
 * Decide qué hacer con cada botón y valida los ajustes.
 */
public class Presenter implements IPresenter{
    private final IView view;
    private final IModel model;

    public Presenter(IView view, IModel model) {
        this.view = view;
        this.model = model;
    }

    /**
     * Botón Iniciar. Primero valida: si hay error se avisa y no se toca nada.
     * En pausa reanuda; si no está en marcha, inicia y bloquea los ajustes.
     */
    @Override
    public void alIniciar(Config config) {
        String error = validar(config);
        if (error != null) {
            view.mostrarError(error);
            return;
        }
        if(model.getEstado()==Estado.EN_PAUSA){
            model.reanudar();

        }else if(model.getEstado() != Estado.EN_MARCHA){
            model.iniciar(config);
            view.setAjustesEditables(false);
        }

    }

    /** Botón Pausa: solo tiene sentido pausar algo que está en marcha. */
    @Override
    public void alPausar() {
        if (model.getEstado() == Estado.EN_MARCHA) {
            model.pausar();
        }
    }

    /** Botón Detener: interrumpe los hilos, espera a que terminen y desbloquea los ajustes. */
    @Override
    public void alDetener() {
        model.detener();
        view.setAjustesEditables(true);
    }

    @Override
    public Estado getEstado() {
        return model.getEstado();
    }

    @Override
    public int getStockTotal() {
        return model.getStock();
    }

    @Override
    public int getTotalProducido() {
        return model.getContadorProd();
    }

    @Override
    public int getTotalConsumido() {
        return model.getContadorCon();
    }

    @Override
    public List<ResourceInfo> getResources() {
        return model.getResources();
    }

    @Override
    public List<HiloInfo> getHilos() {
        return model.getHilos();
    }
    /**
     * Comprueba los rangos. Devuelve el mensaje de error, o null si todo está bien.
     * Los rangos que no se usan (ilimitado, sin control de stock) no se validan.
     */
    private String validar(Config c){
        if (c.tiempoProducirMin() > c.tiempoProducirMax()){
            return "El tiempo mínimo de producir no puede ser mayor que el máximo";
        }if (c.tiempoConsumirMin() > c.tiempoConsumirMax()){
            return "El tiempo mínimo de consumir no puede ser mayor que el máximo";
        }
        if(!c.producirIlimitado() && c.producirMin() > c.producirMax()){
            return "El mínimo de producir no puede ser mayor que el máximo";
        } if(!c.consumirIlimitado() && c.consumirMin() > c.consumirMax()){
            return "El mínimo de consumir no puede ser mayor que el máximo";
        }
        if (c.controlStock() && c.stockMin() >= c.stockMax()){
            return "El stock mínimo no puede ser mayor o igual que el máximo";
        }

        return null;
    }

}
