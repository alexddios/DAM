//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
Scanner leer = new Scanner(System.in);

void main() {
    System.out.print("Elige dificultad (Intermedio(I)/Difícil(D): ");
    var eleccion = leer.nextLine();
    switch (eleccion) {
        case "I" -> EjerciciosIntermedios();
        case "D" -> EjerciciosDificiles();
    }
}

void EjerciciosIntermedios() {
    System.out.print("Elige ejercicio (Del 1 al 4): ");
    var eleccion = Integer.parseInt(leer.nextLine());
    switch (eleccion) {
        case 1 -> Ejercicio1();
        case 2 -> Ejercicio2();
        case 3 -> Ejercicio3();
        case 4 -> Ejercicio4();
    }
}

void Ejercicio1() {
    final int[][] array = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}};
    for (int[] col : array) {
        for (int num : col) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}

void Ejercicio2() {
    final int[][] array = {
            {2, 4, 6},
            {1, 3, 5},
            {7, 8, 9}};
    int suma = 0;
    for (int[] col : array) {
        for (int num : col) {
            suma += num;
        }
    }
    System.out.println("La suma de todos los elementos es: " + suma);
}

void Ejercicio3() {
    final int[][] array = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}};

    for (int i = 0; i < array.length; i++) {
        int sumafila = 0;
        for (int j = 0; j < array[i].length; j++) {
            sumafila += array[i][j];
        }
        System.out.println("Fila " + i + " suma: " + sumafila);
    }
    int totalColumnas = array[0].length;
    int totalFilas = array.length;
    for (int j = 0; j < totalColumnas; j++) {
        int sumaCol = 0;
        for (int i = 0; i < totalFilas; i++) {
            sumaCol += array[i][j];
        }
        System.out.println("Columna " + j + " suma: " + sumaCol);
    }

}

void Ejercicio4() {
    Random ran = new Random();
    int[][][] array = new int[3][4][3];
    for (int i = 0; i < array.length; i++) {
        for (int j = 0; j < array[i].length; j++) {
            for (int k = 0; k < array[i][j].length; k++) {
                int grados;
                if (k == 0) {
                    grados = ran.nextInt(10, 15) + 1;

                } else if (k == 1) {
                    grados = ran.nextInt(15, 34) + 1;
                } else {
                    grados = ran.nextInt(0, 15) + 1;
                }
                array[i][j][k] = grados;
            }
        }
    }
    for (int i = 0; i < array.length; i++) {
        for (int j = 0; j < array[i].length; j++) {
            int maniana = array[i][j][0];
            int tarde = array[i][j][1];
            int noche = array[i][j][2];

            System.out.println("Ciudad " + (i + 1) + ",Día " + (j + 1) + ": Mañana=" + maniana + "ºC, Tarde=" + tarde + "ºC, Noche=" + noche + "ºC");
        }
    }
}

void EjerciciosDificiles() {
    System.out.print("Elige ejercicio (Del 5 al 8): ");
    var eleccion = Integer.parseInt(leer.nextLine());
    switch (eleccion) {
        case 5 -> Ejercicio5();
        case 6 -> Ejercicio6();
        case 7 -> Ejercicio7();
        case 8->Ejercicio8();
    }
}

void Ejercicio5() {

        int[][][] temperaturas = new int[3][4][3];
        String[] nombresPeriodos = {"Mañana", "Tarde", "Noche"}; // Auxiliar para imprimir nombres

        int maxTempAbsoluta = -100;
        int regCiudad = 0, regDia = 0, regPeriodo = 0;


        for (int i = 0; i < temperaturas.length; i++) {
            for (int j = 0; j < temperaturas[i].length; j++) {
                for (int k = 0; k < temperaturas[i][j].length; k++) {
                    if (k == 0) temperaturas[i][j][k] = (int)(Math.random() * 6 + 10);      // 10-15
                    else if (k == 1) temperaturas[i][j][k] = (int)(Math.random() * 16 + 15); // 15-30
                    else temperaturas[i][j][k] = (int)(Math.random() * 16);                  // 0-15

                    if (temperaturas[i][j][k] > maxTempAbsoluta) {
                        maxTempAbsoluta = temperaturas[i][j][k];
                        regCiudad = i;
                        regDia = j;
                        regPeriodo = k;
                    }
                }
            }
        }

        System.out.println("--- ESTADÍSTICAS POR CIUDAD ---");

        for (int i = 0; i < temperaturas.length; i++) {
            double sumaTotalCiudad = 0;
            int contadorRegistros = 0;


            int maxSumaDia = -1;
            int diaMasCalurosoIndex = -1;

            for (int j = 0; j < temperaturas[i].length; j++) {
                int sumaDelDia = 0;

                for (int k = 0; k < temperaturas[i][j].length; k++) {
                    int tempActual = temperaturas[i][j][k];


                    sumaTotalCiudad += tempActual;
                    contadorRegistros++;


                    sumaDelDia += tempActual;
                }

                if (sumaDelDia > maxSumaDia) {
                    maxSumaDia = sumaDelDia;
                    diaMasCalurosoIndex = j;
                }
            }

            double media = sumaTotalCiudad / contadorRegistros;

            System.out.println("\nCIUDAD " + (i + 1) + ":");
            System.out.printf("  > Temperatura media: %.2fºC\n", media);
            System.out.println("  > Día más caluroso: Día " + (diaMasCalurosoIndex + 1) +
                    " (Suma de temps: " + maxSumaDia + "ºC)");
        }
        System.out.println("\n---------------------------------");
        System.out.println("RÉCORD DE TEMPERATURA MÁXIMA REGISTRADA");
        System.out.println("Temperatura: " + maxTempAbsoluta + "ºC");
        System.out.println("Ocurrió en: Ciudad " + (regCiudad + 1) + ", Día " + (regDia + 1) + ", Horario: " + nombresPeriodos[regPeriodo]);
    }


void Ejercicio6() {
    final int[][] array={
            {1,2},
            {4,5},
            {5,6}
    };
    int filas=array.length;
    int col=array[0].length;
    int [][] transpuesta = new int[col][filas];
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < col; j++) {
            transpuesta[j][i] = array[i][j];
        }
    }
    System.out.println("--- Matriz Original (2x3) ---");
    imprimirMatriz(array);

    System.out.println("\n--- Matriz Transpuesta (3x2) ---");
    imprimirMatriz(transpuesta);
}

void Ejercicio7() {
    int[][] matriz = {
            {1,  2,  3,  4},
            {5,  6,  7,  8},
            {9,  10, 11, 12},
            {13, 14, 15, 16}
    };

    int sumaDiagonal = 0;
    int sumaSecundaria = 0;
    int n = matriz.length;

    for (int i = 0; i < n; i++) {
        sumaDiagonal += matriz[i][i];

        sumaSecundaria += matriz[i][n - 1 - i];
    }

    System.out.println("--- RESULTADOS ---");
    System.out.println("Suma Diagonal Principal: " + sumaDiagonal);
    System.out.println("Suma Diagonal Secundaria: " + sumaSecundaria);
}
void Ejercicio8(){
    final int [][] array={
            {1,2,3},
            {4,5,6},
            {7,8,9}
    };
    int contpares=0,contimpares=0;


    for (int[] col:array){
        for (int num:col){
            if(num%2==0){
                contpares++;
            }else{
                contimpares++;
            }
        }
    }
    System.out.printf("""
            Pares %d
            Impares %d""",contpares,contimpares);
}
void imprimirMatriz(int [][]matriz){
    for (int[] ints : matriz) {
        for (int anInt : ints) {
            System.out.print(anInt + "\t");
        }
        System.out.println();
    }
}