package Ej7;

public class Diposit {
    private double capacidadMaxima;
    private double litrosActuales;

    public Diposit(double capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        litrosActuales=0;
    }
    public void repostar(double litros){
        this.litrosActuales+=litros;
        if (this.litrosActuales>=capacidadMaxima){
            this.litrosActuales=capacidadMaxima;
        }
    }
    public void circular(double km){
        this.litrosActuales-=(km*0.05);
        if(litrosActuales<=0) this.litrosActuales=0;
    }
    public void consultar(){
        System.out.println("Litros: " + this.litrosActuales + " Autonomía: " + this.litrosActuales/0.05);
    }
}
