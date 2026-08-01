public class Client {
    private final String dni;
    private final String nom;
    private final String email;
    private final String telefon;

    public Client(String dni, String nom, String email, String telefon) {
        this.dni = dni;
        this.nom = nom;
        this.email = email;
        this.telefon = telefon;
    }

    public String getDni() {
        return dni;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

}
