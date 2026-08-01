package Apartado4;

import java.util.ArrayList;
import java.util.List;

public class Zona {
    private String codi;
    private boolean esInterior;
    private List<Planta> plantes;

    public Zona(String codi, boolean esInterior) {
        this.codi = codi;
        this.esInterior = esInterior;
        this.plantes = new ArrayList<>();
    }

    public boolean afegirPlanta(String nom, String tipusReg, double alturaCm) {
        if(alturaCm<0){
            System.out.println("Altura no valida");
            return false;
        }
        Planta planta = new Planta(nom, tipusReg, alturaCm);
        this.plantes.add(planta);
        return true;
    }
    public int numeroPlantesTipusRegSuperenAlturaIndicada(String tipusReg, double alturaCm){
        int contador = 0;
        for (Planta planta : this.plantes) {
            if (planta.getTipusReg().equals(tipusReg) && planta.getAlturaCm() >= alturaCm) contador++;
        }
        return contador;
    }
}
