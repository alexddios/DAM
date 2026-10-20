import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PeliculaXmlRepository {
    private final File archivo;

    public PeliculaXmlRepository(String ruta) {
        this.archivo = new File(ruta);
    }

    public Map<Integer, Pelicula> cargar() {
        Map<Integer, Pelicula> peliculas = new HashMap<>();

        if (!archivo.exists() || archivo.length() == 0) {
            System.out.println("⚠️ Aviso: El fichero 'peliculas.xml' no existe o está vacío. Se creará uno nuevo al añadir la primera película.");
            return peliculas;
        }

        try {
            Document documento = new SAXBuilder().build(archivo);
            Element raiz = documento.getRootElement();

            for (Element elemento : raiz.getChildren("pelicula")) {
                Pelicula pelicula = new Pelicula(
                        Integer.parseInt(elemento.getAttributeValue("id")),
                        elemento.getChildText("nombre"),
                        elemento.getChildText("autor"),
                        Integer.parseInt(elemento.getChildText("anio")),
                        elemento.getChildText("genero"));
                peliculas.put(pelicula.getId(), pelicula);
            }
        } catch (JDOMException | IOException | NumberFormatException e) {
            System.out.println("❌ ERROR CRÍTICO: El archivo 'peliculas.xml' contiene errores de formato (" + e.getMessage() + ").");
            System.out.println("🛡️ Por seguridad, se ha bloqueado la carga para evitar sobrescribir y perder tus datos. Repara el archivo manualmente.");
            System.exit(1);
        }

        return peliculas;
    }

    public void guardar(Map<Integer, Pelicula> peliculas) throws IOException {
        Element raiz = new Element("catalogo");
        for (Pelicula pelicula : peliculas.values()) {
            raiz.addContent(convertirAElemento(pelicula));
        }

        Document documento = new Document(raiz);
        XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
        try (FileWriter escritor = new FileWriter(archivo)) {
            salida.output(documento, escritor);
        }
    }

    private Element convertirAElemento(Pelicula pelicula) {
        Element elemento = new Element("pelicula");
        elemento.setAttribute("id", String.valueOf(pelicula.getId()));
        elemento.addContent(new Element("nombre").setText(pelicula.getNombre()));
        elemento.addContent(new Element("autor").setText(pelicula.getAutor()));
        elemento.addContent(new Element("anio").setText(String.valueOf(pelicula.getAnio())));
        elemento.addContent(new Element("genero").setText(pelicula.getGenero()));
        return elemento;
    }
}
