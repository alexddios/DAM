package model;

/**
 * El asterisco: su texto ("*") y su estado físico (DatosAsterisco).
 * Con su propio hilo, cada MS milisegundos calcula cuánto tiempo (dt) ha pasado y mueve el asterisco
 * con MRUA, como BasicPhysicsEngine de MVCGameEngine. Así la velocidad va en píxeles por segundo
 * y no depende de lo rápido que gire el hilo.
 */
public class Asterisco implements Runnable {
    private static final int MS = 16;                  // ~60 pasos por segundo
    private static final double DT_MAX = 0.5;          // segundos; si pasa más, se recorta (como en el motor del profesor)
    private static final double NANOS_POR_SEGUNDO = 1_000_000_000.0;

    private final String texto;
    // volatile: lo escribe el hilo del asterisco y lo lee el hilo de la vista (a través del presenter)
    private volatile DatosAsterisco datos;
    // volatile: lo cambia stop() desde otro hilo y lo lee el bucle de run()
    private volatile boolean running;

    /**
     * @param x,y   posición inicial en píxeles
     * @param vx,vy velocidad inicial en píxeles por segundo
     * @param ax,ay aceleración en píxeles por segundo al cuadrado
     */
    public Asterisco(String texto, double x, double y, double vx, double vy, double ax, double ay) {
        this.texto = texto;
        this.datos = new DatosAsterisco(System.nanoTime(), x, y, vx, vy, ax, ay);
        this.running = false;
    }

    /** Arranca el hilo. Si ya está en marcha no hace nada (evita crear dos hilos). */
    public void start() {
        if (!running) {
            running = true;
            // El reloj empieza ahora: si estuvo parado, el dt del primer paso no cuenta todo el tiempo de la pausa
            DatosAsterisco d = datos;
            datos = new DatosAsterisco(System.nanoTime(), d.posX(), d.posY(), d.speedX(), d.speedY(), d.accX(), d.accY());
            new Thread(this, "asterisco").start();
        }
    }

    /** Para el hilo; el asterisco se queda donde está y Iniciar lo reanuda. */
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            datos = integrar(datos);
            try {
                Thread.sleep(MS);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    /** MRUA: v1 = v0 + a·dt  y  x1 = x0 + (v0 + v1)/2 · dt. Devuelve los datos nuevos; no toca los antiguos. */
    private DatosAsterisco integrar(DatosAsterisco d) {
        long ahora = System.nanoTime();
        double dt = Math.min((ahora - d.timeStamp()) / NANOS_POR_SEGUNDO, DT_MAX);

        double nuevaVx = d.speedX() + d.accX() * dt;
        double nuevaVy = d.speedY() + d.accY() * dt;

        double nuevaX = d.posX() + (d.speedX() + nuevaVx) * 0.5 * dt;   // velocidad media
        double nuevaY = d.posY() + (d.speedY() + nuevaVy) * 0.5 * dt;

        return new DatosAsterisco(ahora, nuevaX, nuevaY, nuevaVx, nuevaVy, d.accX(), d.accY());
    }

    public String getTexto() { return texto; }
    public double getX() { return datos.posX(); }
    public double getY() { return datos.posY(); }
}
