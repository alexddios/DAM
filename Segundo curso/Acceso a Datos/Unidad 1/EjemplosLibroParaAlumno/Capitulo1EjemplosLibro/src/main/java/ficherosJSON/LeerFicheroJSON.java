package ficherosJSON;
/*
 * muestra el contenido de un fichero json
 */
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class LeerFicheroJSON {
	static File jsonFile = new File("unempleado.json");
	
	public static void main(String[] args) throws StreamReadException, DatabindException, IOException {
		// Instanciar ObjectMapper de Jackson
		ObjectMapper mapper = new ObjectMapper();

		// Leer archivo JSON y mapearlo a un Map		
		Map<String, Object> jsonData = mapper.readValue(jsonFile, Map.class);

		// Mostrar los datos leídos
		System.out.println("Contenido del JSON:");
		System.out.println(jsonData);
		
	
	}
}