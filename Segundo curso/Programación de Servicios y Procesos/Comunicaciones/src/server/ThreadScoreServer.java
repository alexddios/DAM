package server;

import model.HighScores;

import java.net.ServerSocket;
import java.net.Socket;

public class ThreadScoreServer {
    private final HighScores hs;
    private final int PORT = 1234;
    public ThreadScoreServer(){
        hs = new HighScores();
        try {
            ServerSocket serverSock = new ServerSocket(PORT);
            Socket clientSock;
            while (true){
                System.out.println("Esperando al cliente...");
                clientSock = serverSock.accept();
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
