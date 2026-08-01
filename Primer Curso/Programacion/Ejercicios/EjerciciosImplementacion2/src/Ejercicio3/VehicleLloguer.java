package Ejercicio3;

public abstract class VehicleLloguer {
    protected String idVehicle;
    protected String marca;
    protected String model;
    protected double preuPerHora;
    protected boolean disponible;

    public VehicleLloguer(String idVehicle, String marca, String model, double preuPerHora) {
        this.idVehicle = idVehicle;
        this.marca = marca;
        this.model = model;
        this.preuPerHora = preuPerHora;
        this.disponible = true;
    }
}
