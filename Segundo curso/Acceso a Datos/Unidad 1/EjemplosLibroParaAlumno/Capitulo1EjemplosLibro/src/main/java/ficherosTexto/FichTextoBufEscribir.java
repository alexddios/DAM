package ficherosTexto;

import java.io.*;

public class FichTextoBufEscribir {
	public static void main(String[] args) {    
	
		try {
			FileWriter fic = new FileWriter("FichTexto.txt", true); //declara fichero
			BufferedWriter fichero = new BufferedWriter(fic);
			for (int i = 1; i < 11; i++) {
				fichero.write("Fila numero: " + i); // escribe una línea
				fichero.newLine(); // escribe un salto de línea
			}
			fichero.close();
		} catch (FileNotFoundException fn) {
			System.out.println("No se encuentra el fichero");
		} catch (IOException io) {
			System.out.println("Error de E/S ");
		}
		
		//EJEMPLO CON PrintWriter
		PrintWriter fichero;
		try {
			fichero = new PrintWriter(new FileWriter("FichTexto.txt"));
			for(int i=1; i<11; i++)
				fichero.println("Fila numero: "+i);	  		
				fichero.close();				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      


		
		
	}
}