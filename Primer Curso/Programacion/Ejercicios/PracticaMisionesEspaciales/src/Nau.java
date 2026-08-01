import java.util.ArrayList;
import java.util.List;

public class Nau {
    private final String matricula;
    private final String nom;
    private final List<Modulo> moduls;

    public Nau(String matricula, String nom){
        this.matricula=matricula;
        this.nom=nom;
        this.moduls= new ArrayList<>();
    }

    public double pesTotalModuls() {
        double pesTotal = 0.0;
        for (Modulo modul : moduls) {
            pesTotal += modul.getMassaKg();
        }
        return pesTotal;
    }
    public Motor motorPrincipal() {
        for (Modulo modul : moduls) {
            if (modul instanceof Motor motor) {
                return motor;
            }
        }
        return null;
    }


    public boolean crearMotor(String id, double massaKg, double potenciaKw) {
        if (id == null || id.isBlank()) return false;
        if (existeixIdModulo(id)) return false;
        if (potenciaKw <= 0) return false;
        if (massaKg <= 0) return false;

        Motor motor = new Motor(id, massaKg, potenciaKw);
        moduls.add(motor);
        return true;
    }

    public boolean crearHabitat(String id, double massaKg, int llits) {
        if (id == null || id.isBlank()) return false;
        if (existeixIdModulo(id)) return false;
        if (llits <= 0) return false;
        if (massaKg <= 0) return false;

        Habitat habitat = new Habitat(id, massaKg, llits);
        moduls.add(habitat);
        return true;
    }
    private boolean existeixIdModulo(String id) {
        if (id == null) return false;
        for (Modulo modul : moduls) {
            if (id.equals(modul.getId())) {
                return true;
            }
        }
        return false;
    }

}
