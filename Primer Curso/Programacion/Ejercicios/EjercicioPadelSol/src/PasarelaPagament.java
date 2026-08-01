
public class PasarelaPagament {

    public static boolean pagar(double preu) {
        boolean pagatCorrectament = Math.random() < 0.9;
        if (pagatCorrectament) {
            System.out.println("Reserva pagada (" + preu + "€)");
        } else {
            System.out.println("Hi ha hagut un error durant el procés de pagament (" + preu + "€)");
        }
        return pagatCorrectament;
    }

}
