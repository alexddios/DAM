import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstadistiquesText {
     void main() {
        String nomFitxer = "C:\\Users\\alexd\\DAM\\Segundo curso\\Jumi\\Ejercicios\\src\\spanish.txt";
        int totalParaules = 0;
        int[] freqCaracters = new int[256];
        int[] lletresInici = new int[256];

        FI fitxer = new FI(nomFitxer);

        fitxer.obrir();
        String linea = fitxer.llegirLinia();

         while (linea != null) {
             String paraula = linea.trim().toLowerCase();
             if (!paraula.isEmpty()) {
                 totalParaules++;
             }
             linea = fitxer.llegirLinia();

             for (char c: paraula.toCharArray()){
                    freqCaracters[c]++;
             }
             char primeraLletra = paraula.charAt(0);
             if (Character.isLetter(primeraLletra)){
                 lletresInici[primeraLletra]++;
             }
         }
        fitxer.tancar();
         System.out.println("2) Nombre total de paraules: " + totalParaules);

         System.out.println("1) Freqüència total de cada caràcter:");
         for (int i = 0; i < freqCaracters.length; i++) {
             if (freqCaracters[i] > 0) {
                 System.out.println("'" + (char)i + "': " + freqCaracters[i]);
             }
         }
         System.out.println("3) Paraules que comencen per cada lletra:");
         int lletresDiferents = 0;
         for (int i = 0; i < lletresInici.length; i++) {
             if (lletresInici[i] > 0) {
                 System.out.println("'" + (char)i + "': " + lletresInici[i]);
                 lletresDiferents++;
             }
         }
         System.out.println("4) Nombre mitjà d'aparicions de cada caràcter per paraula:");
         for (int i = 0; i < freqCaracters.length; i++) {
             if (freqCaracters[i] > 0) {
                 double mitjana = (double) freqCaracters[i] / totalParaules;
                 System.out.printf("'%c': %.4f\n", (char)i, mitjana);
             }
         }
         System.out.println("5) Nombre mitjà de paraules per lletra inicial:");
         double mitjanaParaulesPerLletra = (lletresDiferents > 0) ? ((double)totalParaules / lletresDiferents) : 0;
         System.out.printf("%.2f", mitjanaParaulesPerLletra);
    }
}
