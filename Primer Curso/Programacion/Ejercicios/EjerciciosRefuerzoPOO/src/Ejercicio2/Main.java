package Ejercicio2;

public class Main {
    public static void main(String[] args) {
        Jugador j1= new Jugador("Lia");
        j1.sumarPunts(10);
        j1.actualizarRecord();
        j1.mostrar();

        j1.restarPunts(50);
        j1.actualizarRecord();
        j1.mostrar();

        j1.sumarPunts(30);
        j1.actualizarRecord();
        j1.mostrar();
    }

}