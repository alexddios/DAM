# Sistema de Armas (Weapons System)

Este documento explica el **flujo completo del sistema de armas** en MVCGameEngine, enfocándose en cómo funciona la cadencia de disparo y la creación de proyectiles.

## 🎯 Concepto General

El sistema de armas funciona con un **modelo basado en solicitudes (requests)** que se procesan de manera asíncrona en el thread de cada entidad. Las armas son componentes **pasivos** que deciden cuándo disparar, pero no crean proyectiles directamente.

## 🧵 Modelo de Threading

Cada `DynamicBody` (incluyendo `PlayerBody`) ejecuta su propio thread independiente:

```
┌─────────────────────────────────────────────────────────┐
│              Main/AWT Thread (UI)                       │
│         - Captura input del teclado                     │
│         - Llama a controller.playerFire()               │
└─────────────────────────────────────────────────────────┘
                         │
         ┌───────────────┼───────────────┐
         │               │               │
         ▼               ▼               ▼
   ┌──────────┐    ┌──────────┐    ┌──────────┐
   │ Thread   │    │ Thread   │    │ Thread   │
   │ Body 1   │    │ Body 2   │    │ Body N   │
   │          │    │          │    │          │
   │ loop()   │    │ loop()   │    │ loop()   │
   └──────────┘    └──────────┘    └──────────┘
         │               │               │
         └───────────────┼───────────────┘
                         │
                         ▼
                  ┌─────────────┐
                  │    Model    │
                  │ (Coordina   │
                  │  eventos)   │
                  └─────────────┘
```

## 🔄 Flujo Completo de Disparo

### 1️⃣ Registro de Solicitud (Thread Principal - UI)

Cuando el usuario presiona el botón de disparo:

```java
// Main/AWT Thread
Usuario presiona SPACE
    ↓
Controller.playerFire(playerId)
    ↓
Model.playerFire(playerId)
    ↓
PlayerBody.registerFireRequest()  // ← Thread-safe (AtomicLong)
```

**Código relevante:**

```java
// Model.java
public void playerFire(String playerId) {
    PlayerBody pBody = (PlayerBody) this.playerBodies.get(playerId);
    if (pBody != null) {
        pBody.registerFireRequest();
    }
}

// PlayerBody.java
public void registerFireRequest() {
    Weapon active = getActiveWeapon();
    if (active != null) {
        active.registerFireRequest();
    }
}

// AbstractWeapon.java
@Override
public void registerFireRequest() {
    this.lastFireRequest.set(System.nanoTime());  // Thread-safe
}
```

### 2️⃣ Loop del PlayerBody (Thread Independiente)

Cada `DynamicBody` ejecuta continuamente su propio loop:

```java
// DynamicBody.java - Ejecutado en thread independiente
private void loop() {
    while (this.state != BodyState.DEAD) {
        try {
            // 1. Calcula nueva física
            PhysicsValuesDTO oldPhyValues = this.getPhysicsValues();
            PhysicsValuesDTO newPhyValues = this.physicsEngine.computeNext(oldPhyValues);
            
            // 2. Procesa eventos (aquí se detecta MUST_FIRE)
            this.model.processBodyEvents(this, newPhyValues, oldPhyValues);
            
            Thread.sleep(this.updateIntervalMillis);
            
        } catch (InterruptedException e) {
            this.die();
        }
    }
}
```

### 3️⃣ Detección del Evento MUST_FIRE

El modelo detecta si el arma debe disparar:

```java
// Model.java - detectEvents()
// ⚠️ Ejecutado en el thread del body específico
private List<Event> detectEvents(Body checkBody, 
        PhysicsValuesDTO newPhyValues, PhysicsValuesDTO oldPhyValues) {
    
    List<Event> events = null;
    
    // ... detección de límites, colisiones, etc. ... 
    
    // 3) Verifica si debe disparar
    if (checkBody instanceof PlayerBody) {
        if (((PlayerBody) checkBody).mustFireNow(newPhyValues)) {  // ← DECISOR
            if (events == null)
                events = new ArrayList<>(2);
            events.add(new Event(checkBody, null, EventType.MUST_FIRE)); // ← CREA EVENTO
        }
    }
    
    // 4) Life over
    if (checkBody.isLifeOver()) {
        if (events == null)
            events = new ArrayList<>(1);
        events.add(new Event(checkBody, null, EventType.LIFE_OVER));
    }
    
    return events == null ? List.of() : events;
}
```

**🔑 Punto Clave:** `mustFireNow()` **decide** si debe generarse el evento de disparo.

### 4️⃣ Decisión del Arma (mustFireNow)

La decisión de disparar la toma el arma considerando cooldown, munición y solicitudes:

```java
// PlayerBody.java
public boolean mustFireNow(PhysicsValuesDTO newPhyValues) {
    if (this.currentWeaponIndex < 0 || this.currentWeaponIndex >= this.weapons.size()) {
        return false;
    }

    Weapon weapon = this.weapons.get(this.currentWeaponIndex);
    if (weapon == null) {
        return false;
    }

    // Calcula el delta de tiempo desde la última actualización
    double dtNanos = newPhyValues.timeStamp - this.getPhysicsValues().timeStamp;
    double dtSeconds = dtNanos / 1_000_000_000;

    return weapon.mustFireNow(dtSeconds);  // ← DECISOR FINAL
}
```

#### Ejemplo: BasicWeapon

```java
// BasicWeapon.java
@Override
public boolean mustFireNow(double dtSeconds) {
    if (this.getCooldown() > 0) {
        // Cool down weapon. Any pending requests are discarded.
        this.decCooldown(dtSeconds);
        this.markAllRequestsHandled();
        return false; // ❌ Weapon is overheated
    }

    if (this.getCurrentAmmo() <= 0) {
        // No ammunition: reload, set time to reload and discard requests
        this.setState(WeaponState.RELOADING);
        this.markAllRequestsHandled();
        this.setCooldown(this.getWeaponConfig().reloadTime);
        this.setCurrentAmmo(this.getWeaponConfig().maxAmmo);
        return false; // ❌ Reloading
    }

    this.setState(WeaponState.READY);
    if (!this.hasRequest()) {
        // Nothing to do
        this.setCooldown(0);
        return false; // ❌ No request
    }

    // ✅ Fire
    this.markAllRequestsHandled();
    this.decCurrentAmmo();
    this.setCooldown(1.0 / this.getWeaponConfig().fireRate); // ← CONTROL DE CADENCIA
    return true;
}
```

**Control de Cadencia:** El cooldown se calcula como `1.0 / fireRate`:
- Si `fireRate = 5` disparos/segundo → cooldown = 0.2 segundos
- Si `fireRate = 10` disparos/segundo → cooldown = 0.1 segundos

### 5️⃣ Procesamiento del Evento (Thread del Body)

```java
// Model.java - processBodyEvents()
public void processBodyEvents(Body body,
        PhysicsValuesDTO newPhyValues, PhysicsValuesDTO oldPhyValues) {

    if (!isProcessable(body)) {
        return;
    }

    BodyState previousState = body.getState();
    body.setState(BodyState.HANDS_OFF); // ← Bloquea el body

    try {
        // 1. Detecta eventos (incluyendo MUST_FIRE)
        List<Event> events = this.detectEvents(body, newPhyValues, oldPhyValues);
        
        // 2. Envía eventos al controlador para que decida acciones
        List<ActionDTO> actions = null;
        if (events != null && !events.isEmpty())
            actions = this.domainEventProcessor.decideActions(events);
        
        if (actions == null)
            actions = new ArrayList<>(4);
        
        // 3. Agrega acción MOVE por defecto si no hay otra acción de física
        boolean hasPhysicsBodyAction = actions.stream()
                .anyMatch(a -> a.executor == ActionExecutor.PHYSICS_BODY);

        if (!hasPhysicsBodyAction) {
            actions.add(new ActionDTO(body.getEntityId(),
                    ActionType.MOVE, ActionExecutor.PHYSICS_BODY, ActionPriority.NORMAL));
        }

        // 4. Ejecuta las acciones
        this.doActions(actions, newPhyValues, oldPhyValues);

    } finally {
        if (body.getState() == BodyState.HANDS_OFF) {
            body.setState(BodyState.ALIVE);
        }
    }
}
```

### 6️⃣ Controlador Genera Acción FIRE

```java
// Controller.java - decideActions()
public List<ActionDTO> decideActions(List<Event> events) {
    List<ActionDTO> allActions = new ArrayList<>();
    
    for (Event event : events) {
        List<ActionDTO> actions = this.applyGameRules(event);
        if (actions != null) {
            allActions.addAll(actions);
        }
    }
    
    return allActions;
}

// Controller.java - applyGameRules()
private List<ActionDTO> applyGameRules(Event event) {
    List<ActionDTO> actions = new ArrayList<>(8);
    
    switch (event.eventType) {
        case MUST_FIRE:
            actions.add(new ActionDTO(event.entityIdPrimaryBody,
                    ActionType.FIRE,           // ← ACCIÓN
                    ActionExecutor.MODEL,      // ← Ejecutor
                    ActionPriority.NORMAL));
            break;
        
        case REACHED_NORTH_LIMIT:
            actions.add(new ActionDTO(event.entityIdPrimaryBody,
                    ActionType.REBOUND_IN_NORTH, 
                    ActionExecutor.PHYSICS_BODY, 
                    ActionPriority.HIGH));
            break;
        
        // ... otros eventos ...
    }
    
    return actions;
}
```

### 7️⃣ Modelo Ejecuta la Acción FIRE

```java
// Model.java - doActions()
private void doActions(List<ActionDTO> actions, 
        PhysicsValuesDTO newPhyValues, PhysicsValuesDTO oldPhyValues) {
    
    for (ActionDTO action : actions) {
        Body body = this.getBodies().get(action.entityId);
        
        switch (action.executor) {
            case MODEL:
                this.doModelAction(action.actionType, body, newPhyValues, oldPhyValues);
                break;
            
            case PHYSICS_BODY: 
                this.doPhysicsAction(action.actionType, body, newPhyValues, oldPhyValues);
                break;
        }
    }
}

// Model.java - doModelAction()
private void doModelAction(ActionType action, Body body,
        PhysicsValuesDTO newPhyValues, PhysicsValuesDTO oldPhyValues) {
    
    if (body == null) {
        return;
    }

    switch (action) {
        case FIRE:
            this.spawnProjectileFrom(body, newPhyValues);  // ← CREA PROYECTIL
            break;
        
        case DIE:
            this.killDynamicBody(body);
            break;
        
        case EXPLODE_IN_FRAGMENTS:
            break;
        
        default:
    }
}
```

### 8️⃣ Creación del Proyectil

```java
// Model.java - spawnProjectileFrom()
private void spawnProjectileFrom(Body shooter, PhysicsValuesDTO shooterNewPhy) {
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

    // Calcula dirección de disparo
    double angleDeg = shooterNewPhy.angle;
    double angleRad = Math.toRadians(angleDeg);
    double dirX = Math.cos(angleRad);
    double dirY = Math.sin(angleRad);

    // Calcula posición inicial (con offset desde el jugador)
    double angleInRads = Math.toRadians(shooterNewPhy.angle - 90);
    double posX = shooterNewPhy.posX + Math.cos(angleInRads) * weaponConfig.shootingOffset;
    double posY = shooterNewPhy.posY + Math.sin(angleInRads) * weaponConfig.shootingOffset;

    // Velocidad inicial = velocidad del jugador + velocidad de disparo
    double projSpeedX = shooterNewPhy.speedX + weaponConfig.firingSpeed * dirX;
    double projSpeedY = shooterNewPhy.speedY + weaponConfig.firingSpeed * dirY;

    // Aceleración (para misiles)
    double accX = weaponConfig.acceleration * dirX;
    double accY = weaponConfig.acceleration * dirY;

    // Crea el proyectil (con su propio thread)
    String entityId = this.addProjectile(weaponConfig.projectileSize,
            posX, posY, projSpeedX, projSpeedY,
            accX, accY, angleDeg, 0d, 0d, 0d, weaponConfig.maxLifeTime,
            shooter.getEntityId());

    if (entityId == null || entityId.isEmpty()) {
        return; // ======= Max entity quantity reached =======>>
    }
    
    // Notifica al controlador para registrar el visual
    this.domainEventProcessor.notifyNewProjectileFired(
            entityId, weaponConfig.projectileAssetId);
}
```

### 9️⃣ Notificación a la Vista

```java
// Controller.java - implementa DomainEventProcessor
public void notifyNewProjectileFired(String entityId, String assetId) {
    this.view.addDynamicRenderable(entityId, assetId);  // ← Registra sprite visual
}
```

---

## 📊 Diagrama de Flujo Completo

```
━━━━━━━━━━━ THREAD PRINCIPAL (UI) ━━━━━━━━━━━
Usuario presiona SPACE
    ↓
Controller.playerFire(playerId)
    ↓
Model.playerFire(playerId)
    ↓
PlayerBody.registerFireRequest()
    ↓
Weapon.registerFireRequest()  ← AtomicLong.set(nanoTime) [Thread-safe]

━━━━━━━━━━━ THREAD del PlayerBody (ejecutándose continuamente) ━━━━━━━━━━━
    ↓
DynamicBody.loop()
    ↓
PhysicsEngine.computeNext() → calcula nuevos valores físicos
    ↓
Model.processBodyEvents(this, newPhy, oldPhy)
    ↓
Model.detectEvents(body, newPhy, oldPhy)
    ↓
PlayerBody.mustFireNow(newPhy)
    ↓
Weapon.mustFireNow(dtSeconds)
    ↓
  ┌─ Verifica cooldown > 0? → SÍ → return false
  ├─ Verifica ammo <= 0? → SÍ → Recarga, return false
  ├─ Verifica hasRequest()? → NO → return false
  └─ ✅ TODO OK:
       - markAllRequestsHandled()
       - decCurrentAmmo()
       - setCooldown(1.0 / fireRate)  ← CONTROL DE CADENCIA
       - return true
    ↓
¿mustFireNow() retorna true?
    ↓ SÍ
Model crea: Event(MUST_FIRE)
    ↓
Controller.decideActions([Event(MUST_FIRE)])
    ↓
Controller.applyGameRules(MUST_FIRE)
    ↓
Controller retorna: [ActionDTO(FIRE, MODEL, NORMAL)]
    ↓
Model.doActions([ActionDTO(FIRE)])
    ↓
Model.doModelAction(FIRE, body, newPhy, oldPhy)
    ↓
Model.spawnProjectileFrom(body, newPhy)
    ↓
  - Calcula posición (con shootingOffset)
  - Calcula velocidad inicial (velocidad body + firingSpeed)
  - Calcula aceleración (para misiles)
    ↓
Model.addProjectile(...) → Crea DynamicBody del proyectil
    ↓
━━━━━━━━━━━ NUEVO THREAD del Proyectil ━━━━━━━━━━━
    ↓
El proyectil inicia su propio loop()
    ↓
Model.notifyNewProjectileFired(entityId, assetId)
    ↓
Controller.notifyNewProjectileFired(entityId, assetId)
    ↓
View.addDynamicRenderable(entityId, assetId)
```

---

## 🔧 Configuración de Armas (WeaponDto)

Cada arma está configurada mediante un DTO inmutable:

```java
public class WeaponDto {
    public final WeaponType type;
    public final String projectileAssetId;    // Sprite del proyectil
    public final double projectileSize;       // Tamaño/radio de colisión
    public final double firingSpeed;          // Velocidad inicial del proyectil
    public final double acceleration;         // Aceleración (para misiles)
    public final double accelerationTime;     // Duración de la aceleración
    public final double shootingOffset;       // Distancia desde el centro del jugador
    public final int burstSize;               // Proyectiles por ráfaga
    public final int burstFireRate;           // Cadencia dentro de la ráfaga
    public final double fireRate;             // Disparos por segundo
    public final int maxAmmo;                 // Munición máxima
    public final double reloadTime;           // Tiempo de recarga (segundos)
    public final double projectileMass;       // Masa del proyectil
    public final double maxLifeTime;          // Vida máxima del proyectil (segundos)
}
```

### Parámetros Clave para la Cadencia

- **`fireRate`**: Disparos por segundo (ej: 5 = un disparo cada 0.2s)
- **`burstSize`**: Cantidad de proyectiles por ráfaga (ej: 3)
- **`burstFireRate`**: Cadencia dentro de la ráfaga (ej: 10 = 0.1s entre disparos)
- **`reloadTime`**: Tiempo de recarga cuando se agota la munición

---

## 🎯 Tipos de Armas

El sistema usa el patrón Factory para crear diferentes tipos:

```java
// WeaponFactory.java
public static Weapon create(WeaponDto weaponConfig) {
    switch (weaponConfig.type) {
        case PRIMARY_WEAPON:
            return new BasicWeapon(weaponConfig);      // Disparo simple
        
        case SECONDARY_WEAPON:
            return new BurstWeapon(weaponConfig);      // Ráfagas
        
        case MISSILE_LAUNCHER:
            return new MissileLauncher(weaponConfig);  // Misiles con aceleración
        
        case MINE_LAUNCHER:
            return new MineLauncher(weaponConfig);     // Minas
        
        default:
            throw new IllegalArgumentException(
                    "Tipo de arma desconocido: " + weaponConfig.type);
    }
}
```

### BasicWeapon

Arma semiautomática simple con cooldown entre disparos.

**Comportamiento:**
- Un disparo por solicitud
- Cooldown de `1.0 / fireRate` segundos entre disparos
- Recarga automática al agotar munición

### BurstWeapon

Arma que dispara múltiples proyectiles en ráfaga rápida por cada solicitud.

**Comportamiento:**
- Dispara `burstSize` proyectiles por solicitud
- Cooldown entre disparos de ráfaga: `1.0 / burstFireRate`
- Cooldown entre ráfagas: `1.0 / fireRate`
- Cancela ráfagas al recargar

```java
// BurstWeapon.java - Fragmento clave
@Override
public boolean mustFireNow(double dtSeconds) {
    // ... verificaciones de cooldown y munición ...
    
    if (this.shotsRemainingInBurst > 0) {
        // Ráfaga en curso
        this.markAllRequestsHandled();
        this.shotsRemainingInBurst--;
        this.decCurrentAmmo();

        if (this.shotsRemainingInBurst == 0) {
            // Ráfaga terminada
            this.setCooldown(1.0 / this.getWeaponConfig().fireRate);
        } else {
            // Más disparos en la ráfaga
            this.setCooldown(1.0 / this.getWeaponConfig().burstFireRate);
        }

        return true;
    }

    if (!this.hasRequest()) {
        return false;
    }

    // Inicia nueva ráfaga
    this.markAllRequestsHandled();
    int burstSize = Math.max(1, getWeaponConfig().burstSize);
    this.shotsRemainingInBurst = burstSize - 1;
    this.decCurrentAmmo();

    if (this.shotsRemainingInBurst == 0) {
        this.setCooldown(1.0 / this.getWeaponConfig().fireRate);
    } else {
        this.setCooldown(1.0 / this.getWeaponConfig().burstFireRate);
    }
    
    return true;
}
```

### MissileLauncher

Lanzador de misiles con aceleración sostenida.

**Comportamiento:**
- Similar a `BasicWeapon` en cadencia
- Los proyectiles tienen `acceleration` y `accelerationTime` configurados
- La aceleración se aplica durante `accelerationTime` segundos después del lanzamiento

### MineLauncher

Lanzador de minas (proyectiles sin velocidad inicial o con velocidad muy baja).

**Comportamiento:**
- Similar a `BasicWeapon`
- Típicamente `firingSpeed = 0` o muy bajo
- Las minas quedan estáticas o con movimiento mínimo

---

## 🔑 Responsabilidades por Componente

| Componente | Responsabilidad |
|------------|----------------|
| **Weapon.mustFireNow()** | **DECISOR**: Determina si técnicamente puede disparar (cooldown, munición, solicitudes) |
| **PlayerBody.mustFireNow()** | Delega la decisión al arma activa |
| **Model.detectEvents()** | Crea el evento `MUST_FIRE` si `mustFireNow()` retorna `true` |
| **Controller.applyGameRules()** | Convierte el evento `MUST_FIRE` en acción `FIRE` |
| **Model.doModelAction()** | Ejecuta la acción `FIRE` creando el proyectil físicamente |
| **Model.spawnProjectileFrom()** | Calcula física inicial y crea la entidad proyectil |
| **Controller.notifyNewProjectileFired()** | Registra el sprite visual del proyectil |

---

## 🧵 Thread-Safety

### Registro de Solicitudes

```java
// AbstractWeapon.java
private final AtomicLong lastFireRequest = new AtomicLong(0L);
protected long lastHandledRequest = 0L;

@Override
public void registerFireRequest() {
    this.lastFireRequest.set(System.nanoTime());  // Thread-safe
}

protected boolean hasRequest() {
    return this.lastFireRequest.get() > this.lastHandledRequest;
}

protected void markAllRequestsHandled() {
    this.lastHandledRequest = this.lastFireRequest.get();
}
```

**Diseño:**
- `AtomicLong` permite que el thread UI registre solicitudes sin bloqueos
- Solo la solicitud más reciente importa (no hay cola)
- Las solicitudes durante cooldown o recarga se descartan
- Comportamiento predecible y determinista

---

## 📈 Ejemplos de Configuración

### Arma Rápida (High Fire Rate)

```java
new WeaponDto(
    WeaponType.PRIMARY_WEAPON,
    "laser_projectile",
    5.0,           // projectileSize
    800.0,         // firingSpeed
    0.0,           // acceleration
    0.0,           // accelerationTime
    1,             // burstSize
    0,             // burstFireRate (no usado)
    10.0,          // fireRate: 10 disparos/segundo → cooldown 0.1s
    100,           // maxAmmo
    2.0,           // reloadTime
    10.0,          // projectileMass
    3.0,           // maxLifeTime
    30.0           // shootingOffset
)
```

### Arma de Ráfagas

```java
new WeaponDto(
    WeaponType.SECONDARY_WEAPON,
    "bullet_projectile",
    3.0,           // projectileSize
    600.0,         // firingSpeed
    0.0,           // acceleration
    0.0,           // accelerationTime
    3,             // burstSize: 3 disparos por ráfaga
    15,            // burstFireRate: 15 disparos/s dentro de ráfaga → 0.067s
    5.0,           // fireRate: 5 ráfagas/segundo → cooldown 0.2s
    200,           // maxAmmo
    4.0,           // reloadTime
    10.0,          // projectileMass
    0.5,           // maxLifeTime
    25.0           // shootingOffset
)
```

### Lanzamisiles

```java
new WeaponDto(
    WeaponType.MISSILE_LAUNCHER,
    "missile_projectile",
    8.0,           // projectileSize
    0.0,           // firingSpeed: inicia desde velocidad del jugador
    500.0,         // acceleration: 500 unidades/s²
    1.0,           // accelerationTime: 1 segundo de propulsión
    1,             // burstSize
    0,             // burstFireRate
    2.0,           // fireRate: 2 misiles/segundo → cooldown 0.5s
    4,             // maxAmmo
    4.0,           // reloadTime
    1000.0,        // projectileMass
    5.0,           // maxLifeTime
    35.0           // shootingOffset
)
```

---

## 🎓 Conclusión

El sistema de armas de MVCGameEngine implementa:

1. **Threading por entidad**: Cada body procesa su lógica de disparo en su propio thread
2. **Decisión centralizada**: `mustFireNow()` es el único punto que decide si disparar
3. **Arquitectura evento-acción**: Separación clara entre detección (evento) y ejecución (acción)
4. **Thread-safety**: Uso de `AtomicLong` para solicitudes concurrentes
5. **Cadencia precisa**: Control mediante cooldown calculado desde `fireRate`
6. **Extensibilidad**: Fácil agregar nuevos tipos de armas mediante herencia de `AbstractWeapon`
7. **Configuración externa**: `WeaponDto` permite definir armas sin modificar código

Esta arquitectura garantiza un comportamiento determinista, predecible y thread-safe en un entorno altamente concurrente.