package Apartado4;

public class Planta {
    private String nom;
    private String tipusReg;
    private double alturaCm;

    public Planta(String nom, String tipusReg, double alturaCm) {
        this.nom = nom;
        this.tipusReg = tipusReg;
        this.alturaCm = alturaCm;
    }
    public String getNom() {
        return this.nom;
    }
    public String getTipusReg() {
        return this.tipusReg;
    }
    public double getAlturaCm() {
        return this.alturaCm;
    }
}
