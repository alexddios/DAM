package com.alex.servicio;

import com.alex.modelo.*;
import com.alex.repositorio.*;

import java.io.IOException;

public class CatalogoPeliculas {
    private final PeliculaJsonRepository repositorio;
    private Catalogo catalogo;

    public CatalogoPeliculas(PeliculaJsonRepository jsonRepository) {
        this.repositorio = jsonRepository;
    }
    public void cargar() throws IOException{
        this.catalogo = repositorio.cargar();
    }
    public void usar(Catalogo c){
        this.catalogo = c;
    }
    public boolean estaCargado(){
        return catalogo != null;
    }
    public boolean estaVacio() {
        return this.catalogo.getPeliculas().isEmpty();
    }

    public Iterable<Pelicula> obtenerTodas() {
        return catalogo.getPeliculas();
    }

    public Pelicula buscar(int id) {
        for (Pelicula p : catalogo.getPeliculas()){
            if (p.getId() == id) return p;
        }
        return null;
    }

    public Pelicula añadir(String titulo, String director, int año, String genero) throws IOException {
        int id = siguienteId();
        Pelicula pelicula = new Pelicula(id, titulo, director, año, genero);
        catalogo.getPeliculas().add(pelicula);
        try {
            repositorio.guardar(catalogo);
        } catch (IOException e) {
            catalogo.getPeliculas().remove(pelicula);
            throw e;
        }
        return pelicula;
    }

    public boolean modificar(Pelicula pelicula, String titulo, String director, int año, String genero)
            throws IOException {
        Pelicula anterior = new Pelicula(
                pelicula.getId(),
                pelicula.getTitulo(),
                pelicula.getDirector(),
                pelicula.getAño(),
                pelicula.getGenero());

        pelicula.setTitulo(titulo);
        pelicula.setDirector(director);
        pelicula.setAño(año);
        pelicula.setGenero(genero);

        try {
            repositorio.guardar(catalogo);
            return true;
        } catch (IOException e) {
            restaurar(pelicula, anterior);
            throw e;
        }
    }

    public void eliminar(Pelicula pelicula) throws IOException {
        int posicion = catalogo.getPeliculas().indexOf(pelicula);
        catalogo.getPeliculas().remove(pelicula);
        try {
            repositorio.guardar(catalogo);
        } catch (IOException e) {
            catalogo.getPeliculas().add(posicion, pelicula);
            throw e;
        }
    }

    private int siguienteId() {
        int maxId = 0;
        for (Pelicula p : catalogo.getPeliculas()) {
            if (p.getId() > maxId) {
                maxId = p.getId();
            }
        }
        return maxId + 1;
    }

    private void restaurar(Pelicula destino, Pelicula origen) {
        destino.setTitulo(origen.getTitulo());
        destino.setDirector(origen.getDirector());
        destino.setAño(origen.getAño());
        destino.setGenero(origen.getGenero());
    }
    public boolean existe(){
        return repositorio.existe();
    }
}
