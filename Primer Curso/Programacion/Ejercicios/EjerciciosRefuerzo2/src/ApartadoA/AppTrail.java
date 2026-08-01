package ApartadoA;

import java.util.*;

public class AppTrail {
    private String nom;
    private List<Ruta> rutes;

    public AppTrail(String nom) {
        this.nom = nom;
        this.rutes = new ArrayList<>();
    }

    public int comptarRutesPerDificultat(Dificultat dificultat){
        if(dificultat == null) return 0;
        int contador = 0;
        for (Ruta ruta : rutes){
            if (ruta.getDificultat()== dificultat){
                contador++;
            }
        }
        return contador;
    }
    public boolean teRutaAmbDesnivellSuperiorA(double valor){
        if(valor<0) return false;
        for (Ruta ruta : rutes){
            if (ruta.getDesnivellAcumulat() > valor) return true;
        }
        return false;
    }
    public boolean crearRuta(String nom, double desnivellAcumulat, Dificultat dificultat,PuntAvituallament puntAvituallament ){
        if (!validarRuta(nom, desnivellAcumulat, dificultat)) return false;
        Ruta ruta = new Ruta(nom, desnivellAcumulat, dificultat, puntAvituallament);
        return this.rutes.add(ruta);
    }
    private boolean validarRuta(String nom, double desnivellAcumulat, Dificultat dificultat){
        if (nom == null || nom.isBlank()) return false;
        if (desnivellAcumulat<0) return false;
        return dificultat != null;
    }
}
