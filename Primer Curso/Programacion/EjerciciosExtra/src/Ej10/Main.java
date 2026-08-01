package Ej10;

public class Main {
    public static void main(String[] args){
        Libro libro = new Libro("Los tres mosqueteros","Alex De Dios Pallicer",2);
        for (int i=0; i<=2;i++){
            libro.prestar();
            libro.info();
        }
        libro.devolver();
        libro.info();
        libro.prestar();
        libro.info();
    }
}
