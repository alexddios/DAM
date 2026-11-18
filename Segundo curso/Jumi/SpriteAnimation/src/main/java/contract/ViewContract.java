package contract;

/** El contrato de la vista: lo que el presentador puede pedirle. */
public interface ViewContract {
    void setPresenter(PresenterContract presenter);  // a quién pedirle qué pintar
    void start();                                    // empezar a pintar
    int renderFps();                                 // cuántas veces por segundo pinta
    void setRenderFps(int fps);                      // cambiar ese ritmo
}
