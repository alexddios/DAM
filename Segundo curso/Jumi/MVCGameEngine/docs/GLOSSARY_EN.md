# Glossary of Concepts - MVCGameEngine

**English** | **[Español](GLOSSARY.md)**

This document provides a glossary of the most important concepts used in the MVCGameEngine project, a real-time 2D physics engine implemented in Java.

## Architecture and Design Patterns

### MVC (Model-View-Controller)
Architectural pattern that separates the application into three main components:
- **Model**: Manages game state and simulation logic
- **View**: Handles visual presentation and rendering
- **Controller**: Coordinates communication between Model and View, processing user input

### Model
Component that represents the game logic layer. Manages all entities, physics simulation, and world state. Runs on its own thread to keep the simulation independent from rendering.

### View
Presentation layer that handles visual rendering using Java Swing. Requests game state snapshots through the Controller and renders them on screen. Contains no simulation logic.

### Controller
Central coordinator of the MVC architecture. Connects Model and View, manages engine initialization, processes user commands, and provides access to state snapshots for rendering.

## Bodies

### AbstractBody
Abstract base class for all bodies in the simulation. Defines:
- Unique identifier (`entityId`)
- Lifecycle state (`BodyState`: STARTING, ALIVE, DEAD)
- Physics engine reference (`PhysicsEngine`)
- Size (`size`)
- Maximum lifetime (optional)
- Static counters for tracking created, alive, and dead bodies

### DynamicBody
Dynamic entity that moves and rotates according to physics laws. Features:
- Runs on its own dedicated thread
- Has a `PhysicsEngine` that calculates its movement
- Continuously updates its position, velocity, and acceleration
- Interacts with the world (collisions, edge bounces)
- Examples: asteroids, projectiles

### StaticBody
Static entity that doesn't move during simulation. Features:
- Has no thread
- Uses `NullPhysicsEngine` (no physics calculations)
- Fixed position, rotation, and size
- Used for obstacles, static visual decoration
- Examples: planets, decorative obstacles

### PlayerBody
Special entity that extends `DynamicBody` and represents the player. Features:
- Keyboard control (thrust, rotation, fire)
- Weapon system with multiple slots
- Configurable parameters: maximum thrust force, angular acceleration
- Unique player identifier (`playerId`)

### DecoBody
Purely decorative body with no physics or game logic. Used for visual elements that don't interact with the world (background elements, temporary visual effects).

### PhysicsBody
Interface that marks bodies having physical behavior. Provides default methods for:
- Getting physics values (`PhysicsValuesDTO`)
- Applying movement
- Managing bounces at world edges

### BodyState
Enumeration defining body lifecycle states:
- **STARTING**: Body created but not activated
- **ALIVE**: Body active in simulation
- **DEAD**: Body marked for removal

### BodyDTO
Data Transfer Object (DTO) containing immutable information about a body for safe transfer between Model and View layers. Includes entityId, assetId, size, position (x, y), and angle.

## Physics Engine

### PhysicsEngine
Interface defining the contract for physics engines. Responsibilities:
- Calculate new physics values based on elapsed time
- Manage bounces at world boundaries
- Update thrust and angular acceleration values

### BasicPhysicsEngine
Concrete implementation of `PhysicsEngine` that applies basic physics:
- MRUA integration (Uniformly Accelerated Rectilinear Motion)
- Velocity calculation: v₁ = v₀ + a·Δt
- Position calculation: x₁ = x₀ + v_avg·Δt
- Thrust application according to rotation angle
- Friction and elasticity management in bounces

### NullPhysicsEngine
"Null" physics engine used by `StaticBody`. Performs no physics calculations, maintaining constant values.

### AbstractPhysicsEngine
Abstract base class providing common implementation for physics engines, including physics value management and bounces.

### PhysicsValuesDTO
Immutable object that encapsulates the complete physical state of a body at a specific moment:
- **timeStamp**: Timestamp in nanoseconds
- **posX, posY**: Position in 2D space
- **speedX, speedY**: Velocity (x, y components)
- **accX, accY**: Acceleration (x, y components)
- **angle**: Rotation angle in degrees
- **angularSpeed**: Angular velocity (degrees/second)
- **angularAcc**: Angular acceleration (degrees/second²)
- **thrust**: Applied thrust force

## Event and Action System

### ActionDTO (Data Transfer Object)
Data transfer object that encapsulates an action to execute:
- **type**: Action type (`ActionType`)
- **executor**: Executor that will process the action (`ActionExecutor`)
- **priority**: Execution priority (`ActionPriority`)

### EventDTO (Data Transfer Object)
Object representing an event in the simulation:
- **entity**: Entity generating the event
- **eventType**: Event type (`EventType`)

### ActionType
Enumeration of possible action types in the system (e.g., move, rotate, fire).

### EventType
Enumeration of event types that can occur in simulation (e.g., collision, out of bounds).

### ActionExecutor
Interface defining objects capable of executing actions on the model.

### ActionPriority
Enumeration defining priority levels for action execution.

## System States

### ModelState
Enumeration defining Model lifecycle states:
- **STARTING**: Initializing
- **ALIVE**: Running simulation
- **STOPPED**: Stopped

### EngineState
Enumeration defining complete engine states (Controller).

## World and Generation

### WorldDefinition
Object defining the complete configuration of a world:
- World dimensions (`worldWidth`, `worldHeight`)
- Visual asset catalog (`AssetCatalog`)
- Background, decorator, and gravitational body definitions
- Asteroid, spaceship, and weapon configurations

### SceneGenerator
Class responsible for generating the initial static scene based on a `WorldDefinition`. Creates and places all static bodies and decorators.

### LifeGenerator
Automatic generator of dynamic bodies that maintains simulation activity, creating new dynamic bodies when needed and managing player creation.

## Weapon System

### Weapon
Interface/base class for weapon systems that can fire projectiles.

### BasicWeapon
Basic weapon implementation with:
- Projectile configuration (`WeaponDto`)
- Fire rate control
- Fire request system

### WeaponDto
Configuration object for weapons defining projectile properties and weapon behavior.

## Assets and Rendering

### AssetCatalog
Catalog that organizes and manages all visual resources (sprites, images) used in the game.

### AssetType
Enumeration of asset types (backgrounds, solid_bodies, space_decors, spaceship, weapons, etc.).

### EntityInfoDTO
Data transfer object containing information for rendering a static entity:
- Entity ID
- Asset ID
- Size
- Position (x, y)
- Rotation angle

### DBodyInfoDTO
Extension of `EntityInfoDTO` for dynamic entities, adding:
- Timestamp
- Velocity (speedX, speedY)
- Acceleration (accX, accY)

### Renderer
Component that handles the rendering loop, drawing the current game state on screen using snapshots provided by the Controller.

## Utilities

### DoubleVector
Utility class for 2D vector mathematics, providing common vector operations.

### Images
Image loading and caching system for efficient graphics resource management.

### Fx (Effects)
Visual effects system for animations and particles.

## Physics Concepts

### Thrust
Force applied to an entity in the direction of its current angle. Used to propel spaceships and other dynamic objects.

### Angular Velocity
Rotation speed of an entity, measured in degrees per second.

### Angular Acceleration
Change in angular velocity per unit time, measured in degrees per second squared.

### Elasticity
Property determining how much kinetic energy is conserved during a collision or bounce (value between 0 and 1).

### Friction
Force opposing motion, gradually reducing entity velocities.

## Concurrent Programming

### Thread-Safe Collections
Thread-safe collections (like `ConcurrentHashMap`) used for managing entities in a multithreaded environment.

### Volatile Variables
Variables marked as `volatile` to ensure visibility across threads (e.g., `ModelState`, `BodyState`, `EngineState`). Ensures all threads always see the latest value.

### Immutable Objects
Immutable objects like `PhysicsValuesDTO` and other DTOs that ensure concurrency safety by not allowing modification after creation. This allows sharing them between threads without synchronization.

---

## Execution Flow

1. **Initialization**: `Main` creates Controller, Model, and View
2. **Asset Loading**: Controller loads visual resources into View
3. **Scene Generation**: SceneGenerator creates the static scene (static bodies and decorators) based on WorldDefinition
4. **Activation**: Model and View activate, starting their execution loops
5. **Life Generation**: LifeGenerator creates players and manages programmatic generation of dynamic bodies
6. **Simulation Loop**: DynamicBody instances calculate physics on separate threads
7. **Rendering Loop**: View requests snapshots and renders current state
8. **Input Processing**: Controller translates keyboard input into Model actions
9. **Event Processing**: Model manages events (collisions, bounces) and executes actions through the ActionDTO/EventDTO system
