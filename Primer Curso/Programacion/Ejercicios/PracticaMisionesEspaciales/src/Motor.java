public class Motor extends Modulo {
    private final double potenciaKw;

    public Motor(String id, double massaKg, double potenciaKw) {
        super(id, massaKg);
        this.potenciaKw = potenciaKw;
    }

    @Override
    public String nomTipus() {
        return "Motor";
    }
}
