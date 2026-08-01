public class Guitarra extends Instrument{
    private String tipusCordes;
    private boolean teAmplificador;

    public Guitarra(String codi, String nom, double preuLloguer, String tipusCordes) {
        super(codi, nom, preuLloguer);
        this.tipusCordes = tipusCordes;
        this.teAmplificador = false;
    }
    @Override
    public void descriure() {
        System.out.println("Guitarra: " + this.nom + " " + this.tipusCordes);
    }
}
