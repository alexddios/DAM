public class Alumne {
    private String nom;
    private int edat;
    private String grup;

    public Alumne(String nom,int edat,String grup){
        this.nom=nom;
        this.edat=edat;
        this.grup=grup;
    }
    public String dades(){
        return String.format("%s,%d,%s", this.nom, this.edat, this.grup);
    }
}
