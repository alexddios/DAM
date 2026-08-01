package Apartado5;

public class Main {
    public static void main(String[] args) {
        Arxiu arxiu = new Arxiu("Informàtica");

        System.out.println("=== Proves d'afegir documents ===");

        System.out.println("Afegir DOC1: " + arxiu.afegirDocument("DOC1", "Manual Java", 120));
        System.out.println("Afegir DOC2: " + arxiu.afegirDocument("DOC2", "Bases de Dades", 80));
        System.out.println("Afegir DOC1 repetit: " + arxiu.afegirDocument("DOC1", "Java Avançat", 200));
        System.out.println("Afegir DOC3 amb pàgines negatives: " + arxiu.afegirDocument("DOC3", "Error", -5));
        System.out.println("Afegir DOC4 amb 0 pàgines: " + arxiu.afegirDocument("DOC4", "Error Zero", 0));

        System.out.println("=== Final de les proves ===");
    }
}
