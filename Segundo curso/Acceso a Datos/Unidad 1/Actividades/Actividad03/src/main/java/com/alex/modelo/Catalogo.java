package com.alex.modelo;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private List<Pelicula> peliculas;

    public Catalogo(List <Pelicula> peliculas) {
        this.peliculas = peliculas;
    }
    public Catalogo(){
        this.peliculas=new ArrayList<>();
    }

    public List <Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List <Pelicula> peliculas) {
        this.peliculas = peliculas;
    }
}
