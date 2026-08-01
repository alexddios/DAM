package Apartado1;

public class Peix {
    private String nom;
    private final String especie;

    public Peix(String nom, String especie) {
        this.nom = nom;
        this.especie = especie;
    }
    public String getEspecie() {
        return this.especie;
    }
    public String getNom() {
        return this.nom;
    }
}
