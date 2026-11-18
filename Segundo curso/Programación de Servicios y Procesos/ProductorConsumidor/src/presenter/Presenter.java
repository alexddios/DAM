package presenter;

import config.Config;
import model.IModel;
import view.IView;

import java.util.List;

/** Puente entre la Vista y el Modelo. Casi todo es delegar. */
public class Presenter implements IPresenter {

    private final IModel model;
    private final IView view;

    public Presenter(IModel model, IView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void start() {
        view.setPresenter(this);
        view.start();
    }

    // ---------- Acciones ----------
    @Override public void onStart(Config config) { model.start(config); }
    @Override public void onPause() { model.pause(); }
    @Override public void onResume() { model.resume(); }
    @Override public void onStop() { model.stop(); }

    // ---------- Datos ----------
    @Override public String getState() { return model.getState(); }
    @Override public boolean isActive() { return model.isActive(); }
    @Override public boolean isPaused() { return model.isPaused(); }
    @Override public int getTotalStock() { return model.getTotalStock(); }
    @Override public int getTotalProduced() { return model.getTotalProduced(); }
    @Override public int getTotalConsumed() { return model.getTotalConsumed(); }
    @Override public List<String> getResourcesInfo() { return model.getResourcesInfo(); }
    @Override public List<String> getThreadsInfo() { return model.getThreadsInfo(); }
}
