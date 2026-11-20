package model;

import java.io.IOException;
import java.util.List;

public interface IModel {
    List<ScoreInfo> pedirPuntuaciones() throws IOException;
    void enviarPuntuacion(ScoreInfo score) throws IOException;
    void conectarse() throws IOException;
    void despedirse() throws IOException;
}