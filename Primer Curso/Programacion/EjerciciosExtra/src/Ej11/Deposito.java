package Ej11;

public class Deposito {
    private double capacidadMaxima;
    private  double contenidoActual;

    public Deposito(double capacidadMaxima){
        this.capacidadMaxima=capacidadMaxima;
        this.contenidoActual=0;
    }
    void llenar(double litros) {
        if (litros <= 0) return;

        if (this.contenidoActual + litros > capacidadMaxima) {
            double loQueCabia = capacidadMaxima - contenidoActual;
            this.contenidoActual = capacidadMaxima;
            System.out.println("Se han llenado " + loQueCabia + " litros (Capacidad máxima alcanzada).");
        } else {
            this.contenidoActual += litros;
            System.out.println("Se han añadido " + litros + " litros.");
        }
    }
    void vaciar(double litros) {
        if (litros <= 0) {
            System.out.println("La cantidad a vaciar debe ser positiva.");
            return;
        }

        if (this.contenidoActual - litros < 0) {
            // Si lo que pide sacar es más de lo que tengo
            double loQueHabia = this.contenidoActual;
            this.contenidoActual = 0;
            System.out.println("Se han vaciado " + loQueHabia + " litros. El depósito ahora está vacío.");
        } else {
            // Si hay suficiente líquido
            this.contenidoActual -= litros;
            System.out.println("Se han vaciado " + litros + " litros.");
        }
    }
    void pasar(Deposito destino, double litrosARevasar) {
        // 1. ¿Cuánto podemos sacar de aquí?
        double puedoSacar = Math.min(litrosARevasar, this.contenidoActual);

        // 2. ¿Cuánto cabe allí?
        double cabeEnDestino = destino.capacidadMaxima - destino.contenidoActual;

        // 3. La transferencia real es el mínimo de ambos
        double transferenciaReal = Math.min(puedoSacar, cabeEnDestino);

        if (transferenciaReal > 0) {
            this.vaciar(transferenciaReal);
            destino.llenar(transferenciaReal);
            System.out.println("Transferencia realizada con éxito.");
        } else {
            System.out.println("No se ha podido realizar la transferencia (Origen vacío o Destino lleno).");
        }
    }
    void estado(){
        System.out.println("Litros disponibles: " + this.contenidoActual);
    }
}
