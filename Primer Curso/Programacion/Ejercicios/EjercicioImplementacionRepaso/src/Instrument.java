public abstract class Instrument {
    protected final String codi;
    protected String nom;
    protected double preuLloguer;

    public Instrument(String codi, String nom, double preuLloguer) {
        this.codi = codi;
        this.nom = nom;
        this.preuLloguer = preuLloguer;
    }

    public abstract void descriure();

    public String getCodi() {
        return codi;
    }
}
