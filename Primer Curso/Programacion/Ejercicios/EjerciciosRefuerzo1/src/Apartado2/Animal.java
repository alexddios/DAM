package Apartado2;

public class Animal {
    private String nom;
    private int edat;
    private boolean necesitaMedicacio;

    Animal(String nom, int edat, boolean necesitaMedicacio){
        this.nom = nom;
        this.edat = edat;
        this.necesitaMedicacio = necesitaMedicacio;
    }
    public int getEdat() {
        return this.edat;
    }
    public boolean getNecesitaMedicacio() {
        return this.necesitaMedicacio;
    }
    public String getNom() {
        return this.nom;
    }
}
