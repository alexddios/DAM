package model;

import util.FicheroEntrada;
import util.FicheroSalida;

import java.util.ArrayList;
import java.util.List;

public class HighScores {
    private final List<ScoreInfo> scores;
    private static final int MAX = 10;
    private static final String RUTA = "scores.txt";
    public HighScores(){
        this.scores = new ArrayList<>();
        cargarScores();
    }
    public void addScore(ScoreInfo score){
        int pos = scores.size();
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i).puntos() < score.puntos()){
                pos =i;
                break;
            }

        }
        scores.add(pos,score);
        if (scores.size()> MAX){
            scores.removeLast();
        }
    }
    public void addScore(String linea){
        try {
            String [] trozos = linea.split("&");
            String nombre = trozos[0].trim();
            int puntos = Integer.parseInt(trozos[1].trim());
            addScore(new ScoreInfo(nombre,puntos));
        }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            System.out.printf("Línea ignorada: %s%n",linea);
        }

    }
    public void guardarScores(){
        FicheroSalida f = new FicheroSalida(RUTA);
        f.abrir(false);
        for (ScoreInfo s : scores){
            f.escribirLinea(s.nombre()+"&"+s.puntos());
        }
        f.cerrar();
    }
    public void cargarScores(){
        FicheroEntrada f = new FicheroEntrada(RUTA);
        f.abrir();
        String linea = f.leerLinea();
        while (linea != null){
            addScore(linea);
            linea = f.leerLinea();
        }
        f.cerrar();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HIGH$$ ");
        for (ScoreInfo s : scores){
            sb.append(s);
        }
        return sb.toString();
    }
}
