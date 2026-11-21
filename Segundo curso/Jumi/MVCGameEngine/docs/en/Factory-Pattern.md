# Factory Pattern

## Overview

The Factory Pattern is a creational design pattern that provides an interface for creating objects without specifying their exact classes. Instead of using direct object construction (using `new`), factory methods encapsulate the object creation logic.

## What It Provides

### Flexibility in Object Creation
- Centralizes object creation logic
- Allows creating different objects based on configuration or context
- Hides complex construction logic from clients

### Loose Coupling
- Client code doesn't depend on concrete classes
- Easy to introduce new types without changing client code
- Follows the Open/Closed Principle (open for extension, closed for modification)

### Code Reusability
- Creation logic can be reused across the application
- Reduces code duplication
- Provides consistent object initialization

## How It's Implemented

### Basic Factory Method

```java
// Product interface
public interface PhysicsEngine {
    PhysicsValues update(double dtSeconds);
}

// Concrete products
public class BasicPhysicsEngine implements PhysicsEngine { ... }
public class SpinPhysicsEngine implements PhysicsEngine { ... }
public class NullPhysicsEngine implements PhysicsEngine { ... }

// Factory method
public class PhysicsEngineFactory {
    public static PhysicsEngine create(String type, PhysicsValues initial) {
        switch (type) {
            case "basic": return new BasicPhysicsEngine(initial);
            case "spin": return new SpinPhysicsEngine(initial);
            case "null": return new NullPhysicsEngine(initial);
            default: throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}

// Client usage
PhysicsEngine engine = PhysicsEngineFactory.create("basic", initialValues);
```

### Factory in Balls Project

**Location**: `src/world/providers/RandomWorldDefinitionProvider.java`

The project uses the Factory pattern to create world configurations:

```java
public class RandomWorldDefinitionProvider implements WorldDefinitionProvider {
    @Override
    public WorldDefinition provide() {
        WorldDefinition world = new WorldDefinition();
        
        // Factory method creates different world configurations
        world.setWorldWidth(random(MIN_WIDTH, MAX_WIDTH));
        world.setWorldHeight(random(MIN_HEIGHT, MAX_HEIGHT));
        world.setAssetCatalog(createAssetCatalog());
        world.setBackgroundDef(createBackgroundDefinition());
        world.setDynamicBodyDefs(createDynamicBodies());
        world.setStaticBodyDefs(createStaticBodies());
        
        return world;
    }
    
    private AssetCatalog createAssetCatalog() {
        // Creates and configures asset catalog
    }
    
    private List<DynamicBodyDef> createDynamicBodies() {
        // Creates dynamic body definitions
    }
}
```

## Benefits

1. **Encapsulation**: Object creation logic is hidden from client code
2. **Flexibility**: Easy to add new product types
3. **Consistency**: Ensures objects are created correctly every time
4. **Testing**: Easy to substitute mock objects in tests
5. **Configuration**: Can create different objects based on configuration files

## Variants

### Simple Factory
A single class with a method that returns different object types:
```java
public class SimpleFactory {
    public static Product createProduct(String type) {
        if (type.equals("A")) return new ProductA();
        if (type.equals("B")) return new ProductB();
        return null;
    }
}
```

### Factory Method Pattern
Each product type has its own factory:
```java
public abstract class ProductFactory {
    public abstract Product createProduct();
}

public class ProductAFactory extends ProductFactory {
    public Product createProduct() {
        return new ProductA();
    }
}
```

### Abstract Factory Pattern
Creates families of related objects:
```java
public interface GUIFactory {
    Button createButton();
    TextField createTextField();
}

public class WindowsFactory implements GUIFactory {
    public Button createButton() { return new WindowsButton(); }
    public TextField createTextField() { return new WindowsTextField(); }
}

public class MacFactory implements GUIFactory {
    public Button createButton() { return new MacButton(); }
    public TextField createTextField() { return new MacTextField(); }
}
```

## Common Implementation Mistakes

### 1. Factory Becomes God Object

**Wrong**:
```java
public class MegaFactory {
    // ❌ Factory knows about too many unrelated types
    public Object create(String type) {
        switch (type) {
            case "physics": return new PhysicsEngine();
            case "weapon": return new Weapon();
            case "entity": return new Entity();
            case "asset": return new Asset();
            case "ui": return new UIComponent();
            // ... hundreds of cases
        }
    }
}
```

**Correct**:
```java
// ✓ Separate factories for different domains
public class PhysicsEngineFactory {
    public PhysicsEngine create(String type) { ... }
}

public class WeaponFactory {
    public Weapon create(String type) { ... }
}

public class EntityFactory {
    public Entity create(String type) { ... }
}
```

### 2. Tight Coupling to Concrete Classes

**Wrong**:
```java
public class WorldFactory {
    public World create() {
        // ❌ Directly instantiating concrete classes
        World world = new World();
        world.addEntity(new Asteroid());
        world.addEntity(new Spaceship());
        world.addEntity(new Planet());
        return world;
    }
}
```

**Correct**:
```java
public class WorldFactory {
    private EntityFactory entityFactory;
    
    public World create(WorldDefinition def) {
        // ✓ Using definitions and other factories
        World world = new World();
        for (EntityDef entityDef : def.getEntityDefs()) {
            world.addEntity(entityFactory.create(entityDef));
        }
        return world;
    }
}
```

### 3. No Validation

**Wrong**:
```java
public class Factory {
    public Product create(String type) {
        // ❌ No validation
        return new Product(type);
    }
}
```

**Correct**:
```java
public class Factory {
    private static final Set<String> VALID_TYPES = 
        Set.of("type1", "type2", "type3");
    
    public Product create(String type) {
        // ✓ Validate input
        if (!VALID_TYPES.contains(type)) {
            throw new IllegalArgumentException(
                "Invalid type: " + type);
        }
        return new Product(type);
    }
}
```

### 4. Complex Construction in Factory

**Wrong**:
```java
public class EntityFactory {
    public Entity create(EntityDef def) {
        // ❌ Too much logic in factory
        Entity entity = new Entity();
        entity.setPosition(def.getX(), def.getY());
        entity.setVelocity(calculateVelocity(def));
        entity.setAcceleration(calculateAcceleration(def));
        entity.setPhysicsEngine(createPhysicsEngine(def));
        entity.setWeapon(createWeapon(def));
        entity.setAssets(loadAssets(def));
        entity.validate();
        entity.initialize();
        return entity;
    }
}
```

**Correct**:
```java
public class EntityFactory {
    public Entity create(EntityDef def) {
        // ✓ Delegate to builder or entity constructor
        return new EntityBuilder()
            .from(def)
            .build();
    }
}

// Or use constructor with all parameters
public Entity create(EntityDef def) {
    return new Entity(def);
}
```

### 5. Not Using Dependency Injection

**Wrong**:
```java
public class Factory {
    public Product create() {
        // ❌ Factory creates all dependencies
        Database db = new Database();
        Logger logger = new Logger();
        Config config = new Config();
        return new Product(db, logger, config);
    }
}
```

**Correct**:
```java
public class Factory {
    private final Database db;
    private final Logger logger;
    private final Config config;
    
    // ✓ Dependencies injected into factory
    public Factory(Database db, Logger logger, Config config) {
        this.db = db;
        this.logger = logger;
        this.config = config;
    }
    
    public Product create() {
        return new Product(db, logger, config);
    }
}
```

### 6. Returning Null

**Wrong**:
```java
public class Factory {
    public Product create(String type) {
        if (type.equals("A")) return new ProductA();
        if (type.equals("B")) return new ProductB();
        // ❌ Returning null for unknown types
        return null;
    }
}
```

**Correct**:
```java
public class Factory {
    public Product create(String type) {
        if (type.equals("A")) return new ProductA();
        if (type.equals("B")) return new ProductB();
        // ✓ Throw exception for invalid input
        throw new IllegalArgumentException("Unknown type: " + type);
    }
}

// Or use Optional
public Optional<Product> create(String type) {
    if (type.equals("A")) return Optional.of(new ProductA());
    if (type.equals("B")) return Optional.of(new ProductB());
    return Optional.empty();
}
```

## Best Practices

1. **Keep Factories Focused**: One factory per product family
2. **Validate Inputs**: Check parameters before creating objects
3. **Use Enums**: Replace string types with enums when possible
4. **Document Creation Logic**: Explain complex initialization
5. **Consider Builders**: For objects with many optional parameters
6. **Thread Safety**: Make factories thread-safe if used concurrently
7. **Immutable Products**: Consider returning immutable objects when appropriate

## When to Use

Use the Factory Pattern when:
- Object creation is complex or requires multiple steps
- You need to create different types based on configuration
- You want to hide implementation details from clients
- You need to centralize object creation for consistency
- You want to make your code more testable

## When Not to Use

Avoid the Factory Pattern when:
- Object creation is simple (`new Product()` is sufficient)
- You only have one product type
- The creation logic never changes
- It adds unnecessary complexity

## Real-World Examples

### Web Frameworks
```java
// Creating different database connections
ConnectionFactory factory = new ConnectionFactory();
Connection conn = factory.create("mysql"); // or "postgresql", "oracle"
```

### GUI Libraries
```java
// Creating platform-specific UI components
WidgetFactory factory = WidgetFactory.getInstance();
Button button = factory.createButton(); // Windows/Mac/Linux button
```

### Game Engines
```java
// Creating different entity types
EntityFactory factory = new EntityFactory();
Entity player = factory.create("player");
Entity enemy = factory.create("enemy");
Entity powerup = factory.create("powerup");
```

## Related Patterns

- [Strategy Pattern](Strategy-Pattern.md) - Factories often create Strategy objects
- [Singleton Pattern](Singleton-Pattern.md) - Factories are sometimes implemented as Singletons
- **Builder Pattern** - Alternative for complex object construction
- **Prototype Pattern** - Alternative that clones existing objects

## References

- Source: `src/world/providers/RandomWorldDefinitionProvider.java`
- Gang of Four Design Patterns
- [Refactoring Guru - Factory Pattern](https://refactoring.guru/design-patterns/factory-method)
