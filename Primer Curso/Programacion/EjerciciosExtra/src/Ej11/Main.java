package Ej11;

public class Main {
    public static void main(String[] args){
        Deposito gran = new Deposito(100);
        Deposito petit = new Deposito(20);

        gran.llenar(50);
        gran.pasar(petit, 30); // Petit solo puede recibir 20. ¿Qué pasa con los otros 10?
        gran.estado();
        petit.estado();
    }
}
