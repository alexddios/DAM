package presenter;

import java.util.List;

import check.Check;
import contract.SpriteView;

/**
 * Prueba el presentador sin ventana, con FakeView y FakeModel.
 * Es el ejercicio «Una prueba sin ventana» del documento, resuelto.
 */
public class PresenterTest {

    public static void main(String[] args) {
        System.out.println("PresenterTest (sin ventana)");
        connects();
        starts();
        renderSpeedStaysBetween1And120();
        spriteSpeedStaysBetween1And120();
        spriteScaleStaysBetween1And8();
        spriteViewsGiveWhatTheViewShows();
        moves();
        createsDucksUpToTheMaximum();
        Check.finish("PresenterTest");
    }

    static void connects() {
        FakeView view = new FakeView();
        Presenter presenter = new Presenter(new FakeModel(), view);
        Check.that("el constructor se presenta a la vista", view.presenter == presenter);
    }

    static void starts() {
        FakeModel model = new FakeModel();
        FakeView view = new FakeView();
        Presenter presenter = new Presenter(model, view);

        Check.that("antes de start() no hay nada arrancado", !model.started && !view.started);
        presenter.start();
        Check.that("start() arranca el modelo y la vista", model.started && view.started);
    }

    static void renderSpeedStaysBetween1And120() {
        FakeView view = new FakeView();
        Presenter presenter = new Presenter(new FakeModel(), view);
        presenter.setFpsLimits(1, 120);

        presenter.changeRenderSpeed(+1);
        Check.equal("la rueda sube el pintado de 60 a 61", 61, view.renderFps());
        presenter.changeRenderSpeed(-2);
        Check.equal("y lo baja a 59", 59, view.renderFps());
        presenter.changeRenderSpeed(+500);
        Check.equal("el pintado no pasa de 120", 120, view.renderFps());
        presenter.changeRenderSpeed(-500);
        Check.equal("el pintado no baja de 1", 1, view.renderFps());
    }

    static void spriteSpeedStaysBetween1And120() {
        FakeModel model = new FakeModel();
        Presenter presenter = new Presenter(model, new FakeView());
        presenter.setFpsLimits(1, 120);

        presenter.changeSpriteSpeed(0, +1);
        Check.equal("la rueda sube el pato de 8 a 9", 9, model.fps);
        presenter.changeSpriteSpeed(0, +500);
        Check.equal("el pato no pasa de 120", 120, model.fps);
        presenter.changeSpriteSpeed(0, -500);
        Check.equal("el pato no baja de 1", 1, model.fps);
    }

    static void spriteScaleStaysBetween1And8() {
        FakeModel model = new FakeModel();
        Presenter presenter = new Presenter(model, new FakeView());   // sin setScaleLimits: valen los de por defecto

        presenter.changeSpriteScale(0, +1);
        Check.equal("la rueda amplía el pato de 4 a 5", 5, model.scale);
        presenter.changeSpriteScale(0, +500);
        Check.equal("el pato no se amplía más de 8 veces", 8, model.scale);
        presenter.changeSpriteScale(0, -500);
        Check.equal("ni encoge por debajo de 1", 1, model.scale);

        presenter.setScaleLimits(2, 3);
        presenter.changeSpriteScale(0, +500);
        Check.equal("main puede cambiar los límites", 3, model.scale);
    }

    static void spriteViewsGiveWhatTheViewShows() {
        FakeModel model = new FakeModel();
        Presenter presenter = new Presenter(model, new FakeView());

        List<SpriteView> spriteViews = presenter.spriteViews();
        Check.equal("spriteViews() da un SpriteView por sprite", 1, spriteViews.size());
        Check.equal("y spriteCount() dice cuántos hay", 1, presenter.spriteCount());
        SpriteView duck = spriteViews.get(0);
        Check.that("con su imagen, posición, nombre y velocidad",
                   duck.image() == model.frame && duck.x() == 100 && duck.y() == 200
                   && duck.name().equals("Pato") && duck.fps() == 8);

        model.moveTo(0, 0, 0);
        Check.that("el SpriteView no cambia aunque cambie el modelo", duck.x() == 100 && duck.y() == 200);
    }

    static void createsDucksUpToTheMaximum() {
        FakeModel model = new FakeModel();
        Presenter presenter = new Presenter(model, new FakeView());
        presenter.setMaxSprites(3);

        presenter.createDuck(50, 60);
        Check.that("createDuck pide al modelo un pato en (50, 60)", model.duckX == 50 && model.duckY == 60);
        presenter.createDuck(70, 80);
        Check.equal("ya hay 3 sprites", 3, model.sprites);
        presenter.createDuck(90, 90);
        Check.that("con 3, el máximo, no pide más", model.sprites == 3 && model.duckX == 70);
    }

    static void moves() {
        FakeModel model = new FakeModel();
        Presenter presenter = new Presenter(model, new FakeView());

        presenter.moveSprite(0, 10, 20);
        Check.that("moveSprite lleva el pato a (10, 20)", model.x == 10 && model.y == 20);
        SpriteView duck = presenter.spriteViews().get(0);
        Check.that("y el siguiente SpriteView lo enseña allí", duck.x() == 10 && duck.y() == 20);
    }
}
