package Ejercicio2;

public class Jugador {
   private String nom;
    private int punts;
    private int record;

    public Jugador(String nom){
        this.nom=nom;
        this.punts=0;
        this.record=0;
    }
    void sumarPunts(int quantitat){
        this.punts+=quantitat;
    }
    void restarPunts(int quantitat){
        this.punts-=quantitat;
        if(this.punts<0){
            this.punts=0;
        }
    }
    void actualizarRecord(){
        if(this.punts>this.record){
            this.record=this.punts;
        }
    }
    void mostrar(){
        System.out.println(this.nom + " punts="+this.punts + " record=" + record);
    }
}
