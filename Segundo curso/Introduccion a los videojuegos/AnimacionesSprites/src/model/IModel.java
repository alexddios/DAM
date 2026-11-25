package model;

import java.util.List;

/** Lo que el Presenter puede pedirle al modelo. */
public interface IModel {
    /** Crea una animación nueva que usa la hoja id del catálogo, con ms milisegundos entre frames. */
    void crearAnimacion(String id, int ms);

    /** Crea el objeto en la esquina de arriba a la izquierda, con su velocidad y su ángulo (grados). Empieza sin empuje y sin girar. */
    void crearDinamicObject(double vx, double vy, double angle);

    /** El objeto creado (null si todavía no hay). */
    DinamicObject getDinamicObject();

    /** Todas las animaciones creadas, en orden de creación. */
    List<SpriteAnimation> getAnimaciones();
}
