package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import contract.SpriteView;

/**
 * La diana: una mira roja que la vista pinta encima del sprite que hay bajo el ratón.
 * Se mide con la imagen del sprite, así que crece y mengua con él.
 */
public class Target {

    private static final Color COLOR = new Color(255, 70, 70);
    private static final BasicStroke STROKE = new BasicStroke(3);

    /** Pinta la mira centrada en el sprite: dos círculos y cuatro trazos de cruz con el centro libre. */
    public void paint(Graphics2D g, SpriteView spriteView) {
        int width = spriteView.image().getWidth();
        int height = spriteView.image().getHeight();
        int centerX = spriteView.x() + width / 2;
        int centerY = spriteView.y() + height / 2;
        int radius = Math.max(width, height) * 3 / 4;   // el círculo grande rodea el sprite
        int inner = radius / 2;                          // el pequeño, la mitad

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(COLOR);
        g.setStroke(STROKE);
        g.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        g.drawOval(centerX - inner, centerY - inner, 2 * inner, 2 * inner);

        // La cruz: de un poco fuera del círculo grande hasta el pequeño, arriba, abajo, izquierda y derecha.
        int outer = radius + 8;
        g.drawLine(centerX, centerY - outer, centerX, centerY - inner);
        g.drawLine(centerX, centerY + inner, centerX, centerY + outer);
        g.drawLine(centerX - outer, centerY, centerX - inner, centerY);
        g.drawLine(centerX + inner, centerY, centerX + outer, centerY);
    }
}
