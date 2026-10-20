package ejemplosNIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FilesEjemplos {

	public static void main(String[] args) throws IOException {

		Path fichero1 = Paths.get("D:", "EJERCICIOS", "UNI1", "ejemplo1.txt");
		// Existencia y comprobación de permisos:
		System.out.println("Fichero " + fichero1);
		System.out.println("¿Existe ?          : " + Files.exists(fichero1));
		System.out.println("¿Se puede leer?    : " + Files.isReadable(fichero1));
		System.out.println("¿Se puede escribir?: " + Files.isWritable(fichero1));
		System.out.println("¿Es ejecuteable?   : " + Files.isExecutable(fichero1));
		System.out.println("¿Es un directorio? : " + Files.isDirectory(fichero1));
		
		if (Files.exists(fichero1)) 
			System.out.println("Tamaño             : " + Files.size(fichero1));

		// CREAR UN FICHERO SI NO EXISTE
		Path fichero2 = Paths.get("D:", "EJERCICIOS", "UNI1", "ejemplo2.txt");
		if (!Files.exists(fichero2)) {
			Files.createFile(fichero2);
			System.out.println("Fichero2 creado");
		}

		// CREAR UN DIRECTORIO
		Path directorio = Paths.get("D:", "EJERCICIOS", "UNI2");
		Files.createDirectories(directorio);
		System.out.println("Directorio creado");

		// BORRAR FICHERO SI EXISTE
		if(Files.deleteIfExists(fichero1))
			System.out.println("Fichero1 eliminado");
		else 
			System.out.println("Fichero1 a eliminar no existe");		
		
		//COPIAR FICHEROS		
		Path origen = Paths.get("D:/EJERCICIOS/origen.txt");		
		Path destino = Paths.get("D:/EJERCICIOS/destino.txt");
		Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
		System.out.println("Archivo copiado a: " + destino);
		
		//MOVER O RENOMBRAR FICHEROS
		Path fichero = Paths.get("D:/EJERCICIOS/destino.txt");
		Path nuevoDestino = Paths.get("D:/EJERCICIOS/nuevodestino.txt");
		Files.move(fichero, nuevoDestino, StandardCopyOption.REPLACE_EXISTING);
		System.out.println("Archivo movido a: " + nuevoDestino);		
	}

}
