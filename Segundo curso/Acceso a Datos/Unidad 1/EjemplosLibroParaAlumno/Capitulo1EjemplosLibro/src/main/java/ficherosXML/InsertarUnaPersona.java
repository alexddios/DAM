package ficherosXML;
import java.io.File;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


import java.io.FileWriter;
import java.io.IOException;

public class InsertarUnaPersona {

	public static void main(String[] args) throws JDOMException, IOException {
		File xmlFile = new File("personas.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(xmlFile);

        // Obtener el elemento raíz <personas>
        Element rootElement = document.getRootElement();

        // Crear un nuevo nodo <persona>
        Element persona = new Element("persona");
        persona.setAttribute("id", "3");
        persona.addContent(new Element("nombre").setText("María"));
        persona.addContent(new Element("edad").setText("25"));

        // Añadir el nuevo nodo <persona> al elemento raíz 
        rootElement.addContent(persona);

        // Guardar los cambios 
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new FileWriter(xmlFile));

        System.out.println("Se añadió un nuevo elemento al fichero XML.");
	}

}
