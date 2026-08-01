package ApartadoB;

public class Main {
    public static void main(String[] args) {
        AppFesta app = new AppFesta("Festa Major");

        Local l1 = new Local("Disco Blue", TipusLocal.DISCOTECA);
        Local l2 = new Local("Pub Red", TipusLocal.PUB);

        app.afegirLocal(l1);
        app.afegirLocal(l2);

        System.out.println("Crear PlaNit 1: " + app.crearPlaNit("Pla 1", l1));
        System.out.println("Crear PlaNit 2: " + app.crearPlaNit("Pla 2", l2));
        System.out.println("Crear PlaNit Duplicat: " + app.crearPlaNit("Pla 1", l1));


        
        PlaNit p1 = new PlaNit("Pla Test", l1);
        System.out.println("Participació 1 (Pau): " + p1.crearParticipacio("12345678A", 50.0, true));
        System.out.println("Participació 2 (Anna): " + p1.crearParticipacio("87654321B", 30.0, false));
        System.out.println("Participació Duplicada (Pau): " + p1.crearParticipacio("12345678A", 20.0, false));
        System.out.println("Participació Import Negatiu: " + p1.crearParticipacio("11111111C", -10.0, false));

        System.out.println("Gasto Pau en Pla Test: " + p1.obtenirImportGastatParticipant("12345678A"));
        System.out.println("¿Ha conducido Pau en Pla Test?: " + p1.haConduitParticipant("12345678A"));
        System.out.println("¿Ha conducido Anna en Pla Test?: " + p1.haConduitParticipant("87654321B"));

        app.crearPlaNit("Pla 3", l1);

        
        System.out.println("Plans de tipus DISCOTECA: " + app.comptarPlansPerTipusLocal(TipusLocal.DISCOTECA));
        System.out.println("Plans de tipus PUB: " + app.comptarPlansPerTipusLocal(TipusLocal.PUB));
    }
}
