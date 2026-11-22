package engine.view.core;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import engine.controller.mappers.DynamicRenderableMapper;
import engine.controller.ports.EngineState;
import engine.utils.images.ImageCache;
import engine.utils.images.Images;
import engine.utils.profiling.impl.RendererProfiler;
import engine.utils.helpers.DoubleVector;
import engine.view.hud.impl.InstrumentationHUD;
import engine.view.hud.impl.PlayerHUD;
import engine.view.hud.impl.SpatialGridHUD;
import engine.view.hud.impl.SystemHUD;
import engine.view.renderables.impl.DynamicRenderable;
import engine.view.renderables.impl.Renderable;
import engine.utils.pooling.PoolMDTO;
import engine.view.renderables.ports.DynamicRenderDTO;
import engine.view.renderables.ports.PlayerRenderDTO;
import engine.view.renderables.ports.RenderDTO;
import engine.view.renderables.ports.RenderMetricsDTO;
import engine.view.renderables.ports.SpatialGridStatisticsRenderDTO;

import java.awt.Toolkit;

/**
 * Renderer
 * --------
 *
 * Active rendering loop responsible for drawing the current frame to the
 * screen. This class owns the rendering thread and performs all drawing using
 * a BufferStrategy-based back buffer.
 *
 * Architectural role
 * ------------------
 * The Renderer is a pull-based consumer of visual snapshots provided by the
 * View.
 * It never queries or mutates the model directly.
 *
 * Rendering is decoupled from simulation through immutable snapshot DTOs
 * (EntityInfoDTO / DBodyInfoDTO), ensuring that rendering remains deterministic
 * and free of model-side race conditions.
 *
 * Threading model
 * ---------------
 * - A dedicated render thread drives the render loop (Runnable).
 * - Rendering is active only while the engine state is ALIVE.
 * - The loop terminates cleanly when the engine reaches STOPPED.
 *
 * Data access patterns
 * --------------------
 * Three different renderable collections are used, each with a consciously
 * chosen
 * concurrency strategy based on update frequency and thread ownership:
 *
 * 1) Dynamic bodies (DBodies)
 * - Stored in a plain HashMap.
 * - Updated and rendered exclusively by the render thread.
 * - No concurrent access → no synchronization required.
 *
 * 2) Static bodies (SBodies)
 * - Rarely updated, potentially from non-render threads
 * (model → controller → view).
 * - Stored using a copy-on-write strategy:
 * * Updates create a new Map instance.
 * * The reference is swapped atomically via a volatile field.
 * - The render thread only reads stable snapshots.
 *
 * 3) Decorators
 * - Same access pattern as static bodies.
 * - Uses the same copy-on-write + atomic swap strategy.
 *
 * This design avoids locks, minimizes contention, and guarantees that the
 * render thread always iterates over a fully consistent snapshot.
 *
 * Frame tracking
 * --------------
 * A monotonically increasing frame counter (currentFrame) is used to:
 * - Track renderable liveness.
 * - Remove obsolete renderables deterministically.
 *
 * Each update method captures a local frame snapshot to ensure internal
 * consistency, even if the global frame counter advances later.
 *
 * Rendering pipeline
 * ------------------
 * Per frame:
 * 1) Background is rendered to a VolatileImage for fast blitting.
 * 2) Decorators are drawn.
 * 3) Static bodies are drawn.
 * 4) Dynamic bodies are updated and drawn.
 * 5) HUD elements (FPS) are rendered last.
 *
 * Alpha compositing is used to separate opaque background rendering from
 * transparent entities.
 *
 * Performance considerations
 * --------------------------
 * - Triple buffering via BufferStrategy.
 * - VolatileImage used for background caching.
 * - Target frame rate ~60 FPS (16 ms delay).
 * - FPS is measured using a rolling one-second window.
 *
 * Design goals
 * ------------
 * - Deterministic rendering.
 * - Zero blocking in the render loop.
 * - Clear ownership of mutable state.
 * - Explicit, documented concurrency decisions.
 *
 * This class is intended to behave as a low-level rendering component suitable
 * for a small game engine rather than a UI-centric Swing renderer.
 */
public class Renderer extends Canvas implements Runnable {

    // region Constants
    private static final int REFRESH_DELAY_IN_MILLIS = 1; //
    private static final long MONITORING_PERIOD_NS = 750_000_000L;
    // endregion

    // region Fields
    private DoubleVector viewDimension;
    private View view;
    private int delayInMillis = 5;
    private long currentFrame = 0;
    private Thread thread;

    private BufferedImage background;
    private Images images;
    private ImageCache imagesCache;
    private VolatileImage viBackground;
    private final PlayerHUD playerHUD = new PlayerHUD();
    private final SystemHUD systemHUD = new SystemHUD();
    private final SpatialGridHUD spatialGridHUD = new SpatialGridHUD();
    private final InstrumentationHUD instrumentationHUD = new InstrumentationHUD();
    private final RendererProfiler rendererProfiler = new RendererProfiler(MONITORING_PERIOD_NS);

    private double cameraX = 0.0d;
    private double cameraY = 0.0d;
    private double maxCameraClampY;
    private double maxCameraClampX;
    private double backgroundScrollSpeedX = 0.4;
    private double backgroundScrollSpeedY = 0.4;

    private final ArrayList<String> visibleEntityIds = new ArrayList<>(1600);
    private final int[] scratchIdxBuffer = new int[1600];

    private final Map<String, DynamicRenderable> dynamicRenderables = new ConcurrentHashMap<>(2500);
    private PoolMDTO<DynamicRenderDTO> dynamicRenderDtoPool;
    private DynamicRenderableMapper dynamicRenderMapper;
    private volatile Map<String, Renderable> staticRenderables = new ConcurrentHashMap<>(100);
    // endregion

    // region Constructors
    public Renderer(View view) {
        this.view = view;

        this.setIgnoreRepaint(true);
        this.setCameraClampLimits();
    }
    // endregion

    // *** PUBLICS ***

    public boolean activate() {
        // Be sure all is ready to begin render!
        if (this.viewDimension == null) {
            throw new IllegalArgumentException("View dimensions not setted");
        }

        if ((this.viewDimension.x <= 0) || (this.viewDimension.y <= 0)) {
            throw new IllegalArgumentException("Canvas size error: ("
                    + this.viewDimension.x + "," + this.viewDimension.y + ")");
        }

        // BufferStrategy fails silently when canvas > screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (this.viewDimension.x > screenSize.width || this.viewDimension.y > screenSize.height) {
            throw new IllegalStateException(
                    "Renderer: Canvas size (" + (int) this.viewDimension.x + "x" + (int) this.viewDimension.y + ") "
                            + "exceeds screen size (" + screenSize.width + "x" + screenSize.height + "). "
                            + "Reduce viewDimension in Main.java or disable UI scaling (sun.java2d.uiScale).");
        }

        while (!this.isDisplayable()) {
            try {
                Thread.sleep(this.delayInMillis);
            } catch (InterruptedException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        this.setPreferredSize(
                new Dimension((int) this.viewDimension.x, (int) this.viewDimension.y));

        // Initialize DTO pooling
        this.dynamicRenderDtoPool = new PoolMDTO<>(
                () -> new DynamicRenderDTO(null, 0, 0, 0, 0, 0L, 0, 0, 0, 0, 0L));
        this.dynamicRenderMapper = new DynamicRenderableMapper(this.dynamicRenderDtoPool);

        this.thread = new Thread(this);
        this.thread.setName("Renderer");
        this.thread.setPriority(Thread.NORM_PRIORITY + 2);
        this.thread.start();

        System.out.println("Renderer: Activated");
        return true;
    }

    // region adders (add***)
    public void addStaticRenderable(String entityId, String assetId) {
        Renderable renderable = new Renderable(entityId, assetId, this.imagesCache, this.currentFrame);
        this.staticRenderables.put(entityId, renderable);
    }

    public void addDynamicRenderable(String entityId, String assetId) {
        DynamicRenderable renderable = new DynamicRenderable(entityId, assetId, this.imagesCache, this.currentFrame);
        this.dynamicRenderables.put(entityId, renderable);
    }
    // endregion

    // region getters (get***)
    public Renderable getLocalPlayerRenderable() {
        String localPlayerId = this.view.getLocalPlayerId();

        if (localPlayerId == null || localPlayerId.isEmpty()) {
            return null; // ======= No player to follow =======>>
        }
        Renderable renderableLocalPlayer = this.dynamicRenderables.get(this.view.getLocalPlayerId());
        return renderableLocalPlayer;
    }

    /**
     * Get render metrics for HUD display
     */
    public RenderMetricsDTO getRenderMetrics() {
        return new RenderMetricsDTO(
                this.rendererProfiler.getAvgDrawBackgroundMs(),
                this.rendererProfiler.getAvgDrawStaticMs(),
                this.rendererProfiler.getAvgDrawDynamicMs(),
                this.rendererProfiler.getAvgQueryDynamicMs(),
                this.rendererProfiler.getAvgPaintDynamicMs(),
                this.rendererProfiler.getAvgDrawHudsMs(),
                this.rendererProfiler.getAvgDrawMs(),
                this.rendererProfiler.getAvgUpdateMs(),
                this.rendererProfiler.getAvgFrameMs());
    }
    // endregion

    // region notifiers (notify***)
    public void notifyDynamicIsDead(String entityId) {
        this.dynamicRenderables.remove(entityId);
    }
    // endregion

    // region setters (set***)
    public void setImages(BufferedImage background, Images images) {
        this.background = background;
        this.viBackground = null;

        this.images = images;
        this.imagesCache = new ImageCache(this.getGraphicsConfSafe(), this.images);
    }

    public void setViewDimension(DoubleVector viewDim) {
        this.viewDimension = viewDim;
        this.setCameraClampLimits();
        this.setPreferredSize(new Dimension((int) this.viewDimension.x, (int) this.viewDimension.y));
    }

    // endregion

    public void updateStaticRenderables(ArrayList<RenderDTO> renderablesData) {
        if (renderablesData == null) {
            return; // ========= Nothing to render by the moment ... =========>>
        }

        Map<String, Renderable> newRenderables = new java.util.concurrent.ConcurrentHashMap<>(this.staticRenderables);

        if (renderablesData.isEmpty()) {
            newRenderables.clear(); //
            this.staticRenderables = newRenderables;
            return;
        }

        // Update a renderable associated with each DBodyRenderInfoDTO
        long cFrame = this.currentFrame;
        for (RenderDTO renderableData : renderablesData) {
            String entityId = renderableData.entityId;
            if (entityId == null || entityId.isEmpty()) {
                continue;
            }

            Renderable renderable = newRenderables.get(entityId);
            if (renderable == null) {
                throw new IllegalStateException("Renderer: Static renderable not found: " + entityId);
            }
            renderable.update(renderableData, cFrame);
        }

        newRenderables.entrySet().removeIf(e -> e.getValue().getLastFrameSeen() != cFrame);
        this.staticRenderables = newRenderables; // atomic swap
    }

    // *** PRIVATES ***

    // region drawers (draw***)
    private void drawDynamicRenderable(Graphics2D g) {
        // Measure spatial query
        long queryStart = this.rendererProfiler.startInterval();
        ArrayList<String> visibleIds = this.view.queryEntitiesInRegion(
                this.cameraX, this.cameraX + this.viewDimension.x,
                this.cameraY, this.cameraY + this.viewDimension.y,
                this.scratchIdxBuffer,
                this.visibleEntityIds);
        this.rendererProfiler.stopInterval(RendererProfiler.METRIC_QUERY_DYNAMIC, queryStart);

        // Measure paint loop
        long paintStart = this.rendererProfiler.startInterval();
        for (String entityId : visibleIds) {
            DynamicRenderable renderable = this.dynamicRenderables.get(entityId);
            if (renderable != null) {
                renderable.paint(g, this.currentFrame);
            }
        }
        this.rendererProfiler.stopInterval(RendererProfiler.METRIC_PAINT_DYNAMIC, paintStart);
    }

    private void drawHUDs(Graphics2D g) {

        long fps = this.rendererProfiler.getLastFps();
        double avgDrawMs = this.rendererProfiler.getAvgDrawMs();

        this.systemHUD.draw(g,
                fps,
                String.format("%.0f", avgDrawMs) + " ms",
                this.imagesCache == null ? 0 : this.imagesCache.size(),
                String.format("%.0f", this.imagesCache == null ? 0 : this.imagesCache.getHitsPercentage()) + "%",
                this.view.getEntityAliveQuantity(),
                this.view.getEntityDeadQuantity(),
                this.currentFrame);

        // this.instrumentationHUD.draw(g, this.getRenderMetrics().toObjectArray());

        PlayerRenderDTO playerData = this.view.getLocalPlayerRenderData();
        if (playerData != null) {
            this.playerHUD.draw(g, playerData.toObjectArray());
        }

        SpatialGridStatisticsRenderDTO spatialGridStats = this.view.getSpatialGridStatistics();
        if (spatialGridStats != null) {
            this.spatialGridHUD.draw(g, spatialGridStats.toObjectArray());
        }
    }

    private void drawStaticRenderables(Graphics2D g) {
        Map<String, Renderable> renderables = this.staticRenderables;

        for (Renderable renderable : renderables.values()) {
            if (this.isVisible(renderable)) {
                renderable.paint(g, this.currentFrame);
            }
        }
    }

    private boolean isVisible(Renderable renderable) {
        RenderDTO renderData = renderable.getRenderData();
        if (renderData == null) {
            return false;
        }

        double viewW = this.viewDimension.x;
        double viewH = this.viewDimension.y;

        double camLeft = this.cameraX;
        double camTop = this.cameraY;
        double camRight = camLeft + viewW;
        double camBottom = camTop + viewH;

        double half = renderData.size * 0.5d;
        if (renderable.getImage() != null) {
            double halfW = renderable.getImage().getWidth(null) * 0.5d;
            double halfH = renderable.getImage().getHeight(null) * 0.5d;
            half = Math.max(halfW, halfH);
        }

        double minX = renderData.posX - half;
        double maxX = renderData.posX + half;
        double minY = renderData.posY - half;
        double maxY = renderData.posY + half;

        if (maxX < camLeft || minX > camRight) {
            return false; // ==== Out of horizontal bounds ======>>
        }

        if (maxY < camTop || minY > camBottom) {
            return false; // ==== Out of vertical bounds ======>>
        }

        return true;
    }

    private void drawScene(BufferStrategy bs) {
        Graphics2D gg;

        do {
            gg = (Graphics2D) bs.getDrawGraphics();
            try {
                // 1) BACKGROUND
                long bgStart = this.rendererProfiler.startInterval();
                gg.setComposite(AlphaComposite.Src); // Opaque
                this.drawTiledBackground(gg);
                gg.drawImage(this.viBackground, 0, 0, null);
                this.rendererProfiler.stopInterval(RendererProfiler.METRIC_DRAW_BACKGROUND, bgStart);

                // 2) WORLD (translated due camera)
                gg.setComposite(AlphaComposite.SrcOver); // With transparency
                AffineTransform defaultTransform = gg.getTransform();
                gg.translate(-this.cameraX, -this.cameraY);

                // Draw static renderables
                long staticStart = this.rendererProfiler.startInterval();
                this.drawStaticRenderables(gg);
                this.rendererProfiler.stopInterval(RendererProfiler.METRIC_DRAW_STATIC, staticStart);

                // Draw dynamic renderables
                long dynamicStart = this.rendererProfiler.startInterval();
                this.drawDynamicRenderable(gg);
                this.rendererProfiler.stopInterval(RendererProfiler.METRIC_DRAW_DYNAMIC, dynamicStart);

                gg.setTransform(defaultTransform);

                // 3) HUD (on top of everything)
                long hudsStart = this.rendererProfiler.startInterval();
                gg.setComposite(AlphaComposite.SrcOver); // With transparency
                this.drawHUDs(gg);
                this.rendererProfiler.stopInterval(RendererProfiler.METRIC_DRAW_HUDS, hudsStart);

            } finally {
                gg.dispose();
            }

            bs.show();
            Toolkit.getDefaultToolkit().sync();
        } while (bs.contentsLost());
    }

    private void drawTiledBackground(Graphics2D g) {
        if (this.background == null || this.viewDimension == null)
            return;

        final int viewW = (int) this.viewDimension.x;
        final int viewH = (int) this.viewDimension.y;
        if (viewW <= 0 || viewH <= 0)
            return;

        final int tileW = this.background.getWidth(null);
        final int tileH = this.background.getHeight(null);
        if (tileW <= 0 || tileH <= 0)
            return;

        final double scrollX = this.cameraX * this.backgroundScrollSpeedX;
        final double scrollY = this.cameraY * this.backgroundScrollSpeedY;

        // Tile offset in [-(tile-1)..0], stable with negatives
        final int offX = -Math.floorMod((int) Math.floor(scrollX), tileW);
        final int offY = -Math.floorMod((int) Math.floor(scrollY), tileH);

        // Start 1 tile before to ensure full coverage
        final int startX = offX - tileW;
        final int startY = offY - tileH;
        for (int x = startX; x < viewW + tileW; x += tileW) {
            for (int y = startY; y < viewH + tileH; y += tileH) {
                g.drawImage(this.background, x, y, null);
            }
        }
    }
    // endregion

    // region getters (get***)
    private GraphicsConfiguration getGraphicsConfSafe() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        if (gc == null) {
            gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();
        }

        return gc;
    }

    private VolatileImage getVIBackground() {
        this.viBackground = this.getVolatileImage(
                this.viBackground,
                this.background,
                new Dimension((int) this.viewDimension.x, (int) this.viewDimension.y));

        return this.viBackground;

    }

    private VolatileImage getVolatileImage(
            VolatileImage vi, BufferedImage src, Dimension dim) {

        GraphicsConfiguration gc = this.getGraphicsConfSafe();

        if (vi == null || vi.getWidth() != dim.width || vi.getHeight() != dim.height
                || vi.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
            // New volatile image
            vi = gc.createCompatibleVolatileImage(dim.width, dim.height, Transparency.OPAQUE);
        }

        int val;
        do {
            val = vi.validate(gc);
            if (val != VolatileImage.IMAGE_OK || vi.contentsLost()) {
                Graphics2D g = vi.createGraphics();
                g.drawImage(src, 0, 0, dim.width, dim.height, null);
                g.dispose();
            }
        } while (vi.contentsLost());

        return vi;
    }
    // endregion

    // region setters (set***)
    private void setCameraClampLimits() {
        DoubleVector woldDim = this.view.getWorldDimension();

        if (woldDim == null || this.viewDimension == null) {
            this.maxCameraClampX = 0.0;
            this.maxCameraClampY = 0.0;
            return; // ======= No world or view dimensions info ======= >>
        }

        this.maxCameraClampX = Math.max(0.0, woldDim.x - this.viewDimension.x);
        this.maxCameraClampY = Math.max(0.0, woldDim.y - this.viewDimension.y);
    }
    // endregion

    // region updaters (update***)
    private void updateCamera() {
        Renderable localPlayerRenderable = this.getLocalPlayerRenderable();
        DoubleVector worldDim = this.view.getWorldDimension();

        if (localPlayerRenderable == null || this.viewDimension == null || worldDim == null) {
            return; // ======== No player or data to follow =======>>
        }

        RenderDTO playerData = localPlayerRenderable.getRenderData();

        double playerX = playerData.posX - this.cameraX;
        double playerY = playerData.posY - this.cameraY;

        double desiredX;
        double desiredY;

        double minX = this.viewDimension.x * 0.3;
        double maxX = this.viewDimension.x * 0.7;
        double minY = this.viewDimension.y * 0.3;
        double maxY = this.viewDimension.y * 0.7;

        if (playerX < minX) {
            desiredX = playerData.posX - minX;
        } else if (playerX > maxX) {
            desiredX = playerData.posX - maxX;
        } else {
            desiredX = playerData.posX - (playerX);
        }

        if (playerY < minY) {
            desiredY = playerData.posY - minY;
        } else if (playerY > maxY) {
            desiredY = playerData.posY - maxY;
        } else {
            desiredY = playerData.posY - (playerY);
        }

        // double desiredX = playerData.posX - (this.viewDimension.x / 2.0d);
        // double desiredY = playerData.posY - (this.viewDimension.y / 2.0d);

        this.cameraX += (desiredX - this.cameraX);
        this.cameraY += (desiredY - this.cameraY);

        // // Clamp when camera goes out of world limits
        this.cameraX = clamp(cameraX, 0.0, this.maxCameraClampX);
        this.cameraY = clamp(cameraY, 0.0, this.maxCameraClampY);
    }

    private void updateDynamicRenderables(ArrayList<DynamicRenderDTO> renderablesData) {
        if (renderablesData == null || renderablesData.isEmpty()) {
            // If no objects are alive this frame, clear the snapshot entirely
            for (DynamicRenderable renderable : this.dynamicRenderables.values()) {
                DynamicRenderDTO dto = (DynamicRenderDTO) renderable.getRenderData();
                if (dto != null) {
                    this.dynamicRenderDtoPool.release(dto);
                }
            }
            this.dynamicRenderables.clear();
            return; // ========= Nothing to render by the moment ... =========>>
        }

        // Update or create a renderable associated with each DBodyRenderInfoDTO
        long cFrame = this.currentFrame;
        for (DynamicRenderDTO renderableData : renderablesData) {
            String entityId = renderableData.entityId;
            if (entityId == null || entityId.isEmpty()) {
                this.dynamicRenderDtoPool.release(renderableData);
                continue;
            }

            DynamicRenderable renderable = this.dynamicRenderables.get(entityId);
            if (renderable != null) {
                // Existing renderable → update its snapshot and sprite if needed
                DynamicRenderDTO current = (DynamicRenderDTO) renderable.getRenderData();
                if (current == null) {
                    DynamicRenderDTO pooled = this.dynamicRenderDtoPool.acquire();
                    pooled.updateFrom(renderableData);
                    this.dynamicRenderDtoPool.release(renderableData);
                    renderable.update(pooled, cFrame);
                } else {
                    renderable.update(renderableData, cFrame);
                    this.dynamicRenderDtoPool.release(renderableData);
                }
            } else {
                this.dynamicRenderDtoPool.release(renderableData);
            }
        }

        // Remove renderables not updated this frame (i.e., objects no longer alive)
        this.dynamicRenderables.entrySet().removeIf(entry -> {
            DynamicRenderable renderable = entry.getValue();
            if (renderable.getLastFrameSeen() == cFrame) {
                return false;
            }

            DynamicRenderDTO dto = (DynamicRenderDTO) renderable.getRenderData();
            if (dto != null) {
                this.dynamicRenderDtoPool.release(dto);
            }

            return true;
        });
    }
    // endregion

    private static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    private void renderFrame(Graphics2D g) {
        CanvasMetrics metrics = this.getCanvasMetrics();
        ViewportTransform transform = this.calculateViewportTransform(metrics);

        this.clearCanvas(g, metrics);

        Graphics2D worldG = (Graphics2D) g.create();
        try {
            this.applyWorldTransform(worldG, transform);
            this.drawWorld(worldG);
        } finally {
            worldG.dispose();
        }
    }

    private void applyWorldTransform(Graphics2D g, ViewportTransform transform) {
        g.translate(transform.offsetX, transform.offsetY);
        g.scale(transform.scale, transform.scale);
    }

    private void clearCanvas(Graphics2D g, CanvasMetrics metrics) {
        g.setComposite(AlphaComposite.Src); // Opaque
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, metrics.width, metrics.height);
    }

    private ViewportTransform calculateViewportTransform(CanvasMetrics metrics) {
        double scaleX = metrics.width / this.viewDimension.x;
        double scaleY = metrics.height / this.viewDimension.y;
        double scale = Math.min(scaleX, scaleY);

        double scaledWidth = this.viewDimension.x * scale;
        double scaledHeight = this.viewDimension.y * scale;
        double offsetX = (metrics.width - scaledWidth) * 0.5d;
        double offsetY = (metrics.height - scaledHeight) * 0.5d;

        return new ViewportTransform(scale, offsetX, offsetY);
    }

    private CanvasMetrics getCanvasMetrics() {
        int canvasWidth = this.getWidth();
        int canvasHeight = this.getHeight();
        if (canvasWidth <= 0 || canvasHeight <= 0) {
            canvasWidth = (int) this.viewDimension.x;
            canvasHeight = (int) this.viewDimension.y;
        }

        return new CanvasMetrics(canvasWidth, canvasHeight);
    }

    private void drawWorld(Graphics2D g) {
        g.setComposite(AlphaComposite.Src); // Opaque
        g.drawImage(this.getVIBackground(), 0, 0, null);

        g.setComposite(AlphaComposite.SrcOver); // With transparency
        this.drawStaticRenderables(g);
        this.drawDynamicRenderable(g);
        this.drawHUDs(g);
    }

    private static final class CanvasMetrics {
        private final int width;
        private final int height;

        private CanvasMetrics(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    private static final class ViewportTransform {
        private final double scale;
        private final double offsetX;
        private final double offsetY;

        private ViewportTransform(double scale, double offsetX, double offsetY) {
            this.scale = scale;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
        }
    }

    // *** INTERFACE IMPLEMENTATIONS ***

    // region Runnable
    @Override
    public void run() {
        this.createBufferStrategy(3);
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            throw new IllegalStateException(
                    "Renderer: BufferStrategy creation failed (canvas too large): "
                            + (int) this.viewDimension.x + "x" + (int) this.viewDimension.y);
        }

        while (true) {
            EngineState engineState = this.view.getEngineState();
            if (engineState == EngineState.STOPPED) {
                break; // ======= Engine stopped, exit render loop =======>>
            }

            long frameIntervalStart = 0L;
            if (engineState == EngineState.ALIVE) { // TO-DO Pause condition
                this.currentFrame++;
                frameIntervalStart = this.rendererProfiler.startInterval();
                this.rendererProfiler.addFrame();

                // 1) Recover snapshot of dynamic renderables data
                long updateStart = this.rendererProfiler.startInterval();
                ArrayList<DynamicRenderDTO> renderData = this.view.snapshotRenderData(this.dynamicRenderMapper);
                this.updateDynamicRenderables(renderData);
                this.updateCamera();
                this.rendererProfiler.stopInterval(RendererProfiler.METRIC_UPDATE, updateStart);

                // 2) Draw the scene with the current snapshot
                long drawStart = this.rendererProfiler.startInterval();
                this.drawScene(bs);
                this.rendererProfiler.stopInterval(RendererProfiler.METRIC_DRAW, drawStart);

                this.view.syncInputState(); // To prevent key events
            }

            try {
                Thread.sleep(REFRESH_DELAY_IN_MILLIS);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

            if (engineState == EngineState.ALIVE) {
                this.rendererProfiler.stopInterval(RendererProfiler.METRIC_FRAME, frameIntervalStart);
            }
        }
    }
    // endregion
}
