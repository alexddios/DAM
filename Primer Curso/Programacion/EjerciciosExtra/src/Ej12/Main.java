package Ej12;

public class Main {
    public static void main(String[] args) {
        Ascensor ascensor = new Ascensor(10); // Límite máximo 10 [cite: 7]

        System.out.println("--- Prueba 1: Movimiento básico ---");
        ascensor.subir(); // Debería ir al piso 1
        ascensor.mostrarEstado();

        System.out.println("\n--- Prueba 2: Seguridad de puertas ---");
        ascensor.abrirPuertas();
        ascensor.subir(); // Debería fallar: puertas abiertas [cite: 98]
        ascensor.irA(5);  // Debería fallar: puertas abiertas

        System.out.println("\n--- Prueba 3: Límites y Teletransporte ---");
        ascensor.cerrarPuertas();
        ascensor.irA(10); // Ir al piso máximo
        ascensor.subir(); // Debería fallar: ya está en el máximo [cite: 20]
        ascensor.irA(0);  // Volver a planta baja
        ascensor.bajar(); // Debería fallar: no hay piso -1 [cite: 21]

        ascensor.mostrarEstado();
    }
}
