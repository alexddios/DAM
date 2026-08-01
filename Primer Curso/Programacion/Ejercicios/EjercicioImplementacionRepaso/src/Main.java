public class Main {
    private static int ok = 0, ko = 0;

    public static void main(String[] args) {
        System.out.println("=== Proves UML -> codi (consola) ===");

        provarAcademiaAfegirPiano();
        provarAcademiaAfegirGuitarra();
        provarAcademiaAfegirCurs();
        provarAcademiaAfegirProfesor();
        provarAcademiaGetInstrument();
        provarAcademiaGetProfesorIEliminar();
        provarAlumne();
        provarProfesor();
        provarCursMatricular();
        provarCursCalcularNotaFinal();

        System.out.println("\n=== RESULTAT ===");
        System.out.println("PASS: " + ok);
        System.out.println("FAIL: " + ko);
    }



    private static void provarAcademiaAfegirPiano() {
        System.out.println("\n--- Tests Academia.afegirPiano ---");
        Academia a = new Academia("A1", "Academia Prova");

        check(a.afegirPiano("P1", "Yamaha", 25.0, 88, true), "Afegir piano válido");
        check(!a.afegirPiano(null, "Yamaha2", 20.0, 88, false), "No permite codi null");
        check(!a.afegirPiano("", "Yamaha2", 20.0, 88, false), "No permite codi vacío");
        check(!a.afegirPiano("P2", null, 20.0, 88, false), "No permite nom null");
        check(!a.afegirPiano("P2", "", 20.0, 88, false), "No permite nom vacío");
        check(!a.afegirPiano("P2", "Casio", 0.0, 88, false), "No permite preuLloguer = 0");
        check(!a.afegirPiano("P2", "Casio", -5.0, 88, false), "No permite preuLloguer negativo");
        check(!a.afegirPiano("P2", "Casio", 20.0, 0, false), "No permite numTecles = 0");
        check(!a.afegirPiano("P2", "Casio", 20.0, -1, false), "No permite numTecles negativo");
        check(!a.afegirPiano("P1", "Duplicat", 20.0, 61, false), "No permite codi de instrumento duplicado");
    }

    private static void provarAcademiaAfegirGuitarra() {
        System.out.println("\n--- Tests Academia.afegirGuitarra ---");
        Academia a = new Academia("A1", "Academia Prova");

        check(a.afegirGuitarra("G1", "Fender", 18.0, "niló"), "Afegir guitarra válida");
        check(!a.afegirGuitarra(null, "Gibson", 18.0, "acer"), "No permite codi null");
        check(!a.afegirGuitarra("", "Gibson", 18.0, "acer"), "No permite codi vacío");
        check(!a.afegirGuitarra("G2", null, 18.0, "acer"), "No permite nom null");
        check(!a.afegirGuitarra("G2", "", 18.0, "acer"), "No permite nom vacío");
        check(!a.afegirGuitarra("G2", "Gibson", 0.0, "acer"), "No permite preuLloguer = 0");
        check(!a.afegirGuitarra("G2", "Gibson", -2.0, "acer"), "No permite preuLloguer negativo");
        check(!a.afegirGuitarra("G2", "Gibson", 18.0, null), "No permite tipusCordes null");
        check(!a.afegirGuitarra("G2", "Gibson", 18.0, ""), "No permite tipusCordes vacío");
        check(!a.afegirGuitarra("G1", "Duplicada", 20.0, "acer"), "No permite codi de instrumento duplicado");
    }

    private static void provarAcademiaAfegirCurs() {
        System.out.println("\n--- Tests Academia.afegirCurs ---");
        Academia a = new Academia("A1", "Academia Prova");

        check(a.afegirCurs("C1", "Piano Inicial", Nivell.INICIAL), "Afegir curs válido");
        check(!a.afegirCurs(null, "Curs 2", Nivell.INTERMEDI), "No permite codi null");
        check(!a.afegirCurs("", "Curs 2", Nivell.INTERMEDI), "No permite codi vacío");
        check(!a.afegirCurs("C2", null, Nivell.INTERMEDI), "No permite nom null");
        check(!a.afegirCurs("C2", "", Nivell.INTERMEDI), "No permite nom vacío");
        check(!a.afegirCurs("C1", "Curs duplicat", Nivell.AVANÇAT), "No permite codi de curs duplicado");

        check(!a.afegirCurs("C3", "Curs nivell null", null), "Actualmente  no permite nivell null");
    }

    private static void provarAcademiaAfegirProfesor() {
        System.out.println("\n--- Tests Academia.afegirProfesor ---");
        Academia a = new Academia("A1", "Academia Prova");

        check(a.afegirProfesor("Joan", "111A", "Piano"), "Afegir profesor válido");
        check(!a.afegirProfesor(null, "222B", "Guitarra"), "No permite nom null");
        check(!a.afegirProfesor("", "222B", "Guitarra"), "No permite nom vacío");
        check(!a.afegirProfesor("Maria", null, "Guitarra"), "No permite dni null");
        check(!a.afegirProfesor("Maria", "", "Guitarra"), "No permite dni vacío");
        check(!a.afegirProfesor("Maria", "222B", null), "No permite especialitat null");
        check(!a.afegirProfesor("Maria", "222B", ""), "No permite especialitat vacía");
        check(!a.afegirProfesor("Pere", "111A", "Violí"), "No permite dni duplicado");
    }

    private static void provarAcademiaGetInstrument() {
        System.out.println("\n--- Tests Academia.getInstrument ---");
        Academia a = new Academia("A1", "Academia Prova");
        a.afegirPiano("P1", "Yamaha", 25.0, 88, true);
        a.afegirGuitarra("G1", "Fender", 18.0, "niló");

        check(a.getInstrument(0) != null, "Obtiene instrumento en posición 0");
        check("P1".equals(a.getInstrument(0).getCodi()), "El instrumento en posición 0 es el esperado");
        check(a.getInstrument(1) != null, "Obtiene instrumento en posición 1");
        check("G1".equals(a.getInstrument(1).getCodi()), "El instrumento en posición 1 es el esperado");
        check(a.getInstrument(-1) == null, "Devuelve null con posición negativa");
        check(a.getInstrument(2) == null, "Devuelve null con posición fuera de rango");
    }

    private static void provarAcademiaGetProfesorIEliminar() {
        System.out.println("\n--- Tests Academia.getProfesor / eliminarProfessor ---");
        Academia a = new Academia("A1", "Academia Prova");
        a.afegirProfesor("Joan", "111A", "Piano");

        check(a.getProfesor("111A") != null, "Obtiene profesor existente");
        check("111A".equals(a.getProfesor("111A").getDni()), "El DNI del profesor obtenido es correcto");
        check(a.getProfesor("999Z") == null, "Devuelve null si el profesor no existe");

        check(a.eliminarProfessor("111A"), "Elimina profesor existente");
        check(a.getProfesor("111A") == null, "Profesor eliminado deja de existir");
        check(!a.eliminarProfessor("111A"), "No elimina dos veces el mismo profesor");
        check(!a.eliminarProfessor("999Z"), "No elimina un profesor inexistente");
    }

    private static void provarAlumne() {
        System.out.println("\n--- Tests Alumne ---");
        Alumne alumne = new Alumne("Laia", "333C", 15);

        check("Laia".equals(alumne.getNom()), "Getter getNom correcto");
        check("333C".equals(alumne.getDni()), "Getter getDni correcto");
        check(alumne.getEdat() == 15, "Getter getEdat correcto");
    }

    private static void provarProfesor() {
        System.out.println("\n--- Tests Profesor ---");
        Profesor profesor = new Profesor("Marta", "444D", "Cant");

        check("444D".equals(profesor.getDni()), "Getter getDni de Profesor correcto");
    }

    private static void provarCursMatricular() {
        System.out.println("\n--- Tests Curs.matricular ---");

        Curs cursInicial = new Curs("C1", "Iniciació", Nivell.INICIAL);
        Alumne alumne1 = new Alumne("Anna", "A1", 10);
        Alumne alumneDuplicat = new Alumne("Anna 2", "A1", 12);

        check(cursInicial.matricular(alumne1), "Matricula alumno válido en nivel inicial");
        check(!cursInicial.matricular(alumneDuplicat), "No permite matricular alumno con dni duplicado");
        check(!cursInicial.matricular(null), "No permite matricular null");

        Curs cursIntermedi = new Curs("C2", "Intermedi", Nivell.INTERMEDI);
        Alumne alumne2 = new Alumne("Pau", "A2", 8);
        check(cursIntermedi.matricular(alumne2), "Matricula alumno válido en nivel intermedio sin restricción de edad");

        Curs cursAvancat = new Curs("C3", "Avançat", Nivell.AVANÇAT);
        Alumne menor = new Alumne("Nil", "A3", 15);
        Alumne major = new Alumne("Clara", "A4", 16);

        check(!cursAvancat.matricular(menor), "No permite matricular menor de 16 en nivel avanzado");
        check(cursAvancat.matricular(major), "Permite matricular alumno de 16 o más en nivel avanzado");

        Curs cursAmbNivellNull = new Curs("C4", "Sense nivell", null);
        Alumne alumne3 = new Alumne("Oriol", "A5", 14);
        check(cursAmbNivellNull.matricular(alumne3), "Actualmente no permite matricular si nivell es null");
    }

    private static void provarCursCalcularNotaFinal() {
        System.out.println("\n--- Tests Curs.calcularNotaFinal ---");
        Curs curs = new Curs("C1", "Piano", Nivell.INICIAL);

        check(curs.calcularNotaFinal() == 0.0, "La nota final actual es 0.0");
    }

    private static void check(boolean cond, String msg) {
        if (cond) {
            ok++;
            System.out.println("[PASS] " + msg);
        } else {
            ko++;
            System.out.println("[FAIL] " + msg);
        }
    }
}

