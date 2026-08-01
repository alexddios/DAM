package Ejercicio2;

public class GestioPrestecs {
    public static void main(String[] args) {

        // 1. Creació dels objectes necessaris (Actors i Recursos)
        Alumne alumne1 = new Alumne("Joan Garcia");
        Professor prof1 = new Professor("Dra. Marta Vidal");
        Aula aulaAuditori = new Aula("AUD-01");

        Material micro = new Material("Micròfon sense fils");
        Material projector = new Material("Projector HD");
        Material altaveus = new Material("Joc altaveus actius");

        // CAS D'ÚS 1: Préstec estàndard sense incidències
        // Justificació: El constructor força que definim Ejercicio2.Alumne, Ejercicio2.Professor i Ejercicio2.Aula (multiplicitat 1)
        Prestec p1 = new Prestec(alumne1, prof1, aulaAuditori);

        // Afegim materials (multiplicitat 1..*)
        p1.afegirMaterial(micro);
        p1.afegirMaterial(projector);

        // Imprimim per demostrar la navegabilitat
        System.out.println("CAS 1: Préstec normal");
        p1.imprimirFitxaPrestec();

        // CAS D'ÚS 2: Préstec amb problemes (Incidències)
        // Reutilitzem actors per simplicitat
        Prestec p2 = new Prestec(alumne1, prof1, aulaAuditori);
        p2.afegirMaterial(altaveus);

        // Afegim incidències (multiplicitat 0..*)
        // Això demostra que la navegabilitat Ejercicio2.Prestec -> Ejercicio2.Incidencia funciona
        p2.registrarIncidencia(new Incidencia("Cable d'alimentació pelat"));
        p2.registrarIncidencia(new Incidencia("Retard en la devolució de 30 minuts"));

        System.out.println("CAS 2: Préstec amb incidències");
        p2.imprimirFitxaPrestec();
    }
}