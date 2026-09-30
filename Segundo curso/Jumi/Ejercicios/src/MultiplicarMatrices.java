import java.util.Random;
import java.util.Scanner;

public class MultiplicarMatrices {
    static void main() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        System.out.print("Filas matriz A: ");
        int filA = scanner.nextInt();
        System.out.print("Columnas matriz A: ");
        int colA = scanner.nextInt();
        System.out.print("Filas matriz B: ");
        int filB = scanner.nextInt();
        System.out.print("Columnas matriz B: ");
        int colB = scanner.nextInt();

        if (colA != filB) {
            System.out.println("Error: El número de columnas de A debe ser igual al número de filas de B.");
            return;
        }

        int matrizA[][] = new int[filA][colA];
        int matrizB[][] = new int[filB][colB];

        int resultado[][] = new int[filA][colB];

        System.out.println("\nMatriz A generada:");
        for (int i = 0; i < filA; i++) {
            for (int j = 0; j < colA; j++) {
                matrizA[i][j] = random.nextInt(9) + 1;
                System.out.print(matrizA[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("\nMatriz B generada:");
        for (int i = 0; i < filB; i++) {
            for (int j = 0; j < colB; j++) {
                matrizB[i][j] = random.nextInt(9) + 1;
                System.out.print(matrizB[i][j] + "\t");
            }
            System.out.println();
        }
        for (int i = 0; i < filA; i++) {
            for (int j = 0; j < colB; j++) {
                for (int k = 0; k < colA; k++) {
                    resultado[i][j] += matrizA[i][k]*matrizB[k][j];
                }
            }
        }
        System.out.println("\nMatriz Resultado (A x B):");
        for (int i = 0; i < resultado.length; i++) {
            for (int j = 0; j < resultado[0].length; j++) {
                System.out.print(resultado[i][j] + "\t");
            }
            System.out.println();
        }
        scanner.close();

    }
}
