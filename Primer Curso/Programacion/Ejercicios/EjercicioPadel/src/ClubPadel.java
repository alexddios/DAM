import java.util.*;
import java.time.*;

public class ClubPadel {
    private int id;
    private String nom;
    private String telefon;
    private String movil;
    private List<Pista> pistes;
    private List<Reserva> reserves;
    private List<Client> clients;
    private PasarelaPagament pasarela;
    private final LocalTime OBERTURA;
    private final LocalTime TANCAMENT;

    public ClubPadel(int id, String nom, String telefon, String movil,LocalTime OBERTURA,LocalTime TANCAMENT) {
        this.id = id;
        this.nom = nom;
        this.telefon = telefon;
        this.movil = movil;
        this.pistes = new ArrayList<>();
        this.reserves = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.pasarela = new PasarelaPagament();
        this.OBERTURA=OBERTURA;
        this.TANCAMENT=TANCAMENT;
    }


    public void afegirClient(Client c) {
        this.clients.add(c);
    }

    public boolean llevarClient(String dni) {
        for (int i = 0; i < clients.size() ; i++) {
            if (clients.get(i).getDni().equals(dni)){
                System.out.println("Se ha encontrado el usuario");
                clients.remove(i);

                return true;

            }else {
                System.out.println("No se ha encontrado el usuario");
                return false;
            }
        }
        return false;
    }


    public void crearPista(double preu30min) {
        Pista nova = new Pista(pistes.size(),preu30min);
        pistes.add(nova);
    }

    public boolean reservar(Pista p, Client c, LocalDate d, LocalTime h, int duracio) {
        if (h.getMinute() % 30 != 0 || duracio % 30 != 0) return false;
        if (h.isBefore(OBERTURA) || h.plusMinutes(duracio).isAfter(TANCAMENT)) return false;

        for (Reserva r : reserves) {
            if (r.isActiva() && r.getPista().equals(p) && r.getDataInici().equals(d)) {
                if (h.isBefore(r.getHoraFi()) && h.plusMinutes(duracio).isAfter(r.getHoraInici())) {
                    return false;
                }
            }
        }

        double preuTotal = (duracio / 30.0) * p.getPreu30min();
        if (pasarela.pagar(preuTotal)) {
            Reserva nuevaReserva = new Reserva(reserves.size() + 1, d, h, duracio, c, p);
            reserves.add(nuevaReserva);
            return true;
        }
        return false;
    }

    public void consultarDispo(LocalDate dia) {
        System.out.println("Disponibilitat per al dia: " + dia);
    }
}