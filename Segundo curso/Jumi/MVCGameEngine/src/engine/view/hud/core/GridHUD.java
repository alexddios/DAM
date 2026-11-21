package engine.view.hud.core;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.FontMetrics;
import java.awt.Graphics2D;


/* GridHUD
 * -------
 * Debug overlay renderer for a fixed-topology spatial grid.
 *
 * This class is UI-only: it does not know about the simulation model or any SpatialGrid internals.
 * The caller supplies per-frame camera/viewport plus (optionally) non-empty cell indices.
 *
 * Coordinate conventions:
 * - World coordinates are in pixels.
 * - (camWorldX, camWorldY) is the top-left world coordinate visible on screen.
 * - Viewport is [0..viewW) x [0..viewH) in screen pixels.
 * - Screen position = world position - camera position.
 */
public final class GridHUD {

    private final int cellSizePx;
    private final int cellsX;
    private final int cellsY;
    private final int worldWidthPx;
    private final int worldHeightPx;

    /**
     * Creates a GridHUD bound to a fixed grid topology.
     *
     * The HUD validates the grid properties once at construction time so per-frame
     * drawing methods can be smaller and safer.
     *
     * @param cellSizePx size of each grid cell in world pixels (must be > 0)
     * @param cellsX number of grid columns (must be > 0)
     * @param cellsY number of grid rows (must be > 0)
     */
    public GridHUD(int cellSizePx, int cellsX, int cellsY) {
        if (cellSizePx <= 0) throw new IllegalArgumentException("cellSizePx must be > 0");
        if (cellsX <= 0) throw new IllegalArgumentException("cellsX must be > 0");
        if (cellsY <= 0) throw new IllegalArgumentException("cellsY must be > 0");

        // Basic overflow guard (world sizes are derived from these multiplications)
        long w = (long) cellsX * (long) cellSizePx;
        long h = (long) cellsY * (long) cellSizePx;
        if (w > Integer.MAX_VALUE || h > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Grid world size overflows int: " + w + "x" + h);
        }

        this.cellSizePx = cellSizePx;
        this.cellsX = cellsX;
        this.cellsY = cellsY;
        this.worldWidthPx = (int) w;
        this.worldHeightPx = (int) h;
    }

    /** Draws the grid lines over the current viewport. */
    public void drawGridLines(
            Graphics2D g,
            int camWorldX,
            int camWorldY,
            int viewW,
            int viewH,
            float alpha) {

        if (g == null) return;
        if (viewW <= 0 || viewH <= 0) return;

        int worldLeft = camWorldX;
        int worldTop = camWorldY;
        int worldRight = camWorldX + viewW;
        int worldBottom = camWorldY + viewH;

        // Clamp to world bounds (avoid drawing outside the defined grid)
        if (worldLeft < 0) worldLeft = 0;
        if (worldTop < 0) worldTop = 0;
        if (worldRight > worldWidthPx) worldRight = worldWidthPx;
        if (worldBottom > worldHeightPx) worldBottom = worldHeightPx;

        if (worldLeft >= worldRight || worldTop >= worldBottom) return;

        final int startCx = worldLeft / cellSizePx;
        final int endCx = (worldRight + cellSizePx - 1) / cellSizePx;

        final int startCy = worldTop / cellSizePx;
        final int endCy = (worldBottom + cellSizePx - 1) / cellSizePx;

        final Composite prev = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, clamp01(alpha)));

        // Vertical lines
        for (int cx = startCx; cx <= endCx; cx++) {
            final int xWorld = cx * cellSizePx;
            final int xScreen = xWorld - camWorldX;
            g.drawLine(xScreen, worldTop - camWorldY, xScreen, worldBottom - camWorldY);
        }

        // Horizontal lines
        for (int cy = startCy; cy <= endCy; cy++) {
            final int yWorld = cy * cellSizePx;
            final int yScreen = yWorld - camWorldY;
            g.drawLine(worldLeft - camWorldX, yScreen, worldRight - camWorldX, yScreen);
        }

        g.setComposite(prev);
    }

    /**
     * Draws filled rectangles for the buckets that are non-empty.
     *
     * The caller provides a compact list of non-empty bucket indices (1D indices).
     * This avoids scanning all buckets in the HUD.
     *
     * If drawCounts is true, bucket sizes are drawn on top of each cell.
     */
    public void drawNonEmptyCells(
            Graphics2D g,
            int camWorldX,
            int camWorldY,
            int viewW,
            int viewH,
            int[] nonEmptyCellIdxs,
            int nonEmptyCount,
            IntGetter bucketSizeGetter,   // callback: bucket size by cell index
            boolean drawCounts,
            float fillAlpha,
            float textAlpha) {

        if (g == null) return;
        if (viewW <= 0 || viewH <= 0) return;
        if (nonEmptyCellIdxs == null || nonEmptyCount <= 0) return;
        if (nonEmptyCount > nonEmptyCellIdxs.length) throw new IllegalArgumentException("nonEmptyCount > array length");

        int worldLeft = camWorldX;
        int worldTop = camWorldY;
        int worldRight = camWorldX + viewW;
        int worldBottom = camWorldY + viewH;

        // Clamp to world bounds
        if (worldLeft < 0) worldLeft = 0;
        if (worldTop < 0) worldTop = 0;
        if (worldRight > worldWidthPx) worldRight = worldWidthPx;
        if (worldBottom > worldHeightPx) worldBottom = worldHeightPx;

        if (worldLeft >= worldRight || worldTop >= worldBottom) return;

        final Composite prev = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, clamp01(fillAlpha)));

        for (int i = 0; i < nonEmptyCount; i++) {
            final int cellIdx = nonEmptyCellIdxs[i];
            if (cellIdx < 0) continue; // ignore sentinels if you use them

            final int cx = cellIdx % cellsX;
            final int cy = cellIdx / cellsX;
            if (cy < 0 || cy >= cellsY) continue;

            final int xWorld = cx * cellSizePx;
            final int yWorld = cy * cellSizePx;

            // Viewport culling
            if (xWorld + cellSizePx < worldLeft || xWorld > worldRight) continue;
            if (yWorld + cellSizePx < worldTop || yWorld > worldBottom) continue;

            final int x = xWorld - camWorldX;
            final int y = yWorld - camWorldY;

            g.fillRect(x, y, cellSizePx, cellSizePx);
            g.drawRect(x, y, cellSizePx, cellSizePx);

            if (drawCounts && bucketSizeGetter != null) {
                final int k = bucketSizeGetter.get(cellIdx);

                // Draw counts more opaque for readability
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, clamp01(textAlpha)));

                final String s = String.valueOf(k);
                final FontMetrics fm = g.getFontMetrics();
                g.drawString(s, x + 2, y + fm.getAscent());

                // Restore fill alpha
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, clamp01(fillAlpha)));
            }
        }

        g.setComposite(prev);
    }

    /** Convenience: draws both grid lines and non-empty cells. */
    public void draw(
            Graphics2D g,
            int camWorldX,
            int camWorldY,
            int viewW,
            int viewH,
            float gridLinesAlpha,
            int[] nonEmptyCellIdxs,
            int nonEmptyCount,
            IntGetter bucketSizeGetter,
            boolean drawCounts,
            float fillAlpha,
            float textAlpha) {

        drawGridLines(g, camWorldX, camWorldY, viewW, viewH, gridLinesAlpha);

        drawNonEmptyCells(
                g, camWorldX, camWorldY, viewW, viewH,
                nonEmptyCellIdxs, nonEmptyCount,
                bucketSizeGetter,
                drawCounts,
                fillAlpha, textAlpha
        );
    }

    /** Minimal functional interface to avoid boxing. */
    @FunctionalInterface
    public interface IntGetter {
        int get(int cellIdx);
    }

    private static float clamp01(float a) {
        if (a < 0f) return 0f;
        if (a > 1f) return 1f;
        return a;
    }
}