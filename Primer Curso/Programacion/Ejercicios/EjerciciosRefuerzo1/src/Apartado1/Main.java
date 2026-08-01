package Apartado1;

public class Main {
    public static void main(String[] args) {
        Aquari aquari = new Aquari(3);
        Peix maetu = new Peix("Mateu", "Tauro");
        Peix isma = new Peix("Isma", "Payaso");
       Peix alex = new Peix("Alex", "estudioso");
       Peix aleix = new Peix("Aleix", "farmaceutic");
       aquari.afegirPeix(maetu);
       aquari.afegirPeix(isma);
       aquari.afegirPeix(aleix);
       aquari.afegirPeix(alex);

       aquari.contarPeixos("Tauro");
       aquari.contarPeixos("estudioso");
       aquari.contarPeixos("Payaso");
       aquari.contarPeixos("farmaceutic");
       aquari.contarPeixos(null);
    }
}
