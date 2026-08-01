public class Coche {
   private String marca;
    private String modelo;
    private int velocidad;

    public Coche(String marca,String modelo){
        this.marca=marca;
        this.modelo=modelo;
        this.velocidad=0;

    }
    void acelerar(int parametro){
        this.velocidad+=parametro;
        if (this.velocidad<0){
            this.velocidad=0;
        }
    }
    void frenar (int parametro){
        this.velocidad -= parametro;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
}
