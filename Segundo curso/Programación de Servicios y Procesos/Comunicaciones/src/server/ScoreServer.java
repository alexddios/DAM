package server;

import model.HighScores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor secuencial de puntuaciones (capítulo 29 del libro).
 * Atiende a un solo cliente cada vez: mientras está en processClient() esperando
 * mensajes con readLine(), no vuelve a accept() y los demás clientes tienen que esperar.
 */
public class ScoreServer {
    private final HighScores hs;
    private static final int PORT = 1234;

    /**
     * Crea el ServerSocket (binding del puerto 1234) y entra en el bucle de clientes:
     * accept() bloquea hasta que llega uno y devuelve el Socket para hablar con él.
     */
    public ScoreServer() {
        hs = new HighScores();
        try {
            ServerSocket serverSock = new ServerSocket(PORT);
            Socket clientSock;
            BufferedReader in;
            PrintWriter out;

            while (true) {
                System.out.println("Esperando al cliente...");
                clientSock = serverSock.accept();
                System.out.printf("Conexión del cliente desde %s %n", clientSock.getInetAddress().getHostAddress());

                // Streams sobre el socket: leer líneas y escribir líneas (autoFlush = true)
                in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
                out = new PrintWriter(clientSock.getOutputStream(), true);
                processClient(in, out);

                clientSock.close();
                System.out.println("Conexión con el cliente cerrada");
                hs.guardarScores();   // copia de seguridad tras cada cliente, como en el libro
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    /**
     * Bucle de mensajes de un cliente. Termina si el cliente envía 'bye'
     * o si readLine() devuelve null (el cliente cerró la conexión sin despedirse).
     */
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

    /**
     * Atiende 'get' (responde con la lista) y 'score' (quita la palabra y añade la puntuación).
     * Cualquier otro mensaje se ignora sin romper el servidor.
     */
    private void doRequest(String line, PrintWriter out) {
        if (line.trim().toLowerCase().equals("get")) {
            System.out.println("Processing 'get'");
            out.println(hs.toString());
        } else if ((line.length() >= 6) && // "score"
                (line.substring(0, 5).toLowerCase().equals("score"))) {
            System.out.println("Processing 'score'");
            hs.addScore(line.substring(5));   // de "score ana & 50 &" queda " ana & 50 &"
        } else
            System.out.println("Ignoring input line");
    }

    static void main() {
        new ScoreServer();
    }
}
