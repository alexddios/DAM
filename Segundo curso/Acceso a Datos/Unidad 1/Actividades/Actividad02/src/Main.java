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
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    // Método principal: ya no lanza excepciones hacia fuera
    public static void main(String[] args) {
        int numero = -1;
        // Cargamos las películas. Si el archivo está corrupto, el programa se detendrá para proteger los datos.
        Map<Integer, Pelicula> peliculas = leerPeliculas();

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

            numero = leerEntero("");

            switch (numero) {
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
                case 5:
                    eliminarPeliculaID(peliculas);
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("❌ Opción no válida. Inténtalo de nuevo.");
            }
        } while (numero != 0);
    }

    // MÉTODO AUXILIAR: Garantiza que el usuario introduzca un número y no texto
    public static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Debes introducir un número entero válido.");
            }
        }
    }

    public static Map<Integer, Pelicula> leerPeliculas() {
        Map<Integer, Pelicula> res = new HashMap<>();
        File file = new File("peliculas.xml");

        // SITUACIÓN 1: El fichero XML no existe
        if (!file.exists() || file.length() == 0) {
            System.out.println("⚠️ Aviso: El fichero 'peliculas.xml' no existe o está vacío. Se creará uno nuevo al añadir la primera película.");
            return res;
        }

        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(file);
            Element raiz = document.getRootElement();

            for (Element pelicula : raiz.getChildren("pelicula")) {
                Pelicula p = new Pelicula(
                        Integer.parseInt(pelicula.getAttributeValue("id")),
                        pelicula.getChildText("nombre"),
                        pelicula.getChildText("autor"),
                        Integer.parseInt(pelicula.getChildText("anio")),
                        pelicula.getChildText("genero"));
                res.put(Integer.parseInt(pelicula.getAttributeValue("id")), p);
            }
        } catch (JDOMException | IOException e) {
            // SITUACIÓN 2: El documento XML contiene errores de formato
            System.out.println("❌ ERROR CRÍTICO: El archivo 'peliculas.xml' contiene errores de formato (" + e.getMessage() + ").");
            System.out.println("🛡️ Por seguridad, se ha bloqueado la carga para evitar sobrescribir y perder tus datos. Repara el archivo manualmente.");
            System.exit(1); // Cerramos el programa para evitar catástrofes
        }
        return res;
    }

    public static void mostrarPeliculas(Map<Integer, Pelicula> peliculas) {
        if (peliculas.isEmpty()) {
            System.out.println("📭 El catálogo está vacío.");
            return;
        }
        for (Pelicula p : peliculas.values()) {
            System.out.println(p);
        }
    }

    public static void buscarID(Map<Integer, Pelicula> peliculas) {
        int id = leerEntero("Id de la película: ");
        // SITUACIÓN 3: Se introduce un ID que no existe
        if (peliculas.containsKey(id)) {
            System.out.println(peliculas.get(id));
        } else {
            System.out.println("❌ No se encontró ninguna película con el ID: " + id);
        }
    }

    public static void aniadirPelicula(Map<Integer, Pelicula> peliculas) {
        System.out.print("Nombre de la película: ");
        String nombre = scanner.nextLine();
        System.out.print("Autor de la película: ");
        String autor = scanner.nextLine();
        // SITUACIÓN 4: Valores no válidos en campos numéricos
        int anio = leerEntero("Año de la película: ");
        System.out.print("Género de la película: ");
        String genero = scanner.nextLine();

        int maxID = 0;
        for (Integer id : peliculas.keySet()) {
            if (id > maxID) {
                maxID = id;
            }
        }
        int nuevoId = maxID + 1;

        try {
            File file = new File("peliculas.xml");
            Document document;
            Element raiz;

            // SITUACIÓN 1 & 2: Gestión dinámica del archivo (si no existe, lo creamos)
            if (file.exists() && file.length() > 0) {
                SAXBuilder saxBuilder = new SAXBuilder();
                document = saxBuilder.build(file);
                raiz = document.getRootElement();
            } else {
                raiz = new Element("catalogo");
                document = new Document(raiz);
            }

            Element nuevaPelicula = new Element("pelicula");
            nuevaPelicula.setAttribute("id", String.valueOf(nuevoId));
            nuevaPelicula.addContent(new Element("nombre").setText(nombre));
            nuevaPelicula.addContent(new Element("autor").setText(autor));
            nuevaPelicula.addContent(new Element("anio").setText(String.valueOf(anio)));
            nuevaPelicula.addContent(new Element("genero").setText(genero));

            raiz.addContent(nuevaPelicula);

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(document, new FileWriter("peliculas.xml"));

            peliculas.put(nuevoId, new Pelicula(nuevoId, nombre, autor, anio, genero));
            System.out.printf("\n✅ Película '%s' añadida con éxito con el ID: %d \n", nombre, nuevoId);

        } catch (Exception e) {
            // SITUACIÓN 5: Error al guardar
            System.out.println("❌ Error al acceder o guardar el fichero XML: " + e.getMessage());
        }
    }

    public static void modificarPeliculaID(Map<Integer, Pelicula> peliculas) {
        File xmlFile = new File("peliculas.xml");
        if (!xmlFile.exists()) {
            System.out.println("❌ No hay fichero XML. Añade una película primero.");
            return;
        }

        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(xmlFile);
            Element raiz = document.getRootElement();

            String id = String.valueOf(leerEntero("Introduce el id de la película: "));
            Element peliculaMod = null;

            for (Element pelicula : raiz.getChildren("pelicula")) {
                if (pelicula.getAttributeValue("id").equals(id)) {
                    peliculaMod = pelicula;
                    break;
                }
            }

            if (peliculaMod != null) {
                System.out.printf("""
                        \nDatos actuales de la película:
                        Nombre: %s
                        Autor: %s
                        Año: %s
                        Género: %s
                        """, peliculaMod.getChildText("nombre"),
                        peliculaMod.getChildText("autor"),
                        peliculaMod.getChildText("anio"),
                        peliculaMod.getChildText("genero"));

                boolean continuar = true;
                boolean modificado = false;

                while (continuar) {
                    System.out.print("""
                            \n¿Qué campo deseas modificar?
                            1. Nombre
                            2. Autor
                            3. Año
                            4. Genero
                            0. Terminar y guardar cambios
                            Elige una opción: 
                            """);
                    int opcion = leerEntero("");

                    switch (opcion) {
                        case 1:
                            System.out.print("Nuevo nombre: ");
                            peliculaMod.getChild("nombre").setText(scanner.nextLine());
                            modificado = true;
                            break;
                        case 2:
                            System.out.print("Nuevo autor: ");
                            peliculaMod.getChild("autor").setText(scanner.nextLine());
                            modificado = true;
                            break;
                        case 3:
                            // Validamos que el año sea un número antes de modificar
                            int nuevoAnio = leerEntero("Nuevo año: ");
                            peliculaMod.getChild("anio").setText(String.valueOf(nuevoAnio));
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
                            System.out.println("❌ Opción no válida. Por favor, elige un número del 0 al 4.");
                    }
                }

                if (modificado) {
                    XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
                    xmlOutputter.output(document, new FileWriter("peliculas.xml"));

                    peliculas.put(Integer.parseInt(id), new Pelicula(
                            Integer.parseInt(id),
                            peliculaMod.getChildText("nombre"),
                            peliculaMod.getChildText("autor"),
                            Integer.parseInt(peliculaMod.getChildText("anio")),
                            peliculaMod.getChildText("genero")
                    ));
                    System.out.println("✅ Cambios guardados correctamente");
                } else {
                    System.out.println("No se realizó ninguna modificación");
                }
            } else {
                System.out.printf("❌ No se encontró la película con el id = %s\n", id);
            }
            // ¡Se eliminaron las líneas 177 y 178 del código original que sobreescribían el archivo por error!

        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }

    public static void eliminarPeliculaID(Map<Integer, Pelicula> peliculas) {
        File xmlFile = new File("peliculas.xml");
        if (!xmlFile.exists()) {
            System.out.println("❌ No hay fichero XML. Añade una película primero.");
            return;
        }

        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(xmlFile);
            Element raiz = document.getRootElement();

            String id = String.valueOf(leerEntero("Introduce el id de la película a eliminar: "));
            Element peliculaEliminar = null;

            for (Element pelicula : raiz.getChildren("pelicula")) {
                if (pelicula.getAttributeValue("id").equals(id)) {
                    peliculaEliminar = pelicula;
                    break;
                }
            }

            if (peliculaEliminar != null) {
                raiz.removeContent(peliculaEliminar);
                peliculas.remove(Integer.parseInt(id));

                XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
                xmlOutputter.output(document, new FileWriter("peliculas.xml"));

                System.out.printf("✅ Se eliminó la película con id = %s\n", id);

            } else {
                System.out.printf("❌ No se encontró la película con el id = %s\n", id);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al eliminar la película: " + e.getMessage());
        }
    }
}