package Ejercicio1;

public class ContadorLimitat {
    private int limitInferior;
    private int limitSuperior;
    private int valor;

    public ContadorLimitat(int limitInferior,int limitSuperior){
        this.limitInferior=limitInferior;
        this.limitSuperior=limitSuperior;
        this.valor=0;
    }
    void mostrar(){
        System.out.println(valor);
    }
    void incrementar(){
        if (valor<limitSuperior){
            this.valor++;
        }else{
            System.out.println("No pots incrementar!");
        }
    }
    void decrementar(){
        if(valor>limitInferior){
            valor--;
        }else{
            System.out.println("No pots decrementar!");
        }
    }

    public void setValor(int valor) {
        if(valor>=this.limitInferior && valor<=this.limitSuperior){
            this.valor = valor;
        }else{
            System.out.println("No pot superar els límits");
        }

    }
}
