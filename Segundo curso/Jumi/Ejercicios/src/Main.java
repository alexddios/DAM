//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    ej5();
}

void ej1() {
    int sum = 0;
    for (int i = 0; i < 100; i++) {
        if (i % 2 != 0) {
            sum += i;
        }

    }
    System.out.println(sum);
}

void ej2() {
    for (int i = 1; i <= 10; i++) {
        System.out.print("Tabla del " + i);
        System.out.println();
        for (int j = 1; j <= 10; j++) {
            System.out.print(i + "*" + j + " = " + i * j + " ");
            System.out.println();
        }
    }

}

//Escribir por pantalla el calendario en formato de calendario con mes, dia de la semana y día
void ej3() {
    Map<String, Integer> map = new HashMap<>();
    map.put("Enero", 31);
    map.put("Febrero", 28);
    map.put("Marzo", 31);
    map.put("Abril", 30);
    map.put("Mayo", 31);
    map.put("Junio", 30);
    map.put("Julio", 31);
    map.put("Agosto", 31);
    map.put("Septiembre", 30);
    map.put("Octubre", 31);
    map.put("Noviembre", 30);
    map.put("Diciembre", 31);

    String[] mesesOrdenados = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    int diaSemanaActual = 3;

    for (String mes : mesesOrdenados) {
        int totalDias = map.get(mes);

        System.out.println(mes);
        System.out.println("Lu Ma Mi Ju Vi Sa Do");

        for (int i = 0; i < diaSemanaActual; i++) {
            System.out.print("   ");
        }

        for (int dia = 1; dia <= totalDias; dia++) {
            System.out.printf("%2d ", dia);

            diaSemanaActual = (diaSemanaActual + 1) % 7;

            if (diaSemanaActual == 0) {
                System.out.println();
            }
        }

        if (diaSemanaActual != 0) {
            System.out.println();
        }
        System.out.println();
    }
}

void ej4() {
    int n = 7;

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (j == i || j == (n - 1 - i) || j == n / 2 || i == n / 2) {
                System.out.print("* ");
            } else {
                System.out.print("  ");
            }
        }
        System.out.println();
    }
}

void ej5() {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    System.out.print("Introduce el número de filas: ");
    int filas = scanner.nextInt();
    System.out.print("Introduce el número de columnas: ");
    int columnas = scanner.nextInt();

    int[][] matriz = new int[filas][columnas];

    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            matriz[i][j] = random.nextInt(99) + 1;
        }
    }

    System.out.println("\nMatriz original:");
    imprimirMatriz(matriz);

    ordenarBurbuja(matriz);

    System.out.println("\nMatriz ordenada de menor a mayor:");
    imprimirMatriz(matriz);

    scanner.close();
}

void ordenarBurbuja(int[][] matriz) {
    int filas = matriz.length;
    int columnas = matriz[0].length;
    int totalElementos = filas * columnas;

    for (int i = 0; i < totalElementos - 1; i++) {
        for (int j = 0; j < totalElementos - 1 - i; j++) {
            int fActual = j / columnas;
            int cActual = j % columnas;

            int fSiguiente = (j + 1) / columnas;
            int cSiguiente = (j + 1) % columnas;

            if (matriz[fActual][cActual] > matriz[fSiguiente][cSiguiente]) {
                int aux = matriz[fActual][cActual];
                matriz[fActual][cActual] = matriz[fSiguiente][cSiguiente];
                matriz[fSiguiente][cSiguiente] = aux;
            }
        }
    }
}

void imprimirMatriz(int[][] matriz) {
    for (int[] fila : matriz) {
        for (int elemento : fila) {
            System.out.printf("%4d", elemento);
        }
        System.out.println();
    }
}

