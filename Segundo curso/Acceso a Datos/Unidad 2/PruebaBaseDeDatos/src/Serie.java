public class Serie {
    private int id;
    private String titol;
    private String genere;
    private int temporades;
    private String plataforma;

    // Serie nova: encara no té id de BD (és autoincremental)
    public Serie(String titol, String genere, int temporades, String plataforma) {
        this.titol = titol;
        this.genere = genere;
        this.temporades = temporades;
        this.plataforma = plataforma;
    }

    // Serie recuperada de la BD: ja té id
    public Serie(int id, String titol, String genere, int temporades, String plataforma) {
        this(titol, genere, temporades, plataforma);
        this.id = id;
    }

    public int getId() { return id; }
    public String getTitol() { return titol; }
    public String getGenere() { return genere; }
    public int getTemporades() { return temporades; }
    public String getPlataforma() { return plataforma; }

    @Override
    public String toString() {
        return id + " | " + titol + " | " + genere + " | " + temporades + " temporades | " + plataforma;
    }
}
