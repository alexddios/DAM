package Apartado6;

public class Mineral extends Pieza{
    private int duresa;

    public Mineral(String nom, int any, int duresa) {
        super(nom, any);
        this.duresa = duresa;
    }
    public int getDuresa() {
        return this.duresa;
    }
}
