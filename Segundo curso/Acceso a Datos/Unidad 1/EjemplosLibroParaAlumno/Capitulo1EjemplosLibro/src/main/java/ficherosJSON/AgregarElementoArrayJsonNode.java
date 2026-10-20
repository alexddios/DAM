package ficherosJSON;

/*
 * Agrega un empleado al array de empleados
 */
import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AgregarElementoArrayJsonNode {

	public static void main(String[] args) throws Exception {
		// Crear ObjectMapper
		ObjectMapper mapper = new ObjectMapper();

		// Leer el archivo JSON como JsonNode
		File jsonFile = new File("empleados.json");
		JsonNode rootNode = mapper.readTree(jsonFile);

		// Verificar si es un ArrayNode (para añadir elementos al array)
		if (rootNode.isArray()) {
			ArrayNode arrayNode = (ArrayNode) rootNode;
			// Crear un nuevo objeto JSON para añadir al array
			ObjectNode nuevoEmpleado = mapper.createObjectNode();
			nuevoEmpleado.put("id", 44);
			nuevoEmpleado.put("apellido", "SABINA");
			nuevoEmpleado.put("dep", 30);
			nuevoEmpleado.put("salario", 3100.0);

			arrayNode.add(nuevoEmpleado);

			// Guardar el árbol modificado
			mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
			System.out.println("Empleado añadido.");
			
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode));
		}
	}
}
