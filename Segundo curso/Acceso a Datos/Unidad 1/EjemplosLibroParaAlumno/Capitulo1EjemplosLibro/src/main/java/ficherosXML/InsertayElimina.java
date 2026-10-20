package ficherosXML;
import java.io.*;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class InsertayElimina {

	public static void main(String[] args) throws Exception {

		File xmlFile = new File("personas.xml");
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(xmlFile);

		// Obtener el elemento raíz <personas>
		Element raiz = document.getRootElement();

		// Crear un nuevo nodo <persona>
		Element persona = new Element("persona");
		persona.setAttribute("id", "4");
		persona.addContent(new Element("nombre").setText("Pedro"));
		persona.addContent(new Element("edad").setText("25"));

		// Añadir el nuevo nodo <persona> al elemento raíz
		raiz.addContent(persona);
		System.out.println("id=4 Añadido");

		// ELIMINAR EL id 1
		// Buscar el nodo <persona> con el atributo id="1" y eliminarlo
		List<Element> personas = raiz.getChildren("persona");
		for (Element per : personas) {
			if (per.getAttributeValue("id").equals("1")) {
				raiz.removeContent(per);
				System.out.println("id=1 Eliminado");
				break;
			}
		}

		// Guardar los cambios en el mismo fichero
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		xmlOutputter.output(document, new FileWriter(xmlFile));
	}

}
