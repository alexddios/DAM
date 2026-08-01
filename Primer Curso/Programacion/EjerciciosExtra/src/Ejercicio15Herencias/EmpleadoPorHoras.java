package Ejercicio15Herencias;

public class EmpleadoPorHoras extends Empleado{
    private double horasTrabajadas;
    private double pagoPorHora;

    public EmpleadoPorHoras(String nombre, int id, double horasTrabajadas, double pagoPorHora) {
        super(nombre, id);
        this.horasTrabajadas = horasTrabajadas;
        this.pagoPorHora = pagoPorHora;
    }

    @Override
    public void calcularSalario() {
        System.out.printf("El salario de %s es de %.2f con id: %d %n",this.nombre,horasTrabajadas*pagoPorHora,this.id);
    }
}
