public class Furgoneta extends Vehicle{
    private double capacitatKilos;

    public Furgoneta(String marca, String matricula, double preuPerDia, double capacitatKilos) {
        super(marca, matricula, preuPerDia);
        this.capacitatKilos = capacitatKilos;
    }

    @Override
    public double calcularPreuBase(int dies) {
        return 0;
    }
}
