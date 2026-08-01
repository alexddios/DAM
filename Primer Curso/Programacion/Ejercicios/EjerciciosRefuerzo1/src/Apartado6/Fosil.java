package Apartado6;

public class Fosil extends Pieza {
    private String periodeGeologic;

    public Fosil(String nom, int any, String periodeGeologic) {
        super(nom, any);
        this.periodeGeologic = periodeGeologic;
    }
}
