package ejemplosNIO;
import java.nio.file.*;

public class FileSystemEjemplos {

	public static void main(String[] args) {
		FileSystem fs = FileSystems.getDefault();
		// Obtener el separador
		System.out.println("Separador: " + fs.getSeparator());

		// Listar directorios raíz
		System.out.println("Directorios raíz:");
		for (Path root : fs.getRootDirectories()) {
			System.out.println("\t"+root);
		}

		// Obtener una ruta
		Path ruta = fs.getPath("D:", "EJERCICIOS");
		System.out.println("Ruta:");
		System.out.println("\t"+ruta.getFileName());
	    System.out.println("\t"+ruta.getParent());		
	}
}
