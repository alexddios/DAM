package Ejercicio1;

public class Main {
    public static void main(String[] args) {
        ContadorLimitat c = new ContadorLimitat(0, 10); // min/max
        c.mostrar();
        c.incrementar();
        c.incrementar();
        c.mostrar();
        c.decrementar();
        c.mostrar();
        c.setValor(10);
        c.incrementar(); // no hauria de passar de 10
        c.mostrar();
        c.setValor(-3); // no hauria de baixar de 0
        c.mostrar();
    }
}