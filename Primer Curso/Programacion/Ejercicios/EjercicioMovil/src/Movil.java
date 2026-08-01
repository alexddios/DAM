public class Movil {
    private final String marca;
    private final String model;
    private int bateria;       // 0..100
    private boolean ences;

    public Movil(String marca, String model) {
        this.marca = marca;
        this.model = model;
        this.bateria = 30;
        this.ences = false;
    }

    public void encendre() {
        if (ences) {
            System.out.println("El mòbil ja està ences");
            return;
        }
        if (bateria <= 0) {
            System.out.println("No es pot encendre: bateria esgotada");
            return;
        }
        ences = true;
    }

    public void apagar() {
        if (!ences) {
            System.out.println("El mòbil ja està apagat");
            return;
        }
        ences = false;
    }

    public void utilitzar(int minuts) {
        if (minuts < 0) {
            System.out.println("Minuts negatius");
            return;
        }
        if (!ences) {
            System.out.println("El mòbil està apagat");
            return;
        }

        bateria -= minuts;
        if (bateria <= 0) {
            bateria = 0;
            apagar();
        }
    }

    public void carregar(int minuts) {
        if (minuts < 0) {
            System.out.println("Minuts negatius");
            return;
        }

        bateria += minuts;
        if (bateria > 100) {
            bateria = 100;
        }

        // Si quieres encender automáticamente al tener batería:
        // if (!ences && bateria > 0) encendre();
    }

    public void imprimirEstat() {
        String encendido = ences ? "Enendido":"Apagado";
        System.out.println(marca + " " + model + " " + bateria + " " + encendido);
    }

}
