
private static int ok = 0, ko = 0;

void main() {
    IO.println("=== Proves UML -> codi (consola) ===");

    // 1) Crear nau i mòduls via fàbrica (boolean)
    Nau nau = new Nau("NAU-01", "HERMES");
    check(nau.crearMotor("M1", 1200.0, 5000.0), "crearMotor retorna true");
    check(nau.crearHabitat("H1", 800.0, 4), "crearHabitat retorna true");

    check(nau.pesTotalModuls() > 0.0, "pesTotalModuls calcula una suma > 0");
    check(nau.motorPrincipal() != null, "motorPrincipal retorna un Motor (no null)");
    check(nau.motorPrincipal() instanceof Motor, "motorPrincipal retorna realment un Motor");

    String tipusMotor = nau.motorPrincipal().nomTipus();
    check(tipusMotor != null && !tipusMotor.isBlank(), "Motor.nomTipus retorna un text no buit");

    //Nuevas pruebas
    check(!nau.crearMotor("M1", 999.0, 1111.0), "crearMotor retorna false si l'id ja existeix (duplicat M1)");
    check(!nau.crearHabitat("H1", 700.0, 2), "crearHabitat retorna false si l'id ja existeix (duplicat H1)");

    check(nau.crearMotor("M2", 1100.0, 4500.0), "crearMotor permet afegir un segon motor (id diferent)");
    check(nau.motorPrincipal() != null, "motorPrincipal segueix retornant un Motor amb diversos motors");

    check(!nau.crearMotor("   ", 100.0, 100.0), "crearMotor retorna false si l'id és blanc");
    check(!nau.crearHabitat("", 100.0, 1), "crearHabitat retorna false si l'id és buit");
    check(!nau.crearHabitat(null, 100.0, 1), "crearHabitat retorna false si l'id és null");
    check(!nau.crearMotor("M3",-90,100), "crearMotor retorna false si la massa es negativa");
    check(!nau.crearHabitat("H2",-100,1), "crearHabitat retorna false si la massa es negativa");
    check(!nau.crearMotor("M4",100,0), "crearMotor retorna false si la potencia es negativa");
    check(!nau.crearHabitat("H3",100,0), "crearHabitat retorna false si el llits es negatiu");

    // 2) Crear Planeta y missió
    Planeta mart = new Planeta("Mart");
    Missio m1 = new Missio("MIS-001", EstatMissio.PLANIFICADA, nau, mart);

    // 3) Provar Agencia: afegir / cercar / llevar
    Agencia ag = new Agencia("ESA");

    check(ag.afegirMissio(m1), "afegirMissio retorna true en afegir");
    check(!ag.afegirMissio(m1), "afegirMissio retorna false si ja hi és (duplicat)");

    Missio trobada = ag.cercarMissio("MIS-001");
    check(trobada != null, "cercarMissio troba una missió existent");

    check(ag.llevarMissio("MIS-001"), "llevarMissio elimina i retorna true");
    check(!ag.llevarMissio("MIS-001"), "llevarMissio retorna false si no existeix");
    check(ag.cercarMissio("MIS-001") == null, "després d'eliminar, cercarMissio retorna null");

    //Nuevas pruebas
    Missio m2 = new Missio("MIS-002", EstatMissio.EN_CURS, nau, mart);
    check(ag.afegirMissio(m2), "afegirMissio retorna true en afegir una missió EN_CURS");
    check(!ag.llevarMissio("MIS-002"), "llevarMissio retorna false si la missió està EN_CURS");
    check(ag.cercarMissio("MIS-002") != null, "la missió EN_CURS no s'ha eliminat (encara existeix)");

    IO.println("\n=== RESULTAT ===");
    IO.println("PASS: " + ok);
    IO.println("FAIL: " + ko);
}

private static void check(boolean cond, String msg) {
    if (cond) {
        ok++;
        IO.println("[PASS] " + msg);
    } else {
        ko++;
        IO.println("[FAIL] " + msg);
    }
}
