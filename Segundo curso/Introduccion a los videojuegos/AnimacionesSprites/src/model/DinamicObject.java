package model;

/**
 * El objeto (planeta o nave): el id de su imagen en el Catalogo (ej. "Nave") y su estado físico (DatosDinamicObject).
 * Con su propio hilo, cada MS milisegundos calcula cuánto tiempo (dt) ha pasado y mueve el objeto
 * con MRUA, como BasicPhysicsEngine de MVCGameEngine. Así la velocidad va en píxeles por segundo
 * y no depende de lo rápido que gire el hilo.
 * La aceleración sale del empuje y del ángulo: sin empuje no hay aceleración y la velocidad se mantiene (inercia).
 * El giro lo manda el jugador con girar(): es la velocidad angular de cada paso.
 */
public class DinamicObject implements Runnable {
    private static final int MS = 16;                  // ~60 pasos por segundo
    private static final double DT_MAX = 0.5;          // segundos; si pasa más, se recorta (como en el motor del profesor)
    private static final double NANOS_POR_SEGUNDO = 1_000_000_000.0;

    private final String idImagen;   // id de la hoja en el Catalogo: el modelo no tiene la imagen, solo su id
    // volatile: lo escribe el hilo del objeto y lo lee el hilo de la vista (a través del presenter)
    private volatile DatosDinamicObject datos;
    // volatile: lo cambia stop() desde otro hilo y lo lee el bucle de run()
    private volatile boolean running;

    // volatile: lo escribe girar() desde el hilo de la ventana y lo lee integrar() en el hilo del objeto.
    // Es la velocidad angular que pide el jugador, en grados por segundo (negativa = izquierda, 0 = no gira).
    // Va aparte de "datos" para que solo el hilo del objeto escriba "datos" y no se pierda ninguna orden.
    private volatile double giro;
    // volatile: lo escribe empujar() desde el hilo de la ventana y lo lee integrar() en el hilo del objeto.
    // Es el empuje del motor en píxeles por segundo al cuadrado (0 = motor apagado). Va aparte de "datos" por lo mismo que giro.
    private volatile double empuje;

    /**
     * @param x,y   posición inicial en píxeles
     * @param vx,vy velocidad inicial en píxeles por segundo
     * @param angle ángulo inicial en grados
     * El motor empieza apagado y no gira: lo cambian empujar() y girar().
     */
    public DinamicObject(String idImagen, double x, double y, double vx, double vy, double angle) {
        this.idImagen = idImagen;
        this.datos = new DatosDinamicObject(System.nanoTime(), x, y, vx, vy, 0, 0, angle);   // accX y accY empiezan en 0: se calculan en cada paso
        this.running = false;
    }

    /** Arranca el hilo. Si ya está en marcha no hace nada (evita crear dos hilos). */
    public void start() {
        if (!running) {
            running = true;
            // El reloj empieza ahora: si estuvo parado, el dt del primer paso no cuenta todo el tiempo de la pausa
            DatosDinamicObject d = datos;
            datos = new DatosDinamicObject(System.nanoTime(), d.posX(), d.posY(), d.speedX(), d.speedY(), d.accX(), d.accY(),d.angle());
            new Thread(this, "dinamic-object").start();
        }
    }

    /** Para el hilo; el objeto se queda donde está y Iniciar lo reanuda. */
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

    /**
     * Un paso de movimiento. Devuelve los datos nuevos; no toca los antiguos.
     * Aceleración: sale del empuje y del ángulo actual (accX = empuje·cos, accY = empuje·sin).
     * Movimiento (MRUA): v1 = v0 + a·dt  y  x1 = x0 + (v0 + v1)/2 · dt.
     * Giro: el ángulo avanza con la velocidad angular que pide el jugador: ángulo1 = ángulo0 + giro·dt.
     */
    private DatosDinamicObject integrar(DatosDinamicObject d) {
        long ahora = System.nanoTime();
        double dt = Math.min((ahora - d.timeStamp()) / NANOS_POR_SEGUNDO, DT_MAX);

        double empuje = this.empuje;     // se lee una sola vez, como giro: otro hilo puede cambiarlo en cualquier momento

        double accX = Math.cos(Math.toRadians(d.angle())) * empuje;
        double accY = Math.sin(Math.toRadians(d.angle())) * empuje;

        double nuevaVx = d.speedX() + accX * dt;
        double nuevaVy = d.speedY() + accY * dt;

        double nuevaX = d.posX() + (d.speedX() + nuevaVx) * 0.5 * dt;   // velocidad media
        double nuevaY = d.posY() + (d.speedY() + nuevaVy) * 0.5 * dt;

        // Giro: se lee "giro" una sola vez (otro hilo puede cambiarlo en cualquier momento)
        double nuevaVelAng = giro;                            // velocidad angular de este paso, en grados por segundo
        double nuevoAng = d.angle() + nuevaVelAng * dt;       // ángulo + velocidad · tiempo

        return new DatosDinamicObject(ahora, nuevaX, nuevaY, nuevaVx, nuevaVy, accX, accY,nuevoAng);
    }

    public String getIdImagen() { return idImagen; }
    public double getX() { return datos.posX(); }
    public double getY() { return datos.posY(); }
    public double getAngle() { return datos.angle(); }

    /**
     * Pide al objeto que gire a esa velocidad hasta que se le diga otra cosa.
     * Se llama desde otro hilo (el de la ventana): solo guarda la orden, la aplica el hilo del objeto en el siguiente paso.
     *
     * @param gradosPorSegundo velocidad angular: negativa gira a la izquierda, positiva a la derecha, 0 deja de girar
     */
    public void girar(double gradosPorSegundo) {
        this.giro = gradosPorSegundo;
    }

    /**
     * Pide al objeto que empuje con esa fuerza hasta que se le diga otra cosa.
     * Se llama desde otro hilo (el de la ventana): solo guarda la orden, la aplica el hilo del objeto en el siguiente paso.
     * El empuje va en la dirección a la que mira el objeto (ver integrar).
     *
     * @param empuje fuerza en píxeles por segundo al cuadrado; 0 apaga el motor
     */
    public void empujar(double empuje){
        this.empuje = empuje;
    }

}
