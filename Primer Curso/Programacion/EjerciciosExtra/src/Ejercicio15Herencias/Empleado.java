package Ejercicio15Herencias;

// Añadimos 'abstract' para que nadie pueda hacer: new Empleado(...)
public abstract class Empleado {
    protected String nombre;
    protected int id;

    public Empleado(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    // Método abstracto: no tiene cuerpo { }, termina en punto y coma.
    // Obliga a las subclases a implementar su propia versión.
    public abstract void calcularSalario();
}