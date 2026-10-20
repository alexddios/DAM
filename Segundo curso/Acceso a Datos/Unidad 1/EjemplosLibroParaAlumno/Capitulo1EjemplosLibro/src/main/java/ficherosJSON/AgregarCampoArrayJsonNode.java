package ficherosJSON;
/*
 * Agrega a un elemento del array JSON un campo, por ejemplo ciudad
 */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;

public class AgregarCampoArrayJsonNode {
	public static void main(String[] args) throws Exception {
		// Crear ObjectMapper
		ObjectMapper mapper = new ObjectMapper();

		// Leer el fichero JSON como JsonNode
		File jsonFile = new File("empleados.json");
		JsonNode rootNode = mapper.readTree(jsonFile);

		// Verificar si el JSON es un array
		if (rootNode.isArray()) {
			ArrayNode arrayNode = (ArrayNode) rootNode;

			// Recorrer los elementos del array
			for (JsonNode node : arrayNode) {
				// Buscar el elemento con "id": 3
				if (node.get("id").asInt() == 3) {
					// Convertir el elemento en ObjectNode para modificarlo
					ObjectNode empleado = (ObjectNode) node;

					// Añadir el nuevo campo "ciudad"
					empleado.put("ciudad", "Guadalajara");

					System.out.println("Campo añadido al elemento con id = 3");
				}
			}

			// Guardar los cambios en el fichero
			mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, arrayNode);

			System.out.println("Fichero modificado y guardado.");
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode));

		}
	}
}