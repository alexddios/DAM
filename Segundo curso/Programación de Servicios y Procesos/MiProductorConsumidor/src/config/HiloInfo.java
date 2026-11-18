package config;

/**
 * "Foto" de un hilo que viaja del Modelo a la Vista.
 * objetivo vale -1 cuando el hilo es ilimitado.
 */
public record HiloInfo(
        String nombre,
        int unidades,
        int objetivo,
        EstadoHilo estadoHilo
) {
}
