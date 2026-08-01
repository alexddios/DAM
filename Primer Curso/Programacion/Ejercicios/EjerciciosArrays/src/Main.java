
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
Scanner leer = new Scanner(System.in);

void main() {
    System.out.print("Elige dificultad (Fácil(F)/Intermedio(I)/Difícil(D): ");
    var eleccion = leer.nextLine();
    switch (eleccion) {
        case "F" -> EjerciciosFaciles();
        case "I" -> EjerciciosIntermedios();
        case "D" -> EjerciciosDificiles();
    }
}

void EjerciciosFaciles() {
    System.out.print("Elige ejercicio (Del 1 al 10): ");
    var eleccion = Integer.parseInt(leer.nextLine());
    switch (eleccion) {
        case 1 -> Ejercicio1();
        case 2 -> Ejercicio2();
        case 3 -> Ejercicio3();
        case 4 -> Ejercicio4();
        case 5 -> Ejercicio5();
        case 6 -> Ejercicio6();
        case 7 -> Ejercicio7();
        case 8 -> Ejercicio8();
        case 9 -> Ejercicio9();
        case 10 -> Ejercicio10();
    }
}

void Ejercicio1() {
    int[] numeros = {1, 2, 3, 4, 5};
}

void Ejercicio2() {
    int[] numeros = new int[5];
    numeros[0] = 1;
    numeros[1] = 2;
    numeros[2] = 3;
    numeros[3] = 4;
    numeros[4] = 5;
}

void Ejercicio3() {
    int[] numeros = new int[5];
    int sum = 0;
    System.out.println("Introduce 5 números");
    for (int i = 0; i < numeros.length; i++) {
        System.out.print("Número " + i + ": ");
        var numero = Integer.parseInt(leer.nextLine());
        numeros[i] = numero;
        sum += numero;
    }
    for (int i = 0; i < numeros.length - 1; i++) {
        System.out.println(numeros[i]);
    }
    System.out.println(numeros[4] + " +");
    System.out.println("------");
    System.out.print(sum);
}

void Ejercicio4() {
    int[] numeros = {5, 3, 10, -8, 25, 0};
    int numGrande = Integer.MIN_VALUE;
    int numPequenio = Integer.MAX_VALUE;
    for (int numero : numeros) {
        if (numero > numGrande) {
            numGrande = numero;
        }
        if (numero < numPequenio) {
            numPequenio = numero;
        }
    }
    System.out.println("El número más grande es: " + numGrande);
    System.out.println("El número más pequeño es: " + numPequenio);
}

void Ejercicio5() {
    final int[] array = {1, 6, 2, 4, 3, 1, 25};
    boolean encontrado = false;
    System.out.print("Introduce un número: ");
    var num = Integer.parseInt(leer.nextLine());
    for (int numero : array) {
        if (num == numero) {
            encontrado = true;
            System.out.println("Encontrado");
        }
    }
    if (!encontrado) {
        System.out.println("No encontrado");
    }

}

void Ejercicio6() {
    final String[] array = {"patata", "bolsa", "manzana", "tomate", "ordenador"};
    boolean encontrado = false;
    System.out.print("Introduce una palabra: ");
    var palabra = leer.nextLine();
    for (String pal : array) {
        if (palabra.equals(pal)) {
            encontrado = true;
            System.out.println("Encontrado");
        }
    }
    if (!encontrado) {
        System.out.println("No encontrado");
    }
}

void Ejercicio7() {
    final int[] array = {1, 2, 1, 4, 1, 5};
    int contador = 0;
    for (int uno : array) {
        if (uno == 1) {
            contador++;
        }
    }
    System.out.println("El uno sale: " + contador + " veces.");
}

void Ejercicio8() {
    int[] array = new int[20];
    int numeros = 1;
    for (int i = 0; i < array.length; i++) {
        array[i] = numeros;
        numeros++;
    }
    imprimirArray(array);
}

void Ejercicio9() {
    final int[] array = {1, 6, 2, 4, 3, 1, 25};
    int[] arraynuevo = copiarArray(array);
    imprimirArray(arraynuevo);
}

void Ejercicio10() {
    final int[] array = {10, 20, 30, 40, 50};
    System.out.print("El array es: {");
    for (int i = array.length - 1; i > 0; i--) {
        System.out.print(array[i] + " , ");
    }
    System.out.print(array[0] + " }");
}

void EjerciciosIntermedios() {
    System.out.print("Elige ejercicio (Del 11 al 15): ");
    var eleccion = Integer.parseInt(leer.nextLine());
    switch (eleccion) {
        case 11 -> Ejercicio11();
        case 12 -> Ejercicio12();
        case 13 -> Ejercicio13();
        case 14 -> Ejercicio14();
        case 15 -> Ejercicio15();
    }
}

void Ejercicio11() {
    final int[] array1 = {1, 2, 3, 4};
    final int[] array2 = {5, 6, 7, 8};
    int[] arrayresultado = new int[4];
    for (int i = 0; i < array1.length; i++) {
        arrayresultado[i] = array1[i] + array2[i];
    }
    imprimirArray(arrayresultado);

}

void Ejercicio12() {
    final int[] array = {2, 3, 5, 67, 9, 6, 4};
    int contpar = 0, contimpar = 0;
    for (int num : array) {
        if (num % 2 == 0) {
            contpar++;
        } else {
            contimpar++;
        }
    }
    System.out.println("Hay " + contpar + " números pares y " + contimpar + " números impares");
}

void Ejercicio13() {
    final int[] array = {1, 2, 3, 4, 5};
    int[] arrayrotado = copiarArray(array);

    arrayrotado[0] = array[array.length - 1];
    imprimirArray(arrayrotado);

}

void Ejercicio14() {
    final int[] array = {1, 2, 3, 4, 5, 0};
    boolean esOrdenado = true;
    for (int i = 0; i < array.length - 1 && esOrdenado; i++) {
        if (array[i] > array[i + 1]) {
            esOrdenado = false;
        }
    }
    System.out.println("esOrdenado = " + esOrdenado);
}

void Ejercicio15() {
    final int[] array = {1, 2, 3, 4, 5, 6, 7};
    int[] arraynuevo = copiarArray(array);
    arraynuevo[0] = array[array.length - 1];
    arraynuevo[arraynuevo.length - 1] = array[0];
    imprimirArray(arraynuevo);
}

void EjerciciosDificiles() {
    System.out.print("Elige ejercicio (Del 16 al 18): ");
    var eleccion = Integer.parseInt(leer.nextLine());
    switch (eleccion) {
        case 16 -> Ejercicio16();
        case 17 -> Ejercicio17();
        case 18 -> Ejercicio18();
    }
}

void Ejercicio16() {
    final int[] array1 = {1, 2, 3};
    final int[] array2 = {4, 5, 6};
    int[] arrayresultado = new int[array1.length];
    int suma = 0;
    for (int i = 0; i < array1.length; i++) {
        arrayresultado[i] = array1[i] * array2[i];
    }
    imprimirArray(arrayresultado);
    System.out.println();
    for (int num : arrayresultado) {
        suma += num;
    }
    System.out.println("La suma de los elementos del array nuevo es: " + suma);
}

void Ejercicio17() {
    final int[] array = {1, 2, 2, 3, 3, 3, 4,2,3,5,1,2,3};
ordenarArray(array);
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

void Ejercicio18() {
    final int[] array = {1, 2, 2, 3, 3, 3, 4,3,2,1,3,4,5,6,3,7};
    ordenarArray(array);
    int numMasFrecuente = array[0];
    int cont = 1;
    int maxVeces = 0;
    for (int i = 0; i < array.length - 1; i++) {
        if (array[i] == array[i + 1]) {
            cont++;
        } else {
            if (cont > maxVeces) {
                maxVeces = cont;
                numMasFrecuente = array[i];
                cont = 1;
            }
        }
    }
    if (cont > maxVeces) {
        maxVeces = cont;
        numMasFrecuente = array[array.length - 1];
    }
    System.out.println("Número más frecuente: " + numMasFrecuente);
    System.out.println("Veces que aparece: " + maxVeces);
}

void imprimirArray(int[] array) {
    System.out.print("El array es: {");
    for (int i = 0; i < array.length - 1; i++) {
        System.out.print(array[i] + " , ");
    }
    System.out.print(array[array.length - 1] + " }");
}
int[] copiarArray(int [] array){
    int [] arraynuevo= new int[array.length];
    for (int i = 0; i < array.length; i++) {
        arraynuevo[i] = array[i];
    }
    return arraynuevo;
}
void ordenarArray(int [] array){
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