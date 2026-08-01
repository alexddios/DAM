//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
Random ran = new Random();
Scanner leer = new Scanner(System.in);
void main() {
    System.out.print("Que ejercicio quieres? (Del 1 al 3):");
    int eleccion = leer.nextInt();
    leer.nextLine();
    switch (eleccion) {
        case 1 ->
                Ejercicio1();
        case 2 -> {
            Ejercicio2();
        }
        case 3 -> {
            Ejercicio3();
        }
    }
}
void Ejercicio1(){
    var numrandom = ran.nextInt(1,6)+1;
    System.out.println("Ha salido un" + numrandom);
}
void Ejercicio2(){
    var numrandom=ran.nextInt(50,100)+1;
    System.out.println("Ha salido el: " + numrandom);
}
void Ejercicio3(){
var numrandom=(ran.nextInt(1,50)+1)*2;
    System.out.println(numrandom);
}