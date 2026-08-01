package Ej9;

public class Comandament {
    private int volumen;
    private int canal;
    private boolean encendida;

    public Comandament() {
        this.volumen = 10;
        this.canal = 1;
        this.encendida = false;
    }

    void subirVolumen() {
        if (encendida && volumen < 100) { // Solo si está encendida y hay margen
            volumen++;
            System.out.println("Volumen: " + volumen);
        } else if (!encendida) {
            System.out.println("TV apagada.");
        } else {
            System.out.println("Volumen al máximo.");
        }
    }

    void bajarVolumen() {
        if (encendida && volumen > 0) {
            volumen--;
            System.out.println("Volumen: " + volumen);
        } else if (!encendida) {
            System.out.println("TV apagada.");
        } else {
            System.out.println("Mute.");
        }
    }

    void cambiarCanal(int nouCanal) {
        if (encendida && nouCanal >= 1 && nouCanal <= 50) {
            this.canal = nouCanal;
            System.out.println("Cambiado al canal " + canal);
        } else if (!encendida) {
            System.out.println("La tele está apagada");
        } else {
            System.out.println("El canal " + nouCanal + " no existe [1..50]");
        }
    }

    void botonEncender() {
        encendida = !encendida; // Alterna entre true/false
        System.out.println(encendida ? "TV Encendida" : "TV Apagada");
    }

    void estado() {
        System.out.println("Estado -> Encendida: " + encendida + " | Canal: " + canal + " | Volumen: " + volumen);
    }
}