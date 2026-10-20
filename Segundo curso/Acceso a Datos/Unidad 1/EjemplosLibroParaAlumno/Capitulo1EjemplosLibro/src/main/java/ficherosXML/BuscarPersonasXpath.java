package ficherosXML;
import org.jdom2.Document;
import org.jdom2.Element;

import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import java.io.File;

import java.util.List;

public class BuscarPersonasXpath {
	public static void main(String[] args) throws Exception {

		File file = new File("personas.xml");
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(file);
		//
		String consulta = "//personas/persona[nombre='Juan']";
		Buscar(consulta, document);
		//
		consulta = "//personas/persona[@id='2']";
		Buscar(consulta, document);
	}

	// Consultar con XPath
	private static void Buscar(String consulta, Document document) {		
		XPathFactory xPathFactory = XPathFactory.instance();	
		XPathExpression<Element> expr = xPathFactory.compile(consulta, Filters.element());
		System.out.println(consulta);
		List<Element> results = expr.evaluate(document);

		for (Element ele : results) {			
			System.out.printf("\tId: %s,  Nombre: %s, Edad: %s%n",
					  ele.getAttribute("id").getValue(),
			ele.getChildText("nombre"),  ele.getChildText("edad"));
		}

	}
}