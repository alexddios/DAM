package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del modelo del cliente con un Socket TCP.
 * Se abre una sola conexión al empezar y se mantiene hasta cerrar la ventana.
 */
public class Model implements IModel{
    private static final int PORT = 1234;
    private final String host;   // IP o nombre del ordenador donde corre el servidor

    private Socket sock;
    private BufferedReader in;   // para leer líneas que envía el servidor
    private PrintWriter out;     // para enviar líneas al servidor

    public Model(String host) {
        this.host = host;
    }

    /**
     * Envía 'get', lee la respuesta "HIGH$$ n1 & p1 & n2 & p2 & ..." y la convierte en una lista.
     * Tras quitar "HIGH$$" y separar por '&', los nombres quedan en las posiciones pares
     * y los puntos en las impares, por eso el bucle avanza de dos en dos.
     * La condición i+1 < length evita salirse del array cuando la lista está vacía.
     */
    @Override
    public List<ScoreInfo> pedirPuntuaciones() throws IOException {
        comprobarConexion();
        List<ScoreInfo> res = new ArrayList<>();
        out.println("get");
        String linea = in.readLine();

        // null significa que el servidor ha cerrado la conexión
        if (linea == null) {
            throw new IOException("Se ha perdido la conexión con el servidor");
        }
        if (!linea.startsWith("HIGH$$")) {
            throw new IOException("Respuesta inesperada del servidor: " + linea);
        }
        String datos = linea.substring(6).trim();   // "HIGH$$" tiene 6 caracteres
        String [] trozos = datos.split("&");
        for (int i = 0; i+1 < trozos.length; i+=2){
            String nombre = trozos[i].trim();
            int puntos = Integer.parseInt(trozos[i+1].trim());
            res.add(new ScoreInfo(nombre,puntos));
        }
        return res;
    }


    /** Envía la puntuación; el toString de ScoreInfo ya da el formato "nombre & puntos & ". */
    @Override
    public void enviarPuntuacion(ScoreInfo score) throws IOException {
        comprobarConexion();
        out.println("score "+score);
    }

    /**
     * Crea el socket (aquí se conecta con el servidor) y monta los streams encima.
     * InputStreamReader convierte los bytes del socket en caracteres.
     * El 'true' del PrintWriter es el autoFlush: cada println se envía al momento.
     */
    @Override
    public void conectarse() throws IOException {
        sock = new Socket(host,PORT);
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new PrintWriter(sock.getOutputStream(),true);
    }

    /** Se despide del servidor con 'bye' y cierra el socket. Sin conexión no hay nada que cerrar. */
    @Override
    public void despedirse() throws IOException {
        if (out == null){
            return;
        }
        out.println("bye");
        sock.close();

    }

    /** Si la conexión falló al empezar, out es null: se avisa con una IOException en vez de un NullPointerException. */
    private void comprobarConexion() throws IOException{
        if (out == null) {
            throw new IOException("No hay conexión con el servidor");
        }
    }
}
