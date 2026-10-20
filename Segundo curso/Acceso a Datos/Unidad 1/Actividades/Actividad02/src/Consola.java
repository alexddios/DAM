import java.io.IOException;
import java.util.Scanner;

public class Consola {
    private final CatalogoPeliculas catalogo;
    private final Scanner scanner;

    public Consola(CatalogoPeliculas catalogo) {
        this.catalogo = catalogo;
        this.scanner = new Scanner(System.in);
    }

    public void ejecutar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("");

            switch (opcion) {
                case 1:
                    mostrarPeliculas();
                    break;
                case 2:
                    buscarPelicula();
                    break;
                case 3:
                    aniadirPelicula();
                    break;
                case 4:
                    modificarPelicula();
                    break;
                case 5:
                    eliminarPelicula();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("❌ Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
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
    }

    private void mostrarPeliculas() {
        if (catalogo.estaVacio()) {
            System.out.println("📭 El catálogo está vacío.");
            return;
        }
        for (Pelicula pelicula : catalogo.obtenerTodas()) {
            System.out.println(pelicula);
        }
    }

    private void buscarPelicula() {
        int id = leerEntero("Id de la película: ");
        Pelicula pelicula = catalogo.buscar(id);
        if (pelicula != null) {
            System.out.println(pelicula);
        } else {
            System.out.println("❌ No se encontró ninguna película con el ID: " + id);
        }
    }

    private void aniadirPelicula() {
        String nombre = leerTexto("Nombre de la película: ");
        String autor = leerTexto("Autor de la película: ");
        int anio = leerEntero("Año de la película: ");
        String genero = leerTexto("Género de la película: ");

        try {
            Pelicula pelicula = catalogo.aniadir(nombre, autor, anio, genero);
            System.out.printf("\n✅ Película '%s' añadida con éxito con el ID: %d \n",
                    pelicula.getNombre(), pelicula.getId());
        } catch (IOException e) {
            System.out.println("❌ Error al acceder o guardar el fichero XML: " + e.getMessage());
        }
    }

    private void modificarPelicula() {
        int id = leerEntero("Introduce el id de la película: ");
        Pelicula pelicula = catalogo.buscar(id);
        if (pelicula == null) {
            System.out.println("❌ No se encontró la película con el id = " + id);
            return;
        }

        System.out.printf("""
                
                Datos actuales de la película:
                Nombre: %s
                Autor: %s
                Año: %d
                Género: %s
                """, pelicula.getNombre(), pelicula.getAutor(), pelicula.getAnio(), pelicula.getGenero());

        String nombre = pelicula.getNombre();
        String autor = pelicula.getAutor();
        int anio = pelicula.getAnio();
        String genero = pelicula.getGenero();
        boolean modificado = false;
        boolean continuar = true;

        while (continuar) {
            System.out.print("""
                    
                    ¿Qué campo deseas modificar?
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
                    nombre = leerTexto("Nuevo nombre: ");
                    modificado = true;
                    break;
                case 2:
                    autor = leerTexto("Nuevo autor: ");
                    modificado = true;
                    break;
                case 3:
                    anio = leerEntero("Nuevo año: ");
                    modificado = true;
                    break;
                case 4:
                    genero = leerTexto("Nuevo género: ");
                    modificado = true;
                    break;
                case 0:
                    continuar = false;
                    break;
                default:
                    System.out.println("❌ Opción no válida. Por favor, elige un número del 0 al 4.");
            }
        }

        if (!modificado) {
            System.out.println("No se realizó ninguna modificación");
            return;
        }

        try {
            catalogo.modificar(pelicula, nombre, autor, anio, genero);
            System.out.println("✅ Cambios guardados correctamente");
        } catch (IOException e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }

    private void eliminarPelicula() {
        int id = leerEntero("Introduce el id de la película a eliminar: ");
        Pelicula pelicula = catalogo.buscar(id);
        if (pelicula == null) {
            System.out.println("❌ No se encontró la película con el id = " + id);
            return;
        }

        try {
            catalogo.eliminar(pelicula);
            System.out.println("✅ Se eliminó la película con id = " + id);
        } catch (IOException e) {
            System.out.println("❌ Error al eliminar la película: " + e.getMessage());
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Debes introducir un número entero válido.");
            }
        }
    }
}
