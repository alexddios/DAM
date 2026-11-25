package model;

/**
 * Estado físico del objeto en un instante: cuándo es, dónde está, a qué velocidad va, hacia dónde mira
 * y cuánto empuja el motor.
 * Es un record, así que no se modifica: cada paso crea uno nuevo y el hilo de la vista siempre lee uno completo.
 * Misma idea que PhysicsValuesDTO de MVCGameEngine.
 *
 * @param timeStamp instante (nanoTime) al que corresponden estos valores
 * @param posX      posición en x, en píxeles
 * @param posY      posición en y, en píxeles
 * @param speedX    velocidad en x, en píxeles por segundo
 * @param speedY    velocidad en y, en píxeles por segundo
 * @param accX      aceleración en x, en píxeles por segundo al cuadrado (se calcula en cada paso: empuje · cos(ángulo))
 * @param accY      aceleración en y, en píxeles por segundo al cuadrado (se calcula en cada paso: empuje · sin(ángulo))
 * @param angle     hacia dónde mira, en grados (0 = derecha; crece en el sentido de las agujas del reloj)
 * @param thrust    empuje del motor, en píxeles por segundo al cuadrado (0 = motor apagado)
 */
public record DatosDinamicObject(long timeStamp,
                             double posX, double posY,
                             double speedX, double speedY,
                             double accX, double accY,
                           double angle, double thrust) {
}
