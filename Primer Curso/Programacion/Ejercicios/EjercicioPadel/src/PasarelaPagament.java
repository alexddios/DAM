public class PasarelaPagament {
    public static boolean pagar(double preu) {
        System.out.println("Processant pagament de: " + preu + "€...");
        return preu > 0 && Math.random() > 0.1;
    }
}
