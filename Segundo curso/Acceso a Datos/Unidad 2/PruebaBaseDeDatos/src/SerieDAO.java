import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SerieDAO {
    private static final String URL = "jdbc:sqlite:data/series.db";

    public void crearTaula() {
        String sql = """
                CREATE TABLE IF NOT EXISTS series (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    titol TEXT NOT NULL,
                    genere TEXT,
                    temporades INTEGER,
                    plataforma TEXT
                )""";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creant la taula: " + e.getMessage());
        }
    }

    public int inserir(Serie s) {
        String sql = "INSERT INTO series (titol, genere, temporades, plataforma) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getTitol());
            ps.setString(2, s.getGenere());
            ps.setInt(3, s.getTemporades());
            ps.setString(4, s.getPlataforma());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en inserir: " + e.getMessage());
            return 0;
        }
    }

    public List<Serie> obtenirTotes() {
        return consultar("SELECT * FROM series", null);
    }

    public List<Serie> obtenirPerGenere(String genere) {
        return consultar("SELECT * FROM series WHERE genere = ?", genere);
    }

    private List<Serie> consultar(String sql, String filtre) {
        List<Serie> llista = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (filtre != null) ps.setString(1, filtre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    llista.add(new Serie(rs.getInt("id"), rs.getString("titol"),
                            rs.getString("genere"), rs.getInt("temporades"), rs.getString("plataforma")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en consultar: " + e.getMessage());
        }
        return llista;
    }

    public int actualitzarTemporades(int id, int temporades) {
        String sql = "UPDATE series SET temporades = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, temporades);
            ps.setInt(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en actualitzar: " + e.getMessage());
            return 0;
        }
    }

    public int eliminar(int id) {
        String sql = "DELETE FROM series WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en eliminar: " + e.getMessage());
            return 0;
        }
    }
}
