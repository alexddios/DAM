import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class VisorImagenGris extends JPanel {
    private BufferedImage imagen;

    public VisorImagenGris(String rutaImagen) {
        try {
            imagen = ImageIO.read(new File(rutaImagen));

            // Si la imagen se ha cargado correctamente, la procesamos
            if (imagen != null) {
                convertirAGrises();
            }

        } catch (IOException e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
        }
    }

    private void convertirAGrises() {
        int ancho = imagen.getWidth();
        int alto = imagen.getHeight();

        // Recorremos cada píxel de la imagen (eje X e Y)
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                // 1. Obtenemos el color original del píxel
                int rgbOriginal = imagen.getRGB(x, y);

                // 2. Extraemos los canales usando operaciones de bits (Bitwise)
                // El canal Alpha (transparencia) lo dejamos igual para no perderlo
                int alpha = (rgbOriginal >> 24) & 0xFF;
                int rojo  = (rgbOriginal >> 16) & 0xFF;
                int verde = (rgbOriginal >> 8) & 0xFF;
                int azul  = rgbOriginal & 0xFF;

                // 3. Calculamos la media de los tres colores
                int media = (rojo + verde + azul) / 3;

                // 4. Creamos el nuevo color poniendo la media en el rojo, verde y azul
                int rgbGris = (alpha << 24) | (media << 16) | (media << 8) | media;

                // 5. Sustituimos el píxel original por el nuevo píxel en gris
                imagen.setRGB(x, y, rgbGris);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, this);
        } else {
            g.drawString("No se encontró la imagen.", 20, 30);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (imagen != null) {
            return new Dimension(imagen.getWidth(), Math.max(imagen.getHeight(), 100));
        }
        return new Dimension(400, 300);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Mi Visor de Imágenes en Blanco y Negro");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // IMPORTANTE: Cambia la ruta por una imagen real de tu disco
            String ruta = "C:\\Users\\alexd\\DAM\\Segundo curso\\Introduccion a los videojuegos\\Imagen\\pikachu.jpg";
            VisorImagenGris panel = new VisorImagenGris(ruta);

            ventana.add(panel);
            ventana.pack();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}