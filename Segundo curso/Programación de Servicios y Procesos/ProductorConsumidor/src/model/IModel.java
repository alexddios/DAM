package model;

import config.Config;

import java.util.List;

/** Contrato del modelo. */
public interface IModel {

    void start(Config config);   // crea los hilos con esa configuración y los arranca
    void pause();
    void resume();
    void stop();                 // interrumpe los hilos y espera con join() a que terminen

    String getState();
    boolean isActive();          // hay simulación (en marcha o en pausa)
    boolean isPaused();

    int getTotalStock();
    int getTotalProduced();
    int getTotalConsumed();
    List<String> getResourcesInfo();
    List<String> getThreadsInfo();
}
