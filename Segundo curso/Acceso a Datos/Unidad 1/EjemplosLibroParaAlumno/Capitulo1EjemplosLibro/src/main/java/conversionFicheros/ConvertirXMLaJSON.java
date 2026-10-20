package conversionFicheros;
/*
 * Convierte  XML a JSON 
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.util.Map;

public class ConvertirXMLaJSON {
	public static void main(String[] args) throws Exception {
		// Leer el fichero XML
		XmlMapper xmlMapper = new XmlMapper();
		File xmlFile = new File("libros.xml");

		// Convertir XML a Map
		Map<String, Object> map = xmlMapper.readValue(xmlFile, Map.class);

		// Convertir el Map a JSON
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
		
		System.out.println(json);// Mostrar el JSON
		
		// Escribir datos en un fichero JSON
		File jsonFile = new File("libros.json");
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, map);
	}
}
