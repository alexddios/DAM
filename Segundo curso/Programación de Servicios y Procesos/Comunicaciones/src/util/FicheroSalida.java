package util;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Utilidad para escribir un fichero de texto línea a línea.
 * Captura ella misma las excepciones y las muestra por consola.
 */
public class FicheroSalida {

    private final String nombre;
    private FileWriter fichero;
    private BufferedWriter escritor;

    public FicheroSalida(String nombre) {
        this.nombre = nombre;
    }

    /** Abre el fichero. Con anadir = false se sobrescribe; con true se escribe al final. */
    public void abrir(boolean anadir) {
        try {
            fichero = new FileWriter(nombre, anadir);
            escritor = new BufferedWriter(fichero);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /** Escribe una línea y su salto de línea. */
    public void escribirLinea(String linea) {
        try {
            escritor.write(linea);
            escritor.newLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /** Cierra el fichero; cerrar el BufferedWriter vuelca lo que quede pendiente en el disco. */
    public void cerrar() {
        try {
            escritor.close();
            fichero.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                fichero.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
