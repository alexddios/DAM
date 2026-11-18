package model;

import java.awt.image.BufferedImage;

/**
 * Una animación del modelo, con su propio hilo que va cambiando de fotograma.
 * El ritmo depende del tiempo que pasa, no de lo rápido que se pinte.
 * No es pública: fuera del paquete model nadie la ve. El presentador pide sus datos al Model.
 */
class Sprite implements Runnable {

    private static final long TICK_MS = 5;   // cada cuánto mira el hilo si toca cambiar de fotograma

    private final String name;
    private final BufferedImage[] frames;   // los fotogramas originales, sin ampliar

    // Estos campos los escribe un hilo y los lee otro (el de pintado).
    // volatile hace que cada hilo vea siempre el último valor escrito.
    private volatile int frameIndex;   // lo cambia el hilo del sprite
    private volatile int fps;          // lo cambia la rueda del ratón
    private volatile int scale;        // cuántas veces se amplía: también lo cambia la rueda
    private volatile int x;            // x e y: los cambia el arrastre
    private volatile int y;

    public Sprite(String name, BufferedImage[] frames, int scale, int fps, int x, int y) {
        this.name = name;
        this.frames = frames;
        this.scale = scale;
        this.fps = fps;
        this.x = x;
        this.y = y;
    }

    public String name() { return this.name; }
    public BufferedImage frame() { return this.frames[this.frameIndex]; }   // el fotograma que toca ahora, sin ampliar
    public int scale() { return this.scale; }
    public int fps() { return this.fps; }
    public void setFps(int fps) { this.fps = fps; }
    public int x() { return this.x; }
    public int y() { return this.y; }

    /** Mueve el sprite. Si se pinta justo entre las dos líneas, se verá a medio mover
     *  durante un fotograma: nada grave, así que no hace falta sincronizar. */
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Cambia la escala sin mover el centro: la esquina se aleja (o se acerca) la mitad de lo que crece.
     * Como en moveTo, si se pinta a medio cambiar se verá así durante un fotograma.
     */
    public void setScale(int scale) {
        int growth = scale - this.scale;   // cuántas veces el tamaño original crece (o encoge, si es negativo)
        this.x -= this.frames[0].getWidth() * growth / 2;
        this.y -= this.frames[0].getHeight() * growth / 2;
        this.scale = scale;
    }

    /** Crea y arranca el hilo del sprite. Dura lo que la aplicación: al cerrar la ventana, termina con ella. */
    public void start() {
        Thread thread = new Thread(this, "Sprite " + this.name);
        thread.start();
    }

    /** Lo que hace el hilo del sprite: cada poco, avanza los fotogramas que tocan por el tiempo pasado. */
    @Override
    public void run() {
        long previous = System.nanoTime();
        double pending = 0;            // fotogramas que tocaba avanzar y aún no se han avanzado

        while (true) {
            long now = System.nanoTime();
            double elapsedSeconds = (now - previous) / 1_000_000_000.0;
            pending += elapsedSeconds * this.fps;   // los fotogramas que tocan en ese tiempo
            previous = now;

            while (pending >= 1) {
                this.frameIndex = (this.frameIndex + 1) % this.frames.length;   // tras el último, vuelve al primero
                pending--;
            }

            try {
                Thread.sleep(TICK_MS);
            } catch (InterruptedException e) {
                return;                // nadie interrumpe este hilo; si lo hiciera, terminaría
            }
        }
    }
}
