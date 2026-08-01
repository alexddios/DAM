package botiga.model;

public abstract class ArticleCatalog {
    protected final String codi;
    protected String nom;
    protected double preu;

    public ArticleCatalog(String codi, String nom, double preu) {
        this.codi = codi;
        this.nom = nom;
        this.preu = preu;
    }

    public String getCodi() { return codi; }
    public double getPreu() { return preu; }
}