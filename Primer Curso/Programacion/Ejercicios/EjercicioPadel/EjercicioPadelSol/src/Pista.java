public class Pista {

    private int numPista;
    private double preu30min;
    private boolean activa;

    public Pista(int numPista, double preu30min) {
        this.numPista = numPista;
        this.preu30min = preu30min;
        this.activa = true;
    }

    public void activar() {
        this.activa = true;
    }

    public void desactivar() {
        this.activa = false;
    }

    public int getNumPista() {
        return numPista;
    }

    public double getPreu30min() {
        return preu30min;
    }

    public boolean isActiva() {
        return activa;
    }
}
