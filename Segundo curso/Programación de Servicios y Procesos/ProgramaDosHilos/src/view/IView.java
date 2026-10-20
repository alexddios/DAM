package view;

import presenter.IPresenter;

public interface IView {
    void setPresenter(IPresenter presenter);
    void updateCount(int currentCount);
    // Métodos futuros para controlar la simulación (Play, Stop, etc.)
}
