import java.util.*;

public class BuscadorNotes {
    // Atribut 'final' per garantir que la referència a la llista no canviï mai
    private final List<String> notes;

    // Constructor: Millor pràctica per inicialitzar objectes i dependències
    public BuscadorNotes() {
        this.notes = new ArrayList<>();
    }

    public void afegirNota(String txt) {
        // Validació simple per evitar afegir nuls a la col·lecció
        if (txt != null) {
            notes.add(txt);
        }
    }

    public List<String> cercar(String consulta) {
        List<String> resultats = new ArrayList<>();

        // 1. Guard Clauses (Clàusules de salvaguarda):
        // Retornem d'immediat si la consulta no és vàlida per evitar càlculs innecessaris.
        if (consulta == null || consulta.trim().isEmpty()) {
            return resultats;
        }

        // 2. Eficiència: Normalitzem la consulta UNA sola vegada fora del bucle.
        // En la teva versió anterior, es processava la consulta en cada iteració.
        String queryNormalitzada = normalitzar(consulta);

        // 3. Bucle per processar cada nota
        for (String nota : notes) {
            if (nota != null) {
                //Com que String és IMMUTABLE, el mètode normalitzar
                // ha de RETORNAR un nou String. L'original 'nota' es manté intacte.
                String notaNormalitzada = normalitzar(nota);

                // Comparem sobre el text "net", però si coincideix,
                // afegim la nota ORIGINAL (amb accents/majúscules) als resultats.
                if (notaNormalitzada.contains(queryNormalitzada)) {
                    resultats.add(nota);
                }
            }
        }

        return resultats;
    }

    /**
     * Mètode auxiliar per centralitzar la lògica de "neteja" de text.
     */
    private String normalitzar(String text) {
        // Retornem el resultat de les transformacions encadenades.
        // Nota: .replace() no modifica el String original, en retorna un de nou.
        return text.trim().toLowerCase()
            .replace("à", "a")
            .replace("è", "e")
            .replace("é", "e")
            .replace("í", "i")
            .replace("ò", "o")
            .replace("ó", "o")
            .replace("ú", "u");
    }
}