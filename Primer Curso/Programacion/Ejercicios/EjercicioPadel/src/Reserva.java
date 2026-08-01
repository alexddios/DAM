import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private final int id;
    private LocalDate dataInici;
    private LocalTime horaInici;
    private int durada;
    private boolean activa;
    private double preuTotal;
    private Client client;
    private Pista pista;

    public Reserva(int id, LocalDate data, LocalTime hora, int durada, Client c, Pista p) {
        this.id = id;
        this.dataInici = data;
        this.horaInici = hora;
        this.durada = durada;
        this.client = c;
        this.pista = p;
        this.activa = true;
        this.preuTotal = (durada / 30.0) * p.getPreu30min();
    }

    public void cancelar() {
        this.activa = false;
    }

    public LocalTime getHoraInici() { return horaInici; }
    public LocalTime getHoraFi() { return horaInici.plusMinutes(durada); }
    public LocalDate getDataInici() { return dataInici; }
    public Pista getPista() { return pista; }
    public boolean isActiva() { return activa; }
}