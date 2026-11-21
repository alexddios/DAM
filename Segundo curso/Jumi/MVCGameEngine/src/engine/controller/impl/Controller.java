package engine.controller.impl;

import java.util.ArrayList;
import java.util.List;

import engine.actions.ActionDTO;
import engine.assets.core.AssetCatalog;
import engine.controller.mappers.DynamicRenderableMapper;
import engine.controller.mappers.EmitterMapper;
import engine.controller.mappers.PlayerRenderableMapper;
import engine.controller.mappers.ProfilingStatisticsMapper;
import engine.controller.mappers.RenderableMapper;
import engine.controller.mappers.SpatialGridStatisticsMapper;
import engine.controller.ports.ActionsGenerator;
import engine.controller.ports.EngineState;
import engine.controller.ports.WorldManager;
import engine.events.domain.ports.eventtype.DomainEvent;
import engine.model.bodies.ports.BodyData;
import engine.model.emitter.ports.EmitterConfigDto;
import engine.model.impl.Model;
import engine.model.ports.DomainEventProcessor;
import engine.utils.helpers.DoubleVector;
import engine.view.core.View;
import engine.view.renderables.ports.DynamicRenderDTO;
import engine.view.renderables.ports.PlayerRenderDTO;
import engine.view.renderables.ports.RenderDTO;
import engine.view.renderables.ports.SpatialGridStatisticsRenderDTO;
import engine.world.ports.DefEmitterDTO;

/**
 * Controller
 * ----------
 *
 * Central coordinator of the MVC triad that orchestrates the game engine:
 * - Owns references to Model and View
 * - Performs engine startup wiring (assets, world definition, dimensions)
 * - Bridges user input (View) into Model commands
 * - Transforms Model domain data (BodyDTO) into View render data (RenderDTO)
 * - Implements game rules by converting domain events into actions
 * - Manages entity lifecycle notifications between Model and View
 *
 * Implemented Interfaces
 * ----------------------
 * 1) WorldInitializer - Initial world setup (decorators, static bodies, assets)
 * 2) WorldEvolver - Runtime entity creation (players, dynamics, equipment)
 * 3) DomainEventProcessor - Game logic decision layer (events → actions)
 *
 * Responsibilities (high level)
 * -----------------------------
 *
 * 1) Bootstrapping / activation sequence
 * - Validates that all required dependencies are present (worldDimension,
 * model, view)
 * - Configures world dimensions in both Model and View
 * - Activates View (starts Renderer loop)
 * - Activates Model (enables entity creation and physics)
 * - Switches controller state to ALIVE when everything is ready
 *
 * 2) World building / entity creation
 * - addPlayer(): creates player entity, adds visual to View
 * - addDynamicBody(): creates dynamic entity, adds visual to View
 * - addDecorator() / addStaticBody(): creates static entity, pushes snapshot
 * to View
 * - addWeaponToPlayer() / addEmitterToPlayer(): equips player with weapons
 * or particle emitters
 *
 * Important: Static bodies and decorators are "push-updated" into the View.
 * After adding a static/decorator entity, the controller fetches a fresh
 * static snapshot from the Model and pushes it to the View via
 * updateStaticRenderables(). This matches the design where static visuals
 * usually do not change every frame, avoiding unnecessary per-frame updates.
 *
 * 3) Runtime command dispatch
 * - Exposes high-level player commands that the View calls in response to
 * user input:
 * * playerThrustOn / playerThrustOff / playerReverseThrust
 * * playerRotateLeftOn / playerRotateRightOn / playerRotateOff
 * * playerFire
 * * playerSelectNextWeapon
 * - All of these are simple delegations to the Model, keeping the View free
 * of simulation logic
 *
 * 4) Snapshot access for rendering
 * - getDynamicRenderablesData(): transforms Model's BodyDTO list into
 * DynamicRenderDTO list via mapper. Called once per frame by Renderer.
 * - getPlayerRenderData(playerId): transforms PlayerDTO into PlayerRenderDTO
 * for HUD/UI rendering
 * - getSpatialGridStatistics(): provides collision detection grid metrics
 * for debugging/monitoring
 * - Entity statistics: getEntityAliveQuantity(), getEntityCreatedQuantity(),
 * getEntityDeadQuantity()
 *
 * 5) Game rules / decision layer (DomainEventProcessor interface)
 * - provideActions delegates to the injected GameRulesEngine
 *
 * Data transformation (Mappers)
 * -----------------------------
 * The Controller uses dedicated mapper classes to translate between Model
 * domain DTOs and View render DTOs:
 * - DynamicRenderableMapper: BodyDTO → DynamicRenderDTO
 * - PlayerRenderableMapper: PlayerDTO → PlayerRenderDTO
 * - RenderableMapper: BodyDTO → RenderDTO (generic static bodies)
 * - WeaponMapper: WorldDefWeaponDTO → WeaponDto
 * - EmitterMapper: WorldDefEmitterDTO → EmitterConfigDto
 * - SpatialGridStatisticsMapper: SpatialGridStatisticsDTO →
 * SpatialGridStatisticsRenderDTO
 *
 * This layer ensures complete decoupling: Model knows nothing about rendering,
 * View knows nothing about physics simulation.
 *
 * Entity lifecycle notifications (DomainEventProcessor interface)
 * ----------------------------------------------------------------
 * The Controller acts as observer/notifier for entity lifecycle events:
 * - notifyNewDynamic(entityId, assetId): tells View to create visual for
 * dynamic entity
 * - notifyNewStatic(entityId, assetId): tells View to create visual for
 * static entity, then pushes static snapshot update
 * - notifyDynamicIsDead(entityId): tells View to remove dynamic visual
 * - notifyPlayerIsDead(entityId): tells View to remove player visual
 * - notifyStaticIsDead(entityId): triggers static snapshot update
 *
 * These notifications enable the View to maintain its own renderable
 * collections in sync with the Model's entity state.
 *
 * Engine state
 * ------------
 * engineState is volatile and represents the Controller's view of the engine
 * lifecycle:
 * - STARTING: initial state after construction
 * - ALIVE: set after activate() finishes successfully
 * - PAUSED: set via enginePause() (future use)
 * - STOPPED: set via engineStop() (future use)
 *
 * Dependency injection rules
 * --------------------------
 * - setModel(model): stores the model and injects the controller as
 * DomainEventProcessor (model.setDomainEventProcessor(this)). This enables
 * the Model to delegate game rules decisions to the Controller.
 * - setView(view): stores the view and injects the controller into the view
 * (view.setController(this)). This enables the View to send player commands
 * and pull rendering snapshots.
 * - Bidirectional injection creates a clean separation: Model owns simulation,
 * Controller owns rules, View owns rendering.
 *
 * Threading notes
 * ---------------
 * - The Controller itself mostly acts as a stateless facade
 * - Key concurrency point: Renderer thread calls getDynamicRenderablesData()
 * every frame (~60Hz)
 * - Static snapshots are pushed occasionally from Model thread when
 * static/decorator entities are created/destroyed
 * - All data transformations (mappers) create new DTO instances, preventing
 * shared mutable state between threads
 * - Volatile engineState ensures visibility across threads
 * - Keeping Controller methods small and side-effect-light reduces contention
 * and makes cross-thread interactions predictable
 *
 * Design goals
 * ------------
 * - Enforce strict layer separation via DTOs and mappers
 * - Provide a single point of control for game rules (GamesRulesEngine)
 * - Enable independent testing of Model (physics) and View (rendering)
 * - Support hot-swapping of game rules without touching Model or View code
 * - Minimize coupling: Model/View never reference each other directly
 */
public class Controller implements WorldManager, DomainEventProcessor {

    // region Fields
    private volatile EngineState engineState;
    private final ActionsGenerator gameRulesEngine;
    private Model model;
    private View view;
    private DoubleVector viewDimension;
    private DoubleVector worldDimension;
    private int maxBodies;
    // endregion

    // region Constructors
    public Controller(
            DoubleVector worldDim, DoubleVector viewDime, int maxBodies,
            View view, Model model,
            ActionsGenerator gameRulesEngine) {

        if (worldDim == null) {
            throw new IllegalArgumentException("Null world dimension");
        }
        if (viewDime == null) {
            throw new IllegalArgumentException("Null view dimension");
        }
        if (view == null) {
            throw new IllegalArgumentException("Null view");
        }
        if (model == null) {
            throw new IllegalArgumentException("Null model");
        }
        if (gameRulesEngine == null) {
            throw new IllegalArgumentException("Null game rules engine");
        }
        if (maxBodies <= 0) {
            throw new IllegalArgumentException("Invalid max dynamic bodies: " + maxBodies);
        }

        this.engineState = EngineState.STARTING;
        this.gameRulesEngine = gameRulesEngine;
        this.maxBodies = maxBodies;
        model.setMaxBodies(maxBodies);

        this.setModel(model);
        this.setView(view);
        this.setWorldDimension(worldDim);
        this.setViewDimension(viewDime);
    }
    // endregion

    // *** PUBLICS (alphabetical sort) ***

    public void activate() {
        if (this.worldDimension == null) {
            throw new IllegalArgumentException("Null world dimension");
        }

        if (this.viewDimension == null) {
            throw new IllegalArgumentException("Null view dimension");
        }

        if (this.view == null) {
            throw new IllegalArgumentException("No view injected");
        }

        if (this.model == null) {
            throw new IllegalArgumentException("No model injected");
        }

        this.view.activate();
        this.model.activate();
        this.engineState = EngineState.ALIVE;
        System.out.println("Controller: Activated ");
    }

    // region Engine (engine**)
    public void enginePause() {
        this.engineState = EngineState.PAUSED;
    }

    public void engineStop() {
        this.engineState = EngineState.STOPPED;
    }
    // endregion Engine

    // region Getters
    public EngineState getEngineState() {
        return this.engineState;
    }

    public int getEntityAliveQuantity() {
        return this.model.getAliveQuantity();
    }

    public int getEntityCreatedQuantity() {
        return this.model.getCreatedQuantity();
    }

    public int getEntityDeadQuantity() {
        return this.model.getDeadQuantity();
    }

    public DoubleVector getWorldDimension() {
        return this.worldDimension;
    }

    public PlayerRenderDTO getPlayerRenderData(String playerId) {
        return PlayerRenderableMapper.fromPlayerDTO(this.model.getPlayerData(playerId));
    }

    public Object[] getProfilingHUDValues(long fps) {
        return ProfilingStatisticsMapper.fromProfilingStatistics(
                this.model.getProfilingStatistics(), fps);
    }

    public SpatialGridStatisticsRenderDTO getSpatialGridStatistics() {
        return SpatialGridStatisticsMapper.fromSpatialGridStatisticsDTO(

                this.model.getSpatialGridStatistics());
    }
    // endregion Getters

    // region Player commands
    public void playerFire(String playerId) {
        this.model.playerFire(playerId);
    }

    public void playerThrustOn(String playerId) {
        this.model.playerThrustOn(playerId);
    }

    public void playerThrustOff(String playerId) {
        this.model.playerThrustOff(playerId);
    }

    public void playerReverseThrust(String playerId) {
        this.model.playerReverseThrust(playerId);
    }

    public void playerRotateLeftOn(String playerId) {
        this.model.playerRotateLeftOn(playerId);
    }

    public void playerRotateOff(String playerId) {
        this.model.playerRotateOff(playerId);
    }

    public void playerRotateRightOn(String playerId) {
        this.model.playerRotateRightOn(playerId);
    }

    public void playerSelectNextWeapon(String playerId) {
        this.model.playerSelectNextWeapon(playerId);
    }
    // endregion

    // region Queries
    public ArrayList<String> queryEntitiesInRegion(
            double minX, double maxX, double minY, double maxY,
            int[] scratchCellIndices, ArrayList<String> scratchEntityIds) {

        // Query al modelo (que tiene el SpatialGrid)
        return this.model.queryEntitiesInRegion(
                minX, maxX, minY, maxY,
                scratchCellIndices, scratchEntityIds);
    }
    // endregion

    // region setters
    public void setLocalPlayer(String playerId) {
        this.view.setLocalPlayer(playerId);
    }

    public void setModel(Model model) {
        this.model = model;
        this.model.setDomainEventProcessor(this);
    }

    public void setView(View view) {
        this.view = view;
        this.view.setController(this);
    }

    public void setViewDimension(DoubleVector d) {
        if (d == null) {
            throw new IllegalArgumentException("View dimension cannot be null");
        }
        if (d.x <= 0 || d.y <= 0) {
            throw new IllegalArgumentException("Invalid view dimension: " + d);
        }
        this.viewDimension = d;
        this.view.setViewDimension(d);
    }

    public void setWorldDimension(DoubleVector d) {
        if (d == null) {
            throw new IllegalArgumentException("World dimension cannot be null");
        }
        if (d.x <= 0 || d.y <= 0) {
            throw new IllegalArgumentException("Invalid world dimension: " + d);
        }

        this.worldDimension = new DoubleVector(d);
        this.model.setWorldDimension(d);
        this.view.setWorldDimension(d);
    }
    // endregion setters

    public ArrayList<DynamicRenderDTO> snapshotRenderData() {
        ArrayList<BodyData> snapshot = this.model.snapshotRenderData();
        ArrayList<DynamicRenderDTO> renderables = new ArrayList<>();

        for (BodyData bodyData : snapshot) {
            DynamicRenderDTO renderable = DynamicRenderableMapper.fromBodyDTO(bodyData);
            renderables.add(renderable);
        }

        return renderables;
    }

    public ArrayList<DynamicRenderDTO> snapshotRenderData(DynamicRenderableMapper mapper) {
        ArrayList<BodyData> snapshot = this.model.snapshotRenderData();
        return mapper.fromBodyDTOPooled(snapshot);
    }

    // *** INTERFACE IMPLEMENTATIONS (one region per interface) ***

    // region DomainEventProcessor
    @Override
    public void provideActions(List<DomainEvent> domainEvents, List<ActionDTO> actions) {
        this.gameRulesEngine.provideActions(domainEvents, actions);
    }

    @Override
    public void notifyDynamicIsDead(String entityId) {
        this.view.notifyDynamicIsDead(entityId);
    }

    @Override
    public void notifyPlayerIsDead(String entityId) {
        this.view.notifyPlayerIsDead(entityId);
    }

    @Override
    public void notifyNewDynamic(String entityId, String assetId) {
        this.view.addDynamicRenderable(entityId, assetId);
    }

    @Override
    public void notifyNewStatic(String entityId, String assetId) {
        this.view.addStaticRenderable(entityId, assetId);

        this.updateStaticRenderablesView();
    }

    @Override
    public void notifyStaticIsDead(String entityId) {
        this.updateStaticRenderablesView();
    }
    // endregion DomainEventProcessor

    // region WorldManager
    @Override
    public void addDecorator(String assetId, double size, double posX, double posY, double angle) {
        String entityId = this.model.addDecorator(size, posX, posY, angle, -1L);

        if (entityId == null || entityId.isEmpty()) {
            return; // ======= Max entity quantity reached =======>
        }

        this.view.addStaticRenderable(entityId, assetId);
        this.updateStaticRenderablesView();
    }

    @Override
    public void addDynamicBody(String assetId, double size, double posX, double posY,
            double speedX, double speedY, double accX, double accY,
            double angle, double angularSpeed, double angularAcc, double thrust) {

        String entityId = this.model.addDynamic(size, posX, posY, speedX, speedY,
                accX, accY, angle, angularSpeed, angularAcc, thrust, -1L);

        if (entityId == null || entityId.isEmpty()) {
            return; // ======= Max entity quantity reached =======>
        }
        this.view.addDynamicRenderable(entityId, assetId);
    }

    @Override
    public String addPlayer(String assetId, double size, double posX, double posY,
            double speedX, double speedY, double accX, double accY,
            double angle, double angularSpeed, double angularAcc, double thrust) {

        String entityId = this.model.addPlayer(size, posX, posY, speedX, speedY,
                accX, accY, angle, angularSpeed, angularAcc, thrust, -1L);

        if (entityId == null) {
            return null; // ======= Max entity quantity reached =======>>
        }

        this.view.addDynamicRenderable(entityId, assetId);
        return entityId;
    }

    @Override
    public void addStaticBody(String assetId, double size, double posX, double posY, double angle) {

        String entityId = this.model.addStatic(size, posX, posY, angle, -1L);
        if (entityId == null || entityId.isEmpty()) {
            return; // ======= Max entity quantity reached =======>>
        }

        this.view.addStaticRenderable(entityId, assetId);
        this.updateStaticRenderablesView();
    }

    @Override
    public void equipTrail(String playerId, DefEmitterDTO bodyEmitterDef) {
        EmitterConfigDto bodyEmitter = EmitterMapper.fromWorldDef(bodyEmitterDef);
        this.model.bodyEquipTrail(playerId, bodyEmitter);
    }

    @Override
    public void equipWeapon(String playerId, DefEmitterDTO bodyEmitterDef, int shootingOffset) {

        EmitterConfigDto bodyEmitter = EmitterMapper.fromWorldDef(bodyEmitterDef);
        this.model.playerEquipWeapon(playerId, bodyEmitter);
    }

    @Override
    public void loadAssets(AssetCatalog assets) {
        this.view.loadAssets(assets);
    }
    // endregion

    // *** PRIVATE (Internal, helpers, ...) ***

    private void updateStaticRenderablesView() {
        ArrayList<BodyData> bodiesData = this.model.getStaticsData();
        ArrayList<RenderDTO> renderablesData = RenderableMapper.fromBodyDTO(bodiesData);
        this.view.updateStaticRenderables(renderablesData);
    }
}