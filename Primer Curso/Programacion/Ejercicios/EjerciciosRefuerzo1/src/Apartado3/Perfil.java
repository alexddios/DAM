package Apartado3;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Perfil {
    private String nomUsuari;
    private Date dataCreacio;
    private List<Insignia> insignies;

    public Perfil(String nomUsuari, Date dataCreacio) {
        this.nomUsuari = nomUsuari;
        this.dataCreacio = dataCreacio;
        this.insignies = new ArrayList<>();
    }

    public boolean afegirInsignia(Insignia insignia) {
        return this.insignies.add(insignia);
    }

    public boolean teInsigniaAmbNivellMinim(int nivell) {
        for (Insignia insignia : insignies) {
            if (insignia.getNivell() >= nivell) return true;
        }
        return false;
    }
}
