package server;

import model.HighScores;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor multihilo: el hilo principal solo acepta conexiones (el "recepcionista")
 * y crea un hilo ThreadScoreHandler por cada cliente (los "camareros").
 * Así un cliente lento no bloquea a los demás.
 */
public class ThreadScoreServer {
    private final HighScores hs;
    private final int PORT = 1234;

    /**
     * Crea un único HighScores que compartirán todos los hilos y entra en el bucle de accept().
     * Nada más aceptar un cliente, le pasa su Socket a un hilo nuevo y vuelve a esperar.
     */
    public ThreadScoreServer(){
        hs = new HighScores();
        try {
            ServerSocket serverSock = new ServerSocket(PORT);
            Socket clientSock;
            while (true){
                System.out.println("Esperando al cliente...");
                clientSock = serverSock.accept();
                // start() crea un hilo nuevo que ejecuta run(); run() a secas se ejecutaría en este mismo hilo
                new Thread(new ThreadScoreHandler(clientSock,hs)).start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void main(String[] args) {
        new ThreadScoreServer();
    }
}
