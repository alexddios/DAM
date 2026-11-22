# Strategy Pattern

## Overview

The Strategy Pattern is a behavioral design pattern that defines a family of algorithms, encapsulates each one, and makes them interchangeable. The pattern lets the algorithm vary independently from clients that use it.

## What It Provides

### Algorithm Flexibility
- Swap algorithms at runtime without changing client code
- Add new algorithms without modifying existing code
- Choose the best algorithm based on context or configuration

### Separation of Concerns
- Algorithm implementation is separate from its usage
- Each strategy is independent and can be tested separately
- Reduces conditional logic in client code

### Open/Closed Principle
- Open for extension (new strategies can be added)
- Closed for modification (existing code doesn't change)

## How It's Implemented

### Basic Strategy Pattern

```java
// Strategy interface
public interface PhysicsEngine {
    PhysicsValues update(double dtSeconds);
    void applyThrust(double thrust);
    void applyAngularAcceleration(double angularAcc);
}

// Concrete Strategy 1
public class BasicPhysicsEngine implements PhysicsEngine {
    @Override
    public PhysicsValues update(double dtSeconds) {
        // Basic physics calculations with friction and gravity
    }
}

// Concrete Strategy 2
public class SpinPhysicsEngine implements PhysicsEngine {
    @Override
    public PhysicsValues update(double dtSeconds) {
        // Physics with enhanced rotation and spin effects
    }
}

// Concrete Strategy 3
public class NullPhysicsEngine implements PhysicsEngine {
    @Override
    public PhysicsValues update(double dtSeconds) {
        // No physics - for static objects
        return currentValues; // No changes
    }
}

// Context - uses a strategy
public class DynamicBody {
    private PhysicsEngine physicsEngine;
    
    public void setPhysicsEngine(PhysicsEngine engine) {
        this.physicsEngine = engine;
    }
    
    public void update(double dtSeconds) {
        // Delegate to strategy
        PhysicsValues newValues = physicsEngine.update(dtSeconds);
    }
}
```

### Strategy in Balls Project

**Location**: `src/model/physics/*PhysicsEngine.java`

The project uses the Strategy pattern for interchangeable physics engines:

```java
// Each entity can use different physics
DynamicBody asteroid = new DynamicBody();
asteroid.setPhysicsEngine(new SpinPhysicsEngine(initialValues));

DynamicBody missile = new DynamicBody();
missile.setPhysicsEngine(new BasicPhysicsEngine(initialValues));

StaticBody planet = new StaticBody();
planet.setPhysicsEngine(new NullPhysicsEngine(initialValues));
```

## Benefits

1. **Runtime Flexibility**: Change behavior at runtime
2. **Testability**: Each strategy can be tested independently
3. **Maintainability**: Strategies are isolated and easy to modify
4. **Extensibility**: Add new strategies without changing existing code
5. **Reduced Complexity**: Eliminates complex conditional logic

## Variants

### Simple Strategy
Basic implementation with interface and concrete classes (shown above).

### Strategy with Factory
Combine with Factory pattern for creation:
```java
public class PhysicsEngineFactory {
    public static PhysicsEngine create(String type) {
        switch (type) {
            case "basic": return new BasicPhysicsEngine();
            case "spin": return new SpinPhysicsEngine();
            case "null": return new NullPhysicsEngine();
            default: throw new IllegalArgumentException();
        }
    }
}
```

### Strategy with Context State
Context maintains state and delegates to strategy:
```java
public class Character {
    private MovementStrategy movement;
    private AttackStrategy attack;
    
    public void changeMovementMode(MovementStrategy newMovement) {
        this.movement = newMovement;
    }
    
    public void move() {
        movement.execute(this);
    }
}
```

## Common Implementation Mistakes

### 1. Strategy with Too Much State

**Wrong**:
```java
public class PhysicsEngine {
    // ❌ Strategy shouldn't hold entity-specific state
    private double posX, posY;
    private double speedX, speedY;
    private String entityId;
    
    public void update() {
        // Modifies internal state
        this.posX += this.speedX;
        this.posY += this.speedY;
    }
}
```

**Correct**:
```java
public class PhysicsEngine {
    // ✓ Strategy operates on provided data
    public PhysicsValues update(PhysicsValues currentValues, double dt) {
        // Returns new values, doesn't modify state
        double newX = currentValues.getPosX() + currentValues.getSpeedX() * dt;
        double newY = currentValues.getPosY() + currentValues.getSpeedY() * dt;
        return new PhysicsValues(newX, newY, ...);
    }
}
```

### 2. Client Code Knows About Concrete Strategies

**Wrong**:
```java
public class Game {
    public void updateEntity(Entity entity) {
        // ❌ Client knows about concrete strategy types
        if (entity.getPhysicsEngine() instanceof BasicPhysicsEngine) {
            ((BasicPhysicsEngine) entity.getPhysicsEngine()).applyFriction();
        } else if (entity.getPhysicsEngine() instanceof SpinPhysicsEngine) {
            ((SpinPhysicsEngine) entity.getPhysicsEngine()).applyTorque();
        }
    }
}
```

**Correct**:
```java
public class Game {
    public void updateEntity(Entity entity) {
        // ✓ Client uses strategy interface only
        PhysicsEngine engine = entity.getPhysicsEngine();
        engine.update(deltaTime);
    }
}

// If different behavior is needed, add to interface
public interface PhysicsEngine {
    void update(double dt);
    void applyForce(double fx, double fy); // Common interface
}
```

### 3. Too Many Small Strategies

**Wrong**:
```java
// ❌ Over-engineering with too many trivial strategies
public interface AddStrategy {
    int add(int a, int b);
}

public class SimpleAddStrategy implements AddStrategy {
    public int add(int a, int b) { return a + b; }
}

public class SafeAddStrategy implements AddStrategy {
    public int add(int a, int b) {
        if (willOverflow(a, b)) throw new ArithmeticException();
        return a + b;
    }
}
```

**Correct**:
```java
// ✓ Use strategy for complex, varied algorithms
public interface SortStrategy {
    void sort(int[] array);
}

public class QuickSort implements SortStrategy { ... }
public class MergeSort implements SortStrategy { ... }
public class HeapSort implements SortStrategy { ... }
```

### 4. Strategy with Side Effects

**Wrong**:
```java
public class PaymentStrategy {
    public boolean pay(double amount) {
        // ❌ Strategy modifies external state
        Database.getInstance().updateBalance(amount);
        Logger.getInstance().log("Payment processed");
        EmailService.getInstance().sendReceipt();
        return true;
    }
}
```

**Correct**:
```java
public class PaymentStrategy {
    private final Database db;
    private final Logger logger;
    private final EmailService email;
    
    // ✓ Dependencies injected, clear side effects
    public PaymentStrategy(Database db, Logger logger, EmailService email) {
        this.db = db;
        this.logger = logger;
        this.email = email;
    }
    
    public PaymentResult pay(double amount) {
        // Clear what happens
        db.updateBalance(amount);
        logger.log("Payment processed");
        email.sendReceipt();
        return new PaymentResult(true, amount);
    }
}
```

### 5. Not Using the Interface Properly

**Wrong**:
```java
public interface Strategy {
    void execute();
}

public class ConcreteStrategy implements Strategy {
    public void execute() { ... }
    
    // ❌ Methods not in interface
    public void specialMethod() { ... }
    public int getSpecialValue() { ... }
}

// Client code
Strategy strategy = getStrategy();
if (strategy instanceof ConcreteStrategy) {
    ((ConcreteStrategy) strategy).specialMethod(); // ❌ Defeats purpose
}
```

**Correct**:
```java
public interface Strategy {
    void execute();
    // ✓ All needed methods in interface
    void special();
    int getValue();
}

public class ConcreteStrategy implements Strategy {
    public void execute() { ... }
    public void special() { ... }
    public int getValue() { ... }
}

// Client code
Strategy strategy = getStrategy();
strategy.special(); // ✓ Uses interface
```

### 6. Hardcoded Strategy Selection

**Wrong**:
```java
public class Context {
    public Context(String difficulty) {
        // ❌ Strategy selection hardcoded
        if (difficulty.equals("easy")) {
            this.strategy = new EasyStrategy();
        } else if (difficulty.equals("hard")) {
            this.strategy = new HardStrategy();
        }
    }
}
```

**Correct**:
```java
public class Context {
    private Strategy strategy;
    
    // ✓ Strategy injected from outside
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}

// Factory or builder creates strategies
Context context = new Context(
    StrategyFactory.create(difficulty)
);
```

## Best Practices

1. **Keep Strategies Stateless**: Or minimal state that's strategy-specific, not context-specific
2. **Use Dependency Injection**: Inject strategies rather than creating them internally
3. **Common Interface**: Ensure all strategies implement the same interface completely
4. **Immutable Results**: Strategies should return new objects rather than modifying input
5. **Clear Naming**: Strategy names should clearly indicate their algorithm
6. **Document Differences**: Explain when to use each strategy
7. **Thread Safety**: Make strategies thread-safe if used concurrently

## When to Use

Use the Strategy Pattern when:
- You have multiple algorithms for the same task
- You want to switch algorithms at runtime
- You have complex conditional logic based on algorithm choice
- Different clients need different variations of an algorithm
- You want to isolate algorithm implementation details

## When Not to Use

Avoid the Strategy Pattern when:
- You only have one algorithm
- The algorithm never changes
- The overhead of extra classes isn't justified
- Simple conditional logic is clearer

## Real-World Examples

### Sorting Algorithms
```java
public interface SortStrategy {
    void sort(int[] array);
}

List<Integer> data = new ArrayList<>();
Sorter sorter = new Sorter();

// Small dataset - use insertion sort
sorter.setStrategy(new InsertionSort());
sorter.sort(data);

// Large dataset - use merge sort
sorter.setStrategy(new MergeSort());
sorter.sort(data);
```

### Compression Algorithms
```java
public interface CompressionStrategy {
    byte[] compress(byte[] data);
    byte[] decompress(byte[] data);
}

Compressor compressor = new Compressor();
compressor.setStrategy(new ZipCompression());    // Fast, moderate compression
compressor.setStrategy(new GzipCompression());   // Slower, better compression
compressor.setStrategy(new Bzip2Compression());  // Slowest, best compression
```

### Payment Processing
```java
public interface PaymentStrategy {
    PaymentResult process(double amount);
}

PaymentProcessor processor = new PaymentProcessor();
processor.setStrategy(new CreditCardPayment());
processor.setStrategy(new PayPalPayment());
processor.setStrategy(new CryptocurrencyPayment());
```

### Route Planning
```java
public interface RouteStrategy {
    Route calculate(Location start, Location end);
}

Navigator navigator = new Navigator();
navigator.setStrategy(new FastestRouteStrategy());
navigator.setStrategy(new ShortestRouteStrategy());
navigator.setStrategy(new ScenicRouteStrategy());
```

## Comparison with Similar Patterns

### Strategy vs State
- **Strategy**: Focuses on interchangeable algorithms
- **State**: Focuses on object behavior changing with internal state

### Strategy vs Command
- **Strategy**: Different ways to do something
- **Command**: Encapsulates a request as an object

### Strategy vs Template Method
- **Strategy**: Uses composition (has-a)
- **Template Method**: Uses inheritance (is-a)

## Testing Strategies

```java
@Test
public void testBasicPhysicsEngine() {
    PhysicsEngine engine = new BasicPhysicsEngine();
    PhysicsValues initial = new PhysicsValues(0, 0, 10, 0, ...);
    
    PhysicsValues result = engine.update(initial, 1.0);
    
    assertEquals(10.0, result.getPosX(), 0.01); // Moved 10 units
}

@Test
public void testStrategySwapping() {
    DynamicBody entity = new DynamicBody();
    entity.setPhysicsEngine(new BasicPhysicsEngine());
    
    entity.update(1.0);
    double pos1 = entity.getPosition();
    
    // Swap strategy
    entity.setPhysicsEngine(new SpinPhysicsEngine());
    
    entity.update(1.0);
    double pos2 = entity.getPosition();
    
    assertNotEquals(pos1, pos2); // Different behavior
}
```

## Related Patterns

- [Factory Pattern](Factory-Pattern.md) - Often used to create strategies
- **State Pattern** - Similar structure, different intent
- **Template Method Pattern** - Alternative approach using inheritance
- **Command Pattern** - Encapsulates actions as objects

## References

- Source: `src/model/physics/PhysicsEngine.java`
- Source: `src/model/physics/BasicPhysicsEngine.java`
- Source: `src/model/physics/SpinPhysicsEngine.java`
- Source: `src/model/physics/NullPhysicsEngine.java`
- Gang of Four Design Patterns
- [Refactoring Guru - Strategy Pattern](https://refactoring.guru/design-patterns/strategy)
