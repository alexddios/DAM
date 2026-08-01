package Ej13Herencias;

public class Vehiculo {
    protected String marca;
    protected String modelo;

    public Vehiculo(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }
    public void encender(){
        System.out.println("El vehículo está encendido");
    }
}
