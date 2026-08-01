import java.util.Scanner;

public class Faciles {
    static Scanner leer = new Scanner(System.in);
    static void Ejercicio1(){
     bienvenida();
    }
   static void bienvenida(){
        System.out.println("Bienvenido a los ejercicios");
    }
    static void Ejercicio2(){
        menu();
    }
    static void menu(){
        System.out.println("Opción 1: Hola");
        System.out.println("Opción 2: Adios");
        System.out.print("Elige opción:");
        var opcion = Integer.parseInt(leer.nextLine());
        switch (opcion){
            case 1-> System.out.println("Hola");
            case 2-> System.out.println("Adios");
        }
    }
    static void Ejercicio3(){
    guiones();
    }
    static void guiones(){
        for (int i=0; i<=30;i++){
            System.out.print("-");
        }
    }
    static void Ejercicio4(){
        System.out.print("Escribe tu nombre: ");
        var nombre = leer.nextLine();
        saludo(nombre);
    }
    static void saludo(String nombre){
        System.out.println("Hola " + nombre);
    }
    static void Ejercicio5(){
        System.out.print("Número 1: ");
        int num1=Integer.parseInt(leer.nextLine());
        System.out.print("Número 2: ");
        int num2 = Integer.parseInt(leer.nextLine());
        division(num1,num2);
    }
    static void division(int num1,int num2){
        System.out.println("La división es: " + num1/num2);
    }
    static void Ejercicio6(){
        System.out.print("Numero de la tabla de multiplicar: ");
        var num = Integer.parseInt(leer.nextLine());
        tabla(num);
    }
    static void tabla(int num){
        for (int i=1;i<=num;i++){
            for(int j=1;j<num+1;j++){
                System.out.print(i+ "x" + j + " = ");
                System.out.print(i*j + " ");
            }
            System.out.println();
        }
    }
    static void Ejercicio7(){
        System.out.print("Número 1: ");
        var num1= Integer.parseInt(leer.nextLine());
        System.out.print("Número 2: ");
        var num2 = Integer.parseInt(leer.nextLine());
        int res= suma(num1,num2);
        System.out.println("La suma es: " + res);
    }
    static int suma(int n1,int n2){
        return n1+n2;
    }
    static void Ejercicio8(){
        System.out.print("Número: ");
        int num = Integer.parseInt(leer.nextLine());
    boolean res = esPar(num);
        System.out.println("Resultado = " + res);
    }
    static boolean esPar(int num){
        return num % 2 == 0;
    }
    static void Ejercicio9(){
        System.out.print("Número 1: ");
        int num1 = Integer.parseInt(leer.nextLine());
        System.out.print("Número 2: ");
        int num2 = Integer.parseInt(leer.nextLine());
        int mayor = mayor(num1,num2);
        System.out.println("Mayor = " + mayor);
    }
    static int mayor(int n1,int n2){
        return Math.max(n1, n2);
    }
    static void Ejercicio10(){
        System.out.print("Cuanto mide el lado del cuadrado? ");
        double lado = Double.parseDouble(leer.nextLine());
        double area = calcularArea(lado);
        System.out.println("Area: " + area);
    }
    static double calcularArea(double lado){
        return lado*lado;
    }
    static void Ejercicio11(){
        System.out.print("Precio del producto: ");
        double precio = Double.parseDouble(leer.nextLine());
        double precioFinal= aplicarIVA(precio);
        System.out.println("El precio final es de " + precioFinal + "€");
    }
    static double aplicarIVA(double precio){
        double iva = precio *0.21;
        return precio +iva;
    }
    static void Ejercicio12(){
        System.out.print("Nota: ");
        var nota = Double.parseDouble(leer.nextLine());
        String res = resultado(nota);
        System.out.println("Resultado: " + res);
    }
    static String resultado(double nota){
        if (nota>=9) return "Sobresaliente";
        else if(nota>=7) return "Notable";
        else if (nota>=5) return "Aprobado";
        else return "Suspenso";
    }

}
