package util;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Utilidad para leer un fichero de texto línea a línea.
 * Si el fichero no existe (por ejemplo, la primera vez que arranca el servidor),
 * leerLinea() devuelve null y cerrar() no hace nada, así que no salta ningún NullPointerException.
 */
public class FicheroEntrada {

    private final String nombre;
    private FileReader fichero;
    private BufferedReader lector;

    public FicheroEntrada(String nombre) {
        this.nombre = nombre;
    }

    public void abrir() {
        try {
            fichero = new FileReader(nombre);
            lector = new BufferedReader(fichero);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String leerLinea() {
        if (lector == null) {
            return null;
        }
        String res = null;
        try {
            res = lector.readLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public void cerrar() {
        if (lector == null) {
            return;
        }
        try {
            lector.close();
            fichero.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                fichero.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
