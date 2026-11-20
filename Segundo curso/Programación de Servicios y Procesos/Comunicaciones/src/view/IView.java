package view;


import presenter.IPresenter;

public interface IView {
    void mostrarPuntuaciones(String lista);
    void mostrarMensaje(String mensaje);
    String leerNombre();
    String leerPuntuacion();
    void setPresenter(IPresenter presenter);
}
