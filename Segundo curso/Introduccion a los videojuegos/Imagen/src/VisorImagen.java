import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class VisorImagen extends JPanel {
    private BufferedImage imagen;

    public VisorImagen(String rutaImagen) {
        try {
            // 1. Lee la imagen directamente desde el disco
            imagen = ImageIO.read(new File(rutaImagen));
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 2. Pinta la imagen en las coordenadas (0, 0) de la ventana
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, this);
        } else {
            g.drawString("No se encontró la imagen en la ruta especificada.", 20, 30);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        // Ajusta el tamaño del panel al tamaño exacto de la imagen
        if (imagen != null) {
            return new Dimension(imagen.getWidth(), imagen.getHeight());
        }
        return new Dimension(400, 300); // Tamaño por defecto si la imagen falla
    }

    public static void main(String[] args) {
        // Se recomienda ejecutar la GUI de Swing en su propio hilo (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Mi Visor de Imágenes");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // IMPORTANTE: Cambia esta ruta a la ubicación real de tu imagen (usa '/' o '\\' en Windows)
            String ruta = "C:\\Users\\alexd\\DAM\\Segundo curso\\Introduccion a los videojuegos\\Imagen\\pikachu.jpg";
            VisorImagen panel = new VisorImagen(ruta);

            ventana.add(panel);
            ventana.pack(); // Redimensiona la ventana para que se ajuste al 'PreferredSize' del panel
            ventana.setLocationRelativeTo(null); // Centra la ventana en la pantalla
            ventana.setVisible(true);
        });
    }
}