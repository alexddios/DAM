package ficherosTexto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.io.IOException;

public class FichTextoEscribirNIO {
	public static void main(String[] args) {
		// Ruta 
		Path ruta = Path.of("FichTexto.txt");
		// cadena a escribir
		String cadena = "\nEscribimos una linea usando java.nio.file.";

		try {
			// CREATE crea un fichero si no existe. Si existe, no lo modifica
			Files.writeString(ruta, cadena, StandardOpenOption.CREATE);

			// APPEND si el fichero existe, agrega los datos al final
			Files.writeString(ruta, cadena, StandardOpenOption.APPEND);

			System.out.println("Operación correcta.");
		} catch (IOException e) {
			System.err.println("Error al escribir en el fichero: " + e.getMessage());
		}
	}
}