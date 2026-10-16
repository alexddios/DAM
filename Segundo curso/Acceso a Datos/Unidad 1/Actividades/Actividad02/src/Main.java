public class Main {
    public static void main(String[] args) {
        PeliculaXmlRepository repositorio = new PeliculaXmlRepository("peliculas.xml");
        CatalogoPeliculas catalogo = new CatalogoPeliculas(repositorio);
        Consola consola = new Consola(catalogo);

        consola.ejecutar();
    }
}
