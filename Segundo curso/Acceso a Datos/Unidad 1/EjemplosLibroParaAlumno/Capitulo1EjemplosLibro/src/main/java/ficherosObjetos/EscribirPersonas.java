package ficherosObjetos;

import java.io.*;

public class EscribirPersonas {
	public static void main(String[] args) throws IOException {
	
		File fichero = new File("FichPersonas.dat");// declara el fichero

		//crea el flujo de salida
		FileOutputStream fileout = new FileOutputStream(fichero); 
		
		// conecta el flujo de bytes al flujo de datos
		ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

		String nombres[] = { "Ana", "Luis Miguel", "Alicia", "Pedro", 
				             "Manuel", "Andrés", "Julio", "Antonio",
				             "María Jesús" };

		int edades[] = { 14, 15, 13, 15, 16, 12, 16, 14, 13 };
		
		System.out.println("INSERTO DATOS DE PERSONAS.");
		
		for (int i = 0; i < edades.length; i++) { // recorro los arrays
			Persona persona = new Persona(nombres[i], edades[i]); // creo la persona
			dataOS.writeObject(persona); // escribo la persona en el fichero
			System.out.println("Registro insertado...");
		}
		dataOS.close(); // cerrar stream de salida
	}
}