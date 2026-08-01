package ApartadoA;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTrailTest {
    private PuntAvituallament punt;
    private AppTrail app;

    @BeforeEach
    public void setUp() {
        punt = new PuntAvituallament("Punt de pruebas", true);
        app = new AppTrail("Trail de pruebas");
    }

    @Test
    public void testCrearRutas() {
        boolean ruta1 = app.crearRuta("Ruta 1", 350.5, Dificultat.FACIL, punt);
        boolean ruta2 = app.crearRuta("Ruta 2", 1200.0, Dificultat.MODERADA, punt);

        assertTrue(ruta1, "La ruta 1 debería crearse correctamente");
        assertTrue(ruta2, "La ruta 2 debería crearse correctamente");
    }

    @Test
    public void testComptarRutesPerDificultat() {
        app.crearRuta("Ruta 1", 350.5, Dificultat.FACIL, punt);
        app.crearRuta("Ruta 2", 1200.0, Dificultat.MODERADA, punt);

        assertEquals(1, app.comptarRutesPerDificultat(Dificultat.FACIL), "Debe haber 1 ruta fácil");
        assertEquals(1, app.comptarRutesPerDificultat(Dificultat.MODERADA), "Debe haber 1 ruta moderada");
    }

    @Test
    public void testTeRutaAmbDesnivellSuperiorA() {
        app.crearRuta("Ruta 1", 350.5, Dificultat.FACIL, punt);
        app.crearRuta("Ruta 2", 1200.0, Dificultat.MODERADA, punt);

        assertTrue(app.teRutaAmbDesnivellSuperiorA(350.5), "Debe existir ruta con desnivel superior a 350.5");
        assertFalse(app.teRutaAmbDesnivellSuperiorA(1300.0), "No debe existir ruta con desnivel superior a 1300.0");
    }

    @Test
    public void testCrearTramsEnRuta() {
        Ruta ruta = new Ruta("Ruta trams", 500.0, Dificultat.ALTA, punt);

        boolean tram1 = ruta.crearTram(1, 2.5, TipusTerreny.ROCA, Afluencia.ALTA);
        boolean tram2 = ruta.crearTram(2, 1.2, TipusTerreny.PISTA, Afluencia.MITJANA);
        boolean tram3 = ruta.crearTram(3, 3.8, TipusTerreny.PISTA, Afluencia.BAIXA);

        assertTrue(tram1, "El tramo 1 debería crearse correctamente");
        assertTrue(tram2, "El tramo 2 debería crearse correctamente");
        assertTrue(tram3, "El tramo 3 debería crearse correctamente");
    }

    @Test
    public void testComptarTramsPerTerreny() {
        Ruta ruta = new Ruta("Ruta trams", 500.0, Dificultat.ALTA, punt);

        ruta.crearTram(1, 2.5, TipusTerreny.ROCA, Afluencia.ALTA);
        ruta.crearTram(2, 1.2, TipusTerreny.PISTA, Afluencia.MITJANA);
        ruta.crearTram(3, 3.8, TipusTerreny.PISTA, Afluencia.BAIXA);

        assertEquals(1, ruta.comptarTramsPerTerreny(TipusTerreny.ROCA), "Debe haber 1 tramo de roca");
        assertEquals(2, ruta.comptarTramsPerTerreny(TipusTerreny.PISTA), "Debe haber 2 tramos de pista");
        assertEquals(0, ruta.comptarTramsPerTerreny(TipusTerreny.CORRIOL), "Debe haber 0 tramos de camino");
    }
}
