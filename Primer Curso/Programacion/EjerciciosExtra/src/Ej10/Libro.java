package Ej10;

public class Libro {
    private String titulo;
    private String autor;
    private int ejemplaresDisponibles;

    public Libro(String titulo,String autor, int ejemplaresDisponibles){
        this.titulo=titulo;
        this.autor=autor;
        this.ejemplaresDisponibles=ejemplaresDisponibles;
    }
    boolean prestar(){
        if (this.ejemplaresDisponibles>0){
            this.ejemplaresDisponibles--;
            System.out.println("Se ha prestado un libro");
            return true;

        }else{
            System.out.println("No quedan mas libros");
            return false;
        }
    }
    void devolver(){
        this.ejemplaresDisponibles++;
        System.out.println("Se ha devuelto un libro");
    }
    void info(){
        System.out.println("Libro: " + this.titulo + " de " + this.autor + " | Ejemplares Diponibles: " + this.ejemplaresDisponibles);
    }
}
