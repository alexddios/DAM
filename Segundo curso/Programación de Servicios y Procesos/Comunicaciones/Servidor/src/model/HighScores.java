package model;

import util.FicheroEntrada;
import util.FicheroSalida;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista de las mejores puntuaciones del servidor (máximo 10), ordenada de mayor a menor.
 * Se carga de scores.txt al crearse y se guarda en ese fichero.
 *
 * En el servidor multihilo todos los hilos comparten el mismo objeto, por eso los
 * métodos que leen o modifican la lista son synchronized: así un hilo no puede
 * leer la lista mientras otro la está cambiando.
 */
public class HighScores {
    private final List<ScoreInfo> scores;
    private static final int MAX = 10;
    private static final String RUTA = "scores.txt";

    /** Crea la lista vacía y la rellena con lo que haya en el fichero. */
    public HighScores(){
        this.scores = new ArrayList<>();
        cargarScores();
    }

    /**
     * Inserta una puntuación en su posición para mantener el orden.
     * Busca la primera puntuación menor que la nueva; si no hay ninguna, va al final.
     * En caso de empate, la nueva queda detrás de las que ya estaban.
     * Si la lista pasa de MAX, se elimina la última (la más baja).
     */
    public synchronized void addScore(ScoreInfo score){
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

    /**
     * Añade una puntuación a partir de un texto "nombre & puntos &" (o "nombre&puntos").
     * Si el texto no tiene el formato correcto se ignora sin romper el servidor.
     * No hace falta synchronized: solo trocea el texto y llama al addScore anterior,
     * que es el que toca la lista.
     */
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

    /** Escribe la lista en scores.txt (sobrescribiéndolo), una puntuación por línea: nombre&puntos. */
    public synchronized void guardarScores(){
        FicheroSalida f = new FicheroSalida(RUTA);
        f.abrir(false);
        for (ScoreInfo s : scores){
            f.escribirLinea(s.nombre()+"&"+s.puntos());
        }
        f.cerrar();
    }

    /**
     * Lee scores.txt línea a línea (lectura anticipada) y añade cada puntuación.
     * Usa addScore(String) para reutilizar la conversión y ordenar aunque el fichero esté desordenado.
     * No es synchronized porque solo se llama desde el constructor, antes de que existan hilos.
     */
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

    /** Respuesta al mensaje 'get' del protocolo: "HIGH$$ nombre1 & puntos1 & ... ". */
    @Override
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder("HIGH$$ ");
        for (ScoreInfo s : scores){
            sb.append(s);
        }
        return sb.toString();
    }
}
