package presenter;

/**
 * Lo que la vista necesita para pintar el planeta: qué imagen (su id) y dónde.
 * Solo lleva datos del presenter a la vista.
 */
public record PlanetaDTO(String idImagen, int x, int y, double angle) {
}
