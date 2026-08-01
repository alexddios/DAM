import Ficheros.FI;
import Ficheros.FO;
import Ficheros.GF;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
Scanner scanner = new Scanner(System.in);
void main() {
    System.out.println("Ejercicio?");
    int ej = Integer.parseInt(scanner.nextLine());
    List<Libro> libros = new ArrayList<>();
    switch (ej){
        case 2: Ejercicio2();
        case 3: Ejercicio3();
        case 4: libros = Ejercicio4();
        case 5: Ejercicio5(libros);
        case 6: Ejercicio6();
        case 7: Ejercicio7();
    }
}
public void Ejercicio2(){
    GF.crearDirectorio("biblioteca/");
    GF.crearDirectorio("biblioteca/importacion/");
    GF.crearDirectorio("biblioteca/exportacion/");
}
public void Ejercicio3(){
    List<Libro> libros = new ArrayList<>();
    libros.add(new Libro("Autor1", "Titulo1", 2020, 10.0));
    libros.add(new Libro("Autor2", "Titulo2", 2021, 20.0));
    libros.add(new Libro("Autor3", "Titulo3", 2022, 30.0));
    libros.add(new Libro("Autor4", "Titulo4", 2023, 40.0));
    libros.add(new Libro("Autor5", "Titulo5", 2024, 50.0));
    libros.add(new Libro("Autor6", "Titulo6", 2025, 60.0));
    FO fo = new FO("biblioteca/importacion/libros.csv");
    fo.obrir(false);
    fo.gravarLinia("Titulo,Autor,Anyo,Precio");
    for (Libro l : libros){
        fo.gravarLinia(l.lineaCSV());
    }
    fo.tancar();
}
public List<Libro> Ejercicio4(){
    FI fi = new FI("biblioteca/importacion/libros.csv");
    fi.obrir();

    fi.llegirLinia();
    List<Libro> libros = new ArrayList<>();
    String linea = fi.llegirLinia();
    int numeroLibros=0;
    double precioTotal=0;
    double precioMedio = 0;
    String tituloLibroAntiguo= " ";
    int anyoPublicacion = Integer.MAX_VALUE;
    while (linea != null){
        libros.add(Libro.fromCSV(linea));
        numeroLibros++;
        linea=fi.llegirLinia();
    }
    for (Libro l : libros){
        precioTotal += l.getPrecio();
        if (l.getAnyoPublicacion() < anyoPublicacion) {
            tituloLibroAntiguo = l.getTitulo();
            anyoPublicacion = l.getAnyoPublicacion();
        }
    }
    precioMedio = precioTotal/numeroLibros;
    System.out.printf(Locale.US,"""
                Número total de libros leidos: %d
                Precio medio de todos los libros: %.2f
                Titulo del libro más antiguo : %s
            """,numeroLibros,precioMedio,tituloLibroAntiguo);

    fi.tancar();
    return libros;
}
public void Ejercicio5(List<Libro> libros){
    FO fo = new FO("biblioteca/exportacion/catalogo_clasicos.txt");
    fo.obrir(false);
    libros.addFirst(new Libro("Titulo0","Autor0",1990,5.00));
    for (Libro l:libros){
        if(l.getAnyoPublicacion()<2000){
            fo.gravarLinia(String.format(Locale.US,"[%d] %s - %s (%.2f €)",l.getAnyoPublicacion(),l.getTitulo(),l.getAutor(),l.getPrecio()));
        }
    }
    fo.tancar();
}
public void Ejercicio6(){
    FO fo = new FO("biblioteca/importacion/libros.csv");
    fo.obrir(true);
    List<Libro> nuevosLibros = new ArrayList<>();
    nuevosLibros.add(Libro.fromCSV("Titulo7,Autor7,2010,40.00"));
    nuevosLibros.add(Libro.fromCSV("Titulo8,Autor8,2004,20.00"));
    for (Libro l : nuevosLibros){
        fo.gravarLinia(l.lineaCSV());
    }
    fo.tancar();
}
public void Ejercicio7(){
    GF.borrar("biblioteca/exportacion/catalogo_clasicos.txt");
    GF.borrar("biblioteca/importacion/libros.csv");
    GF.borrar("biblioteca/exportacion");
    GF.borrar("biblioteca/importacion");
    GF.borrar("biblioteca");
}