package presenter;

import model.IModel;
import model.ScoreInfo;
import view.IView;

import java.io.IOException;
import java.util.List;

public class Presenter implements IPresenter{
    private final IView view;
    private final IModel model;

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

    @Override
    public void cerrar() {
        try {
            model.despedirse();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
