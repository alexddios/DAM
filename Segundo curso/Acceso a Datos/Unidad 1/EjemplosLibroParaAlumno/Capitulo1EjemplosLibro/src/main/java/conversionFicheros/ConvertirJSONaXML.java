package conversionFicheros;

/*
 * Convierte   JSON a XML 
 */
import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ConvertirJSONaXML {

	public static void main(String[] args) throws Exception {

		// Crear ObjectMapper
		ObjectMapper mapper = new ObjectMapper();

		File jsonFile = new File("libros.json");
		JsonNode jsonNode = mapper.readTree(jsonFile); // Convertir JSON a JsonNode

		// Crear XmlMapper para manejar XML
		XmlMapper xmlMapper = new XmlMapper();
		String xml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode); // Convertir JsonNode a
																								// XML

		System.out.println("XML generado:\n" + xml);
		
		// crear el fichero
		File xmlFile = new File("librosnuevo.xml");
		xmlMapper.writerWithDefaultPrettyPrinter().writeValue(xmlFile, jsonNode);

	}

}
