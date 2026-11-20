package presenter;

/**
 * Acciones del usuario que la vista comunica al presentador.
 * No reciben parámetros: la vista es pasiva y el presentador le pide los datos.
 */
public interface IPresenter {
    /** El usuario ha pulsado "Get Scores". */
    void getScores();

    /** El usuario ha pulsado Enter en el campo de la puntuación. */
    void enviar();

    /** El usuario está cerrando la ventana. */
    void cerrar();
}
