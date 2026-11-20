package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Model implements IModel{
    private static final int PORT = 1234;
    private static final  String HOST = "localhost";

    private Socket sock;
    private BufferedReader in;
    private PrintWriter out;

    @Override
    public List<ScoreInfo> pedirPuntuaciones() throws IOException {
        comprobarConexion();
        List<ScoreInfo> res = new ArrayList<>();
        out.println("get");
        String linea = in.readLine();

        if (linea == null) {
            throw new IOException("Se ha perdido la conexión con el servidor");
        }
        if (!linea.startsWith("HIGH$$")) {
            throw new IOException("Respuesta inesperada del servidor: " + linea);
        }
        String datos = linea.substring(6).trim();
        String [] trozos = datos.split("&");
        for (int i = 0; i+1 < trozos.length; i+=2){
            String nombre = trozos[i].trim();
            int puntos = Integer.parseInt(trozos[i+1].trim());
            res.add(new ScoreInfo(nombre,puntos));
        }
        return res;
    }


    @Override
    public void enviarPuntuacion(ScoreInfo score) throws IOException {
        comprobarConexion();
        out.println("score "+score);
    }

    @Override
    public void conectarse() throws IOException {
        sock = new Socket(HOST,PORT);
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new PrintWriter(sock.getOutputStream(),true);
    }

    @Override
    public void despedirse() throws IOException {
        if (out == null){
            return;
        }
        out.println("bye");
        sock.close();

    }
    private void comprobarConexion() throws IOException{
        if (out == null) {
            throw new IOException("No hay conexión con el servidor");
        }
    }
}
