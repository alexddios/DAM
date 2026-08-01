package Sistema;

/**
 * Clase encargada de la lógica de procesamiento y presentación de resultados.
 * Se ha refactorizado para separar la lógica de cálculo de la de visualización[cite: 1409, 1530].
 */
public class ProcesadorFactura {
    public static final double IVA = 0.21;
    public static final double DESC_VIP = 0.20;
    public static final double DESC_EMP = 0.10;
    public static final double DESC_PAR = 0.05;

    /**
     * Coordina el procesamiento de la factura.
     * @param factura Objeto con los datos de entrada.
     * @return El importe final formateado indirectamente en la salida.
     */
    public double procesar(Factura factura) {
        validar(factura);

        double desc = calcularDescuento(factura.getTipoCliente());
        double subtotal = factura.getImporteBase() * (1 - desc);
        double total = subtotal * (1 + IVA);

        mostrar(factura.getCliente(), total);
        return total;
    }

    private void validar(Factura f) {
        if (f.getCliente() == null || f.getCliente().isEmpty())
            throw new IllegalArgumentException("Cliente inválido [cite: 1478]");
        if (f.getImporteBase() <= 0)
            throw new IllegalArgumentException("Importe inválido [cite: 1481]");
    }

    private double calcularDescuento(int tipo) {
        return switch (tipo) {
            case 1 -> DESC_PAR;
            case 2 -> DESC_EMP;
            case 3 -> DESC_VIP;
            default -> 0.0;
        };
    }

    /**
     * Muestra el resultado final por consola con formato profesional.
     * Usa printf para limitar a 2 decimales y añadir el símbolo de moneda.
     * @param c Nombre del cliente.
     * @param t Total calculado.
     */
    private void mostrar(String c, double t) {
        // %.2f limita a dos decimales, %n es un salto de línea
        System.out.printf("Factura de: %s | Total: %.2f€%n", c, t);
    }
}