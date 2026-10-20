package ficherosJSON;
/*
 * Agrega un campo a un JSON a una persona
 */
import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EliminarCampoJsonNode {

	public static void main(String[] args) throws Exception {
		// Crear ObjectMapper
		ObjectMapper mapper = new ObjectMapper();

		// Leer el archivo JSON como JsonNode
		File jsonFile = new File("persona.json");
		JsonNode rootNode = mapper.readTree(jsonFile);

		// Verificar si es un ObjectNode 
		if (rootNode.isObject()) {
			ObjectNode objectNode = (ObjectNode) rootNode;

			// eliminar la edad
			objectNode.remove("edad");	

			// Guardar los cambios en el archivo
			mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, objectNode);

			System.out.println("Elemento eliminado del JSON.");
			
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode));

		}

	}
}
