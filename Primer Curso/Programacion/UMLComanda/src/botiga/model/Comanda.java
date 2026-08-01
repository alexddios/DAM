package botiga.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Comanda implements Exportable {
    private final int id;
    private LocalDate data;
    private EstatComanda estat;
    private String codiSeguiment;


    private final Client client;
    private CupoDescompte cupo;
    private final List<LiniaComanda> linies;

    public Comanda(int id, Client client) {
        this.id = id;
        this.client = client;
        this.data = LocalDate.now();
        this.estat = EstatComanda.PENDENT;
        this.codiSeguiment = null;
        this.cupo = null;
        this.linies = new ArrayList<>();
    }


    public boolean afegirLinia(Producte producte, int quantitat, double preuUnitari) {
        if (estat == EstatComanda.CANCELADA) return false;
        if (quantitat <= 0) return false;

        LiniaComanda nova = new LiniaComanda(producte, quantitat, preuUnitari);
        return linies.add(nova);
    }


    public void aplicarCupo(CupoDescompte cupo) {
        this.cupo = cupo;
    }
    public void llevarCupo(){
        this.cupo=null;
    }

    public double calcularTotal() {
        double base = calcularBase();
        double impostos = calcularImpostos();
        double total = base + impostos;
        return aplicarCupoIntern(total);
    }

    private double calcularBase() {
        double base = 0.0;
        for (LiniaComanda l : linies) {
            base += l.subTotal();
        }
        return base;
    }

    private double calcularImpostos() {
        double impostos = 0.0;
        for (LiniaComanda l : linies) {
            impostos += l.calcularImpost();
        }
        return impostos;
    }

    private double aplicarCupoIntern(double total) {
        if (cupo == null) return total;
        return total - (total * (cupo.getPercent() / 100.0));
    }

    @Override
    public String exportar() {
        return "Comanda ID: " + id + " - Total: " + calcularTotal();
    }
}