public class Client {
    private String dni;
    private String nom;
    private int edat;

    public Client(String dni, String nom, int edat) {
        this.dni = dni;
        this.nom = nom;
        this.edat = edat;
    }

    public String getDni() {
        return dni;
    }

    public String getNom() {
        return nom;
    }

    public int getEdat() {
        return edat;
    }
}
