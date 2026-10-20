package mapeoCSV;

/*
 * serializar personas 
 */
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class SerializarCSV {

	public static void main(String[] args) throws Exception {
		
		//unapersona();
		variaspersonas();

	}

	// una persona
	private static void unapersona() throws Exception {

		Persona persona = new Persona("Ana", 25);

		FileWriter fw = new FileWriter("personas.csv");

		StatefulBeanToCsv<Persona> beanToCsv = new StatefulBeanToCsvBuilder<Persona>(fw).build();
		beanToCsv.write(persona);
		System.out.println("Archivo CSV creado correctamente.");
		fw.close();
	}

	// varias personas en una lista
	private static void variaspersonas() throws Exception {
		List<Persona> personas = Arrays.asList(
				new Persona("Ana", 25), 
				new Persona("Carlos", 30),
				new Persona("Elena", 28));

		FileWriter writer = new FileWriter("personas.csv");

		StatefulBeanToCsv<Persona> beanToCsv = new StatefulBeanToCsvBuilder<Persona>(writer).build();
		beanToCsv.write(personas);
		System.out.println("Archivo CSV creado correctamente.");
		writer.close();
	}
}