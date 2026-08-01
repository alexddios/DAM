package Ficheros;

import java.nio.file.Files;
import java.nio.file.Path;

public class GF {
    // Comprobar si un fichero o carpeta existe
    public static boolean existe(String ruta) {
        return Files.exists(Path.of(ruta));
    }

    // CREAR CARPETA
    public static void crearDirectorio(String ruta) {
        try {
            Path dir = Path.of(ruta);
            if (!Files.exists(dir)) {
                Files.createDirectory(dir); // Crea el directorio
                System.out.println("Carpeta creada correctamente.");
            }
        } catch (Exception ex) {
            System.out.println("Error al crear la carpeta: " + ex.getMessage());
        }
    }

    // CREAR FICHERO
    public static void crearFichero(String ruta) {
        try {
            Path archivo = Path.of(ruta);
            if (!Files.exists(archivo)) {
                Files.createFile(archivo); // Crea el archivo en blanco
                System.out.println("Fichero creado correctamente.");
            }
        } catch (Exception ex) {
            System.out.println("Error al crear el fichero: " + ex.getMessage());
        }
    }

    // Borrar fichero o carpeta (nota: las carpetas deben estar vacías para borrarse)
    public static void borrar(String ruta) {
        try {
            Files.deleteIfExists(Path.of(ruta));
            System.out.println("Elemento borrado correctamente.");
        } catch (Exception ex) {
            System.out.println("Error al borrar: " + ex);
        }
    }
}