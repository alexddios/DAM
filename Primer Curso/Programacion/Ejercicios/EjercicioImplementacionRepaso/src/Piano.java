public class Piano extends Instrument{
    private int numTecles;
    private boolean esElectronic;

    public Piano(String codi, String nom, double preuLloguer, int numTecles, boolean esElectronic) {
        super(codi, nom, preuLloguer);
        this.numTecles = numTecles;
        this.esElectronic = esElectronic;
    }
    @Override
    public void descriure() {
        System.out.println("Piano: " + this.nom + " " + this.numTecles + " " + this.esElectronic);
    }
}
