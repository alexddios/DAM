package model;

/**
 * Estado físico de la nave en un instante: cuándo es, dónde está, a qué velocidad va y cuánto acelera.
 * Es un record, así que no se modifica: cada paso crea uno nuevo y el hilo de la vista siempre lee uno completo.
 * Misma idea que PhysicsValuesDTO de MVCGameEngine.
 *
 * @param timeStamp instante (nanoTime) al que corresponden estos valores
 * @param posX      posición en x, en píxeles
 * @param posY      posición en y, en píxeles
 * @param speedX    velocidad en x, en píxeles por segundo
 * @param speedY    velocidad en y, en píxeles por segundo
 * @param accX      aceleración en x, en píxeles por segundo al cuadrado
 * @param accY      aceleración en y, en píxeles por segundo al cuadrado
 */
public record DatosNave(long timeStamp,
                             double posX, double posY,
                             double speedX, double speedY,
                             double accX, double accY) {
}
