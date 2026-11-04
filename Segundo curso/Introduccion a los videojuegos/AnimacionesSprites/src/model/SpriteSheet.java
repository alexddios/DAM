package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Hoja de sprites: una imagen PNG con varios frames colocados en una rejilla.
 * Al crearla, carga la imagen del disco y recorta TODOS los frames una sola vez;
 * después getFrame solo devuelve el frame ya recortado, sin crear objetos nuevos.
 * Varias SpriteAnimation pueden compartir la misma hoja.
 */
public class SpriteSheet {
    private String ruta;              // ruta del archivo (para los mensajes de error)
    private final int columnas;       // columnas de la rejilla
    private int filas;                // filas de la rejilla
    private int pasos;                // frames reales de la animación (puede haber huecos al final)
    private int spriteWidth;          // ancho de cada frame en píxeles
    private int spriteHeight;         // alto de cada frame en píxeles
    private BufferedImage[] subImages; // frames ya recortados, en orden (posición n = frame n)

    /**
     * Carga la hoja, comprueba que se puede dividir en frames iguales y recorta todos los frames.
     *
     * @param ruta     ruta del PNG, relativa a la carpeta del proyecto (ej. "res/Bird.png")
     * @param columnas columnas de la rejilla
     * @param filas    filas de la rejilla
     * @param pasos    número de frames de la animación (1..columnas*filas)
     * @throws IllegalArgumentException si la imagen no se puede leer, no cuadra o los pasos no son válidos
     */
    public SpriteSheet(String ruta, int columnas, int filas, int pasos){
        // 1. Cargar la imagen del disco.
        //    Es variable local: solo hace falta aquí para recortar; después se usan los frames del array.
        BufferedImage image;
        try {
            this.ruta=ruta;
            image = ImageIO.read(new File(this.ruta));
            // ImageIO no lanza excepción con formatos que no entiende (p. ej. WebP): devuelve null
            if (image == null) {
                throw new IllegalArgumentException("Formato no soportado (¿es WebP?): " + ruta);
            }
        } catch (IOException e) {
            // El archivo no existe o no se puede leer
            throw new RuntimeException(e);
        }

        // 2. Comprobar que todos los frames miden lo mismo:
        //    el resto (%) de dividir píxeles entre columnas/filas son los píxeles que sobran, y tiene que ser 0
        if(image.getWidth()%columnas != 0 || image.getHeight()%filas != 0){
            throw new IllegalArgumentException("La hoja " + ruta + " (" + image.getWidth() + "x" + image.getHeight()
                    + ") no se puede dividir en " + columnas + " columnas y " + filas + " filas");
        }
        this.columnas = columnas;
        this.filas = filas;

        // 3. Los pasos no pueden superar las casillas de la rejilla
        if (pasos <= 0 || pasos > filas * columnas) {
            throw new IllegalArgumentException("Pasos inválidos: " + pasos
                    + " (debe estar entre 1 y " + (filas * columnas) + ")");
        }
        this.pasos=pasos;

        // 4. Tamaño de cada frame: la imagen dividida entre columnas y filas
        this.spriteWidth = image.getWidth() / columnas;
        this.spriteHeight = image.getHeight() / filas;

        // 5. Recortar todos los frames una vez.
        //    Va al final porque necesita pasos ya comprobado y spriteWidth/spriteHeight ya calculados.
        this.subImages= new BufferedImage[pasos];
        for (int n=0; n<subImages.length;n++){
            // De número de frame a posición en la rejilla
            int fila = n / columnas;      // división entera → fila
            int columna = n %columnas;    // resto → columna

            // De posición en la rejilla a píxeles dentro de la hoja
            int x = columna*this.spriteWidth;
            int y = fila*this.spriteHeight;
            this.subImages[n] = image.getSubimage(x,y,this.spriteWidth,this.spriteHeight);
        }
    }

    /**
     * Devuelve el frame número n, ya recortado en el constructor (no crea ningún objeto nuevo).
     * Los frames se numeran de izquierda a derecha y de arriba abajo, empezando en 0.
     *
     * @throws IndexOutOfBoundsException si n no está entre 0 y pasos-1
     */
    public BufferedImage getFrame(int n){
        if (n < 0 || n > pasos-1){
            throw new IndexOutOfBoundsException("Posición inválida: "+n);
        }
        return this.subImages[n];
    }

    /** Número de frames de la animación; SpriteAnimation lo usa para volver al frame 0. */
    public int getPasos(){
        return this.pasos;
    }
}
