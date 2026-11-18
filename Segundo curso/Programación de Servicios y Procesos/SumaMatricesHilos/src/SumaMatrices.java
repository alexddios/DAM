public class SumaMatrices {
    private int[][] matrizA;
    private int[][] matrizB;
    private int[][] matrizC;

    public SumaMatrices() {
        this(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        }, new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
    }

    public SumaMatrices(int[][] matrizA, int[][] matrizB) {
        this.matrizA = matrizA;
        this.matrizB = matrizB;
        this.matrizC = new int[matrizA.length][matrizA[0].length];
    }

    public int[][] sumar() throws InterruptedException {
        Thread[] hilos = new Thread[matrizA.length];

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(new SumaFila(matrizA, matrizB, matrizC, i));
            hilos[i].start();
        }

        for (int i = 0; i < hilos.length; i++) {
            hilos[i].join();
        }

        return matrizC;
    }

    public void ejecutar() throws InterruptedException {
        System.out.println("Matriz A: ");
        Matriz.imprimir(matrizA);
        System.out.println();
        System.out.println("Matriz B: ");
        Matriz.imprimir(matrizB);

        sumar();

        System.out.println();
        System.out.println("Matriz C: ");
        Matriz.imprimir(matrizC);
    }
}
