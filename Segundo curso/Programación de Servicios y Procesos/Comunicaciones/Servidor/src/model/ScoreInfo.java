package model;

/**
 * Una entrada del ranking: un nombre y su puntuación.
 * Es un record porque los datos no cambian una vez creados (inmutable).
 * La usan tanto el servidor (HighScores) como el cliente (Model).
 */
public record ScoreInfo(String nombre, int puntos) {

    /**
     * Devuelve la entrada con el formato del protocolo: "nombre & puntos & ".
     * Así HighScores puede montar la respuesta del 'get' solo concatenando objetos.
     */
    @Override
    public String toString() {
        return String.format("%s & %d & ", nombre, puntos);
    }
}
