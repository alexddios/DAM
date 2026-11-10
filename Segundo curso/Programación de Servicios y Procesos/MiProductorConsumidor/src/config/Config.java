package config;

/**
 * Todos los ajustes del panel de control en un solo objeto.
 * La Vista lo rellena, el Presenter lo pasa y el Modelo lo usa.
 * Está en su propio paquete para que la Vista y el Modelo no se conozcan.
 * Es inmutable: varios hilos pueden leerlo a la vez sin sincronizar.
 */
public record Config(
        int numProductores,
        int numConsumidores,
        int numRecursos,
        boolean productoresFijos,     // true = fijo, false = variable
        boolean consumidoresFijos,    // true = fijo, false = variable

        int tiempoProducirMin,        // ms
        int tiempoProducirMax,
        int tiempoConsumirMin,        // ms
        int tiempoConsumirMax,

        boolean producirIlimitado,
        int producirMin,
        int producirMax,
        boolean consumirIlimitado,
        int consumirMin,
        int consumirMax,

        boolean controlStock,
        WaitMode modoEspera,          // SLEEP o WAIT_NOTIFY
        int stockMin,
        int stockMax,

        boolean sincronizado
) {
}