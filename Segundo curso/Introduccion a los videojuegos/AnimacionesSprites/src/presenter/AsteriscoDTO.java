package presenter;

/**
 * Lo que la vista necesita para pintar el asterisco: qué texto y dónde.
 * Solo lleva datos del presenter a la vista.
 */
public record AsteriscoDTO(String texto, int x, int y) {
}
