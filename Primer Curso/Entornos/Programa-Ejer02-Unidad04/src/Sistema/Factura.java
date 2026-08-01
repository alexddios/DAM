package Sistema;
/**
 * Representa la entidad de datos de una Factura.
 * Esta clase aplica el patrón de Encapsulamiento, protegiendo sus atributos.
 * @author Alex
 * @version 1.0
 */

public class Factura {
    private String cliente;
    private double importeBase;
    private int tipoCliente;
    /**
     * Constructor para inicializar una factura con sus datos básicos.
     * @param cliente Nombre o razón social del cliente.
     * @param importeBase Monto inicial antes de impuestos y descuentos.
     * @param tipoCliente Categoría (1: Particular, 2: Empresa, 3: VIP).
     */

    public Factura(String cliente, double importeBase, int tipoCliente) {
        this.cliente = cliente;
        this.importeBase = importeBase;
        this.tipoCliente = tipoCliente;
    }

    // Getters y Setters
    public String getCliente() { return cliente; }
    public double getImporteBase() { return importeBase; }
    public int getTipoCliente() { return tipoCliente; }
}