package view;

import java.awt.Canvas;

/**
 * El lienzo, parte interna de la vista. No pinta: es la superficie sobre la que pinta el hilo de la vista.
 * Es un Canvas porque permite pintar de forma explícita, cuando lo decide nuestro hilo.
 */
public class Viewer extends Canvas {

    public Viewer() {
        this.setIgnoreRepaint(true);   // nada se dibuja solo: Swing no debe repintarlo por su cuenta
    }
}
