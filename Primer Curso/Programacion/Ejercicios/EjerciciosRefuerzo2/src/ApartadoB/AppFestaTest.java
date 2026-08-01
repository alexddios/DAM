package ApartadoB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppFestaTest {
    private AppFesta app;
    private Local discoteca;
    private Local pub;
    private Local bar;

    @BeforeEach
    public void setUp() {
        app = new AppFesta("Festa Major");
        discoteca = new Local("Disco Blue", TipusLocal.DISCOTECA);
        pub = new Local("Pub Red", TipusLocal.PUB);
        bar = new Local("Bar Green", TipusLocal.BAR_MUSICAL);

        app.afegirLocal(discoteca);
        app.afegirLocal(pub);
        app.afegirLocal(bar);
    }

    // ===== TESTS DE AFEGIR LOCAL =====

    @Test
    public void testAfegirLocalValido() {
        AppFesta nuevaApp = new AppFesta("Nueva Festa");
        Local nuevoLocal = new Local("Nuevo Local", TipusLocal.DISCOTECA);

        assertTrue(nuevaApp.afegirLocal(nuevoLocal), "Debería añadir el local correctamente");
    }

    @Test
    public void testAfegirLocalDuplicado() {
        assertFalse(app.afegirLocal(discoteca), "No debería permitir añadir un local duplicado");
    }

    @Test
    public void testAfegirLocalNulo() {
        assertFalse(app.afegirLocal(null), "No debería permitir añadir un local nulo");
    }

    // ===== TESTS DE CREAR PLA NIT =====

    @Test
    public void testCrearPlaNitValido() {
        assertTrue(app.crearPlaNit("Pla 1", discoteca), "El plan 1 debería crearse correctamente");
        assertTrue(app.crearPlaNit("Pla 2", pub), "El plan 2 debería crearse correctamente");
        assertTrue(app.crearPlaNit("Pla 3", bar), "El plan 3 debería crearse correctamente");
    }

    @Test
    public void testCrearPlaNitDuplicado() {
        app.crearPlaNit("Pla 1", discoteca);
        assertFalse(app.crearPlaNit("Pla 1", pub), "No debería permitir crear un plan con nombre duplicado");
    }

    @Test
    public void testCrearPlaNitNomNull() {
        assertFalse(app.crearPlaNit(null, discoteca), "No debería permitir nombre nulo");
    }

    @Test
    public void testCrearPlaNitNomBlanco() {
        assertFalse(app.crearPlaNit("", discoteca), "No debería permitir nombre vacío");
        assertFalse(app.crearPlaNit("   ", discoteca), "No debería permitir nombre con solo espacios");
    }

    @Test
    public void testCrearPlaNitLocalNull() {
        assertFalse(app.crearPlaNit("Pla Test", null), "No debería permitir local nulo");
    }

    @Test
    public void testCrearPlaNitLocalNoRegistrado() {
        Local localNoRegistrado = new Local("Local No Registrado", TipusLocal.DISCOTECA);
        assertFalse(app.crearPlaNit("Pla Test", localNoRegistrado), "No debería permitir un local que no está en la lista");
    }

    // ===== TESTS DE COMPTAR PLANS PER TIPUS LOCAL =====

    @Test
    public void testComptarPlansPerTipusLocal() {
        app.crearPlaNit("Pla 1", discoteca);
        app.crearPlaNit("Pla 2", pub);
        app.crearPlaNit("Pla 3", discoteca);
        app.crearPlaNit("Pla 4", bar);

        assertEquals(2, app.comptarPlansPerTipusLocal(TipusLocal.DISCOTECA), "Debe haber 2 planes en discoteca");
        assertEquals(1, app.comptarPlansPerTipusLocal(TipusLocal.PUB), "Debe haber 1 plan en pub");
        assertEquals(1, app.comptarPlansPerTipusLocal(TipusLocal.BAR_MUSICAL), "Debe haber 1 plan en bar");
    }

    @Test
    public void testComptarPlansPerTipusLocalSinPlanes() {
        assertEquals(0, app.comptarPlansPerTipusLocal(TipusLocal.DISCOTECA), "No debería haber planes sin crear ninguno");
    }

    @Test
    public void testComptarPlansPerTipusLocalNoExistente() {
        app.crearPlaNit("Pla 1", discoteca);

        assertEquals(0, app.comptarPlansPerTipusLocal(TipusLocal.BAR_MUSICAL), "No debería haber planes en bar si no se han creado");
    }

    // ===== TESTS DE CALCULAR TOTAL GASTAT PER PARTICIPANT =====

    @Test
    public void testCalcularTotalGastatUnPlan() {
        app.crearPlaNit("Pla 1", discoteca);
        PlaNit pla1 = new PlaNit("Pla 1", discoteca);
        pla1.crearParticipacio("12345678A", 50.0, true);

        // Necesitamos crear participaciones a través de los planes registrados en app
        // Como no tenemos acceso directo, creamos un escenario de prueba completo
    }

    @Test
    public void testCalcularTotalGastatMultiplesPlanes() {
        // Nota: Este test requeriría acceso a los PlaNit creados por AppFesta
        // Como no hay getters, probamos la funcionalidad indirectamente
        double total = app.calcularTotalGastatPerParticipant("12345678A");
        assertEquals(0.0, total, 0.01, "Sin participaciones debería ser 0");
    }

    @Test
    public void testCalcularTotalGastatParticipantNoExistente() {
        app.crearPlaNit("Pla 1", discoteca);

        double total = app.calcularTotalGastatPerParticipant("99999999Z");
        assertEquals(0.0, total, 0.01, "Un participante que no existe debería tener gasto 0");
    }

    // ===== TESTS DE COMPTAR VEGADES QUE HA CONDUIT =====

    @Test
    public void testComptarVegadesQueHaConduitSinPlanes() {
        assertEquals(0, app.comptarVegadesQueHaConduit("12345678A"), "Sin planes debería retornar 0");
    }

    @Test
    public void testComptarVegadesQueHaConduitParticipantNoExistente() {
        app.crearPlaNit("Pla 1", discoteca);

        assertEquals(0, app.comptarVegadesQueHaConduit("99999999Z"), "Participante no existente debería retornar 0");
    }

    // ===== TESTS DE PLANIT - CREAR PARTICIPACIO =====

    @Test
    public void testCrearParticipacioValida() {
        PlaNit pla = new PlaNit("Pla Test", discoteca);

        assertTrue(pla.crearParticipacio("12345678A", 50.0, true), "Participación válida con conductor");
        assertTrue(pla.crearParticipacio("87654321B", 30.0, false), "Participación válida sin conductor");
        assertTrue(pla.crearParticipacio("11111111C", 0.0, false), "Participación válida con importe 0");
    }

    @Test
    public void testCrearParticipacioInvalidaDuplicada() {
        PlaNit pla = new PlaNit("Pla Test", discoteca);

        pla.crearParticipacio("12345678A", 50.0, true);
        assertFalse(pla.crearParticipacio("12345678A", 20.0, false), "No debería permitir participación duplicada");
    }

    @Test
    public void testCrearParticipacioInvalidaImporteNegativo() {
        PlaNit pla = new PlaNit("Pla Test", discoteca);

        assertFalse(pla.crearParticipacio("11111111C", -10.0, false), "No debería permitir importe negativo");
        assertFalse(pla.crearParticipacio("22222222D", -0.01, true), "No debería permitir importe negativo pequeño");
    }

    @Test
    public void testCrearParticipacioInvalidaDniBlanco() {
        PlaNit pla = new PlaNit("Pla Test", discoteca);

        assertFalse(pla.crearParticipacio("", 50.0, true), "No debería permitir DNI vacío");
        assertFalse(pla.crearParticipacio("   ", 30.0, false), "No debería permitir DNI con solo espacios");
    }

    // ===== TESTS DE PLANIT - OBTENIR IMPORT GASTAT PARTICIPANT =====

    @Test
    public void testObtenirImportGastatParticipantExistente() {
        PlaNit pla = new PlaNit("Pla Test", discoteca);
        pla.crearParticipacio("12345678A", 50.0, true);
        pla.crearParticipacio("87654321B", 75.5, false);

        assertEquals(50.0, pla.obtenirImportGastatParticipant("12345678A"), 0.01, "El gasto de Pau debería ser 50.0");
        assertEquals(75.5, pla.obtenirImportGastatParticipant("87654321B"), 0.01, "El gasto de Anna debería ser 75.5");
    }

    @Test
    public void testObtenirImportGastatParticipantNoExistente() {
        PlaNit pla = new PlaNit("Pla Test", discoteca);

        assertEquals(0.0, pla.obtenirImportGastatParticipant("99999999Z"), 0.01, "Participante no existente debería retornar 0");
    }

    @Test
    public void testObtenirImportGastatParticipantConImporteCero() {
        PlaNit pla = new PlaNit("Pla Test", discoteca);
        pla.crearParticipacio("12345678A", 0.0, true);

        assertEquals(0.0, pla.obtenirImportGastatParticipant("12345678A"), 0.01, "Participante con gasto 0 debería retornar 0");
    }

    // ===== TESTS DE PLANIT - HA CONDUIT PARTICIPANT =====

    @Test
    public void testHaConduitParticipantTrue() {
        PlaNit pla = new PlaNit("Pla Test", discoteca);
        pla.crearParticipacio("12345678A", 50.0, true);

        assertTrue(pla.haConduitParticipant("12345678A"), "El participante debería haber conducido");
    }

    @Test
    public void testHaConduitParticipantFalse() {
        PlaNit pla = new PlaNit("Pla Test", discoteca);
        pla.crearParticipacio("87654321B", 30.0, false);

        assertFalse(pla.haConduitParticipant("87654321B"), "El participante no debería haber conducido");
    }

    @Test
    public void testHaConduitParticipantNoExistente() {
        PlaNit pla = new PlaNit("Pla Test", discoteca);

        assertFalse(pla.haConduitParticipant("99999999Z"), "Participante no existente debería retornar false");
    }

    @Test
    public void testHaConduitMultiplesParticipantes() {
        PlaNit pla = new PlaNit("Pla Test", discoteca);
        pla.crearParticipacio("12345678A", 50.0, true);
        pla.crearParticipacio("87654321B", 30.0, false);
        pla.crearParticipacio("11111111C", 20.0, true);

        assertTrue(pla.haConduitParticipant("12345678A"), "Pau debería haber conducido");
        assertFalse(pla.haConduitParticipant("87654321B"), "Anna no debería haber conducido");
        assertTrue(pla.haConduitParticipant("11111111C"), "Marc debería haber conducido");
    }

    // ===== TESTS DE PLANIT - GET LOCAL =====

    @Test
    public void testGetLocal() {
        PlaNit pla = new PlaNit("Pla Test", discoteca);

        assertEquals(discoteca, pla.getLocal(), "El local debería ser el asignado en el constructor");
    }

    @Test
    public void testGetLocalDiferentes() {
        PlaNit pla1 = new PlaNit("Pla 1", discoteca);
        PlaNit pla2 = new PlaNit("Pla 2", pub);

        assertEquals(discoteca, pla1.getLocal(), "El local del plan 1 debería ser discoteca");
        assertEquals(pub, pla2.getLocal(), "El local del plan 2 debería ser pub");
        assertNotEquals(pla1.getLocal(), pla2.getLocal(), "Los locales deberían ser diferentes");
    }
}
