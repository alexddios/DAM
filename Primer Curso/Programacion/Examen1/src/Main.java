//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
Scanner leer = new Scanner(System.in);
Random ran = new Random();

void main() {
    System.out.print("Ejercicio?");
    var Ejercicio = Integer.parseInt(leer.nextLine());
    switch (Ejercicio) {
        case 1 -> Ejercicio1();
        case 2 -> Ejercicio2();
        case 3 -> Ejercicio3();
        case 4 -> Ejercicio4();
    }
}

void Ejercicio1() {
    System.out.print("Tienes entrada? (true/false):");
    var tieneEntrada = Boolean.parseBoolean(leer.nextLine());
    System.out.print("Cuantos años tienes? ");
    var anios = Integer.parseInt(leer.nextLine());
    if (tieneEntrada && anios >= 18) {
        System.out.println("Puedes entrar");
    } else {
        System.out.println("No puedes entrar");
    }
}

void Ejercicio2() {
    while (true) {
        int numRandom = ran.nextInt(6) + 1;
        switch (numRandom) {
            case 6 -> System.out.println("Has sacado un seis");
            case 1 -> System.out.println("Mala suerte");
            default -> System.out.println("Número: " + numRandom);
        }
    }
}

void Ejercicio3() {
    int[] array = new int[5];
    int suma = 0;
    for (int i = 0; i < array.length; i++) {
        System.out.print("Número? ");
        array[i] = Integer.parseInt(leer.nextLine());

    }
    for (int num : array) {
        suma += num;
    }
    System.out.println("Suma = " + suma);
}

void Ejercicio4() {
    int dim = 3;
    int[][] array = new int[dim][dim];
    int suma = 0;
    for (int i = 0; i < array.length; i++) {
        for (int j = 0; j < array[i].length; j++) {
            System.out.print("Número de la fila " + i + " y de la columna " + j + " : ");
            array[i][j] = Integer.parseInt(leer.nextLine());
        }
    }
    for (int[] ints : array) {
        for (int anInt : ints) {
            System.out.print(anInt + " ");
        }
        System.out.println();
    }
    for (int i=0; i< array.length;i++){
        for (int j=0; j<array[i].length;j++){
            if (i==j){
                suma+=array[i][j];
            }
        }
    }
    System.out.println("Suma = " + suma);
}
