package botiga.model;

import botiga.utils.CalculadoraImpostos;

public class LiniaComanda {
    private final int quantitat;
    private double preuUnitari;
    private Producte producte;

    public LiniaComanda(Producte producte, int quantitat, double preuUnitari) {
        this.producte = producte;
        this.quantitat = quantitat;
        this.preuUnitari = preuUnitari;
    }

    public double subTotal() {
        return quantitat * preuUnitari;
    }

    public double calcularImpost() {
        return CalculadoraImpostos.calcular(subTotal());
    }
}