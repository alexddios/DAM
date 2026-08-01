package Ej5;

public class Termostat {
    private double temperaturaActual;
    private double temperaturaMaxima;

    public Termostat(double temperaturaMaxima){
        this.temperaturaActual=20;
        this.temperaturaMaxima=temperaturaMaxima;
    }
    public void pujarTemp(double x){
        this.temperaturaActual+=x;
        if(this.temperaturaActual>=this.temperaturaMaxima){
            System.out.println("La temperatura no puede superar a la máxima");
            this.temperaturaActual=this.temperaturaMaxima;
        }
    }
    public void baixarTemp(double x){
        this.temperaturaActual-=x;
        if(this.temperaturaActual<=0){
            System.out.println("La temperatura no puede bajar de 0");
            this.temperaturaActual=0;
        }
    }

    public void setTemperatura(double temperaturaActual) {
        if (temperaturaActual>0 && temperaturaActual<this.temperaturaMaxima){
            this.temperaturaActual = temperaturaActual;

        }else{
            System.out.println("La temperatura tiene que estar entre 0 y la máxima");
        }

    }
    public void mostrar(){
        System.out.println("temperaturaActual = " + temperaturaActual);
    }
}
