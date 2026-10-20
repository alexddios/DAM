package mapeoAnotacionesJSON_XML;




import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class MainDeserializarPersonas {

	public static void main(String[] args) throws Exception {
		deserializarJSON();
		deserializarXML();
	}// main

	private static void deserializarJSON() throws Exception {
		String cadenajson = """
				{
				  "personas" : [ {
				    "edad" : 30,
				    "nombre" : "Maria"
				  }, {
				    "edad" : 35,
				    "nombre" : "Pepe"
				  } ],
				  "direccion" : "Avenida Madrid 5"
				}
								""";

		ObjectMapper objectMapper = new ObjectMapper();
		Casa casa = objectMapper.readValue(cadenajson, Casa.class);

		System.out.println("JSON: \n"+ casa);
				
	}

	private static void deserializarXML() throws Exception {
		String cadenaxml = """
				<CasaXML>
				  <persona>
				    <edad>30</edad>
				    <nombre>Maria</nombre>
				  </persona>
				  <persona>
				    <edad>35</edad>
				    <nombre>Pepe</nombre>
				  </persona>
				  <direccion>Avenida Madrid 5</direccion>
				</CasaXML>
								""";
		XmlMapper xmlMapper = new XmlMapper();
		Casa casa = xmlMapper.readValue(cadenaxml, Casa.class);

		System.out.println("XML: \n"+ casa);
		
	}

}
