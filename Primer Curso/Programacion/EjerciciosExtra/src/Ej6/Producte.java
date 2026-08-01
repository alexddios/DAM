package Ej6;

public class Producte {
    private String nom;
    private int quantitat;
    private double preu;


    public Producte(String nom,double preu){
        this.nom=nom;
        this.setQuantitat(0);
        this.preu=preu;
    }
    public void afegirStock(int quantitat){
        this.setQuantitat(this.quantitat + quantitat);
        System.out.println("Se han añadido " + quantitat + " " + nom);
        System.out.println("Ahora tienes " + this.quantitat + nom);
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }
    public void vendre(int quantitat){
        this.quantitat-=quantitat;
        if (this.quantitat<0){
            System.out.println("No hay suficientes productos");
            this.quantitat=0;
        }
    }
    public void mostrar(){
        System.out.println("Producto: " + nom + " Cantidad: " + quantitat + " Precio: " + preu + " €");
    }
}