package model;

import java.util.List;

/** Lo que el Presenter puede pedirle al modelo. */
public interface IModel {
    /** Carga una hoja de sprites y la guarda con el identificador id. */
    void loadSS(String id, String ruta, int columnas, int filas,int pasos);

    /** Crea una animación nueva a partir de la hoja id, con ms milisegundos entre frames. */
    void crearAnimacion(String id, int ms);

    /** Todas las animaciones creadas, en orden de creación. */
    List<SpriteAnimation> getAnimaciones();
}
