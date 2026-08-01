package Ejercicio3;

public class Bicicleta extends VehicleLloguer{
    private String tipus;
    private boolean esElectrica;

    public Bicicleta(String idVehicle, String marca, String model, double preuPerHora, String tipus, boolean esElectrica) {
        super(idVehicle, marca, model, preuPerHora);
        this.tipus = tipus;
        this.esElectrica = esElectrica;
    }
}
