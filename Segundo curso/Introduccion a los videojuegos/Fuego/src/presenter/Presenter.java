package presenter;

import model.IModel;
import view.IView;

import java.awt.image.BufferedImage;

public class Presenter implements IPresenter{
    private final IModel model;
    private final IView view;

    public Presenter(IModel model, IView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void start(){
        view.setPresenter(this);
        model.start();
        view.start();
    }

    @Override
    public BufferedImage getImage() {
        return model.getImage();
    }
}