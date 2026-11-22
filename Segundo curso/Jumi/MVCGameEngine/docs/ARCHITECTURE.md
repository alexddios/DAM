# MVCGameEngine Architecture Documentation

## Table of Contents

1. [Overview](#overview)
2. [MVC Core Components](#mvc-core-components)
   - [Model](#model)
   - [Controller](#controller)
   - [View](#view)
   - [Renderer](#renderer)
3. [Entity System](#entity-system)
   - [AbstractBody](#abstractbody)
   - [DynamicBody](#dynamicbody)
   - [StaticBody](#staticbody)
   - [PlayerBody](#playerbody)
4. [Weapon System](#weapon-system)
   - [AbstractWeapon](#abstractweapon)
   - [Weapon Implementations](#weapon-implementations)
5. [Threading Model](#threading-model)
6. [Data Transfer Objects (DTOs)](#data-transfer-objects-dtos)
7. [Design Patterns](#design-patterns)
8. [Implementation Guidelines](#implementation-guidelines)

---

## Overview

MVCGameEngine is a concurrent, event-driven game engine built on the Model-View-Controller (MVC) architectural pattern. The engine features a unique per-entity threading model where each dynamic body runs in its own thread, enabling true parallelism for physics simulation.

### Key Features

- **MVC Architecture**: Clear separation between simulation (Model), coordination (Controller), and presentation (View/Renderer)
- **Per-Entity Threading**: Each dynamic body runs in its own thread with independent physics updates
- **Event-Driven Processing**: Events are detected, actions are decided by game rules, and executed by appropriate components
- **Snapshot-Based Rendering**: Immutable DTOs ensure thread-safe communication between simulation and rendering
- **Weapon Request System**: Fire-and-forget weapon requests with cooldown management
- **Concurrent Collections**: ConcurrentHashMap for thread-safe entity management

### Architecture Diagram

```
┌──────────────────────────────────────────────────────────────┐
│                      Application Layer                        │
│                          (Main)                               │
└──────────────────────────────────────────────────────────────┘
                              │
          ┌───────────────────┼───────────────────┐
          │                   │                   │
          ▼                   ▼                   ▼
    ┌──────────┐      ┌────────────┐      ┌────────────┐
    │  Model   │      │ Controller │      │    View    │
    │          │◄─────┤  (Rules &  │─────►│            │
    │ Entities │      │   Events)  │      │  Renderer  │
    └──────────┘      └────────────┘      └────────────┘
          │                   │                   │
          │                   │                   │
          ▼                   ▼                   │
    ┌──────────────────┐ ┌──────────┐            │
    │  DynamicBody 1   │ │   DTOs   │◄───────────┘
    │   (Thread 1)     │ │ BodyDTO  │  (Pull snapshots)
    ├──────────────────┤ │EventDTO  │
    │  DynamicBody 2   │ │ActionDTO │
    │   (Thread 2)     │ └──────────┘
    ├──────────────────┤
    │      ...         │
    ├──────────────────┤
    │  PlayerBody N    │
    │   + Weapons      │
    ├──────────────────┤
    │  StaticBody      │
    │  (No thread)     │
    └──────────────────┘
```

---

## MVC Core Components

### Model

The Model is the core simulation layer that owns and manages all game entities. It operates on an event-driven architecture where entities report physics updates, and the Model processes events to determine appropriate actions.

#### Responsibilities

- **Entity Management**: Create, track, and manage dynamic bodies, static bodies, players, and decorators
- **Event Processing**: Detect events from entity physics updates (boundary reached, life over, collisions)
- **Thread-Safe Operations**: Manage concurrent access using ConcurrentHashMap for entity storage
- **Snapshot Generation**: Provide immutable DTOs for rendering
- **World Boundaries**: Enforce world limits and entity lifecycle

#### Entity Collections

The Model maintains several concurrent maps for different entity types:

```java
private final Map<String, AbstractBody> dynamicBodies = new ConcurrentHashMap<>(MAX_ENTITIES);
private final Map<String, AbstractBody> decorators = new ConcurrentHashMap<>(100);
private final Map<String, AbstractBody> gravityBodies = new ConcurrentHashMap<>(50);
private final Map<String, AbstractBody> playerBodies = new ConcurrentHashMap<>(10);
private final Map<String, AbstractBody> staticBodies = new ConcurrentHashMap<>(100);
```

#### Event-Driven Processing

The Model processes dynamic body events in three phases:

1. **Event Detection**: Check physics values for boundary crossings, life expiration, etc.
2. **Action Resolution**: Query Controller for appropriate actions based on events
3. **Action Execution**: Execute actions on entities or through the Model

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

#### Adding Entities

```java
// Add a dynamic body
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

// Add a player
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

The Controller coordinates between Model and View, managing the engine lifecycle, processing user input, and implementing game rules through an event-action mapping system.

#### Responsibilities

- **Bootstrap & Activation**: Initialize Model and View with required dependencies
- **Input Handling**: Translate user input into Model commands
- **Game Rules**: Implement the `decideActions()` method to map events to actions
- **Snapshot Bridging**: Provide DTOs from Model to View for rendering
- **Entity Creation**: Delegate entity creation to Model and update View

#### Engine States

```java
public enum EngineState {
    STARTING,
    ALIVE,
    PAUSED,
    STOPPED
}
```

#### Input Command Delegation

The Controller exposes player control methods that delegate to the Model:

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

#### Game Rules - Event to Action Mapping

The Controller implements the `DomainEventProcesor` interface to decide what actions should occur based on events:

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

    // Default action: move if not dead
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

The View is the presentation layer that manages the window, UI components, and coordinates with the Renderer. It handles user input and delegates commands to the Controller.

#### Responsibilities

- **Window Management**: Create and manage the JFrame and UI components
- **Asset Loading**: Load images and sprites for rendering
- **Input Handling**: Capture keyboard events and translate to Controller commands
- **Renderer Coordination**: Start and manage the Renderer thread
- **Snapshot Updates**: Push static/decorator snapshots when they change

#### Key Components

```java
public class View extends JFrame implements KeyListener {
    private Controller controller;
    private Renderer renderer;
    private ControlPanel controlPanel;
    private Dimension viewDim;
    
    // Asset catalogs
    private BufferedImage background;
    private Images dynamicBodyImages;
    private Images staticBodyImages;
    private Images decoratorImages;
}
```

#### Input Handling

The View translates keyboard events into Controller commands:

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

#### Asset Loading

```java
public void loadAssets(BufferedImage background, 
                      Images dynamicBodyImages,
                      Images staticBodyImages, 
                      Images decoratorImages) {
    this.background = background;
    this.dynamicBodyImages = dynamicBodyImages;
    this.staticBodyImages = staticBodyImages;
    this.decoratorImages = decoratorImages;
}
```

---

### Renderer

The Renderer is a dedicated thread that continuously pulls entity snapshots and draws them to the screen using Java2D BufferStrategy.

#### Responsibilities

- **Active Rendering Loop**: Run in a dedicated thread, pulling snapshots each frame
- **Draw Ordering**: Render background, decorators, static bodies, dynamic bodies, and HUD
- **Image Management**: Cache and manage textures through ImageCache
- **Performance Optimization**: Use VolatileImage for hardware-accelerated rendering
- **Frame Tracking**: Remove stale renderables based on frame counters

#### Threading Model

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

#### Rendering Pipeline

```java
private void render() {
    BufferStrategy strategy = this.getBufferStrategy();
    if (strategy == null) {
        this.createBufferStrategy(2);
        return;
    }

    Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

    try {
        // 1. Draw background
        renderBackground(g);
        
        // 2. Draw decorators
        renderDecorators(g);
        
        // 3. Draw static bodies
        renderStaticBodies(g);
        
        // 4. Update and draw dynamic bodies
        updateAndRenderDynamicBodies(g);
        
        // 5. Draw HUD
        renderHUD(g);
        
    } finally {
        g.dispose();
        strategy.show();
    }
}
```

#### Dynamic Body Snapshot Updates

The Renderer pulls fresh snapshots from the Controller each frame:

```java
private void updateAndRenderDynamicBodies(Graphics2D g) {
    long currentFrame = this.getCurrentFrame();
    
    // Pull fresh data from controller
    ArrayList<DynamicRenderDTO> freshData = 
        this.view.getController().getDBodyRenderData();
    
    // Update renderable map
    for (DynamicRenderDTO dto : freshData) {
        DynamicRenderable renderable = dynamicRenderables.get(dto.entityId);
        
        if (renderable == null) {
            // Create new renderable
            renderable = new DynamicRenderable(dto, imageCache, currentFrame);
            dynamicRenderables.put(dto.entityId, renderable);
        } else {
            // Update existing
            renderable.update(dto, currentFrame);
        }
        
        // Render
        renderable.render(g);
    }
    
    // Remove stale renderables
    dynamicRenderables.entrySet().removeIf(
        entry -> entry.getValue().getLastUpdateFrame() < currentFrame - 2
    );
}
```

---

## Entity System

The Entity System is built on a hierarchy rooted in `AbstractBody`, with different concrete types for dynamic, static, player, and decorator entities.

### Entity Hierarchy

```
AbstractBody (Abstract Base)
├── DynamicBody (Runnable - own thread)
│   ├── PlayerBody (adds weapon management)
│   └── Projectiles, Asteroids, etc.
├── StaticBody (no thread)
│   └── Walls, Platforms, Obstacles
└── DecoBody (decorator, no thread)
    └── Visual elements
```

### AbstractBody

AbstractBody provides the common foundation for all entities with lifecycle management, physics integration, and state tracking.

#### Key Features

- **Unique Identification**: UUID-based entity ID
- **Lifecycle States**: STARTING → ALIVE → DEAD
- **Static Counters**: Track created, alive, and dead entity counts
- **Physics Integration**: Each body has a PhysicsEngine (BasicPhysicsEngine or NullPhysicsEngine)
- **Lifespan Support**: Optional maximum life in seconds for temporary entities

#### Body States

```java
public enum BodyState {
    STARTING,  // Initial state before activation
    ALIVE,     // Active and simulating
    HANDS_OFF, // Temporarily locked during event processing
    DEAD       // Terminated
}
```

#### Implementation

```java
public abstract class AbstractBody {
    private static volatile int aliveQuantity = 0;
    private static volatile int createdQuantity = 0;
    private static volatile int deadQuantity = 0;

    private Model model = null;
    private volatile BodyState state;
    private final String entityId;
    private final PhysicsEngine phyEngine;
    private final long bornTime = System.nanoTime();
    private final double maxLifeInSeconds;

    public AbstractBody(PhysicsEngine phyEngine, double maxLifeInSeconds) {
        this.entityId = UUID.randomUUID().toString();
        this.phyEngine = phyEngine;
        this.state = BodyState.STARTING;
        this.maxLifeInSeconds = maxLifeInSeconds;
    }

    public synchronized void activate() {
        if (this.model == null) {
            throw new IllegalArgumentException("Model not setted");
        }

        if (!this.model.isAlive()) {
            throw new IllegalArgumentException(
                "Entity activation error due MODEL is not alive!");
        }

        if (this.state != BodyState.STARTING) {
            throw new IllegalArgumentException(
                "Entity activation error due is not starting!");
        }

        AbstractBody.aliveQuantity++;
        this.state = BodyState.ALIVE;
    }

    public synchronized void die() {
        this.state = BodyState.DEAD;
        AbstractBody.deadQuantity++;
        AbstractBody.aliveQuantity--;
    }

    public boolean isLifeOver() {
        if (this.maxLifeInSeconds <= 0) {
            return false;
        }
        return this.getLifeInSeconds() >= this.maxLifeInSeconds;
    }

    public double getLifeInSeconds() {
        return (System.nanoTime() - this.bornTime) / 1_000_000_000.0D;
    }

    public PhysicsValuesDTO getPhysicsValues() {
        return this.phyEngine.getPhysicsValues();
    }

    // Getters, setters...
}
```

---

### DynamicBody

DynamicBody represents entities with active physics simulation. Each dynamic body runs in its own thread, continuously updating its physics state and reporting to the Model for event processing.

#### Key Features

- **Per-Entity Threading**: Implements Runnable, runs in dedicated thread
- **Continuous Physics Updates**: Physics engine calculates new state each iteration
- **Event Reporting**: Reports physics changes to Model for event detection
- **State Synchronization**: Uses HANDS_OFF state during event processing
- **Bounded Actions**: Supports rebound, movement, and death actions

#### Threading Model

Each DynamicBody has its own thread that:
1. Calculates new physics values
2. Reports to Model for event processing
3. Waits briefly between iterations
4. Terminates when state becomes DEAD

#### Implementation

```java
public class DynamicBody extends AbstractBody implements PhysicsBody, Runnable {

    private Thread thread;
    private final BasicPhysicsEngine phyEngine;

    public DynamicBody(BasicPhysicsEngine phyEngine) {
        super(phyEngine);
        this.phyEngine = phyEngine;
    }

    public DynamicBody(BasicPhysicsEngine phyEngine, double maxLifeInSeconds) {
        super(phyEngine, maxLifeInSeconds);
        this.phyEngine = phyEngine;
    }

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
                Thread.sleep(5); // Brief pause between updates
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

    public void reboundInWest(PhysicsValuesDTO newPhy, PhysicsValuesDTO oldPhy,
                             int worldWidth, int worldHeight) {
        newPhy.posX = worldWidth;
        newPhy.speedX = -oldPhy.speedX;
        this.phyEngine.setPhysicsValues(newPhy);
    }

    // Additional rebound methods...
}
```

---

### StaticBody

StaticBody represents immovable entities with fixed positions. Unlike dynamic bodies, static bodies have no thread and use a NullPhysicsEngine that doesn't update.

#### Key Features

- **No Thread**: Activate() doesn't start a thread
- **Fixed Position**: Uses NullPhysicsEngine with constant values
- **Lightweight**: Minimal overhead for non-moving entities
- **Common Uses**: Walls, platforms, obstacles, decorative elements

#### Implementation

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

#### Usage Example

```java
// Create a wall at position (100, 50) with size 20
String wallId = model.addStaticBody(20, 100, 50, 0);

// Create a platform
String platformId = model.addStaticBody(30, 200, 300, 0);
```

---

### PlayerBody

PlayerBody extends DynamicBody with player-specific features including weapon management and control input handling.

#### Key Features

- **Weapon Management**: Multiple weapons with selection
- **Control Input**: Thrust, rotation, and firing controls
- **Fire Request System**: Thread-safe weapon firing requests
- **All DynamicBody Features**: Inherits physics and threading

#### Weapon Integration

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

#### Control Methods

```java
public void thrustOn() {
    this.getPhysicsEngine().setThrust(this.getPhysicsValues().thrust);
}

public void thrustOff() {
    this.getPhysicsEngine().setThrust(0);
}

public void reverseThrust() {
    this.getPhysicsEngine().setThrust(-this.getPhysicsValues().thrust);
}

public void rotateLeftOn() {
    this.addAngularAcceleration(-ROTATION_SPEED);
}

public void rotateRightOn() {
    this.addAngularAcceleration(ROTATION_SPEED);
}

public void rotateOff() {
    this.getPhysicsEngine().setAngularAcceleration(0);
}
```

---

## Weapon System

The Weapon System implements a request-based firing mechanism where weapons are passive components that respond to fire requests during their update cycle.

### AbstractWeapon

AbstractWeapon is the base class for all weapons, implementing a thread-safe fire request system with cooldown and ammo management.

#### Design Philosophy

- **Passive Component**: Weapons have no threads and perform no asynchronous work
- **Request-Based**: Fire requests are registered from any thread, consumed during update
- **Monotonic Requests**: Only the most recent fire request matters; no queuing
- **Deterministic**: All firing logic occurs during `mustFireNow(dtSeconds)` calls

#### Concurrency Model

```java
public abstract class AbstractWeapon implements Weapon {

    private final String id;
    private final WeaponDto weaponConfig;
    private final AtomicLong lastFireRequest = new AtomicLong(0L);
    protected long lastHandledRequest = 0L;
    protected int currentAmmo;

    public AbstractWeapon(WeaponDto weaponConfig) {
        if (weaponConfig.fireRate <= 0) {
            throw new IllegalArgumentException(
                    "fireRatePerSec must be > 0. Weapon not created");
        }

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

    // Abstract method: subclasses implement firing logic
    public abstract boolean mustFireNow(double dtSeconds);
}
```

#### Request Flow

1. **Register**: User input calls `registerFireRequest()` (from any thread)
2. **Check**: `hasRequest()` returns true if new request exists
3. **Process**: `mustFireNow(dtSeconds)` decides whether to fire
4. **Consume**: `markAllRequestsHandled()` marks request as processed

---

### Weapon Implementations

#### BasicWeapon

Simple semi-automatic weapon with cooldown between shots.

```java
public class BasicWeapon extends AbstractWeapon {

    private double cooldown = 0.0; // seconds

    public BasicWeapon(WeaponDto weaponConfig) {
        super(weaponConfig);
    }

    @Override
    public boolean mustFireNow(double dtSeconds) {
        if (this.cooldown > 0) {
            // Cool down weapon. Any pending requests are discarded.
            this.cooldown -= dtSeconds;
            this.markAllRequestsHandled();
            return false;
        }

        if (this.currentAmmo <= 0) {
            // No ammunition: reload, set time to reload and discard requests
            this.markAllRequestsHandled();
            cooldown = this.getWeaponConfig().reloadTime;
            this.currentAmmo = this.getWeaponConfig().maxAmmo;
            return false;
        }

        if (!this.hasRequest()) {
            // Nothing to do
            this.cooldown = 0;
            return false;
        }

        // Fire
        this.markAllRequestsHandled();
        this.currentAmmo--;
        cooldown = 1.0 / this.getWeaponConfig().fireRate;
        return true;
    }
}
```

#### BurstWeapon

Weapon that fires multiple shots in rapid succession per trigger pull.

```java
public class BurstWeapon extends AbstractWeapon {

    private double cooldown = 0.0d;
    private int shotsRemainingInBurst = 0;

    public BurstWeapon(WeaponDto weaponConfig) {
        super(weaponConfig);
    }

    @Override
    public boolean mustFireNow(double dtSeconds) {
        if (this.cooldown > 0) {
            this.cooldown -= dtSeconds;
            this.markAllRequestsHandled();
            return false;
        }

        if (this.currentAmmo <= 0) {
            // No ammunition: reload
            this.markAllRequestsHandled();
            this.shotsRemainingInBurst = 0;
            cooldown = this.getWeaponConfig().reloadTime;
            this.currentAmmo = this.getWeaponConfig().maxAmmo;
            return false;
        }

        if (this.shotsRemainingInBurst > 0) {
            // Burst mode ongoing - fire next shot
            this.markAllRequestsHandled();
            this.shotsRemainingInBurst--;
            this.currentAmmo--;

            if (this.shotsRemainingInBurst == 0) {
                // Burst finished
                this.cooldown = 1.0 / this.getWeaponConfig().fireRate;
            } else {
                // More shots in burst
                this.cooldown = 1.0 / this.getWeaponConfig().burstFireRate;
            }

            return true;
        }

        if (!this.hasRequest()) {
            return false;
        }

        // Start new burst
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

#### MissileLauncher and MineLauncher

Specialized weapons for different projectile types:

```java
public class MissileLauncher extends BasicWeapon {
    public MissileLauncher(WeaponDto weaponConfig) {
        super(weaponConfig);
    }
    // Uses BasicWeapon logic with missile-specific configuration
}

public class MineLauncher extends BasicWeapon {
    public MineLauncher(WeaponDto weaponConfig) {
        super(weaponConfig);
    }
    // Uses BasicWeapon logic with mine-specific configuration
}
```

---

### WeaponDto

Configuration data for weapons, defining all weapon parameters:

```java
public class WeaponDto {
    public final String weaponId;
    public final WeaponType weaponType;
    public final double fireRate;           // Shots per second
    public final double reloadTime;         // Seconds
    public final int maxAmmo;
    public final int burstSize;             // For burst weapons
    public final double burstFireRate;      // Shots per second within burst
    public final double firingSpeed;        // Projectile speed
    public final double acceleration;       // Projectile acceleration
    public final double projectileSize;
    public final String projectileAssetId;
    public final double shootingOffset;     // Spawn distance from player

    // Constructor with all parameters...
}
```

---

### WeaponFactory

Factory pattern for creating weapons based on configuration:

```java
public class WeaponFactory {

    public static Weapon create(WeaponDto weaponConfig) {
        switch (weaponConfig.weaponType) {
            case BASIC:
                return new BasicWeapon(weaponConfig);
            case BURST:
                return new BurstWeapon(weaponConfig);
            case MISSILE_LAUNCHER:
                return new MissileLauncher(weaponConfig);
            case MINE_LAUNCHER:
                return new MineLauncher(weaponConfig);
            default:
                throw new IllegalArgumentException(
                    "Unknown weapon type: " + weaponConfig.weaponType);
        }
    }
}
```

---

### Projectile Spawning

When a weapon's `mustFireNow()` returns true, the Model spawns a projectile:

```java
private void spawnProjectileFrom(DynamicBody shooter, 
                                PhysicsValuesDTO shooterNewPhy) {
    if (!(shooter instanceof PlayerBody)) {
        return;
    }
    PlayerBody pBody = (PlayerBody) shooter;

    Weapon activeWeapon = pBody.getActiveWeapon();
    if (activeWeapon == null) {
        return;
    }

    WeaponDto weaponConfig = activeWeapon.getWeaponConfig();
    if (weaponConfig == null) {
        return;
    }

    double angleDeg = shooterNewPhy.angle;
    double angleRad = Math.toRadians(angleDeg);

    double dirX = Math.cos(angleRad);
    double dirY = Math.sin(angleRad);

    double angleInRads = Math.toRadians(shooterNewPhy.angle - 90);
    double posX = shooterNewPhy.posX + 
                  Math.cos(angleInRads) * weaponConfig.shootingOffset;
    double posY = shooterNewPhy.posY + 
                  Math.sin(angleInRads) * weaponConfig.shootingOffset;

    double projSpeedX = shooterNewPhy.speedX + weaponConfig.firingSpeed * dirX;
    double projSpeedY = shooterNewPhy.speedY + weaponConfig.firingSpeed * dirY;

    double accX = weaponConfig.acceleration * dirX;
    double accY = weaponConfig.acceleration * dirY;

    String entityId = this.addDynamicBody(weaponConfig.projectileSize,
            posX, posY, projSpeedX, projSpeedY,
            accX, accY, angleDeg, 0d, 0d, 0d, maxLifeInSeconds);

    if (entityId != null && !entityId.isEmpty()) {
        this.domainEventProcessor.notifyNewProjectileFired(
                entityId, weaponConfig.projectileAssetId);
    }
}
```

---

## Threading Model

MVCGameEngine implements a unique **per-entity threading** model where each DynamicBody runs in its own thread. This enables true parallel physics simulation across multiple CPU cores.

### Thread Architecture

```
┌──────────────────────────────────────────────────────────┐
│                     Main/AWT Thread                       │
│           - Window management (JFrame)                    │
│           - Keyboard input capture                        │
└──────────────────────────────────────────────────────────┘
                           │
          ┌────────────────┼────────────────┐
          │                │                │
          ▼                ▼                ▼
    ┌──────────┐    ┌──────────┐    ┌──────────┐
    │ DBody 1  │    │ DBody 2  │    │ DBody N  │
    │ Thread   │    │ Thread   │    │ Thread   │
    │          │    │          │    │          │
    │ Physics  │    │ Physics  │    │ Physics  │
    │ Update   │    │ Update   │    │ Update   │
    └──────────┘    └──────────┘    └──────────┘
          │                │                │
          └────────────────┼────────────────┘
                           │
                           ▼
                    ┌──────────────┐
                    │    Model     │
                    │   (Event     │
                    │ Processing)  │
                    └──────────────┘
                           │
                           ▼
                    ┌──────────────┐
                    │  Renderer    │
                    │   Thread     │
                    │              │
                    │ Pull DTOs    │
                    │ Draw Frame   │
                    └──────────────┘
```

### Thread Categories

#### 1. Per-Entity Threads (DynamicBody)

Each DynamicBody runs in its own thread:

```java
@Override
public void run() {
    PhysicsValuesDTO newPhyValues;

    while ((this.getState() != BodyState.DEAD)
            && (this.getModel().getState() != ModelState.STOPPED)) {

        if ((this.getState() == BodyState.ALIVE)
                && (this.getModel().getState() == ModelState.ALIVE)) {

            // Calculate new physics state
            newPhyValues = this.phyEngine.calcNewPhysicsValues();
            
            // Report to Model for event processing
            this.getModel().processDBodyEvents(this, newPhyValues, 
                                               this.phyEngine.getPhysicsValues());
        }

        try {
            Thread.sleep(5); // Brief pause
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            break;
        }
    }
}
```

**Benefits**:
- True parallelism: Each entity updates independently
- Scales with CPU cores
- Simple entity lifecycle management

**Considerations**:
- Thread creation overhead (mitigated by entity pooling)
- Requires careful synchronization

#### 2. Renderer Thread

The Renderer runs in its own dedicated thread:

```java
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
```

The Renderer pulls snapshots from the Model via Controller each frame.

#### 3. Main/AWT Thread

Handles Swing UI events and keyboard input, delegating commands to the Controller.

### Synchronization Strategy

#### ConcurrentHashMap for Entity Storage

The Model uses ConcurrentHashMap for thread-safe entity management:

```java
private final Map<String, AbstractBody> dynamicBodies = 
    new ConcurrentHashMap<>(MAX_ENTITIES);
private final Map<String, AbstractBody> playerBodies = 
    new ConcurrentHashMap<>(10);
private final Map<String, AbstractBody> staticBodies = 
    new ConcurrentHashMap<>(100);
```

#### HANDS_OFF State for Event Processing

During event processing, entities are locked with HANDS_OFF state:

```java
public void processDBodyEvents(DynamicBody dynamicBody,
        PhysicsValuesDTO newPhyValues, PhysicsValuesDTO oldPhyValues) {
    
    if (!isProcessable(dynamicBody)) {
        return;
    }

    BodyState previousState = dynamicBody.getState();
    dynamicBody.setState(BodyState.HANDS_OFF); // Lock entity

    try {
        // Process events, resolve actions, execute actions
    } finally {
        if (dynamicBody.getState() == BodyState.HANDS_OFF) {
            dynamicBody.setState(BodyState.ALIVE); // Unlock
        }
    }
}
```

#### Atomic Operations for Weapons

Weapons use AtomicLong for thread-safe fire requests:

```java
private final AtomicLong lastFireRequest = new AtomicLong(0L);

@Override
public void registerFireRequest() {
    this.lastFireRequest.set(System.nanoTime());
}

protected boolean hasRequest() {
    return this.lastFireRequest.get() > this.lastHandledRequest;
}
```

#### Copy-on-Write for Static Renderables

Static bodies and decorators use volatile references with copy-on-write:

```java
// In Renderer
private volatile Map<String, Renderable> staticRenderables = new HashMap<>();
private volatile Map<String, Renderable> decoratorRenderables = new HashMap<>();

public void updateSBodyInfo(ArrayList<RenderDTO> sBodyData) {
    Map<String, Renderable> newMap = new HashMap<>(sBodyData.size());
    
    for (RenderDTO dto : sBodyData) {
        newMap.put(dto.entityId, new Renderable(dto, imageCache));
    }
    
    this.staticRenderables = newMap; // Atomic swap
}
```

### Threading Best Practices

1. **Immutable DTOs**: All data passed between threads uses immutable DTOs
2. **No Direct Access**: View/Renderer never access Model entities directly
3. **Volatile States**: Use volatile for state flags (ModelState, BodyState)
4. **Minimal Locks**: Rely on concurrent collections instead of explicit locks
5. **Entity Isolation**: Each entity thread is independent

---

## Data Transfer Objects (DTOs)

DTOs are immutable value objects used to transfer data between layers without exposing mutable state.

### PhysicsValuesDTO

Contains all physics state for an entity:

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
    
    // Constructor and copy methods...
}
```

### BodyDTO

Snapshot of body data for rendering:

```java
public class BodyDTO {
    public final String entityId;
    public final PhysicsValuesDTO physicsValues;
    
    public BodyDTO(String entityId, PhysicsValuesDTO physicsValues) {
        this.entityId = entityId;
        this.physicsValues = physicsValues;
    }
}
```

### EventDTO

Represents a detected event from an entity:

```java
public class EventDTO {
    public final AbstractBody entity;
    public final EventType type;
    
    public EventDTO(AbstractBody entity, EventType type) {
        this.entity = entity;
        this.type = type;
    }
}

public enum EventType {
    REACHED_EAST_LIMIT,
    REACHED_WEST_LIMIT,
    REACHED_NORTH_LIMIT,
    REACHED_SOUTH_LIMIT,
    MUST_FIRE,
    LIFE_OVER,
    COLLIDED
}
```

### ActionDTO

Represents an action to be executed:

```java
public class ActionDTO {
    public final ActionType type;
    public final ActionPriority priority;
    public final ActionExecutor executor;
    
    public ActionDTO(ActionType type, ActionPriority priority, 
                    ActionExecutor executor) {
        this.type = type;
        this.priority = priority;
        this.executor = executor;
    }
}

public enum ActionType {
    MOVE, REBOUND_IN_EAST, REBOUND_IN_WEST,
    REBOUND_IN_NORTH, REBOUND_IN_SOUTH,
    DIE, FIRE, EXPLODE_IN_FRAGMENTS, GO_INSIDE, NONE
}

public enum ActionExecutor {
    BODY,  // Action executed by the body itself
    MODEL  // Action executed by the Model
}
```

### DynamicRenderDTO

Extended rendering data for dynamic bodies:

```java
public class DynamicRenderDTO extends RenderDTO {
    public final double speedX;
    public final double speedY;
    
    // Additional dynamic-specific rendering data
}
```

---

## Design Patterns

### Patterns Used in MVCGameEngine

#### 1. Model-View-Controller (MVC)
- **Purpose**: Separate simulation, coordination, and presentation
- **Usage**: Core architecture separating Model, Controller, and View/Renderer
- **Benefits**: Clear responsibilities, testability, maintainability

#### 2. Data Transfer Object (DTO)
- **Purpose**: Transfer immutable data between layers
- **Usage**: BodyDTO, PhysicsValuesDTO, EventDTO, ActionDTO
- **Benefits**: Thread-safe communication, clean layer separation

#### 3. Factory Pattern
- **Purpose**: Encapsulate object creation
- **Usage**: WeaponFactory creates weapons based on WeaponDto
- **Implementation**:

```java
public class WeaponFactory {
    public static Weapon create(WeaponDto weaponConfig) {
        switch (weaponConfig.weaponType) {
            case BASIC:
                return new BasicWeapon(weaponConfig);
            case BURST:
                return new BurstWeapon(weaponConfig);
            case MISSILE_LAUNCHER:
                return new MissileLauncher(weaponConfig);
            default:
                throw new IllegalArgumentException("Unknown weapon type");
        }
    }
}
```

#### 4. Strategy Pattern
- **Purpose**: Different physics behaviors
- **Usage**: PhysicsEngine interface with BasicPhysicsEngine and NullPhysicsEngine
- **Benefits**: Flexibility in physics simulation approaches

#### 5. Event-Action Pattern
- **Purpose**: Decouple event detection from action execution
- **Usage**: Events detected in Model, actions decided by Controller, executed appropriately
- **Flow**: Event Detection → Action Resolution → Action Execution

#### 6. Ports and Adapters (Hexagonal Architecture)
- **Purpose**: Define interfaces (ports) for external interactions
- **Usage**: PhysicsEngine port, Weapon port, DomainEventProcessor port
- **Benefits**: Testability, flexibility, clear boundaries
    }
    
    public void release(Projectile projectile) {
        if (inUse.remove(projectile)) {
            projectile.reset();
            available.offer(projectile);
        }
    }
}
```

#### 7. Singleton Pattern
- **Purpose**: Global access to managers
- **Usage**: ResourceManager, SoundManager, GameModel (use sparingly)
- **Implementation**:

```java
public class ResourceManager {
    private static volatile ResourceManager instance;
    
    private ResourceManager() {}
    
    public static ResourceManager getInstance() {
        if (instance == null) {
            synchronized (ResourceManager.class) {
                if (instance == null) {
                    instance = new ResourceManager();
                }
            }
        }
        return instance;
    }
}
```

---

## Implementation Guidelines

### Project Structure

```
MVCGameEngine/
├── src/
│   ├── core/
│   │   ├── Controller.java
│   │   ├── Model.java
│   │   ├── View.java
│   │   └── Renderer.java
│   ├── entities/
│   ��   ├── Entity.java
│   │   ├── DynamicBody.java
│   │   ├── StaticBody.java
│   │   ├── Player.java
│   │   └── Enemy.java
│   ├── weapons/
│   │   ├── AbstractWeapon.java
│   │   ├── Pistol.java
│   │   └── Shotgun.java
│   ├── systems/
│   │   ├── PhysicsSystem.java
│   │   ├── CollisionSystem.java
│   │   └── RenderSystem.java
│   ├── utils/
│   │   ├── Vector2.java
│   │   ├── BoundingBox.java
│   │   └── MathUtils.java
│   └── Game.java
├── resources/
│   ├── textures/
│   ├── sounds/
│   └── shaders/
└── docs/
    └── ARCHITECTURE.md
```

### Coding Standards

#### Naming Conventions
- **Classes**: PascalCase (e.g., `DynamicBody`, `AbstractWeapon`)
- **Methods**: camelCase (e.g., `update()`, `applyForce()`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_VELOCITY`)
- **Private Fields**: camelCase with descriptive names

#### Documentation
- All public APIs must have Javadoc comments
- Include usage examples for complex methods
- Document thread-safety guarantees
- Explain design decisions in comments

#### Error Handling
- Use exceptions for exceptional conditions
- Validate input parameters
- Provide meaningful error messages
- Log errors with appropriate severity levels

### Performance Considerations

#### Memory Management
- **Object Pooling**: Reuse frequently created objects
- **Lazy Initialization**: Delay resource loading until needed
- **Resource Cleanup**: Properly dispose of resources
- **Avoid Allocations**: Minimize allocations in update loops

#### Rendering Optimization
- **Batch Rendering**: Group similar draw calls
- **Frustum Culling**: Don't render off-screen entities
- **Level of Detail**: Use simpler models at distance
- **Texture Atlases**: Combine textures to reduce state changes

#### Physics Optimization
- **Spatial Partitioning**: Use quadtrees or grids for collision detection
- **Sleeping Objects**: Don't simulate stationary objects
- **Broad Phase**: Quick rejection before detailed collision tests
- **Fixed Time Step**: Use consistent physics time step

---

## Best Practices

### 1. Separation of Concerns
- Keep MVC components independent
- Don't let View access Model directly
- Controller mediates all communication

### 2. Composition Over Inheritance
- Prefer component-based entity systems
- Use interfaces for flexibility
- Avoid deep inheritance hierarchies

### 3. Immutability Where Possible
- Use final fields for configuration
- Return defensive copies of mutable objects
- Consider immutable value objects (Vector2, Color)

### 4. Thread Safety
- Document thread-safety guarantees
- Use concurrent collections appropriately
- Minimize shared mutable state
- Prefer message passing over shared state

### 5. Testing
- Write unit tests for game logic
- Mock dependencies in tests
- Test edge cases and error conditions
- Use integration tests for system interactions

### 6. Resource Management
- Load resources asynchronously
- Implement proper cleanup (dispose methods)
- Use resource managers for caching
- Handle missing resources gracefully

### 7. Extensibility
- Design for extension (open/closed principle)
- Use dependency injection
- Provide clear extension points
- Document how to extend the engine

### 8. Performance Profiling
- Profile before optimizing
- Measure frame times and memory usage
- Identify hotspots with profiler
- Optimize the most impactful areas first

---

## Conclusion

MVCGameEngine provides a robust, scalable foundation for 2D game development. By following the MVC architecture and utilizing well-established design patterns, the engine maintains clean separation of concerns while providing the flexibility needed for diverse game implementations.

### Key Takeaways

1. **MVC Architecture**: Ensures maintainable and testable code
2. **Entity System**: Flexible hierarchy supporting various game object types
3. **Weapon System**: Extensible framework for diverse weapon mechanics
4. **Threading Model**: Optimized for performance with proper synchronization
5. **Design Patterns**: Proven solutions to common problems
6. **Best Practices**: Guidelines for writing quality game code

### Next Steps

- Review the example implementations in the repository
- Implement custom entities extending DynamicBody and StaticBody
- Create new weapon types by extending AbstractWeapon
- Experiment with different design patterns for your game mechanics
- Profile and optimize your game performance

### Additional Resources

- [MVC Pattern Documentation](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)
- [Game Programming Patterns](https://gameprogrammingpatterns.com/)
- [Java Concurrency in Practice](https://jcip.net/)
- [OpenGL Tutorial](https://learnopengl.com/)

---

**Document Version**: 1.0  
**Last Updated**: 2025-12-18  
**Maintainer**: MVCGameEngine Team
