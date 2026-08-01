package Ej8;

public class CuentaBancaria {
    private String titular;
    private double saldo;

    public CuentaBancaria(String titular){
        this.titular=titular;
        this.saldo=0;
    }
    void ingresar(double cantidad){
        if (cantidad>=0){
            this.saldo+=cantidad;
            System.out.println("Se han añadido "+cantidad + " € a la cuenta de " + titular);
        }else{
            System.out.println("Solo se aceptan números positivos");
        }
    }
    boolean retirar(double cantidad) {
        if (cantidad <= 0) {
            System.out.println("Solo se aceptan números positivos");
            return false;
        } else if (cantidad > this.saldo) { // Comprobamos si pide más de lo que tiene
            System.out.println("No tienes suficiente dinero");
            return false;
        } else {
            this.saldo -= cantidad;
            System.out.println("Se han sacado " + cantidad + " € de la cuenta de " + titular);
            return true;
        }
    }
    void saldo(){
        System.out.println("Saldo de " + this.titular + " = " + this.saldo);
    }
    void transferir(CuentaBancaria destino, double cantidad){
        boolean transferible=retirar(cantidad);
        if (transferible){
            destino.ingresar(cantidad);
            System.out.println("Se han tranferido " + cantidad + " € de " + this.titular + " a " + destino.titular);
        }else {
            System.err.println("Ha habido algún error");
        }
    }
}
