package Apartado5;

public class Document {
    private String codi;
    private String titol;
    private int numeroPagines;

    public Document(String codi, String titol, int numeroPagines) {
        this.codi = codi;
        this.titol = titol;
        this.numeroPagines = numeroPagines;
    }
    public String getCodi() {
        return this.codi;
    }
    public String getTitol() {
        return this.titol;
    }
    public int getNumeroPagines() {
        return this.numeroPagines;
    }
}
