public class Persona {
    protected  String nombre;

    public Persona(String nombre) {
        this.nombre = nombre;
    }
    public void saludar() {
        System.out.println("Hola, soy " + nombre);
    }
    public void presentar() {
        System.out.println("Mi nombre es " + nombre + " y soy una persona");
    }

}
