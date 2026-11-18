package util;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class FicheroSalida {

    private final String nombre;
    private FileWriter fichero;
    private BufferedWriter escritor;

    public FicheroSalida(String nombre) {
        this.nombre = nombre;
    }

    public void abrir(boolean anadir) {
        try {
            fichero = new FileWriter(nombre, anadir);
            escritor = new BufferedWriter(fichero);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void escribirLinea(String linea) {
        try {
            escritor.write(linea);
            escritor.newLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

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
