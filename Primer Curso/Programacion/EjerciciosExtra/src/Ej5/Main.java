package Ej5;

public class Main {
    public static void main(String[] args) {
        Termostat t = new Termostat(30.0); // max 30
        t.pujarTemp(5);
        t.mostrar(); // Debería marcar 25.0
        t.pujarTemp(10); // Intentamos llegar a 35
        t.mostrar(); // Debería marcar 30.0 (límite)
        t.setTemperatura(-5); // Valor imposible
        t.mostrar(); // Debería seguir en 30 o el valor anterior válido
    }
}
