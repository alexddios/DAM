import java.util.Scanner;

public class Intermedios {
    static Scanner leer = new Scanner(System.in);

    static void Ejercicio13() {
        suma();
    }

    static void suma() {
        System.out.print("Número 1: ");
        int num1 = Integer.parseInt(leer.nextLine());
        System.out.print("Número 2: ");
        int num2 = Integer.parseInt(leer.nextLine());
        int suma = num1 + num2;
        System.out.println("Suma: " + suma);
    }

    static void Ejercicio14() {
        int n = 6;
        generarTriangulo(n);
    }

    public static void generarTriangulo(int alturaMaxima) {
        for (int i = -(alturaMaxima - 1); i <= (alturaMaxima - 1); i++) {
            int numeroEstrellas = alturaMaxima - Math.abs(i);
            imprimirFila(numeroEstrellas);
        }
    }
    public static void imprimirFila(int cantidad) {
        for (int j = 0; j < cantidad; j++) {
            System.out.print("*");
        }
        System.out.println();
    }

    static void Ejercicio15() {
        final int[] array = {1, 2, 2, 3, 3, 3, 4};
        ordenarArray(array);
        vecesNumeros(array);
    }
    static void vecesNumeros(int [] array){
        int cont = 1;
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == array[i + 1]) {
                cont++;
            } else {
                String palabra = (cont == 1) ? "vez" : "veces";
                System.out.println(array[i] + " aparece " + cont + " " + palabra);
                cont = 1;
            }

        }
        String palabra = (cont == 1) ? "vez" : "veces";
        System.out.println(array[array.length - 1] + " aparece " + cont + " " + palabra);
    }
    static void ordenarArray(int [] array){
        for (int i=0; i<array.length-1;i++){
            for (int j=0; j<array.length-1-i;j++){
                if (array[j]>array[j+1]){
                    int aux=array[j];
                    array[j]=array[j+1];
                    array[j+1]=aux;
                }
            }
        }
    }

}
