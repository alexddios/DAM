package assets;

/**
 * Datos de una hoja de sprites del catálogo. No es la imagen: solo dónde está y cómo se recorta.
 *
 * @param ruta     ruta del PNG, relativa a la carpeta del proyecto (ej. "res/pato.png")
 * @param columnas columnas de la rejilla
 * @param filas    filas de la rejilla
 * @param pasos    frames de la animación (puede haber casillas vacías al final)
 */
public record HojaInfo(String ruta, int columnas, int filas, int pasos) {

}
