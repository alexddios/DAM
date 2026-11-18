package presenter;

import config.Config;

import java.util.List;

/** Contrato del presenter: lo que la Vista puede pedirle. */
public interface IPresenter {

    void start();

    // Botones del panel de control
    void onStart(Config config);
    void onPause();
    void onResume();
    void onStop();

    // Datos para pintar
    String getState();
    boolean isActive();
    boolean isPaused();
    int getTotalStock();
    int getTotalProduced();
    int getTotalConsumed();
    List<String> getResourcesInfo();
    List<String> getThreadsInfo();
}
