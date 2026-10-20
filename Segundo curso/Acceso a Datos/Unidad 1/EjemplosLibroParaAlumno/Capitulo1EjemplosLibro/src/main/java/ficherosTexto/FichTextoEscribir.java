package ficherosTexto;
import java.io.*;

public class FichTextoEscribir {
	public static void main(String[] args) throws IOException {
		// declara fichero
		File fichero = new File("FichTexto.txt");
		// crear el flujo de salida
		FileWriter fic = new FileWriter(fichero);

		String cadena = "Esto es una prueba con FileWriter";

		// convierte un String en array de caracteres
		char[] array = cadena.toCharArray();

		for (int i = 0; i < array.length; i++)
			fic.write(array[i]); // se va escribiendo un carácter

		fic.write("\n"); // se escribe un salto de línea
		fic.append('*'); // se escribe un *
		fic.write(array);  // se escribe un array de caracteres

		cadena = "\n*última línea*";
		fic.write(cadena); // se escribe un String
		
		fic.close(); // cerrar fichero
	}
}
