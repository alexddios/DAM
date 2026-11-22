# Copilot Coding Prompt - MVCGameEngine

> **Quick reference for AI-assisted code generation**  
> Full documentation: [CODE_ORGANIZATION_STANDARD.md](CODE_ORGANIZATION_STANDARD.md)

---

## 🚨 CRITICAL RULES

### 1. **ALPHABETICAL ORDER IS MANDATORY**
- **NEVER** break alphabetical order for any reason
- Functional grouping comes from **naming conventions**, not manual organization
- Methods are sorted alphabetically within their visibility section

### 2. **NO INNER CLASSES**
- All classes must be external in the same package
- Use sibling files, not nested classes

### 3. **REQUIRED SUBSECTIONS** (if class contains them)
```java
// region Constants
// endregion Constants

// region Fields
// endregion Fields

// region Constructors
// endregion Constructors
```

---

## 📋 CLASS STRUCTURE TEMPLATE

```java
package engine.example;

// region Constants
private static final int MAX_VALUE = 100;
// endregion Constants

// region Fields
private final String name;
private int counter;
// endregion Fields

// region Constructors
public ExampleClass(String name) {
    this.name = name;
}
// endregion Constructors

// *** PUBLICS ***

// region Add (optional: 3+ methods with same prefix)
public void addItem(Item item) { }
public void addListener(Listener listener) { }
// endregion Add

// region Get
public int getCounter() { }
public String getName() { }
// endregion Get

// region Is
public boolean isActive() { }
public boolean isValid() { }
// endregion Is

// region Set
public void setCounter(int value) { }
public void setName(String name) { }
// endregion Set

public void otherMethod() { }  // Alphabetically placed
public void start() { }
public void stop() { }

// *** INTERFACE IMPLEMENTATIONS ***

// region InterfaceA (MANDATORY regions by interface)
@Override
public void methodA1() { }

@Override
public void methodA2() { }
// endregion InterfaceA

// region InterfaceB
@Override
public void methodB1() { }
// endregion InterfaceB

// *** PRIVATE ***

private void calculateSomething() { }
private void initializeDefaults() { }
private void updateInternalState() { }
```

---

## 🏷️ NAMING CONVENTIONS (for automatic grouping)

| Prefix | Purpose | Example |
|--------|---------|---------|
| `add*` | Entity creation/addition | `addPlayer()`, `addWeapon()` |
| `get*` | Queries (no side effects) | `getPlayerData()`, `getCount()` |
| `set*` | Configuration/Mutation | `setModel()`, `setView()` |
| `is*` / `has*` | Boolean checks | `isAlive()`, `hasWeapon()` |
| `{entity}*` | Entity commands | `playerFire()`, `weaponReload()` |
| `engine*` | Lifecycle/Engine control | `engineStart()`, `enginePause()` |
| `notify*` | Event notifications | `notifyPlayerDied()` |
| `load*` | Batch/Catalog loading | `loadAssets()`, `loadConfig()` |
| `calculate*` | Computations | `calculatePhysics()` |
| `update*` | State updates | `updatePosition()` |
| `configure*` | Setup operations | `configureMetrics()` |
| `custom*` | Customization hooks | `customReport()` |

**Key principle:** Prefixes create automatic alphabetical clustering

---

## 📦 SECTION ORDER

1. **Constants** (// region Constants)
2. **Fields** (// region Fields)
3. **Constructors** (// region Constructors)
4. **// *** PUBLICS *** ** (alphabetical, optional regions)
5. **// *** INTERFACE IMPLEMENTATIONS *** ** (MANDATORY regions by interface)
6. **// *** PRIVATE *** ** (alphabetical, optional regions)
7. **// *** PRIVATE STATIC *** ** (if needed)

---

## ✅ REGION RULES

### Required Regions
- `// region Constants` - if class has constants
- `// region Fields` - if class has fields
- `// region Constructors` - if class has constructors
- `// region InterfaceName` - MANDATORY for each interface in INTERFACE IMPLEMENTATIONS

### Optional Regions (use when 3+ methods share prefix)
- `// region Add` - for `add*` methods
- `// region Get` - for `get*` methods
- `// region Set` - for `set*` methods
- `// region Is` - for `is*` / `has*` methods
- Custom regions for entity prefixes (`// region Player`, etc.)

### Region Guidelines
- ✅ Regions maintain alphabetical order
- ✅ Methods inside regions are alphabetically sorted
- ✅ Regions follow where their content naturally falls alphabetically
- ❌ Don't use regions for 1-2 methods (unnecessary clutter)

---

## 🔍 QUICK VALIDATION CHECKLIST

Before submitting generated code, verify:

- [ ] Alphabetical order is strict (no exceptions)
- [ ] Constants, Fields, Constructors have their `// region` subsections
- [ ] No inner classes (all classes are external)
- [ ] PUBLICS section uses optional regions for 3+ same-prefix methods
- [ ] INTERFACE IMPLEMENTATIONS has one region per interface
- [ ] Methods use consistent naming prefixes for functional grouping
- [ ] Regions don't break alphabetical order
- [ ] Section headers use correct format: `// *** SECTION ***`

---

## 📚 EXAMPLE: Real Class Structure

```java
// region Fields
private final ProfilerConfig config;
private final ConcurrentHashMap<String, Metric> metricsMap;
// endregion Fields

// region Constructors
public Profiler(ProfilerConfig config) {
    this.config = config;
    this.metricsMap = new ConcurrentHashMap<>();
}
// endregion Constructors

// *** PUBLICS ***

// region Add
public void addMetric(String key) { }
public void addValue(String key, long value) { }
// endregion Add

// region Get
public Metric getMetric(String key) { }
public Map<String, Metric> getMetrics() { }
public String getMetricString(String key) { }
// endregion Get

public void start() { }
public void stop() { }

// *** PRIVATE ***

private void captureSnapshot() { }
private void resetMetrics() { }
private void updateMetric(String key, long value) { }
```

**Alphabetical verification:**
- Add* (addMetric < addValue) ✅
- Get* (getMetric < getMetrics < getMetricString) ✅
- start < stop ✅
- Private: capture < reset < update ✅

---

## 💡 WHEN GENERATING CODE

1. **Start with structure**: Constants → Fields → Constructors
2. **Apply naming conventions**: Use consistent prefixes
3. **Sort alphabetically**: Within each section
4. **Add regions**: For 3+ methods with same prefix
5. **Verify order**: Double-check alphabetical sorting
6. **No inner classes**: Extract to external files

---

## 🎯 COMMON PATTERNS

### Pattern: Getter/Setter pairs
```java
// region Get
public String getName() { }
public int getValue() { }
// endregion Get

// region Set
public void setName(String name) { }
public void setValue(int value) { }
// endregion Set
```

### Pattern: Entity commands
```java
// region Player
public void playerFire() { }
public void playerJump() { }
public void playerRotate(float angle) { }
// endregion Player
```

### Pattern: Lifecycle
```java
// Alphabetically placed individually (if only 1-2 methods)
public void start() { }
public void stop() { }

// Or grouped if 3+ methods:
// region Engine
public void enginePause() { }
public void engineResume() { }
public void engineStart() { }
public void engineStop() { }
// endregion Engine
```

---

## 🚫 ANTI-PATTERNS (Never do this)

❌ **Breaking alphabetical order for grouping**
```java
// BAD
public void start() { }
public void stop() { }
public void addItem() { }  // Out of order!
```

❌ **Inner classes**
```java
// BAD
public class Outer {
    private static class Inner { }  // NEVER
}
```

❌ **Regions breaking alphabetical flow**
```java
// BAD
// region Lifecycle
public void activate() { }  // A
public void start() { }     // S
// endregion Lifecycle

public void getItem() { }  // G is between A and S - WRONG ORDER
```

✅ **Correct: alphabetical with natural regions**
```java
// GOOD
public void activate() { }  // A

public void getItem() { }   // G

public void start() { }     // S
```

---

## 📖 Reference

For complete documentation with detailed examples and rationale:  
👉 [CODE_ORGANIZATION_STANDARD.md](CODE_ORGANIZATION_STANDARD.md)

---

**Last Updated:** 2026-02-07  
**Project:** MVCGameEngine  
**Standard Version:** 1.0
