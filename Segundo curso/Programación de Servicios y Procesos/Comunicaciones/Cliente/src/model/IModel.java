package model;

import java.io.IOException;
import java.util.List;

/**
 * Modelo del cliente: la única parte que habla con el servidor y conoce el protocolo.
 * Los errores de red no se capturan aquí: suben como IOException y el presentador
 * decide qué mensaje ve el usuario.
 */
public interface IModel {
    /** Envía 'get' y devuelve las puntuaciones ya interpretadas. */
    List<ScoreInfo> pedirPuntuaciones() throws IOException;

    /** Envía 'score nombre & puntos &' con la puntuación indicada. */
    void enviarPuntuacion(ScoreInfo score) throws IOException;

    /** Abre el socket con el servidor y prepara los streams de entrada y salida. */
    void conectarse() throws IOException;

    /** Envía 'bye' y cierra la conexión. Si no había conexión, no hace nada. */
    void despedirse() throws IOException;
}
