package Ejercicio15Herencias;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {

        EmpleadoSalariado empleadoSalariado = new EmpleadoSalariado("Alex",1,100.50);
        EmpleadoPorHoras empleadoPorHoras = new EmpleadoPorHoras("Caty",2,12.0,50.6);
        List<Empleado> empleados = new ArrayList<>();

        empleados.add(empleadoSalariado);
        empleados.add(empleadoPorHoras);

        for (Empleado e:empleados){
            System.out.println("---------");
            e.calcularSalario();
        }
    }
}
