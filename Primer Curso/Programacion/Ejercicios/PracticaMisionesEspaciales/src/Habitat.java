public class Habitat extends Modulo{
    private final int llits;

    public Habitat(String id, double massaKg, int llits) {
        super(id, massaKg);
        this.llits = llits;
    }
    @Override
    public String nomTipus() {
        return "Habitat";
    }
}
