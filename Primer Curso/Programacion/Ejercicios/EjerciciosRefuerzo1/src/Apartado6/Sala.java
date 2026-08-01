package Apartado6;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private String nom;
    private List<Pieza> peces;

    public Sala(String nom) {
        this.nom = nom;
        this.peces = new ArrayList<>();
    }

    public boolean afegirPieza(Pieza p) {
        if (p.getAny() <0) return false;
        peces.add(p);
        return true;
    }
    public int numeroPiezasMineralesDurezaSuperior(int dureza){
        int contador = 0;
        for (Pieza p : peces) {
            if (p instanceof Mineral && ((Mineral) p).getDuresa() >= dureza) contador++;
        }
        return contador;
    }
}
