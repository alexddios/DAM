package presenter;

import config.Config;
import config.Estado;
import config.EstadoHilo;
import config.ResourceInfo;

import java.util.List;

public interface IPresenter {
    void alIniciar(Config config);
    void alPausar();
    void alDetener();
    Estado getEstado();
    int getStockTotal();
    int getTotalProducido();
    int getTotalConsumido();
    List<ResourceInfo> resources();
    List<EstadoHilo> hilos();
}
