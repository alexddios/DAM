import org.w3c.dom.stylesheets.LinkStyle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonatgesDAO {
    private final String url = "jdbc:sqlite:data\\\\personatges.db";

    public int inserir(Personatge p){
        String sql = """
                INSERT INTO personatges(nom,joc,classe,nivell)
                VALUES (?,?,?,?)
                """;
        try(
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement ps = conn.prepareStatement(sql)

        ){
            ps.setString(1, p.getNom());
            ps.setString(2, p.getJoc());
            ps.setString(3, p.getClasse());
            ps.setInt(4, p.getNivell());
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return 0;
        }
    }
    public List<Personatge> obtenirTotes(){
        List<Personatge> res = new ArrayList<>();

        String sql = """
                SELECT *
                FROM personatges
                """;
        try(
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
                ){
            while (rs.next()) {
                Personatge p = new Personatge(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("joc"),
                        rs.getString("classe"),
                        rs.getInt("nivell")
                );
                res.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error: "+ e);
        }
        return res;
    }
    public List<Personatge> obtenirPerJoc(String joc){
        List<Personatge> personatges = new ArrayList<>();

        String sql = """
        SELECT * FROM personatge
        WHERE joc = ?
        ORDER BY nivell ASC
        """;

        try (
                Connection conn = DriverManager.getConnection(this.url);
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, joc);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Personatge p = new Personatge(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("joc"),
                            rs.getString("classe"),
                            rs.getInt("nivell")
                    );
                    personatges.add(p);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return personatges;
    }
    public Map<Integer,ArrayList<Personatge>> getDicPersonatgesByNivellMin (int nivellMinim){
        Map<Integer, ArrayList<Personatge>> mapaPersonatges = new HashMap<>();

        String sql = """
        SELECT * FROM personatges
        where nivell >= ?
    """;

        try (
                Connection conn = DriverManager.getConnection(this.url);
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, nivellMinim);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Personatge p = new Personatge(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("joc"),
                            rs.getString("classe"),
                            rs.getInt("nivell")
                    );

                    int nivell = p.getNivell();

                    if (!mapaPersonatges.containsKey(nivell)) {
                        mapaPersonatges.put(nivell, new ArrayList<>());
                    }

                    mapaPersonatges.get(nivell).add(p);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mapaPersonatges;
    }
    public int cambiarNivell(int id,int nivell){
        String sql = """
                UPDATE Personatges
                SET nivell = ?
                WHERE id=?
                """;
        try(
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setInt(1,nivell);
            ps.setInt(2,id);

            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return 0;
        }
    }
    public int eliminarPerId(int id){
        String sql= """
                DELETE FROM Personatges
                where id=?
                """;
        try(
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setInt(1,id);
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: "+e);
            return 0;
        }
    }
}
