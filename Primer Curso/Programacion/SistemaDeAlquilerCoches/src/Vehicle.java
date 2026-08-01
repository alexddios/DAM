public abstract class Vehicle {
    protected String matricula;
    protected String marca;
    protected double preuPerDia;

    public Vehicle(String marca, String matricula, double preuPerDia) {
        this.marca = marca;
        this.matricula = matricula;
        this.preuPerDia = preuPerDia;
    }

    public String getMatricula() {
        return matricula;
    }

    public double getPreuPerDia() {
        return preuPerDia;
    }
    public abstract double calcularPreuBase(int dies);
}
