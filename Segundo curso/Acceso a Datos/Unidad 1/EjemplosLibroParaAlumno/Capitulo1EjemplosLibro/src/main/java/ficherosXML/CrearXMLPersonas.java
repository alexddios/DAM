package ficherosXML;
import java.io.FileWriter;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class CrearXMLPersonas {

	public static void main(String[] args) throws Exception {

		// Crear el elemento raíz
		Element root = new Element("personas");

		// Añadir elementos hijos
		Element p1 = new Element("persona");
		p1.setAttribute("id", "1");
		p1.addContent(new Element("nombre").setText("Juan"));
		p1.addContent(new Element("edad").setText("30"));
		root.addContent(p1);

		Element p2 = new Element("persona");
		p2.setAttribute("id", "2");
		p2.addContent(new Element("nombre").setText("Ana"));
		p2.addContent(new Element("edad").setText("25"));
		root.addContent(p2);

		// Crear el documento
		Document document = new Document(root);

		// Guardar en un archivo
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		xmlOutputter.output(document, new FileWriter("personas.xml"));

		System.out.println("Fichero XML creado.");
		
		String salida = xmlOutputter.outputString(document);
		System.out.println(salida);
	}
}