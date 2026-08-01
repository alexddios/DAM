public class Personatge {
    private int id;
    private String nom;
    private String joc;
    private String classe;
    private int nivell;

    public Personatge(int id, String nom, String joc, String classe, int nivell) {
        this.id = id;
        this.nom = nom;
        this.joc = joc;
        this.classe = classe;
        this.nivell = nivell;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getJoc() {
        return joc;
    }

    public String getClasse() {
        return classe;
    }

    public int getNivell() {
        return nivell;
    }

    @Override
    public String toString() {
        return "Personatge{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", joc='" + joc + '\'' +
                ", classe='" + classe + '\'' +
                ", nivell=" + nivell +
                '}';
    }
}
