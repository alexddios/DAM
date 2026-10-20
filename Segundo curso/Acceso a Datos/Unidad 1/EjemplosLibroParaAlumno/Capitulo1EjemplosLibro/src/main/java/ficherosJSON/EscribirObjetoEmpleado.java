package ficherosJSON;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EscribirObjetoEmpleado {

	public static void main(String[] args) throws StreamWriteException, DatabindException, IOException {
		// Instanciar ObjectMapper
				ObjectMapper mapper = new ObjectMapper();

				// Crear un mapa con datos
				Empleado empleado = new Empleado(33,"FRAILE",30, 3000.34);
				
				// Escribir el JSON en el fichero
				File jsonFile = new File("salida.json");
				mapper.writeValue(jsonFile, empleado);
				
				//mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, empleado);

				System.out.println("Fichero creado.");

	}

}
