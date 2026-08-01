import java.time.LocalDateTime;

public class Reserva {

    private int id;
    private LocalDateTime inici;
    private int durada;
    private boolean cancelada;
    private double preuTotal;
    private Pista pista;
    private Client client;

    public Reserva(int id, LocalDateTime inici, int durada, Pista pista, Client client) {
        this.id = id;
        this.inici = inici;
        this.durada = durada;
        this.pista = pista;
        this.client = client;
        this.cancelada = false;
        this.preuTotal = ((double) durada / 30) * pista.getPreu30min();
    }

    public void cancelar() {
        this.cancelada = true;
        System.out.println("Reserva " + this.id + " cancelada");
    }

    public boolean esSolapen(Pista p, LocalDateTime i, int d) {
        if (p.getNumPista() != this.pista.getNumPista()) {
            return false;
        }

        if (this.cancelada) {
            return false;
        }

        LocalDateTime fiThis = this.inici.plusMinutes(this.durada);
        LocalDateTime fiNou  = i.plusMinutes(d);

        return this.inici.isBefore(fiNou) && i.isBefore(fiThis);
    }

    public int getId() {
        return id;
    }

    public Pista getPista() {
        return pista;
    }

    public Client getClient() {
        return client;
    }

    public double getPreuTotal() {
        return preuTotal;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public int getDurada() {
        return durada;
    }

    public LocalDateTime getInici() {
        return inici;
    }
}
