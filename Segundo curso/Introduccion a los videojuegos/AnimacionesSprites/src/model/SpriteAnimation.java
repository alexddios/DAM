package model;

import java.awt.image.BufferedImage;

/**
 * Una animación concreta en ejecución. Usa una SpriteSheet y, con su propio hilo,
 * va avanzando de frame cada msFrames milisegundos.
 * Varias animaciones pueden compartir hoja: cada una lleva su propio frame actual y su velocidad.
 */
public class SpriteAnimation implements Runnable {
    // volatile: lo escribe el hilo de la animación y lo lee el hilo de la vista (getImage)
    private volatile int frameActual;
    // volatile: lo cambia stop() desde otro hilo y lo lee el bucle de run()
    private volatile boolean running;
    private SpriteSheet hoja;     // hoja de la que salen los frames
    private int msFrames;         // milisegundos entre un frame y el siguiente

    public SpriteAnimation(SpriteSheet hoja, int msFrames) {
        this.hoja = hoja;
        this.msFrames = msFrames;
        this.frameActual = 0;
        this.running = false;     // todavía no hay hilo: empieza parada
    }

    /** Arranca el hilo de la animación. Si ya está en marcha no hace nada (evita crear dos hilos). */
    public void start(){
        if (!running) {
            running = true;
            new Thread(this).start();   // ejecuta run() en un hilo nuevo
        }
    }

    /** Para la animación: el bucle de run() termina y el hilo muere. Se queda en el frame actual. */
    public void stop(){
        running = false;
    }

    /** Bucle del hilo: avanza un frame, espera, y repite mientras running sea true. */
    @Override
    public void run(){
        while (running){
            // Siguiente frame; el % lo devuelve a 0 tras el último (0,1,...,pasos-1,0,1,...).
            // En una sola asignación para que frameActual nunca valga "pasos", ni un instante.
            frameActual = (frameActual+1) % hoja.getPasos();
            try {
                Thread.sleep(msFrames);
            }catch (InterruptedException e){
                running=false;
            }
        }
    }

    /** Imagen del frame que toca ahora mismo. */
    public BufferedImage getImage(){
        return hoja.getFrame(frameActual);
    }

    /** Número del frame actual (útil para pruebas). */
    public int getFrameActual() {
        return frameActual;
    }
}
