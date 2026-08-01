package ApartadoB;

public class Participacio {
    private final String dni;
    private double importGastat;
    private boolean haConduit;

    public Participacio (String dni, double importGastat, boolean haConduit){
        this.dni=dni;
        this.importGastat=importGastat;
        this.haConduit=haConduit;
    }

    public String getDni() {
        return dni;
    }

    public double getImportGastat() {
        return importGastat;
    }

    public boolean isHaConduit() {
        return haConduit;
    }
}
