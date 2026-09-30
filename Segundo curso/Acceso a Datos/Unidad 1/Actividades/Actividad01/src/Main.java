import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

Scanner scanner = new Scanner(System.in);

void main() {
    int numero = -1;
    List<Pelicula> peliculas = leerPeliculas("peliculas.txt");
    do {
        System.out.println("""
                ===== CATÁLOGO =====
                1. Mostrar todas
                2. Buscar por género
                3. Buscar por título
                4. Generar XML
                5. Importar XML
                0. Salir
                """);

        System.out.print("Elige una opción: ");
        try {
            numero = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            numero = -1;
        }
        switch (numero){
            case 1:
                mostrarTodas(peliculas);
                break;
            case 2:
                buscarGenero(peliculas);
                break;
            case 3:
                buscarTitulo(peliculas);
                break;
            case 4:
                generarXML(peliculas);
                break;
            case 5:
                exportarXML("peliculas.xml");
                break;
            case 0:
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Opción no válida. Inténtalo de nuevo.");
        }
    } while (numero != 0);
}
public static List<Pelicula> leerPeliculas(String nombreFichero) {
    FI fi = new FI(".\\datos\\".concat(nombreFichero));
    fi.obrir();
    List<Pelicula> peliculas = new ArrayList<>();
    fi.llegirLinia();
    String linea = fi.llegirLinia();
    while (linea != null) {
        String[] parts = linea.split("\\|");
        Pelicula pelicula = new Pelicula(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                Integer.parseInt(parts[3]),
                parts[4]);
        peliculas.add(pelicula);
        linea = fi.llegirLinia();
    }
    fi.tancar();
    return peliculas;
}
public void mostrarTodas(List<Pelicula> peliculas){

    for (Pelicula p : peliculas){
        System.out.println(p.getNombre());
    }
}
public void buscarGenero(List<Pelicula> peliculas){
    Set<String> generos = new HashSet<>();
    for (Pelicula p:peliculas){
        generos.add(p.getGenero());
    }
    for (String genero : generos) {
        System.out.println("- " + genero);
    }
    System.out.print("\nEscribe el género que quieres buscar: ");
    String generoBuscado = scanner.nextLine();

    System.out.println("\nResultados:");
    for (Pelicula p : peliculas) {
        if (p.getGenero().equalsIgnoreCase(generoBuscado)) {
            System.out.println(p); // Imprime el registro completo
        }
    }
}

public void buscarTitulo(List<Pelicula> peliculas){
    System.out.print("\nIntroduce el título (o una parte de él): ");
    String textoBusqueda = scanner.nextLine().toLowerCase();

    System.out.println("\nResultados:");
    boolean hayCoincidencias = false;
    for (Pelicula p : peliculas) {
        if (p.getNombre().toLowerCase().contains(textoBusqueda)) {
            System.out.println(p);
            hayCoincidencias = true;
        }
    }

    if (!hayCoincidencias) {
        System.out.println("No se han encontrado películas con ese texto.");
    }
}
public void generarXML(List<Pelicula> peliculas){
    try {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        // Elemento raíz
        Element rootElement = doc.createElement("catalogo");
        doc.appendChild(rootElement);

        for (Pelicula p : peliculas){
            Element pelicula = doc.createElement("pelicula");
            rootElement.appendChild(pelicula);

            pelicula.setAttribute("id", Integer.toString(p.getId()));

            Element nombre = doc.createElement("nombre");
            nombre.appendChild(doc.createTextNode(p.getNombre()));
            pelicula.appendChild(nombre);

            Element autor = doc.createElement("autor");
            autor.appendChild(doc.createTextNode(p.getAutor()));
            pelicula.appendChild(autor);

            Element anio = doc.createElement("anio");
            anio.appendChild(doc.createTextNode(Integer.toString(p.getAnio())));
            pelicula.appendChild(anio);

            Element genero = doc.createElement("genero");
            genero.appendChild(doc.createTextNode(p.getGenero()));
            pelicula.appendChild(genero);
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult((new File("peliculas.xml")));

        transformer.transform(source,result);

        System.out.println("Archivo XML creado con éxito.");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
public static void exportarXML(String nombreFichero){
    try {
        File xmlFile = new File(nombreFichero);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("pelicula");
        List<Pelicula> peliculasLeidas = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                // Extraemos el atributo "id"
                int id = Integer.parseInt(eElement.getAttribute("id"));

                // Extraemos el texto de los hijos buscando por el nombre de su etiqueta
                String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
                String autor = eElement.getElementsByTagName("autor").item(0).getTextContent();
                int anio = Integer.parseInt(eElement.getElementsByTagName("anio").item(0).getTextContent());
                String genero = eElement.getElementsByTagName("genero").item(0).getTextContent();

                // Reconstruimos el objeto Pelicula y lo añadimos a nuestra lista
                Pelicula pelicula = new Pelicula(id, nombre, autor, anio, genero);
                peliculasLeidas.add(pelicula);
            }
        }
        // 4. Mostramos el resultado por pantalla
        System.out.println("\n--- Datos cargados desde XML ---");
        for (Pelicula p : peliculasLeidas) {
            System.out.println(p);
        }
    }catch (Exception e){
        System.out.println("Ocurrió un error al procesar el XML: " + e.getMessage());
        e.printStackTrace();
    }

}