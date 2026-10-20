package ficherosJSON;
/*
 * Modifica el empleado con id 4 y elimina el empleado con id 3
 */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.util.Iterator;

public class ModificarEliminarEmpleadosJsonNode {
	public static void main(String[] args) throws Exception {

		// Crear ObjectMapper
		ObjectMapper mapper = new ObjectMapper();

		// Leer el archivo JSON como un árbol (JsonNode)
		File jsonFile = new File("empleados.json");
		JsonNode rootNode = mapper.readTree(jsonFile);

		Iterator<JsonNode> iterator = rootNode.elements();
		while (iterator.hasNext()) {
			JsonNode empleado = iterator.next();

			// Modificar datos
			if (empleado.get("id").asInt() == 4) {
				((ObjectNode) empleado).put("salario", 3333.0);
				((ObjectNode) empleado).put("apellido", "REYES");
			}

			// Eliminar empleado con ID 3
			if (empleado.get("id").asInt() == 3) {
				iterator.remove();
			}
		}		
		// Guardar el árbol modificado
		mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
		System.out.println("Fichero modificado con JsonNode.");

	}
}
