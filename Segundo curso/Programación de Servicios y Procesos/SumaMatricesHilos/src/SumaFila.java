public class SumaFila implements Runnable{
    private int [][] matrizA;
    private int [][] matrizB;
    private int [][] matrizC;
    private int fila;

    public SumaFila(int[][] matrizA, int[][] matrizB, int[][] matrizC, int fila) {
        this.matrizA = matrizA;
        this.matrizB = matrizB;
        this.matrizC = matrizC;
        this.fila = fila;
    }

    @Override
    public void run() {
        for (int j = 0; j < matrizA[0].length; j++) {
            matrizC[fila][j] = matrizA[fila][j]+matrizB[fila][j];
        }
    }
}
