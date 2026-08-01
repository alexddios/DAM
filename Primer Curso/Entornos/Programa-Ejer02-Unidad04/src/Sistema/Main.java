package Sistema;
public class Main {
    public static void main(String[] args) {
        // 1. Creación de datos
        Factura miFactura = new Factura("ACME Corp", 100.0, 2);

        // 2. Creación del procesador (Lógica)
        ProcesadorFactura procesador = new ProcesadorFactura();

        // 3. Ejecución
        try {
            procesador.procesar(miFactura);
        } catch (Exception e) {
            System.err.println("Error en el proceso: " + e.getMessage());
        }
    }
}