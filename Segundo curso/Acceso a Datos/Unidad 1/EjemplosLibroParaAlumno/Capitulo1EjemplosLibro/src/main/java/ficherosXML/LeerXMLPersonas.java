package ficherosXML;
import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class LeerXMLPersonas {

	public static void main(String[] args) throws JDOMException, IOException {
		// Cargar el fichero XML
		File file = new File("personas.xml");
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(file);

		// Obtener el elemento raíz
		Element raiz = document.getRootElement();
		System.out.println("Elemento raíz: " + raiz.getName());

		// Recorrer los elementos hijos
		for (Element persona : raiz.getChildren("persona")) {
			String id = persona.getAttributeValue("id");
			String nombre = persona.getChildText("nombre");
			String edad = persona.getChildText("edad");

			System.out.printf("Id: %s, Nombre: %s, Edad: %s %n", id, nombre, edad);
		}
	}

}
