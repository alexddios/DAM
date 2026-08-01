package botiga.model;

import java.util.*;

public class Producte extends ArticleCatalog {
    private final Set<String> tags;
    private final List<String> imatges;
    private final Map<String, String> atributs;
    private final String[] codisBarres;

    public Producte(String codi, String nom, double preu) {
        super(codi, nom, preu);
        this.tags = new HashSet<>();
        this.imatges = new ArrayList<>();
        this.atributs = new HashMap<>();
        this.codisBarres = new String[10];
    }


    public boolean teTag(String t) {
        return tags.contains(t);
    }
}