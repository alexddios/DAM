package model;

import java.awt.image.BufferedImage;

import check.Check;

/** Comprueba que el modelo no deja salir sus objetos (el fotograma que da es una copia) que la escala no mueve el centro y cómo nace un pato. */
public class ModelTest {

    public static void main(String[] args) {
        System.out.println("ModelTest");

        Model model = new Model();
        model.addSprite("Pato", "/sprites/pato.png", 16, 16, 4, 8, 120, 220);   // no se arranca: el fotograma no cambia

        BufferedImage frame = model.frame(0);
        Check.that("cada vez da una imagen nueva", model.frame(0) != frame);

        int pixel = frame.getRGB(30, 30);
        frame.setRGB(30, 30, ~pixel);   // pintamos encima de la imagen recibida
        Check.equal("pintar en la copia no cambia el fotograma del modelo", pixel, model.frame(0).getRGB(30, 30));

        Check.equal("el pato sale ampliado a escala 4: 64 px (16 × 4)", 64, model.frame(0).getWidth());
        Check.equal("con escala 4", 4, model.scale(0));
        model.setScale(0, 6);
        Check.equal("a escala 6 mide 96 px (16 × 6)", 96, model.frame(0).getWidth());
        Check.equal("y 96 px de alto", 96, model.frame(0).getHeight());
        // Antes iba de 120 a 184 (centro en 152); ahora mide 32 px más: la esquina se aleja 16.
        Check.that("crece desde el centro: la esquina pasa a (104, 204)", model.x(0) == 104 && model.y(0) == 204);
        model.setScale(0, 4);
        Check.that("y al volver a 4 vuelve a (120, 220)", model.x(0) == 120 && model.y(0) == 220);

        // Ctrl+clic: un pato nuevo como el de main al empezar (escala 4, 8 por segundo), centrado en el punto.
        model.setScale(0, 6);   // aunque el pato de main haya cambiado de escala
        model.createDuck(300, 200);
        Check.equal("createDuck añade un sprite", 2, model.spriteCount());
        Check.that("que se llama Pato y va a 8 por segundo", model.name(1).equals("Pato") && model.fps(1) == 8);
        Check.equal("con la escala con la que nació el pato, 4", 4, model.scale(1));
        Check.that("centrado en (300, 200): esquina en (268, 168)", model.x(1) == 268 && model.y(1) == 168);
        model.moveTo(1, 0, 0);
        Check.that("y se mueve sin arrastrar al pato original", model.x(0) != 0);

        Model withoutDuck = new Model();
        withoutDuck.createDuck(10, 10);
        Check.equal("sin pato en main, createDuck no hace nada", 0, withoutDuck.spriteCount());

        Check.finish("ModelTest");
        System.exit(0);   // el pato nuevo tiene su hilo en marcha: sin esto, la prueba no terminaría
    }
}
