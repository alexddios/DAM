/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejerciciosprebucles;

import java.util.Scanner;

/**
 *
 * @author alexd
 */
public class EjerciciosPreBucles {

    /**
     * @param args the command line arguments
     */
    Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        (new EjerciciosPreBucles()).inicio();
    }

    public void inicio() {
        System.out.print("Que ejercicio quieres? (Del 1 al 3)");
        int eleccion = leer.nextInt();
        leer.nextLine();
        switch (eleccion) {
            case 1 ->
                Ejercicio1();
            case 2 ->
                Ejercicio2();
            case 3 ->
                Ejercicio3();
        }
    }

    public void Ejercicio1() {
        int numerodenotas = 5;
        float sumanotas = 0;
        for (int i = 1; i <= numerodenotas; i++) {
            System.out.print("Introduce nota " + i + " :");
            float nota;
            nota = leer.nextFloat();
            leer.nextLine();
            sumanotas = sumanotas + nota;
        }
        float media = sumanotas / numerodenotas;
        System.out.println("La media de las notas es: " + media);

    }

    private void Ejercicio2() {
        double preciototal = 0;
        System.out.print("Cuantos platos?");
        int platos = leer.nextInt();
        leer.nextLine();
        for (int i = 1; i <= platos; i++) {
            System.out.print("Introduce el precio del plato " + i + " :");
            double precio = leer.nextDouble();
            leer.nextLine();
            preciototal = preciototal + precio;
        }
        if (preciototal > 50) {
            double descuento = preciototal * 0.1;
            preciototal = preciototal - descuento;
        }
        System.out.print("Cuanto dinero tienes?");
        double dinero = leer.nextDouble();
        leer.nextLine();
        if (dinero >= preciototal) {
            System.out.println("¡Genial! Tu total es " + preciototal + "€. Tienes " + dinero + ", así que te sobran " + Math.round(dinero - preciototal) + "€.");
        } else {
            System.out.println("¡Oh, vaya! Tu total es " + preciototal + ", pero solo tienes " + dinero + ", así que te faltan " + Math.round(preciototal - dinero) + "€.");
        }
    }

    private void Ejercicio3() {
        double temperatura;
        double media=0;
        double suma=0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        String diascriticos="";
        for (int i = 1; i <=7; i++) {
            System.out.print("Temperatura del día " + i + ": ");
             temperatura = leer.nextInt();
             leer.nextLine();
             suma += temperatura;
             media = suma/7;
             if (temperatura <min){
                 min=temperatura;
             }
             if (temperatura>max){
                 max=temperatura;
             }
             if (temperatura <= 0 || temperatura >= 35){
                 diascriticos+= +i +",";
             }
        }
        System.out.println("La media de las temperaturas es: " + media);
        System.out.println("La temperatura mínima es de: " + min);
        System.out.println("La temperatura máxima es de: " + max);
        System.out.println("Los dias criticos han sido: " + diascriticos);
        
    }
}
