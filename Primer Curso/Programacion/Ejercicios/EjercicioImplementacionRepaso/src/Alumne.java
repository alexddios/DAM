public class Alumne {
    private String nom;
    private String dni;
    private int edat;
    private double nota;

    public Alumne(String nom, String dni, int edat, double nota) {
        this.nom = nom;
        this.dni = dni;
        this.edat = edat;
        this.nota=nota;
    }

    public String getNom() {
        return nom;
    }

    public String getDni() {
        return dni;
    }

    public int getEdat() {
        return edat;
    }

    public double getNota() {
        return nota;
    }
}
