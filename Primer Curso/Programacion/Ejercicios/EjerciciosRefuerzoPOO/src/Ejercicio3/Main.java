package Ejercicio3;

public class Main {
    public static void main(String[] args) {
        TargetaTransport t = new TargetaTransport(2.00);
        t.estat();
        t.pagarViatge();
        t.pagarViatge(); // aquest ha de fallar
        t.estat();
        t.recarregar(-5); // no ha de permetre
        t.recarregar(10);
        t.pagarViatge();
        t.estat();
    }
}
