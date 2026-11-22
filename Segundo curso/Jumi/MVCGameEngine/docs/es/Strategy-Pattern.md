# Patrón Strategy

## Visión General

El Patrón Strategy es un patrón de diseño de comportamiento que define una familia de algoritmos, encapsula cada uno y los hace intercambiables. El patrón permite que el algoritmo varíe independientemente de los clientes que lo usan.

## Qué Aporta

### Flexibilidad de Algoritmos
- Intercambiar algoritmos en tiempo de ejecución sin cambiar el código cliente
- Añadir nuevos algoritmos sin modificar el código existente
- Elegir el mejor algoritmo basado en contexto o configuración

### Separación de Responsabilidades
- La implementación del algoritmo está separada de su uso
- Cada estrategia es independiente y puede probarse por separado
- Reduce la lógica condicional en el código cliente

### Principio Abierto/Cerrado
- Abierto para extensión (se pueden añadir nuevas estrategias)
- Cerrado para modificación (el código existente no cambia)

## Cómo se Implementa

### Patrón Strategy Básico

```java
// Interfaz de estrategia
public interface PhysicsEngine {
    PhysicsValues update(double dtSeconds);
    void applyThrust(double thrust);
    void applyAngularAcceleration(double angularAcc);
}

// Estrategia Concreta 1
public class BasicPhysicsEngine implements PhysicsEngine {
    @Override
    public PhysicsValues update(double dtSeconds) {
        // Cálculos físicos básicos con fricción y gravedad
    }
}

// Estrategia Concreta 2
public class SpinPhysicsEngine implements PhysicsEngine {
    @Override
    public PhysicsValues update(double dtSeconds) {
        // Física con rotación mejorada y efectos de giro
    }
}

// Estrategia Concreta 3
public class NullPhysicsEngine implements PhysicsEngine {
    @Override
    public PhysicsValues update(double dtSeconds) {
        // Sin física - para objetos estáticos
        return currentValues; // Sin cambios
    }
}

// Contexto - usa una estrategia
public class DynamicBody {
    private PhysicsEngine physicsEngine;
    
    public void setPhysicsEngine(PhysicsEngine engine) {
        this.physicsEngine = engine;
    }
    
    public void update(double dtSeconds) {
        // Delegar a la estrategia
        PhysicsValues newValues = physicsEngine.update(dtSeconds);
    }
}
```

### Strategy en el Proyecto Balls

**Ubicación**: `src/model/physics/*PhysicsEngine.java`

El proyecto usa el patrón Strategy para motores físicos intercambiables:

```java
// Cada entidad puede usar física diferente
DynamicBody asteroid = new DynamicBody();
asteroid.setPhysicsEngine(new SpinPhysicsEngine(initialValues));

DynamicBody missile = new DynamicBody();
missile.setPhysicsEngine(new BasicPhysicsEngine(initialValues));

StaticBody planet = new StaticBody();
planet.setPhysicsEngine(new NullPhysicsEngine(initialValues));
```

## Beneficios

1. **Flexibilidad en Tiempo de Ejecución**: Cambiar comportamiento en tiempo de ejecución
2. **Testabilidad**: Cada estrategia puede probarse independientemente
3. **Mantenibilidad**: Las estrategias están aisladas y fáciles de modificar
4. **Extensibilidad**: Añadir nuevas estrategias sin cambiar el código existente
5. **Complejidad Reducida**: Elimina lógica condicional compleja

## Variantes

### Strategy Simple
Implementación básica con interfaz y clases concretas (mostrado arriba).

### Strategy con Factory
Combinar con patrón Factory para creación:
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

### Strategy con Estado de Contexto
El contexto mantiene estado y delega a la estrategia:
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

## Errores Comunes de Implementación

### 1. Estrategia con Demasiado Estado

**Incorrecto**:
```java
public class PhysicsEngine {
    // ❌ Estrategia no debería mantener estado específico de entidad
    private double posX, posY;
    private double speedX, speedY;
    private String entityId;
    
    public void update() {
        // Modifica estado interno
        this.posX += this.speedX;
        this.posY += this.speedY;
    }
}
```

**Correcto**:
```java
public class PhysicsEngine {
    // ✓ Estrategia opera sobre datos proporcionados
    public PhysicsValues update(PhysicsValues currentValues, double dt) {
        // Retorna nuevos valores, no modifica estado
        double newX = currentValues.getPosX() + currentValues.getSpeedX() * dt;
        double newY = currentValues.getPosY() + currentValues.getSpeedY() * dt;
        return new PhysicsValues(newX, newY, ...);
    }
}
```

### 2. Código Cliente Conoce Estrategias Concretas

**Incorrecto**:
```java
public class Game {
    public void updateEntity(Entity entity) {
        // ❌ Cliente conoce tipos de estrategia concretos
        if (entity.getPhysicsEngine() instanceof BasicPhysicsEngine) {
            ((BasicPhysicsEngine) entity.getPhysicsEngine()).applyFriction();
        } else if (entity.getPhysicsEngine() instanceof SpinPhysicsEngine) {
            ((SpinPhysicsEngine) entity.getPhysicsEngine()).applyTorque();
        }
    }
}
```

**Correcto**:
```java
public class Game {
    public void updateEntity(Entity entity) {
        // ✓ Cliente usa solo interfaz de estrategia
        PhysicsEngine engine = entity.getPhysicsEngine();
        engine.update(deltaTime);
    }
}

// Si se necesita comportamiento diferente, añadir a la interfaz
public interface PhysicsEngine {
    void update(double dt);
    void applyForce(double fx, double fy); // Interfaz común
}
```

### 3. Demasiadas Estrategias Pequeñas

**Incorrecto**:
```java
// ❌ Sobre-ingeniería con demasiadas estrategias triviales
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

**Correcto**:
```java
// ✓ Usar estrategia para algoritmos complejos y variados
public interface SortStrategy {
    void sort(int[] array);
}

public class QuickSort implements SortStrategy { ... }
public class MergeSort implements SortStrategy { ... }
public class HeapSort implements SortStrategy { ... }
```

### 4. Estrategia con Efectos Secundarios

**Incorrecto**:
```java
public class PaymentStrategy {
    public boolean pay(double amount) {
        // ❌ Estrategia modifica estado externo
        Database.getInstance().updateBalance(amount);
        Logger.getInstance().log("Pago procesado");
        EmailService.getInstance().sendReceipt();
        return true;
    }
}
```

**Correcto**:
```java
public class PaymentStrategy {
    private final Database db;
    private final Logger logger;
    private final EmailService email;
    
    // ✓ Dependencias inyectadas, efectos secundarios claros
    public PaymentStrategy(Database db, Logger logger, EmailService email) {
        this.db = db;
        this.logger = logger;
        this.email = email;
    }
    
    public PaymentResult pay(double amount) {
        // Claro lo que sucede
        db.updateBalance(amount);
        logger.log("Pago procesado");
        email.sendReceipt();
        return new PaymentResult(true, amount);
    }
}
```

### 5. No Usar la Interfaz Correctamente

**Incorrecto**:
```java
public interface Strategy {
    void execute();
}

public class ConcreteStrategy implements Strategy {
    public void execute() { ... }
    
    // ❌ Métodos no en la interfaz
    public void specialMethod() { ... }
    public int getSpecialValue() { ... }
}

// Código cliente
Strategy strategy = getStrategy();
if (strategy instanceof ConcreteStrategy) {
    ((ConcreteStrategy) strategy).specialMethod(); // ❌ Derrota el propósito
}
```

**Correcto**:
```java
public interface Strategy {
    void execute();
    // ✓ Todos los métodos necesarios en la interfaz
    void special();
    int getValue();
}

public class ConcreteStrategy implements Strategy {
    public void execute() { ... }
    public void special() { ... }
    public int getValue() { ... }
}

// Código cliente
Strategy strategy = getStrategy();
strategy.special(); // ✓ Usa interfaz
```

### 6. Selección de Estrategia Hardcodeada

**Incorrecto**:
```java
public class Context {
    public Context(String difficulty) {
        // ❌ Selección de estrategia hardcodeada
        if (difficulty.equals("easy")) {
            this.strategy = new EasyStrategy();
        } else if (difficulty.equals("hard")) {
            this.strategy = new HardStrategy();
        }
    }
}
```

**Correcto**:
```java
public class Context {
    private Strategy strategy;
    
    // ✓ Estrategia inyectada desde fuera
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}

// Factory o builder crea estrategias
Context context = new Context(
    StrategyFactory.create(difficulty)
);
```

## Mejores Prácticas

1. **Mantén las Estrategias Sin Estado**: O estado mínimo que es específico de la estrategia, no del contexto
2. **Usa Inyección de Dependencias**: Inyecta estrategias en lugar de crearlas internamente
3. **Interfaz Común**: Asegura que todas las estrategias implementen la misma interfaz completamente
4. **Resultados Inmutables**: Las estrategias deberían retornar nuevos objetos en lugar de modificar la entrada
5. **Nombres Claros**: Los nombres de estrategias deben indicar claramente su algoritmo
6. **Documenta las Diferencias**: Explica cuándo usar cada estrategia
7. **Seguridad de Hilos**: Haz las estrategias seguras para hilos si se usan concurrentemente

## Cuándo Usar

Usa el Patrón Strategy cuando:
- Tienes múltiples algoritmos para la misma tarea
- Quieres cambiar algoritmos en tiempo de ejecución
- Tienes lógica condicional compleja basada en elección de algoritmo
- Diferentes clientes necesitan diferentes variaciones de un algoritmo
- Quieres aislar los detalles de implementación del algoritmo

## Cuándo No Usar

Evita el Patrón Strategy cuando:
- Solo tienes un algoritmo
- El algoritmo nunca cambia
- El overhead de clases extra no está justificado
- La lógica condicional simple es más clara

## Ejemplos del Mundo Real

### Algoritmos de Ordenamiento
```java
public interface SortStrategy {
    void sort(int[] array);
}

List<Integer> data = new ArrayList<>();
Sorter sorter = new Sorter();

// Dataset pequeño - usar insertion sort
sorter.setStrategy(new InsertionSort());
sorter.sort(data);

// Dataset grande - usar merge sort
sorter.setStrategy(new MergeSort());
sorter.sort(data);
```

### Algoritmos de Compresión
```java
public interface CompressionStrategy {
    byte[] compress(byte[] data);
    byte[] decompress(byte[] data);
}

Compressor compressor = new Compressor();
compressor.setStrategy(new ZipCompression());    // Rápido, compresión moderada
compressor.setStrategy(new GzipCompression());   // Más lento, mejor compresión
compressor.setStrategy(new Bzip2Compression());  // Más lento, mejor compresión
```

### Procesamiento de Pagos
```java
public interface PaymentStrategy {
    PaymentResult process(double amount);
}

PaymentProcessor processor = new PaymentProcessor();
processor.setStrategy(new CreditCardPayment());
processor.setStrategy(new PayPalPayment());
processor.setStrategy(new CryptocurrencyPayment());
```

### Planificación de Rutas
```java
public interface RouteStrategy {
    Route calculate(Location start, Location end);
}

Navigator navigator = new Navigator();
navigator.setStrategy(new FastestRouteStrategy());
navigator.setStrategy(new ShortestRouteStrategy());
navigator.setStrategy(new ScenicRouteStrategy());
```

## Comparación con Patrones Similares

### Strategy vs State
- **Strategy**: Se enfoca en algoritmos intercambiables
- **State**: Se enfoca en el comportamiento del objeto cambiando con estado interno

### Strategy vs Command
- **Strategy**: Diferentes maneras de hacer algo
- **Command**: Encapsula una solicitud como un objeto

### Strategy vs Template Method
- **Strategy**: Usa composición (tiene-un)
- **Template Method**: Usa herencia (es-un)

## Testing de Estrategias

```java
@Test
public void testBasicPhysicsEngine() {
    PhysicsEngine engine = new BasicPhysicsEngine();
    PhysicsValues initial = new PhysicsValues(0, 0, 10, 0, ...);
    
    PhysicsValues result = engine.update(initial, 1.0);
    
    assertEquals(10.0, result.getPosX(), 0.01); // Se movió 10 unidades
}

@Test
public void testStrategySwapping() {
    DynamicBody entity = new DynamicBody();
    entity.setPhysicsEngine(new BasicPhysicsEngine());
    
    entity.update(1.0);
    double pos1 = entity.getPosition();
    
    // Intercambiar estrategia
    entity.setPhysicsEngine(new SpinPhysicsEngine());
    
    entity.update(1.0);
    double pos2 = entity.getPosition();
    
    assertNotEquals(pos1, pos2); // Comportamiento diferente
}
```

## Patrones Relacionados

- [Patrón Factory](Factory-Pattern.md) - A menudo usado para crear estrategias
- **Patrón State** - Estructura similar, intención diferente
- **Patrón Template Method** - Enfoque alternativo usando herencia
- **Patrón Command** - Encapsula acciones como objetos

## Referencias

- Fuente: `src/model/physics/PhysicsEngine.java`
- Fuente: `src/model/physics/BasicPhysicsEngine.java`
- Fuente: `src/model/physics/SpinPhysicsEngine.java`
- Fuente: `src/model/physics/NullPhysicsEngine.java`
- Gang of Four Design Patterns
- [Refactoring Guru - Patrón Strategy](https://refactoring.guru/es/design-patterns/strategy)
