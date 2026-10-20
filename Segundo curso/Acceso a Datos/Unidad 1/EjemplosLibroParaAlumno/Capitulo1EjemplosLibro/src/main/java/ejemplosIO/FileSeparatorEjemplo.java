package ejemplosIO;
import java.io.File;
public class FileSeparatorEjemplo {
	public static void main(String[] args) {
		
	    String ruta = "D:" + File.separator + "EJERCICIOS" + File.separator + "UNI1"+ File.separator +  "ejemplo1.txt";
        System.out.println("Ruta construida: " + ruta);

        // Crear un objeto File usando la ruta
        File file = new File(ruta);

        // Comprobar si el fichero existe
        if (file.exists()) {
            System.out.println("El fichero existe.");
        } else {
            System.out.println("El fichero no existe.");
        }
	}
}
