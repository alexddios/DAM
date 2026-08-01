//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
Scanner leer = new Scanner(System.in);
void main() {
    System.out.print("Que ejercicio quieres? (Del 1 al 6):");
    int eleccion = leer.nextInt();
    leer.nextLine();
    switch (eleccion) {
        case 1 -> Ejercicio1();
        case 2 -> {
            Ejercicio2();
        }
        case 3 -> {
            Ejercicio3();
        }
        case 4 -> {
            Ejercicio4();
        }
        case 5 -> {
            Ejercicio5();

        }
        case 6->{
            Ejercicio6();
        }
    }
}
void Ejercicio1(){
    for (int i=1;i<=10;i++){
        for(int j=1;j<11;j++){
            System.out.print(i+ "x" + j + " = ");
            System.out.print(i*j + " ");
        }
        System.out.println();
    }
}
void Ejercicio2(){
    System.out.print("Tamaño del cuadrado? ");
    var tamanio=leer.nextInt();
    leer.nextLine();
    for (int i=1;i<=tamanio;i++){
        for (int j=1;j<=tamanio;j++){
            System.out.print("* ");
        }
        System.out.println();
    }
}
void Ejercicio3(){
    int n = 5;
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= i; j++) {
            System.out.print("*");
        }
        System.out.println();
    }
}
void Ejercicio4(){
    System.out.print("Introduce la base: ");
    var base = leer.nextInt();
    System.out.println("Introduce la altura: ");
    var altura=leer.nextInt();
    for (int i=1;i<=altura;i++){
        for (int j=1;j<=base;j++){
            System.out.print("* ");
        }
        System.out.println();
    }
}
void Ejercicio5(){
    System.out.print("Introduce el valor de n: ");
    int n = leer.nextInt();
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= i; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
}
void Ejercicio6(){
    int hora=0,minuto=0,segundo=0;
    String horaform;
    String minutoform;
    String segundoform;
    for (int i=0; hora<24; i++){
        try {
            // Se lee mucho más claro: "Duerme 1 segundo"
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        horaform = String.format("%02d",hora);
        minutoform= String.format("%02d",minuto);
        segundoform= String.format("%02d",segundo);
        System.out.println(horaform+ ":" + minutoform + ":" + segundoform);
        segundo++;
        if(segundo ==60){
            segundo=0;
            minuto++;
            if (minuto==60){
                minuto=0;
                hora++;
            }
        }
    }
}
