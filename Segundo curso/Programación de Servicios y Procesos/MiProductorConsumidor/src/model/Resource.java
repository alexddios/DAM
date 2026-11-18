package model;

import config.WaitMode;

/**
 * Recurso compartido (almacén) sobre el que trabajan productores y consumidores.
 * Es el único objeto que tocan varios hilos a la vez, así que aquí está toda
 * la sincronización: synchronized, wait() y notifyAll().
 *
 * Según la configuración, producir y consumir siguen uno de tres caminos:
 *  - Wait:    synchronized + wait()/notifyAll().
 *  - Sleep:   espera activa: comprueba, y si no puede, duerme SIN el candado.
 *  - SinSync: sin protección, para provocar condiciones de carrera a propósito.
 */
public class Resource {
    // volatile: en modo sin sincronización otros hilos leen el stock sin candado,
    // y así ven siempre el último valor escrito (visibilidad, NO atomicidad).
    private volatile int stock;
    private final int minimo;
    private final int maximo;
    private final String nombre;
    private final boolean controlStock;
    private final WaitMode modoEspera;
    private final boolean sincronizado;
    // Contadores para calcular el stock esperado. Se modifican SIEMPRE con el
    // candado, incluso en modo sin sincronización, para que sean fiables.
    private int producidos;
    private int consumidos;


    public Resource(int minimo,
                    int maximo,
                    String nombre,
                    boolean controlStock,
                    WaitMode modoEspera,
                    boolean sincronizado) {
        this.maximo = maximo;
        this.minimo = minimo;
        this.stock = minimo;
        this.nombre = nombre;
        this.controlStock = controlStock;
        this.modoEspera = modoEspera;
        this.sincronizado = sincronizado;
        this.producidos = 0;
        this.consumidos = 0;
    }

    /**
     * Elige cómo producir según la configuración.
     * NO es synchronized: si lo fuera, el modo sleep dormiría con el candado
     * cogido y nadie podría consumir (bloqueo).
     */
    public void producir() throws InterruptedException {
        if (!sincronizado) {
            producirSinSync();
        } else if (modoEspera == WaitMode.SLEEP) {
            producirSleep();
        } else {
            producirWait();
        }
    }

    /**
     * Modo wait-notifyAll. synchronized hace que comprobar la condición y
     * modificar el stock sea atómico.
     */
    private synchronized void producirWait() throws InterruptedException {
        // while y no if: al despertar, otro hilo puede haber llenado el
        // recurso antes que este, así que hay que volver a comprobar.
        while (controlStock && stock >= maximo) {
            wait();             // suelta el candado y se duerme hasta un notifyAll()
        }
        stock++;
        producidos++;
        notifyAll();            // despierta a los que esperan (p. ej. consumidores con stock vacío)
    }

    /**
     * Modo sleep (espera activa). Comprueba y modifica dentro de un bloque
     * synchronized, pero duerme FUERA de él: Thread.sleep() no suelta el
     * candado, y si durmiera dentro, ningún consumidor podría entrar.
     * No hace falta notifyAll(): aquí nadie está en wait().
     */
    private void producirSleep() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!controlStock || stock < maximo) {
                    stock++;
                    producidos++;
                    return;
                }
            }
            Thread.sleep(50);   // duerme sin el candado y vuelve a comprobar
        }
    }

    /**
     * Modo sin sincronización: provoca condiciones de carrera a propósito.
     * stock++ son tres pasos (leer, calcular, escribir). Si dos hilos los
     * intercalan, uno pisa el resultado del otro y se pierde una unidad.
     * No se puede usar wait() sin el candado, así que la espera es con sleep.
     */
    private void producirSinSync() throws InterruptedException {
        while (controlStock && stock >= maximo) {   // comprobar SIN candado
            Thread.sleep(50);
        }
        int leido = stock;          // 1. leer
        leido = leido + 1;          // 2. calcular
        stock = leido;              // 3. escribir
        contarProduccion();         // el contador SÍ protegido
    }

    /** Suma una producción con el candado, para que el stock esperado sea fiable. */
    private synchronized void contarProduccion() {
        producidos++;
    }

    /** Igual que producir(), pero restando. Tampoco es synchronized por el mismo motivo. */
    public void consumir() throws InterruptedException {
       if (!sincronizado){
           consumirSinSync();
       } else if (modoEspera == WaitMode.SLEEP) {
            consumirSleep();
        } else {
            consumirWait();
        }
    }

    /** Consumir sin protección: misma carrera que en producirSinSync(). */
    private void consumirSinSync() throws InterruptedException{
        while (controlStock && stock <= minimo) {
            Thread.sleep(50);
        }
        int leido = stock;          // 1. leer
        leido = leido-1;            // 2. calcular
        stock = leido;              // 3. escribir
        contarConsumicion();        // el contador SÍ protegido
    }

    /** Suma una consumición con el candado. */
    private synchronized void contarConsumicion(){
        consumidos++;
    }

    /** Modo wait-notifyAll: espera mientras el stock esté en el mínimo. */
    private synchronized void consumirWait() throws InterruptedException {
        while (controlStock && stock <= minimo) {   // while: se vuelve a comprobar al despertar
            wait();                                 // suelta el candado mientras espera
        }
        stock--;
        consumidos++;
        notifyAll();                                // despierta a productores que esperaban hueco
    }

    /** Modo sleep: comprueba con el candado y duerme sin él. */
    private void consumirSleep() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!controlStock || stock > minimo) {
                    stock--;
                    consumidos++;
                    return;
                }
            }
            Thread.sleep(50);   // duerme sin el candado y vuelve a comprobar
        }
    }

    public synchronized int getStock() {
        return stock;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Stock que debería haber si no se hubiera perdido nada: mínimo + producidos - consumidos.
     * synchronized porque lee dos contadores que se modifican con el candado:
     * así se ven actualizados y no se mezcla un valor "de antes" con uno "de después".
     */
    public synchronized int getStockEsperado(){
        return  minimo+producidos-consumidos;
    }

    /** true si el stock real no coincide con el esperado (ha habido una condición de carrera). */
    public boolean hayDescuadre(){
        return getStockEsperado() != stock;
    }

    /** true si el stock se ha salido de los límites (solo puede pasar sin control de stock). */
    public boolean fueraDeLimites(){
        return stock < minimo || stock >maximo;
    }
}
