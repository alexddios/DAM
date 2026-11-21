# MVC Pattern Implementation

## Overview

The MVC (Model-View-Controller) pattern in the Balls project strictly separates the simulation logic, visual presentation, and user interaction into three distinct components. This implementation demonstrates a real-world application of MVC in a multithreaded game engine context.

## What It Provides

### Clear Separation of Concerns
- **Model** handles all simulation logic, physics calculations, and entity management
- **View** manages rendering, asset loading, and visual presentation
- **Controller** coordinates communication, processes user input, and manages application lifecycle

### Thread Safety
- Model runs simulation in dedicated entity threads
- View runs rendering in a separate render thread
- Controller provides thread-safe snapshot access between components

### Maintainability
- Each component can be modified independently
- Business logic is isolated from presentation
- Easy to test individual components

## How It's Implemented

### Model Component

**Location**: `src/model/Model.java`

The Model manages the simulation state:

```java
public class Model implements Runnable {
    private final ConcurrentHashMap<String, DynamicBody> dBodies;
    private final ConcurrentHashMap<String, StaticBody> sBodies;
    private volatile ModelState modelState;
    
    // Returns immutable snapshots for rendering
    public List<DBodyInfoDTO> getDBodyInfo() { ... }
    public List<EntityInfoDTO> getSBodyInfo() { ... }
}
```

**Responsibilities**:
- Entity lifecycle management (creation, activation, destruction)
- Physics simulation coordination
- Thread-safe entity collections using `ConcurrentHashMap`
- Providing immutable snapshots via DTOs

**Key Features**:
- Each `DynamicBody` runs on its own thread
- Uses `volatile` variables for state transitions
- Never directly accessed by View

### View Component

**Location**: `src/view/View.java`

The View manages the presentation layer:

```java
public class View extends JFrame implements KeyListener {
    private Renderer renderer;
    private Controller controller;
    
    public void loadAssets(AssetCatalog catalog) { ... }
    public void activate() { ... }
}
```

**Responsibilities**:
- Window management using Java Swing
- Asset loading and image catalog management
- Keyboard input capture
- Delegating rendering to the Renderer

**Key Features**:
- No simulation logic
- Communicates with Model exclusively through Controller
- Runs rendering in dedicated thread

### Controller Component

**Location**: `src/controller/Controller.java`

The Controller coordinates the MVC components:

```java
public class Controller {
    private Model model;
    private View view;
    private volatile EngineState engineState;
    
    public void activate() { ... }
    public List<DBodyInfoDTO> getDBodyInfo() { ... }
    public void playerFire() { ... }
}
```

**Responsibilities**:
- Application bootstrap and initialization
- User input translation (keyboard → Model commands)
- Snapshot provision for rendering
- Game rules and decision making
- Engine lifecycle management

**Key Features**:
- Dependency injection for Model and View
- Validates dependencies before activation
- Provides thread-safe snapshot access

## Communication Flow

### 1. Initialization Sequence
```
Main → Controller.setModel(model)
     → Controller.setView(view)
     → Controller.setAssets(...)
     → Controller.activate()
         → View.loadAssets()
         → View.activate() [starts Renderer thread]
         → Model.activate() [starts simulation]
```

### 2. Rendering Flow (Pull-based)
```
Renderer thread → View.getDBodyInfo()
                → Controller.getDBodyInfo()
                → Model.getDBodyInfo()
                → Returns List<DBodyInfoDTO>
```

### 3. User Input Flow
```
User presses key → View.keyPressed()
                 → Controller.playerFire()
                 → Model.fireWeapon()
                 → Entity updates
```

### 4. Static Entity Updates (Push-based)
```
Controller.addSBody() → Model.addSBody()
                      → Model.getSBodyInfo()
                      → View.updateSBodyInfo()
```

## Implementation Details

### Snapshot Pattern

The implementation uses DTOs (Data Transfer Objects) for safe data transfer:

```java
// Immutable snapshot of dynamic entity state
public class DBodyInfoDTO {
    private final String entityId;
    private final String assetId;
    private final double posX, posY;
    private final double speedX, speedY;
    private final double angle;
    // ... more fields
}
```

**Benefits**:
- Thread-safe: immutable objects can be shared between threads
- No coupling: View doesn't access mutable Model state
- Clean interface: clear contract between components

### Threading Model

- **Model Thread**: Each `DynamicBody` has its own physics update thread
- **Render Thread**: View's `Renderer` runs independently at ~60 FPS
- **EDT (Event Dispatch Thread)**: Swing UI events processed here
- **Main Thread**: Handles initialization then terminates

### State Management

Each component maintains its own state:

```java
// Model state
enum ModelState { STARTING, ALIVE, STOPPED }

// Engine state (Controller)
enum EngineState { STARTING, ALIVE, PAUSED, STOPPED }

// Entity state
enum EntityState { STARTING, ALIVE, DEAD }
```

## Common Implementation Mistakes

### 1. Direct Model-View Communication

**Wrong**:
```java
// View directly accessing Model
public class View {
    private Model model; // ❌ Violates MVC
    
    public void render() {
        model.getEntities().forEach(...); // ❌ Direct access
    }
}
```

**Correct**:
```java
// View accesses Model through Controller
public class View {
    private Controller controller; // ✓ Proper separation
    
    public void render() {
        List<DBodyInfoDTO> entities = controller.getDBodyInfo(); // ✓
    }
}
```

### 2. Business Logic in View

**Wrong**:
```java
public class View {
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // ❌ Physics calculation in View
            double dx = Math.cos(angle) * thrust;
            double dy = Math.sin(angle) * thrust;
            entity.applyForce(dx, dy);
        }
    }
}
```

**Correct**:
```java
public class View {
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // ✓ Delegate to Controller
            controller.playerFire();
        }
    }
}
```

### 3. Mutable State Sharing

**Wrong**:
```java
public class Model {
    // ❌ Returning mutable internal state
    public Map<String, DynamicBody> getDynamicBodies() {
        return dBodies; // Direct reference
    }
}
```

**Correct**:
```java
public class Model {
    // ✓ Returning immutable snapshots
    public List<DBodyInfoDTO> getDBodyInfo() {
        return dBodies.values().stream()
            .map(DynamicBody::getInfo)
            .collect(Collectors.toList());
    }
}
```

### 4. Tight Coupling

**Wrong**:
```java
public class Controller {
    // ❌ Controller knows about specific View implementation
    public void updateDisplay() {
        ((SwingView) view).getJFrame().repaint();
    }
}
```

**Correct**:
```java
public class Controller {
    // ✓ Controller uses View interface
    public List<DBodyInfoDTO> getDBodyInfo() {
        return model.getDBodyInfo(); // View pulls when needed
    }
}
```

### 5. Synchronous Update Assumptions

**Wrong**:
```java
public class Controller {
    public void addEntity() {
        model.addDBody(...); // Async operation
        // ❌ Assuming entity is immediately available
        view.render(); 
    }
}
```

**Correct**:
```java
public class Controller {
    public void addEntity() {
        model.addDBody(...);
        // ✓ Let normal rendering cycle pick up changes
        // Entity will appear in next getDBodyInfo() call
    }
}
```

### 6. Ignoring Thread Safety

**Wrong**:
```java
public class Model {
    private List<DynamicBody> entities; // ❌ Not thread-safe
    
    public void addEntity(DynamicBody body) {
        entities.add(body); // ❌ Race condition
    }
}
```

**Correct**:
```java
public class Model {
    private final ConcurrentHashMap<String, DynamicBody> entities; // ✓
    
    public void addEntity(DynamicBody body) {
        entities.put(body.getId(), body); // ✓ Thread-safe
    }
}
```

## Best Practices

1. **Keep Controllers Thin**: Controllers should orchestrate, not implement business logic
2. **Use Immutable DTOs**: Always transfer data using immutable objects
3. **Validate in Activate**: Check all dependencies during the activation phase
4. **Document Threading**: Clearly document which thread accesses what
5. **State Machines**: Use enums for component lifecycle states
6. **Dependency Injection**: Use constructor or setter injection for testability
7. **Observer Pattern**: Consider using observers for Model→View updates in GUI applications

## Testing Considerations

### Model Testing
```java
@Test
public void testPhysicsUpdate() {
    Model model = new Model();
    model.setDimension(800, 600);
    // Test without View or Controller
    model.addDBody(...);
    // Verify physics calculations
}
```

### View Testing
```java
@Test
public void testRendering() {
    View view = new View();
    view.setDimension(800, 600);
    // Test with mock Controller
    MockController controller = new MockController();
    view.setController(controller);
    // Verify rendering logic
}
```

### Integration Testing
```java
@Test
public void testMVCIntegration() {
    Controller controller = new Controller();
    Model model = new Model();
    View view = new View();
    
    controller.setModel(model);
    controller.setView(view);
    controller.activate();
    
    // Test complete flow
}
```

## Related Patterns

- [Factory Pattern](Factory-Pattern.md) - Used for creating world configurations
- [Strategy Pattern](Strategy-Pattern.md) - Used for interchangeable physics engines
- [DTO Pattern](DTO-Pattern.md) - Used for data transfer between MVC layers

## References

- Source: `src/controller/Controller.java`
- Source: `src/model/Model.java`
- Source: `src/view/View.java`
- Architecture: [ARCHITECTURE.md](../../ARCHITECTURE.md)
