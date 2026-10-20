package ficherosJSON;

/*
 * lee el objeto persona del fichero persona.json
 */
import java.io.File;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LeerPersonaJsonNode {

	public static void main(String[] args) throws Exception {
		// Crear ObjectMapper
		ObjectMapper mapper = new ObjectMapper();

		// Leer el archivo JSON como un árbol (JsonNode)
		File jsonFile = new File("persona.json");
		JsonNode rootNode = mapper.readTree(jsonFile);

		//solo es hay un objeto
		ObjectNode node = (ObjectNode) rootNode;
		
		// Leer los campos del nodo
		int id = node.get("id").asInt();
		String nombre = node.get("nombre").asText();
		int edad = node.get("edad").asInt();
		String ciudad = node.get("ciudad").asText();

		// mostrar los valores leídos
		System.out.println("ID: " + id);
		System.out.println("Nombre: " + nombre);
		System.out.println("edad: " + edad);
		System.out.println("Ciudad: " + ciudad);
		System.out.println();
	}

}
