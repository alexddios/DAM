package model;

/**
 * La nave: el id de su imagen en el Catalogo (ej. "Nave") y su estado físico (DatosNave).
 * Con su propio hilo, cada MS milisegundos calcula cuánto tiempo (dt) ha pasado y mueve la nave
 * con MRUA, como BasicPhysicsEngine de MVCGameEngine. Así la velocidad va en píxeles por segundo
 * y no depende de lo rápido que gire el hilo.
 */
public class Nave implements Runnable {
    private static final int MS = 16;                  // ~60 pasos por segundo
    private static final double DT_MAX = 0.5;          // segundos; si pasa más, se recorta (como en el motor del profesor)
    private static final double NANOS_POR_SEGUNDO = 1_000_000_000.0;

    private final String idImagen;   // id de la hoja en el Catalogo: el modelo no tiene la imagen, solo su id
    // volatile: lo escribe el hilo de la nave y lo lee el hilo de la vista (a través del presenter)
    private volatile DatosNave datos;
    // volatile: lo cambia stop() desde otro hilo y lo lee el bucle de run()
    private volatile boolean running;

    /**
     * @param x,y   posición inicial en píxeles
     * @param vx,vy velocidad inicial en píxeles por segundo
     * @param ax,ay aceleración en píxeles por segundo al cuadrado
     */
    public Nave(String idImagen, double x, double y, double vx, double vy, double ax, double ay) {
        this.idImagen = idImagen;
        this.datos = new DatosNave(System.nanoTime(), x, y, vx, vy, ax, ay);
        this.running = false;
    }

    /** Arranca el hilo. Si ya está en marcha no hace nada (evita crear dos hilos). */
    public void start() {
        if (!running) {
            running = true;
            // El reloj empieza ahora: si estuvo parado, el dt del primer paso no cuenta todo el tiempo de la pausa
            DatosNave d = datos;
            datos = new DatosNave(System.nanoTime(), d.posX(), d.posY(), d.speedX(), d.speedY(), d.accX(), d.accY());
            new Thread(this, "nave").start();
        }
    }

    /** Para el hilo; la nave se queda donde está y Iniciar lo reanuda. */
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
    private DatosNave integrar(DatosNave d) {
        long ahora = System.nanoTime();
        double dt = Math.min((ahora - d.timeStamp()) / NANOS_POR_SEGUNDO, DT_MAX);

        double nuevaVx = d.speedX() + d.accX() * dt;
        double nuevaVy = d.speedY() + d.accY() * dt;

        double nuevaX = d.posX() + (d.speedX() + nuevaVx) * 0.5 * dt;   // velocidad media
        double nuevaY = d.posY() + (d.speedY() + nuevaVy) * 0.5 * dt;

        return new DatosNave(ahora, nuevaX, nuevaY, nuevaVx, nuevaVy, d.accX(), d.accY());
    }

    public String getIdImagen() { return idImagen; }
    public double getX() { return datos.posX(); }
    public double getY() { return datos.posY(); }
}
