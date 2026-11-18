package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import contract.ModelContract;

/**
 * El modelo de MVP: el estado de la aplicación, los sprites. No sabe que existe una ventana.
 * No deja salir nada suyo: hacia fuera da números, textos y copias ampliadas de los fotogramas.
 */
public class Model implements ModelContract {

    private static final String DUCK = "Pato";   // el sprite que sirve de modelo para los patos nuevos

    // La lista la recorre el hilo de pintado mientras el de eventos le añade patos.
    // CopyOnWriteArrayList lo permite: cada vez que se añade, hace una lista nueva y cambia una por otra.
    private final List<Sprite> sprites = new CopyOnWriteArrayList<>();

    // Cómo nació el pato: lo apunta addSprite y lo usa createDuck. Sin pato, createDuck no hace nada.
    private BufferedImage[] duckFrames;
    private int duckScale;
    private int duckFps;

    public Model() {}   // nace vacío: main le pide las animaciones con addSprite

    /**
     * Crea un sprite a partir de su hoja y lo añade. No es del contrato: solo lo usa main,
     * y así el presentador no puede crear animaciones (no compilaría).
     */
    public void addSprite(String name, String sheet, int frameWidth, int frameHeight, int scale,
                          int fps, int x, int y) {
        BufferedImage[] frames = SpriteSheet.load(sheet, frameWidth, frameHeight);   // a su tamaño original
        this.sprites.add(new Sprite(name, frames, scale, fps, x, y));

        if (name.equals(DUCK)) {   // el pato: se apunta cómo es, para hacer más con Ctrl+clic
            this.duckFrames = frames;
            this.duckScale = scale;
            this.duckFps = fps;
        }
    }

    /**
     * Un pato nuevo, igual que el pato de main al empezar: los mismos fotogramas (no relee la hoja),
     * su escala y su velocidad. Con su centro en el punto pulsado, y ya animándose.
     */
    @Override
    public void createDuck(int centerX, int centerY) {
        if (this.duckFrames == null) {
            return;   // main no ha puesto ningún pato
        }

        int x = centerX - this.duckFrames[0].getWidth() * this.duckScale / 2;   // de su centro a su esquina
        int y = centerY - this.duckFrames[0].getHeight() * this.duckScale / 2;
        Sprite duck = new Sprite(DUCK, this.duckFrames, this.duckScale, this.duckFps, x, y);
        this.sprites.add(duck);
        duck.start();   // solo se piden patos con la ventana abierta, así que la aplicación ya ha arrancado
    }

    @Override
    public void start() {
        for (Sprite sprite : this.sprites) {
            sprite.start();
        }
    }

    @Override
    public int spriteCount() {
        return this.sprites.size();
    }

    // Los datos de cada sprite, por su número. El Sprite no sale de aquí.

    /**
     * El fotograma que toca ahora, en una copia ya ampliada a su escala:
     * quien lo reciba no puede cambiar el del modelo.
     */
    @Override
    public BufferedImage frame(int index) {
        Sprite sprite = this.sprites.get(index);
        return enlargedCopy(sprite.frame(), sprite.scale());
    }

    @Override public int x(int index) { return this.sprites.get(index).x(); }
    @Override public int y(int index) { return this.sprites.get(index).y(); }
    @Override public String name(int index) { return this.sprites.get(index).name(); }
    @Override public int fps(int index) { return this.sprites.get(index).fps(); }
    @Override public void setFps(int index, int fps) { this.sprites.get(index).setFps(fps); }
    @Override public int scale(int index) { return this.sprites.get(index).scale(); }
    @Override public void setScale(int index, int scale) { this.sprites.get(index).setScale(scale); }
    @Override public void moveTo(int index, int x, int y) { this.sprites.get(index).moveTo(x, y); }

    /** Una imagen nueva, scale veces más grande que la original. Sin suavizar, para que se vean los píxeles (pixel art). */
    private static BufferedImage enlargedCopy(BufferedImage image, int scale) {
        int width = image.getWidth() * scale;
        int height = image.getHeight() * scale;

        BufferedImage copy = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = copy.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);   // sin interpolación: vecino más próximo
        g.dispose();
        return copy;
    }
}
