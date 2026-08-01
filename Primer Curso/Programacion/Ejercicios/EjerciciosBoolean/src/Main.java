//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import  java.util.Scanner;
Scanner leer = new Scanner(System.in);
void main() {

    System.out.print("Que ejercicio quieres? (Del 1 al 3):");
    int eleccion = leer.nextInt();
    leer.nextLine();
    switch (eleccion) {
        case 1 ->
                Ejercicio1();
        case 2 -> 
            Ejercicio2();

        case 3 -> {
            Ejercicio3();
        }

    }

}
void Ejercicio1(){
    boolean estadoparque = false,permiso=false;
    System.out.print("Esta el parque abierto? (Si/No): ");
    var abiertocerrado= leer.nextLine();
    System.out.print("Tienes permiso(Si/No): ");
    var estadopermiso=leer.nextLine();
    if (abiertocerrado.equals("Si")){
        estadoparque=true;
    }
    if (estadopermiso.equals("Si")){
        permiso=true;
    }
    if (estadoparque && permiso){
        System.out.println("Acceso permitido");
    } else{
        System.out.println("Acceso denegado");
    }
}
void Ejercicio2(){
    boolean mantenimiento=false, fuego=false,calor=false;
    System.out.print("Esta el modo mantenimiento activado?(Si/No): ");
    var modo=leer.nextLine();
    if (modo.equals("Si")){
        mantenimiento=true;
    }
    System.out.print("Hay fuego? (Si/No): ");
    var hayfuego=leer.nextLine();
    if (hayfuego.equals("Si")){
        fuego=true;
    }
    System.out.print("Hace calor? (Si/No): ");
    var hacecalor = leer.nextLine();
    if (hacecalor.equals("Si")){
        calor=true;
    }
    if (mantenimiento | (!fuego && !calor)){
        System.out.println("Alarma apagada");
    }else{
        System.out.println("Alarma encendida");
    }
}
void Ejercicio3(){
    boolean limitevelocidad=false,ambulancia=false,luces=false;
    String estadoluces;
    System.out.print("Ha superado de limite de velocidad?(Si/No): ");
    var limitesuperado=leer.nextLine();
    if (limitesuperado.equals("Si")){
        limitevelocidad=true;
    }
    System.out.println("Es una ambulancia? (Si/No): ");
    var esambulancia=leer.nextLine();
    if (esambulancia.equals("Si")){
        ambulancia=true;

    }
    System.out.println("Tiene las luces encendidas?(Si/No): ");
    estadoluces=leer.nextLine();
    if (estadoluces.equals("Si")){
        luces=true;
    }
    if ((limitevelocidad && ambulancia && !luces)|(limitevelocidad && !ambulancia && !luces)){
        System.out.println("Se ha puesto una multa");
    }else{
        System.out.println("No se ha puesto una multa");
    }
}

