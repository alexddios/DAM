# Documentación de Arquitectura de MVCGameEngine

## Tabla de Contenidos

1. [Visión General](#visión-general)
2. [Componentes Principales MVC](#componentes-principales-mvc)
   - [Model](#model)
   - [Controller](#controller)
   - [View](#view)
   - [Renderer](#renderer)
3. [Sistema de Entidades](#sistema-de-entidades)
   - [AbstractBody](#abstractbody)
   - [DynamicBody](#dynamicbody)
   - [StaticBody](#staticbody)
   - [PlayerBody](#playerbody)
4. [Sistema de Armas](#sistema-de-armas)
   - [AbstractWeapon](#abstractweapon)
   - [Implementaciones de Armas](#implementaciones-de-armas)
5. [Modelo de Hilos](#modelo-de-hilos)
6. [Objetos de Transferencia de Datos (DTOs)](#objetos-de-transferencia-de-datos-dtos)
7. [Patrones de Diseño](#patrones-de-diseño)
8. [Guías de Implementación](#guías-de-implementación)

---

## Visión General

MVCGameEngine es un motor de juego concurrente y orientado a eventos construido sobre el patrón arquitectónico Modelo-Vista-Controlador (MVC). El motor presenta un modelo único de hilos por entidad donde cada cuerpo dinámico se ejecuta en su propio hilo, habilitando verdadero paralelismo para la simulación física.

### Características Clave

- **Arquitectura MVC**: Clara separación entre simulación (Model), coordinación (Controller) y presentación (View/Renderer)
- **Hilos por Entidad**: Cada cuerpo dinámico se ejecuta en su propio hilo con actualizaciones físicas independientes
- **Procesamiento Orientado a Eventos**: Los eventos se detectan, las acciones se deciden por reglas del juego y se ejecutan por componentes apropiados
- **Renderizado Basado en Snapshots**: DTOs inmutables aseguran comunicación thread-safe entre simulación y renderizado
- **Sistema de Solicitudes de Armas**: Solicitudes de disparo fire-and-forget con gestión de cooldown
- **Colecciones Concurrentes**: ConcurrentHashMap para gestión thread-safe de entidades

### Diagrama de Arquitectura

```
┌──────────────────────────────────────────────────────────────┐
│                      Capa de Aplicación                       │
│                          (Main)                               │
└──────────────────────────────────────────────────────────────┘
                              │
          ┌───────────────────┼───────────────────┐
          │                   │                   │
          ▼                   ▼                   ▼
    ┌──────────┐      ┌────────────┐      ┌────────────┐
    │  Model   │      │ Controller │      │    View    │
    │          │◄─────┤  (Reglas & │─────►│            │
    │ Entidades│      │   Eventos) │      │  Renderer  │
    └──────────┘      └────────────┘      └────────────┘
          │                   │                   │
          │                   │                   │
          ▼                   ▼                   │
    ┌──────────────────┐ ┌──────────┐            │
    │  DynamicBody 1   │ │   DTOs   │◄───────────┘
    │   (Hilo 1)       │ │ BodyDTO  │  (Pull snapshots)
    ├──────────────────┤ │EventDTO  │
    │  DynamicBody 2   │ │ActionDTO │
    │   (Hilo 2)       │ └──────────┘
    ├──────────────────┤
    │      ...         │
    ├──────────────────┤
    │  PlayerBody N    │
    │   + Armas        │
    ├──────────────────┤
    │  StaticBody      │
    │  (Sin hilo)      │
    └──────────────────┘
```

---

## Componentes Principales MVC

### Model

El Model es la capa central de simulación que posee y gestiona todas las entidades del juego. Opera en una arquitectura orientada a eventos donde las entidades reportan actualizaciones físicas y el Model procesa eventos para determinar acciones apropiadas.

#### Responsabilidades

- **Gestión de Entidades**: Crear, rastrear y gestionar cuerpos dinámicos, cuerpos estáticos, jugadores y decoradores
- **Procesamiento de Eventos**: Detectar eventos de actualizaciones físicas de entidades (límites alcanzados, vida terminada, colisiones)
- **Operaciones Thread-Safe**: Gestionar acceso concurrente usando ConcurrentHashMap para almacenamiento de entidades
- **Generación de Snapshots**: Proveer DTOs inmutables para renderizado
- **Límites del Mundo**: Hacer cumplir límites del mundo y ciclo de vida de entidades

#### Colecciones de Entidades

El Model mantiene varios mapas concurrentes para diferentes tipos de entidades:

```java
private final Map<String, AbstractBody> dynamicBodies = new ConcurrentHashMap<>(MAX_ENTITIES);
private final Map<String, AbstractBody> decorators = new ConcurrentHashMap<>(100);
private final Map<String, AbstractBody> gravityBodies = new ConcurrentHashMap<>(50);
private final Map<String, AbstractBody> playerBodies = new ConcurrentHashMap<>(10);
private final Map<String, AbstractBody> staticBodies = new ConcurrentHashMap<>(100);
```

#### Procesamiento Orientado a Eventos

El Model procesa eventos de cuerpos dinámicos en tres fases:

1. **Detección de Eventos**: Verificar valores físicos para cruces de límites, expiración de vida, etc.
2. **Resolución de Acciones**: Consultar al Controller por acciones apropiadas basadas en eventos
3. **Ejecución de Acciones**: Ejecutar acciones en entidades o a través del Model

```java
public void processDBodyEvents(DynamicBody dynamicBody,
        PhysicsValuesDTO newPhyValues, PhysicsValuesDTO oldPhyValues) {
    
    if (!isProcessable(dynamicBody)) {
        return;
    }

    BodyState previousState = dynamicBody.getState();
    dynamicBody.setState(BodyState.HANDS_OFF);

    try {
        List<EventDTO> events = this.detectEvents(
                dynamicBody, newPhyValues, oldPhyValues);

        List<ActionDTO> actions = this.resolveActionsForEvents(
                dynamicBody, events);

        this.doActions(
                dynamicBody, actions, newPhyValues, oldPhyValues);

    } finally {
        if (dynamicBody.getState() == BodyState.HANDS_OFF) {
            dynamicBody.setState(BodyState.ALIVE);
        }
    }
}
```

#### Agregando Entidades

```java
// Agregar un cuerpo dinámico
public String addDynamicBody(double size, double posX, double posY,
        double speedX, double speedY, double accX, double accY,
        double angle, double angularSpeed, double angularAcc, 
        double thrust, double maxLifeInSeconds) {
    
    if (AbstractBody.getAliveQuantity() >= this.maxDBody) {
        return null;
    }

    PhysicsValuesDTO phyVals = new PhysicsValuesDTO(nanoTime(), posX, posY, 
            angle, size, speedX, speedY, accX, accY, 
            angularSpeed, angularAcc, thrust);

    DynamicBody dBody = new DynamicBody(new BasicPhysicsEngine(phyVals), 
                                        maxLifeInSeconds);

    dBody.setModel(this);
    dBody.activate();
    this.dynamicBodies.put(dBody.getEntityId(), dBody);

    return dBody.getEntityId();
}

// Agregar un jugador
public String addPlayer(double size, double posX, double posY, 
        double speedX, double speedY, double accX, double accY,
        double angle, double angularSpeed, double angularAcc, double thrust) {
    
    PhysicsValuesDTO phyVals = new PhysicsValuesDTO(
            nanoTime(), posX, posY, angle, size,
            speedX, speedY, accX, accY,
            angularSpeed, angularAcc, thrust);

    PlayerBody pBody = new PlayerBody(new BasicPhysicsEngine(phyVals));

    pBody.setModel(this);
    pBody.activate();
    String entityId = pBody.getEntityId();
    this.dynamicBodies.put(entityId, pBody);
    this.playerBodies.put(entityId, pBody);

    return entityId;
}
```

---

### Controller

El Controller coordina entre Model y View, gestionando el ciclo de vida del motor, procesando entrada de usuario e implementando reglas del juego a través de un sistema de mapeo evento-acción.

#### Responsabilidades

- **Bootstrap & Activación**: Inicializar Model y View con dependencias requeridas
- **Manejo de Entrada**: Traducir entrada de usuario en comandos del Model
- **Reglas del Juego**: Implementar el método `decideActions()` para mapear eventos a acciones
- **Puente de Snapshots**: Proveer DTOs del Model a View para renderizado
- **Creación de Entidades**: Delegar creación de entidades al Model y actualizar View

#### Estados del Motor

```java
public enum EngineState {
    STARTING,
    ALIVE,
    PAUSED,
    STOPPED
}
```

#### Delegación de Comandos de Entrada

El Controller expone métodos de control del jugador que delegan al Model:

```java
public void playerThrustOn(String playerId) {
    this.model.playerThrustOn(playerId);
}

public void playerFire(String playerId) {
    this.model.playerFire(playerId);
}

public void playerRotateLeftOn(String playerId) {
    this.model.playerRotateLeftOn(playerId);
}

public void selectNextWeapon(String playerId) {
    this.model.selectNextWeapon(playerId);
}
```

#### Reglas del Juego - Mapeo de Evento a Acción

El Controller implementa la interfaz `DomainEventProcesor` para decidir qué acciones deben ocurrir basadas en eventos:

```java
@Override
public List<ActionDTO> decideActions(AbstractBody entity, 
                                     List<EventDTO> events) {
    return applyGameRules(entity, events);
}

private List<ActionDTO> applyGameRules(AbstractBody entity, 
                                       List<EventDTO> events) {
    List<ActionDTO> actions = new ArrayList<>();

    for (EventDTO event : events) {
        switch (event.type) {
            case REACHED_EAST_LIMIT:
            case REACHED_WEST_LIMIT:
            case REACHED_NORTH_LIMIT:
            case REACHED_SOUTH_LIMIT:
                actions.add(new ActionDTO(ActionType.DIE, 
                           ActionPriority.HIGH, ActionExecutor.MODEL));
                break;

            case MUST_FIRE:
                actions.add(new ActionDTO(ActionType.FIRE, 
                           ActionPriority.HIGH, ActionExecutor.MODEL));
                break;

            case LIFE_OVER:
                actions.add(new ActionDTO(ActionType.DIE, 
                           ActionPriority.HIGH, ActionExecutor.MODEL));
                break;
        }
    }

    // Acción predeterminada: mover si no está muerto
    boolean hasDeath = actions.stream()
        .anyMatch(a -> a.type == ActionType.DIE);
    
    if (!hasDeath) {
        actions.add(new ActionDTO(ActionType.MOVE, 
                   ActionPriority.NORMAL, ActionExecutor.BODY));
    }

    return actions;
}
```

---

### View

La View es la capa de presentación que gestiona la ventana, componentes UI y coordina con el Renderer. Maneja entrada de usuario y delega comandos al Controller.

#### Responsabilidades

- **Gestión de Ventana**: Crear y gestionar el JFrame y componentes UI
- **Carga de Assets**: Cargar imágenes y sprites para renderizado
- **Manejo de Entrada**: Capturar eventos de teclado y traducir a comandos del Controller
- **Coordinación con Renderer**: Iniciar y gestionar el hilo del Renderer
- **Actualizaciones de Snapshots**: Empujar snapshots estáticos/decoradores cuando cambian

#### Componentes Clave

```java
public class View extends JFrame implements KeyListener {
    private Controller controller;
    private Renderer renderer;
    private ControlPanel controlPanel;
    private Dimension viewDim;
    
    // Catálogos de assets
    private BufferedImage background;
    private Images dynamicBodyImages;
    private Images staticBodyImages;
    private Images decoratorImages;
}
```

#### Manejo de Entrada

La View traduce eventos de teclado en comandos del Controller:

```java
@Override
public void keyPressed(KeyEvent e) {
    if (this.controller.getEngineState() != EngineState.ALIVE) {
        return;
    }

    switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
            this.controller.playerThrustOn(playerId);
            break;
        case KeyEvent.VK_DOWN:
            this.controller.playerReverseThrust(playerId);
            break;
        case KeyEvent.VK_LEFT:
            this.controller.playerRotateLeftOn(playerId);
            break;
        case KeyEvent.VK_RIGHT:
            this.controller.playerRotateRightOn(playerId);
            break;
        case KeyEvent.VK_SPACE:
            if (!fireKeyDown) {
                this.controller.playerFire(playerId);
                fireKeyDown = true;
            }
            break;
        case KeyEvent.VK_W:
            this.controller.selectNextWeapon(playerId);
            break;
    }
}

@Override
public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
        case KeyEvent.VK_DOWN:
            this.controller.playerThrustOff(playerId);
            break;
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_RIGHT:
            this.controller.playerRotateOff(playerId);
            break;
        case KeyEvent.VK_SPACE:
            fireKeyDown = false;
            break;
    }
}
```

---

### Renderer

El Renderer es un hilo dedicado que continuamente extrae snapshots de entidades y los dibuja en pantalla usando Java2D BufferStrategy.

#### Responsabilidades

- **Bucle Activo de Renderizado**: Ejecutarse en un hilo dedicado, extrayendo snapshots cada frame
- **Orden de Dibujo**: Renderizar fondo, decoradores, cuerpos estáticos, cuerpos dinámicos y HUD
- **Gestión de Imágenes**: Cachear y gestionar texturas a través de ImageCache
- **Optimización de Rendimiento**: Usar VolatileImage para renderizado acelerado por hardware
- **Seguimiento de Frames**: Eliminar renderables obsoletos basados en contadores de frames

#### Modelo de Hilos

```java
public class Renderer extends Canvas implements Runnable {
    private Thread renderThread;
    
    public void activate() {
        this.renderThread = new Thread(this, "Renderer");
        this.renderThread.start();
    }
    
    @Override
    public void run() {
        while (view.getEngineState() != EngineState.STOPPED) {
            if (view.getEngineState() == EngineState.ALIVE) {
                render();
            } else {
                Thread.yield();
            }
        }
    }
}
```

#### Pipeline de Renderizado

```java
private void render() {
    BufferStrategy strategy = this.getBufferStrategy();
    if (strategy == null) {
        this.createBufferStrategy(2);
        return;
    }

    Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

    try {
        // 1. Dibujar fondo
        renderBackground(g);
        
        // 2. Dibujar decoradores
        renderDecorators(g);
        
        // 3. Dibujar cuerpos estáticos
        renderStaticBodies(g);
        
        // 4. Actualizar y dibujar cuerpos dinámicos
        updateAndRenderDynamicBodies(g);
        
        // 5. Dibujar HUD
        renderHUD(g);
        
    } finally {
        g.dispose();
        strategy.show();
    }
}
```

---

## Sistema de Entidades

El Sistema de Entidades está construido sobre una jerarquía enraizada en `AbstractBody`, con diferentes tipos concretos para entidades dinámicas, estáticas, de jugador y decoradoras.

### Jerarquía de Entidades

```
AbstractBody (Base Abstracta)
├── DynamicBody (Runnable - hilo propio)
│   ├── PlayerBody (agrega gestión de armas)
│   └── Proyectiles, Asteroides, etc.
├── StaticBody (sin hilo)
│   └── Muros, Plataformas, Obstáculos
└── DecoBody (decorador, sin hilo)
    └── Elementos visuales
```

### AbstractBody

AbstractBody provee la base común para todas las entidades con gestión de ciclo de vida, integración física y seguimiento de estado.

#### Características Clave

- **Identificación Única**: ID de entidad basado en UUID
- **Estados de Ciclo de Vida**: STARTING → ALIVE → DEAD
- **Contadores Estáticos**: Rastrear conteos de entidades creadas, vivas y muertas
- **Integración Física**: Cada body tiene un PhysicsEngine (BasicPhysicsEngine o NullPhysicsEngine)
- **Soporte de Tiempo de Vida**: Tiempo de vida máximo opcional en segundos para entidades temporales

#### Estados de Body

```java
public enum BodyState {
    STARTING,  // Estado inicial antes de activación
    ALIVE,     // Activo y simulando
    HANDS_OFF, // Temporalmente bloqueado durante procesamiento de eventos
    DEAD       // Terminado
}
```

---

### DynamicBody

DynamicBody representa entidades con simulación física activa. Cada cuerpo dinámico se ejecuta en su propio hilo, actualizando continuamente su estado físico y reportando al Model para procesamiento de eventos.

#### Características Clave

- **Hilos por Entidad**: Implementa Runnable, se ejecuta en hilo dedicado
- **Actualizaciones Continuas de Física**: El motor físico calcula nuevo estado cada iteración
- **Reporte de Eventos**: Reporta cambios físicos al Model para detección de eventos
- **Sincronización de Estado**: Usa estado HANDS_OFF durante procesamiento de eventos
- **Acciones Acotadas**: Soporta rebote, movimiento y acciones de muerte

#### Modelo de Hilos

Cada DynamicBody tiene su propio hilo que:
1. Calcula nuevos valores físicos
2. Reporta al Model para procesamiento de eventos
3. Espera brevemente entre iteraciones
4. Termina cuando el estado se vuelve DEAD

#### Implementación

```java
public class DynamicBody extends AbstractBody implements PhysicsBody, Runnable {

    private Thread thread;
    private final BasicPhysicsEngine phyEngine;

    @Override
    public synchronized void activate() {
        super.activate();

        this.thread = new Thread(this);
        this.thread.setName("Body " + this.getEntityId());
        this.thread.setPriority(Thread.NORM_PRIORITY - 1);
        this.thread.start();
        this.setState(BodyState.ALIVE);
    }

    @Override
    public void run() {
        PhysicsValuesDTO newPhyValues;

        while ((this.getState() != BodyState.DEAD)
                && (this.getModel().getState() != ModelState.STOPPED)) {

            if ((this.getState() == BodyState.ALIVE)
                    && (this.getModel().getState() == ModelState.ALIVE)) {

                newPhyValues = this.phyEngine.calcNewPhysicsValues();
                this.getModel().processDBodyEvents(this, newPhyValues, 
                                                   this.phyEngine.getPhysicsValues());
            }

            try {
                Thread.sleep(5); // Breve pausa entre actualizaciones
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void doMovement(PhysicsValuesDTO newPhyValues) {
        this.phyEngine.setPhysicsValues(newPhyValues);
    }

    public void reboundInEast(PhysicsValuesDTO newPhy, PhysicsValuesDTO oldPhy,
                             int worldWidth, int worldHeight) {
        newPhy.posX = 0;
        newPhy.speedX = -oldPhy.speedX;
        this.phyEngine.setPhysicsValues(newPhy);
    }

    // Métodos adicionales de rebote...
}
```

---

### StaticBody

StaticBody representa entidades inamovibles con posiciones fijas. A diferencia de los cuerpos dinámicos, los cuerpos estáticos no tienen hilo y usan un NullPhysicsEngine que no se actualiza.

#### Características Clave

- **Sin Hilo**: activate() no inicia un hilo
- **Posición Fija**: Usa NullPhysicsEngine con valores constantes
- **Liviano**: Sobrecarga mínima para entidades que no se mueven
- **Usos Comunes**: Muros, plataformas, obstáculos, elementos decorativos

#### Implementación

```java
public class StaticBody extends AbstractBody {

    public StaticBody(double size, double x, double y, double angle) {
        super(new NullPhysicsEngine(size, x, y, angle));
    }

    @Override
    public synchronized void activate() {
        super.activate();
        this.setState(BodyState.ALIVE);
    }
}
```

---

### PlayerBody

PlayerBody extiende DynamicBody con características específicas del jugador incluyendo gestión de armas y manejo de entrada de control.

#### Integración de Armas

```java
public class PlayerBody extends DynamicBody {
    private final List<Weapon> weapons = new ArrayList<>();
    private int selectedWeaponIndex = 0;

    public void addWeapon(Weapon weapon) {
        this.weapons.add(weapon);
    }

    public void selectNextWeapon() {
        if (weapons.isEmpty()) return;
        selectedWeaponIndex = (selectedWeaponIndex + 1) % weapons.size();
    }

    public Weapon getActiveWeapon() {
        if (weapons.isEmpty()) return null;
        return weapons.get(selectedWeaponIndex);
    }

    public void requestFire() {
        Weapon active = getActiveWeapon();
        if (active != null) {
            active.registerFireRequest();
        }
    }

    public boolean mustFireNow(PhysicsValuesDTO newPhyValues) {
        Weapon active = getActiveWeapon();
        if (active == null) return false;
        
        double dt = (newPhyValues.timeStamp - 
                    this.getPhysicsValues().timeStamp) / 1_000_000_000.0;
        return active.mustFireNow(dt);
    }
}
```

---

## Sistema de Armas

El Sistema de Armas implementa un mecanismo de disparo basado en solicitudes donde las armas son componentes pasivos que responden a solicitudes de disparo durante su ciclo de actualización.

### AbstractWeapon

AbstractWeapon es la clase base para todas las armas, implementando un sistema thread-safe de solicitudes de disparo con gestión de cooldown y munición.

#### Filosofía de Diseño

- **Componente Pasivo**: Las armas no tienen hilos y no realizan trabajo asíncrono
- **Basado en Solicitudes**: Las solicitudes de disparo se registran desde cualquier hilo, se consumen durante la actualización
- **Solicitudes Monotónicas**: Solo importa la solicitud de disparo más reciente; sin cola
- **Determinístico**: Toda la lógica de disparo ocurre durante llamadas a `mustFireNow(dtSeconds)`

#### Modelo de Concurrencia

```java
public abstract class AbstractWeapon implements Weapon {

    private final String id;
    private final WeaponDto weaponConfig;
    private final AtomicLong lastFireRequest = new AtomicLong(0L);
    protected long lastHandledRequest = 0L;
    protected int currentAmmo;

    public AbstractWeapon(WeaponDto weaponConfig) {
        this.id = UUID.randomUUID().toString();
        this.weaponConfig = weaponConfig;
        this.currentAmmo = weaponConfig.maxAmmo;
    }

    @Override
    public void registerFireRequest() {
        this.lastFireRequest.set(System.nanoTime());
    }

    protected boolean hasRequest() {
        return this.lastFireRequest.get() > this.lastHandledRequest;
    }

    protected void markAllRequestsHandled() {
        this.lastHandledRequest = this.lastFireRequest.get();
    }

    // Método abstracto: subclases implementan lógica de disparo
    public abstract boolean mustFireNow(double dtSeconds);
}
```

### Implementaciones de Armas

#### BasicWeapon

Arma semiautomática simple con cooldown entre disparos.

```java
public class BasicWeapon extends AbstractWeapon {

    private double cooldown = 0.0;

    @Override
    public boolean mustFireNow(double dtSeconds) {
        if (this.cooldown > 0) {
            this.cooldown -= dtSeconds;
            this.markAllRequestsHandled();
            return false;
        }

        if (this.currentAmmo <= 0) {
            this.markAllRequestsHandled();
            cooldown = this.getWeaponConfig().reloadTime;
            this.currentAmmo = this.getWeaponConfig().maxAmmo;
            return false;
        }

        if (!this.hasRequest()) {
            return false;
        }

        // Disparar
        this.markAllRequestsHandled();
        this.currentAmmo--;
        cooldown = 1.0 / this.getWeaponConfig().fireRate;
        return true;
    }
}
```

#### BurstWeapon

Arma que dispara múltiples tiros en rápida sucesión por pulsación del gatillo.

```java
public class BurstWeapon extends AbstractWeapon {

    private double cooldown = 0.0d;
    private int shotsRemainingInBurst = 0;

    @Override
    public boolean mustFireNow(double dtSeconds) {
        if (this.cooldown > 0) {
            this.cooldown -= dtSeconds;
            this.markAllRequestsHandled();
            return false;
        }

        if (this.currentAmmo <= 0) {
            this.markAllRequestsHandled();
            this.shotsRemainingInBurst = 0;
            cooldown = this.getWeaponConfig().reloadTime;
            this.currentAmmo = this.getWeaponConfig().maxAmmo;
            return false;
        }

        if (this.shotsRemainingInBurst > 0) {
            // Ráfaga en curso - disparar siguiente tiro
            this.markAllRequestsHandled();
            this.shotsRemainingInBurst--;
            this.currentAmmo--;

            if (this.shotsRemainingInBurst == 0) {
                this.cooldown = 1.0 / this.getWeaponConfig().fireRate;
            } else {
                this.cooldown = 1.0 / this.getWeaponConfig().burstFireRate;
            }

            return true;
        }

        if (!this.hasRequest()) {
            return false;
        }

        // Iniciar nueva ráfaga
        this.markAllRequestsHandled();
        int burstSize = Math.max(1, getWeaponConfig().burstSize);
        this.shotsRemainingInBurst = burstSize - 1;
        this.currentAmmo--;

        if (this.shotsRemainingInBurst == 0) {
            this.cooldown = 1.0 / this.getWeaponConfig().fireRate;
        } else {
            this.cooldown = 1.0 / this.getWeaponConfig().burstFireRate;
        }
        
        return true;
    }
}
```

---

## Modelo de Hilos

MVCGameEngine implementa un modelo único de **hilos por entidad** donde cada DynamicBody se ejecuta en su propio hilo. Esto habilita verdadera simulación física paralela a través de múltiples núcleos de CPU.

### Arquitectura de Hilos

```
┌──────────────────────────────────────────────────────────┐
│                     Hilo Principal/AWT                    │
│           - Gestión de ventana (JFrame)                   │
│           - Captura de entrada de teclado                 │
└──────────────────────────────────────────────────────────┘
                           │
          ┌────────────────┼────────────────┐
          │                │                │
          ▼                ▼                ▼
    ┌──────────┐    ┌──────────┐    ┌──────────┐
    │ DBody 1  │    │ DBody 2  │    │ DBody N  │
    │  Hilo    │    │  Hilo    │    │  Hilo    │
    │          │    │          │    │          │
    │ Física   │    │ Física   │    │ Física   │
    │Actualiz. │    │Actualiz. │    │Actualiz. │
    └──────────┘    └──────────┘    └──────────┘
          │                │                │
          └────────────────┼────────────────┘
                           │
                           ▼
                    ┌──────────────┐
                    │    Model     │
                    │  (Procesa-   │
                    │   miento de  │
                    │   Eventos)   │
                    └──────────────┘
                           │
                           ▼
                    ┌──────────────┐
                    │  Renderer    │
                    │    Hilo      │
                    │              │
                    │ Pull DTOs    │
                    │ Draw Frame   │
                    └──────────────┘
```

### Estrategia de Sincronización

#### ConcurrentHashMap para Almacenamiento de Entidades

El Model usa ConcurrentHashMap para gestión thread-safe de entidades:

```java
private final Map<String, AbstractBody> dynamicBodies = 
    new ConcurrentHashMap<>(MAX_ENTITIES);
```

#### Estado HANDS_OFF para Procesamiento de Eventos

Durante el procesamiento de eventos, las entidades se bloquean con estado HANDS_OFF:

```java
dynamicBody.setState(BodyState.HANDS_OFF); // Bloquear entidad
try {
    // Procesar eventos, resolver acciones, ejecutar acciones
} finally {
    dynamicBody.setState(BodyState.ALIVE); // Desbloquear
}
```

#### Operaciones Atómicas para Armas

Las armas usan AtomicLong para solicitudes thread-safe de disparo:

```java
private final AtomicLong lastFireRequest = new AtomicLong(0L);

@Override
public void registerFireRequest() {
    this.lastFireRequest.set(System.nanoTime());
}
```

---

## Objetos de Transferencia de Datos (DTOs)

Los DTOs son objetos de valor inmutables usados para transferir datos entre capas sin exponer estado mutable.

### PhysicsValuesDTO

Contiene todo el estado físico para una entidad:

```java
public class PhysicsValuesDTO {
    public final long timeStamp;
    public final double posX;
    public final double posY;
    public final double angle;
    public final double size;
    public final double speedX;
    public final double speedY;
    public final double accX;
    public final double accY;
    public final double angularSpeed;
    public final double angularAcc;
    public final double thrust;
}
```

### EventDTO

Representa un evento detectado de una entidad:

```java
public class EventDTO {
    public final AbstractBody entity;
    public final EventType type;
}

public enum EventType {
    REACHED_EAST_LIMIT, REACHED_WEST_LIMIT,
    REACHED_NORTH_LIMIT, REACHED_SOUTH_LIMIT,
    MUST_FIRE, LIFE_OVER, COLLIDED
}
```

### ActionDTO

Representa una acción a ejecutar:

```java
public class ActionDTO {
    public final ActionType type;
    public final ActionPriority priority;
    public final ActionExecutor executor;
}

public enum ActionType {
    MOVE, REBOUND_IN_EAST, REBOUND_IN_WEST,
    REBOUND_IN_NORTH, REBOUND_IN_SOUTH,
    DIE, FIRE, EXPLODE_IN_FRAGMENTS, GO_INSIDE, NONE
}

public enum ActionExecutor {
    BODY,  // Acción ejecutada por el body mismo
    MODEL  // Acción ejecutada por el Model
}
```

---

## Patrones de Diseño

### Patrones Utilizados en MVCGameEngine

#### 1. Modelo-Vista-Controlador (MVC)
- **Propósito**: Separar simulación, coordinación y presentación
- **Uso**: Arquitectura central separando Model, Controller y View/Renderer

#### 2. Objeto de Transferencia de Datos (DTO)
- **Propósito**: Transferir datos inmutables entre capas
- **Uso**: BodyDTO, PhysicsValuesDTO, EventDTO, ActionDTO

#### 3. Patrón Factory
- **Propósito**: Encapsular creación de objetos
- **Uso**: WeaponFactory crea armas basadas en WeaponDto

#### 4. Patrón Strategy
- **Propósito**: Diferentes comportamientos de física
- **Uso**: Interfaz PhysicsEngine con BasicPhysicsEngine y NullPhysicsEngine

#### 5. Patrón Evento-Acción
- **Propósito**: Desacoplar detección de eventos de ejecución de acciones
- **Flujo**: Detección de Eventos → Resolución de Acciones → Ejecución de Acciones

#### 6. Puertos y Adaptadores (Arquitectura Hexagonal)
- **Propósito**: Definir interfaces (puertos) para interacciones externas
- **Uso**: Puerto PhysicsEngine, puerto Weapon, puerto DomainEventProcessor

---

## Guías de Implementación

### Estructura Real del Proyecto

```
MVCGameEngine/
├── src/
│   ├── main/
│   │   └── Main.java
│   ├── controller/
│   │   ├── Controller.java
│   │   ├── EngineState.java
│   │   └── ports/DomainEventProcesor.java
│   ├── model/
│   │   ├── Model.java
│   │   ├── bodies/
│   │   │   ├── AbstractBody.java
│   │   │   ├── DynamicBody.java
│   │   │   ├── StaticBody.java
│   │   │   └── PlayerBody.java
│   │   ├── physics/
│   │   │   ├── BasicPhysicsEngine.java
│   │   │   └── PhysicsValuesDTO.java
│   │   └── weapons/
│   │       ├── AbstractWeapon.java
│   │       ├── BasicWeapon.java
│   │       ├── BurstWeapon.java
│   │       └── WeaponFactory.java
│   ├── view/
│   │   ├── View.java
│   │   ├── Renderer.java
│   │   └── renderables/
│   └── world/
└── docs/
    ├── ARCHITECTURE.md
    └── ARCHITECTURE_ES.md
```

### Decisiones Clave de Diseño

#### 1. Hilos por Entidad

Cada DynamicBody se ejecuta en su propio hilo:
- **Beneficios**: Verdadero paralelismo, lógica de entidad simplificada
- **Compromisos**: Sobrecarga de creación de hilos
- **Recomendación**: Adecuado para conteos moderados de entidades (< 5000)

#### 2. Desacoplamiento Evento-Acción

Los eventos son detectados por entidades, las acciones son decididas por Controller:
- **Beneficios**: Reglas del juego centralizadas en Controller
- **Uso**: Fácil modificar comportamiento del juego sin cambiar código de entidades

#### 3. Armas Basadas en Solicitudes

Las armas usan un mecanismo de solicitud de disparo:
- **Beneficios**: Thread-safe, determinístico, simple de razonar
- **Ventaja**: Las armas son pasivas, sin hilos en segundo plano

#### 4. Renderizado Basado en DTOs

El renderizado extrae DTOs inmutables en lugar de acceder a entidades:
- **Beneficios**: Thread-safe, separación limpia, sin bloqueo de entidades

---

## Conclusión

MVCGameEngine implementa una arquitectura única combinando principios MVC con paralelismo por entidad. Las innovaciones clave son:

### Fortalezas Centrales

1. **Hilos por Entidad**: Cada cuerpo dinámico se ejecuta independientemente
2. **Arquitectura Orientada a Eventos**: Separación limpia entre detección, decisión y ejecución
3. **Armas Basadas en Solicitudes**: Sistema thread-safe y determinístico
4. **Snapshots con DTOs**: Transferencia inmutable de datos asegura renderizado thread-safe
5. **Reglas Flexibles del Juego**: Centralizadas en Controller, fáciles de modificar

### Mejores Prácticas

1. **Mantener Entidades Genéricas**: Poner lógica específica del juego en Controller
2. **Usar DTOs**: Nunca exponer estado mutable de entidades a otras capas
3. **Minimizar Locks**: Confiar en ConcurrentHashMap y datos inmutables
4. **Probar Concurrencia**: Probar con diferentes conteos y tiempos de entidades
5. **Perfilar Primero**: Medir antes de optimizar

---

**Versión del Documento**: 2.0  
**Última Actualización**: 2026-01-01  
**Autor**: Equipo MVCGameEngine  
**Nota**: Este documento refleja la arquitectura real implementada, verificada contra el código base.
