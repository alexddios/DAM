package config;

public record ResourceInfo(
        String nombre,
        int stock,
        int stockEsperado,
        boolean descuadre,
        boolean fueraDeLimites
) {

}
