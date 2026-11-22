# Implementación del Patrón MVC

## Visión General

El patrón MVC (Modelo-Vista-Controlador) en el proyecto Balls separa estrictamente la lógica de simulación, la presentación visual y la interacción del usuario en tres componentes distintos. Esta implementación demuestra una aplicación del mundo real de MVC en un contexto de motor de juego multihilo.

## Qué Aporta

### Clara Separación de Responsabilidades
- **Modelo** maneja toda la lógica de simulación, cálculos físicos y gestión de entidades
- **Vista** gestiona el renderizado, carga de assets y presentación visual
- **Controlador** coordina la comunicación, procesa la entrada del usuario y gestiona el ciclo de vida de la aplicación

### Seguridad de Hilos
- El Modelo ejecuta la simulación en hilos dedicados de entidades
- La Vista ejecuta el renderizado en un hilo de renderizado separado
- El Controlador proporciona acceso seguro a instantáneas entre componentes

### Mantenibilidad
- Cada componente puede modificarse independientemente
- La lógica de negocio está aislada de la presentación
- Fácil de probar componentes individuales

## Cómo se Implementa

### Componente Modelo

**Ubicación**: `src/model/Model.java`

El Modelo gestiona el estado de la simulación:

```java
public class Model implements Runnable {
    private final ConcurrentHashMap<String, DynamicBody> dBodies;
    private final ConcurrentHashMap<String, StaticBody> sBodies;
    private volatile ModelState modelState;
    
    // Retorna instantáneas inmutables para renderizado
    public List<DBodyInfoDTO> getDBodyInfo() { ... }
    public List<EntityInfoDTO> getSBodyInfo() { ... }
}
```

**Responsabilidades**:
- Gestión del ciclo de vida de entidades (creación, activación, destrucción)
- Coordinación de simulación física
- Colecciones de entidades seguras para hilos usando `ConcurrentHashMap`
- Proporcionar instantáneas inmutables vía DTOs

**Características Clave**:
- Cada `DynamicBody` se ejecuta en su propio hilo
- Usa variables `volatile` para transiciones de estado
- Nunca accedido directamente por la Vista

### Componente Vista

**Ubicación**: `src/view/View.java`

La Vista gestiona la capa de presentación:

```java
public class View extends JFrame implements KeyListener {
    private Renderer renderer;
    private Controller controller;
    
    public void loadAssets(AssetCatalog catalog) { ... }
    public void activate() { ... }
}
```

**Responsabilidades**:
- Gestión de ventana usando Java Swing
- Carga de assets y gestión de catálogo de imágenes
- Captura de entrada de teclado
- Delegación del renderizado al Renderer

**Características Clave**:
- Sin lógica de simulación
- Se comunica con el Modelo exclusivamente a través del Controlador
- Ejecuta el renderizado en hilo dedicado

### Componente Controlador

**Ubicación**: `src/controller/Controller.java`

El Controlador coordina los componentes MVC:

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

**Responsabilidades**:
- Arranque e inicialización de la aplicación
- Traducción de entrada del usuario (teclado → comandos del Modelo)
- Provisión de instantáneas para renderizado
- Reglas del juego y toma de decisiones
- Gestión del ciclo de vida del motor

**Características Clave**:
- Inyección de dependencias para Modelo y Vista
- Valida dependencias antes de la activación
- Proporciona acceso seguro a instantáneas

## Flujo de Comunicación

### 1. Secuencia de Inicialización
```
Main → Controller.setModel(model)
     → Controller.setView(view)
     → Controller.setAssets(...)
     → Controller.activate()
         → View.loadAssets()
         → View.activate() [inicia hilo Renderer]
         → Model.activate() [inicia simulación]
```

### 2. Flujo de Renderizado (Basado en Pull)
```
Hilo Renderer → View.getDBodyInfo()
              → Controller.getDBodyInfo()
              → Model.getDBodyInfo()
              → Retorna List<DBodyInfoDTO>
```

### 3. Flujo de Entrada del Usuario
```
Usuario presiona tecla → View.keyPressed()
                       → Controller.playerFire()
                       → Model.fireWeapon()
                       → Entidad se actualiza
```

### 4. Actualizaciones de Entidades Estáticas (Basado en Push)
```
Controller.addSBody() → Model.addSBody()
                      → Model.getSBodyInfo()
                      → View.updateSBodyInfo()
```

## Detalles de Implementación

### Patrón de Instantánea

La implementación usa DTOs (Objetos de Transferencia de Datos) para transferencia segura de datos:

```java
// Instantánea inmutable del estado de entidad dinámica
public class DBodyInfoDTO {
    private final String entityId;
    private final String assetId;
    private final double posX, posY;
    private final double speedX, speedY;
    private final double angle;
    // ... más campos
}
```

**Beneficios**:
- Seguro para hilos: objetos inmutables pueden compartirse entre hilos
- Sin acoplamiento: la Vista no accede al estado mutable del Modelo
- Interfaz limpia: contrato claro entre componentes

### Modelo de Hilos

- **Hilo del Modelo**: Cada `DynamicBody` tiene su propio hilo de actualización física
- **Hilo de Renderizado**: El `Renderer` de la Vista se ejecuta independientemente a ~60 FPS
- **EDT (Event Dispatch Thread)**: Eventos UI de Swing procesados aquí
- **Hilo Principal**: Maneja la inicialización y luego termina

### Gestión de Estado

Cada componente mantiene su propio estado:

```java
// Estado del Modelo
enum ModelState { STARTING, ALIVE, STOPPED }

// Estado del Motor (Controlador)
enum EngineState { STARTING, ALIVE, PAUSED, STOPPED }

// Estado de Entidad
enum EntityState { STARTING, ALIVE, DEAD }
```

## Errores Comunes de Implementación

### 1. Comunicación Directa Modelo-Vista

**Incorrecto**:
```java
// Vista accediendo directamente al Modelo
public class View {
    private Model model; // ❌ Viola MVC
    
    public void render() {
        model.getEntities().forEach(...); // ❌ Acceso directo
    }
}
```

**Correcto**:
```java
// Vista accede al Modelo a través del Controlador
public class View {
    private Controller controller; // ✓ Separación apropiada
    
    public void render() {
        List<DBodyInfoDTO> entities = controller.getDBodyInfo(); // ✓
    }
}
```

### 2. Lógica de Negocio en la Vista

**Incorrecto**:
```java
public class View {
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // ❌ Cálculo físico en Vista
            double dx = Math.cos(angle) * thrust;
            double dy = Math.sin(angle) * thrust;
            entity.applyForce(dx, dy);
        }
    }
}
```

**Correcto**:
```java
public class View {
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // ✓ Delegar al Controlador
            controller.playerFire();
        }
    }
}
```

### 3. Compartir Estado Mutable

**Incorrecto**:
```java
public class Model {
    // ❌ Retornar estado interno mutable
    public Map<String, DynamicBody> getDynamicBodies() {
        return dBodies; // Referencia directa
    }
}
```

**Correcto**:
```java
public class Model {
    // ✓ Retornar instantáneas inmutables
    public List<DBodyInfoDTO> getDBodyInfo() {
        return dBodies.values().stream()
            .map(DynamicBody::getInfo)
            .collect(Collectors.toList());
    }
}
```

### 4. Acoplamiento Estrecho

**Incorrecto**:
```java
public class Controller {
    // ❌ Controlador conoce implementación específica de Vista
    public void updateDisplay() {
        ((SwingView) view).getJFrame().repaint();
    }
}
```

**Correcto**:
```java
public class Controller {
    // ✓ Controlador usa interfaz de Vista
    public List<DBodyInfoDTO> getDBodyInfo() {
        return model.getDBodyInfo(); // Vista obtiene cuando necesita
    }
}
```

### 5. Asumir Actualizaciones Síncronas

**Incorrecto**:
```java
public class Controller {
    public void addEntity() {
        model.addDBody(...); // Operación asíncrona
        // ❌ Asumir que la entidad está disponible inmediatamente
        view.render(); 
    }
}
```

**Correcto**:
```java
public class Controller {
    public void addEntity() {
        model.addDBody(...);
        // ✓ Dejar que el ciclo normal de renderizado capture cambios
        // La entidad aparecerá en la próxima llamada a getDBodyInfo()
    }
}
```

### 6. Ignorar Seguridad de Hilos

**Incorrecto**:
```java
public class Model {
    private List<DynamicBody> entities; // ❌ No seguro para hilos
    
    public void addEntity(DynamicBody body) {
        entities.add(body); // ❌ Condición de carrera
    }
}
```

**Correcto**:
```java
public class Model {
    private final ConcurrentHashMap<String, DynamicBody> entities; // ✓
    
    public void addEntity(DynamicBody body) {
        entities.put(body.getId(), body); // ✓ Seguro para hilos
    }
}
```

## Mejores Prácticas

1. **Mantén los Controladores Delgados**: Los controladores deben orquestar, no implementar lógica de negocio
2. **Usa DTOs Inmutables**: Siempre transfiere datos usando objetos inmutables
3. **Valida en Activate**: Verifica todas las dependencias durante la fase de activación
4. **Documenta el Threading**: Documenta claramente qué hilo accede a qué
5. **Máquinas de Estado**: Usa enums para estados del ciclo de vida de componentes
6. **Inyección de Dependencias**: Usa inyección por constructor o setter para testabilidad
7. **Patrón Observer**: Considera usar observers para actualizaciones Modelo→Vista en aplicaciones GUI

## Consideraciones de Testing

### Testing del Modelo
```java
@Test
public void testPhysicsUpdate() {
    Model model = new Model();
    model.setDimension(800, 600);
    // Probar sin Vista o Controlador
    model.addDBody(...);
    // Verificar cálculos físicos
}
```

### Testing de la Vista
```java
@Test
public void testRendering() {
    View view = new View();
    view.setDimension(800, 600);
    // Probar con Controlador mock
    MockController controller = new MockController();
    view.setController(controller);
    // Verificar lógica de renderizado
}
```

### Testing de Integración
```java
@Test
public void testMVCIntegration() {
    Controller controller = new Controller();
    Model model = new Model();
    View view = new View();
    
    controller.setModel(model);
    controller.setView(view);
    controller.activate();
    
    // Probar flujo completo
}
```

## Patrones Relacionados

- [Patrón Factory](Factory-Pattern.md) - Usado para crear configuraciones de mundo
- [Patrón Strategy](Strategy-Pattern.md) - Usado para motores físicos intercambiables
- [Patrón DTO](DTO-Pattern.md) - Usado para transferencia de datos entre capas MVC

## Referencias

- Fuente: `src/controller/Controller.java`
- Fuente: `src/model/Model.java`
- Fuente: `src/view/View.java`
- Arquitectura: [ARCHITECTURE.md](../../ARCHITECTURE.md)
