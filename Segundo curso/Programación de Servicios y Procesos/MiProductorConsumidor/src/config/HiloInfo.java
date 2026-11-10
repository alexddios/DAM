package config;

public record HiloInfo(
        String nombre,
        int unidades,
        int objetivo,
        EstadoHilo estadoHilo
) {
}
