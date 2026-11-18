package presenter;

import java.util.ArrayList;
import java.util.List;

import contract.ModelContract;
import contract.SpriteView;
import contract.PresenterContract;
import contract.ViewContract;

/** El presentador de MVP: traduce el modelo a lo que la vista pinta y aplica las reglas a cada gesto. */
public class Presenter implements PresenterContract {

    private final ModelContract model;
    private final ViewContract view;

    // Entre qué velocidades se mueve la rueda, la de pintado y la de los sprites. main los cambia con setFpsLimits.
    private int minFps = 1;
    private int maxFps = 120;

    // Entre qué escalas se mueve la rueda sobre un sprite. main los cambia con setScaleLimits.
    private int minScale = 1;
    private int maxScale = 8;

    // Cuántos sprites puede haber como mucho: con 12 el HUD cabe en el lienzo. main lo cambia con setMaxSprites.
    private int maxSprites = 12;

    public Presenter(ModelContract model, ViewContract view) {   // las recibe, no las crea
        this.model = model;
        this.view = view;

        view.setPresenter(this);   // se conecta con la vista: lo verás en «Conectar antes de arrancar»
    }

    /** Los límites de velocidad. No es del contrato: solo lo usa main, al montar la aplicación. */
    public void setFpsLimits(int min, int max) {
        this.minFps = min;
        this.maxFps = max;
    }

    /** Los límites de escala. No es del contrato: solo lo usa main, al montar la aplicación. */
    public void setScaleLimits(int min, int max) {
        this.minScale = min;
        this.maxScale = max;
    }

    /** El máximo de sprites. No es del contrato: solo lo usa main, al montar la aplicación. */
    public void setMaxSprites(int max) {
        this.maxSprites = max;
    }

    @Override
    public void start() {          // solo arranca
        this.model.start();
        this.view.start();
    }

    /** De cada sprite, solo lo que la vista enseña: su imagen, dónde va, y su nombre y velocidad. */
    @Override
    public List<SpriteView> spriteViews() {
        List<SpriteView> spriteViews = new ArrayList<>();
        for (int i = 0; i < this.model.spriteCount(); i++) {
            spriteViews.add(new SpriteView(this.model.frame(i), this.model.x(i), this.model.y(i),
                                     this.model.name(i), this.model.fps(i)));
        }

        return List.copyOf(spriteViews);   // una lista que nadie puede cambiar
    }

    @Override
    public int spriteCount() {
        return this.model.spriteCount();
    }

    @Override
    public void changeRenderSpeed(int delta) {
        this.view.setRenderFps(this.limit(this.view.renderFps() + delta, this.minFps, this.maxFps));
    }

    @Override
    public void changeSpriteSpeed(int index, int delta) {
        this.model.setFps(index, this.limit(this.model.fps(index) + delta, this.minFps, this.maxFps));
    }

    @Override
    public void changeSpriteScale(int index, int delta) {
        this.model.setScale(index, this.limit(this.model.scale(index) + delta, this.minScale, this.maxScale));
    }

    @Override
    public void moveSprite(int index, int x, int y) {
        this.model.moveTo(index, x, y);
    }

    /** La regla: si aún cabe otro sprite, se lo pide al modelo; si no, no hace nada. Cómo se hace un pato lo sabe el modelo. */
    @Override
    public void createDuck(int x, int y) {
        if (this.model.spriteCount() < this.maxSprites) {
            this.model.createDuck(x, y);
        }
    }

    /** Deja el valor entre min y max. */
    private int limit(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
}
