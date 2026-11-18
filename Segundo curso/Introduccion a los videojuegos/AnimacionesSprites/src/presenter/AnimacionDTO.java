package presenter;

/**
 * Lo que la vista necesita para pintar una animación: de qué hoja es y qué frame toca.
 * Solo lleva datos del presenter a la vista; nadie lo cambia una vez creado.
 *
 * @param idHoja id de la hoja en el Catalogo (ej. "Pato")
 * @param frame  número del frame que hay que pintar
 */
public record AnimacionDTO(String idHoja, int frame) {
}
