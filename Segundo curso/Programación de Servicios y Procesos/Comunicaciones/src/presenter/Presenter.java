package presenter;

import model.IModel;
import model.ScoreInfo;
import view.IView;

import java.io.IOException;
import java.util.List;

/**
 * Intermediario entre la vista y el modelo. Es el único que conoce a los dos.
 * Recibe las acciones del usuario, valida los datos, usa el modelo y le dice
 * a la vista qué mostrar. No depende de Swing: solo trabaja con IModel e IView.
 */
public class Presenter implements IPresenter{
    private final IView view;
    private final IModel model;

    /**
     * Guarda las dependencias, se presenta a la vista y se conecta al servidor.
     * Si la conexión falla, la aplicación sigue abierta y el usuario ve el aviso.
     */
    public Presenter(IModel model, IView view){
        this.view = view;
        this.model = model;
        this.view.setPresenter(this);
        try {
            model.conectarse();
        } catch (IOException e) {
            view.mostrarMensaje("No se puede conectar con el servidor. ¿Está arrancado?");
        }
    }

    /** Pide las puntuaciones al modelo y las convierte en texto numerado: "1. ana : 50". */
    @Override
    public void getScores() {
        try {
            List<ScoreInfo> scores = model.pedirPuntuaciones();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < scores.size(); i++) {
                ScoreInfo s = scores.get(i);
                sb.append(i + 1).append(". ")
                        .append(s.nombre()).append(" : ")
                        .append(s.puntos())
                        .append("\n");
            }
            view.mostrarPuntuaciones(sb.toString());
            if (scores.isEmpty()) {
                view.mostrarMensaje("La lista está vacia");
            }
        } catch (IOException e) {
            view.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Lee el nombre y la puntuación de la vista, los valida y envía la puntuación.
     * Cada validación que falla muestra un mensaje y sale con return.
     * El '&' está prohibido en el nombre porque es el separador del protocolo.
     * Después de enviar, refresca la lista.
     */
    @Override
    public void enviar() {
        String nombre = view.leerNombre().trim();
        String textoPuntuacion = view.leerPuntuacion();
        if (nombre.isBlank()){
            view.mostrarMensaje("Escribe un nombre");
            return;
        }
        if (nombre.contains("&")){
            view.mostrarMensaje("El nombre no puede contener '&'");
            return;
        }
        int puntuacion;
        try {
            puntuacion = Integer.parseInt(textoPuntuacion.trim());
        } catch (NumberFormatException e) {
            view.mostrarMensaje("La puntuación tiene que ser un número");
            return;
        }
        ScoreInfo s = new ScoreInfo(nombre,puntuacion);
        try {
            model.enviarPuntuacion(s);
            getScores();
        } catch (IOException e) {
            view.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Se despide del servidor antes de que se cierre la aplicación.
     * El error va a la consola porque la ventana está desapareciendo.
     */
    @Override
    public void cerrar() {
        try {
            model.despedirse();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
