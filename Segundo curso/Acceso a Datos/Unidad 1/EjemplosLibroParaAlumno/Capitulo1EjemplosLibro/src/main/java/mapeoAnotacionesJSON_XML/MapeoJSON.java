package mapeoAnotacionesJSON_XML;

/*
 * Mapear a JSON
 */
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapeoJSON {
    public static void main(String[] args) throws Exception {
        // Crear un objeto Empleado
        Persona persona =  new Persona("Maria", 30);  
        // Crear un ObjectMapper para JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // Convertir el objeto Java a JSON
        String json = objectMapper.writeValueAsString(persona);

        // Mostrar el JSON
        System.out.println("Persona en JSON:");
        System.out.println(json);
    }
}
