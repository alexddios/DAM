package model;

import java.awt.image.BufferedImage;

import check.Check;

/** Comprueba que SpriteSheet recorta bien una hoja en columna (pato) y una en fila (explosión). */
public class SpriteSheetTest {

    public static void main(String[] args) {
        System.out.println("SpriteSheetTest");

        BufferedImage[] duck = SpriteSheet.load("/sprites/pato.png", 16, 16);
        Check.equal("el pato, en columna, tiene 4 fotogramas", 4, duck.length);
        Check.equal("de 16 px de ancho: a su tamaño, sin ampliar", 16, duck[0].getWidth());
        Check.equal("y 16 px de alto", 16, duck[0].getHeight());

        BufferedImage[] explosion = SpriteSheet.load("/sprites/explosion.png", 48, 48);
        Check.equal("la explosión, en fila, tiene 8 fotogramas", 8, explosion.length);
        Check.equal("de 48 px de ancho", 48, explosion[0].getWidth());
        Check.equal("y 48 px de alto", 48, explosion[0].getHeight());

        Check.that("los fotogramas son distintos entre sí", differ(duck[0], duck[1]) && differ(explosion[0], explosion[4]));
        Check.finish("SpriteSheetTest");
    }

    private static boolean differ(BufferedImage a, BufferedImage b) {
        for (int y = 0; y < a.getHeight(); y++) {
            for (int x = 0; x < a.getWidth(); x++) {
                if (a.getRGB(x, y) != b.getRGB(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }
}
