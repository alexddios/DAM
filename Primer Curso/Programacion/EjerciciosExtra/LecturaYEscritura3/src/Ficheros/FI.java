package Ficheros;

import java.io.BufferedReader;
import java.io.FileReader;

public class FI {

    private final String nom;
    private FileReader fic;
    private BufferedReader reader;

    public FI(String n) {
        nom = n;
    }

    public void obrir() {
        try {
            fic = new FileReader(nom);
            reader = new BufferedReader(fic);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String llegirLinia() {
        String res = null;
        try {
            res = reader.readLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public void tancar() {
        try {
            reader.close();
            fic.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                fic.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
