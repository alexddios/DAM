# Práctica: Simulador Productor – Consumidor

**Módulo:** Programación de Servicios y Procesos (2º DAM)
**Tema:** Programación multihilo en Java
**Herramientas:** Java + Swing, IntelliJ IDEA

---

## 1. Contexto

El problema del **productor – consumidor** es un clásico de la programación concurrente. Unos hilos (**productores**) generan recursos y los dejan en un almacén compartido, y otros hilos (**consumidores**) los retiran. Todos trabajan **a la vez**, así que hay que resolver dos problemas:

1. **Esperar cuando no se puede seguir.** Un productor no debe añadir si el almacén está lleno, y un consumidor no debe retirar si está vacío.
2. **Evitar condiciones de carrera.** Si dos hilos modifican el stock a la vez sin coordinarse, el resultado es incorrecto.

En esta práctica construirás un **simulador gráfico** que permita configurar el escenario, ejecutarlo y observar qué ocurre con cada combinación de opciones, incluidas las que provocan errores a propósito.

## 2. Objetivos de aprendizaje

Al terminar la práctica deberás saber:

- Crear hilos con `Runnable` y `Thread`, y arrancarlos con `start()`.
- Proteger un recurso compartido con `synchronized`.
- Coordinar hilos con `wait()` y `notifyAll()`, y diferenciarlo de esperar con `Thread.sleep()`.
- Usar `volatile` para que los cambios de una variable sean visibles entre hilos.
- Detener hilos con `interrupt()` y esperar a que terminen con `join()`.
- Actualizar una interfaz Swing desde otro hilo de forma segura (`SwingUtilities.invokeLater`).
- Organizar una aplicación con la arquitectura **Modelo – Vista – Presenter (MVP)**.

## 3. Descripción general

La aplicación muestra una ventana con dos zonas:

- **Izquierda: Panel de control.** Los ajustes de la simulación y los botones *Iniciar*, *Pausa* y *Detener*.
- **Derecha: Panel de datos.** El estado de la simulación, actualizado continuamente.

El usuario configura los parámetros y pulsa *Iniciar*. Se crean los hilos productores y consumidores, que trabajan sobre uno o varios recursos compartidos. Mientras la simulación está en marcha, los ajustes no se pueden modificar.

## 4. Panel de control

El panel debe contener **todas** las opciones siguientes:

| Opción | Tipo de control | Significado |
|---|---|---|
| Nº productores | Campo numérico | Cuántos hilos productores se crean |
| Nº consumidores | Campo numérico | Cuántos hilos consumidores se crean |
| Nº tipos de recurso | Campo numérico | Cuántos recursos (almacenes) distintos hay |
| Tipo de recurso de los productores | Fijo / Variable | **Fijo:** cada productor trabaja siempre con el mismo recurso, repartidos en orden. **Variable:** en cada producción elige un recurso al azar |
| Tipo de recurso de los consumidores | Fijo / Variable | Igual que el anterior, para los consumidores |
| Tiempo producir | mín – máx (ms) | Cada producción tarda un tiempo aleatorio dentro de ese rango |
| Tiempo consumir | mín – máx (ms) | Cada consumición tarda un tiempo aleatorio dentro de ese rango |
| Recursos a producir | Ilimitados / mín – máx | Cuántas unidades produce cada productor en total. Si es un rango, a cada productor se le asigna un número aleatorio dentro de él y, al alcanzarlo, termina |
| Recursos a consumir | Ilimitados / mín – máx | Igual, para los consumidores |
| Control de stock | Sí / No | Si es **No**, se ignoran los límites: el stock puede superar el máximo o bajar del mínimo (incluso ser negativo) |
| Tipo de control de stock | sleep / wait-notifyAll | Cómo espera un hilo que no puede continuar (ver apartado 5) |
| Límite de recursos | mín / máx | Stock mínimo y máximo de cada recurso (solo con control de stock) |
| Sincronización producir / consumir | Sí / No | Si es **No**, el stock se modifica sin `synchronized` y deben poder observarse condiciones de carrera |

**Botones:**

- **Iniciar:** valida los ajustes, crea los hilos y los arranca. Si la simulación está en pausa, la reanuda.
- **Pausa:** los hilos dejan de producir y consumir hasta que se reanude.
- **Detener:** interrumpe todos los hilos y espera a que terminen.

Los campos que no aplican deben desactivarse. Por ejemplo, los límites con el control de stock en *No*, o el rango de recursos a producir con *Ilimitados* marcado.

**Validación:** si algún valor no es un número entero válido, o no cumple su mínimo (por ejemplo, un máximo menor que su mínimo), se muestra un mensaje de error y **no se inicia nada**.

## 5. Comportamiento de los recursos

Cada recurso guarda su **stock**. Producir suma 1 y consumir resta 1.

Con **control de stock**:

- Un productor solo puede producir si `stock < máximo`.
- Un consumidor solo puede consumir si `stock > mínimo`.

Si no puede, el hilo **espera**. Cómo espera depende del tipo de control:

- **sleep:** comprueba la condición; si no se cumple, duerme un tiempo corto y vuelve a comprobar (espera activa).
- **wait-notifyAll:** se bloquea con `wait()`. Cada vez que un hilo modifica el stock, llama a `notifyAll()` para despertar a los que esperan, que vuelven a comprobar su condición.

Con **sincronización activada**, comprobar la condición y modificar el stock debe hacerse de forma **atómica**, dentro de `synchronized`.

Con **sincronización desactivada**, el stock se modifica sin protección (leer, calcular, escribir). Para que el error se vea, el recurso debe llevar también la cuenta de cuántas producciones y consumiciones se han hecho, y comparar el stock real con el **esperado** (producidos − consumidos). Si no coinciden, la vista debe indicar el **descuadre**.

## 6. Panel de datos

Se actualiza cada **100 ms** y muestra:

- **config.Estado** de la simulación: Parado, En marcha, En pausa, Finalizado o Detenido.
- **Stock total**, **total producido** y **total consumido**.
- **Por cada recurso:** stock actual, stock esperado y un aviso si hay descuadre o si el stock está fuera de los límites.
- **Por cada hilo:** nombre, unidades producidas o consumidas (con su objetivo, si lo tiene) y qué está haciendo (produciendo, esperando en un recurso, en pausa, terminado…).

Cuando todos los hilos hayan terminado su trabajo (recursos limitados), el estado debe pasar a **Finalizado** automáticamente.

## 7. Requisitos técnicos obligatorios

1. **Arquitectura MVP** con tres paquetes (`model`, `view`, `presenter`) y una interfaz por capa: `IModel`, `IView`, `IPresenter`.
   - La Vista y el Modelo **no se conocen**: todo pasa por el Presenter.
   - Los ajustes viajan de la Vista al Modelo en un único objeto de configuración, definido en un paquete propio para que ninguna de las dos capas dependa de la otra.
2. **Cada productor y cada consumidor es un hilo** (clase que implementa `Runnable`).
3. **La Vista también es un hilo:** implementa `Runnable` y su `run()` es un bucle que refresca los datos cada 100 ms. Como los componentes Swing solo pueden tocarse desde el hilo de Swing, el pintado se hace con `SwingUtilities.invokeLater`.
4. **Uso de `join()`:**
   - Al detener: primero `interrupt()` a todos los hilos y después `join()` para esperar a que terminen.
   - Un hilo **vigilante** hace `join()` de todos los productores y consumidores para detectar cuándo han terminado y poner el estado en *Finalizado*.
5. **Los botones se gestionan en una clase aparte** que implemente `ActionListener`. Se distingue qué botón se ha pulsado mediante su *action command*.
6. **No se pueden usar expresiones lambda** (`->`) ni referencias a métodos (`::`) en ningún sitio. Cuando haga falta un `Runnable` o un `ActionListener`, se escribe una clase.
7. **`Main` no es un hilo ni implementa `Runnable`:** su `main()` solo crea el Modelo, la Vista y el Presenter y arranca la aplicación.
8. Las variables que un hilo modifica y otro lee deben ser `volatile` (o estar protegidas por `synchronized`).
9. Los hilos se crean como **daemon**, para que al cerrar la ventana el programa termine.

## 8. Estructura orientativa

```
ProductorConsumidor/
└─ src/
   ├─ Main.java
   ├─ config/      Config, WaitMode
   ├─ model/       IModel, model.Model, model.Resource, model.Producer, model.Consumer, model.Watcher
   ├─ presenter/   IPresenter, Presenter
   └─ view/        IView, View, ControlPanel, ControlListener, DetailPanel, PaintTask
```

## 9. Fases recomendadas

Haz la práctica por partes, comprobando que cada una funciona antes de pasar a la siguiente.

1. **El recurso.** Una clase `Resource` con un stock, `producir()` y `consumir()` usando `synchronized`, `wait()` y `notifyAll()`, con un límite máximo. Pruébala desde un `main` con un productor y un consumidor.
2. **Productores y consumidores.** Clases `Runnable` con su bucle `run()`, su tiempo de espera y su contador. Lanza varios desde un `main` y comprueba que el stock nunca se sale de los límites.
3. **Detener con `interrupt()` y `join()`.** Que los hilos terminen limpiamente al interrumpirlos.
4. **El Modelo.** Una clase que cree los recursos y los hilos a partir de una configuración, y que permita iniciar, pausar y detener. Añade el hilo vigilante.
5. **Las opciones avanzadas.** Varios tipos de recurso, fijo / variable, rangos aleatorios, control de stock Sí/No, sleep frente a wait-notifyAll y sincronización Sí/No (con el cálculo del stock esperado).
6. **MVP.** Las interfaces y el Presenter.
7. **La Vista.** La ventana con los dos paneles, la Vista como hilo de refresco y el pintado con `invokeLater`.
8. **El panel de control.** Todos los campos, la validación, el activado y desactivado de campos, y la clase `ActionListener` de los botones.

## 10. Pruebas que debe superar

| # | Configuración | Resultado esperado |
|---|---|---|
| 1 | 2 productores, 2 consumidores, 1 recurso, límite 0 / 10, todo en Sí | El stock sube y baja sin salirse nunca de 0 – 10 |
| 2 | 0 consumidores | El stock llega al máximo y los productores se quedan esperando |
| 3 | 0 productores | Los consumidores se quedan esperando con el stock en el mínimo |
| 4 | Control de stock en No, más consumidores que productores | El stock se vuelve negativo |
| 5 | Sincronización en No, tiempos a 0, 5 productores y 5 consumidores | Aparece un descuadre entre el stock real y el esperado |
| 6 | Recursos a producir y a consumir limitados, con cantidades que cuadren | Todos los hilos terminan y el estado pasa a Finalizado |
| 7 | Pulsar Pausa y luego Iniciar | Los contadores se congelan y después continúan |
| 8 | Pulsar Detener | Todos los hilos terminan y el estado pasa a Detenido |
| 9 | 3 productores fijos y 3 recursos, sin consumidores ni control de stock | Cada recurso recibe solo lo que produce su productor |
| 10 | Escribir «abc» o un máximo menor que el mínimo y pulsar Iniciar | Aparece un mensaje de error y no se inicia nada |

## 11. Preguntas de comprensión

Responde brevemente en la memoria de la práctica:

1. ¿Por qué la Vista no llama directamente al Modelo?
2. ¿Qué diferencia hay entre `wait()`, `Thread.sleep()` y `join()`?
3. ¿Por qué la condición de espera se comprueba en un `while` y no en un `if`?
4. ¿Qué ocurre si se quita `synchronized`? ¿Por qué `volatile` no basta para que `stock++` sea seguro?
5. ¿Por qué la Vista no puede modificar los componentes Swing directamente desde su propio hilo?
6. ¿Qué ventaja tiene `wait-notifyAll` frente a `sleep` como forma de esperar?
7. ¿Para qué sirve `join()` al detener la simulación? ¿Qué podría pasar si no se usara?
8. Con todos los productores en modo *Variable* y sin consumidores, ¿por qué acaban todos bloqueados aunque algún recurso tenga hueco?

## 12. Entrega

- El proyecto de IntelliJ completo, que compile y se ejecute desde `Main`.
- El código comentado en las partes relacionadas con hilos.
- Las respuestas a las preguntas del apartado 11.
