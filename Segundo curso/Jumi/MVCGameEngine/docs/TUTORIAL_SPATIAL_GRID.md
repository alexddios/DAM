# 📘 Tutorial: Optimización de Colisiones con SpatialGrid

## 🎯 ¿Qué es SpatialGrid?

`SpatialGrid` es una estructura de datos espacial que divide el mundo del juego en una cuadrícula de celdas. En lugar de comparar cada entidad con todas las demás (O(n²)), solo comparas entidades que están en celdas cercanas (O(n×k), donde k es el número promedio de vecinos por celda).

### 🔥 Ventajas Principales

- **Reducción de complejidad**: De O(n²) a O(n×k)
- **Zero-allocation strategy**: Uso de buffers reutilizables para evitar garbage collection
- **Thread-safe**: Usa `ConcurrentHashMap` para operaciones concurrentes
- **Topología fija**: Sin realocaciones durante runtime

---

## 📐 Conceptos Básicos

### Estructura del Grid

```java
// El mundo se divide en celdas de tamaño fijo
cellSize = 128 píxeles
worldWidth = 1920 píxeles
worldHeight = 1080 píxeles

// Celdas calculadas: 
cellsX = ⌈1920 / 128⌉ = 15 celdas
cellsY = ⌈1080 / 128⌉ = 9 celdas
total = 15 × 9 = 135 celdas
```

### Mapeo de Entidades

Cada entidad puede ocupar **múltiples celdas** si su bounding box cruza los límites: 

```
┌─────┬─────┬─────┐
│  A  │ A,B │  B  │
├─────┼─────┼─────┤
│     │ B,C │  C  │
├─────┼─────┼─────┤
│     │  C  │     │
└─────┴─────┴─────┘
```

---

## 🛠️ Implementación Paso a Paso

### **Paso 1: Crear el SpatialGrid**

```java
public class Model implements BodyEventProcessor {
    private final SpatialGrid spatialGrid;
    
    public Model(double worldWidth, double worldHeight, int maxDynamicBodies) {
        // Parámetros: 
        // - cellSize: 128 (tamaño óptimo para tu tipo de juego)
        // - worldWidth/Height: dimensiones del mundo
        // - maxCellsPerBody: 16 (máximo número de celdas que puede ocupar una entidad)
        this.spatialGrid = new SpatialGrid(
            128,                    // cellSize
            (int) worldWidth,       // worldWidth
            (int) worldHeight,      // worldHeight
            16                      // maxCellsPerBody
        );
    }
}
```

**⚙️ Consideraciones de Configuración:**

- **cellSize**: Debe ser ~2-3× el tamaño promedio de tus entidades
- **maxCellsPerBody**: Calcula como `⌈(maxEntitySize / cellSize)²⌉ + buffer`

---

### **Paso 2: Configurar Buffers en las Entidades (Zero-Allocation)**

Cada `Body` necesita buffers scratch reutilizables:

```java
public class AbstractBody implements Body {
    // Buffers para evitar asignaciones durante physics update
    private final SpatialGrid spatialGrid;
    private final int[] scratchIdxs;                           // Buffer de índices de celdas
    private final ArrayList<String> scratchCandidateIds;       // Candidatos de colisión
    private final HashSet<String> scratchSeenCandidateIds;     // Deduplicación
    
    public AbstractBody(BodyEventProcessor bodyEventProcessor, 
                       SpatialGrid spatialGrid,
                       PhysicsEngine phyEngine, 
                       BodyType bodyType,
                       double maxLifeInSeconds) {
        
        this.spatialGrid = spatialGrid;
        
        // Crear buffers con capacidad basada en el grid
        this.scratchIdxs = new int[spatialGrid.getMaxCellsPerBody()];
        this.scratchCandidateIds = new ArrayList<>(64);
        this.scratchSeenCandidateIds = new HashSet<>(64);
        
        // ...  resto de inicialización
    }
}
```

**✅ Beneficio**: Estos buffers se crean UNA VEZ y se reutilizan cada frame → sin GC pauses. 

---

### **Paso 3: Actualizar Posiciones en el Grid (Upsert)**

Cada vez que una entidad se mueve, actualiza su registro en el grid:

```java
public void updateEntityPosition(Body body) {
    PhysicsValuesDTO physics = body.getPhysicsValues();
    
    // Calcular AABB (Axis-Aligned Bounding Box)
    double halfSize = physics.size / 2.0;
    double minX = physics.posX - halfSize;
    double maxX = physics.posX + halfSize;
    double minY = physics.posY - halfSize;
    double maxY = physics.posY + halfSize;
    
    // Actualizar el grid (usa el buffer scratch)
    spatialGrid.upsert(
        body.getEntityId(),     // ID única de la entidad
        minX, maxX,             // Bounding box X
        minY, maxY,             // Bounding box Y
        body.getScratchIdxs()   // Buffer reutilizable
    );
}
```

**🔍 Lo que hace `upsert()` internamente:**

1. Calcula las celdas que ocupa el nuevo AABB
2. Compara con las celdas anteriores
3. **Elimina** la entidad de celdas viejas
4. **Añade** la entidad a celdas nuevas
5. Actualiza el mapeo reverso (entityId → celdas)

---

### **Paso 4: Consultar Candidatos de Colisión**

En lugar de iterar todas las entidades, solo consulta vecinos cercanos:

```java
private List<Event> checkCollisions(Body checkBody, PhysicsValuesDTO newPhysics) {
    if (! this.isCollidable(checkBody))
        return List.of();
    
    String checkBodyId = checkBody.getEntityId();
    
    // 1) Obtener candidatos usando el grid (reutiliza ArrayList)
    ArrayList<String> candidates = checkBody.getScratchCandidateIds();
    spatialGrid.queryCollisionCandidates(checkBodyId, candidates);
    
    if (candidates.isEmpty())
        return List.of();
    
    // 2) Deduplicar (una entidad puede aparecer varias veces si comparten múltiples celdas)
    HashSet<String> seen = checkBody.getScratchSeenCandidateIds();
    seen.clear();
    
    List<Event> collisionEvents = null;
    
    for (String candidateId :  candidates) {
        // Deduplicación básica
        if (! seen.add(candidateId))
            continue;
        
        // Evitar duplicados por simetría (A-B vs B-A)
        if (checkBodyId.compareTo(candidateId) >= 0)
            continue;
        
        // 3) Verificación precisa de colisión
        Body otherBody = this.dynamicBodies.get(candidateId);
        if (otherBody == null || !this.isCollidable(otherBody))
            continue;
        
        PhysicsValuesDTO otherPhysics = otherBody.getPhysicsValues();
        
        // Detección precisa (círculo-círculo)
        if (intersectCircles(newPhysics, otherPhysics)) {
            if (collisionEvents == null)
                collisionEvents = new ArrayList<>();
            
            collisionEvents.add(
                new Event(checkBody, otherBody, EventType.COLLISION)
            );
        }
    }
    
    return (collisionEvents == null) ? List.of() : collisionEvents;
}
```

**🎯 Técnicas de Optimización:**

1. **Deduplicación por HashSet**: Elimina duplicados de múltiples celdas compartidas
2. **Comparación lexicográfica**: `compareTo()` evita procesar pares A-B y B-A
3. **Lazy allocation**: `collisionEvents` solo se crea si hay colisiones

---

### **Paso 5: Remover Entidades del Grid**

Cuando una entidad muere o sale del mundo:

```java
public void removeBody(String entityId) {
    Body body = this.dynamicBodies.remove(entityId);
    
    if (body != null) {
        // Limpiar del spatial grid
        this.spatialGrid.remove(entityId);
    }
}
```

---

## 📊 Monitoreo y Estadísticas

### Obtener Métricas del Grid

```java
public SpatialGridStatisticsDTO getGridStatistics() {
    return spatialGrid.getStatistics();
}
```

**Métricas disponibles:**

- `nonEmptyCells`: Celdas con al menos 1 entidad
- `emptyCells`: Celdas vacías
- `avgBucketSizeNonEmpty`: Promedio de entidades por celda ocupada
- `maxBucketSize`: Máximo de entidades en una celda
- `estimatedPairChecks`: Estimación de comparaciones totales (∑ n(n-1)/2)

### Visualización con HUD

```java
public class SpatialGridHUD extends DataHUD {
    private void addItems() {
        this.addTitle("SPATIAL GRID");
        this.addTextItem("Cell Size");
        this.addTextItem("Total Cells");
        this.addBar("Empties", 125, false);
        this.addTextItem("Avg Bodies");
        this.addTextItem("Max Bodies");
        this.addTextItem("Pair Checks");
    }
}
```

---

## ⚡ Optimización Avanzada

### 1. **Tamaño de Celda Óptimo**

```java
// Regla general: 
cellSize = 2.5 × averageEntitySize

// Ejemplo:
// Si tus naves miden ~50px
cellSize = 2.5 × 50 = 125 → usa 128 (potencia de 2)
```

### 2. **Ajustar maxCellsPerBody**

Si ves este warning en consola:
```
Warning: computeCellIdxsClamped() overflow.  maxCellsPerBody=16
```

Aumenta el parámetro:
```java
this.spatialGrid = new SpatialGrid(128, width, height, 32); // antes era 16
```

### 3. **Monitoreo de Performance**

```java
SpatialGridStatisticsDTO stats = spatialGrid.getStatistics();

// Indicadores de salud del grid: 
double loadBalance = stats.avgBucketSizeNonEmpty;  // Ideal:  2-5
int hotspots = stats.maxBucketSize;                // Ideal:  <10

if (loadBalance > 10) {
    System.err.println("⚠️ Considera AUMENTAR cellSize");
}

if (stats.emptyCells > stats.nonEmptyCells * 3) {
    System.err.println("⚠️ Considera REDUCIR cellSize");
}
```

---

## 🎮 Flujo Completo de Integración

```java
// En cada frame de physics:
public void updatePhysics(double deltaTime) {
    for (Body body : dynamicBodies.values()) {
        // 1. Calcular nueva posición
        PhysicsValuesDTO newPhysics = body.getPhysicsEngine().integrate(deltaTime);
        
        // 2. Actualizar spatial grid
        updateSpatialGrid(body, newPhysics);
        
        // 3. Detectar colisiones usando el grid
        List<Event> collisions = checkCollisions(body, newPhysics);
        
        // 4. Procesar eventos
        processCollisionEvents(collisions);
        
        // 5. Aplicar nueva posición
        body.getPhysicsEngine().setPhysicsValues(newPhysics);
    }
}

private void updateSpatialGrid(Body body, PhysicsValuesDTO physics) {
    double halfSize = physics.size / 2.0;
    spatialGrid.upsert(
        body.getEntityId(),
        physics.posX - halfSize, physics.posX + halfSize,
        physics.posY - halfSize, physics.posY + halfSize,
        body.getScratchIdxs()
    );
}
```

---

## 📈 Resultados Esperados

### Comparación de Performance

| Entidades | Sin Spatial Grid | Con Spatial Grid | Mejora |
|-----------|------------------|------------------|--------|
| 100       | 4,950 checks     | ~250 checks      | 20×    |
| 500       | 124,750 checks   | ~1,250 checks    | 100×   |
| 1000      | 499,500 checks   | ~2,500 checks    | 200×   |

**Fórmula:**
- Sin grid: n(n-1)/2
- Con grid: n × avgNeighbors (típicamente 2-5)

---

## 🚨 Problemas Comunes

### ❌ "maxCellsPerBody overflow"

**Causa**:  Una entidad es demasiado grande para el `cellSize` actual. 

**Solución**:
```java
// Aumenta maxCellsPerBody o reduce cellSize
new SpatialGrid(128, width, height, 32); // era 16
```

### ❌ Colisiones duplicadas

**Causa**: No estás deduplicando correctamente.

**Solución**:
```java
// Usa HashSet para seen
HashSet<String> seen = checkBody.getScratchSeenCandidateIds();
seen.clear();
for (String id : candidates) {
    if (!seen.add(id)) continue; // ← Deduplicación
    // ... 
}
```

### ❌ Performance sigue siendo mala

**Diagnóstico**:
```java
SpatialGridStatisticsDTO stats = getGridStatistics();
System.out.println("Avg per bucket: " + stats.avgBucketSizeNonEmpty);
System.out.println("Max in bucket: " + stats.maxBucketSize);

// Si avgBucketSizeNonEmpty > 20: 
//   → cellSize es muy GRANDE, reduce a la mitad
// Si emptyCells > 80%:
//   → cellSize es muy PEQUEÑO, duplica
```

---

## 🎓 Resumen

1. **Crea** el `SpatialGrid` con parámetros adecuados (cellSize ≈ 2-3× entity size)
2. **Inicializa** buffers scratch en cada `Body` para zero-allocation
3. **Actualiza** el grid con `upsert()` cuando las entidades se mueven
4. **Consulta** candidatos con `queryCollisionCandidates()` en lugar de iterar todas
5. **Monitorea** estadísticas con `getStatistics()` para ajustar parámetros
6. **Limpia** entidades muertas con `remove()`

**Resultado**: De O(n²) a O(n×k) con k ≈ 2-5 → **100× más rápido** con 1000 entidades.

---

## 📚 Referencias

- Código fuente: [`src/model/spatial/core/SpatialGrid.java`](../src/model/spatial/core/SpatialGrid.java)
- Ejemplo de uso: [`src/model/implementations/Model.java`](../src/model/implementations/Model.java)
- HUD de monitoreo: [`src/view/huds/implementations/SpatialGridHUD.java`](../src/view/huds/implementations/SpatialGridHUD.java)