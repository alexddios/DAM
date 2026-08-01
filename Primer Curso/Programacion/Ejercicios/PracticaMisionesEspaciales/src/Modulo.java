public abstract class Modulo {
    protected String id;
    protected double massaKg;

    public Modulo(String id, double massaKg) {
        this.id = id;
        this.massaKg = massaKg;
    }

    public final double getMassaKg() {
        return massaKg;
    }
    public abstract String nomTipus();

    public final String getId() {
        return id;
    }
}
