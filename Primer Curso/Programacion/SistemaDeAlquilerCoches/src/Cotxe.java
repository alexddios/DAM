public class Cotxe extends Vehicle{
    private int places;

    public Cotxe(String marca, String matricula, double preuPerDia, int places) {
        super(marca, matricula, preuPerDia);
        this.places = places;
    }

    @Override
    public double calcularPreuBase(int dies) {
        return 0;
    }
}
