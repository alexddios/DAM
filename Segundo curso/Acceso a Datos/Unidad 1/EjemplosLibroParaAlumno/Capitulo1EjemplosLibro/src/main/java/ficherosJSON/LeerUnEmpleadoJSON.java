package ficherosJSON;

import java.io.File;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LeerUnEmpleadoJSON {

	static File jsonFile = new File("unempleado.json");

	public static void main(String[] args) throws Exception {
		 ObjectMapper mapper = new ObjectMapper();
	      
		 // Leer fichero JSON y mapearlo a un Map		
		 Map<String, Object> json = mapper.readValue(jsonFile, Map.class);
		 System.out.println("Contenido del JSON: \n\t" + json);
				
		 // Leer fichero JSON y mapearlo a un objeto Empleado		
		 Empleado empleado = mapper.readValue(jsonFile, Empleado.class);
	     System.out.println("Objeto Empleado: \n\t" +empleado);	    

	}

}
