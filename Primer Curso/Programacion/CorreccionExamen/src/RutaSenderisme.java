public class RutaSenderisme extends RecursParc{
    private double distanciaKm;
    private String dificultat;

    public RutaSenderisme(String nom, String ubicacio, double distanciaKm, String dificultat) {
        super(nom, ubicacio);
        this.distanciaKm = distanciaKm;
        this.dificultat = dificultat;
    }
    @Override
    public void mostrarInformacio() {
        System.out.printf("""
                   Nom: %s
                   Ubicacio: %s
                   Distancia: %f km
                   Dificultat: %s
                """, this.nom, this.ubicacio, this.distanciaKm, this.dificultat);
    }
}
