package ApartadoB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppFesta {
    private String nom;
    private final List<Local> locals;
    private final Map<String, PlaNit> plansNit;

    public AppFesta(String nom){
        this.nom=nom;
        this.locals=new ArrayList<>();
        this.plansNit = new HashMap<>();
    }

    public boolean crearPlaNit(String nom, Local local){
        if (!validarPlaNit(nom, local)) return false;
        PlaNit plaNit = new PlaNit(nom, local);
        this.plansNit.put(nom, plaNit);
        return true;
    }

    private boolean validarPlaNit(String nom, Local local) {
        if (nom == null || nom.isBlank() || this.plansNit.containsKey(nom)) return false;
        if (local == null || !this.locals.contains(local)) return false;
        return true;
    }

    public boolean afegirLocal(Local local) {
        if (local != null && !this.locals.contains(local)) {
            return this.locals.add(local);
        }
        return false;
    }

    public double calcularTotalGastatPerParticipant(String dni){
        double res=0;
        for (PlaNit plaNit : this.plansNit.values()){
            res+= plaNit.obtenirImportGastatParticipant(dni);
        }
        return res;
    }

    public int comptarVegadesQueHaConduit(String dni){
        int cont = 0;
        for (PlaNit plaNit : this.plansNit.values()){
            if (plaNit.haConduitParticipant(dni))cont++;
        }
        return cont;
    }
    public int comptarPlansPerTipusLocal(TipusLocal tipus){
        int cont=0;
        for (PlaNit plaNit : this.plansNit.values()){
            if (plaNit.getLocal().getTipus() == tipus) cont++;
        }
        return cont;
    }
}
