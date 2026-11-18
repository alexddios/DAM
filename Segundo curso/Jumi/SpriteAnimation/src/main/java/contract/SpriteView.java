package contract;

import java.awt.image.BufferedImage;

/**
 * Lo que la vista necesita para enseñar un sprite: la imagen que toca, dónde pintarla,
 * y su nombre y velocidad para el HUD. Nadie la cambia una vez hecha.
 * No es un contrato: son los datos que el presentador le da a la vista con spriteViews().
 */
public record SpriteView(BufferedImage image, int x, int y, String name, int fps) {}
