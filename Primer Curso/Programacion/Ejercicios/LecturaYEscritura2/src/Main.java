import Ficheros.FI;
import Ficheros.FO;
import Ficheros.GF;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
Scanner scanner = new Scanner(System.in);

void main() {
    System.out.println("Ejercicio?");
    int ejercicio = Integer.parseInt(scanner.nextLine());
    switch (ejercicio) {
        case 1:
            Ejercicio1();
            break;
        case 2:
            Ejercicio2();
            break;
        case 3:
            Ejercicio3();
            break;
        case 4:
            Ejercicio4();
            break;
        case 5:
            Ejercicio5();
            break;
        case 6:
            Ejercicio6();
            break;
        case 7:
            Ejercicio7();
            break;
    }
}
    public void Ejercicio1(){
        Videojoc videojoc = new Videojoc("Fortnite","Battle Royale",100);
        String linea = "Minecraft,Sandbox,250";
        System.out.println(videojoc.lineaCSV());
        System.out.println(Videojoc.objecteCSV(linea));
    }

    public void Ejercicio2(){
        GF.crearDirectorio("dades/");
        GF.crearDirectorio("dades/entrada/");
        GF.crearDirectorio("dades/sortida/");
    }

    public void Ejercicio3(){
        List<Videojoc> videojocs = new ArrayList<>();
        videojocs.add(new Videojoc("Fortnite","Battle Royale",100));
        videojocs.add(new Videojoc("Minecraft","Sandbox",250));
        videojocs.add(new Videojoc("The Legend of Zelda","Aventura",60));
        videojocs.add(new Videojoc("Elden Ring","RPG",120));
        videojocs.add(new Videojoc("League of Legends","MOBA",500));
        FO fo = new FO("dades/entrada/videojocs.csv");
        fo.obrir(false);
        fo.gravarLinia("Titol,Genere,Hores");
        for (Videojoc videojoc : videojocs){
            fo.gravarLinia(String.format("%s,%s,%d",videojoc.getTitol(),videojoc.getGenere(),videojoc.getHores()));
        }
        fo.tancar();
    }

    public void Ejercicio4(){
        FI fi = new FI("dades/entrada/videojocs.csv");
        List<Videojoc> videojocs = new ArrayList<>();
        fi.obrir();
        fi.llegirLinia();
        String linea = fi.llegirLinia();
        while (linea != null){
            videojocs.add(Videojoc.objecteCSV(linea));
            linea=fi.llegirLinia();
        }
        int contador = videojocs.size();
        int sumaHores = 0;
        for (Videojoc v : videojocs){
            sumaHores+= v.getHores();
        }
        System.out.printf("""
                Nombre total de videojocs llegits: %d
                Suma total d’hores de joc: %d
                """,contador,sumaHores);
        fi.tancar();
    }

    public void Ejercicio5(){
        FO fo = new FO("dades/sortida/videojocs_llargs.csv");
        FI fi = new FI("dades/entrada/videojocs.csv");
        fo.obrir(false);
        fi.obrir();
        List<Videojoc> videojocs = new ArrayList<>();
        fi.llegirLinia();
        String linea = fi.llegirLinia();
        while (linea != null){
            videojocs.add(Videojoc.objecteCSV(linea));
            linea = fi.llegirLinia();
        }
        fo.gravarLinia("Posicio,Titol,Hores,Genere");
        int cont=0;
        for (Videojoc videojoc : videojocs) {
            if (videojoc.getHores() >= 50) {
                cont++;
                fo.gravarLinia(cont + "," + videojoc.lineaCSV());
            }
        }
        fo.tancar();
        fi.tancar();
    }

    public void Ejercicio6(){
        FI fi = new FI("dades/entrada/videojocs.csv");
        FO fo = new FO("dades/sortida/resum.txt");
        int contTotalVideojocs = 0;
        int contVideojocs50Hores = 0;
        int totalHores = 0;
        List<Videojoc> videojocs = new ArrayList<>();
        fi.obrir();
        fo.obrir(false);

        fi.llegirLinia();
        String linea = fi.llegirLinia();
        while (linea != null){
            videojocs.add(Videojoc.objecteCSV(linea));
            linea = fi.llegirLinia();
        }
        for (Videojoc v : videojocs){
            contTotalVideojocs++;
            if (v.getHores()>=50){
                contVideojocs50Hores++;
            }
            totalHores += v.getHores();
        }
        fi.tancar();
        fo.gravarLinia(String.format("""
                Total de videojocs: %d
                Videojocs amb 50 hores o més: %d
                Total d’hores de joc: %d
                """,contTotalVideojocs,contVideojocs50Hores,totalHores));
        fo.tancar();
    }

    public void Ejercicio7(){
        GF.borrar("dades/sortida/resum.txt");
        GF.borrar("dades/sortida/videojocs_llargs.csv");
        GF.borrar("dades/entrada/videojocs.csv");
        GF.borrar("dades/sortida");
        GF.borrar("dades/entrada");
        GF.borrar("dades");
    }

