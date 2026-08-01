package Ej13Herencias;

public class Coche extends Vehiculo{
    private int numPuertas;

    public Coche(String marca, String modelo, int numPuertas) {
        super(marca, modelo);
        this.numPuertas = numPuertas;
    }

    @Override
    public void encender() {
        System.out.printf("El coche %s %s de  %d puertas ha arrancado",this.marca,this.modelo,this.numPuertas);
    }
}
