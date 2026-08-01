package Ejercicio3;

public class TargetaTransport {
    private double saldo;
    private int viatges;

    public TargetaTransport(double saldo){
        this.saldo=saldo;
        this.viatges=0;
    }

    public void recarregar(double x) {
        if (x>=0){
            this.saldo = this.saldo + x;
        }else{
            System.out.println("No es pot introduir saldo negatiu!");
        }

    }
    public void pagarViatge() {
        if (this.saldo>=1.50) {
            this.saldo = this.saldo - 1.50;
            this.viatges = this.viatges + 1;
        }else{
            System.out.println("No pots pagar el viatge! Et falten "+(1.50-this.saldo) + "€");
        }
    }
    public void estat(){
        System.out.println("Saldo = " + this.saldo + " Viatges = "+this.viatges);
    }
}
