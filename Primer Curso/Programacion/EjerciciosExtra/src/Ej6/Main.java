package Ej6;

public class Main {
    static void main(){
        Producte libreta = new Producte("Libreta",2.50);
        libreta.setQuantitat(10);
        libreta.afegirStock(5);
        libreta.vendre(20);
        libreta.mostrar();
    }



}
