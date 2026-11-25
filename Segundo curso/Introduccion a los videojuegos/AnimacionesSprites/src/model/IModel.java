package model;

import java.util.List;

/** Lo que el Presenter puede pedirle al modelo. */
public interface IModel {
    /** Crea una animación nueva que usa la hoja id del catálogo, con ms milisegundos entre frames. */
    void crearAnimacion(String id, int ms);

    /** Crea la nave en la esquina de arriba a la izquierda, con su velocidad y su aceleración. */
    void crearNave(double vx, double vy, double ax, double ay);

    /** La nave creado (null si todavía no hay). */
    Nave getNave();

    /** Todas las animaciones creadas, en orden de creación. */
    List<SpriteAnimation> getAnimaciones();
}
