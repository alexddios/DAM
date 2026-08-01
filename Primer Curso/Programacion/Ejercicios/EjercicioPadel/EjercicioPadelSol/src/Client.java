public class Client {

    private String nom;
    private String dni;
    private String mail;
    private String telefon;

    public Client(String nom, String dni, String mail, String telefon) {
        this.nom = nom;
        this.dni = dni;
        this.mail = mail;
        this.telefon = telefon;
    }

    public String getDni() {
        return dni;
    }

    public String getNom() {
        return nom;
    }
}
