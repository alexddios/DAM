package ficherosXML;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;

public class ModificarPersonas {
    public static void main(String[] args) {
        try {          
            File xmlFile = new File("personas.xml");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(xmlFile);

            // Obtener el elemento raíz 
            Element raiz = document.getRootElement();

            // Buscar el nodo <persona> con el atributo id="1"
            Element personaMod = null;
            for (Element persona : raiz.getChildren("persona")) {
            	if (persona.getAttributeValue("id").equals("1")) {
                	personaMod = persona;
                    break;
                }
            }

            if (personaMod != null) {
                // Modificar el texto del nodo <nombre>
            	personaMod.getChild("nombre").setText("Juanito");
                System.out.println("Modificación realizada 1.");
            } else {
                System.out.println("No se encontró el id=1.");
            }

            
          
            //Buscar los nodos persona con la edad = 25 y modificarlos           
            for (Element persona : raiz.getChildren("persona")) {
            	if(persona.getChildText("edad").equals("35")) {
                	persona.getChild("edad").setText("45");  
                	System.out.println("Modificación realizada.");                  
                }
            }

            //AÑADIR CONTENIDO A UN NODO EXISTENTE
             for (Element persona : raiz.getChildren("persona")) {
                if(persona.getChildText("nombre").equals("Ana")) {
                	persona.addContent(new Element("oficio").setText("Directora"));
                	System.out.println("Se ha añadido un nodo.");                  
                }
            }
                                  
            // Guardar los cambios en el mismo archivo
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(document, new FileWriter("personas.xml"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
