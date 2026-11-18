package model;

public record ScoreInfo(String nombre, int puntos) {

    @Override
    public String toString() {
        return String.format("%s & %d & ", nombre, puntos);
    }
}
