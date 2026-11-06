package com.alex.vista;

import com.alex.modelo.*;
import com.alex.servicio.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;

public class Consola {
    private final CatalogoPeliculas catalogo;
    private final Scanner scanner;
    private final MigradorXmlJson migradorXmlJson;

    public Consola(CatalogoPeliculas catalogo, MigradorXmlJson migradorXmlJson) {
        this.catalogo = catalogo;
        this.scanner = new Scanner(System.in);
        this.migradorXmlJson=migradorXmlJson;
    }

    public void ejecutar() {
        try {
            catalogo.cargar();
        }catch (FileNotFoundException e){
            System.out.println( "No existe peliculas.json. Usa la opción 1 para convertir el XML.");
        }catch (JsonProcessingException e){
            System.out.println("El JSON tiene un formato incorrecto.");
        }catch (IOException e){
            System.out.println("Error al leer el fichero.");
        }
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("");
            if(opcion>=2 && opcion <=6 && !catalogo.estaCargado()){
                System.out.println("Primero convierte el XML (opción 1)");
                continue;
            }

            switch (opcion) {
                case 1:
                    convertirXml();
                    break;
                case 2:
                    mostrarPeliculas();
                    break;
                case 3:
                    buscarPelicula();
                    break;
                case 4:
                    aniadirPelicula();
                    break;
                case 5:
                    modificarPelicula();
                    break;
                case 6:
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
                1. Convertir XML a JSON
                2. Mostrar todas las películas
                3. Buscar una película por ID
                4. Añadir una película
                5. Modificar una película por ID
                6. Eliminar una película por ID
                0. Salir
                ==========================================
                Selecciona una opción: """);
    }
    // El XML solo se lee aquí: si no existe, PeliculaXmlRepository informa y detiene el programa.
    // Si el JSON ya existe, se pregunta antes para no perder los cambios hechos con el CRUD.
    private void convertirXml(){
        if (catalogo.existe()){
            if(!leerSiNo("El JSON ya existe. ¿Sobrescribir? (s/n): ")){
                System.out.println("Conversión cancelada");
                return;
            }
        }
        try{
           Catalogo nuevo = migradorXmlJson.migrar();
           this.catalogo.usar(nuevo);
            System.out.printf("Migradas %d películas \n",nuevo.getPeliculas().size());
        } catch (IOException e) {
            System.out.printf("❌ Error al convertir el XML a JSON: %s%n", e.getMessage());
        }

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
        String titulo = leerTexto("Título de la película: ");
        String director = leerTexto("Director de la película: ");
        int año = leerAño("Año de la película: ");
        String genero = leerTexto("Género de la película: ");

        try {
            Pelicula pelicula = catalogo.añadir(titulo, director, año, genero);
            System.out.printf("\n✅ Película '%s' añadida con éxito con el ID: %d \n",
                    pelicula.getTitulo(), pelicula.getId());
        } catch (IOException e) {
            System.out.println("❌ Error al guardar el fichero JSON: " + e.getMessage());
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
                Titulo: %s
                Director: %s
                Año: %d
                Género: %s
                """, pelicula.getTitulo(), pelicula.getDirector(), pelicula.getAño(), pelicula.getGenero());

        String titulo = pelicula.getTitulo();
        String director = pelicula.getDirector();
        int año = pelicula.getAño();
        String genero = pelicula.getGenero();
        boolean modificado = false;
        boolean continuar = true;

        while (continuar) {
            System.out.print("""
                    
                    ¿Qué campo deseas modificar?
                    1. Titulo
                    2. Director
                    3. Año
                    4. Genero
                    0. Terminar y guardar cambios
                    Elige una opción:
                    """);
            int opcion = leerEntero("");

            switch (opcion) {
                case 1:
                    titulo = leerTexto("Nuevo titulo: ");
                    modificado = true;
                    break;
                case 2:
                    director = leerTexto("Nuevo director: ");
                    modificado = true;
                    break;
                case 3:
                    año = leerAño("Nuevo año: ");
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
            catalogo.modificar(pelicula, titulo, director, año, genero);
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
    // Si el dato no es válido, se repite la petición en lugar de volver al menú
    private String leerTexto(String mensaje) {
        while (true){
            System.out.print(mensaje);
            String respuesta = scanner.nextLine().trim();
            if(!respuesta.isEmpty()){
                return respuesta;
            }
            System.out.println("❌ Este campo es obligatorio.");
        }
    }

    // Si el dato no es un número entero, se repite la petición en lugar de volver al menú
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Debes introducir un número entero válido.");
            }
        }
    }

    // Año entre 1888 (primera película conocida) y 10 años a partir del actual.
    // Si está fuera de rango, se repite la petición
    private int leerAño(String mensaje) {
        int maximo = Year.now().getValue() + 10;
        while (true) {
            int año = leerEntero(mensaje);
            if (año >= 1888 && año <= maximo) {
                return año;
            }
            System.out.printf("❌ El año debe estar entre 1888 y %d.%n", maximo);
        }
    }
    private boolean leerSiNo(String mensaje){
    while (true){
        System.out.print(mensaje);
        String respuesta = scanner.nextLine().trim();
        if(respuesta.equalsIgnoreCase("s")){
            return true;
        }
        if (respuesta.equalsIgnoreCase("n")){
            return false;
        }
        System.out.println("Responde 's' o 'n'.");
    }

    }
}
