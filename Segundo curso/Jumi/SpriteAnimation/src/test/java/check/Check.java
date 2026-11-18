package check;

/** Una mini librería de pruebas, para no depender de JUnit: comprueba y cuenta. */
public final class Check {

    private static int passed;
    private static int failed;

    private Check() {}

    public static void equal(String what, Object expected, Object actual) {
        that(what + " (esperado " + expected + ", obtenido " + actual + ")", expected.equals(actual));
    }

    public static void that(String what, boolean ok) {
        if (ok) {
            passed++;
            System.out.println("  OK    " + what);
        } else {
            failed++;
            System.out.println("  FALLA " + what);
        }
    }

    /** Escribe el resumen y termina con código 1 si algo ha fallado (así lo ve el script). */
    public static void finish(String suite) {
        System.out.println(suite + ": " + passed + " correctas, " + failed + " fallidas");
        if (failed > 0) System.exit(1);
    }
}
