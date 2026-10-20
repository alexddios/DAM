import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CatalogoPeliculas {
    private final PeliculaXmlRepository repositorio;
    private final Map<Integer, Pelicula> peliculas;

    public CatalogoPeliculas(PeliculaXmlRepository repositorio) {
        this.repositorio = repositorio;
        this.peliculas = repositorio.cargar();
    }

    public boolean estaVacio() {
        return peliculas.isEmpty();
    }

    public Iterable<Pelicula> obtenerTodas() {
        return peliculas.values();
    }

    public Pelicula buscar(int id) {
        return peliculas.get(id);
    }

    public Pelicula aniadir(String nombre, String autor, int anio, String genero) throws IOException {
        int id = siguienteId();
        Pelicula pelicula = new Pelicula(id, nombre, autor, anio, genero);
        peliculas.put(id, pelicula);
        try {
            repositorio.guardar(peliculas);
        } catch (IOException e) {
            peliculas.remove(id);
            throw e;
        }
        return pelicula;
    }

    public boolean modificar(Pelicula pelicula, String nombre, String autor, int anio, String genero)
            throws IOException {
        Pelicula anterior = new Pelicula(
                pelicula.getId(),
                pelicula.getNombre(),
                pelicula.getAutor(),
                pelicula.getAnio(),
                pelicula.getGenero());

        pelicula.setNombre(nombre);
        pelicula.setAutor(autor);
        pelicula.setAnio(anio);
        pelicula.setGenero(genero);

        try {
            repositorio.guardar(peliculas);
            return true;
        } catch (IOException e) {
            restaurar(pelicula, anterior);
            throw e;
        }
    }

    public void eliminar(Pelicula pelicula) throws IOException {
        peliculas.remove(pelicula.getId());
        try {
            repositorio.guardar(peliculas);
        } catch (IOException e) {
            peliculas.put(pelicula.getId(), pelicula);
            throw e;
        }
    }

    private int siguienteId() {
        int maxId = 0;
        for (Integer id : peliculas.keySet()) {
            if (id > maxId) {
                maxId = id;
            }
        }
        return maxId + 1;
    }

    private void restaurar(Pelicula destino, Pelicula origen) {
        destino.setNombre(origen.getNombre());
        destino.setAutor(origen.getAutor());
        destino.setAnio(origen.getAnio());
        destino.setGenero(origen.getGenero());
    }
}
