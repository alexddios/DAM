package ApartadoA;

public class Main {
    public static void main(String[] args) {
        // Crear un punto de avituallamiento de prueba
        PuntAvituallament punt = new PuntAvituallament("Punt de pruebas", true);

        // Crear la aplicación
        AppTrail app = new AppTrail("Trail de pruebas");

        // Crear rutas de prueba
        boolean ruta1 = app.crearRuta("Ruta 1", 350.5, Dificultat.FACIL, punt);
        boolean ruta2 = app.crearRuta("Ruta 2", 1200.0, Dificultat.MODERADA, punt);

        System.out.println("Ruta 1 creada: " + ruta1);
        System.out.println("Ruta 2 creada: " + ruta2);

        // Pruebas de conteo por dificultad
        System.out.println("Rutas fáciles: " + app.comptarRutesPerDificultat(Dificultat.FACIL));
        System.out.println("Rutas medias: " + app.comptarRutesPerDificultat(Dificultat.MODERADA));

        // Prueba de búsqueda por desnivel
        System.out.println("Existe ruta con desnivel 350.5: " + app.teRutaAmbDesnivellSuperiorA(350.5));
        System.out.println("Existe ruta con desnivel 999.0: " + app.teRutaAmbDesnivellSuperiorA(999.0));

        // Crear una ruta manualmente para probar tramos
        Ruta ruta = new Ruta("Ruta trams", 500.0, Dificultat.ALTA, punt);

        boolean tram1 = ruta.crearTram(1, 2.5, TipusTerreny.ROCA, Afluencia.ALTA);
        boolean tram2 = ruta.crearTram(2, 1.2, TipusTerreny.PISTA, Afluencia.MITJANA);
        boolean tram3 = ruta.crearTram(3, 3.8, TipusTerreny.PISTA, Afluencia.BAIXA);

        System.out.println("Tram 1 creado: " + tram1);
        System.out.println("Tram 2 creado: " + tram2);
        System.out.println("Tram 3 creado: " + tram3);

        // Conteo por terreno
        System.out.println("Tramos de montaña: " + ruta.comptarTramsPerTerreny(TipusTerreny.ROCA));
        System.out.println("Tramos de pista: " + ruta.comptarTramsPerTerreny(TipusTerreny.PISTA));
        System.out.println("Tramos de camino: " + ruta.comptarTramsPerTerreny(TipusTerreny.CORRIOL));
    }
}
