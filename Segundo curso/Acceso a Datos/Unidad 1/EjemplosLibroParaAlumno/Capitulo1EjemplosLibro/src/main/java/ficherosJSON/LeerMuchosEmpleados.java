package ficherosJSON;
/*
 * lee todos los empleados como una lista de objetos Empleado
 */
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class LeerMuchosEmpleados {
	File jsonFile = new File("empleados.json");

	public static void main(String[] args) throws StreamReadException, DatabindException, IOException {
	
		     File jsonFile = new File("empleados.json");
			// Crear un ObjectMapper
			ObjectMapper mapper = new ObjectMapper();

			// Leer el JSON como una lista de Empleado			
			TypeReference<List<Empleado>> ref = new TypeReference<List<Empleado>>() { };			
			List<Empleado> empleados =  (List<Empleado>) mapper.readValue(jsonFile,ref);
			
			// Recorrer la lista de empleados
			for (Empleado empleado : empleados) {
				System.out.println(empleado);
			}		
	}
}
