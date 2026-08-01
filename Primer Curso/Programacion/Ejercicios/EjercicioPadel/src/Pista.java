public class Pista {
    private int numPista;
    private double preu30min;
    private boolean activa;

    public Pista(int numPista,double preu30min) {
        this.numPista=numPista;
        this.preu30min = preu30min;
        this.activa = true;
    }

    public void activar() {
        this.activa = true;
        System.out.println("Pista " + numPista + " activada.");
    }

    public void desactivar() {
        this.activa = false;
        System.out.println("Pista " + numPista + " desactivada per manteniment.");
    }

    public double getPreu30min() { return preu30min; }
}