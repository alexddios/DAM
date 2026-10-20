package ficherosAleatorios;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class EscribirFichAleatorio {
	static int longitudRegistro= 34;
	
	public static void main(String[] args) throws IOException {
		File fichero = new File("AleatorioEmple.dat");
		fichero.delete();
	    // declara el fichero de acceso aleatorio
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");

		int id[] = { 7, 9, 10, 20, 3, 5, 4 }; // identificadores

		String apellido[] = { "FERNANDEZ", "GIL", "LOPEZ", "RAMOS", "SEVILLA", "CASILLA", "REY" }; // apellidos
		short dep[] = { 10, 20, 10, 10, 30, 30, 20 }; // departamentos
		double salario[] = { 1000.45, 2400.60, 3000.0, 1500.56, 
				             2200.0, 1435.87, 2000.0 };//salarios
		int n = id.length;

		for (int i = 0; i < n; i++) {
			long posicion = (id[i]-1) * longitudRegistro; 
			file.seek(posicion); //posicionarse
			
			file.writeInt(id[i]);
			StringBuffer buffer = new StringBuffer(apellido[i]);
			buffer.setLength(10); // 10 caracteres para el apellido
			file.writeChars(buffer.toString());// insertar apellido
			
			

			file.writeShort(dep[i]); // insertar departamento
			file.writeDouble(salario[i]);// insertar salario
		}

		file.close(); // cerrar fichero

	}

}
