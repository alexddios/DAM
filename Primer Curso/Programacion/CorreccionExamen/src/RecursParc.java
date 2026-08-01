public abstract class RecursParc {
    protected String nom;
    protected String ubicacio;

    public RecursParc(String nom, String ubicacio) {
        this.nom = nom;
        this.ubicacio = ubicacio;
    }
    public abstract void mostrarInformacio();
}
