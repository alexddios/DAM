package Ej12;

public class Ascensor {
    private int pisoActual;
    private int pisoMaximo;
    private boolean puertasAbiertas;

    public Ascensor(int pisoMaximo){
        this.pisoActual=0;
        this.pisoMaximo=pisoMaximo;
        this.puertasAbiertas=false;
    }
    void abrirPuertas(){

        if(puertasAbiertas){

            System.out.println("Las puertas ya están abiertas");

        }else{

            puertasAbiertas=true;

            System.out.println("Puertas abiertas");

        }

    }

    void cerrarPuertas(){

        if (puertasAbiertas){

            puertasAbiertas=false;

            System.out.println("Puertas cerradas");

        }else {

            System.out.println("Las puertas ya está cerradas");

        }

    }

    private boolean puedeMoverse() {
        if (puertasAbiertas) {
            System.out.println("ERROR: Puertas abiertas.");
            return false;
        }
        return true;
    }

    // Método subir: ahora es el que realmente cambia el piso
    void subir() {
        if (!puedeMoverse()) return;
        if (pisoActual < pisoMaximo) {
            pisoActual++;
            System.out.println("Ascensor en piso " + pisoActual);
        } else {
            System.out.println("Ya estás en el ático.");
        }
    }

    // Método bajar: igual que subir pero hacia abajo
    void bajar() {
        if (!puedeMoverse()) return;
        if (pisoActual > 0) {
            pisoActual--;
            System.out.println("Ascensor en piso " + pisoActual);
        } else {
            System.out.println("Ya estás en la planta baja.");
        }
    }

    // Método irA: decide si tiene que subir o bajar repetidamente
    void irA(int pisoDestino) {
        if (!puedeMoverse()) return;
        if (pisoDestino < 0 || pisoDestino > pisoMaximo) {
            System.out.println("Piso no válido.");
            return;
        }

        System.out.println("Iniciando trayecto al piso " + pisoDestino + "...");

        while (pisoActual != pisoDestino) {
            if (pisoActual < pisoDestino) {
                subir();
            } else {
                bajar();
            }
        }
        System.out.println("Has llegado a tu destino.");
    }
    void mostrarEstado() {
        String estadoPuertas = puertasAbiertas ? "ABIERTAS" : "CERRADAS";
        System.out.println("Piso actual: " + pisoActual + " | Puertas: " + estadoPuertas);
    }
}