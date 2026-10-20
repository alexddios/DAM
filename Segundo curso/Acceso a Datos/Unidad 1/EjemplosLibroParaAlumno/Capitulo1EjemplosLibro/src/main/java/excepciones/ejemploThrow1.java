package excepciones;

/*
 * Prueba de excepciones 3
 */
import java.io.*;

public class ejemploThrow1 {

	private static void LeerFichero(String nombreArchivo) throws IOException {
		FileReader file = new FileReader(nombreArchivo);
		BufferedReader br = new BufferedReader(file);
		System.out.println(br.readLine());
		br.close();
	}

	public static void main(String[] args) {
		try {
			LeerFichero("ejemplofichtexto.txt");
		} catch (IOException e) {
			System.out.println("Error al leer el fichero: " + e.getMessage());
		}
	}

}
