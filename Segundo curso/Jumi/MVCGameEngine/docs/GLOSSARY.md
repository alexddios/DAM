# Glosario de Conceptos - MVCGameEngine

**[English](GLOSSARY_EN.md)** | **Español**

Este documento proporciona un glosario de los conceptos más importantes utilizados en el proyecto MVCGameEngine, un motor de física 2D en tiempo real implementado en Java.

## Arquitectura y Patrones de Diseño

### MVC (Model-View-Controller)
Patrón arquitectónico que separa la aplicación en tres componentes principales:
- **Model**: Gestiona el estado del juego y la lógica de simulación
- **View**: Maneja la presentación visual y renderizado
- **Controller**: Coordina la comunicación entre Model y View, procesando entrada del usuario

### Model
Componente que representa la capa de lógica del juego. Gestiona todas las entidades, la física de la simulación, y el estado del mundo. Ejecuta en su propio hilo para mantener la simulación independiente del renderizado.

### View
Capa de presentación que maneja el renderizado visual utilizando Java Swing. Solicita instantáneas del estado del juego a través del Controller y las renderiza en pantalla. No contiene lógica de simulación.

### Controller
Coordinador central de la arquitectura MVC. Conecta Model y View, gestiona la inicialización del motor, procesa comandos de usuario, y proporciona acceso a instantáneas del estado para el renderizado.

## Cuerpos (Bodies)

### AbstractBody
Clase base abstracta para todos los cuerpos en la simulación. Define:
- Identificador único (`entityId`)
- Estado del ciclo de vida (`BodyState`: STARTING, ALIVE, DEAD)
- Referencia al motor de física (`PhysicsEngine`)
- Tamaño (`size`)
- Tiempo de vida máximo (opcional)
- Contadores estáticos para seguimiento de cuerpos creados, vivos y muertos

### DynamicBody
Entidad dinámica que se mueve y rota según las leyes de física. Características:
- Ejecuta en su propio hilo dedicado
- Posee un `PhysicsEngine` que calcula su movimiento
- Actualiza continuamente su posición, velocidad y aceleración
- Interactúa con el mundo (colisiones, rebotes en bordes)
- Ejemplos: asteroides, proyectiles

### StaticBody
Entidad estática que no se mueve durante la simulación. Características:
- No tiene hilo propio
- Utiliza `NullPhysicsEngine` (sin cálculos físicos)
- Posición, rotación y tamaño fijos
- Se usa para obstáculos, decoración visual estática
- Ejemplos: planetas, obstáculos decorativos

### PlayerBody
Entidad especial que extiende `DynamicBody` y representa al jugador. Características:
- Control mediante teclado (empuje, rotación, disparo)
- Sistema de armas con múltiples slots
- Parámetros configurables: fuerza máxima de empuje, aceleración angular
- Identificador único de jugador (`playerId`)

### DecoBody
Cuerpo puramente decorativo sin física ni lógica de juego. Se utiliza para elementos visuales que no interactúan con el mundo (elementos de fondo, efectos visuales temporales).

### PhysicsBody
Interfaz que marca cuerpos que tienen comportamiento físico. Proporciona métodos por defecto para:
- Obtener valores físicos (`PhysicsValuesDTO`)
- Aplicar movimiento
- Gestionar rebotes en los bordes del mundo

### BodyState
Enumeración que define los estados del ciclo de vida de un cuerpo:
- **STARTING**: Cuerpo creado pero no activado
- **ALIVE**: Cuerpo activo en la simulación
- **DEAD**: Cuerpo marcado para eliminación

### BodyDTO
Objeto de Transferencia de Datos (DTO) que contiene información inmutable sobre un cuerpo para transferencia segura entre las capas Model y View. Incluye entityId, assetId, size, posición (x, y) y ángulo.

## Motor de Física (Physics Engine)

### PhysicsEngine
Interfaz que define el contrato para motores de física. Responsabilidades:
- Calcular nuevos valores físicos basados en el tiempo transcurrido
- Gestionar rebotes en los límites del mundo
- Actualizar valores de empuje y aceleración angular

### BasicPhysicsEngine
Implementación concreta de `PhysicsEngine` que aplica física básica:
- Integración MRUA (Movimiento Rectilíneo Uniformemente Acelerado)
- Cálculo de velocidad: v₁ = v₀ + a·Δt
- Cálculo de posición: x₁ = x₀ + v_avg·Δt
- Aplicación de empuje según el ángulo de rotación
- Gestión de fricción y elasticidad en rebotes

### NullPhysicsEngine
Motor de física "nulo" usado por `StaticBody`. No realiza cálculos físicos, manteniendo valores constantes.

### AbstractPhysicsEngine
Clase base abstracta que proporciona implementación común para motores de física, incluyendo gestión de valores físicos y rebotes.

### PhysicsValuesDTO
Objeto inmutable que encapsula el estado físico completo de un cuerpo en un momento específico:
- **timeStamp**: Marca temporal en nanosegundos
- **posX, posY**: Posición en el espacio 2D
- **speedX, speedY**: Velocidad (componentes x, y)
- **accX, accY**: Aceleración (componentes x, y)
- **angle**: Ángulo de rotación en grados
- **angularSpeed**: Velocidad angular (grados/segundo)
- **angularAcc**: Aceleración angular (grados/segundo²)
- **thrust**: Fuerza de empuje aplicada

## Sistema de Eventos y Acciones

### ActionDTO (Data Transfer Object)
Objeto de transferencia de datos que encapsula una acción a ejecutar:
- **type**: Tipo de acción (`ActionType`)
- **executor**: Ejecutor que procesará la acción (`ActionExecutor`)
- **priority**: Prioridad de ejecución (`ActionPriority`)

### EventDTO (Data Transfer Object)
Objeto que representa un evento en la simulación:
- **entity**: Entidad que genera el evento
- **eventType**: Tipo de evento (`EventType`)

### ActionType
Enumeración de tipos de acciones posibles en el sistema (ej: mover, rotar, disparar).

### EventType
Enumeración de tipos de eventos que pueden ocurrir en la simulación (ej: colisión, salida de límites).

### ActionExecutor
Interfaz que define objetos capaces de ejecutar acciones en el modelo.

### ActionPriority
Enumeración que define niveles de prioridad para la ejecución de acciones.

## Estados del Sistema

### ModelState
Enumeración que define los estados del ciclo de vida del Model:
- **STARTING**: Inicializando
- **ALIVE**: Ejecutando simulación
- **STOPPED**: Detenido

### EngineState
Enumeración que define los estados del motor completo (Controller).

## Mundo y Generación

### WorldDefinition
Objeto que define la configuración completa de un mundo:
- Dimensiones del mundo (`worldWidth`, `worldHeight`)
- Catálogo de assets visuales (`AssetCatalog`)
- Definiciones de fondo, decoradores, cuerpos gravitacionales
- Configuraciones de asteroides, naves espaciales, armas

### SceneGenerator
Clase responsable de generar la escena estática inicial basándose en una `WorldDefinition`. Crea y coloca todos los cuerpos estáticos y decoradores.

### LifeGenerator
Generador automático de cuerpos dinámicos que mantiene la actividad en la simulación, creando nuevos cuerpos dinámicos cuando es necesario y gestionando la creación de jugadores.

## Sistema de Armas

### Weapon
Interfaz/clase base para sistemas de armas que pueden disparar proyectiles.

### BasicWeapon
Implementación básica de un arma con:
- Configuración de proyectil (`WeaponDto`)
- Control de cadencia de disparo
- Sistema de solicitud de disparo

### WeaponDto
Objeto de configuración para armas que define propiedades del proyectil y comportamiento del arma.

## Assets y Renderizado

### AssetCatalog
Catálogo que organiza y gestiona todos los recursos visuales (sprites, imágenes) utilizados en el juego.

### AssetType
Enumeración de tipos de assets (backgrounds, solid_bodies, space_decors, spaceship, weapons, etc.).

### EntityInfoDTO
Objeto de transferencia de datos que contiene información para renderizar una entidad estática:
- ID de entidad
- ID de asset
- Tamaño
- Posición (x, y)
- Ángulo de rotación

### DBodyInfoDTO
Extensión de `EntityInfoDTO` para entidades dinámicas, añadiendo:
- Marca temporal
- Velocidad (speedX, speedY)
- Aceleración (accX, accY)

### Renderer
Componente que maneja el bucle de renderizado, dibujando el estado actual del juego en pantalla utilizando las instantáneas proporcionadas por el Controller.

## Utilidades

### DoubleVector
Clase de utilidad para matemáticas vectoriales 2D, proporcionando operaciones vectoriales comunes.

### Images
Sistema de carga y caché de imágenes para gestión eficiente de recursos gráficos.

### Fx (Effects)
Sistema de efectos visuales para animaciones y partículas.

## Conceptos de Física

### Thrust (Empuje)
Fuerza aplicada a una entidad en la dirección de su ángulo actual. Se utiliza para propulsar naves espaciales y otros objetos dinámicos.

### Angular Velocity (Velocidad Angular)
Velocidad de rotación de una entidad, medida en grados por segundo.

### Angular Acceleration (Aceleración Angular)
Cambio en la velocidad angular por unidad de tiempo, medida en grados por segundo cuadrado.

### Elasticity (Elasticidad)
Propiedad que determina cuánta energía cinética se conserva durante una colisión o rebote (valor entre 0 y 1).

### Friction (Fricción)
Fuerza que se opone al movimiento, reduciendo gradualmente la velocidad de las entidades.

## Programación Concurrente

### Thread-Safe Collections
Colecciones seguras para hilos (como `ConcurrentHashMap`) utilizadas para gestionar entidades en un entorno multihilo.

### Volatile Variables
Variables marcadas como `volatile` para garantizar visibilidad entre hilos (ej: `ModelState`, `BodyState`, `EngineState`). Asegura que todos los hilos vean siempre el valor más reciente.

### Immutable Objects
Objetos inmutables como `PhysicsValuesDTO` y otros DTOs que garantizan seguridad en concurrencia al no permitir modificación después de la creación. Esto permite compartirlos entre hilos sin necesidad de sincronización.

---

## Flujo de Ejecución

1. **Inicialización**: `Main` crea Controller, Model y View
2. **Carga de Assets**: Controller carga recursos visuales en View
3. **Generación de Escena**: SceneGenerator crea la escena estática (cuerpos estáticos y decoradores) basándose en WorldDefinition
4. **Activación**: Model y View se activan, iniciando sus bucles de ejecución
5. **Generación de Vida**: LifeGenerator crea jugadores y gestiona la generación programática de cuerpos dinámicos
6. **Bucle de Simulación**: Los cuerpos DynamicBody calculan física en hilos separados
7. **Bucle de Renderizado**: View solicita instantáneas y renderiza el estado actual
8. **Procesamiento de Entrada**: Controller traduce entrada de teclado en acciones del Model
9. **Procesamiento de Eventos**: Model gestiona eventos (colisiones, rebotes) y ejecuta acciones mediante el sistema de ActionDTO/EventDTO

Este glosario proporciona una base sólida para entender la arquitectura y conceptos fundamentales del proyecto MVCGameEngine.