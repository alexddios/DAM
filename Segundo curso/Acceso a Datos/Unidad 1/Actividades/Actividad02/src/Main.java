import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.util.Scanner;
Scanner scanner = new Scanner(System.in);

void main() throws IOException, JDOMException {
    int numero = -1;
    Map<Integer,Pelicula> peliculas = leerPeliculas();
    do {
        System.out.print("""
                ========== CATÁLOGO DE PELÍCULAS ==========
                1. Mostrar todas las películas
                2. Buscar una película por ID
                3. Añadir una película
                4. Modificar una película por ID
                5. Eliminar una película por ID
                0. Salir
                ==========================================
                Selecciona una opción: """);
        try {
            numero = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            numero = -1;
        }
        switch (numero){
            case 1:
                mostrarPeliculas(peliculas);
                break;
            case 2:
                buscarID(peliculas);
                break;
            case 3:
                aniadirPelicula(peliculas);
                break;
            case 4:
                modificarPeliculaID(peliculas);
                break;
//            case 5:
//                eliminarPeliculaID("peliculas.xml");
//                break;
            case 0:
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Opción no válida. Inténtalo de nuevo.");
        }
    } while (numero != 0);
}
public Map<Integer,Pelicula> leerPeliculas() throws IOException, JDOMException {
    Map<Integer,Pelicula> res = new HashMap<>();
    File file = new File("peliculas.xml");
    SAXBuilder saxBuilder = new SAXBuilder();
    Document document = saxBuilder.build(file);

    Element raiz = document.getRootElement();

    for (Element pelicula : raiz.getChildren("pelicula")){
        Pelicula p = new Pelicula(
                Integer.parseInt(pelicula.getAttributeValue("id")),
                pelicula.getChildText("nombre"),
                pelicula.getChildText("autor"),
                Integer.parseInt(pelicula.getChildText("anio")),
                pelicula.getChildText("genero"));
        res.put(Integer.parseInt(pelicula.getAttributeValue("id")),p);
    }
    return res;
}
public void mostrarPeliculas(Map<Integer, Pelicula> peliculas){
    for(Pelicula p : peliculas.values()){
        System.out.println(p);
    }
}
public void buscarID(Map<Integer,Pelicula> peliculas){
    System.out.print("Id de la película: ");
    Integer id = Integer.parseInt(scanner.nextLine());
    System.out.println(peliculas.get(id));
}

public void aniadirPelicula(Map<Integer,Pelicula> peliculas){
    System.out.print("Nombre de la película: ");
    String nombre = scanner.nextLine();
    System.out.print("Autor de la película: ");
    String autor = scanner.nextLine();
    System.out.print("Año de la película: ");
    int anio = Integer.parseInt(scanner.nextLine());
    System.out.print("Genero de la película: ");
    String genero = scanner.nextLine();

    int maxID =0;
    for (Integer id: peliculas.keySet()){
        if(id > maxID){
            maxID=id;
        }
    }
    int nuevoId = maxID+1;
    try {
        File file = new File("peliculas.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(file);
        Element raiz = document.getRootElement();

        Element nuevaPelicula = new Element("pelicula");
        nuevaPelicula.setAttribute("id",String.valueOf(nuevoId));

        nuevaPelicula.addContent(new Element("nombre").setText(nombre));
        nuevaPelicula.addContent(new Element("autor").setText(autor));
        nuevaPelicula.addContent(new Element("anio").setText(String.valueOf(anio)));
        nuevaPelicula.addContent(new Element("genero").setText(genero));

        raiz.addContent(nuevaPelicula);

        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new FileWriter("peliculas.xml"));

        peliculas.put(nuevoId, new Pelicula(nuevoId, nombre, autor, anio, genero));

        System.out.printf("\nPelícula ' %s ' añadida con éxito con el ID: %d \n",nombre,nuevoId);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
public void modificarPeliculaID(Map<Integer,Pelicula> peliculas){
    try {
        File xmlFile = new File("peliculas.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(xmlFile);

        Element raiz = document.getRootElement();

        Element peliculaMod = null;
        System.out.print("Introduce el id de la película: ");
        String id = scanner.nextLine();

        for (Element pelicula : raiz.getChildren("pelicula")){
            if(pelicula.getAttributeValue("id").equals(id)){
                peliculaMod = pelicula;
                break;
            }
        }
        if(peliculaMod != null){
            System.out.printf("""
                    \nDatos actuales de la película:
                    Nombre: %s
                    Autor: %s
                    Año: %s
                    Género: %s
                    """,peliculaMod.getChildText("nombre"),
                    peliculaMod.getChildText("autor"),
                    peliculaMod.getChildText("anio"),
                    peliculaMod.getChildText("genero"));

            boolean continuar = true;
            boolean modificado = false;

            while (continuar){
                System.out.print("""
                        \n¿Qué campo deseas modificar?
                        1. Nombre
                        2. Autor
                        3. Año
                        4. Genero
                        0. Terminar y guardar cambios
                        Elige una opción: 
                        """);
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion){
                    case 1:
                        System.out.print("Nuevo nombre: ");
                        peliculaMod.getChild("nombre").setText(scanner.nextLine());
                        modificado=true;
                        break;
                    case 2:
                        System.out.print("Nuevo autor: ");
                        peliculaMod.getChild("autor").setText(scanner.nextLine());
                        modificado = true;
                        break;
                    case 3:
                        System.out.print("Nuevo año: ");
                        peliculaMod.getChild("anio").setText(scanner.nextLine());
                        modificado = true;
                        break;
                    case 4:
                        System.out.print("Nuevo género: ");
                        peliculaMod.getChild("genero").setText(scanner.nextLine());
                        modificado = true;
                        break;
                    case 0:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, elige un número del 0 al 4.");
                }
            }
            if(modificado){
                XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
                xmlOutputter.output(document, new FileWriter("peliculas.xml"));
                peliculas.put(Integer.parseInt(id), new Pelicula(
                        Integer.parseInt(id),
                        peliculaMod.getChildText("nombre"),
                        peliculaMod.getChildText("autor"),
                        Integer.parseInt(peliculaMod.getChildText("anio")),
                        peliculaMod.getChildText("genero")
                ));
                System.out.println("Cambios guardados correctamente");
            }else {
                System.out.println("No se realizó ninguna modificación");
            }
        }else {
            System.out.printf("No se encontró la película con el id = %s",id);
        }
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new FileWriter("peliculas.xml"));

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}