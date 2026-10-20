package ficherosAleatorios;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ModificarUnRegistro {

	static int longitudRegistro = 34;
	static File fichero = new File("AleatorioEmple.dat");

	public static void main(String[] args) throws IOException {
		
		System.out.println("antes de modificar");
		verRegistro(4);
		
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");

		int identificador = 4;// id a modificar	
		
		long posicion = (identificador - 1) * longitudRegistro; 
		posicion = posicion + 4 + 20; // calcular posicion del departamento
		file.seek(posicion); 
		
		file.writeShort(40); // modificar departamento
		
		double antiguo = file.readDouble();
		double nuevosalario= 300 + antiguo;
		
		posicion = file.getFilePointer()-8;// calcular posicion del salario
		file.seek(posicion); 
		
		file.writeDouble(nuevosalario);// modificar salario
		file.close(); // cerrar fichero

		System.out.println("Despues de modificar");
		verRegistro(4);
		
	}
	
	
	private static void verRegistro(int id) throws IOException {
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");

		long posicion = (id - 1) * longitudRegistro;

		if (posicion >= file.length())
			System.out.printf("ID: %d, NO EXISTE.%n", id);
		else {
			file.seek(posicion); // nos posicionamos
			int idf = file.readInt(); // obtengo id de empleado
			
			if (idf == id) {
				// obtener apellido
				char apellido[] = new char[10], aux;
				for (int i = 0; i < apellido.length; i++) {
					aux = file.readChar();//
					apellido[i] = aux;
				}
				String apellidoS = new String(apellido).trim();// convierto a String el array

				short dep = file.readShort(); // obtener departamento
				double salario = file.readDouble(); // obtener salario

				System.out.printf("ID: %d, Apellido: %s, Departamento: %d, Salario: %,9.2f%n", id, apellidoS, dep,
						salario);
			} else {
				System.out.printf("ID: %d, NO EXISTE, ES UN HUECO.%n", id);
			}
		}

		file.close();
	}

}
