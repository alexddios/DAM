package contract;

import java.util.List;

/** El contrato del presentador: lo que la vista puede pedirle. */
public interface PresenterContract {
    List<SpriteView> spriteViews();               // lo que hay que enseñar de cada sprite
    int spriteCount();                            // cuántos sprites hay
    void changeRenderSpeed(int delta);            // subir o bajar el ritmo de pintado
    void changeSpriteSpeed(int index, int delta); // lo mismo con la velocidad de un sprite
    void changeSpriteScale(int index, int delta); // agrandar o encoger un sprite
    void moveSprite(int index, int x, int y);     // llevar un sprite a otro sitio
    void createDuck(int x, int y);                // crear un pato con su centro en ese punto
    void start();                                 // poner en marcha la aplicación (lo llama main)
}
