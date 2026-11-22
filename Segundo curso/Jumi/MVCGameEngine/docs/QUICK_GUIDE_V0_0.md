# MVCGameEngine

> **Versión:** V 0.0  
> **Estado:** Quick Guide cerrada (baseline)
 
## Guía práctica para programadores de arcades

*(con ejemplos reales de World, Level, IA y Rules)*

---

## Índice

1. [Antes de empezar: cómo pensar este engine](#1-antes-de-empezar-cómo-pensar-este-engine)
2. [El mapa mental completo](#2-el-mapa-mental-completo)
3. [World*: definir el universo visual y físico](#3-world-definir-el-universo-visual-y-físico)
   1. [Qué es World*](#31-qué-es-world)
   2. [ItemDTO vs PrototypeItemDTO (clave)](#32-itemdto-vs-prototypeitemdto-clave)
4. [LevelGenerator: construir la escena estática](#4-levelgenerator-construir-la-escena-estática)
5. [IAGenerator: dar vida al mundo](#5-iagenerator-dar-vida-al-mundo)
6. [ActionsGenerator: las reglas del juego](#6-actionsgenerator-las-reglas-del-juego)
7. [El movimiento y el tiempo](#7-el-movimiento-y-el-tiempo-muy-importante)
8. [El Main: cómo empezar un arcade sin miedo](#8-el-main-cómo-empezar-un-arcade-sin-miedo)
9. [Cómo crear tu primer arcade (recomendado para alumnos)](#9-cómo-crear-tu-primer-arcade-recomendado-para-alumnos)
10. [Regla final para aprender sin miedo](#10-regla-final-para-aprender-sin-miedo)

---

## 1. Antes de empezar: cómo pensar este engine

MVCGameEngine no es un engine “mágico”.  
Es un engine honesto.

👉 El core no decide nada del juego  
👉 Tú decides el juego combinando módulos

**Regla de oro:**

Si entiendes qué hace cada módulo,  
puedes crear un arcade sin tocar el core.

---

## 2. El mapa mental completo

```text
Main
 ├── World*               → qué existe y cómo se ve
 ├── LevelGenerator       → escena estática
 ├── IAGenerator          → dinámica (spawns)
 ├── ActionsGenerator     → reglas
 └── Core (MVC)           → tiempo, física, eventos
```

El Main no diseña el juego, solo conecta piezas.

### Descripción de módulos

| Módulo | Rol | Qué define |
|------|-----|------------|
| **Main** | Orquestador | Qué piezas se conectan para este arcade |
| **World\*** | Universo base | Qué existe, cómo se ve y propiedades físicas base |
| **LevelGenerator** | Escena inicial | Objetos estáticos, decorado, tablero inicial |
| **IAGenerator** | Dinámica | Cuándo y dónde aparecen entidades |
| **ActionsGenerator** | Reglas | Qué pasa cuando ocurre un evento |
| **Core (MVC)** | Motor | Tiempo, física, eventos, commits |

---

## 3. World*: definir el universo visual y físico

### 3.1 Qué es World*

World* responde a:

**¿Qué cosas existen en este juego y cómo son?**

Aquí defines:

- assets disponibles
- objetos fijos del nivel
- prototipos generativos (asteroides, etc.)

### 3.2 ItemDTO vs PrototypeItemDTO (clave)

#### ItemDTO – objetos fijos

Usa ItemDTO cuando:

- algo está siempre en el nivel
- no cambia de tamaño ni posición

Ejemplos:

- el sol
- el fondo
- un planeta central

Estos los instala el **LevelGenerator**.

#### PrototypeItemDTO – objetos variables

Usa prototipos cuando:

- algo aparece muchas veces
- quieres variedad

Ejemplo típico: **asteroides**

El prototipo define:

- rangos de tamaño
- rangos de rotación
- densidad (material)

👉 El prototipo **no crea objetos**, solo define cómo pueden ser.

---

## 4. LevelGenerator: construir la escena estática

### Ejemplo real: BigSunInCenterLevelGenerator

```java
public class BigSunInCenterLevelGenerator {

    public BigSunInCenterLevelGenerator(
            Controller controller,
            WorldDefinition worldDefinition) {

        controller.loadAssets(worldDefinition.gameAssets);

        for (WorldDefItemDTO item : worldDefinition.staticItems) {
            controller.addStaticBody(item);
        }
    }
}
```

---

## 5. IAGenerator: dar vida al mundo

```java
while (running) {
    Thread.sleep(spawnDelay);
    controller.addDynamicBody(...);
}
```

---

## 6. ActionsGenerator: las reglas del juego

```java
if (event.type == EventType.OUT_OF_LIMITS) {
    actions.add(ActionDTO.rebound(event));
}
```

---

## 7. El movimiento y el tiempo

La física propone, las reglas deciden, el core commitea.

---

## 8. El Main: cómo empezar un arcade

```java
new BigSunInCenterLevelGenerator(controller, world);
new AsteroidSpawnIAGenerator(controller, world).activate();
```

---

## 9. Arcades recomendados

- Asteroides clásicos
- Sol central
- Zona prohibida

---

## 10. Regla final

IA → qué aparece  
Rules → qué pasa  
World → cómo se ve  
Level → escenario

---

## Cierre

**Los generadores de ejemplo son plantillas mentales.**
