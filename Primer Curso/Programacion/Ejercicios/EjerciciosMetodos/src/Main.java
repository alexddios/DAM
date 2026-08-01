
Scanner leer = new Scanner(System.in);
void main() {
    System.out.print("Elige la dificultad (Fácil(F)/Intermedio(I) : ");
    var dificultad= leer.nextLine();
    System.out.print("Elige ejercicio (Del 1 al 12 en fácil y del 13 al 15 en Intermedio): ");
    var ejercicio= Integer.parseInt(leer.nextLine());
    switch(dificultad){
        case "F"->EjerciciosFaciles(ejercicio);
        case "I"-> EjerciciosIntermedios(ejercicio);
    }
}
void EjerciciosFaciles(int ejercicio){
switch (ejercicio){
    case 1->Faciles.Ejercicio1();
    case 2->Faciles.Ejercicio2();
    case 3->Faciles.Ejercicio3();
    case 4->Faciles.Ejercicio4();
    case 5->Faciles.Ejercicio5();
    case 6->Faciles.Ejercicio6();
    case 7->Faciles.Ejercicio7();
    case 8->Faciles.Ejercicio8();
    case 9->Faciles.Ejercicio9();
    case 10->Faciles.Ejercicio10();
    case 11->Faciles.Ejercicio11();
    case 12->Faciles.Ejercicio12();
}
}
void EjerciciosIntermedios(int ejercicio){
 switch(ejercicio){
     case 13->Intermedios.Ejercicio13();
     case 14->Intermedios.Ejercicio14();
     case 15->Intermedios.Ejercicio15();
 }
}

