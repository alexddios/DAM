package Apartado4;

public class Main {
    public static void main(String[] args) {
        Zona zona = new Zona("Z1", true);

        System.out.println("Añadiendo plantas...");
        System.out.println("Planta 1: " + zona.afegirPlanta("Rosal", "abundante", 120.5));
        System.out.println("Planta 2: " + zona.afegirPlanta("Cactus", "escaso", 35.0));
        System.out.println("Planta 3: " + zona.afegirPlanta("Orquídea", "moderado", 45.0));
        System.out.println("Planta 4: " + zona.afegirPlanta("Pino", "abundante", 180.0));

        System.out.println("Planta 5 (altura negativa): " + zona.afegirPlanta("Planta rara", "escaso", -10));

        System.out.println();
        System.out.println("Plantas con riego 'abundante' y altura >= 100: "
                + zona.numeroPlantesTipusRegSuperenAlturaIndicada("abundante", 100));

        System.out.println("Plantas con riego 'escaso' y altura >= 30: "
                + zona.numeroPlantesTipusRegSuperenAlturaIndicada("escaso", 30));

        System.out.println("Plantas con riego 'moderado' y altura >= 50: "
                + zona.numeroPlantesTipusRegSuperenAlturaIndicada("moderado", 50));
    }
}
