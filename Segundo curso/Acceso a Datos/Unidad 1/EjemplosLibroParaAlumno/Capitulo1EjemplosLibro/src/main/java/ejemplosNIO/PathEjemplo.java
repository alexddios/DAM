package ejemplosNIO;
import java.nio.file.Path;
import java.nio.file.Paths;
public class PathEjemplo {
	    public static void main(String[] args) {	    	
	    	Path ruta = Paths.get("D:", "EJERCICIOS", "UNI1", "ejemplo1.txt");
	        // Información básica
	        System.out.println("Nombre del fichero: " + ruta.getFileName());
	        System.out.println("Directorio padre: " + ruta.getParent());
	        System.out.println("Raíz: " + ruta.getRoot());
	        System.out.println("Nº de elementos: " + ruta.getNameCount());	        
	        System.out.println("Elemento posicion 1: " + ruta.getName(1));
	        
	        // Propiedades
	        System.out.println("¿Es absoluta? " + ruta.isAbsolute());
	        System.out.println("¿Comienza con 'D:/'? " + ruta.startsWith("D:/"));
	        System.out.println("¿Termina con 'ejemplo.txt'? " + ruta.endsWith("ejemplo.txt"));
	        
	        // Manipulación de la ruta
	        Path resolvedPath = ruta.resolve("otrodir/fichero.txt");
	        System.out.println("Ruta resuelta: " + resolvedPath);

	        Path relativePath = Paths.get("documento.txt");
	        System.out.println("Ruta absoluta: " + relativePath.toAbsolutePath());	       
	    }
	}
