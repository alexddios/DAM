package model;

import java.util.List;

/** Lo que el Presenter puede pedirle al modelo. */
public interface IModel {
    /** Crea una animación nueva que usa la hoja id del catálogo, con ms milisegundos entre frames. */
    void crearAnimacion(String id, int ms);

    /** Crea el planeta en la esquina de arriba a la izquierda, con su velocidad y su aceleración. */
    void crearPlaneta(double vx, double vy, double ax, double ay, double angle, double angularSpeed, double angularAcc);

    /** El planeta creado (null si todavía no hay). */
    DinamicObject getPlaneta();

    /** Todas las animaciones creadas, en orden de creación. */
    List<SpriteAnimation> getAnimaciones();
}
