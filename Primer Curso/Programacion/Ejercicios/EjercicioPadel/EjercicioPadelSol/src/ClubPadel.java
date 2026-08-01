import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ClubPadel {

    private int id;
    private LocalTime obertura;
    private LocalTime tancament;
    private String nom;
    private String telefon;
    private String mail;
    private ArrayList<Pista> pistes;
    private ArrayList<Reserva> reserves;
    private ArrayList<Client> clients;

    public ClubPadel(int id, LocalTime obertura, LocalTime tancament, String nom,
                     String telefon, String mail) {
        this.id = id;
        this.obertura = obertura;
        this.tancament = tancament;
        this.nom = nom;
        this.telefon = telefon;
        this.mail = mail;
        this.pistes = new ArrayList<>();
        this.reserves = new ArrayList<>();
        this.clients = new ArrayList<>();
    }
    public void afegirClient(Client client) {
        String dni = client.getDni();
        for (Client c : this.clients) {
            if (c.getDni().equals(dni)) {
                System.out.println("Client NO afegit: El client " + dni + " ja existeix.");
                return;
            }
        }

        this.clients.add(client); // El client no existeix, el podem afegir
        System.out.println("Client " + dni + " afegit.");
    }

    public void llevarClient(String dni) {
        for (int i = 0; i < this.clients.size(); i++) {
            if (this.clients.get(i).getDni().equals(dni)) {
                this.clients.remove(i);
                System.out.println("Client " + dni + " eliminat");
                return; // Ja hem llevat el client, podem sortir de la funció
            }
        }
        System.out.println("Client " + dni + " NO eliminat perquè no existeix.");
    }

    public void crearPista(double preu30min) {
        if (preu30min <= 0) {
            System.out.println("Pista NO afegida perquè el preu ha de ser major o igual a 0");
            return;
        }
        int numPista = this.pistes.size();
        Pista p = new Pista(numPista,  preu30min);
        this.pistes.add(p);
        System.out.println("Pista " + numPista + " afegida");
    }

    public void consultarDispo(LocalDate dia) {
        System.out.println("Disponibilitat per dia " + dia.toString());

        // Miram la disponibilitat de cada pista
        for (Pista p : this.pistes) {

            // Si la pista està inactiva, no hi ha dispo
            if (!p.isActiva()) {
                System.out.println("Pista " + p.getNumPista() + " tancada");
                continue;
            }

            // Si la pista està activa, anam mirant totes les franges de 30min des de l'obertura fins el tancament
            System.out.println("Pista " + p.getNumPista() + " oberta:");
            LocalDateTime horari = LocalDateTime.of(dia, this.obertura);
            while (horari.toLocalTime().isBefore(this.tancament)) {

                // Miram si hi ha alguna reserva que ocupi la franja
                String ocupacio = "[ ]";
                for (Reserva r : this.reserves) {
                    if (r.esSolapen(p, horari, 30)) {
                        ocupacio = "[X]";
                        break;
                    }
                }

                // Indicam si la franja està o no ocupada
                System.out.println(" - " + horari.toLocalTime() + ocupacio);

                // Passam a la següent franja
                horari = horari.plusMinutes(30);
            }
        }
    }

    public void reservar(Pista p, Client c, LocalDateTime inici, int duracio) {

        // Validacions prèvies
         if (!validarPista(p) || !validarClient(c) || !validarHorari(inici, duracio, p)) {
             return;
         }

        // Tot ok, intentam pagar
        double preu = ((double) duracio / 30) * p.getPreu30min();
        if (!PasarelaPagament.pagar(preu)) {
            return;
        }

        // Pagament ok, cream la reserva
        int numReserva = this.reserves.size();
        Reserva r = new Reserva(numReserva, inici, duracio, p, c);
        this.reserves.add(r);
        System.out.println(
                "Reserva " + numReserva + " creada: " +
                        "inici=" + inici + ", " +
                        "duracio=" + duracio + "min, " +
                        "pista=" + p.getNumPista() + ", " +
                        "client=" + c.getNom()
        );
    }

    private boolean validarPista(Pista p) {

        // Validam si la pista és correcta
        if (p == null) {
            System.out.println("Reserva NO creada: pista incorrecta");
            return false;
        }

        // Validam si la pista és del club
        boolean existeix = false;
        for (Pista pi : this.pistes) {
            if (pi.getNumPista() == p.getNumPista()) {
                existeix = true;
                break;
            }
        }
        if (!existeix) {
            System.out.println("Reserva NO creada: la pista no pertany al club");
            return false;
        }

        // Validam si la pista està activa
        if (!p.isActiva()) {
            System.out.println("Reserva NO creada perquè la pista " + p.getNumPista() + " està inactiva");
            return false;
        }

        // Tot ok
        return true;
    }

    private boolean validarClient(Client c) {

        // Validam si el client és correcte
        if (c == null) {
            System.out.println("Reserva NO creada: client incorrecte");
            return false;
        }

        // Validam si el client és del club
        boolean existeix = false;
        for (Client cl : this.clients) {
            if (cl.getDni().equals(c.getDni())) {
                existeix = true;
                break;
            }
        }
        if (!existeix) {
            System.out.println("Reserva NO creada: el client no pertany al club");
            return false;
        }

        // Tot ok
        return true;
    }

    private boolean validarHorari(LocalDateTime inici, int duracio, Pista p) {

        // Validam si la duració és major a 0
        if (duracio <= 0) {
            System.out.println("Reserva NO creada perquè la duració ha de ser major que 0");
            return false;
        }

        // Validam si l'hora de la reserva i la duració són divisibles entre 30
        if (duracio % 30 != 0 || inici.getMinute() % 30 != 0) {
            System.out.println("Reserva NO creada perquè la duració i l'hora d'inici han de ser" +
                    " divisibles entre 30");
            return false;
        }

        // Validam si la reserva s'ajusta a l'horari del club
        LocalDateTime finalReserva = inici.plusMinutes(duracio);
        if (inici.toLocalTime().isBefore(obertura) || finalReserva.toLocalTime().isAfter(tancament)) {
            System.out.println("Reserva NO creada perquè no s'ajusta a l'horari del club");
            return false;
        }

        // Validam si hi ha alguna reserva que es solapi
        for (Reserva r : this.reserves) {
            if (r.esSolapen(p, inici, duracio)) {
                System.out.println("Reserva NO creada perquè ja existeix una reserva que es sol.lapa");
                return false;
            }
        }

        // Tot ok
        return true;
    }

    public Pista getPista(int numPista) {
        for (Pista p : this.pistes) {
            if (p.getNumPista() == numPista) return p;
        }
        return null;
    }

    public Client getClient(String dni) {
        for (Client c : this.clients) {
            if (c.getDni().equals(dni)) return c;
        }
        return null;
    }

    public Reserva getReserva(int id) {
        for (Reserva r : this.reserves) {
            if (r.getId() == id) return r;
        }
        return null;
    }

}
