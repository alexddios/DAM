package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import contract.SpriteView;

/**
 * El HUD: el marcador de arriba, al estilo de las recreativas, con las velocidades, una por línea.
 * Cada línea lleva su nombre, su cifra en fps y una barra de bloques. La línea que está bajo el ratón,
 * la que cambiará la rueda, se resalta con una banda amarilla.
 * La vista lo pinta en cada vuelta y le pregunta sobre qué línea está el ratón.
 */
public class Hud {

    // Dónde está. Y es la base de la primera línea (la de pintado); cada línea mide LINE.
    static final int X = 16;
    static final int Y = 40;
    static final int LINE = 24;
    static final int WIDTH = 320;

    // La barra: BAR_BLOCKS bloques; se llena entera a BAR_MAX fps (si va más rápido, sigue llena).
    private static final int BAR_MAX = 60;
    private static final int BAR_BLOCKS = 10;
    private static final int BAR_X = X + 170;   // dónde empieza, dentro de la línea
    private static final int BLOCK = 10;        // ancho de cada bloque
    private static final int BLOCK_GAP = 3;     // separación entre bloques

    private static final Color BACKGROUND = new Color(10, 10, 20, 210);   // casi negro, casi opaco
    private static final Color FRAME_OUTER = new Color(0xff3df2);         // marco doble: rosa fuera…
    private static final Color FRAME_INNER = new Color(0x00e5ff);         // …y cian dentro
    private static final Color LABEL = new Color(0xffe600);               // amarillo
    private static final Color TEXT = Color.WHITE;
    private static final Color ACTIVE = new Color(0xffe600);              // la banda de la línea activa
    private static final Color ACTIVE_TEXT = Color.BLACK;
    private static final Color BLOCK_GREEN = new Color(0x39ff14);
    private static final Color BLOCK_YELLOW = new Color(0xffe600);
    private static final Color BLOCK_RED = new Color(0xff3b3b);
    private static final Color BLOCK_OFF = new Color(0x333344);
    private static final Font FONT = new Font(Font.MONOSPACED, Font.BOLD, 15);

    /**
     * Pinta la velocidad de pintado y la de cada sprite, una por línea.
     * (mouseX, mouseY) es dónde está el ratón: la línea que cae debajo se resalta.
     */
    public void paint(Graphics2D g, int renderFps, List<SpriteView> spriteViews, int mouseX, int mouseY) {
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int lines = 1 + spriteViews.size();
        int activeLine = this.lineAt(mouseX, mouseY, lines);   // -1 si el ratón no está sobre el HUD
        this.paintFrame(g, lines);

        this.paintLine(g, 0, "Pintado", renderFps, activeLine == 0);
        for (int i = 0; i < spriteViews.size(); i++) {
            SpriteView spriteView = spriteViews.get(i);
            this.paintLine(g, i + 1, spriteView.name(), spriteView.fps(), activeLine == i + 1);
        }
    }

    /** Sobre qué línea cae el punto (x, y): 0, la de pintado; 1, 2…, los sprites; -1, fuera del HUD. */
    public int lineAt(int x, int y, int lines) {
        int left = X - 8;      // la esquina del recuadro de fondo, como en paintFrame
        int top = Y - LINE;
        if (x < left || x >= left + WIDTH || y < top) {
            return -1;
        }

        int line = (y - top) / LINE;
        if (line >= lines) {
            return -1;   // por debajo de la última línea
        }
        return line;
    }

    /** El fondo y el marco doble: 8 px de margen a la izquierda y abajo. */
    private void paintFrame(Graphics2D g, int lines) {
        int left = X - 8;
        int top = Y - LINE;
        int height = lines * LINE + 8;

        g.setColor(BACKGROUND);
        g.fillRect(left, top, WIDTH, height);

        g.setStroke(new BasicStroke(3));
        g.setColor(FRAME_OUTER);
        g.drawRect(left, top, WIDTH, height);
        g.setStroke(new BasicStroke(1));
        g.setColor(FRAME_INNER);
        g.drawRect(left + 4, top + 4, WIDTH - 8, height - 8);
    }

    /** Una línea: nombre, cifra y barra. Si es la activa, va sobre una banda amarilla, en negro y con ▶. */
    private void paintLine(Graphics2D g, int line, String label, int fps, boolean active) {
        int y = Y + line * LINE - 6;   // la base del texto, 6 px por encima del final de la línea

        Color labelColor = LABEL;
        Color textColor = TEXT;
        if (active) {
            g.setColor(ACTIVE);
            g.fillRect(X - 2, y - 16, WIDTH - 12, LINE - 2);   // la banda, dentro del marco
            labelColor = ACTIVE_TEXT;
            textColor = ACTIVE_TEXT;

            // El triángulo ▶ de la izquierda: sus tres esquinas.
            int[] xs = {X, X, X + 7};
            int[] ys = {y - 11, y - 1, y - 6};
            g.setColor(ACTIVE_TEXT);
            g.fillPolygon(xs, ys, 3);
        }

        g.setFont(FONT);
        g.setColor(labelColor);
        g.drawString(label.toUpperCase(), X + 12, y);
        g.setColor(textColor);
        g.drawString(String.format("%3d fps", fps), X + 100, y);   // %3d: la cifra ocupa 3 huecos

        this.paintBar(g, y, fps, active);
    }

    /** La barra de bloques: cuantos más fps, más bloques encendidos. */
    private void paintBar(Graphics2D g, int y, int fps, boolean active) {
        int on = fps * BAR_BLOCKS / BAR_MAX;   // cuántos bloques se encienden
        if (on < 1) {
            on = 1;                            // siempre se ve uno: la velocidad mínima es 1
        }
        if (on > BAR_BLOCKS) {
            on = BAR_BLOCKS;
        }

        if (active) {
            // Sobre la banda amarilla, un fondo negro: si no, los bloques amarillos no se verían.
            g.setColor(ACTIVE_TEXT);
            g.fillRect(BAR_X - 2, y - 14, BAR_BLOCKS * (BLOCK + BLOCK_GAP) + 1, 16);
        }

        for (int b = 0; b < BAR_BLOCKS; b++) {
            if (b < on) {
                g.setColor(this.blockColor(b));
            } else {
                g.setColor(BLOCK_OFF);
            }
            g.fillRect(BAR_X + b * (BLOCK + BLOCK_GAP), y - 12, BLOCK, 12);
        }
    }

    /** El color de un bloque encendido: verdes los 6 primeros, luego 2 amarillos y 2 rojos. */
    private Color blockColor(int block) {
        if (block < 6) {
            return BLOCK_GREEN;
        }
        if (block < 8) {
            return BLOCK_YELLOW;
        }
        return BLOCK_RED;
    }
}
