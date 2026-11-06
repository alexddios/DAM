package com.alex;

import com.alex.repositorio.*;
import com.alex.servicio.*;
import com.alex.vista.*;

public class Main {
     static void main(String[] args) {
        PeliculaXmlRepository xmlRepository = new PeliculaXmlRepository("peliculas.xml");
        // Un único repositorio JSON, compartido por el migrador y el catálogo
        PeliculaJsonRepository jsonRepository = new PeliculaJsonRepository("peliculas.json");
        MigradorXmlJson migradorXmlJson = new MigradorXmlJson(xmlRepository, jsonRepository);
        CatalogoPeliculas catalogoPeliculas = new CatalogoPeliculas(jsonRepository);
        Consola consola = new Consola(catalogoPeliculas, migradorXmlJson);
        consola.ejecutar();
    }
}
