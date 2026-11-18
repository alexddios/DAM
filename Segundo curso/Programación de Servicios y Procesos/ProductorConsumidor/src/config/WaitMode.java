package config;

/** Cómo espera un hilo cuando no puede producir o consumir. */
public enum WaitMode {
    /** Mira, duerme un rato con Thread.sleep() y vuelve a mirar (espera activa). */
    SLEEP,
    /** Se duerme con wait() hasta que otro hilo le despierte con notifyAll(). */
    WAIT_NOTIFY
}
