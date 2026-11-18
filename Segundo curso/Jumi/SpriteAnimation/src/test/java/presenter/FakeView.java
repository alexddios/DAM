package presenter;

import contract.PresenterContract;
import contract.ViewContract;

/** Una vista falsa: cumple el contrato sin abrir ninguna ventana. */
class FakeView implements ViewContract {

    int fps = 60;
    boolean started;
    PresenterContract presenter;

    @Override public void setPresenter(PresenterContract presenter) { this.presenter = presenter; }
    @Override public void start() { this.started = true; }
    @Override public int renderFps() { return this.fps; }
    @Override public void setRenderFps(int fps) { this.fps = fps; }
}
