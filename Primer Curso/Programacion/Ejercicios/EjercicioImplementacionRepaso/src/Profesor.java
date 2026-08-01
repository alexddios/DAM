public class Profesor {
    private String nom;
    private String dni;
    private String especialitat;

    public Profesor(String nom, String dni, String especialitat) {
        this.nom = nom;
        this.dni = dni;
        this.especialitat = especialitat;
    }

    public String getDni() {
        return dni;
    }
}
