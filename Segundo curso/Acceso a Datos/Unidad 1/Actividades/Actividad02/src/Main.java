import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.util.Scanner;
Scanner scanner = new Scanner(System.in);

void main() throws IOException, JDOMException {
    int numero = -1;
    Map<Pelicula,Integer> peliculas = leerPeliculas();
    mostrarPeliculas(peliculas);
//    do {
//        System.out.println("""
//                ===== CATÁLOGO =====
//                1. Mostrar todas
//                2. Buscar por género
//                3. Buscar por título
//                4. Generar XML
//                5. Importar XML
//                0. Salir
//                """);
//
//        System.out.print("Elige una opción: ");
//        try {
//            numero = Integer.parseInt(scanner.nextLine());
//        } catch (NumberFormatException e) {
//            numero = -1;
//        }
//        switch (numero){
//            case 1:
//                mostrarTodas(peliculas);
//                break;
//            case 2:
//                buscarID(peliculas);
//                break;
//            case 3:
//                buscarTitulo(peliculas);
//                break;
//            case 4:
//                generarXML(peliculas);
//                break;
//            case 5:
//                exportarXML("peliculas.xml");
//                break;
//            case 0:
//                System.out.println("Saliendo del programa...");
//                break;
//            default:
//                System.out.println("Opción no válida. Inténtalo de nuevo.");
//        }
//    } while (numero != 0);
}
public Map<Pelicula,Integer> leerPeliculas() throws IOException, JDOMException {
    Map<Pelicula,Integer> res = new HashMap<>();
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
        res.put(p,Integer.parseInt(pelicula.getAttributeValue("id")));
    }
    return res;
}
public void mostrarPeliculas(Map<Pelicula,Integer> peliculas){
    for(Pelicula p : peliculas.keySet()){
        System.out.println(p);
    }
}

