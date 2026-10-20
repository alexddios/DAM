package mapeoAnotacionesJSON_XML;

/*
 * mapear a XML
 */
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class MapeoXML {
    public static void main(String[] args) throws Exception {
        // Crear un objeto Persona
        Persona persona =  new Persona("Maria", 30);  
        
    	   
        // Crear un XmlMapper para XML
        XmlMapper xmlMapper = new XmlMapper();

        // Convertir el objeto Java a XML
        String xml = xmlMapper.writeValueAsString(persona);

        // Mostrar el XML
        System.out.println("Persona en XML:");
        System.out.println(xml);
    }
}
