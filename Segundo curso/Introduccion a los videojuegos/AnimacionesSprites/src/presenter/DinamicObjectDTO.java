package presenter;

/**
 * Lo que la vista necesita para pintar el objeto: qué imagen (su id), dónde, con qué ángulo
 * y a qué tamaño (lado del cuadrado, en píxeles).
 * Solo lleva datos del presenter a la vista.
 */
public record DinamicObjectDTO(String idImagen, int x, int y, double angle, int size) {
}
