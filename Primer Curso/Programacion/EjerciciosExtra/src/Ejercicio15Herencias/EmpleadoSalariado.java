package Ejercicio15Herencias;

public class EmpleadoSalariado extends Empleado{
    private double salarioMensual;

    public EmpleadoSalariado(String nombre, int id, double salarioMensual) {
        super(nombre, id);
        this.salarioMensual = salarioMensual;
    }

    @Override
    public void calcularSalario() {
        System.out.printf("El salario de %s es de %.2f con id: %d %n",this.nombre,this.salarioMensual,this.id);
    }
}
