/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejerciciosbucles;

import java.util.Scanner;

/**
 *
 * @author alexd
 */
public class EjerciciosBucles {

    /**
     * @param args the command line arguments
     */
    public Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        (new EjerciciosBucles()).inicio();
    }

    public void inicio() {
        System.out.print("Que ejercicio quieres? (Del 1 al 6):");
        int eleccion = leer.nextInt();
        leer.nextLine();
        switch (eleccion) {
            case 1 ->
                Ejercicio1();
            case 2 -> {
                Ejercicio2();
            }
            case 3 -> {
                Ejercicio3();
            }
            case 4 ->
                Ejercicio4();
            case 5 -> {
                Ejercicio5();
            }
            case 6 -> {
                Ejercicio6();
            }

        }

    }

    public void Ejercicio1() {
        final int numerolimite = 100;
        for (int i = 1; i <= numerolimite; i++) {
            System.out.println(i);
        }

    }

    private void Ejercicio2() {
        System.out.print("Introduce el número límite: ");
        int numlimite = leer.nextInt();
        leer.nextLine();
        for (int i = 1; i <= numlimite; i++) {
            System.out.println(i);
        }
    }

    private void Ejercicio3() {
        System.out.print("Número para la tabla de multiplicar? ");
        int num = leer.nextInt();
        for (int i = 1; i <= 10; i++) {
            System.out.println(num + "*" + i + "= " + i * num);
        }
    }

//    private void Ejercicio4() {
//        int numRandom = generarNumeroAleatorio(1, 1);
//        int num;
//        do {
//            System.out.print("Introduce un numero: ");
//            num = leer.nextInt();
//            leer.nextLine();
//            if (num != numRandom) {
//                System.out.println("Número incorrecto");
//            }
//
//        } while (num != numRandom);
//        System.out.println("Número correcto!");
//    }
//    private void Ejercicio4() {
//        int numRandom = generarNumeroAleatorio(1, 1);
//        int num;
//        System.out.print("Introduce un numero: ");
//        num = leer.nextInt();
//        leer.nextLine();
//        while (num != numRandom) {
//            System.out.println("Número incorrecto");
//            System.out.print("Introduce un numero: ");
//            num = leer.nextInt();
//            leer.nextLine();
//        }
//        System.out.println("Número correcto!");
//    }
    private void Ejercicio4() {
        int numRandom = generarNumeroAleatorio(1, 10);
        int num;
        System.out.print("Introduce un numero: ");
        num = leer.nextInt();
        leer.nextLine();
        for (int i = 0; num != numRandom; i++) {
            System.out.println("Número incorrecto");
            System.out.print("Introduce un numero: ");
            num = leer.nextInt();
            leer.nextLine();
        }
        System.out.println("Número correcto!");
    }

    private void Ejercicio5() {
        int suma = 0;
        System.out.print("Introduce el número límite: ");
        int numLimite = leer.nextInt();
        leer.nextLine();
        for (int i = 1; i <= numLimite; i++) {
            if (i % 2 == 0) {
                suma += i;
            }
        }
        System.out.println("La suma es: " + suma);
    }

    private void Ejercicio6() {
        int numRandom;
        int num;
        int acertados = 0;
        int puntos = 0;
        String numacertados="";
        for (int i = 1; i <= 10; i++) {
            numRandom = generarNumeroAleatorio(1, 6);
            System.out.print("Número " + i + ": ");
            num = leer.nextInt();
            if (num == numRandom) {
                acertados++;
                puntos += numRandom;
                numacertados+=i +",";
                
            }
        }
        System.out.println("Acertados: " + acertados);
        System.out.println("Puntos: " + puntos);
        System.out.println("Números acertados:" + numacertados);
    }

    public static int generarNumeroAleatorio(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

}
