# Análisis de Implementación del Patrón Strategy en Balls Engine

**Fecha:** 2025-12-17  
**Usuario:** jumibot  
**Tema:** Aplicación del Patrón Strategy para hacer el engine fácilmente ampliable

---

## Contexto Inicial

Se solicitó analizar el proyecto **jumibot/Balls** para aplicar correctamente el patrón Strategy donde se requiera, especialmente en el sistema de Weapons (Wepon). El objetivo es hacer el engine fácilmente ampliable.

---

## Estado Actual del Proyecto

### ✅ Patrón Strategy Ya Implementado

#### 1. Sistema de Armas (Weapons)
- **Interface**: `Weapon`
- **Clase Abstracta**: `AbstractWeapon`
- **Implementación Concreta**: `BasicWeapon`

```java
public interface Weapon {
    WeaponDto getWeaponConfig();
    String getId();
    void registerFireRequest();
    boolean mustFireNow(double dtSeconds);
}
```

#### 2. Sistema de Físicas (PhysicsEngine)
- **Interface**: `PhysicsEngine`
- **Implementaciones**: `BasicPhysicsEngine`, `NullPhysicsEngine`

---

## Puntos de Implementación del Patrón Strategy

### 1. Sistema de Físicas (PhysicsEngine) ✅ YA IMPLEMENTADO PARCIALMENTE

**Estado actual**: Ya tiene Strategy implementado
- Interface: `PhysicsEngine`
- Implementaciones: `BasicPhysicsEngine`, `NullPhysicsEngine`

**Mejora recomendada**: Añadir más estrategias según TO-DO

#### Pasos para mejorarlo:

1. **Añadir nueva estrategia para campo gravitacional**:
```java
public class GravitationalPhysicsEngine extends AbstractPhysicsEngine implements PhysicsEngine {
    private List<GravitySource> gravitySources;
    
    @Override
    public PhysicsValues calcNewPhysicsValues() {
        // Implementar física con gravedad
    }
}
```

2. **Permitir cambio dinámico de motor**:
```java
// En DynamicBody cambiar de: 
private final BasicPhysicsEngine phyEngine;
// A:
private PhysicsEngine phyEngine; // Sin final, sin tipo concreto

public void setPhysicsEngine(PhysicsEngine newEngine) {
    this.phyEngine = newEngine;
}
```

---

### 2. Sistema de Armas (Weapon) ✅ BIEN IMPLEMENTADO - EXPANDIBLE

**Estado actual**: Excelente base con Strategy

#### 2.1. BurstWeapon (Arma de ráfagas)

**Pasos de implementación**:

1. **Crear la clase `BurstWeapon.java`** en `src/model/weapons/`:

```java
package model.weapons;

public class BurstWeapon extends AbstractWeapon {
    
    private double cooldown = 0.0;
    private int shotsRemaining = 0;
    private double burstInterval = 0.05; // 50ms entre disparos de ráfaga
    private double timeSinceLastShot = 0.0;
    
    public BurstWeapon(String projectileAssetId, double projectileSize,
            double firingSpeed, double acceleration, double accelerationTime,
            double shootingOffset, int burstSize, double fireRate) {
        
        super(projectileAssetId, projectileSize, firingSpeed, acceleration, 
              accelerationTime, shootingOffset, burstSize, fireRate);
    }
    
    @Override
    public boolean mustFireNow(double dtSeconds) {
        // Paso 1: Reducir cooldown general
        if (this.cooldown > 0) {
            this.cooldown -= dtSeconds;
            this.markAllRequestsHandled();
            return false;
        }
        
        // Paso 2: Manejar ráfaga activa
        if (this.shotsRemaining > 0) {
            this.timeSinceLastShot += dtSeconds;
            
            if (this.timeSinceLastShot >= this.burstInterval) {
                this.timeSinceLastShot = 0;
                this.shotsRemaining--;
                return true; // Disparar siguiente de la ráfaga
            }
            return false;
        }
        
        // Paso 3: Iniciar nueva ráfaga si hay solicitud
        if (this.hasRequest()) {
            this.markAllRequestsHandled();
            this.shotsRemaining = this.getWeaponConfig().burstSize - 1;
            this.timeSinceLastShot = 0;
            this.cooldown = 1.0 / this.getWeaponConfig().fireRate;
            return true; // Primer disparo de la ráfaga
        }
        
        return false;
    }
}
```

2. **Actualizar `Model.java`** para usar la nueva estrategia:

```java
public void addWeaponToPlayer(String playerId, String projectileAssetId, 
        double projectileSize, double firingSpeed, double acceleration, 
        double accelerationTime, double shootingOffset, int burstSize, 
        double fireRate) {
    
    PlayerBody pBody = this.pBodies.get(playerId);
    if (pBody == null) {
        return;
    }
    
    // CAMBIO: Decidir estrategia según burstSize
    Weapon weapon;
    if (burstSize > 1) {
        weapon = new BurstWeapon(projectileAssetId, projectileSize,
                firingSpeed, acceleration, accelerationTime,
                shootingOffset, burstSize, fireRate);
    } else {
        weapon = new BasicWeapon(projectileAssetId, projectileSize,
                firingSpeed, acceleration, accelerationTime,
                shootingOffset, burstSize, fireRate);
    }
    
    pBody.addWeapon(weapon);
}
```

#### 2.2. MissileWeapon (Misiles con aceleración)

**Pasos de implementación**:

1. **Crear `MissileWeapon.java`**:

```java
package model.weapons;

public class MissileWeapon extends AbstractWeapon {
    
    private double cooldown = 0.0;
    
    public MissileWeapon(String projectileAssetId, double projectileSize,
            double firingSpeed, double acceleration, double accelerationTime,
            double shootingOffset, int burstSize, double fireRate) {
        
        super(projectileAssetId, projectileSize, firingSpeed, acceleration,
              accelerationTime, shootingOffset, burstSize, fireRate);
    }
    
    @Override
    public boolean mustFireNow(double dtSeconds) {
        if (this.cooldown > 0) {
            this.cooldown -= dtSeconds;
            this.markAllRequestsHandled();
            return false;
        }
        
        if (!this.hasRequest()) {
            return false;
        }
        
        // Misiles: disparo simple con configuración especial
        this.markAllRequestsHandled();
        this.cooldown = 1.0 / this.getWeaponConfig().fireRate;
        return true;
    }
}
```

2. **Usar Factory Pattern para creación**:

```java
// Crear WeaponFactory.java
package model.weapons;

public class WeaponFactory {
    
    public static Weapon createWeapon(WeaponType type, 
            String projectileAssetId, double projectileSize,
            double firingSpeed, double acceleration, 
            double accelerationTime, double shootingOffset, 
            int burstSize, double fireRate) {
        
        switch (type) {
            case BASIC:
                return new BasicWeapon(projectileAssetId, projectileSize,
                        firingSpeed, acceleration, accelerationTime,
                        shootingOffset, burstSize, fireRate);
            
            case BURST:
                return new BurstWeapon(projectileAssetId, projectileSize,
                        firingSpeed, acceleration, accelerationTime,
                        shootingOffset, burstSize, fireRate);
            
            case MISSILE:
                return new MissileWeapon(projectileAssetId, projectileSize,
                        firingSpeed, acceleration, accelerationTime,
                        shootingOffset, burstSize, fireRate);
            
            default:
                throw new IllegalArgumentException("Unknown weapon type: " + type);
        }
    }
}

public enum WeaponType {
    BASIC, BURST, MISSILE
}
```

---

### 3. Sistema de Rebotes (Rebound Strategy) ⚠️ NUEVO - ALTA PRIORIDAD

**Problema actual**: La lógica de rebote está hardcodeada en `AbstractPhysicsEngine`

**Solución con Strategy**:

#### Pasos de implementación:

1. **Crear interface `ReboundStrategy.java`**:

```java
package model.physics.rebound;

import model.physics.PhysicsValues;

public interface ReboundStrategy {
    PhysicsValues applyRebound(PhysicsValues newVals, PhysicsValues oldVals,
                               double worldWidth, double worldHeight,
                               ReboundDirection direction);
}

public enum ReboundDirection {
    EAST, WEST, NORTH, SOUTH
}
```

2. **Implementar estrategias concretas**:

```java
// Rebote elástico (actual)
package model.physics.rebound;

public class ElasticReboundStrategy implements ReboundStrategy {
    @Override
    public PhysicsValues applyRebound(PhysicsValues newVals, PhysicsValues oldVals,
                                      double worldWidth, double worldHeight,
                                      ReboundDirection direction) {
        switch (direction) {
            case EAST:
                return new PhysicsValues(newVals.timeStamp,
                    0.0001, newVals.posY,
                    -newVals.speedX, newVals.speedY,
                    newVals.accX, newVals.accY,
                    oldVals.angle, oldVals.angularSpeed, 
                    oldVals.angularAcc, oldVals.thrust);
            // ... otros casos
        }
    }
}

// Rebote con pérdida de energía
public class DampedReboundStrategy implements ReboundStrategy {
    private final double dampingFactor; // 0.8 = pierde 20% energía
    
    public DampedReboundStrategy(double dampingFactor) {
        this.dampingFactor = dampingFactor;
    }
    
    @Override
    public PhysicsValues applyRebound(PhysicsValues newVals, PhysicsValues oldVals,
                                      double worldWidth, double worldHeight,
                                      ReboundDirection direction) {
        // Similar pero multiplicando velocidad por dampingFactor
    }
}

// Sin rebote (destrucción en contacto)
public class DestructiveReboundStrategy implements ReboundStrategy {
    @Override
    public PhysicsValues applyRebound(...) {
        // Marcar entidad para destrucción
        return null; // Señal de destrucción
    }
}
```

3. **Modificar `AbstractPhysicsEngine`**:

```java
public abstract class AbstractPhysicsEngine {
    private final AtomicReference<PhysicsValues> phyValues;
    private ReboundStrategy reboundStrategy; // NUEVO
    
    public AbstractPhysicsEngine(PhysicsValues phyValues, 
                                 ReboundStrategy reboundStrategy) {
        this.phyValues = new AtomicReference(phyValues);
        this.reboundStrategy = reboundStrategy != null ? 
            reboundStrategy : new ElasticReboundStrategy();
    }
    
    public void setReboundStrategy(ReboundStrategy strategy) {
        this.reboundStrategy = strategy;
    }
    
    public void reboundInEast(PhysicsValues newVals, PhysicsValues oldVals,
                              double worldWidth, double worldHeight) {
        PhysicsValues result = reboundStrategy.applyRebound(
            newVals, oldVals, worldWidth, worldHeight, ReboundDirection.EAST);
        if (result != null) {
            this.setPhysicsValues(result);
        }
    }
    // Similar para West, North, South
}
```

---

### 4. Sistema de Colisiones (Collision Resolution Strategy) ⚠️ NUEVO

**Según TO-DO**: "2) Colision detection"

#### Pasos de implementación:

1. **Crear interface `CollisionStrategy.java`**:

```java
package model.collision;

public interface CollisionStrategy {
    CollisionResult resolveCollision(AbstractEntity entity1, 
                                     AbstractEntity entity2,
                                     PhysicsValues phy1, 
                                     PhysicsValues phy2);
}

public class CollisionResult {
    public final PhysicsValues newPhysics1;
    public final PhysicsValues newPhysics2;
    public final boolean entity1Destroyed;
    public final boolean entity2Destroyed;
    public final double damageToEntity1;
    public final double damageToEntity2;
    
    // Constructor...
}
```

2. **Implementar estrategias**:

```java
// Colisión elástica
public class ElasticCollisionStrategy implements CollisionStrategy {
    @Override
    public CollisionResult resolveCollision(...) {
        // Calcular conservación de momento
    }
}

// Colisión destructiva
public class DestructiveCollisionStrategy implements CollisionStrategy {
    @Override
    public CollisionResult resolveCollision(...) {
        // Ambas entidades destruidas
    }
}

// Colisión con daño
public class DamageCollisionStrategy implements CollisionStrategy {
    @Override
    public CollisionResult resolveCollision(...) {
        // Calcular daño según masa y velocidad
    }
}
```

3. **Integrar en entidades**:

```java
public abstract class AbstractEntity {
    protected CollisionStrategy collisionStrategy;
    
    public void setCollisionStrategy(CollisionStrategy strategy) {
        this.collisionStrategy = strategy;
    }
    
    public CollisionStrategy getCollisionStrategy() {
        return this.collisionStrategy != null ? 
            this.collisionStrategy : new ElasticCollisionStrategy();
    }
}
```

---

### 5. Sistema de Reglas del Juego (Game Rules Strategy) ⚠️ NUEVO

**Según TO-DO**: "5) Game rules"

#### Pasos de implementación:

1. **Crear interface `GameRuleStrategy.java`**:

```java
package controller.rules;

import java.util.List;

public interface GameRuleStrategy {
    List<ActionDTO> evaluateEvent(AbstractEntity entity, EventDTO event);
    boolean isGameOver();
    String getWinner();
}
```

2. **Implementar reglas específicas**:

```java
// Deathmatch
public class DeathmatchRuleStrategy implements GameRuleStrategy {
    private int killsToWin;
    private Map<String, Integer> playerKills;
    
    @Override
    public List<ActionDTO> evaluateEvent(AbstractEntity entity, EventDTO event) {
        // Lógica específica de deathmatch
    }
}

// Survival
public class SurvivalRuleStrategy implements GameRuleStrategy {
    private long survivalTime;
    
    @Override
    public List<ActionDTO> evaluateEvent(AbstractEntity entity, EventDTO event) {
        // Lógica de supervivencia
    }
}

// Capture the Flag
public class CTFRuleStrategy implements GameRuleStrategy {
    // ...
}
```

3. **Usar en Controller**:

```java
public class Controller {
    private GameRuleStrategy gameRules;
    
    public void setGameRules(GameRuleStrategy rules) {
        this.gameRules = rules;
    }
    
    private List<ActionDTO> applyGameRules(AbstractEntity entity, EventDTO event) {
        return gameRules.evaluateEvent(entity, event);
    }
}
```

---

## Resumen de Prioridades

### Alta Prioridad (mejorar extensibilidad inmediata):
1. ✅ **Weapon System** - Añadir `BurstWeapon` y `MissileWeapon`
2. ⚠️ **Rebound Strategy** - Extraer lógica de rebotes
3. ⚠️ **Weapon Factory** - Facilitar creación de armas

### Media Prioridad (features nuevas):
4. ⚠️ **Collision Strategy** - Para TO-DO de colisiones
5. ⚠️ **Game Rules Strategy** - Para diferentes modos de juego

### Baja Prioridad (ya funcional):
6. ✅ **Physics Engine** - Ya implementado, solo expandir

---

## Beneficios de Estas Implementaciones

1. **Extensibilidad**: Añadir nuevos tipos sin modificar código existente
2. **Testabilidad**: Cada estrategia es testable independientemente
3. **Configurabilidad**: Cambiar comportamiento en runtime
4. **Mantenibilidad**: Código organizado y desacoplado
5. **Data-Driven**: Las estrategias pueden cargarse desde configuración

---

## Puntos Clave del Patrón Strategy

### Definición
El patrón **Strategy** es un patrón de diseño de comportamiento que permite definir una familia de algoritmos, encapsular cada uno de ellos y hacerlos intercambiables. Strategy permite que el algoritmo varíe independientemente de los clientes que lo utilizan.

### Estructura del Patrón

```
┌─────────────────┐
│    Context      │
│  (ShoppingCart) │──────┐
└─────────────────┘      │ usa
                         │
                         ▼
              ┌──────────────────┐
              │    <<interface>> │
              │     Strategy     │
              │  (PaymentStrategy)│
              └──────────────────┘
                       △
                       │ implementan
        ┌──────────────┼──────────────┐
        │              │              │
┌───────────────┐ ┌──────────┐ ┌────────────┐
│ ConcreteStratA│ │ ConcreteB│ │ ConcreteC  │
│(CreditCard)   │ │(PayPal)  │ │(Bitcoin)   │
└───────────────┘ └──────────┘ └────────────┘
```

### Componentes:
1. **Strategy (Interface)**: Define el contrato común
2. **ConcreteStrategy**: Implementaciones específicas de algoritmos
3. **Context**: Mantiene referencia a Strategy y delega trabajo

### Características Clave

#### 1. Encapsulación de Algoritmos
Cada algoritmo está en su propia clase, separado y cohesivo.

#### 2. Intercambiabilidad en Runtime
Permite cambiar el algoritmo dinámicamente durante la ejecución.

#### 3. Eliminación de Condicionales
Reemplaza complejos `if-else` o `switch` con polimorfismo.

### Principios SOLID que Cumple

1. **Single Responsibility Principle (SRP)**: Cada estrategia tiene una única responsabilidad
2. **Open/Closed Principle (OCP)**: Abierto a extensión, cerrado a modificación
3. **Dependency Inversion Principle (DIP)**: Depende de abstracciones, no de implementaciones concretas

### Ventajas

✅ **Flexibilidad**: Cambiar algoritmos en runtime  
✅ **Extensibilidad**: Añadir nuevas estrategias sin modificar código  
✅ **Testabilidad**: Cada estrategia se testea independientemente  
✅ **Reutilización**: Las estrategias pueden usarse en diferentes contextos  
✅ **Claridad**: Código más limpio y organizado  
✅ **Eliminación de condicionales**: Código más mantenible  

### Desventajas

❌ **Más clases**: Aumenta el número de objetos en el sistema  
❌ **Complejidad inicial**: Más código que un simple `if`  
❌ **El cliente debe conocer las estrategias**: Para elegir cuál usar  
❌ **Overhead**: Si las estrategias son muy simples, puede ser excesivo  

### Cuándo Usar Strategy

#### ✅ Úsalo cuando:
- Tienes múltiples variantes de un algoritmo
- Necesitas cambiar algoritmo en runtime
- Quieres eliminar condicionales complejos
- Diferentes clases usan algoritmos relacionados
- Necesitas ocultar detalles de implementación

#### ❌ NO lo uses cuando:
- Solo tienes un algoritmo simple
- Las variantes nunca cambiarán
- El overhead de clases no vale la pena
- El algoritmo es extremadamente simple

### Regla de Oro

> **"Si te encuentras escribiendo múltiples `if-else` o `switch` para elegir entre diferentes algoritmos o comportamientos, probablemente necesitas el patrón Strategy"**

---

## Referencias

- Código actual del proyecto en: https://github.com/jumibot/Balls
- Documentación de patrones en: `docs/es/`
- TO-DO del proyecto en: `src/main/Main.java`

---

**Nota**: Este documento sirve como guía de implementación para aplicar el patrón Strategy en el engine Balls y hacerlo más extensible y mantenible.
