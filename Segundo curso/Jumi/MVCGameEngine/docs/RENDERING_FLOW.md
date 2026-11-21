# Flujo de Renderizado de StaticBody y DecoBody

Este documento explica el funcionamiento del sistema de pintado de los objetos `Renderable` asociados a `StaticBody` y `DecoBody`, con especial énfasis en cómo obtienen las imágenes desde la caché (`ImageCache`).

## Índice

1. [Visión General](#visión-general)
2. [Componentes Principales](#componentes-principales)
3. [Flujo Completo: Creación y Primera Imagen](#flujo-completo-creación-y-primera-imagen)
4. [Actualización de Imágenes](#actualización-de-imágenes)
5. [Ciclo de Pintado](#ciclo-de-pintado)
6. [Diferencias con DynamicRenderable](#diferencias-con-dynamicrenderable)

---

## Visión General

El sistema de renderizado para objetos estáticos (`StaticBody` y `DecoBody`) sigue un patrón donde:

- **Modelo**: Contiene las entidades físicas (StaticBody, DecoBody)
- **Controller**: Coordina la creación y sincronización entre modelo y vista
- **Vista/Renderer**: Mantiene objetos `Renderable` que gestionan el pintado
- **ImageCache**: Proporciona imágenes pre-procesadas (rotadas, escaladas) optimizadas

Los `Renderable` son objetos ligeros que:
- Almacenan datos de renderizado (posición, ángulo, tamaño)
- Mantienen una referencia a la imagen actual
- Solicitan imágenes a la caché solo cuando es necesario

---

## Componentes Principales

### StaticBody / DecoBody (Modelo)
```java
// Entidades estáticas sin física dinámica
public class StaticBody extends AbstractPhysicsBody {
    // Usa NullPhysicsEngine (posición fija)
}

public class DecoBody extends AbstractBody {
    // Decoración visual, sin física
}
```

### Renderable (Vista)
```java
public class Renderable {
    private final String entityId;
    private final String assetId;
    private final ImageCache cache;
    
    private RenderDTO renderableValues;  // Posición, ángulo, tamaño
    private BufferedImage image;          // Imagen actual (puede ser null)
    private long lastFrameSeen;           // Para detectar objetos obsoletos
}
```

### ImageCache
Gestiona imágenes transformadas y las reutiliza:
- Indexa por: `(ángulo, assetId, tamaño)`
- Genera versiones rotadas/escaladas bajo demanda
- Evita re-procesar la misma transformación

---

## Flujo Completo: Creación y Primera Imagen

### Paso 1: Creación del Body en el Modelo

```java
// Usuario o WorldInitializer llama:
controller.addStaticBody(assetId, size, posX, posY, angle);
```

El controlador ejecuta:
```java
// Controller.java
public void addStaticBody(String assetId, double size, double posX, double posY, double angle) {
    // 1. Crear entidad en el modelo
    String entityId = this.model.addStaticBody(size, posX, posY, angle, -1L);
    
    if (entityId == null || entityId.isEmpty()) {
        return; // Máximo de entidades alcanzado
    }
    
    // 2. Crear Renderable en la vista (SIN imagen todavía)
    this.view.addStaticRenderable(entityId, assetId);
    
    // 3. Obtener datos del modelo y actualizar renderables
    ArrayList<BodyDTO> bodiesData = this.model.getStaticsData();
    ArrayList<RenderDTO> renderablesData = RenderableMapper.fromBodyDTO(bodiesData);
    
    // 4. Primera actualización (AQUÍ se obtiene la imagen inicial)
    this.view.updateStaticRenderables(renderablesData);
}
```

### Paso 2: Creación del Renderable (sin imagen)

```java
// Renderer.java
public void addStaticRenderable(String entityId, String assetId) {
    // Constructor básico: NO obtiene imagen todavía
    Renderable renderable = new Renderable(entityId, assetId, this.imagesCache, this.currentFrame);
    this.staticRenderables.put(entityId, renderable);
}
```

Constructor básico del `Renderable`:
```java
// Renderable.java
public Renderable(String entityId, String assetId, ImageCache cache, long currentFrame) {
    this.entityId = entityId;
    this.assetId = assetId;
    this.cache = cache;
    this.lastFrameSeen = currentFrame;
    // this.image = null  (no se obtiene imagen aquí)
    // this.renderableValues = null
}
```

**Estado después de este paso**:
- ✅ Renderable creado y registrado
- ❌ `image = null` (no hay imagen todavía)
- ❌ `renderableValues = null` (no hay datos de posición)

### Paso 3: Primera Actualización (obtención de imagen inicial)

El controlador inmediatamente llama a `updateStaticRenderables()`:

```java
// Renderer.java
public void updateStaticRenderables(ArrayList<RenderDTO> renderablesData) {
    // Copia defensiva para thread-safety
    Map<String, Renderable> newRenderables = new ConcurrentHashMap<>(this.staticRenderables);
    
    long cFrame = this.currentFrame;
    for (RenderDTO renderableData : renderablesData) {
        String entityId = renderableData.entityId;
        
        Renderable renderable = newRenderables.get(entityId);
        if (renderable != null) {
            // PRIMERA ACTUALIZACIÓN: aquí se obtiene la imagen
            renderable.update(renderableData, cFrame);
        }
    }
    
    // Swap atómico de la referencia
    this.staticRenderables = newRenderables;
}
```

### Paso 4: Método update() del Renderable

```java
// Renderable.java
public void update(RenderDTO renderInfo, long currentFrame) {
    // 1. Intenta actualizar imagen desde caché
    this.updateImageFromCache(this.assetId, (int) renderInfo.size, renderInfo.angle);
    
    // 2. Actualiza datos de renderizado
    this.lastFrameSeen = currentFrame;
    this.renderableValues = renderInfo;  // Guarda posX, posY, angle, size
}
```

### Paso 5: Obtención de Imagen desde ImageCache

```java
// Renderable.java
private boolean updateImageFromCache(String assetId, int size, double angle) {
    // Determina si necesita actualizar la imagen
    boolean imageNeedsUpdate = this.image == null                    // ← TRUE (primera vez)
            || this.renderableValues == null                         // ← TRUE (primera vez)
            || !this.assetId.equals(assetId)
            || this.renderableValues.size != size
            || (int) this.renderableValues.angle != (int) angle;

    if (imageNeedsUpdate) {
        // Normalizar ángulo entre 0-360°
        int normalizedAngle = ((int) angle % 360 + 360) % 360;
        
        // OBTENER IMAGEN DE LA CACHÉ
        this.image = this.cache.getImage(normalizedAngle, assetId, size);
        
        return true;
    }

    return false;
}
```

**¿Qué hace `ImageCache.getImage()`?**
1. Busca si ya existe una imagen con esos parámetros `(angle, assetId, size)`
2. Si existe: devuelve la referencia (reutilización)
3. Si no existe:
   - Carga la imagen base del assetId
   - Aplica rotación (según ángulo)
   - Aplica escala (según tamaño)
   - Guarda en caché
   - Devuelve la imagen procesada

**Estado después de este paso**:
- ✅ `image = BufferedImage` (imagen lista para pintar)
- ✅ `renderableValues = RenderDTO` (posición, ángulo, tamaño)
- ✅ `lastFrameSeen` actualizado

---

## Actualización de Imágenes

### Cuándo se Actualiza la Imagen

La imagen se vuelve a obtener de la caché **solo si cambia**:
- El tamaño (`size`)
- El ángulo (`angle` - comparación entera, no exacta)
- El assetId (cambio de sprite)
- Si la imagen es `null`

### Optimización: Comparación de Ángulos

```java
(int) this.renderableValues.angle != (int) angle
```

Solo actualiza si el ángulo cambia en grados enteros. Esto evita:
- Solicitudes innecesarias a la caché por cambios decimales
- Re-procesamiento de imágenes para rotaciones imperceptibles

### Ejemplo de Actualización

```java
// Frame 1: Renderable en 45°, tamaño 50
updateImageFromCache("asteroid", 50, 45.0);  
// → Obtiene imagen de caché: asteroid_50_45

// Frame 2: Renderable en 45.3°, tamaño 50
updateImageFromCache("asteroid", 50, 45.3);  
// → NO actualiza (ángulo entero sigue siendo 45)

// Frame 3: Renderable en 46.0°, tamaño 50
updateImageFromCache("asteroid", 50, 46.0);  
// → SÍ actualiza, obtiene: asteroid_50_46

// Frame 4: Renderable en 46°, tamaño 60
updateImageFromCache("asteroid", 60, 46.0);  
// → SÍ actualiza (cambió tamaño), obtiene: asteroid_60_46
```

---

## Ciclo de Pintado

### Loop Principal del Renderer

```java
// Renderer.java
@Override
public void run() {
    BufferStrategy bs = getBufferStrategy();
    
    while (true) {
        if (this.view.getEngineState() == EngineState.ALIVE) {
            this.currentFrame++;
            this.drawScene(bs);  // ← Dibuja todo
        }
        
        Thread.sleep(this.delayInMillis);
    }
}
```

### Método drawScene()

```java
private void drawScene(BufferStrategy bs) {
    Graphics2D g = (Graphics2D) bs.getDrawGraphics();
    
    // 1. Fondo
    g.drawImage(this.getVIBackground(), 0, 0, null);
    
    // 2. Renderables estáticos (StaticBody, DecoBody)
    this.drawStaticRenderables(g);
    
    // 3. HUDs
    this.drawHUDs(g);
    
    // 4. Renderables dinámicos (DynamicBody)
    this.drawDynamicRenderable(g);
    
    g.dispose();
    bs.show();
}
```

### Pintado de Renderables Estáticos

```java
// Renderer.java
private void drawStaticRenderables(Graphics2D g) {
    Map<String, Renderable> renderables = this.staticRenderables;
    
    for (Renderable renderable : renderables.values()) {
        renderable.paint(g);  // ← Pinta cada renderable
    }
}
```

### Método paint() del Renderable

```java
// Renderable.java
public void paint(Graphics2D g) {
    // Guard: no pintar si no hay imagen
    if (this.image == null) {
        return;
    }

    // Guardar transformación original
    AffineTransform defaultTransform = g.getTransform();

    // Crear transformación de rotación
    AffineTransform mainRotation = AffineTransform.getRotateInstance(
            Math.toRadians(this.renderableValues.angle),
            this.renderableValues.posX, 
            this.renderableValues.posY);

    // Aplicar rotación
    g.setTransform(mainRotation);

    // Dibujar imagen centrada en la posición
    g.drawImage(
            this.image,
            (int) (this.renderableValues.posX - this.renderableValues.size / 2),
            (int) (this.renderableValues.posY - this.renderableValues.size / 2),
            null);
    
    // Restaurar transformación original
    g.setTransform(defaultTransform);
}
```

**Notas importantes**:
- La imagen ya viene **pre-rotada** de la caché, pero se aplica transformación adicional para el contexto gráfico
- La posición se ajusta para centrar la imagen (`posX - size/2`)
- Se restaura el transform para no afectar otros elementos

---

## Diferencias con DynamicRenderable

### Similitudes
- Ambos heredan la misma lógica de obtención de imágenes
- Usan `ImageCache` de la misma manera
- Método `paint()` básico es idéntico

### Diferencias Clave

| Aspecto | StaticBody/DecoBody | DynamicBody |
|---------|---------------------|-------------|
| **Frecuencia de actualización** | Solo cuando cambian datos | Cada frame automáticamente |
| **Clase Renderable** | `Renderable` | `DynamicRenderable extends Renderable` |
| **Datos adicionales** | `RenderDTO` (posición, ángulo, tamaño) | `DynamicRenderDTO` (+ velocidad, aceleración) |
| **Thread-safety** | Copy-on-write (volatile Map) | ConcurrentHashMap directo |
| **Pintado extra** | Solo la imagen | Imagen + vectores de debug |
| **Física** | NullPhysicsEngine (estáticos) | BasicPhysicsEngine (movimiento) |

### DynamicRenderable: Pintado Extendido

```java
// DynamicRenderable.java
@Override
public void paint(Graphics2D g) {
    // 1. Pintar imagen base (llama a super)
    super.paint(g);
    
    // 2. Pintar debug (si está activo)
    if (this.debugMode) {
        DynamicRenderDTO bodyInfo = (DynamicRenderDTO) this.getRenderableValues();
        
        // Vector de velocidad (amarillo)
        g.setColor(Color.YELLOW);
        g.drawLine(x, y, x + speedX/4, y + speedY/4);
        
        // Vector de aceleración (rojo)
        g.setColor(Color.RED);
        g.drawLine(x, y, x + accX/5, y + accY/5);
    }
}
```

### Actualización: Estáticos vs Dinámicos

**Estáticos** (on-demand):
```java
// Solo cuando el controlador lo solicita explícitamente
controller.addStaticBody(...);
// → updateStaticRenderables() se llama UNA VEZ
```

**Dinámicos** (cada frame):
```java
// Renderer.java - en drawDynamicRenderable()
private void drawDynamicRenderable(Graphics2D g) {
    // 1. Obtener datos frescos del modelo
    ArrayList<DynamicRenderDTO> renderablesData = this.view.getDynamicRenderablesData();
    
    // 2. Actualizar todos los renderables dinámicos
    this.updateDynamicRenderables(renderablesData);  // ← CADA FRAME
    
    // 3. Pintar
    for (DynamicRenderable renderable : this.dynamicRenderables.values()) {
        renderable.paint(g);
    }
}
```

---

## Diagrama de Flujo Completo

```
┌─────────────────────────────────────────────────────────────┐
│ 1. CREACIÓN                                                 │
└─────────────────────────────────────────────────────────────┘
    Controller.addStaticBody(assetId, size, pos, angle)
         │
         ├─→ Model.addStaticBody() 
         │       └─→ new StaticBody (NullPhysicsEngine)
         │           └─→ Almacena en staticBodies map
         │
         ├─→ View.addStaticRenderable(entityId, assetId)
         │       └─→ Renderer.addStaticRenderable()
         │           └─→ new Renderable(entityId, assetId, cache, frame)
         │               • image = null
         │               • renderableValues = null
         │
         ├─→ Model.getStaticsData()
         │       └─→ ArrayList<BodyDTO> (todos los statics)
         │
         ├─→ RenderableMapper.fromBodyDTO()
         │       └─→ ArrayList<RenderDTO> (posX, posY, angle, size)
         │
         └─→ View.updateStaticRenderables(renderablesData)

┌─────────────────────────────────────────────────────────────┐
│ 2. PRIMERA ACTUALIZACIÓN (Obtención imagen inicial)        │
└─────────────────────────────────────────────────────────────┘
    Renderer.updateStaticRenderables(renderablesData)
         │
         └─→ for each RenderDTO:
                 │
                 └─→ renderable.update(renderDTO, currentFrame)
                         │
                         ├─→ updateImageFromCache(assetId, size, angle)
                         │       │
                         │       ├─→ Detecta: image == null  ✓
                         │       │            renderableValues == null  ✓
                         │       │
                         │       ├─→ normalizedAngle = (angle % 360 + 360) % 360
                         │       │
                         │       └─→ image = cache.getImage(angle, assetId, size)
                         │                    │
                         │                    ├─→ Busca en caché
                         │                    ├─→ Si no existe:
                         │                    │   • Carga imagen base
                         │                    │   • Aplica rotación
                         │                    │   • Aplica escala
                         │                    │   • Guarda en caché
                         │                    └─→ Devuelve BufferedImage
                         │
                         ├─→ lastFrameSeen = currentFrame
                         │
                         └─→ renderableValues = renderDTO

┌─────────────────────────────────────────────────────────────┐
│ 3. CICLO DE PINTADO (cada frame)                           │
└─────────────────────────────────────────────────────────────┘
    Renderer.run() loop
         │
         └─→ drawScene(bufferStrategy)
                 │
                 ├─→ Dibuja fondo
                 │
                 ├─→ drawStaticRenderables(g)
                 │       │
                 │       └─→ for each Renderable:
                 │               │
                 │               └─→ renderable.paint(g)
                 │                       │
                 │                       ├─→ if (image == null) return
                 │                       │
                 │                       ├─→ Aplica AffineTransform (rotación)
                 │                       │
                 │                       ├─→ g.drawImage(image, posX, posY)
                 │                       │
                 │                       └─→ Restaura transform
                 │
                 ├─→ drawHUDs(g)
                 │
                 └─→ drawDynamicRenderables(g)

┌─────────────────────────────────────────────────────────────┐
│ 4. ACTUALIZACIONES POSTERIORES (cuando cambian datos)      │
└─────────────────────────────────────────────────────────────┘
    (Similar al paso 2, pero solo si cambia size/angle)
    
    updateImageFromCache()
         │
         ├─→ Compara: size actual vs nuevo
         ├─→ Compara: (int)angle actual vs (int)nuevo
         │
         ├─→ Si NO cambió: return false (reutiliza imagen)
         │
         └─→ Si cambió: cache.getImage() (nueva imagen)
```

---

## Puntos Clave para Recordar

1. **Creación lazy de imágenes**: El `Renderable` se crea sin imagen, que se obtiene en la primera actualización

2. **ImageCache es el único origen**: Todas las imágenes vienen de la caché, nunca se cargan directamente

3. **Optimización de actualizaciones**: Solo se pide nueva imagen si cambia tamaño o ángulo (entero)

4. **Thread-safety**: 
   - Estáticos usan copy-on-write (swap atómico de Map)
   - Dinámicos usan ConcurrentHashMap

5. **Separación de responsabilidades**:
   - `Renderable`: Gestiona qué imagen mostrar y dónde
   - `ImageCache`: Genera y almacena imágenes transformadas
   - `Renderer`: Coordina el ciclo de pintado

6. **Diferencia estáticos/dinámicos**:
   - Estáticos: actualizan on-demand
   - Dinámicos: actualizan cada frame automáticamente

---

## Referencias de Código

- `src/model/bodies/implementations/StaticBody.java`
- `src/model/bodies/implementations/DecoBody.java`
- `src/view/renderables/implementations/Renderable.java`
- `src/view/renderables/implementations/DynamicRenderable.java`
- `src/view/core/Renderer.java`
- `src/controller/implementations/Controller.java`
- `src/controller/mappers/RenderableMapper.java`
- `images/ImageCache.java`