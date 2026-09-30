public class Pelicula {
    private int id;
    private String nombre;
    private String autor;
    private int anio;
    private String genero;

    public Pelicula(int id,String nombre, String autor, int anio, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.anio = anio;
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", autor='" + autor + '\'' +
                ", anio=" + anio +
                ", genero='" + genero + '\'' +
                '}';
    }
    public String getGenero(){
        return genero;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnio() {
        return anio;
    }
}
