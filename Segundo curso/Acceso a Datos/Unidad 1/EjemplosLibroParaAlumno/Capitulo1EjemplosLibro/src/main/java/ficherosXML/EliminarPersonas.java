package ficherosXML;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class EliminarPersonas {

	public static void main(String[] args) throws JDOMException, IOException {
		// Leer el fichero XML existente
		File xmlFile = new File("personas.xml");
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(xmlFile);

		// Obtener el elemento raíz <personas>
		Element raiz = document.getRootElement();

		// Buscar el nodo <persona> con el atributo id="1" y eliminarlo
		List<Element> personas = raiz.getChildren("persona");
		for (Element persona : personas) {
			if (persona.getAttributeValue("id").equals("1")) {
				raiz.removeContent(persona);
				System.out.println("Se eliminó el elemento con id=1");
				break;
			}
		}

		// Buscar los nodos persona con la edad = 25 y eliminarlos
		Iterator<Element> iterator = raiz.getChildren("persona").iterator();
		while (iterator.hasNext()) {
			Element persona = iterator.next();
			if (persona.getChildText("edad").equals("25")) {
				iterator.remove(); // Eliminar el nodo <persona>
				System.out.println("Persona: " + persona.getChildText("nombre") + ", eliminada");
			}
		}

		// Guardar los cambios en el mismo fichero
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		xmlOutputter.output(document, new FileWriter(xmlFile));

	}

}
