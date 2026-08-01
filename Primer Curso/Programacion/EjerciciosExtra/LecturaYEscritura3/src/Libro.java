import java.util.Locale;

public class Libro {
    private String titulo;
    private String autor;
    private int anyoPublicacion;
    private double precio;

    public Libro(String titulo,String autor, int anyoPublicacion, double precio) {
        this.autor = autor;
        this.titulo = titulo;
        this.anyoPublicacion = anyoPublicacion;
        this.precio = precio;
    }

    public String lineaCSV() {
        // %.2f obliga a poner solo 2 decimales.
        // Locale.US obliga a usar el punto para los decimales.
        return String.format(Locale.US, "%s,%s,%d,%.2f", titulo, autor, anyoPublicacion, precio);
        // El precio de 10.0 saldrá como "10.00"
    }

    public static Libro fromCSV(String cadena){
        String[] partes = cadena.split(",");
        return new Libro(
                partes[0],
                partes[1],
                Integer.parseInt(partes[2]),
                Double.parseDouble(partes[3])
        );
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", anyoPublicacion=" + anyoPublicacion +
                ", precio=" + precio +
                '}';
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnyoPublicacion() {
        return anyoPublicacion;
    }

    public double getPrecio() {
        return precio;
    }
}
