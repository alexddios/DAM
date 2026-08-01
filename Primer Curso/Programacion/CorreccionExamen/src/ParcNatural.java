import java.util.*;

public class ParcNatural {
    private final String id;
    private String nom;
    private boolean obert;
    private List<RecursParc> recursosParc;
    private Map<String, Activitat> activitats;

    public ParcNatural(String id,String nom) {
        this.id = id;
        this.nom = nom;
        this.obert = true;
        this.recursosParc = new ArrayList<>();
        this.activitats = new HashMap<>();
    }
}
