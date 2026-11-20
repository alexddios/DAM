package view;


import presenter.IPresenter;

/**
 * Vista pasiva del cliente: muestra el texto que le da el presentador y le entrega
 * lo que escribe el usuario. No contiene lógica ni conoce sockets ni el protocolo,
 * y ningún método usa tipos de Swing.
 */
public interface IView {
    /** Muestra la lista de puntuaciones ya formateada por el presentador. */
    void mostrarPuntuaciones(String lista);

    /** Muestra un aviso o error decidido por el presentador. */
    void mostrarMensaje(String mensaje);

    /** Devuelve el nombre tal cual lo escribió el usuario. */
    String leerNombre();

    /** Devuelve la puntuación como texto; la conversión y validación las hace el presentador. */
    String leerPuntuacion();

    /** Recibe el presentador al que avisar de las acciones del usuario. */
    void setPresenter(IPresenter presenter);
}
