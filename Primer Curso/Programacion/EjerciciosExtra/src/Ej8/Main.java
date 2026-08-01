package Ej8;

public class Main {
    public static void main(String[] args) {
        // Creamos las instancias siguiendo el modelo de la Activitat 1 [cite: 7]
        CuentaBancaria alex = new CuentaBancaria("Alex");
        CuentaBancaria juan = new CuentaBancaria("Juan");

        System.out.println("--- Pruebas para Alex ---");
        alex.ingresar(100.0); // Ingreso válido
        alex.retirar(40.0);   // Retirada válida
        alex.retirar(100.0);  // Debería fallar: no hay suficiente saldo
        alex.ingresar(-10.0); // Debería fallar: cantidad negativa [cite: 75]
        alex.saldo();
        alex.transferir(juan,30);
        alex.saldo();

        System.out.println("\n--- Pruebas para Juan ---");
        juan.saldo();
        juan.ingresar(50.0);
        juan.retirar(20.0);
        juan.saldo();

    }
}
