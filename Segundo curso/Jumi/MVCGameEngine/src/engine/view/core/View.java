package engine.view.core;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import engine.assets.core.AssetCatalog;
import engine.assets.ports.AssetType;
import engine.controller.impl.Controller;
import engine.controller.mappers.DynamicRenderableMapper;
import engine.controller.ports.EngineState;
import engine.utils.helpers.DoubleVector;
import engine.utils.images.Images;
import engine.view.renderables.ports.DynamicRenderDTO;
import engine.view.renderables.ports.PlayerRenderDTO;
import engine.view.renderables.ports.RenderDTO;
import engine.view.renderables.ports.RenderMetricsDTO;
import engine.view.renderables.ports.SpatialGridStatisticsRenderDTO;

/**
 * View
 * ----
 *
 * Swing top-level window that represents the presentation layer of the engine.
 * This class wires together:
 * - The rendering surface (Renderer)
 * - Asset loading and image catalogs (Images)
 * - User input (KeyListener) and command dispatch to the Controller
 *
 * Architectural role
 * ------------------
 * View is a thin façade over rendering + input:
 * - It does not simulate anything.
 * - It does not own world state.
 * - It communicates with the model exclusively through the Controller.
 *
 * The Renderer pulls dynamic snapshots every frame (via View -> Controller),
 * while static/decorator snapshots are pushed into the View/Renderer only when
 * they change (to avoid redundant per-frame updates for entities that do not
 * move).
 *
 * Lifecycle
 * ---------
 * Construction:
 * - Creates the ControlPanel (UI controls, if any).
 * - Creates the Renderer (Canvas).
 * - Builds the JFrame layout and attaches the key listener.
 *
 * Activation (activate()):
 * - Validates mandatory dependencies (dimensions, background, image catalogs).
 * - Injects view dimensions and images into the Renderer.
 * - Starts the Renderer thread (active rendering loop).
 *
 * Asset management
 * ----------------
 * loadAssets(...) loads and registers all visual resources required by the
 * world:
 * - Background image (single BufferedImage).
 * - Dynamic body sprites (ships, asteroids, missiles, etc.).
 * - Static body sprites (gravity bodies, bombs, etc.).
 * - Decorator sprites (parallax / space decor).
 *
 * The View stores catalogs as Images collections, which are later converted
 * into GPU/compatible caches inside the Renderer (ImageCache).
 *
 * Engine state delegation
 * -----------------------
 * View exposes getEngineState() as a convenience bridge for the Renderer.
 * The render loop can stop or pause based on Controller-owned engine state.
 *
 * Input handling
 * --------------
 * Keyboard input is captured at the rendering Canvas level (Renderer is
 * focusable and receives the KeyListener) and translated into high-level
 * Controller commands:
 * - Thrust on/off (forward uses positive thrust; reverse thrust is handled
 * as negative thrust, and both are stopped via the same thrustOff command).
 * - Rotation left/right and rotation off.
 * - Fire: handled as an edge-triggered action using fireKeyDown to prevent
 * key repeat from generating continuous shots while SPACE is held.
 *
 * Focus and Swing considerations
 * -------------------------------
 * The Renderer is the focus owner for input. Focus is requested after the frame
 * becomes visible using SwingUtilities.invokeLater(...) to improve reliability
 * with Swing's event dispatch timing.
 *
 * Threading considerations
 * ------------------------
 * Swing is single-threaded (EDT), while rendering runs on its own thread.
 * This class keeps its responsibilities minimal:
 * - It only pushes static/decorator updates when needed.
 * - Dynamic snapshot pulling is done inside the Renderer thread through
 * View -> Controller getters.
 *
 * Design goals
 * ------------
 * - Keep the View as a coordinator, not a state holder.
 * - Keep rendering independent and real-time (active rendering).
 * - Translate user input into controller commands cleanly and predictably.
 */
public class View extends JFrame implements KeyListener, WindowFocusListener {

    // region Fields
    private BufferedImage background;
    private Controller controller;
    private final ControlPanel controlPanel;
    private final Images images;
    private String localPlayerId;
    private final Renderer renderer;
    private DoubleVector viewDimension;
    private DoubleVector viewportDimension;
    private DoubleVector worldDimension;
    private AtomicBoolean fireKeyDown = new AtomicBoolean(false);

    // Key state tracking (OS may consume key events without firing keyReleased)
    private final Set<Integer> pressedKeys = new HashSet<>();
    private boolean wasWindowFocused = true;
    // endregion Fields

    // region Constructors
    public View() {
        this.images = new Images("");
        this.controlPanel = new ControlPanel(this);
        this.renderer = new Renderer(this);
        this.createFrame();
    }

    public View(DoubleVector worldDimension, DoubleVector viewDimension) {
        this();
        this.worldDimension = new DoubleVector(worldDimension);
        this.viewDimension = new DoubleVector(viewDimension);
        this.createFrame();
    }
    // endregion

    // *** PUBLIC ***

    public void activate() {
        if (this.viewDimension == null) {
            throw new IllegalArgumentException("View dimensions not setted");
        }
        if (this.background == null) {
            // throw new IllegalArgumentException("Background image not setted");
        }
        if (this.images.getSize() == 0) {
            // throw new IllegalArgumentException("Images catalog is empty");
        }
        if (this.controller == null) {
            throw new IllegalArgumentException("Controller not setted");
        }
        if (this.worldDimension == null) {
            throw new IllegalArgumentException("World dimensions not setted");
        }

        // new
        DoubleVector renderDimension = this.viewportDimension == null
                ? this.viewDimension
                : this.viewportDimension;
        this.renderer.setViewDimension(renderDimension);

        // this.renderer.setViewDimension(this.viewDimension);
        this.renderer.activate();
        this.pack();
        System.out.println("View: Activated");
    }

    // region adders (add***)
    public void addDynamicRenderable(String entityId, String assetId) {
        this.renderer.addDynamicRenderable(entityId, assetId);
    }

    public void addStaticRenderable(String entityId, String assetId) {
        this.renderer.addStaticRenderable(entityId, assetId);
    }
    // endregion

    // region Getters (get***)
    public DoubleVector getWorldDimension() {
        if (this.worldDimension == null) {
            return null;
        }

        return new DoubleVector(this.worldDimension);
    }

    public DoubleVector getViewDimension() {
        return new DoubleVector(this.viewDimension);
    }
    // endregion

    // region Setters (set***)
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setLocalPlayer(String localPlayerId) {
        this.localPlayerId = localPlayerId;
        System.out.println("Viewer: Local player setted " + localPlayerId);
    }

    public void setViewDimension(DoubleVector viewDim) {
        this.viewDimension = viewDim;
    }

    public void setViewportDimension(DoubleVector viewportDim) {
        this.viewportDimension = viewportDim;
    }

    public void setWorldDimension(DoubleVector worldDim) {
        this.worldDimension = worldDim;
    }
    // endregion

    public void loadAssets(AssetCatalog assets) {
        String fileName;
        String path = assets.getPath();

        for (String assetId : assets.getAssetIds()) {
            fileName = assets.get(assetId).fileName;
            this.images.add(assetId, path + fileName);
        }

        // Setting background
        String backgroundId = assets.randomId(AssetType.BACKGROUND);
        System.out.println("View: Setting background image <" + backgroundId + ">");
        this.background = this.images.getImage(backgroundId).image;

        if (this.background == null) {
            throw new IllegalArgumentException("Background image could not be loaded");
        }

        this.renderer.setImages(this.background, this.images);
    }

    // region notifiers (notify***)
    public void notifyDynamicIsDead(String entityId) {
        this.renderer.notifyDynamicIsDead(entityId);
    }

    public void notifyPlayerIsDead(String entityId) {
        this.setLocalPlayer(null);
    }
    // endregion

    public void updateStaticRenderables(ArrayList<RenderDTO> renderablesData) {
        this.renderer.updateStaticRenderables(renderablesData);
    }

    // *** PROTECTED ***

    // region protected Getters (get***)
    protected ArrayList<DynamicRenderDTO> snapshotRenderData() {
        if (this.controller == null) {
            throw new IllegalArgumentException("Controller not setted");
        }

        return this.controller.snapshotRenderData();
    }

    protected ArrayList<DynamicRenderDTO> snapshotRenderData(DynamicRenderableMapper mapper) {
        if (this.controller == null) {
            throw new IllegalArgumentException("Controller not setted");
        }

        return this.controller.snapshotRenderData(mapper);
    }

    protected EngineState getEngineState() {
        return this.controller.getEngineState();
    }

    protected int getEntityAliveQuantity() {
        return this.controller.getEntityAliveQuantity();
    }

    protected int getEntityCreatedQuantity() {
        return this.controller.getEntityCreatedQuantity();
    }

    protected int getEntityDeadQuantity() {
        return this.controller.getEntityDeadQuantity();
    }

    protected PlayerRenderDTO getLocalPlayerRenderData() {
        if (this.localPlayerId == null || this.localPlayerId.isEmpty()) {
            return null;
        }

        return this.controller.getPlayerRenderData(this.localPlayerId);
    }

    protected String getLocalPlayerId() {
        return this.localPlayerId;
    }

    protected Object[] getProfilingHUDValues(long fps) {
        return this.controller.getProfilingHUDValues(fps);
    }

    protected SpatialGridStatisticsRenderDTO getSpatialGridStatistics() {
        return this.controller.getSpatialGridStatistics();
    }

    protected RenderMetricsDTO getRenderMetrics() {
        return this.renderer.getRenderMetrics();
    }
    // endregion

    /**
     * Queries the model via controller for entities visible in the specified
     * region.
     * Fills the provided buffers with results.
     * 
     * @param minX               left edge of query region
     * @param maxX               right edge of query region
     * @param minY               top edge of query region
     * @param maxY               bottom edge of query region
     * @param scratchCellIndices buffer for spatial grid cell indices
     * @param scratchEntityIds   buffer to fill with visible entity IDs
     * @return list of entity IDs in region
     */
    public ArrayList<String> queryEntitiesInRegion(
            double minX, double maxX, double minY, double maxY,
            int[] scratchCellIndices, ArrayList<String> scratchEntityIds) {

        // Relay al controller (que tiene acceso al modelo)
        return this.controller.queryEntitiesInRegion(
                minX, maxX, minY, maxY,
                scratchCellIndices, scratchEntityIds);
    }

    // *** PRIVATE ***

    private void addRenderer(Container container) {
        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1F;
        c.weighty = 0;
        c.gridheight = 10;
        c.gridwidth = 8;
        container.add(this.renderer, c);
    }

    private void createFrame() {
        Container panel;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());

        panel = this.getContentPane();
        this.addRenderer(panel);

        this.setFocusable(true);
        this.addKeyListener(this);
        this.addWindowFocusListener(this);

        this.renderer.setFocusable(false); // El Renderer NO necesita foco
        this.renderer.setIgnoreRepaint(true); // Mejor performance

        this.pack();
        this.setVisible(true);

        SwingUtilities.invokeLater(() -> this.requestFocusInWindow());
    }

    private void resetAllKeyStates() {
        if (this.localPlayerId == null || this.controller == null) {
            return;
        }

        try {
            // Resetear TODOS los controles activos
            this.controller.playerThrustOff(this.localPlayerId);
            this.controller.playerRotateOff(this.localPlayerId);
            this.fireKeyDown.set(false);
        } catch (Exception ex) {
            throw new RuntimeException("Error resetting key states: " + ex.getMessage(), ex);
        }
    }

    /**
     * Sync input state each frame.
     * OS may consume keyboard events (Alt+Tab, Win+X, etc) without firing
     * keyReleased(),
     * causing tracking to become inconsistent. Called from Renderer each frame.
     */
    public void syncInputState() {
        if (this.localPlayerId == null || this.controller == null || this.pressedKeys.isEmpty()) {
            return;
        }

        // When window lacks focus, all keys should be released
        if (!this.wasWindowFocused) {
            if (!this.pressedKeys.isEmpty()) {
                System.out.println("View.syncInputState: Window not focused but keys tracked: "
                        + this.pressedKeys + " - clearing");

                Set<Integer> keysToRelease = new HashSet<>(this.pressedKeys);
                this.pressedKeys.clear();

                for (int keyCode : keysToRelease) {
                    try {
                        this.processKeyRelease(keyCode);
                    } catch (Exception ex) {
                        throw new RuntimeException("View: Key release failed during focus loss: " + keyCode, ex);
                    }
                }
            }
            return;
        }
    }

    // *** INTERFACE IMPLEMENTATIONS ***

    // region WindowFocusListener
    /**
     * Detectamos pérdida de foco para resetear estado de teclas.
     * Esto es crítico porque si el usuario presiona Alt+Tab,
     * el keyReleased() nunca se genera.
     */
    @Override
    public void windowLostFocus(WindowEvent e) {
        this.wasWindowFocused = false;

        // Clear pressed keys (won't receive keyReleased for them)
        Set<Integer> keysToRelease = new HashSet<>(this.pressedKeys);
        this.pressedKeys.clear();

        for (int keyCode : keysToRelease) {
            try {
                this.processKeyRelease(keyCode);
            } catch (Exception ex) {
                throw new RuntimeException("View: Key release failed on focus lost: " + keyCode, ex);
            }
        }

        System.out.println("View: Window lost focus - pressed keys cleared: " + keysToRelease);
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        this.wasWindowFocused = true;
        System.out.println("View: Window gained focus");
    }
    // endregion

    // region KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        try {
            if (this.localPlayerId == null || this.controller == null) {
                return;
            }

            int keyCode = e.getKeyCode();

            // Agregar a tracking si ya no estaba presionada
            if (!this.pressedKeys.contains(keyCode)) {
                this.pressedKeys.add(keyCode);

                // Process only first press (not OS key repeat)
                this.processKeyPress(keyCode);
            }
        } catch (Exception ex) {
            resetAllKeyStates();
            throw new RuntimeException("View: keyPressed event failed", ex);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            if (this.localPlayerId == null || this.controller == null) {
                return;
            }

            int keyCode = e.getKeyCode();

            this.pressedKeys.remove(keyCode);

            this.processKeyRelease(keyCode);
        } catch (Exception ex) {
            throw new RuntimeException("View: keyReleased event failed", ex);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Nothing to do
    }

    /**
     * Procesamiento de keyPress (se llama solo una vez cuando se presiona).
     * NO se llama en key repeat.
     */
    private void processKeyPress(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                this.controller.playerThrustOn(this.localPlayerId);
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_X:
                this.controller.playerReverseThrust(this.localPlayerId);
                break;

            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                this.controller.playerRotateLeftOn(this.localPlayerId);
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                this.controller.playerRotateRightOn(this.localPlayerId);
                break;

            case KeyEvent.VK_SPACE:
                if (!this.fireKeyDown.get()) {
                    this.fireKeyDown.set(true);
                    this.controller.playerFire(this.localPlayerId);
                }
                break;

            case KeyEvent.VK_1:
                this.controller.playerSelectNextWeapon(this.localPlayerId);
                break;
        }
    }

    /**
     * Procesamiento de keyRelease (se llama cuando se libera la tecla).
     * Puede no llamarse si el OS consume el evento.
     */
    private void processKeyRelease(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                this.controller.playerThrustOff(this.localPlayerId);
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_X:
                this.controller.playerThrustOff(this.localPlayerId);
                break;

            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                this.controller.playerRotateOff(this.localPlayerId);
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                this.controller.playerRotateOff(this.localPlayerId);
                break;

            case KeyEvent.VK_SPACE:
                this.fireKeyDown.set(false);
                break;
        }
    }
    // endregion

}
