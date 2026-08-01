package Apartado6;

public abstract class Pieza {
    protected String nom;
    protected int any;

    public Pieza(String nom, int any) {
        this.nom = nom;
        this.any = any;
    }
    public String getNom() {
        return this.nom;
    }
    public int getAny() {
        return this.any;
    }
}
