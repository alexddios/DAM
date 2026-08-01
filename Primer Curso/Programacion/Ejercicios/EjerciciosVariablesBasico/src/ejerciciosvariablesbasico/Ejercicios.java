/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejerciciosvariablesbasico;

import java.util.Scanner;


/**
 *
 * @author alexd
 */
public class Ejercicios {

    /**
     * @param args the command line arguments
     */
    Scanner leer = new Scanner (System.in);
    Scanner leer1 = new Scanner (System.in);
    public void inicio(){
        System.out.println("Ejercicio 1: Pide al usuario dos números, súmalos y devuelve el resultado.");
        System.out.println("Ejercicio 2: Pide al usuario una palabra y devuelve su longitud.");
        System.out.println("""
                           Ejercicio 3: Pide al usuario una distancia en metros, devuelve los kil\u00f3metros,
                           cent\u00edmetros y mil\u00edmetros.""");
        System.out.print("Elige Ejercicio del 1 al 3:");
        int eleccion = leer.nextInt();
        leer.nextLine();
        switch (eleccion){
            case 1 -> Ejercicio1();
            case 2 -> Ejercicio2();
            case 3 -> Ejercicio3();
        }  
}
    
    public void Ejercicio1(){
         System.out.print("Introduce el primer número:");
        int primernumero=leer.nextInt();
         leer.nextLine();
        System.out.print("Introduce el segundo número:");
        int segundonumero=leer.nextInt();
         leer.nextLine();
        System.out.println("La suma es "  + (primernumero+segundonumero));
    }
    public void Ejercicio2(){
        System.out.print("Introduce la palabra:");
       
        String palabra = leer.nextLine();
        leer.nextLine();
        int longitud = palabra.length();
        System.out.println("La palabra mide:" + longitud);
    }
    public void Ejercicio3(){
        System.out.print("Introduce la distancia en metros: ");
        float metros = leer.nextFloat();
        leer.nextLine();
        System.out.println(metros/1000 + " kilometros");
        System.out.println(metros *100 + " centmetros");
        
        
    }
    public static void main(String[] args) {
        (new Ejercicios ()).inicio();
       
    }
    
}
