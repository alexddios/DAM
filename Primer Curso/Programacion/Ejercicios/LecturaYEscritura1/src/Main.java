//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import LecturaEscritura.FI;
import LecturaEscritura.FO;

import java.util.Scanner;

Scanner scanner = new Scanner(System.in);

void main() {
    System.out.println("Ejercicio?");
    int ejercicio = Integer.parseInt(scanner.nextLine());
    switch (ejercicio) {
        case 1: Ejercicio1();
        case 2:Ejercicio2();
        case 3: Ejercicio3();
        case 4:Ejercicio4();
        case 5:Ejercicio5();
        case 6:Ejercicio6();
    }
}

public void Ejercicio1() {
    FI fi= new FI("frases.txt");
    fi.obrir();
    String linea = fi.llegirLinia();
    int cont=0;
    while (linea != null){
        cont++;
        linea = fi.llegirLinia();
    }
    System.out.printf("Número de lineas: %d",cont);
    fi.tancar();
}
public void Ejercicio2() {
    FI fi= new FI("frases.txt");
    fi.obrir();
    String linea = fi.llegirLinia();
    int cont=0;
    while (linea != null){
        if (linea.contains("Java")){
            cont++;
        }
        linea=fi.llegirLinia();
    }
    System.out.printf("Número de lineas que contienen la palabra 'Java': %d",cont);
    fi.tancar();
}
public void Ejercicio3(){
    FO fo = new FO("sortida.txt");
    System.out.println("Número de lineas");
    int numlineas = Integer.parseInt(scanner.nextLine());
    fo.obrir(false);
    for (int i = 1; i <=numlineas ; i++) {
        fo.gravarLinia(String.format("Linea %d",i));
    }
    fo.tancar();
}
public void Ejercicio4(){
    FI fi = new FI("frases.txt");
    FO fo = new FO("copia.txt");
    fi.obrir();
    fo.obrir(false);
    String linea = fi.llegirLinia();
    while (linea != null){
        fo.gravarLinia(linea);
        linea=fi.llegirLinia();
    }
    fi.tancar();
    fo.tancar();
}
public void Ejercicio5(){
    FI fi = new FI("frases.txt");
    FO fo = new FO("majuscules.txt");

    fi.obrir();
    fo.obrir(false);

    String linea = fi.llegirLinia();
    while (linea != null){
        fo.gravarLinia(linea.toUpperCase());
        linea = fi.llegirLinia();
    }
    fi.tancar();
    fo.tancar();
}
public void Ejercicio6(){
    FO fo = new FO("alumnes.csv");
    Alumne alum1 = new Alumne("Alex",22,"DAM");
    Alumne alum2 = new Alumne("Pepito",19,"DAW");
    Alumne alum3 = new Alumne("Muriqi",31,"Mallorca");
    fo.obrir(false);
    fo.gravarLinia("Nom,Edat,Grup");
    fo.gravarLinia(alum1.dades());
    fo.gravarLinia(alum2.dades());
    fo.gravarLinia(alum3.dades());
    fo.tancar();
}