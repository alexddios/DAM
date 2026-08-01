package Ej13Herencias;

public class Main {
    public static void main(String[] args){
        Vehiculo v = new Vehiculo("Camion","Corola");
        Coche c = new Coche("Tesla", "S Plaid",4);
        v.encender();
        c.encender();
    }
}
