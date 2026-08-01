package ApartadoA;

import java.util.*;

public class Ruta {
    private String nom;
    private double desnivellAcumulat;
    private Dificultat dificultat;
    private List<Tram> trams;
    private PuntAvituallament puntAvituallament;

    public Ruta(String nom, double desnivellAcumulat, Dificultat dificultat, PuntAvituallament puntAvituallament) {
        this.nom = nom;
        this.desnivellAcumulat = desnivellAcumulat;
        this.dificultat = dificultat;
        this.trams = new ArrayList<>();
        this.puntAvituallament=puntAvituallament;
    }

    public Dificultat getDificultat(){
        return this.dificultat;
    }
    public double getDesnivellAcumulat(){
        return this.desnivellAcumulat;
    }

    public int comptarTramsPerTerreny(TipusTerreny terreny){
        int contador = 0;
        if(terreny == null) return 0;
        for (Tram tram : this.trams){
            if (tram.getTipusTerreny()==terreny) contador++;
        }
        return contador;
    }

    public boolean crearTram(int numero, double distancia, TipusTerreny terreny, Afluencia afluencia){
        if(!validarTram(numero, distancia, terreny, afluencia)) return false;
        Tram tram = new Tram(numero,distancia,terreny,afluencia);
        return this.trams.add(tram);
    }

    private boolean validarTram(int numero, double distancia, TipusTerreny terreny, Afluencia afluencia) {
        return numero > 0 && distancia > 0 && terreny != null && afluencia != null;
    }
}
