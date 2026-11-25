package view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Lienzo donde se pintan las animaciones, en una rejilla de COLUMNAS x FILAS celdas.
 * No conoce el modelo: solo recibe imágenes y las dibuja.
 */
public class ViewPanel extends Canvas {
    // volatile: lo escribe el hilo de la View (setImages) y lo lee el hilo que pinta (paint)
    private volatile List<BufferedImage> images;
    private static final int COLUMNAS = 5;
    private static final int FILAS = 2;
    private static final Font FUENTE = new Font(Font.SANS_SERIF, Font.BOLD, 40);   // fuente del planeta
    // volatile: los escribe el hilo de la View y los lee el hilo que pinta
    private volatile BufferedImage imagen;
    private volatile int planetaX;
    private volatile int planetaY;
    private volatile double planetaAngulo;   // grados

    /** Recibe la imagen y la posición del planeta. No repinta: lo hace setImages justo después. */
    public void setPlaneta(BufferedImage imagen, int x, int y, double angulo){
        this.imagen = imagen;
        this.planetaX = x;
        this.planetaY = y;
        this.planetaAngulo = angulo;
    }

    /** Recibe los frames actuales y pide un repintado. */
    public void setImages(List<BufferedImage> images){
        this.images = images;
        repaint();
    }

    /** Pinta con doble buffer: primero todo en una imagen en memoria y luego se vuelca de una vez (sin parpadeo). */
    @Override
    public void paint(Graphics g) {
        // Copia local: si setImages cambia la lista a mitad, se sigue pintando la que teníamos
        List<BufferedImage> current = images;
        // Al arrancar el lienzo puede medir 0 y new BufferedImage(0, 0, ...) fallaría
        if (current == null || getWidth() == 0 || getHeight()==0) return;

        // 1. Lienzo en memoria y su pincel
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics gb = buffer.getGraphics();

        // 2. Fondo (borra el frame anterior; sin esto los sprites transparentes dejarían rastro)
        gb.setColor(Color.BLACK);
        gb.fillRect(0, 0, getWidth(), getHeight());

        // 3. Tamaño de cada celda
        int anchoCelda = getWidth()/COLUMNAS;
        int altoCelda  = getHeight()/FILAS;

        // 4. Pintar cada imagen en su celda (misma fórmula que getFrame de SpriteSheet)
        for (int i = 0; i < current.size(); i++) {
            int fila    = i / COLUMNAS;
            int columna = i % COLUMNAS;
            int x = columna * anchoCelda;
            int y = fila*altoCelda;
            gb.drawImage(current.get(i), x, y, anchoCelda, altoCelda, null);
        }

        // 5. El planeta, el último para que quede encima de las animaciones.
        //    drawString recibe la línea base: se suma el ascent para que (x, y) sea su esquina de arriba
        gb.setColor(Color.WHITE);
        gb.setFont(FUENTE);
        if (imagen != null) {
            Graphics2D g2 = (Graphics2D) gb;
            AffineTransform original = g2.getTransform();          // guardar cómo estaba el pincel
            // Girar el pincel alrededor del centro de la imagen (en grados -> radianes)
            g2.rotate(Math.toRadians(planetaAngulo),
                    planetaX + imagen.getWidth() / 2.0, planetaY + imagen.getHeight() / 2.0);
            g2.drawImage(imagen, planetaX, planetaY, null);
            g2.setTransform(original);                              // dejarlo como estaba
        }

        gb.dispose();   // soltar el pincel del buffer

        // 6. Volcar a pantalla
        g.drawImage(buffer, 0, 0, null);
    }

    /** Por defecto update() borra el Canvas antes de pintar, lo que provoca parpadeo; aquí se pinta directamente. */
    @Override
    public void update(Graphics g){ paint(g);}
}
