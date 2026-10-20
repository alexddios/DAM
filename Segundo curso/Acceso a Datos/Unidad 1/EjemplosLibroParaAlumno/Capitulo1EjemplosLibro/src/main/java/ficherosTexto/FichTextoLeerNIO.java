package ficherosTexto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FichTextoLeerNIO {
	public static void main(String[] args) throws IOException  {
		// Crear un objeto Path
		Path path = Paths.get("src\\FichTextoLeer.java");

		// Leer todo el contenido del archivo como una lista de líneas
		List<String> lineas = Files.readAllLines(path);

		for (String linea : lineas) {
			System.out.println(linea);
		}

	}
}
