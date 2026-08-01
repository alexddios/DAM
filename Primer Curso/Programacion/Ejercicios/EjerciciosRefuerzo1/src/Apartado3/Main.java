package Apartado3;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Perfil perfil = new Perfil("alex_123", new Date());

        Insignia insignia = new Insignia("Experto", 3);
        perfil.afegirInsignia(insignia);

        System.out.println(perfil.teInsigniaAmbNivellMinim(2)); // true
        System.out.println(perfil.teInsigniaAmbNivellMinim(3)); // true
        System.out.println(perfil.teInsigniaAmbNivellMinim(4)); // false

        Perfil perfilSenseInsignia = new Perfil("juan_99", new Date());
        System.out.println(perfilSenseInsignia.teInsigniaAmbNivellMinim(1)); // false
    }
}
