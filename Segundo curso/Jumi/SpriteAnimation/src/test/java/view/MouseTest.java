package view;

import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import check.Check;
import contract.SpriteView;
import model.Model;
import presenter.Presenter;

/**
 * Prueba con el ratón de verdad: abre la ventana real y mueve el ratón con java.awt.Robot.
 * Gira la rueda sobre cada cifra del HUD y sobre cada sprite, arrastra cada sprite, crea patos con Ctrl+clic
 * y comprueba cómo se cierra.
 * Durante la prueba no toques el ratón. Con -Dcaptura=carpeta guarda una captura de la ventana.
 */
public class MouseTest {

    private static Robot robot;
    private static Presenter presenter;   // lo que la vista ve de los sprites se comprueba con él, como ella
    private static View view;
    private static Viewer viewer;
    private static boolean disturbed;   // alguien ha movido el ratón durante la prueba

    public static void main(String[] args) throws Exception {
        System.out.println("MouseTest (ventana real y Robot): no toques el ratón hasta que termine");
        // El mismo montaje que SpriteAnimation.main, guardando la vista y el presentador para comprobarlos.
        // El modelo solo se nombra aquí, para montar: lo que hay en pantalla se mira con presenter.spriteViews().
        Model model = new Model();
        model.addSprite("Pato", "/sprites/pato.png", 16, 16, 4, 8, 120, 220);
        model.addSprite("Explosión", "/sprites/explosion.png", 48, 48, 3, 12, 360, 160);
        view = new View();
        view.setCanvasSize(640, 400);
        view.setRenderFps(60);
        presenter = new Presenter(model, view);
        presenter.setFpsLimits(1, 120);
        presenter.setScaleLimits(1, 8);
        presenter.setMaxSprites(12);
        presenter.start();

        if (System.getProperty("depurar") != null) printMouseEvents();
        robot = new Robot();
        robot.setAutoDelay(20);
        viewer = findViewer();
        bringToFront();
        robot.delay(500);
        capture("captura-inicio.png");

        wheelOverEachHudLine();
        wheelOverEachSprite();
        dragEachSprite();
        ctrlClickCreatesADuck();
        capture("captura-final.png");
        stillResponds();
        closesWithTheX();
        if (disturbed) {
            System.out.println("  AVISO: el ratón se ha movido durante la prueba; si algo ha fallado, repítela sin tocarlo.");
        }
        Check.finish("MouseTest");
        System.exit(0);   // cierra la ventana y los hilos, como la X
    }

    // ---------------------------------------------------------------- rueda

    static void wheelOverEachHudLine() {
        int lines = 1 + presenter.spriteCount();
        for (int line = 0; line < lines; line++) {
            int x = Hud.X + 60;
            int y = Hud.Y + line * Hud.LINE - Hud.LINE / 2;   // en medio de la línea
            // Hasta tres intentos: si alguien mueve el ratón, la rueda puede caer en otra línea.
            for (int attempt = 1; attempt <= 3; attempt++) {
                boolean disturbedBefore = disturbed;
                disturbed = false;
                int before = speed(line);
                moveTo(x, y);
                robot.mouseWheel(-3);   // tres muescas hacia arriba: sube 3
                robot.delay(300);
                checkPointerAt(x, y);
                int up = speed(line);
                moveTo(x, y);
                robot.mouseWheel(+1);   // una hacia abajo: baja 1
                robot.delay(300);
                checkPointerAt(x, y);
                int down = speed(line);
                boolean retry = disturbed && attempt < 3;
                disturbed |= disturbedBefore;
                if (retry) continue;
                Check.that("rueda sobre la línea " + line + " del HUD: " + before + " → " + up + " → " + down,
                           up == before + 3 && down == before + 2);
                break;
            }
        }
        // Fuera del HUD la rueda no hace nada.
        int render = view.renderFps();
        moveTo(viewer.getWidth() - 40, viewer.getHeight() - 40);
        robot.mouseWheel(-3);
        robot.delay(300);
        Check.equal("la rueda fuera del HUD y de los sprites no cambia nada", render, view.renderFps());
    }

    /** Sobre un sprite, la rueda lo amplía o lo encoge sin mover su centro. Al final lo deja como estaba. */
    static void wheelOverEachSprite() {
        for (int index = 0; index < presenter.spriteCount(); index++) {
            // Hasta tres intentos, como con el HUD.
            for (int attempt = 1; attempt <= 3; attempt++) {
                boolean disturbedBefore = disturbed;
                disturbed = false;
                SpriteView before = spriteView(index);
                int width = before.image().getWidth();
                int centerX = before.x() + width / 2;
                int centerY = before.y() + before.image().getHeight() / 2;
                moveTo(centerX, centerY);
                robot.mouseWheel(-1);   // una muesca hacia arriba: una escala más
                robot.delay(300);
                checkPointerAt(centerX, centerY);
                SpriteView up = spriteView(index);
                moveTo(centerX, centerY);
                robot.mouseWheel(+1);   // y otra hacia abajo: vuelve
                robot.delay(300);
                checkPointerAt(centerX, centerY);
                SpriteView down = spriteView(index);
                boolean retry = disturbed && attempt < 3;
                disturbed |= disturbedBefore;
                if (retry) continue;
                int grownWidth = up.image().getWidth();
                Check.that("rueda sobre el sprite " + index + ": " + width + " → " + grownWidth + " → "
                           + down.image().getWidth() + " px",
                           grownWidth > width && down.image().getWidth() == width);
                Check.equal("y crece desde su centro", centerX, up.x() + grownWidth / 2);
                break;
            }
        }
    }

    /** Línea 0: velocidad de pintado; 1, 2…: velocidad de cada sprite. */
    static int speed(int line) {
        if (line == 0) {
            return view.renderFps();
        }
        return spriteView(line - 1).fps();
    }

    // ---------------------------------------------------------------- arrastre

    static void dragEachSprite() {
        drag(0, 60, 40);     // el pato, a la derecha y abajo
        drag(1, -80, 30);    // la explosión, a la izquierda y abajo
    }

    static void drag(int index, int dx, int dy) {
        // Hasta tres intentos: si alguien mueve el ratón, el arrastre no acaba donde debe.
        for (int attempt = 1; attempt <= 3; attempt++) {
            boolean disturbedBefore = disturbed;
            disturbed = false;
            SpriteView before = spriteView(index);
            int x0 = before.x();
            int y0 = before.y();
            int grabX = x0 + before.image().getWidth() / 2;    // en el centro del sprite
            int grabY = y0 + before.image().getHeight() / 2;
            moveTo(grabX, grabY);
            robot.delay(100);
            checkPointerAt(grabX, grabY);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            int steps = 10;
            for (int i = 1; i <= steps; i++) {
                moveTo(grabX + dx * i / steps, grabY + dy * i / steps);
            }
            // Con el sprite cogido, su animación sigue: el fotograma cambia.
            boolean animates = changesFrame(index, 400);
            checkPointerAt(grabX + dx, grabY + dy);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(200);
            boolean retry = disturbed && attempt < 3;
            disturbed |= disturbedBefore;
            if (retry) continue;
            SpriteView after = spriteView(index);
            String name = after.name();
            Check.that("arrastre de " + name + ": (" + x0 + ", " + y0 + ") → (" + after.x() + ", " + after.y() + ")",
                       after.x() == x0 + dx && after.y() == y0 + dy);
            Check.that(name + " sigue animándose mientras se arrastra", animates);
            break;
        }
    }

    // ---------------------------------------------------------------- Ctrl+clic

    /** Ctrl+clic en una zona vacía crea un pato con su centro en el punto, ya animándose. Encima de un sprite, también, sin arrastrarlo. */
    static void ctrlClickCreatesADuck() {
        int count = presenter.spriteCount();
        int x = 520;
        int y = 320;
        ctrlClick(x, y);
        Check.equal("Ctrl+clic crea un sprite", count + 1, presenter.spriteCount());
        SpriteView duck = spriteView(count);
        Check.that("es un pato centrado donde se ha pulsado",
                   duck.name().equals("Pato") && duck.x() + duck.image().getWidth() / 2 == x
                   && duck.y() + duck.image().getHeight() / 2 == y);
        Check.that("y ya se anima", changesFrame(count, 400));

        SpriteView below = spriteView(0);
        ctrlClick(below.x() + 5, below.y() + 5);
        SpriteView after = spriteView(0);
        Check.that("Ctrl+clic encima de un sprite también crea un pato y no lo mueve",
                   presenter.spriteCount() == count + 2 && after.x() == below.x() && after.y() == below.y());
    }

    private static void ctrlClick(int x, int y) {
        moveTo(x, y);
        robot.delay(100);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(200);
    }

    // ---------------------------------------------------------------- sigue respondiendo

    static void stillResponds() throws Exception {
        long start = System.nanoTime();
        SwingUtilities.invokeAndWait(MouseTest::doNothing);   // vuelve en cuanto el hilo de eventos lo hace
        long ms = (System.nanoTime() - start) / 1_000_000;
        Check.that("el hilo de eventos de Swing responde (" + ms + " ms)", ms < 1000);
        Check.that("el hilo de pintado sigue vivo", alive("Render"));
        Check.that("los hilos de los sprites siguen vivos", alive("Sprite Pato") && alive("Sprite Explosión"));
        Check.that("la explosión sigue avanzando fotogramas", changesFrame(1, 300));
    }

    // ---------------------------------------------------------------- cierre

    static void closesWithTheX() {
        // Pulsar la X terminaría también esta prueba: solo se comprueba que está configurada así.
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(viewer);
        Check.equal("al pulsar la X, la aplicación termina", JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());
    }

    // ---------------------------------------------------------------- utilidades

    private static Viewer findViewer() throws Exception {
        for (int i = 0; i < 50; i++) {
            for (Frame f : Frame.getFrames()) {
                if (!f.isShowing() || !(f instanceof JFrame)) {
                    continue;
                }
                JFrame window = (JFrame) f;
                if (window.getContentPane().getComponentCount() > 0
                        && window.getContentPane().getComponent(0) instanceof Viewer) {
                    return (Viewer) window.getContentPane().getComponent(0);
                }
            }
            Thread.sleep(100);
        }
        throw new IllegalStateException("No aparece la ventana de la demo");
    }

    private static void bringToFront() throws Exception {
        SwingUtilities.invokeAndWait(MouseTest::keepOnTop);
        robot.delay(300);
        moveTo(viewer.getWidth() - 20, viewer.getHeight() - 20);   // un clic en una zona vacía, para darle el foco
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    /** Siempre encima: así el Robot no hace clic en otra ventana que la tape. Lo hace el hilo de eventos. */
    private static void keepOnTop() {
        java.awt.Window window = SwingUtilities.getWindowAncestor(viewer);
        window.setAlwaysOnTop(true);
        window.toFront();
    }

    /** Una tarea vacía para el hilo de eventos: sirve para ver si responde. */
    private static void doNothing() {
    }

    /** Mueve el ratón a un punto del lienzo (coordenadas del Viewer). */
    private static void moveTo(int x, int y) {
        Point origin = viewer.getLocationOnScreen();
        robot.mouseMove(origin.x + x, origin.y + y);
    }

    /** Mira el fotograma de un sprite cada 20 ms durante un rato. (Mirar solo al final engaña: con 4 fotogramas,
     *  tras avanzar 4 vuelve a estar en el mismo.) Cada imagen es una copia: se comparan sus píxeles. */
    private static boolean changesFrame(int index, int ms) {
        BufferedImage first = spriteView(index).image();
        for (int waited = 0; waited < ms; waited += 20) {
            robot.delay(20);
            if (!samePixels(spriteView(index).image(), first)) return true;
        }
        return false;
    }

    /** Cómo se ve ahora un sprite: lo mismo que recibe la vista. */
    private static SpriteView spriteView(int index) {
        return presenter.spriteViews().get(index);
    }

    /** Si dos imágenes del mismo tamaño tienen los mismos píxeles. */
    private static boolean samePixels(BufferedImage a, BufferedImage b) {
        for (int y = 0; y < a.getHeight(); y++) {
            for (int x = 0; x < a.getWidth(); x++) {
                if (a.getRGB(x, y) != b.getRGB(x, y)) return false;
            }
        }
        return true;
    }

    /** Comprueba que el puntero sigue donde lo dejó el Robot; si no, alguien ha tocado el ratón. */
    private static void checkPointerAt(int x, int y) {
        Point now = pointer();
        if (now.x != x || now.y != y) disturbed = true;
    }

    /** Dónde está el puntero ahora, en coordenadas del lienzo. */
    private static Point pointer() {
        Point origin = viewer.getLocationOnScreen();
        Point now = java.awt.MouseInfo.getPointerInfo().getLocation();
        return new Point(now.x - origin.x, now.y - origin.y);
    }

    /** Con -Ddepurar escribe cada evento del ratón: sirve para ver si alguien lo ha movido. */
    private static void printMouseEvents() {
        java.awt.Toolkit.getDefaultToolkit().addAWTEventListener(MouseTest::printEvent,
                java.awt.AWTEvent.MOUSE_EVENT_MASK | java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK
                | java.awt.AWTEvent.MOUSE_WHEEL_EVENT_MASK);
    }

    private static void printEvent(java.awt.AWTEvent e) {
        if (e.getID() != java.awt.event.MouseEvent.MOUSE_MOVED) System.out.println("    evento: " + e.paramString());
    }

    private static boolean alive(String threadName) {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals(threadName) && t.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private static void capture(String file) throws Exception {
        String dir = System.getProperty("captura");
        if (dir == null) return;
        Point origin = viewer.getLocationOnScreen();
        BufferedImage image = robot.createScreenCapture(
                new Rectangle(origin.x, origin.y, viewer.getWidth(), viewer.getHeight()));
        File out = new File(dir, file);
        out.getParentFile().mkdirs();
        ImageIO.write(image, "png", out);
        System.out.println("  captura: " + out.getAbsolutePath());
    }
}
