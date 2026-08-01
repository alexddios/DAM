public class Alumno  extends Persona{
    private String fp;

    public Alumno(String nombre, String fp) {
        super(nombre);
        this.fp = fp;
    }
    void aprender(){
        System.out.println("Estoy aprendiendo");
    }
    @Override
    public void presentar() {
        System.out.println("Mi nombre es " + nombre + " y soy un alumno");
    }
}
