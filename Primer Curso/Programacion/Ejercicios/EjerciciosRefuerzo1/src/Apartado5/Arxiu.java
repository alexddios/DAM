package Apartado5;

import java.util.*;

public class Arxiu {
    private String departament;
    private Map<String,Document> documents;

    public Arxiu(String departament) {
        this.departament = departament;
        this.documents = new HashMap<>();
    }
    public boolean afegirDocument(String codi, String titol, int numeroPagines){
        if (!validarDocument(codi, numeroPagines)) return false;
        Document document = new Document(codi, titol, numeroPagines);
        documents.put(codi, document);
        return true;
    }

    private boolean validarDocument(String codi, int numeroPagines) {
        if (numeroPagines <1){
            System.out.println("Numero de pagines no valid");
            return false;
        }
        if (documents.containsKey(codi)){
            System.out.println("Ja existeix un document amb aquest codi");
            return false;
        }
        return true;
    }

}
