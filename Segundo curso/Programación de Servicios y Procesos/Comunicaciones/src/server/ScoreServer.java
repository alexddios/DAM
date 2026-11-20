package server;

import model.HighScores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ScoreServer {
    private final HighScores hs;
    private static final int PORT = 1234;

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

                in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
                out = new PrintWriter(clientSock.getOutputStream(), true);
                processClient(in, out);

                clientSock.close();
                System.out.println("Conexión con el cliente cerrada");
                hs.guardarScores();
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

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

    static void main() {
        new ScoreServer();
    }
}

