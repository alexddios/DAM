package ficherosObjetos;

import java.io.*;

public class LeerPersonas {
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		File fichero = new File("FichPersonas.dat");
		ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(fichero));

		try {

			while (true) { // lectura del fichero
				Persona persona = (Persona) dataIS.readObject();
				System.out.printf("Nombre: %s, edad: %d %n", persona.getNombre(), persona.getEdad());

			}

		} catch (EOFException eo) {
			System.out.println("FIN DE LECTURA.");
		} catch (StreamCorruptedException x) {
		}

		dataIS.close(); // cerrar stream de entrada
	}
}