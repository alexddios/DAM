package Ejercicio3;

import java.time.LocalDateTime;

public class Lloguer {
    private LocalDateTime dataInici;
    private LocalDateTime dataFi;
    private double preuTotal;
    private Client client;
    private VehicleLloguer vehicle;

    public Lloguer(LocalDateTime dataInici, LocalDateTime dataFi, double preuTotal, Client client, VehicleLloguer vehicle) {
        this.dataInici = dataInici;
        this.dataFi = dataFi;
        this.preuTotal = preuTotal;
        this.client = client;
        this.vehicle = vehicle;
    }
    public void calcularCost(){

    }
}
