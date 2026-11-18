package model;

/**
 * Una animación concreta en ejecución. Solo guarda números: qué hoja usa (su id),
 * cuántos frames tiene y en cuál está. No conoce ninguna imagen: la vista busca la hoja
 * por su id y pinta el frame que le toca.
 * Con su propio hilo, avanza de frame cada msFrames milisegundos.
 * Varias animaciones pueden usar la misma hoja: cada una lleva su propio frame actual y su velocidad.
 */
public class SpriteAnimation implements Runnable {
    // volatile: lo escribe el hilo de la animación y lo lee el hilo de la vista (a través del presenter)
    private volatile int frameActual;
    // volatile: lo cambia stop() desde otro hilo y lo lee el bucle de run()
    private volatile boolean running;
    private final String idHoja;  // id de la hoja en el Catalogo (ej. "Pato")
    private final int pasos;      // frames de la hoja: sirve para volver al 0 tras el último
    private int msFrames;         // milisegundos entre un frame y el siguiente

    public SpriteAnimation(String idHoja, int pasos, int msFrames) {
        this.idHoja = idHoja;
        this.pasos=pasos;
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
            frameActual = (frameActual+1) % pasos;
            try {
                Thread.sleep(msFrames);
            }catch (InterruptedException e){
                running=false;
            }
        }
    }
    /** Número del frame que toca ahora. El presenter se lo pasa a la vista para que pinte ese frame. */
    public int getFrameActual() {
        return frameActual;
    }

    /** Id de la hoja que usa esta animación. Con él, la vista sabe de qué hoja sacar el frame. */
    public String getIdHoja(){
        return idHoja;
    }
}
