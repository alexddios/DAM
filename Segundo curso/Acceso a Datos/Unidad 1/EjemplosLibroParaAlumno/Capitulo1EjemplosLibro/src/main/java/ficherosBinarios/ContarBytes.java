package ficherosBinarios;

import java.io.*;
public class ContarBytes {
	public static void main(String[] args) throws IOException {

		FileInputStream fic = new FileInputStream("FichTexto.txt");
		int n = 0;
		while ((fic.read()) != -1)
			n++;
		fic.close();
		
		System.out.println("Tamaño del fichero: " + n + " bytes");

	}

}
