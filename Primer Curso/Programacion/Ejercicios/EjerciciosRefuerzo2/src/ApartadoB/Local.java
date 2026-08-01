package ApartadoB;

public class Local {
    private String nom;
    private TipusLocal tipus;

    public Local(String nom,TipusLocal tipus){
        this.nom=nom;
        this.tipus=tipus;
    }

    public TipusLocal getTipus() {
        return tipus;
    }
}
