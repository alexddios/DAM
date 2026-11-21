package engine.model.bodies.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import engine.actions.ActionDTO;
import engine.events.domain.ports.BodyRefDTO;
import engine.events.domain.ports.eventtype.DomainEvent;
import engine.model.bodies.ports.BodyData;
import engine.model.bodies.ports.BodyEventProcessor;
import engine.model.bodies.ports.BodyState;
import engine.model.bodies.ports.BodyType;
import engine.model.emitter.impl.BasicEmitter;
import engine.model.impl.Model;
import engine.model.physics.core.AbstractPhysicsEngine;
import engine.model.physics.ports.PhysicsEngine;
import engine.model.physics.ports.PhysicsValuesDTO;
import engine.utils.pooling.PoolMDTO;
import engine.utils.spatial.core.SpatialGrid;

/**
 * AbstractBody
 * ------------
 *
 * Base class for all simulation entities in the game engine. Provides core
 * functionality for physics simulation, lifecycle management, spatial
 * partitioning, event processing, and emitter systems.
 *
 * This is the foundation of the entity system: all entities (players, dynamic
 * bodies, projectiles, decorators, gravity bodies) extend this class and
 * inherit its threading model, state machine, and physics integration.
 *
 * Core Responsibilities
 * ---------------------
 * - Lifecycle management: STARTING → ALIVE → DEAD state transitions
 * - Physics integration: owns and delegates to PhysicsEngine instance
 * - Spatial indexing: manages registration in SpatialGrid for collision
 * detection
 * - Event processing: delegates to BodyEventProcessor (typically the Model)
 * - Emitter management: supports multiple particle/trail emitters per body
 * - Entity statistics: tracks global alive/created/dead counts
 * - Scratch buffer allocation: provides pre-allocated buffers to avoid GC
 * pressure
 *
 * Architecture Pattern
 * --------------------
 * AbstractBody follows a "composition over inheritance" approach:
 * - PhysicsEngine handles all physics calculations (position, velocity,
 * acceleration)
 * - BodyEventProcessor handles event detection and action decisions
 * - SpatialGrid handles collision broad-phase queries
 * - Emitter instances handle particle/trail generation
 *
 * The body itself acts as a coordinator, delegating specialized work to
 * injected components while managing its own state and lifecycle.
 *
 * Entity Lifecycle
 * ----------------
 * 1) Construction:
 * - State = STARTING
 * - Unique bodyId (UUID) generated
 * - PhysicsEngine, BodyEventProcessor, SpatialGrid injected
 * - Scratch buffers pre-allocated
 * - bornTime recorded for lifetime tracking
 * - createdQuantity++ (static counter)
 *
 * 2) Activation (activate()):
 * - Validates state == STARTING
 * - State → ALIVE
 * - aliveQuantity++ (static counter)
 * - After activation, body participates in physics updates and collision
 * detection
 *
 * 3) Death (die()):
 * - State → DEAD
 * - deadQuantity++, aliveQuantity-- (static counters)
 * - Idempotent: multiple die() calls are safe
 * - Dead bodies are removed from SpatialGrid by the Model
 *
 * State Machine
 * -------------
 * Volatile BodyState field ensures thread-safe state transitions:
 *
 * STARTING: Initial state after construction
 * ↓ activate()
 * ALIVE: Active physics simulation, event processing enabled
 * ↓ processBodyEvents() temporarily transitions to...
 * HANDS_OFF: Critical section - event processing in progress (set by Model)
 * ↓ returns to ALIVE after event processing completes
 * DEAD: Entity is inactive, pending removal
 *
 * The HANDS_OFF state prevents concurrent event processing on the same body,
 * ensuring deterministic behavior in the multithreaded physics simulation.
 *
 * Physics Integration
 * -------------------
 * AbstractBody owns a PhysicsEngine instance that handles all physics
 * calculations:
 * - getPhysicsValues(): returns immutable PhysicsValuesDTO snapshot
 * - doMovement(phyValues): commits new physics state to engine
 * - reboundIn[East|West|North|South](): delegates boundary rebound to engine
 * - isThrusting(): queries engine for thrust state
 *
 * The body never performs physics calculations directly - it delegates to
 * PhysicsEngine. This allows swapping physics implementations without touching
 * entity code.
 *
 * Spatial Grid Integration
 * -------------------------
 * Bodies with spatial grid support (dynamic bodies) maintain their position
 * in the grid for efficient collision detection:
 * - spatialGridUpsert(): updates grid cells occupied by this body
 * - Uses body's bounding circle (posX, posY, size/2) to determine cells
 * - scratchIdxs buffer stores cell indices to avoid allocation
 * - Decorator bodies have spatialGrid == null (no collision detection)
 *
 * Event Processing Pipeline
 * --------------------------
 * processBodyEvents(body, newPhyValues, oldPhyValues):
 * - Delegates to BodyEventProcessor (typically the Model)
 * - Model detects events (collisions, limits, emissions, life over)
 * - Model decides actions based on events
 * - Model executes actions (movement, spawning, death)
 * - Uses scratch buffers to avoid allocation during event processing
 *
 * Emitter System
 * --------------
 * Bodies can be equipped with multiple emitters (particles, trails, etc.):
 * - emitterEquip(emitter): adds emitter, returns emitterId
 * - emitterRemove(emitterId): removes emitter
 * - emitterRequest(emitterId): triggers emission request
 * - checkActiveEmitters(dtSeconds): returns emitters ready to emit
 * - Emitters are stored in ConcurrentHashMap for thread-safe access
 *
 * Typical use cases:
 * - Player ship: thrust trail emitter, projectile emitter
 * - Projectile: trail emitter
 * - Asteroid: debris emitter on destruction
 *
 * Lifetime Management
 * -------------------
 * Bodies support configurable lifetimes:
 * - maxLifeInSeconds > 0: body dies after this duration
 * - maxLifeInSeconds == -1: infinite lifetime (players, static bodies)
 * - getLifeInSeconds(): current age in seconds
 * - getLifePercentage(): normalized age (0.0 = born, 1.0 = expired)
 * - isLifeOver(): true when current age >= maxLifeInSeconds
 *
 * Lifetime is checked during event processing and triggers LifeOver event.
 *
 * Scratch Buffers (Zero-Allocation Design)
 * -----------------------------------------
 * To minimize garbage collection pressure during physics updates, AbstractBody
 * pre-allocates scratch buffers that are reused across frames:
 *
 * - scratchIdxs: int[] for SpatialGrid cell indices
 * - scratchCandidateIds: ArrayList<String> for collision candidates
 * - scratchSeenCandidateIds: HashSet<String> for collision deduplication
 * - scratchEvents: ArrayList<DomainEvent> for event accumulation
 * - scratchActions: List<ActionDTO> for action accumulation
 *
 * All getScratch***() methods clear their buffer before returning, ensuring
 * fresh state for each physics update. This pattern enables 60+ Hz physics
 * updates with minimal GC pauses.
 *
 * Threading Model
 * ---------------
 * Each dynamic body typically runs on its own thread:
 * - setThread(thread): stores reference to owning thread
 * - Thread continuously calculates new physics state and calls
 * processBodyEvents()
 * - State machine (ALIVE ↔ HANDS_OFF) prevents concurrent event processing
 * - Static bodies (decorators, gravity) have no thread (static geometry)
 *
 * Thread safety:
 * - Volatile state field ensures visibility across threads
 * - Static counters (aliveQuantity, createdQuantity, deadQuantity) are volatile
 * - PhysicsEngine handles its own synchronization
 * - Emitters map is ConcurrentHashMap
 *
 * Global Entity Statistics
 * -------------------------
 * Static volatile counters track all entities across the simulation:
 * - aliveQuantity: currently active entities
 * - createdQuantity: total entities created (monotonic)
 * - deadQuantity: total entities destroyed (monotonic)
 *
 * These are updated atomically during activate() and die() and exposed via
 * static getters for monitoring and debugging.
 *
 * Design Goals
 * ------------
 * - Minimize per-frame allocations via scratch buffers
 * - Delegate specialized work to injected components (composition)
 * - Support heterogeneous entity types via inheritance (players, projectiles,
 * etc.)
 * - Enable deterministic multithreaded physics via state machine
 * - Provide clean integration points (PhysicsEngine, BodyEventProcessor,
 * SpatialGrid)
 * - Maintain thread-safe global statistics for debugging/monitoring
 */
public abstract class AbstractBody {

    // region Constants
    private static final double EMITTER_IMMUNITY_TIME = 0.5; // seconds
    // endregion

    // region Static Fields
    private static volatile int aliveQuantity = 0;
    private static volatile int createdQuantity = 0;
    private static volatile int deadQuantity = 0;
    // endregion

    // region Fields
    private final List<ActionDTO> actionsQueue = new ArrayList<>(64);
    private final String bodyEmitterId; // ID of the body that emit this body (or null)
    private final BodyEventProcessor bodyEventProcessor;
    private final String bodyId;
    private final long bornTime = System.nanoTime();
    private final Map<String, BasicEmitter> emitters = new ConcurrentHashMap<>();
    private final double maxLifeInSeconds; // Infinite life by default
    private final PhysicsEngine phyEngine;
    private volatile BodyState state;
    private Thread thread;
    private final BodyType type;
    // endregion
    
    // region Scratch buffers
    private final BodyRefDTO bodyRef;
    private final BodyData bodyData;
    private final SpatialGrid spatialGrid;
    private final int[] scratchIdxs;
    private final ArrayList<String> scratchCandidateIds;
    private final HashSet<String> scratchSeenCandidateIds = new HashSet<>(64);

    private final ArrayList<DomainEvent> scratchEvents = new ArrayList<>(64);
    // endregion

    // region Constructors
    public AbstractBody(BodyEventProcessor bodyEventProcessor, SpatialGrid spatialGrid,
            PhysicsEngine phyEngine, BodyType type,
            double maxLifeInSeconds, String emitterId) {

        this.bodyEventProcessor = bodyEventProcessor;
        this.phyEngine = phyEngine;
        this.type = type;
        this.maxLifeInSeconds = maxLifeInSeconds;
        this.bodyEmitterId = emitterId;

        if (spatialGrid != null) {
            this.spatialGrid = spatialGrid;
            this.scratchIdxs = new int[spatialGrid.getMaxCellsPerBody()];
            this.scratchCandidateIds = new ArrayList<String>(64);

        } else {
            this.spatialGrid = null;
            this.scratchIdxs = null;
            this.scratchCandidateIds = null;
        }

        this.bodyId = UUID.randomUUID().toString();
        this.state = BodyState.STARTING;
        this.bodyRef = new BodyRefDTO(this.bodyId, this.type);
        this.bodyData = new BodyData(this.bodyId, this.type, null);
    }
    // endregion

    // *** PUBLICS ***
    public synchronized void activate() {
        if (this.state != BodyState.STARTING) {
            throw new IllegalArgumentException("Entity activation error due is not starting!");
        }

        AbstractBody.aliveQuantity++;
        this.state = BodyState.ALIVE;
    }

    /**
     * Execute one physics/logic tick for this body.
     * 
     * This method contains the core logic that should be executed once per frame.
     * It is called either:
     * - From the body's own run() loop (when using individual threads)
     * - From MultiBodyRunner.executeBodyStep() (when using batched execution)
     * 
     * Subclasses must implement this to define their per-tick behavior.
     */
    public abstract void onTick();

    public void enqueueExternalAction(ActionDTO action) {
        if (action == null) {
            throw new IllegalArgumentException("Action cannot be null");
        }

        this.actionsQueue.add(action);
    }

    public synchronized void die() {
        if (this.state == BodyState.DEAD) {
            return;
        }

        this.state = BodyState.DEAD;
        AbstractBody.deadQuantity++;

        if (AbstractBody.aliveQuantity > 0) {
            AbstractBody.aliveQuantity--;
        }
        
        // Release 3 DTOs to pool from physics engine
        if (this.bodyEventProcessor instanceof Model && this.phyEngine instanceof AbstractPhysicsEngine) {
            Model model = (Model) this.bodyEventProcessor;
            AbstractPhysicsEngine engine = (AbstractPhysicsEngine) this.phyEngine;
            PoolMDTO<PhysicsValuesDTO> pool = model.getPhysicsValuesPool();
            
            // Release all 3 DTOs: current, next, and snapshot
            pool.release(engine.getPhysicsValues());
            pool.release(engine.getNextPhyValues());
            pool.release(engine.getSnapshotDTO());
        }
    }

    public void doMovement(PhysicsValuesDTO phyValues) {
        PhysicsEngine engine = this.getPhysicsEngine();
        engine.setPhysicsValues(phyValues);
    }

    // region Emitter management (emitter***())
    public List<BasicEmitter> emitterActiveList(double dtSeconds) {
        List<BasicEmitter> active = new ArrayList<>();

        for (BasicEmitter emitter : emitters.values()) {
            if (emitter.mustEmitNow(dtSeconds)) {
                active.add(emitter);
            }
        }

        return active;
    }

    public Collection<BasicEmitter> emittersList() {
        return this.emitters.values();
    }

    public boolean emittersListEmpty() {
        return !this.emitters.isEmpty();
    }

    public String emitterEquip(BasicEmitter emitter) {
        if (emitter == null) {
            throw new IllegalArgumentException("Emitter cannot be null");
        }

        this.emitters.put(emitter.getId(), emitter);
        return emitter.getId();
    }

    public void emitterRemove(String emitterId) {
        if (emitterId == null) {
            throw new IllegalArgumentException("EmitterId cannot be null");
        }

        this.emitters.remove(emitterId);
    }

    public void emitterRequest(String emitterId) {
        if (emitterId == null) {
            throw new IllegalArgumentException("EmitterId cannot be null");
        }
        BasicEmitter emitter = emitters.get(emitterId);
        if (emitter != null) {
            emitter.registerRequest();
        }
    }
    // endregion

    // region Body getters (getBody***())
    public BodyData getBodyData() {
        this.bodyData.setPhysicsValues(this.getPhysicsValues());
        return this.bodyData;
    }

    public String getBodyEmitterId() {
        return this.bodyEmitterId; // Body that emitted this body (emissor body)
    }

    public String getBodyId() {
        return this.bodyId;
    }

    public BodyRefDTO getBodyRef() {
        return this.bodyRef;
    }

    public BodyState getBodyState() {
        return this.state;
    }

    public BodyType getBodyType() {
        return this.type;
    }
    // endregion

    // region getEmitter()
    public BasicEmitter getEmitter(String emitterId) {
        if (emitterId == null) {
            throw new IllegalArgumentException("EmitterId cannot be null");
        }

        return this.emitters.get(emitterId);
    }
    // endregion

    // region Life getters (getLife***())
    public long getLifeBorn() {
        return this.bornTime;
    }

    public double getLifeInSeconds() {
        return (System.nanoTime() - this.bornTime) / 1_000_000_000.0D;
    }

    public double getLifeMaxInSeconds() {
        return this.maxLifeInSeconds;
    }

    public double getLifePercentage() {
        if (this.maxLifeInSeconds <= 0) {
            return 1D;
        }

        return Math.min(1D, this.getLifeInSeconds() / this.maxLifeInSeconds);
    }
    // endregion

    // region Physics getters (getPhysics***())
    public PhysicsEngine getPhysicsEngine() {
        return this.phyEngine;
    }

    public PhysicsValuesDTO getPhysicsValues() {
        return this.phyEngine.getPhysicsValues();
    }
    // endregion

    // region Scratch getters (getScratch***())
    public List<ActionDTO> getActionsQueue() {
        // Do NOT clear here - external actions may have been enqueued
        // The queue will be cleared after actions are executed in
        // Model.executeActionList()
        return this.actionsQueue;
    }

    public ArrayList<String> getScratchClearCandidateIds() {
        this.scratchCandidateIds.clear();
        return scratchCandidateIds;
    }

    public HashSet<String> getScratchClearSeenCandidateIds() {
        this.scratchSeenCandidateIds.clear();
        return this.scratchSeenCandidateIds;
    }

    public ArrayList<DomainEvent> getScratchClearEvents() {
        this.scratchEvents.clear();
        return this.scratchEvents;
    }

    public int[] getScratchIdxs() {
        return this.scratchIdxs;
    }
    // endregion

    // region SpatialGrid getter
    public SpatialGrid getSpatialGrid() {
        return this.spatialGrid;
    }
    // endregion

    // region boolean checks (is***)
    public boolean isEmitterImmune() {
        if (this.bodyEmitterId == null) {
            return false;
        }

        return this.getLifeInSeconds() < EMITTER_IMMUNITY_TIME;
    }

    public boolean isLifeOver() {
        if (this.maxLifeInSeconds < 0) {
            return false;
        }

        boolean lifeOver = this.getLifeInSeconds() >= this.maxLifeInSeconds;
        return lifeOver;
    }

    public boolean isThrusting() {
        return this.getPhysicsEngine().isThrusting();
    }
    // endregion

    public void processBodyEvents(AbstractBody body, PhysicsValuesDTO newPhyValues, PhysicsValuesDTO oldPhyValues) {
        this.bodyEventProcessor.processBodyEvents(body, newPhyValues, oldPhyValues);
    }

    // region Rebound methods
    public void reboundInEast(PhysicsValuesDTO phyValues, double worldWidth, double worldHeight) {

        PhysicsEngine engine = this.getPhysicsEngine();
        engine.reboundInEast(phyValues, worldWidth, worldHeight);
    }

    public void reboundInNorth(PhysicsValuesDTO phyValues, double worldWidth, double worldHeight) {

        PhysicsEngine engine = this.getPhysicsEngine();
        engine.reboundInNorth(phyValues, worldWidth, worldHeight);
    }

    public void reboundInWest(PhysicsValuesDTO phyValues, double worldWidth, double worldHeight) {
        PhysicsEngine engine = this.getPhysicsEngine();
        engine.reboundInWest(phyValues, worldWidth, worldHeight);
    }

    public void reboundInSouth(PhysicsValuesDTO phyValues, double worldWidth, double worldHeight) {

        PhysicsEngine engine = this.getPhysicsEngine();
        engine.reboundInSouth(phyValues, worldWidth, worldHeight);
    }
    // endregion

    // region Setters
    public void setState(BodyState state) {
        this.state = state;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
    // endregion

    public void spatialGridUpsert() {
        if (this.spatialGrid == null) {
            return;
        }

        final PhysicsValuesDTO phyValues = this.getPhysicsValues();

        final double r = phyValues.size * 0.5; // si size es radio, r = committed.size
        final double minX = phyValues.posX - r;
        final double maxX = phyValues.posX + r;
        final double minY = phyValues.posY - r;
        final double maxY = phyValues.posY + r;

        this.spatialGrid.upsert(this.getBodyId(), minX, maxX, minY, maxY, this.getScratchIdxs());
    }

    // *** STATICS ***

    // region PUBLIC getters for static counters
    static public int getAliveQuantity() {
        return AbstractBody.aliveQuantity;
    }

    static public int getCreatedQuantity() {
        return AbstractBody.createdQuantity;
    }

    static public int getDeadQuantity() {
        return AbstractBody.deadQuantity;
    }
    // endregion

    // region PROTECTED setters for static counters
    static protected int decAliveQuantity() {
        AbstractBody.aliveQuantity--;

        return AbstractBody.aliveQuantity;
    }

    static protected int incAliveQuantity() {
        AbstractBody.aliveQuantity++;

        return AbstractBody.aliveQuantity;
    }

    static protected int incCreatedQuantity() {
        AbstractBody.createdQuantity++;

        return AbstractBody.createdQuantity;
    }

    static protected int incDeadQuantity() {
        AbstractBody.deadQuantity++;

        return AbstractBody.deadQuantity;
    }
    // endregion
} 
