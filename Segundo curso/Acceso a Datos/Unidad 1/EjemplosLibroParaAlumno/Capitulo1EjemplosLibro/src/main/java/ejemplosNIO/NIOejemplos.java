package ejemplosNIO;

import java.nio.file.*;

public class NIOejemplos {
    public static void main(String[] args) {
        // Construcción de la ruta usando Paths
        Path ruta = Paths.get("C:", "datos", "Programacion", "fichero.txt");

        System.out.println("Ruta: " + ruta);

        // Comprobar si el archivo existe
        if (Files.exists(ruta)) {
            System.out.println("El archivo existe.");
        } else {
            System.out.println("El archivo no existe.");
        }
    }
}
