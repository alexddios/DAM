package Ejercicio4;

public class TestMicroones {
    public static void main(String[] args) {
        System.out.println("=== INICI DE LES PROVES DEL MICROONES ===");

        // 1. Instanciem l'objecte
        Microones elMeuMicroones = new Microones();

        // 2. Intentem iniciar sense encendre (Prova de la regla "No pot iniciar si està apagat")
        // Nota: Amb el teu codi actual això funcionarà malament fins que apliquis la correcció de sota.
        System.out.println("\n--- Intent d'iniciar estant apagat ---");
        elMeuMicroones.iniciar(30);
        elMeuMicroones.mostrarEstat();

        // 3. Encenem l'aparell
        System.out.println("\n--- Encenent el microones ---");
        elMeuMicroones.encendre();

        // Provem d'encendre un altre cop per veure el missatge
        elMeuMicroones.encendre();

        // 4. Iniciem la cocció amb 60 segons
        System.out.println("\n--- Iniciant cocció (60 segons) ---");
        elMeuMicroones.iniciar(60);
        elMeuMicroones.mostrarEstat();

        // 5. Afegim temps mentre està funcionant
        System.out.println("\n--- Afegint 30 segons extra ---");
        elMeuMicroones.afegirTemps(30);
        elMeuMicroones.mostrarEstat(); // Hauria de ser 90

        // 6. Simulem que passa el temps (esperar)
        System.out.println("\n--- Passen 50 segons... ---");
        elMeuMicroones.esperar(50);
        elMeuMicroones.mostrarEstat(); // Haurien de quedar 40 segons

        // 7. Aturem manualment abans que acabi
        System.out.println("\n--- Aturant manualment ---");
        elMeuMicroones.aturar();
        elMeuMicroones.mostrarEstat();

        // 8. Provem que s'aturi sol quan el temps arriba a 0
        System.out.println("\n--- Nova prova: Iniciar 10 segons i esperar que acabi ---");
        elMeuMicroones.iniciar(10);
        elMeuMicroones.esperar(10); // Esperem tot el temps
        elMeuMicroones.mostrarEstat(); // L'estat hauria de ser false (aturat)

        // 9. Apaguem l'aparell
        System.out.println("\n--- Apagant el sistema ---");
        elMeuMicroones.apagar();
        elMeuMicroones.mostrarEstat();

        System.out.println("=== FIN DE LES PROVES DEL MICROONES ===");
    }
}