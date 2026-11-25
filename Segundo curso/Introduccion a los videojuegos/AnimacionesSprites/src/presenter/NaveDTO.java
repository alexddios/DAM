package presenter;

/**
 * Lo que la vista necesita para pintar la nave: qué imagen (su id) y dónde.
 * Solo lleva datos del presenter a la vista.
 */
public record NaveDTO(String idImagen, int x, int y) {
}
