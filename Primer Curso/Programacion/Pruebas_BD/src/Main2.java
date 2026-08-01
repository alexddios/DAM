import java.util.List;
import java.util.Map;

public class Main2 {
    public static void main (String[] args){
        // 1. Instanciamos nuestra herramienta apuntando a la base de datos
        MotorBD db = new MotorBD("data/personatges.db");

        // --- EJEMPLO 1: INSERTAR DE FORMA GENÉRICA ---
        String sqlInsert = "INSERT INTO personatge (id, nom, joc, classe,nivell) VALUES (?, ?, ?, ?,?)";
        // Fíjate que le pasamos el string y luego los valores separados por comas
        db.ejecutarModificacion(sqlInsert, 11,"Prueba", "Prueba", "Prueba", 1);


        // --- EJEMPLO 2: BORRAR DE FORMA GENÉRICA ---
        String sqlDelete = "DELETE FROM personatge WHERE id = ?";
        db.ejecutarModificacion(sqlDelete, 11);


        // --- EJEMPLO 3: CONSULTAR DE FORMA GENÉRICA ---
        String sqlSelect = "SELECT nom, joc FROM personatge WHERE nivell > ?";
        List<Map<String, Object>> filas = db.ejecutarConsulta(sqlSelect, 3);

        System.out.println("personatges con más de 3 nivell:");
        for (Map<String, Object> fila : filas) {
            // Sacamos los datos del mapa usando el nombre exacto de la columna
            System.out.println(fila.get("nom") + " se emite en " + fila.get("joc"));
        }
    }
}
