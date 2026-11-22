package presenter;

import config.Config;

public interface IPresenter {
    void start();
    void iniciar();
    void pausar();
    void parar();
    int [][] getTemperatures();
    void aplicarConfig(Config config);
}