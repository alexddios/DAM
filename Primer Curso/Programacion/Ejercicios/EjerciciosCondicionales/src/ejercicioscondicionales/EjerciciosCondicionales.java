/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejercicioscondicionales;

import java.util.Scanner;

/**
 *
 * @author alexd
 */
public class EjerciciosCondicionales {

    /**
     * @param args the command line arguments
     */
    public static Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        (new EjerciciosCondicionales()).inicio();
    }

    public void inicio() {
        System.out.print("Que ejercicio quieres? (Del 1 al 4)");
        int eleccion = leer.nextInt();
        leer.nextLine();
        switch (eleccion) {
            case 1 ->
                Ejercicio1();
            case 2 -> {
                Ejercicio2();
            }
            case 3 -> {
                System.out.print("Que apartado? (De la a a la e)");
                String letra = leer.nextLine();
                Ejercicio3(letra);
            }
            case 4 ->
                Ejercicio4();

        }

    }

    private void Ejercicio1() {
        System.out.print("Cuanto dinero tienes? ");
        float dinero = leer.nextFloat();
        leer.nextLine();
        if (dinero > 300) {
            System.out.println("Puedes comprar el sofa");
        } else {
            System.out.println("No puedes comprar el sofa");
        }
    }

    private void Ejercicio2() {
        System.out.print("Cuanto dinero tienes?");
        float dinero = leer.nextFloat();
        leer.nextLine();
        System.out.print("Cuanto cuesta tu juego?");
        float dinerojuego = leer.nextFloat();
        leer.nextLine();
        if (dinerojuego < dinero) {
            System.out.println("Puedes comprar el juego, te sobran " + (dinero - dinerojuego) + "€");
        } else {
            System.out.println("No puedes comprar el juego, te faltan " + (dinerojuego - dinero) + "€");
        }
    }

    private void Ejercicio3(String letra) {
        System.out.print("Introduce la temperatura: ");
        int temperatura = leer.nextInt();
        switch (letra) {
            case "a": {
                if (10 <= temperatura) {
                    System.out.println("La tomatera no esta muerta");
                } else if (temperatura <= 27) {
                    System.out.println("La tomatera no esta muerta");
                } else {
                    System.out.println("La tomatera está muerta");
                }
                break;
            }
            case "b": {
                if (10 <= temperatura && temperatura <= 27) {
                    System.out.println("La tomatera no esta muerta");
                } else {
                    System.out.println("La tomatera está muerta");
                }
                break;
            }
            case "c":
                if (temperatura < 10) {
                    System.out.println("La tomatera se ha muerto de frio");
                } else if (temperatura > 27) {
                    System.out.println("La tomatera se ha muerto de calor");
                } else {
                    System.out.println("La tomatera no se ha muerto");
                }
                break;
            case "d":
                System.out.print("Introduce la humedad: ");
                int humedad = leer.nextInt();
                leer.nextLine();
                if (60 <= humedad && humedad <= 80) {
                    System.out.println("La tomatera no esta muerta");
                } else {
                    System.out.println("La tomatera está muerta");
                }
                break;
            case "e":
                System.out.print("Introduce la humedad: ");
                humedad = leer.nextInt();
                leer.nextLine();
                boolean estaViva=true;
                String causaMuerte="";

        if (temperatura < 10) {
            causaMuerte += "ha muerto de FRÍO";
            estaViva = false;
        } else if (temperatura > 25) {
            causaMuerte += "ha muerto de CALOR";
            estaViva = false;
        }

        if (humedad < 60) {
            if (!estaViva) {
                causaMuerte += " y ";
            }
            causaMuerte += "tiene POCA HUMEDAD";
            estaViva = false;
        } else if (humedad > 80) {
            if (!estaViva) {
                causaMuerte += " y ";
            }
            causaMuerte += "tiene EXCESO DE HUMEDAD";
            estaViva = false;
        }
        if (estaViva) {
            System.out.println("La tomatera está viva");
        } else {
            System.out.println("La tomatera " + causaMuerte + ".");
        }
            default:
                break;
        }
    }

    private void Ejercicio4() {
        System.out.print("Introduce un número:");
        int numero = leer.nextInt();
        leer.nextLine();
        if ((numero%2)==0){
            System.out.println("El número es par");
        }else{
            System.out.println("El número es impar");
        }
    }
}
