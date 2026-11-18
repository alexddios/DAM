package model;

import config.Config;
import config.WaitMode;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Un tipo de recurso: una estantería con su stock.
 * Cómo se protege depende de la configuración:
 *  - Sincronización SÍ / NO          -> si se usa synchronized al modificar el stock.
 *  - Control de stock SÍ / NO        -> si se respetan los límites mín / máx.
 *  - Tipo de control sleep / wait    -> cómo espera el hilo si no puede seguir.
 */
public class Resource {

    /** Cada cuánto vuelve a mirar un hilo en el modo "sleep". */
    private static final int POLL_MS = 20;

    private final String name;
    private final boolean stockControl;
    private final boolean synchronizedAccess;
    private final WaitMode waitMode;
    private final int min;
    private final int max;

    // volatile: los hilos que esperan con sleep tienen que ver el valor nuevo.
    // OJO: volatile NO hace que "stock++" sea seguro; para eso hace falta synchronized.
    private volatile int stock = 0;

    // Cuántas operaciones se han hecho de verdad. Con ellas sabemos cuál DEBERÍA ser el stock.
    private final AtomicInteger produced = new AtomicInteger();
    private final AtomicInteger consumed = new AtomicInteger();

    public Resource(String name, Config c) {
        this.name = name;
        this.stockControl = c.stockControl();
        this.synchronizedAccess = c.synchronizedAccess();
        this.waitMode = c.waitMode();
        this.min = c.stockMin();
        this.max = c.stockMax();
    }

    // =====================================================================
    // PRODUCIR
    // =====================================================================

    public void produce() throws InterruptedException {
        if (synchronizedAccess) {
            produceSynchronized();
        } else {
            produceNotSynchronized();
        }
    }

    private boolean canProduce() {
        return stock < max;
    }

    /** Con sincronización: comprobar y sumar ocurre de golpe, sin que nadie se cuele. */
    private void produceSynchronized() throws InterruptedException {
        if (!stockControl) {
            synchronized (this) {
                stock++;
                produced.incrementAndGet();
            }
            return;
        }

        if (waitMode == WaitMode.WAIT_NOTIFY) {
            synchronized (this) {
                while (!canProduce()) {
                    wait();              // lleno: me duermo y suelto el candado
                }
                stock++;
                produced.incrementAndGet();
                notifyAll();             // despierto a los consumidores
            }
        } else {
            // SLEEP: miro con el candado; si no puedo, suelto el candado y duermo un poco
            while (true) {
                synchronized (this) {
                    if (canProduce()) {
                        stock++;
                        produced.incrementAndGet();
                        return;
                    }
                }
                Thread.sleep(POLL_MS);
            }
        }
    }

    /** Sin sincronización: varios hilos pueden pisarse y el stock sale mal. */
    private void produceNotSynchronized() throws InterruptedException {
        if (stockControl) {
            if (waitMode == WaitMode.WAIT_NOTIFY) {
                // wait() exige tener el candado, así que solo lo cogemos para esperar.
                // La suma de después ya va sin protección.
                synchronized (this) {
                    while (!canProduce()) {
                        wait();
                    }
                }
            } else {
                while (!canProduce()) {
                    Thread.sleep(POLL_MS);
                }
            }
        }

        unsafeChange(+1);
        produced.incrementAndGet();

        if (stockControl && waitMode == WaitMode.WAIT_NOTIFY) {
            synchronized (this) {
                notifyAll();
            }
        }
    }

    // =====================================================================
    // CONSUMIR (espejo de producir)
    // =====================================================================

    public void consume() throws InterruptedException {
        if (synchronizedAccess) {
            consumeSynchronized();
        } else {
            consumeNotSynchronized();
        }
    }

    private boolean canConsume() {
        return stock > min;
    }

    private void consumeSynchronized() throws InterruptedException {
        if (!stockControl) {
            synchronized (this) {
                stock--;
                consumed.incrementAndGet();
            }
            return;
        }

        if (waitMode == WaitMode.WAIT_NOTIFY) {
            synchronized (this) {
                while (!canConsume()) {
                    wait();              // vacío: me duermo y suelto el candado
                }
                stock--;
                consumed.incrementAndGet();
                notifyAll();             // despierto a los productores
            }
        } else {
            while (true) {
                synchronized (this) {
                    if (canConsume()) {
                        stock--;
                        consumed.incrementAndGet();
                        return;
                    }
                }
                Thread.sleep(POLL_MS);
            }
        }
    }

    private void consumeNotSynchronized() throws InterruptedException {
        if (stockControl) {
            if (waitMode == WaitMode.WAIT_NOTIFY) {
                synchronized (this) {
                    while (!canConsume()) {
                        wait();
                    }
                }
            } else {
                while (!canConsume()) {
                    Thread.sleep(POLL_MS);
                }
            }
        }

        unsafeChange(-1);
        consumed.incrementAndGet();

        if (stockControl && waitMode == WaitMode.WAIT_NOTIFY) {
            synchronized (this) {
                notifyAll();
            }
        }
    }

    /**
     * Modifica el stock SIN protección: leer, (otro hilo puede colarse aquí), escribir.
     * El Thread.yield() cede el turno a propósito para que la condición de carrera
     * se vea en pocos segundos; sin él también pasa, pero muy de vez en cuando.
     */
    private void unsafeChange(int delta) {
        int value = stock;
        Thread.yield();
        stock = value + delta;
    }

    // =====================================================================
    // CONSULTAS
    // =====================================================================

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    /** El stock que habría si nadie se hubiera pisado: producidos - consumidos. */
    public int getExpectedStock() {
        return produced.get() - consumed.get();
    }

    /** Lee stock y esperado a la vez (con sincronización activada nunca se ve un valor a medias). */
    public synchronized String getInfo() {
        int s = stock;
        int expected = getExpectedStock();
        String line = String.format("%-12s stock: %4d   (esperado: %4d)", name, s, expected);
        if (s != expected) {
            line += "   <-- DESCUADRE: condición de carrera";
        }
        if (stockControl && (s > max || s < min)) {
            line += "   <-- FUERA DE LÍMITES";
        }
        return line;
    }
}
