package mapeoAnotacionesJSON_XML;

import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class MainSerializarPersonas {

	public static void main(String[] args) throws Exception {		
		Persona p1 = new Persona("Maria", 30);  
        Persona p2 = new Persona("Pepe", 35);   
        
        //crear lista de Personas
        ArrayList<Persona> personas = new ArrayList<Persona>();
        personas.add(p1);
        personas.add(p2);
        
        // Crear el objeto Casa con las personas
        Casa casa = new Casa(personas, "Avenida Madrid 5");
             
        // Crear ObjectMapper para JSON
        ObjectMapper objectMapper = new ObjectMapper();     
        
        // Convertir  a JSON
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(casa);
        System.out.println("CASA en JSON:");
        System.out.println(json);

        // Crear XmlMapper para XML
        XmlMapper xmlMapper = new XmlMapper();
        // Convertir a XML
        String xml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(casa);
        System.out.println("\n\nCASA en XML:");
        System.out.println(xml);
	}
}
