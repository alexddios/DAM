# MVCGameEngine

**[English](README.md)** | **Español**

Un proyecto educativo en Java que demuestra una arquitectura modular de motor de juegos 2D con simulación de física en tiempo real, diseñado para crear juegos estilo arcade como Asteroids, shoot 'em ups espaciales u otros juegos basados en física.

Este proyecto sirve como una plataforma de aprendizaje integral para comprender patrones de arquitectura de software, programación concurrente, fundamentos de motores de juego y principios de diseño orientado a objetos.

## Qué Hace el Motor

**MVCGameEngine** es un motor de juegos 2D flexible que incluye simulación de física en tiempo real, gestión de entidades y capacidades de renderizado. Aunque la implementación de ejemplo demuestra un escenario de disparos espaciales, el motor está diseñado para soportar varios tipos de juegos estilo arcade:

- **Desarrollo de Juegos Flexible**: Crea diferentes tipos de juegos arcade 2D (disparos espaciales como Asteroids, billar, u otros juegos basados en física)
- **Implementación de Ejemplo**: Incluye un ejemplo de disparos espaciales con asteroides, naves espaciales y proyectiles
- **Cuerpos Dinámicos**: Las entidades se mueven, rotan y colisionan según las reglas de física gobernadas por motores de física intercambiables
- **Interacción del Jugador**: Los usuarios pueden controlar entidades jugador con capacidades de empuje, rotación y disparo usando entradas de teclado
- **Múltiples Motores de Física**: Elige entre diferentes implementaciones de física incluyendo física básica y física nula para comportamientos de simulación variados
- **Generación de Escena**: Define mundos con fondos personalizables, cuerpos estáticos y elementos decorativos
- **Sistemas de Armas**: Framework de armas configurable que soporta diferentes tipos de proyectiles y comportamientos
- **Generación de Vida**: Sistema automático de generación de entidades para mantener la actividad del juego
- **Renderizado Visual**: Renderizado de gráficos en tiempo real usando Java Swing con gestión de recursos para sprites y efectos visuales

El motor se ejecuta continuamente con una arquitectura multihilo, separando el renderizado (Vista), la lógica del juego (Modelo) y el manejo de entrada del usuario (Controlador) para un rendimiento y mantenibilidad óptimos.

## Valor Educativo para Aprender Programación

Este proyecto sirve como un excelente recurso educativo para aprender conceptos de programación fundamentales y avanzados:

### Conceptos Básicos de Programación

1. **Programación Orientada a Objetos (POO)**
   - Jerarquías de herencia (AbstractBody, DynamicBody, StaticBody, PlayerBody)
   - Polimorfismo a través de implementaciones de interfaces (PhysicsEngine, ActionExecutor)
   - Encapsulación del estado y comportamiento de las entidades
   - Clases abstractas e implementaciones concretas

2. **Patrones de Diseño**
   - **Modelo-Vista-Controlador (MVC)**: Separación limpia de responsabilidades con Model manejando la simulación, View gestionando el renderizado y Controller coordinando la comunicación
   - **Patrón Factory**: WorldDefinitionProvider para crear diferentes configuraciones de mundos
   - **Patrón Strategy**: Implementaciones intercambiables de PhysicsEngine
   - **Objetos de Transferencia de Datos (DTOs)**: ActionDTO, EventDTO, EntityInfoDTO para transferencia segura de datos entre capas

3. **Programación Concurrente**
   - Multithreading para ejecución paralela de bucles de renderizado y simulación
   - Estructuras de datos thread-safe (ConcurrentHashMap para gestión de entidades)
   - Variables volatile para visibilidad entre hilos (ModelState, EngineState)
   - Sincronización entre el hilo de renderizado y los hilos de simulación

4. **Arquitectura de Software**
   - Diseño dirigido por UML con diagramas de clases definiendo la estructura antes de la implementación
   - Patrón de inyección de dependencias (cableado de Controller, Model, View)
   - Clara separación de responsabilidades entre paquetes (model, view, controller, assets, generators, world)
   - Arquitectura basada en eventos para comportamientos de entidades

5. **Estructuras de Datos y Algoritmos**
   - Gestión eficiente de entidades usando hash maps para búsquedas O(1)
   - Sistema de ejecución de acciones basado en prioridades
   - Algoritmos de detección de colisiones
   - Cálculos espaciales para simulación física

6. **Organización del Código**
   - Estructura de paquetes reflejando capas arquitectónicas
   - Convenciones de nombrado claras y documentación
   - Diseño modular permitiendo agregar características sin cambios en el núcleo

## Valor Educativo para Aprender Motores de Juego

Este proyecto proporciona experiencia práctica con conceptos fundamentales de motores de juego:

### Arquitectura de Motor de Juego

1. **Implementación de Bucle de Juego**
   - Consideraciones de timestep fijo vs. variable
   - Separación de lógica de actualización del renderizado
   - Gestión de tasa de frames y optimización de rendimiento

2. **Sistema de Entidades Componentes**
   - Gestión del estado de entidades (enum BodyState)
   - Atributos de entidades basados en componentes (posición, velocidad, rotación, masa)
   - Gestión del ciclo de vida de entidades (creación, actualizaciones, destrucción)

3. **Simulación de Física**
   - **Múltiples Motores de Física**: Demuestra cómo diferentes implementaciones de física pueden intercambiarse
   - **Valores de Física**: Constantes de física centralizadas (fricción, elasticidad, gravedad)
   - **Cinemática**: Cálculos de posición, velocidad, aceleración
   - **Dinámica de Rotación**: Cálculos de velocidad angular y giro
   - **Manejo de Límites**: Detección y respuesta a colisiones con bordes del mundo

4. **Pipeline de Renderizado**
   - Separación del estado del juego de la representación visual
   - Renderizado basado en sprites con gestión de recursos
   - Renderizado por capas (fondos, entidades, decoradores, UI)
   - Transformaciones de espacio de pantalla
   - Double buffering para animación suave

5. **Manejo de Entrada**
   - Procesamiento de eventos de teclado (implementación de KeyListener)
   - Patrón Command para acciones del jugador (empuje, rotar, disparar)
   - Gestión de estado de entrada para acciones continuas vs. discretas

6. **Gestión de Recursos**
   - Sistema de carga y caché de recursos
   - Organización de catálogo de recursos
   - Gestión del ciclo de vida de recursos
   - Soporte para diferentes tipos de recursos (imágenes, sprites)

7. **Construcción de Mundos**
   - Generación procedural de mundos
   - Colocación de entidades estáticas y dinámicas
   - Sistemas de fondos y decoradores
   - Definiciones de mundos configurables

8. **Mecánicas de Juego**
   - Sistemas de armas con física de proyectiles
   - Generación y ciclo de vida de entidades (LifeGenerator)
   - Sistema de comportamiento basado en eventos
   - Lógica de juego basada en reglas (reglas de límites, reglas de colisión)

### Conceptos Avanzados

- **Máquinas de Estado**: ModelState y EngineState para gestión del ciclo de vida
- **Cámara/Viewport**: Conceptos de dimensión del mundo vs. dimensión de pantalla
- **Optimización de Rendimiento**: Búsquedas eficientes de entidades, asignación mínima de objetos en rutas críticas
- **Extensibilidad**: Clases base abstractas permitiendo nuevos tipos de entidades, motores de física y armas sin modificar código existente

## Aspectos Técnicos Destacados

- **Lenguaje**: Java (aprovechando características fuertes de POO)
- **Framework GUI**: Java Swing para renderizado multiplataforma
- **Arquitectura**: Estricta separación MVC
- **Concurrencia**: Arquitectura multihilo con colecciones thread-safe
- **Diseño**: Los diagramas de clases UML guían la implementación
- **Escalabilidad**: Diseñado para soportar 1000+ entidades dinámicas con límites configurables

## Estructura del Proyecto

El código está organizado en paquetes bien definidos, cada uno con responsabilidades claras:

### Paquetes MVC Principales

- **`main`**: Punto de entrada de la aplicación que inicializa el motor, conecta dependencias e inicia la simulación
- **`model`**: Estado del juego y lógica de simulación (entidades, física, armas, eventos, acciones)
  - **`model.entities`**: Implementaciones de entidades (DynamicBody, StaticBody, PlayerBody, AbstractEntity, DecoEntity)
  - **`model.physics`**: Implementaciones de motores de física (BasicPhysicsEngine, SpinPhysicsEngine, NullPhysicsEngine, AbstractPhysicsEngine)
  - **`model.weapons`**: Implementaciones del sistema de armas (BasicWeapon, WeaponDto)
- **`view`**: Capa de presentación manejando renderizado y visualización (View, Renderer, ControlPanel)
  - **`view.renderables`**: Objetos de representación visual para entidades (DBodyRenderable, EntityRenderable, EntityInfoDTO)
- **`controller`**: Mediador coordinando Model y View, procesando entrada del usuario y gestionando estado del motor

### Paquetes de Soporte

- **`assets`**: Sistema de gestión de recursos para cargar y organizar recursos visuales (Assets, AssetCatalog, AssetInfo, AssetType)
- **`world`**: Definición y configuración de mundos (WorldDefinition, BackgroundDef, DynamicBodyDef, StaticBodyDef, DecoratorDef)
  - **`world.providers`**: Implementaciones de factory para generar diferentes configuraciones de mundos (RandomWorldDefinitionProvider)
- **`generators`**: Generadores de contenido procedural (SceneGenerator para configuración de escena estática, LifeGenerator para generación de entidades dinámicas)
- **`fx`**: Sistema de efectos visuales para animaciones y efectos de partículas (Fx, FxImage, Spin)
- **`_helpers`**: Clases de utilidad para operaciones comunes (DoubleVector para matemática vectorial 2D, RandomArrayList)
- **`_images`**: Infraestructura de carga y caché de imágenes (Images, ImageCache, ImageDTO, CachedImageKeyDTO)
- **`resources`**: Recursos estáticos incluyendo imágenes de sprites organizadas por tipo (backgrounds, gravity_bodies, solid_bodies, space_decors, spaceship, ui_decors, weapons)

Esta estructura de paquetes sigue una clara separación arquitectónica, facilitando localizar funcionalidad y comprender la organización del sistema.

## Documentación

### Documentación de Arquitectura

Para documentación arquitectónica detallada de las clases principales, incluyendo patrones de diseño, modelos de threading y guías de implementación, consulte:

**[ARCHITECTURE.md](ARCHITECTURE.md)** - Documentación integral extraída de los encabezados del código fuente que cubre:
- Componentes MVC (Controller, Model, View, Renderer)
- Sistema de Entidades (DynamicBody, StaticBody)
- Sistema de Armas (AbstractWeapon)

Esta documentación proporciona explicaciones en profundidad de estrategias de concurrencia, gestión de ciclo de vida y filosofías de diseño utilizadas en todo el código.

### Documentación de Patrones de Diseño

Aprenda sobre los patrones arquitectónicos y patrones de diseño utilizados en este proyecto:

- **[Introducción a MVC](docs/es/MVC.md)** - Introducción general al patrón Modelo-Vista-Controlador
- **[Implementación del Patrón MVC](docs/es/MVC-Pattern.md)** - Cómo se implementa MVC en este proyecto, qué aporta, errores comunes y mejores prácticas
- **[Patrón Factory](docs/es/Factory-Pattern.md)** - Cómo se usa el patrón Factory para creación de objetos, beneficios, detalles de implementación y errores comunes
- **[Patrón Strategy](docs/es/Strategy-Pattern.md)** - Cómo el patrón Strategy permite algoritmos intercambiables (motores físicos), ejemplos de uso y anti-patrones
- **[Patrón DTO](docs/es/DTO-Pattern.md)** - Objetos de Transferencia de Datos para transferencia segura entre capas, seguridad de hilos y guías de implementación

Cada documento de patrón explica:
- **Qué proporciona**: Los beneficios y ventajas de usar el patrón
- **Cómo se implementa**: Detalles de implementación concretos de este código
- **Errores comunes**: Anti-patrones y errores a evitar al implementar el patrón
- **Mejores prácticas**: Guías para implementación y uso apropiado

## Comenzando

Para ejecutar la simulación:

1. Compilar todos los archivos fuente Java en el directorio `src`
2. Ejecutar la clase `Main` ubicada en `src/main/Main.java`
3. Usar controles de teclado para interactuar con la entidad del jugador
4. Observar la simulación física y comportamientos de las entidades

## Ruta de Aprendizaje

Para estudiantes y aprendices, recomendamos explorar el código en este orden:

1. **Comenzar con Arquitectura**: Examinar `Main.java` para entender la secuencia de inicialización
2. **Estudiar Componentes MVC**: Leer `Model.java`, `View.java` y `Controller.java` para entender la arquitectura
3. **Explorar Entidades**: Investigar las clases `DynamicBody`, `StaticBody` y `PlayerBody`
4. **Entender Física**: Comparar diferentes implementaciones de `PhysicsEngine`
5. **Analizar Threading**: Rastrear el flujo de ejecución entre el bucle de renderizado y el bucle de simulación
6. **Examinar Sistema de Eventos**: Estudiar cómo `EventDTO` y `ActionDTO` habilitan comportamientos
7. **Revisar Gestión de Recursos**: Entender el sistema `Assets` y carga de recursos

Este proyecto demuestra que los motores de juego no son magia—son sistemas de software bien estructurados construidos sobre fundamentos de programación sólidos. Al estudiar y modificar este código, los aprendices ganan experiencia práctica con prácticas de ingeniería de software profesional mientras exploran el emocionante dominio del desarrollo de juegos.

## Licencia

Este proyecto se publica bajo la licencia Creative Commons CC0 1.0 Universal, haciéndolo libremente disponible para uso educativo, modificación y distribución.
