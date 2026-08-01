



void main() {
    ClubPadel padelmon = new ClubPadel(
            1, LocalTime.of(9, 0), LocalTime.of(22, 0),
            "Padelmon", "971111111", "info@padelmon.com"
    );

    ClubPadel padelbox = new ClubPadel(
            2, LocalTime.of(8, 0), LocalTime.of(23, 0),
            "Padelbox", "971222222", "info@padelbox.com"
    );

    // Clients
    padelmon.afegirClient(new Client("Ana", "111A", "ana@mail.com", "600111111"));
    padelmon.afegirClient(new Client("Bob", "222B", "bob@mail.com", "600222222"));
    padelbox.afegirClient(new Client("Carla", "333C", "carla@mail.com", "600333333"));

    // Pistes
    padelmon.crearPista(10.0); // 0
    padelmon.crearPista(12.0); // 1
    padelbox.crearPista(9.0);  // 0

    // Agafar objectes del club via getters
    Pista pm0 = padelmon.getPista(0);
    Pista pm1 = padelmon.getPista(1);
    Pista pb0 = padelbox.getPista(0);

    Client ana = padelmon.getClient("111A");
    Client bob = padelmon.getClient("222B");
    Client carlaPB = padelbox.getClient("333C");

    LocalDate dia = LocalDate.now().plusDays(1);
    LocalDateTime d10   = LocalDateTime.of(dia, LocalTime.of(10, 0));
    LocalDateTime d1030 = LocalDateTime.of(dia, LocalTime.of(10, 30));
    LocalDateTime d2145 = LocalDateTime.of(dia, LocalTime.of(21, 45));
    LocalDateTime d2130 = LocalDateTime.of(dia, LocalTime.of(21, 30));

    System.out.println("\n===== PADELMON: reserves correctes (pot fallar per pagament) =====");
    padelmon.reservar(pm0, ana, d10, 60);  // id esperat 0 si paga bé
    padelmon.reservar(pm1, bob, d10, 90);  // id esperat 1 si paga bé

    System.out.println("\n===== PADELMON: duració malament (no múltiple 30) =====");
    padelmon.reservar(pm0, ana, d10.plusHours(2), 40);

    System.out.println("\n===== PADELMON: inici malament (minuts no múltiple 30) =====");
    padelmon.reservar(pm0, ana, d2145, 30);

    System.out.println("\n===== PADELMON: fora d'horari (acaba després 22:00) =====");
    padelmon.reservar(pm0, ana, d2130, 60);

    System.out.println("\n===== PADELMON: solapament (si la primera a les 10:00 ha entrat) =====");
    padelmon.reservar(pm0, bob, d1030, 60);

    System.out.println("\n===== PADELMON: pista inactiva =====");
    pm0.desactivar();
    padelmon.reservar(pm0, ana, d10.plusHours(3), 60);
    pm0.activar();

    System.out.println("\n===== PADELMON: client que NO pertany al club =====");
    padelmon.reservar(pm0, carlaPB, d10.plusHours(4), 60);

    System.out.println("\n===== PADELMON: consultar disponibilitat =====");
    padelmon.consultarDispo(dia);

    System.out.println("\n===== CANCEL·LACIÓ: intentar cancel·lar reserva id=0 =====");
    Reserva r0 = padelmon.getReserva(0);
    if (r0 != null) {
        r0.cancelar();
    } else {
        System.out.println("No existeix la reserva 0 (potser el pagament va fallar).");
    }

    System.out.println("\n===== PADELMON: tornar a reservar a les 10:00 (si s'ha cancel·lat, hauria d'anar) =====");
    padelmon.reservar(pm0, ana, d10, 60);

    System.out.println("\n===== PADELBOX: proves bàsiques =====");
    padelbox.reservar(pb0, carlaPB, LocalDateTime.of(dia, LocalTime.of(9, 0)), 60);
    padelbox.consultarDispo(dia);

    System.out.println("\n===== TEST PAGAMENT: 20 intents de reserva (30min, sense solapament) =====");

    LocalDate diaTest = LocalDate.now().plusDays(1);
    LocalDateTime base = LocalDateTime.of(diaTest, LocalTime.of(12, 0));

    Pista pTest = padelmon.getPista(0);
    Client cTest = padelmon.getClient("111A");

    for (int i = 0; i < 20; i++) {
        LocalDateTime inici = base.plusMinutes(i * 30); // 12:00, 12:30, 13:00...
        padelmon.reservar(pTest, cTest, inici, 30);
    }

}
