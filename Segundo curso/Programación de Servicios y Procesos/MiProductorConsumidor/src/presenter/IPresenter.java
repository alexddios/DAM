package presenter;

import config.*;

import java.util.List;

public interface IPresenter {
    void alIniciar(Config config);
    void alPausar();
    void alDetener();
    Estado getEstado();
    int getStockTotal();
    int getTotalProducido();
    int getTotalConsumido();
    List<ResourceInfo> getResources();
    List<HiloInfo> getHilos();
}
