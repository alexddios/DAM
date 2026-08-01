package Apartado6;

public class Main {
    public static void main(String[] args) {
        Sala sala = new Sala("Sala d'exemple");

        System.out.println("=== Proves d'afegir peces ===");

        System.out.println("Afegir mineral 1: " +
                sala.afegirPieza(new Mineral("Quars", 1998, 5)));

        System.out.println("Afegir mineral 2: " +
                sala.afegirPieza(new Mineral("Granit", 2005, 8)));

        System.out.println("Afegir fòssil: " +
                sala.afegirPieza(new Fosil("Ammonit", 1500, "Juràssic")));

        System.out.println("Afegir mineral 3: " +
                sala.afegirPieza(new Mineral("Calcita", 2010, 3)));

        System.out.println("Afegir peça amb any negatiu: " +
                sala.afegirPieza(new Mineral("Error", -10, 10)));

        System.out.println();
        System.out.println("=== Proves de recompte ===");
        System.out.println("Minerals amb duresa >= 4: " +
                sala.numeroPiezasMineralesDurezaSuperior(4));

        System.out.println("Minerals amb duresa >= 8: " +
                sala.numeroPiezasMineralesDurezaSuperior(8));

        System.out.println("Minerals amb duresa >= 10: " +
                sala.numeroPiezasMineralesDurezaSuperior(10));
    }
}
