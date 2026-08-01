import java.util.*;

public class Activitat {
    private final String codi;
    private String titol;
    private int placesMaximes;
    private Map<String, Visitant> visitants; //Mejor mapa de cara al método
    private Guia guia;

    public Activitat(String codi, String titol, int placesMaximes,Guia guia) {
        this.codi = codi;
        this.titol = titol;
        this.placesMaximes = placesMaximes;
        this.guia = guia;
        this.visitants = new HashMap<>();
    }
    public boolean reservar(Visitant visitant) {
        if (this.visitants.size() >= this.placesMaximes) return false;
        if (this.visitants.containsKey(visitant.getdni())) return false;
        this.visitants.put(visitant.getdni(), visitant);
        return true;
    }
    
    //Si es una lista:
//    public boolean reservar(Visitant visitant) {
//        if (this.visitants.size() >= this.placesMaximes) return false;
//        for (Visitant v : this.visitants) {
//            if (v.getdni().equals(visitant.getdni())) return false;
//        }
//        this.visitants.add(visitant);
    //}
}
