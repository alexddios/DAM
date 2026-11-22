package presenter;

import config.Config;
import config.Estado;

public interface IPresenter {
    void start();
    void iniciar();
    void pausar();
    void parar();
    int [][] getTemperatures();
    void aplicarConfig(Config config);
    Estado getEstado();
    int getPasos();
    Config getConfig();
}