package botiga.model;

public class Client {
    private final String nif;
    private String nom;

    public Client(String nif, String nom) {
        this.nif = nif;
        this.nom = nom;
    }
}