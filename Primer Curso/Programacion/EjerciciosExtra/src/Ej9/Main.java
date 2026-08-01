package Ej9;

public class Main {
    public static void main(String[] args) {
        Comandament mando = new Comandament();

        System.out.println("--- Prueba 1: TV Apagada ---");
        mando.estado();
        mando.subirVolumen(); // No debería dejar
        mando.cambiarCanal(5); // No debería dejar

        System.out.println("\n--- Prueba 2: Encendido y Ajustes ---");
        mando.botonEncender();
        mando.subirVolumen();
        mando.subirVolumen();
        mando.cambiarCanal(25);
        mando.estado();

        System.out.println("\n--- Prueba 3: Límites ---");
        mando.cambiarCanal(60); // Canal inexistente
        for(int i = 0; i < 110; i++) { // Intentamos superar el máximo
            mando.subirVolumen();
        }
        mando.estado(); // Debería marcar volumen 100

        System.out.println("\n--- Prueba 4: Apagar ---");
        mando.botonEncender();
        mando.estado();
    }
}
