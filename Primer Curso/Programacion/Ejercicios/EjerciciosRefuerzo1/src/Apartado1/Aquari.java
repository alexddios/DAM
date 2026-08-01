package Apartado1;

import java.util.ArrayList;
import java.util.List;

public class Aquari {
    private int capacitatMaxima;
    private final List<Peix> peixos;

    public Aquari(int capacitatMaxima) {
        this.capacitatMaxima = capacitatMaxima;
        this.peixos = new ArrayList<>();
    }
    public boolean afegirPeix(Peix peix) {
        if (peix == null){
            System.out.println("Peix no valid");
            return false;
        }
       if (peixos.size() >= capacitatMaxima){
           System.out.println("No hi ha cap espai");
           return false;
       }
       peixos.add(peix);
        System.out.println( "Afegit " + peix.getNom());
       return true;
    }
    public int contarPeixos(String especie) {
        if (especie == null){
            System.out.println("Especie no valida");
            return 0;
        }
        int contador = 0;
        for (Peix peix : this.peixos) {
            if (peix.getEspecie().equals(especie)) contador++;
        }
        System.out.printf("%nHi ha %d peixos de la especie %s\n", contador, especie);
        return contador;
    }
}
