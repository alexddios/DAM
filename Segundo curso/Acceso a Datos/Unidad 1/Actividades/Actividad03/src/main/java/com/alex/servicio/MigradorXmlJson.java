package com.alex.servicio;

import com.alex.modelo.*;
import com.alex.repositorio.*;

import java.io.IOException;
import java.util.List;

public class MigradorXmlJson {
    private final PeliculaXmlRepository xmlRepository;
    private final PeliculaJsonRepository jsonRepository;

    public MigradorXmlJson(PeliculaXmlRepository xmlRepository, PeliculaJsonRepository jsonRepository) {
        this.xmlRepository = xmlRepository;
        this.jsonRepository = jsonRepository;
    }
    public Catalogo migrar() throws IOException {
        List<Pelicula> peliculas = this.xmlRepository.cargar();
        Catalogo catalogo = new Catalogo(peliculas);
        this.jsonRepository.guardar(catalogo);
        return catalogo;
    }
}
