package botiga.model;

public class CupoDescompte {
    private final String codi;
    private double percent;

    public CupoDescompte(String codi, double percent) {
        this.codi = codi;
        this.percent = percent;
    }

    public double getPercent() { return percent; }
}