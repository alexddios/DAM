package server;

import model.HighScores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Hilo que atiende a un único cliente del servidor multihilo.
 * Hace lo mismo que el ScoreServer secuencial con un cliente, pero en su propio hilo.
 * Recibe el HighScores compartido, que está protegido con synchronized.
 */
public class ThreadScoreHandler implements Runnable{
    private final Socket socket;
    private final HighScores hs;

    public ThreadScoreHandler(Socket socket, HighScores hs){
        this.socket = socket;
        this.hs = hs;
    }

    /** Monta los streams, atiende al cliente hasta que se va, cierra y guarda las puntuaciones. */
    @Override
    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

            processClient(in,out);

            socket.close();
            hs.guardarScores();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /** Bucle de mensajes: termina con 'bye' o cuando readLine() devuelve null. */
    private void processClient(BufferedReader in, PrintWriter out) {
        String line;
        boolean done = false;
        try {
            while (!done) {
                if ((line = in.readLine()) == null)
                    done = true;
                else {
                    System.out.println("Client msg: " + line);
                    if (line.trim().equals("bye"))
                        done = true;
                    else
                        doRequest(line, out);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /** Atiende 'get' y 'score'; el resto de mensajes se ignora. */
    private void doRequest(String line, PrintWriter out) {
        if (line.trim().toLowerCase().equals("get")) {
            System.out.println("Processing 'get'");
            out.println(hs.toString());
        } else if ((line.length() >= 6) && // "score"
                (line.substring(0, 5).toLowerCase().equals("score"))) {
            System.out.println("Processing 'score'");
            hs.addScore(line.substring(5));
        } else
            System.out.println("Ignoring input line");
    }
}
