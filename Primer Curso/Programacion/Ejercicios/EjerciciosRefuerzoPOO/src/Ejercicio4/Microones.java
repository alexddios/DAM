package Ejercicio4;

public class Microones {
    private boolean ences;
    private boolean estat;
    private int temps;

    public Microones(){
        this.ences=false;
        this.estat=false;
        this.temps=0;
    }
    void encendre(){
        if(ences){
            System.out.println("El microones ja està ences");
        }else{
            ences=true;
        }
    }
    void apagar(){
        if (ences){
            ences=false;
        }else{
            System.out.println("El microones ja está apagat");
        }
    }
    void afegirTemps(int temps){
        if (estat && ences) {
            this.temps += temps;
        }
    }
    void esperar(int temps){
        this.temps-=temps;
        if(this.temps<=0){
            estat=false;
            this.temps=0;
        }
    }
    void iniciar(int tempsInicial){
        if (ences) {
            estat = true;
            this.temps = tempsInicial;
            System.out.println("Microones iniciat amb " + tempsInicial + " segons.");
        } else {
            System.out.println("Error: No es pot iniciar, el microones està apagat.");
        }
    }
    void aturar(){
        estat=false;
        this.temps=0;
    }
    void mostrarEstat(){
        System.out.println("Ences = " + this.ences+"Estat: " + this.estat+ " Temps que queda: "+this.temps);
    }


}
