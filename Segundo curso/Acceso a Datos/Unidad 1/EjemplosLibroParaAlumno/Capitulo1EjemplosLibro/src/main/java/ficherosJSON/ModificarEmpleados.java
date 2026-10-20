package ficherosJSON;
/*
 * Modifica empleados almacenándolos en una lista 
 * de objetos Empleado
 */
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class ModificarEmpleados {
	public static void main(String[] args) throws Exception {

		// Crear el ObjectMapper
		ObjectMapper mapper = new ObjectMapper();

		// Leer el JSON como una lista de objetos Empleado
		File jsonFile = new File("empleados.json");
		List<Empleado> empleados = mapper.readValue(jsonFile, new TypeReference<List<Empleado>>() {
		});

		// ---- MODIFICAR UN EMPLEADO - con ID 4 --
		for (Empleado empleado : empleados) {
			if (empleado.getId() == 4) {
				empleado.setSalario(empleado.getSalario() + 500);
				empleado.setApellido("REYES");
			}
		}

		// ---- AÑADIR UN NUEVO EMPLEADO ----
		Empleado nuevoEmpleado = new Empleado(60, "LOPEZ", 40, 1800.0);
		empleados.add(nuevoEmpleado);

		// ---- ELIMINAR UN EMPLEADO con ID 3 ----
		Iterator<Empleado> iterator = empleados.iterator();
		while (iterator.hasNext()) {
			Empleado empleado = iterator.next();
			if (empleado.getId() == 3) {
				iterator.remove();
			}
		}

		// Guardar los cambios de vuelta al fichero JSON
		mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, empleados);

		System.out.println("Modificaciones realizadas y guardadas en empleados.json.");

	}
}