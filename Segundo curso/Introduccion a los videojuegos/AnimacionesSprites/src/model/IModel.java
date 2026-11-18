package model;

import java.util.List;

/** Lo que el Presenter puede pedirle al modelo. */
public interface IModel {
    /** Crea una animación nueva que usa la hoja id del catálogo, con ms milisegundos entre frames. */
    void crearAnimacion(String id, int ms);

    /** Todas las animaciones creadas, en orden de creación. */
    List<SpriteAnimation> getAnimaciones();
}
