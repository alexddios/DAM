package ficherosObjetos;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class LeerPersonasNIO {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		  // Define la ruta del archivo
        Path ruta = Path.of("FichPersonas.dat");
                
		ObjectInputStream dataIS = 
				new ObjectInputStream(Files.newInputStream(ruta));
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
