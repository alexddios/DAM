public class Visitant {
    private String nom;
    private int edat;
    private final String dni;

    public Visitant(String nom, int edat, String dni) {
        this.nom = nom;
        this.edat = edat;
        this.dni = dni;
    }
    public String getdni() {
        return this.dni;
    }

}
