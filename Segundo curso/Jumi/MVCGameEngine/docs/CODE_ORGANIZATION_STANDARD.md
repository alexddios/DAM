# Code Organization Standard Manual

## Introduction

This document defines the **hybrid organization standard** used in the MVCGameEngine project. This standard combines **strict alphabetical ordering** with **strategic naming conventions** to achieve both fast navigation and logical functional grouping.

### Key Principle
> **"Alphabetical order is never broken. Functional grouping emerges automatically through consistent naming conventions."**

---

## Why This Standard?

### Problems with traditional approaches:
- **Pure alphabetical:** Fast to search, but loses functional context
- **Pure functional grouping:** Logical, but subjective and hard to navigate in large classes
- **No standard:** Chaos, especially in team environments

### Our hybrid solution:
✅ **Predictable** - Methods are always where the alphabet says they are  
✅ **Scalable** - New methods insert naturally without reorganizing  
✅ **Self-documenting** - Method names reveal their category  
✅ **Tool-friendly** - IDE navigation, diffs, and merges work seamlessly  
✅ **Functional grouping** - Related methods cluster automatically via naming  

---

## The Five Structural Sections

Every class is organized into **Five sections** . A section is required if there is at least one method whose visibility matches that section.

Always in this order:

### Section Order

```java
2. // *** PUBLIC ***
3. // *** PUBLIC STATIC *** 
3. // *** INTERFACE IMPLEMENTATIONS ***
4. // *** PRIVATE ***
4. // *** PRIVATE STATIC ***
```

### Section Header Format

```java
// *** SECTION_NAME ***
```

**Rules:**
- Three asterisks before and after
- Section name in UPPERCASE
- Always on a single line
- No blank lines before the comment

---

## Subsections: Organizing Within Sections

### Three special subsection: Constants, Fields and Constructors
These subsections are required if the class contains any constants, fields, or constructors.

### The Hybrid Approach: Alphabetical Order + Subsections

While the **primary organizational principle is strict alphabetical order**, we use **optional subsections** to improve readability and enable IDE features like code folding.

### Two Mechanisms for Creating Subsections

#### 1. **Naming Conventions (Automatic Grouping)**

By using consistent prefixes in method names, related methods automatically cluster together alphabetically:

```java
// *** PUBLICS ***

// All "player" methods group together automatically:
playerFire()              // P-F
playerJump()              // P-J
playerRotate()            // P-R
playerSelectWeapon()      // P-S

// All "get" methods group together automatically:
getEntityCount()          // G-E
getPlayerData()           // G-P
getScore()                // G-S
```

**Key principle:** The alphabetical order creates the grouping automatically - no manual organization needed.

#### 2. **Region Markers (IDE Support - Optional)**

Region markers provide visual organization and code folding support in modern IDEs:

```java
// *** PUBLICS ***

// region Player commands
playerFire()
playerJump()
playerRotate()
playerSelectWeapon()
// endregion Player commands

// region Getters
getEntityCount()
getPlayerData()
getScore()
// endregion Getters
```

### Region Marker Rules

✅ **Regions are OPTIONAL** - They enhance readability but are not required  
✅ **Alphabetical order is NEVER broken** - Methods inside regions are still alphabetically sorted  
✅ **Regions follow alphabetical order** - Region markers should appear where their content naturally falls alphabetically  
✅ **Descriptive names** - Region names should clearly describe the functional group  
✅ **IDE folding support** - Most modern IDEs support `// region` / `// endregion` for code folding

### IDE Support for Region Markers

| IDE | Region Support | Syntax | Folding |
|-----|----------------|--------|---------|
| **IntelliJ IDEA** | ✅ Full support | `// region Name` / `// endregion` | ✅ Yes |
| **Eclipse** | ✅ Full support | `// region Name` / `// endregion` | ✅ Yes |
| **Visual Studio Code** | ✅ Full support | `// region Name` / `// endregion` | ✅ Yes |
| **NetBeans** | ✅ Full support | `// <editor-fold>` / `// </editor-fold>` | ✅ Yes |

**Benefits of region folding:**
- Collapse large functional groups to see the big picture
- Expand only the section you're working on
- Navigate large classes more efficiently
- Maintain visual context during refactoring

### Example: Regions with Alphabetical Order

```java
// *** PUBLICS ***

activate()                        // A - No region (single method)

// region Engine                  
enginePause()                     // E-P - Alphabetically sorted within region
engineStop()                      // E-S
// endregion Engine

// region Getters
getDynamicRenderablesData()       // G-D - Alphabetically sorted within region
getEngineState()                  // G-E
getEntityAliveQuantity()          // G-E-A
getEntityCreatedQuantity()        // G-E-C
getPlayerData()                   // G-P
// endregion Getters

// region Player commands
playerFire()                      // P-F - Alphabetically sorted within region
playerJump()                      // P-J
playerRotate()                    // P-R
playerSelectWeapon()              // P-S
// endregion Player commands

// region Setters
setModel()                        // S-M - Alphabetically sorted within region
setView()                         // S-V
// endregion Setters
```

**Visual breakdown:**
```
A: activate()
E: engine* (2 methods in region)
G: get* (5 methods in region)
P: player* (4 methods in region)
S: set* (2 methods in region)
```

### When to Use Regions

✅ **Use regions when:**
- You have 3+ methods sharing the same prefix (e.g., `player*`, `get*`)
- The functional group is conceptually important to highlight
- You want to enable code folding for better navigation

⚠️ **Don't use regions when:**
- You have only 1-2 methods in the group (unnecessary clutter)
- The group is obvious from method names alone
- You're breaking alphabetical order to force a region

### Critical Rules

🚨 **NEVER break alphabetical order for regions**

```java
// ❌ BAD - Regions break alphabetical order
// *** PUBLICS ***

// region Lifecycle
activate()
enginePause()
engineStop()
// endregion Lifecycle

// region Queries  
getPlayerData()
isAlive()
// endregion Queries
```

```java
// ✅ GOOD - Alphabetical order maintained, regions follow naturally
// *** PUBLICS ***

activate()                        // A

// region Engine
enginePause()                     // E-P
engineStop()                      // E-S
// endregion Engine

getPlayerData()                   // G

isAlive()                         // I
```

---

## Section 1: CONSTRUCTORS

**Rules:**
- Placed immediately after field declarations
- Multiple constructors ordered by parameter count (ascending)
- No regions allowed

**Example:**

```java
public class Controller {
    private Model model;
    private View view;
    
    public Controller(View view, Model model) {
        this.model = model;
        this.view = view;
    }
    
    // *** PUBLICS ***
    // ...
}
```

---

## Section 2: PUBLICS (Alphabetical)

**Rules:**
- All `public` methods that are NOT interface implementations
- **Strict alphabetical order** - no exceptions
- Optional subsections using `// region` markers (for IDE folding)
- Naming conventions create automatic functional grouping

### Format

```java
// *** PUBLICS ***

public void activate() { ... }

// region Engine
public void enginePause() { ... }
public void engineStop() { ... }
// endregion Engine

// region Getters
public int getEntityCount() { ... }
public String getPlayerName() { ... }
// endregion Getters

// region Player commands
public void playerFire() { ... }
public void playerJump() { ... }
// endregion Player commands

// region Setters
public void setModel(Model model) { ... }
public void setView(View view) { ... }
// endregion Setters
```

### Important Notes

1. **Regions are OPTIONAL** - They help with IDE code folding but don't change the alphabetical rule
2. **Methods inside regions are STILL alphabetical**
3. **Region names are descriptive** - They explain the functional group
4. **Alphabetical order is PRIMARY** - Regions are secondary organizational aids

---

## Section 3: INTERFACE IMPLEMENTATIONS

**Rules:**
- All methods with `@Override` annotation
- Grouped by interface name
- **Each interface group is alphabetical within itself**
- Interface groups are ordered alphabetically

### Format

```java
// *** INTERFACE IMPLEMENTATIONS ***

// region InterfaceA
@Override
public void methodA1() { ... }

@Override
public void methodA2() { ... }
// endregion InterfaceA

// region InterfaceB  
@Override
public void methodB1() { ... }

@Override
public void methodB2() { ... }
// endregion InterfaceB
```

### Example from Controller.java

```java
// *** INTERFACE IMPLEMENTATIONS ***

// region DomainEventProcessor
@Override
public void decideActions(...) { ... }

@Override
public void notifyDynamicIsDead(...) { ... }

@Override
public void notifyNewDynamic(...) { ... }

@Override
public void notifyNewStatic(...) { ... }

@Override
public void notifyPlayerIsDead(...) { ... }

@Override
public void notifyStaticIsDead(...) { ... }
// endregion DomainEventProcessor

// region WorldEvolver
@Override
public void addDynamicBody(...) { ... }

@Override
public void addEmitterToPlayer(...) { ... }

@Override
public String addPlayer(...) { ... }

@Override
public void addWeaponToPlayer(...) { ... }
// endregion WorldEvolver

// region WorldInitializer
@Override
public void addDecorator(...) { ... }

@Override
public void addStaticBody(...) { ... }

@Override
public void loadAssets(...) { ... }
// endregion WorldInitializer
```

**Alphabetical verification:**
- DomainEventProcessor: `decideActions` < `notifyDynamicIsDead` < `notifyNewDynamic` < ... ✅
- WorldEvolver: `addDynamicBody` < `addEmitterToPlayer` < `addPlayer` < `addWeaponToPlayer` ✅
- WorldInitializer: `addDecorator` < `addStaticBody` < `loadAssets` ✅

---

## Section 4: PRIVATE (Alphabetical)

**Rules:**
- All `private` methods
- **Strict alphabetical order**
- No subsections needed (typically fewer methods)

### Format

```java
// *** PRIVATE ***

private void applyGameRules(...) { ... }

private void calculatePhysics(...) { ... }

private void resolveCollision(...) { ... }

private void updateStaticRenderablesView() { ... }
```

---

## Naming Conventions: The Key to Functional Grouping

### The Strategy

**By using consistent prefixes in method names, alphabetical order automatically groups related methods together.**

### Standard Prefixes

| Prefix | Purpose | Examples | Alphabetical Group |
|--------|---------|----------|-------------------|
| `get*` | Query (no side effects) | `getPlayerData()`, `getEntityCount()` | All getters together |
| `set*` | Configuration/Mutation | `setModel()`, `setView()` | All setters together |
| `is*` / `has*` | Boolean queries | `isAlive()`, `hasWeapon()` | All predicates together |
| `{entity}*` | Entity commands | `playerFire()`, `weaponReload()` | Commands by entity |
| `engine*` | Lifecycle/Engine control | `engineStart()`, `enginePause()` | Engine operations |
| `notify*` | Event notifications | `notifyPlayerDied()`, `notifyCollision()` | All notifications |
| `add*` | Entity creation | `addPlayer()`, `addWeapon()` | All additions |
| `load*` | Batch/Catalog loading | `loadAssets()`, `loadConfig()` | All load operations |

### How It Works

**Example: Player commands**

```java
// All these methods cluster together alphabetically because they share the "player" prefix:

playerFire()              // P-F
playerReverseThrust()     // P-R
playerRotateLeftOn()      // P-R-L
playerRotateOff()         // P-R-O
playerRotateRightOn()     // P-R-R
playerSelectNextWeapon()  // P-S
playerThrustOff()         // P-T-O
playerThrustOn()          // P-T-O
```

**Result:**  
✅ All 8 player commands are together  
✅ Sub-families emerge: `playerRotate*` (3 methods), `playerThrust*` (2 methods)  
✅ Zero manual organization needed  

### Anti-Example: Breaking the Pattern

❌ **BAD:**
```java
fire()                    // F
playerJump()              // P-J
playerRotate()            // P-R
selectWeapon()            // S
thrust()                  // T
```

**Problem:**  
- Player commands scattered across F, P, S, T  
- No clear grouping  
- Hard to find related methods  

✅ **GOOD:**
```java
playerFire()              // P-F
playerJump()              // P-J
playerRotate()            // P-R
playerSelectWeapon()      // P-S
playerThrust()            // P-T
```

**Result:**
- All player commands together under "P"
- Clear functional family
- Easy to navigate

---

## Real-World Example: Controller.java

### PUBLICS Section Analysis

```java
// *** PUBLICS ***

activate()                        // A - Lifecycle

// region Engine
enginePause()                     // E - Engine lifecycle
engineStop()                      // E - Engine lifecycle
// endregion Engine

// region Getters
getDynamicRenderablesData()       // G-D - Rendering data
getEngineState()                  // G-E - Engine state
getEntityAliveQuantity()          // G-E-A - Entity stats
getEntityCreatedQuantity()        // G-E-C - Entity stats
getEntityDeadQuantity()           // G-E-D - Entity stats
getPlayerRenderData()             // G-P - Player data
getSpatialGridStatistics()        // G-S - Statistics
getWorldDimension()               // G-W - World info
// endregion Getters

// region Player commands
playerFire()                      // P-F - Player action
playerReverseThrust()             // P-R-T - Player movement
playerRotateLeftOn()              // P-R-L - Player rotation
playerRotateOff()                 // P-R-O - Player rotation
playerRotateRightOn()             // P-R-R - Player rotation
playerSelectNextWeapon()          // P-S - Player weapon
playerThrustOff()                 // P-T-O - Player movement
playerThrustOn()                  // P-T-O - Player movement
// endregion Player commands

// region Setters
setLocalPlayer()                  // S-L - Configuration
setModel()                        // S-M - Dependency injection
setView()                         // S-V - Dependency injection
setWorldDimension()               // S-W - Configuration
// endregion Setters
```

### Automatic Groupings Achieved

| Group | Methods | Naming Pattern |
|-------|---------|----------------|
| Engine lifecycle | 2 | `engine*` |
| Getters | 8 | `get*` |
| └─ Entity stats | 3 | `getEntity*` |
| └─ Rendering data | 2 | `get*Data()` / `get*Statistics()` |
| Player commands | 8 | `player*` |
| └─ Rotation | 3 | `playerRotate*` |
| └─ Thrust | 3 | `playerThrust*` / `playerReverseThrust` |
| Setters | 4 | `set*` |

**Benefits:**
- ✅ 8 player commands clustered together
- ✅ 8 getters form a query block
- ✅ 4 setters form a configuration block
- ✅ Sub-families visible: `playerRotate*`, `getEntity*`
- ✅ **Zero manual reorganization if we add `playerDuck()` - it auto-inserts at line P-D**

---

## Benefits of This Standard

### 1. **Predictable Navigation** ⭐⭐⭐⭐⭐
- "Where is `playerRotateOff()`?" → PUBLICS section, letter P
- O(log n) mental binary search
- No need to remember arbitrary groupings

### 2. **Zero Merge Conflicts** ⭐⭐⭐⭐⭐
- Alphabetical insertion point is deterministic
- Two developers adding methods don't conflict
- Git diffs show minimal changes

### 3. **Scalable to Large Classes** ⭐⭐⭐⭐⭐
- Works with 10 methods or 100 methods
- No reorganization needed as class grows
- Complexity stays O(log n) for search

### 4. **Self-Documenting** ⭐⭐⭐⭐
- Method name reveals category: `playerFire()` → player command
- Prefixes act as namespace: `get*`, `set*`, `notify*`
- No need for extensive comments explaining organization

### 5. **Tool-Friendly** ⭐⭐⭐⭐⭐
- IDE "Go to Symbol" works alphabetically
- Structure view shows natural groupings
- `region` markers enable code folding

### 6. **Code Review Friendly** ⭐⭐⭐⭐
- Reviewers know where to look for new methods
- Alphabetical violations are obvious
- Naming inconsistencies stand out

### 7. **Beginner-Friendly** ⭐⭐⭐⭐
- Simple rule: "Put it in alphabetical order"
- No subjective decisions about "logical grouping"
- Easy to verify compliance

---

## Common Mistakes and How to Fix Them

### ❌ Mistake 1: Breaking Alphabetical Order

```java
// BAD
playerFire()
playerJump()
setModel()              // ← Out of order!
playerRotate()
```

**Fix:**
```java
// GOOD
playerFire()
playerJump()
playerRotate()
setModel()              // ← Alphabetically correct
```

### ❌ Mistake 2: Inconsistent Naming

```java
// BAD
fire()                  // Should be playerFire()
playerJump()
selectWeapon()          // Should be playerSelectWeapon()
playerRotate()
```

**Fix:**
```java
// GOOD
playerFire()
playerJump()
playerRotate()
playerSelectWeapon()
```

### ❌ Mistake 3: Wrong Section

```java
// *** PUBLICS ***

@Override
public void notifyPlayerDied() {  // ← Interface method in PUBLICS section!
    // ...
}
```

**Fix:**
```java
// *** PUBLICS ***
// (no interface methods here)

// *** INTERFACE IMPLEMENTATIONS ***/

// region EventListener
@Override
public void notifyPlayerDied() {  // ← Correct section
    // ...
}
// endregion EventListener
```

### ❌ Mistake 4: Using Functional Sections Instead of Alphabetical

```java
// BAD - Functional sections break alphabetical order
// *** PUBLICS ***

// Lifecycle methods
activate()
engineStart()
engineStop()

// Queries
getPlayer()
isAlive()
```

**Fix:**
```java
// GOOD - Alphabetical with regions
// *** PUBLICS ***

activate()

// region Engine
engineStart()
engineStop()
// endregion Engine

getPlayer()

isAlive()
```

### ❌ Mistake 5: Missing Regions for Large Groups

```java
// HARD TO NAVIGATE - 20 player methods with no region marker
playerAttack()
playerBlock()
playerCrouch()
playerDash()
playerFire()
playerJump()
playerReload()
playerRun()
playerSlide()
playerSprint()
// ... 10 more
```

**Fix:**
```java
// EASY TO FOLD - Same alphabetical order, but with region
// region Player commands
playerAttack()
playerBlock()
playerCrouch()
playerDash()
playerFire()
playerJump()
playerReload()
playerRun()
playerSlide()
playerSprint()
// ... 10 more
// endregion Player commands
```

---

## Code Review Checklist

Use this checklist when reviewing code:

### Structure
- [ ] Constructor(s) come first
- [ ] Four sections present: Constructor, PUBLICS, INTERFACE IMPLEMENTATIONS, PRIVATE
- [ ] Section headers use `// *** SECTION_NAME ***` format

### Alphabetical Order
- [ ] PUBLICS section is alphabetically sorted
- [ ] Each interface group in INTERFACE IMPLEMENTATIONS is alphabetically sorted
- [ ] PRIVATE section is alphabetically sorted

### Naming Conventions
- [ ] Commands use entity prefix: `playerFire()`, `weaponReload()`
- [ ] Queries use `get*` / `is*` / `has*`
- [ ] Configuration uses `set*`
- [ ] Notifications use `notify*`
- [ ] No generic names like `fire()`, `update()`, `process()` without context

### Regions (Optional but Recommended)
- [ ] Large functional groups (>3 methods) use `// region Name` markers
- [ ] Region names are descriptive
- [ ] Methods inside regions are still alphabetical

### Interface Implementations
- [ ] All `@Override` methods are in INTERFACE IMPLEMENTATIONS section
- [ ] Grouped by interface name
- [ ] Interface groups are alphabetical
- [ ] Methods within each interface group are alphabetical

---

## Quick Reference Card

```java
// ============================================
// CLASS STRUCTURE TEMPLATE
// ============================================

package com.example;

import ...;

public class ExampleClass implements InterfaceA, InterfaceB {

    // Fields
    private Type field1;
    private Type field2;

    // Constructor(s)
    public ExampleClass(Type field1) {
        this.field1 = field1;
    }

    // *** PUBLICS ***

    public void activate() { ... }

    // region Entity Commands
    public void entityAction() { ... }
    public void entityMove() { ... }
    // endregion Entity Commands

    // region Getters
    public Type getData() { ... }
    public Type getState() { ... }
    // endregion Getters

    // region Setters
    public void setData(Type data) { ... }
    public void setView(View view) { ... }
    // endregion Setters

    // *** INTERFACE IMPLEMENTATIONS ***

    // region InterfaceA
    @Override
    public void methodA1() { ... }

    @Override
    public void methodA2() { ... }
    // endregion InterfaceA

    // region InterfaceB
    @Override
    public void methodB1() { ... }

    @Override
    public void methodB2() { ... }
    // endregion InterfaceB

    // *** PRIVATE ***

    private void helperMethod1() { ... }

    private void helperMethod2() { ... }
}
```

---

## Naming Convention Quick Reference

| Category | Pattern | Example |
|----------|---------|---------|
| **Commands** | `{entity}{Action}()` | `playerFire()`, `weaponReload()` |
| **Queries (data)** | `get{Property}()` | `getPlayerData()`, `getScore()` |
| **Queries (boolean)** | `is{State}()`, `has{Capability}()` | `isAlive()`, `hasAmmo()` |
| **Lifecycle** | `{system}{State}()` | `engineStart()`, `enginePause()` |
| **Configuration** | `set{Property}()` | `setModel()`, `setDifficulty()` |
| **Notifications** | `notify{Event}()` | `notifyPlayerDied()`, `notifyCollision()` |
| **Creation** | `add{Entity}()` | `addPlayer()`, `addWeapon()` |
| **Batch loading** | `load{Collection}()` | `loadAssets()`, `loadConfig()` |

---

## Summary

### Core Principles
1. **Alphabetical order is sacred** - Never break it
2. **Naming conventions create grouping** - Consistent prefixes cluster related methods
3. **Four structural sections** - Constructor, PUBLICS, INTERFACE IMPLEMENTATIONS, PRIVATE
4. **Regions are optional aids** - They help readability but don't override alphabetical order

### Benefits
- **Fast navigation:** O(log n) search time
- **Zero conflicts:** Deterministic insertion points
- **Scalable:** Works with any class size
- **Maintainable:** Clear rules, easy compliance
- **Functional grouping:** Emerges automatically from naming

### The Standard in One Sentence
> **"Organize alphabetically within each section, and let consistent naming conventions create functional groupings automatically."**

---

**Version:** 1.1  
**Last Updated:** 2026-01-22 16:10:04  
**Author:** JUAN MIGUEL RAMON TUR
