PersonatgesDAO dao = new PersonatgesDAO();

void main() {
    List<Personatge> personatges;
    List<Personatge> personatgesJoc;
    Scanner scanner = new Scanner(System.in);
    System.out.print("Ejercicio?");
    int ejercicio = Integer.parseInt(scanner.nextLine());
    switch (ejercicio) {
        case 2:
            Ejercicio2();
            break;
        case 3:
            personatges = Ejercicio3();
            for (Personatge p : personatges) {
                System.out.println(p);
            }
            break;
        case 4:
            personatgesJoc = Ejercicio4("Dragon Ball");
            for (Personatge p : personatgesJoc) {
                System.out.println(p);
            }
            break;
        case 5:
            System.out.print("Nivell mínim:");
            Map<Integer, ArrayList<Personatge>> resultat = Ejercicio5(Integer.parseInt(scanner.nextLine()));

            for (Map.Entry<Integer, ArrayList<Personatge>> entry : resultat.entrySet()) {
                IO.println("Nivel: " + entry.getKey());
                for (Personatge p : entry.getValue()) {
                    IO.println("  " + p);
                }
            }
            break;
        case 6:
            Ejercicio6(1,1); break;
        case 7:
            System.out.println("Que juego quieres eliminar (ID): ");
            Ejercicio7(Integer.parseInt(scanner.nextLine()));
    }
}

public void Ejercicio2() {
    int totalSeries=0;
    totalSeries+=dao.inserir(new Personatge( "Goku", "Dragon Ball", "Saiyan", 9001));
    totalSeries+=dao.inserir(new Personatge( "Lara Croft", "Tomb Raider", "Arqueòloga", 85));
    totalSeries+=dao.inserir(new Personatge( "Mario", "Super Mario", "Lampista", 99));
    totalSeries+=dao.inserir(new Personatge( "Zelda", "The Legend of Zelda", "Princesa", 99));
    totalSeries+=dao.inserir(new Personatge( "Kratos", "God of War", "Déu de la Guerra", 100));
    totalSeries+=dao.inserir(new Personatge( "Aloy", "Horizon Zero Dawn", "Caçadora", 70));
    totalSeries+=dao.inserir(new Personatge( "Geralt of Rivia", "The Witcher", "Bruixot", 95));
    totalSeries+=dao.inserir(new Personatge( "Cloud Strife", "Final Fantasy VII", "Soldat", 88));
    totalSeries+=dao.inserir(new Personatge( "Ellie", "The Last of Us", "Supervivent", 80));
    totalSeries+=dao.inserir(new Personatge( "Master Chief", "Halo", "Spartan", 92));
    System.out.printf("S'han afegit %d series",totalSeries);
}

public List<Personatge> Ejercicio3() {
    return dao.obtenirTotes();
}

public List<Personatge> Ejercicio4(String joc) {
    return dao.obtenirPerJoc(joc);
}

public Map<Integer, ArrayList<Personatge>> Ejercicio5(int nivellMinim) {
    return dao.getDicPersonatgesByNivellMin(nivellMinim);
}
public void Ejercicio6(int id, int nivell){
    System.out.printf("Se han cambiado %d filas",dao.cambiarNivell(id,nivell));
}
public void Ejercicio7(int id){
    System.out.printf("Se han cambiado %d filas",dao.eliminarPerId(id));
}
