//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {

    private static void caso(String titulo, boolean resultado, Boolean esperado) {
        System.out.println(titulo + " -> resultado=" + resultado + (esperado == null ? "" : (" | esperado=" + esperado)));
    }

    public static void main(String[] args) {
        ClubPadel club = new ClubPadel(
                1,
                "Padel Center",
                "934000000",
                "600000000",
                LocalTime.of(9, 0),
                LocalTime.of(22, 0)
        );

        Client ana = new Client("12345678A", "Ana", "ana@mail.com", "600111222");
        Client bob = new Client("87654321B", "Bob", "bob@mail.com", "600333444");
        club.afegirClient(ana);
        club.afegirClient(bob);

        Pista pista1 = new Pista(1, 12.50);
        Pista pista2 = new Pista(2, 15.00);

        LocalDate hoy = LocalDate.now();
        LocalDate manana = hoy.plusDays(1);

        System.out.println("=== Casos base ===");
        boolean r1 = club.reservar(pista1, ana, hoy, LocalTime.of(10, 0), 60);
        caso("Reserva válida 10:00-11:00 (pista1, Ana)", r1, null);

        boolean r2 = club.reservar(pista1, bob, hoy, LocalTime.of(10, 30), 30);
        caso("Solapada 10:30-11:00 (pista1, Bob) -> debería fallar", r2, false);

        boolean r3 = club.reservar(pista2, bob, hoy, LocalTime.of(10, 30), 30);
        caso("Otra pista 10:30-11:00 (pista2, Bob)", r3, null);

        boolean r4 = club.reservar(pista1, ana, hoy, LocalTime.of(12, 10), 30);
        caso("Hora no múltiplo de 30 (12:10) -> debería fallar", r4, false);

        boolean r5 = club.reservar(pista1, ana, hoy, LocalTime.of(21, 30), 60);
        caso("Fuera de horario (21:30 +60) -> debería fallar", r5, false);

        System.out.println("\n=== Casos de límites de horario ===");
        boolean r6 = club.reservar(pista1, bob, hoy, LocalTime.of(9, 0), 30);
        caso("Exacto a la apertura 09:00-09:30", r6, null);

        boolean r7 = club.reservar(pista1, bob, hoy, LocalTime.of(21, 30), 30);
        caso("Exacto al cierre 21:30-22:00", r7, null);

        boolean r8 = club.reservar(pista1, bob, hoy, LocalTime.of(8, 30), 30);
        caso("Antes de abrir 08:30-09:00 -> debería fallar", r8, false);

        boolean r9 = club.reservar(pista1, bob, hoy, LocalTime.of(22, 0), 30);
        caso("Empieza en el cierre 22:00-22:30 -> debería fallar", r9, false);

        System.out.println("\n=== Casos de solapes finos (mismos límites) ===");
        // r1 ocupa 10:00-11:00 en pista1 (si se pagó y se añadió)
        boolean r10 = club.reservar(pista1, bob, hoy, LocalTime.of(9, 30), 30);
        caso("No solapa por la izquierda 09:30-10:00 (pista1)", r10, null);

        boolean r11 = club.reservar(pista1, bob, hoy, LocalTime.of(11, 0), 30);
        caso("No solapa por la derecha 11:00-11:30 (pista1)", r11, null);

        boolean r12 = club.reservar(pista1, bob, hoy, LocalTime.of(10, 0), 30);
        caso("Mismo inicio 10:00-10:30 (pista1) -> debería fallar", r12, false);

        boolean r13 = club.reservar(pista1, bob, hoy, LocalTime.of(10, 30), 60);
        caso("Solape parcial 10:30-11:30 (pista1) -> debería fallar", r13, false);

        boolean r14 = club.reservar(pista1, bob, hoy, LocalTime.of(9, 30), 120);
        caso("Engloba la reserva 09:30-11:30 (pista1) -> debería fallar", r14, false);

        System.out.println("\n=== Casos de duración ===");
        boolean r15 = club.reservar(pista2, ana, hoy, LocalTime.of(13, 0), 0);
        caso("Duración 0 (13:00-13:00) (pista2) -> debería fallar (si lo quieres prohibir)", r15, null);

        boolean r16 = club.reservar(pista2, ana, hoy, LocalTime.of(13, 0), 45);
        caso("Duración no múltiplo de 30 (45) -> debería fallar", r16, false);

        boolean r17 = club.reservar(pista2, ana, hoy, LocalTime.of(14, 0), 150);
        caso("Duración larga 150 (14:00-16:30)", r17, null);

        System.out.println("\n=== Casos de fecha (mismo horario en días distintos) ===");
        boolean r18 = club.reservar(pista1, ana, manana, LocalTime.of(10, 0), 60);
        caso("Misma hora pero mañana (pista1, 10:00-11:00)", r18, null);

        System.out.println("\n=== Casos de pista activa/inactiva (nota) ===");
        pista2.desactivar();
        boolean r19 = club.reservar(pista2, bob, hoy, LocalTime.of(17, 0), 30);
        caso("Reservar pista desactivada (ahora mismo tu lógica NO lo comprueba)", r19, null);
        pista2.activar();

        System.out.println("\n=== Clientes (alta/baja) ===");
        boolean r20 = club.llevarClient("12345678A");
        System.out.println("Eliminar cliente existente (DNI Ana) -> resultado=" + r20);

        boolean r21 = club.llevarClient("00000000Z");
        System.out.println("Eliminar cliente inexistente -> resultado=" + r21);

        System.out.println("\n=== Consultar disponibilidad (placeholder) ===");
        club.consultarDispo(hoy);
    }
}