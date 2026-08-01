package Ficheros;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class FO {

    private final String nom;
    private FileWriter fic;
    private BufferedWriter writer;

    public FO(String n) {
        nom = n;
    }

    public void obrir(boolean afegir) {
        try {
            fic = new FileWriter(nom, afegir);
            writer = new BufferedWriter(fic);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void gravarLinia(String lin) {
        try {
            writer.write(lin);
            writer.newLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void tancar() {
        try {
            writer.close();
            fic.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                fic.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
