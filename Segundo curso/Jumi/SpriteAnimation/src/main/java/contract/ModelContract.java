package contract;

import java.awt.image.BufferedImage;

/**
 * El contrato del modelo: lo que el presentador puede pedirle.
 * Los sprites no salen del modelo: se piden sus datos por su número (0, 1, 2…).
 */
public interface ModelContract {
    void start();                        // pone en marcha las animaciones (un hilo por sprite)
    int spriteCount();                   // cuántos sprites hay
    BufferedImage frame(int index);      // el fotograma que toca ahora
    int x(int index);                    // dónde está
    int y(int index);
    String name(int index);              // cómo se llama
    int fps(int index);                  // a cuántos fotogramas por segundo va
    void setFps(int index, int fps);     // cambiar esa velocidad
    int scale(int index);                // cuántas veces se amplía
    void setScale(int index, int scale); // cambiar ese tamaño, sin mover su centro
    void moveTo(int index, int x, int y);  // llevarlo a otro sitio
    void createDuck(int centerX, int centerY);  // un pato nuevo con su centro ahí, ya animándose
}
