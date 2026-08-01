public class Profesor extends Persona {
    private String contrato;

    public Profesor(String nombre, String contrato) {
        super(nombre);
        this.contrato = contrato;
    }
    void enseñar(){
        System.out.println("Estoy enseñando");
    }
    @Override
    public void presentar() {
        System.out.println("Mi nombre es " + nombre + " y soy un profesor");
    }
}
