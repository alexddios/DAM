package ApartadoA;

public class Tram {
    private int numero;
    private double distancia;
    private TipusTerreny tipusTerreny;
    private Afluencia afluencia;

    public Tram(int numero, double distancia, TipusTerreny tipusTerreny, Afluencia afluencia) {
        this.numero = numero;
        this.distancia = distancia;
        this.tipusTerreny = tipusTerreny;
        this.afluencia = afluencia;
    }

    public TipusTerreny getTipusTerreny() {
        return tipusTerreny;
    }
}
