package config;

/**
 * Todos los ajustes del panel de control en un solo objeto.
 * La Vista lo rellena, el Presenter lo pasa y el Modelo lo usa.
 * Está en su propio paquete para que la Vista y el Modelo no se conozcan entre sí.
 *
 * Es un "record": Java crea solo el constructor y los getters (producers(), consumers()...).
 */
public record Config(
        int producers,              // Nº productores
        int consumers,              // Nº consumidores
        int resourceTypes,          // Nº tipos de recurso
        boolean producerFixedType,  // Tipo de recurso productores: fijo (true) / variable (false)
        boolean consumerFixedType,  // Tipo de recurso consumidores: fijo (true) / variable (false)

        int produceTimeMin,         // Tiempo producir (ms): mín - máx
        int produceTimeMax,
        int consumeTimeMin,         // Tiempo consumir (ms): mín - máx
        int consumeTimeMax,

        boolean produceUnlimited,   // Recursos a producir: ilimitados / mín - máx
        int produceMin,
        int produceMax,
        boolean consumeUnlimited,   // Recursos a consumir: ilimitados / mín - máx
        int consumeMin,
        int consumeMax,

        boolean stockControl,       // Control de stock: sí / no
        WaitMode waitMode,          // Tipo de control: sleep / wait-notifyAll
        int stockMin,               // Límite de recursos: mín / máx
        int stockMax,

        boolean synchronizedAccess  // Sincronización producir/consumir: sí / no
) {
}
