package server;

import model.HighScores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadScoreHandler implements Runnable{
    private final Socket socket;
    private final HighScores hs;

    public ThreadScoreHandler(Socket socket, HighScores hs){
        this.socket = socket;
        this.hs = hs;
    }

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
}
