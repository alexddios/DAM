package Ejercicio2;

import java.util.ArrayList;
import java.util.List;

class Prestec {
    // Associacions de multiplicitat "1"
    // Implementació: Referència directa a l'objecte
    private Alumne alumne;
    private Professor professor;
    private Aula aula;

    // Associacions de multiplicitat "*" (1..* o 0..*)
    // Implementació: Llistes (Collections)
    private List<Material> materials;
    private List<Incidencia> incidencies;

    // Constructor: Força les relacions obligatòries "1"
    public Prestec(Alumne alumne, Professor professor, Aula aula) {
        this.alumne = alumne;
        this.professor = professor;
        this.aula = aula;
        // Inicialitzem les llistes buides
        this.materials = new ArrayList<>();
        this.incidencies = new ArrayList<>();
    }

    // Mètodes per gestionar la multiplicitat "*" (afegir elements a la llista)
    public void afegirMaterial(Material m) {
        this.materials.add(m);
    }

    public void registrarIncidencia(Incidencia i) {
        this.incidencies.add(i);
    }

    // Mètode per demostrar la navegabilitat (Des de Préstec cap a la resta)
    public void imprimirFitxaPrestec() {
        System.out.println("----- FITXA DE PRÉSTEC -----");
        // Navegació cap a objectes simples
        System.out.println("Sol·licitant: " + alumne.getNom());
        System.out.println("Autoritzat per: " + professor.getNom());
        System.out.println("Ubicació: Ejercicio2.Aula " + aula.getCodi());

        // Navegació cap a col·leccions (Materials)
        System.out.println("Materials prestats (" + materials.size() + "):");
        if (materials.isEmpty()) {
            System.out.println("  [ERROR: Un préstec ha de tenir com a mínim 1 material]");
        } else {
            for (Material m : materials) {
                System.out.println("  - " + m.getDescripcio());
            }
        }

        // Navegació cap a col·leccions (Incidències - Opcional 0..*)
        System.out.println("Incidències registrades:");
        if (incidencies.isEmpty()) {
            System.out.println("  (Cap incidència)");
        } else {
            for (Incidencia i : incidencies) {
                System.out.println("  [!] " + i.getDetall());
            }
        }
        System.out.println("----------------------------\n");
    }
}
