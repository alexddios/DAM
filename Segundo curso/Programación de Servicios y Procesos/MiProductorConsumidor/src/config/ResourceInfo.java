package config;

/**
 * "Foto" de un recurso que viaja del Modelo a la Vista.
 * Es inmutable y está en config para que la Vista no conozca la clase Resource.
 */
public record ResourceInfo(
        String nombre,
        int stock,
        int stockEsperado,
        boolean descuadre,
        boolean fueraDeLimites
) {

}
