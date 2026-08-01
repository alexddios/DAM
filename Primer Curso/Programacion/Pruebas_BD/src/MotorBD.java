import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MotorBD {

    private final String url;

    // Al crear el objeto, le decimos a qué base de datos se va a conectar
    public MotorBD(String rutaBD) {
        this.url = "jdbc:sqlite:" + rutaBD;
    }

    /**
     * MÉTODO GENÉRICO PARA INSERT, UPDATE Y DELETE
     * @param sql La consulta con interrogantes (ej: "DELETE FROM tabla WHERE id = ?")
     * @param parametros Los valores para sustituir los interrogantes
     * @return El número de filas afectadas
     */
    public int ejecutarModificacion(String sql, Object... parametros) {
        try (Connection conn = DriverManager.getConnection(this.url);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Asignamos los parámetros genéricos al PreparedStatement
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }

            return ps.executeUpdate(); // Ejecuta y devuelve cuántas filas se han modificado

        } catch (SQLException e) {
            System.out.println("Error en la modificación SQL: " + e.getMessage());
            return -1;
        }
    }

    /**
     * MÉTODO GENÉRICO PARA SELECT
     * Como no sabemos qué objeto devolver (Serie, Alumno, etc.), 
     * devolvemos una Lista de "Mapas" (Diccionarios: Columna -> Valor)
     */
    public List<Map<String, Object>> ejecutarConsulta(String sql, Object... parametros) {
        List<Map<String, Object>> resultados = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(this.url);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Asignamos los parámetros
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                // Obtenemos los metadatos para saber cuántas columnas hay y sus nombres
                ResultSetMetaData metaData = rs.getMetaData();
                int numColumnas = metaData.getColumnCount();

                // Recorremos las filas devueltas
                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    for (int i = 1; i <= numColumnas; i++) {
                        String nombreColumna = metaData.getColumnName(i);
                        Object valor = rs.getObject(i);
                        fila.put(nombreColumna, valor);
                    }
                    resultados.add(fila);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta SQL: " + e.getMessage());
        }

        return resultados;
    }
}