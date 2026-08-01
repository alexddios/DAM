package ApartadoB;

import java.util.HashMap;
import java.util.Map;

public class PlaNit {
    private String nom;
    private Local local;
    private final Map<String,Participacio> participacions;

    public PlaNit(String nom, Local local){
        this.nom=nom;
        this.local=local;
        this.participacions=new HashMap<>();
    }

    public boolean crearParticipacio(String dni, double importGastat, boolean haConduit){
        if(!validarParticipacio(dni,importGastat)) return false;
        Participacio participacio = new Participacio(dni,importGastat,haConduit);
        this.participacions.put(dni,participacio);
        return true;
    }

    private boolean validarParticipacio(String d, double i){
        if (d.isBlank() || this.participacions.containsKey(d)) return false;
        return !(i < 0);
    }

    public double obtenirImportGastatParticipant(String dni){
        Participacio participacio = this.participacions.get(dni);
        if (participacio != null) {
            return participacio.getImportGastat();
        }
        return 0;
    }
    public boolean haConduitParticipant(String dni){
        Participacio participacio = this.participacions.get(dni);
        if (participacio != null){
            return participacio.isHaConduit();
        }
        return false;
    }

    public Local getLocal() {
        return local;
    }
}
