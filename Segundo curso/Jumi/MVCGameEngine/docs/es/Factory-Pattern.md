# Patrón Factory

## Visión General

El Patrón Factory es un patrón de diseño creacional que proporciona una interfaz para crear objetos sin especificar sus clases exactas. En lugar de usar construcción directa de objetos (usando `new`), los métodos factory encapsulan la lógica de creación de objetos.

## Qué Aporta

### Flexibilidad en la Creación de Objetos
- Centraliza la lógica de creación de objetos
- Permite crear diferentes objetos basados en configuración o contexto
- Oculta la lógica de construcción compleja de los clientes

### Bajo Acoplamiento
- El código cliente no depende de clases concretas
- Fácil introducir nuevos tipos sin cambiar el código cliente
- Sigue el Principio Abierto/Cerrado (abierto para extensión, cerrado para modificación)

### Reutilización de Código
- La lógica de creación puede reutilizarse en toda la aplicación
- Reduce la duplicación de código
- Proporciona inicialización consistente de objetos

## Cómo se Implementa

### Método Factory Básico

```java
// Interfaz de producto
public interface PhysicsEngine {
    PhysicsValues update(double dtSeconds);
}

// Productos concretos
public class BasicPhysicsEngine implements PhysicsEngine { ... }
public class SpinPhysicsEngine implements PhysicsEngine { ... }
public class NullPhysicsEngine implements PhysicsEngine { ... }

// Método factory
public class PhysicsEngineFactory {
    public static PhysicsEngine create(String type, PhysicsValues initial) {
        switch (type) {
            case "basic": return new BasicPhysicsEngine(initial);
            case "spin": return new SpinPhysicsEngine(initial);
            case "null": return new NullPhysicsEngine(initial);
            default: throw new IllegalArgumentException("Tipo desconocido: " + type);
        }
    }
}

// Uso del cliente
PhysicsEngine engine = PhysicsEngineFactory.create("basic", initialValues);
```

### Factory en el Proyecto Balls

**Ubicación**: `src/world/providers/RandomWorldDefinitionProvider.java`

El proyecto usa el patrón Factory para crear configuraciones de mundo:

```java
public class RandomWorldDefinitionProvider implements WorldDefinitionProvider {
    @Override
    public WorldDefinition provide() {
        WorldDefinition world = new WorldDefinition();
        
        // El método factory crea diferentes configuraciones de mundo
        world.setWorldWidth(random(MIN_WIDTH, MAX_WIDTH));
        world.setWorldHeight(random(MIN_HEIGHT, MAX_HEIGHT));
        world.setAssetCatalog(createAssetCatalog());
        world.setBackgroundDef(createBackgroundDefinition());
        world.setDynamicBodyDefs(createDynamicBodies());
        world.setStaticBodyDefs(createStaticBodies());
        
        return world;
    }
    
    private AssetCatalog createAssetCatalog() {
        // Crea y configura catálogo de assets
    }
    
    private List<DynamicBodyDef> createDynamicBodies() {
        // Crea definiciones de cuerpos dinámicos
    }
}
```

## Beneficios

1. **Encapsulación**: La lógica de creación de objetos está oculta del código cliente
2. **Flexibilidad**: Fácil añadir nuevos tipos de productos
3. **Consistencia**: Asegura que los objetos se creen correctamente cada vez
4. **Testing**: Fácil sustituir objetos mock en pruebas
5. **Configuración**: Puede crear diferentes objetos basados en archivos de configuración

## Variantes

### Factory Simple
Una sola clase con un método que retorna diferentes tipos de objetos:
```java
public class SimpleFactory {
    public static Product createProduct(String type) {
        if (type.equals("A")) return new ProductA();
        if (type.equals("B")) return new ProductB();
        return null;
    }
}
```

### Patrón Factory Method
Cada tipo de producto tiene su propia factory:
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

### Patrón Abstract Factory
Crea familias de objetos relacionados:
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

## Errores Comunes de Implementación

### 1. Factory se Convierte en Objeto Dios

**Incorrecto**:
```java
public class MegaFactory {
    // ❌ Factory conoce demasiados tipos no relacionados
    public Object create(String type) {
        switch (type) {
            case "physics": return new PhysicsEngine();
            case "weapon": return new Weapon();
            case "entity": return new Entity();
            case "asset": return new Asset();
            case "ui": return new UIComponent();
            // ... cientos de casos
        }
    }
}
```

**Correcto**:
```java
// ✓ Factories separadas para diferentes dominios
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

### 2. Acoplamiento Estrecho a Clases Concretas

**Incorrecto**:
```java
public class WorldFactory {
    public World create() {
        // ❌ Instanciando directamente clases concretas
        World world = new World();
        world.addEntity(new Asteroid());
        world.addEntity(new Spaceship());
        world.addEntity(new Planet());
        return world;
    }
}
```

**Correcto**:
```java
public class WorldFactory {
    private EntityFactory entityFactory;
    
    public World create(WorldDefinition def) {
        // ✓ Usando definiciones y otras factories
        World world = new World();
        for (EntityDef entityDef : def.getEntityDefs()) {
            world.addEntity(entityFactory.create(entityDef));
        }
        return world;
    }
}
```

### 3. Sin Validación

**Incorrecto**:
```java
public class Factory {
    public Product create(String type) {
        // ❌ Sin validación
        return new Product(type);
    }
}
```

**Correcto**:
```java
public class Factory {
    private static final Set<String> VALID_TYPES = 
        Set.of("tipo1", "tipo2", "tipo3");
    
    public Product create(String type) {
        // ✓ Validar entrada
        if (!VALID_TYPES.contains(type)) {
            throw new IllegalArgumentException(
                "Tipo inválido: " + type);
        }
        return new Product(type);
    }
}
```

### 4. Construcción Compleja en Factory

**Incorrecto**:
```java
public class EntityFactory {
    public Entity create(EntityDef def) {
        // ❌ Demasiada lógica en factory
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

**Correcto**:
```java
public class EntityFactory {
    public Entity create(EntityDef def) {
        // ✓ Delegar a builder o constructor de entidad
        return new EntityBuilder()
            .from(def)
            .build();
    }
}

// O usar constructor con todos los parámetros
public Entity create(EntityDef def) {
    return new Entity(def);
}
```

### 5. No Usar Inyección de Dependencias

**Incorrecto**:
```java
public class Factory {
    public Product create() {
        // ❌ Factory crea todas las dependencias
        Database db = new Database();
        Logger logger = new Logger();
        Config config = new Config();
        return new Product(db, logger, config);
    }
}
```

**Correcto**:
```java
public class Factory {
    private final Database db;
    private final Logger logger;
    private final Config config;
    
    // ✓ Dependencias inyectadas en factory
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

### 6. Retornar Null

**Incorrecto**:
```java
public class Factory {
    public Product create(String type) {
        if (type.equals("A")) return new ProductA();
        if (type.equals("B")) return new ProductB();
        // ❌ Retornar null para tipos desconocidos
        return null;
    }
}
```

**Correcto**:
```java
public class Factory {
    public Product create(String type) {
        if (type.equals("A")) return new ProductA();
        if (type.equals("B")) return new ProductB();
        // ✓ Lanzar excepción para entrada inválida
        throw new IllegalArgumentException("Tipo desconocido: " + type);
    }
}

// O usar Optional
public Optional<Product> create(String type) {
    if (type.equals("A")) return Optional.of(new ProductA());
    if (type.equals("B")) return Optional.of(new ProductB());
    return Optional.empty();
}
```

## Mejores Prácticas

1. **Mantén las Factories Enfocadas**: Una factory por familia de productos
2. **Valida las Entradas**: Verifica parámetros antes de crear objetos
3. **Usa Enums**: Reemplaza tipos string con enums cuando sea posible
4. **Documenta la Lógica de Creación**: Explica la inicialización compleja
5. **Considera Builders**: Para objetos con muchos parámetros opcionales
6. **Seguridad de Hilos**: Haz las factories seguras para hilos si se usan concurrentemente
7. **Productos Inmutables**: Considera retornar objetos inmutables cuando sea apropiado

## Cuándo Usar

Usa el Patrón Factory cuando:
- La creación de objetos es compleja o requiere múltiples pasos
- Necesitas crear diferentes tipos basados en configuración
- Quieres ocultar detalles de implementación de los clientes
- Necesitas centralizar la creación de objetos para consistencia
- Quieres hacer tu código más testeable

## Cuándo No Usar

Evita el Patrón Factory cuando:
- La creación de objetos es simple (`new Product()` es suficiente)
- Solo tienes un tipo de producto
- La lógica de creación nunca cambia
- Añade complejidad innecesaria

## Ejemplos del Mundo Real

### Frameworks Web
```java
// Crear diferentes conexiones de base de datos
ConnectionFactory factory = new ConnectionFactory();
Connection conn = factory.create("mysql"); // o "postgresql", "oracle"
```

### Librerías GUI
```java
// Crear componentes UI específicos de plataforma
WidgetFactory factory = WidgetFactory.getInstance();
Button button = factory.createButton(); // Botón Windows/Mac/Linux
```

### Motores de Juegos
```java
// Crear diferentes tipos de entidades
EntityFactory factory = new EntityFactory();
Entity player = factory.create("player");
Entity enemy = factory.create("enemy");
Entity powerup = factory.create("powerup");
```

## Patrones Relacionados

- [Patrón Strategy](Strategy-Pattern.md) - Las factories a menudo crean objetos Strategy
- **Patrón Singleton** - Las factories a veces se implementan como Singletons
- **Patrón Builder** - Alternativa para construcción de objetos complejos
- **Patrón Prototype** - Alternativa que clona objetos existentes

## Referencias

- Fuente: `src/world/providers/RandomWorldDefinitionProvider.java`
- Gang of Four Design Patterns
- [Refactoring Guru - Patrón Factory](https://refactoring.guru/es/design-patterns/factory-method)
