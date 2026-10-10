## Plantilla Base
```java
import javax.swing.*;
import java.awt.*;

public class PruebaLayouts {
    public static void main(String[] args) {
        // Aseguramos que la interfaz se construya en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tutorial de Layouts");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            
            // Contenedor principal donde aplicaremos los layouts
            JPanel panel = new JPanel();
            
            // ==========================================
            // AQUÍ APLICAREMOS LOS DIFERENTES LAYOUTS
            // ==========================================
            
            frame.add(panel);
            frame.setVisible(true);
        });
    }
}
```
## 1. FlowLayout
El `FlowLayout` es el gestor por defecto para los `JPanel`. Coloca los componentes en una línea, uno detrás de otro, de izquierda a derecha. Si no caben, saltan a la siguiente línea, como si fuera texto en un procesador de textos.
```java
// 1. Configurar el layout
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Alineación centro, márgenes de 10px
            
            // 2. Añadir componentes
            panel.add(new JButton("Botón 1"));
            panel.add(new JButton("Botón 2"));
            panel.add(new JButton("Botón 3 largo"));
            panel.add(new JButton("Botón 4"));
            panel.add(new JButton("Botón 5"));
```
![[FlowLayout.png]]
## 2. BorderLayout (El clásico de las ventanas)
El `BorderLayout` es el gestor por defecto de los `JFrame`. Divide el contenedor en 5 regiones: Norte, Sur, Este, Oeste y Centro. Si una región no tiene nada, las demás (especialmente el Centro) ocupan ese espacio.
```java
// 1. Configurar el layout
            panel.setLayout(new BorderLayout(5, 5)); // 5px de separación entre zonas
            
            // 2. Añadir componentes indicando la posición
            panel.add(new JButton("Norte (Top)"), BorderLayout.NORTH);
            panel.add(new JButton("Sur (Bottom)"), BorderLayout.SOUTH);
            panel.add(new JButton("Este (Right)"), BorderLayout.EAST);
            panel.add(new JButton("Oeste (Left)"), BorderLayout.WEST);
            panel.add(new JButton("Centro (Ocupa el resto)"), BorderLayout.CENTER);
```
![[BorderLayout.png]]
## 3. GridLayout (La cuadrícula estricta)
El `GridLayout` divide el contenedor en una cuadrícula de celdas del mismo tamaño exacto. Es perfecto para teclados numéricos, calculadoras o tableros de juegos.
```java
// 1. Configurar el layout: 2 filas, 3 columnas, separación horizontal 5, vertical 5
            panel.setLayout(new GridLayout(2, 3, 5, 5));
            
            // 2. Añadir componentes (se llenan de izquierda a derecha, fila por fila)
            panel.add(new JButton("Uno"));
            panel.add(new JButton("Dos"));
            panel.add(new JButton("Tres"));
            panel.add(new JButton("Cuatro"));
            panel.add(new JButton("Cinco"));
            panel.add(new JButton("Seis"));
```
![[GridLayout.png]]
## 4. BoxLayout (Apilamiento flexible)
Te permite apilar componentes en una sola fila (horizontal) o en una sola columna (vertical). A diferencia de GridLayout, permite que los componentes mantengan sus tamaños preferidos si es posible.
```java
// 1. Configurar el layout: Eje vertical (Y_AXIS)
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            
            // 2. Añadir componentes
            JButton btn1 = new JButton("Primer Botón");
            btn1.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar en el eje X
            
            JButton btn2 = new JButton("Segundo Botón un poco más grande");
            btn2.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            panel.add(btn1);
            panel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciador invisible de 20px
            panel.add(btn2);
```
![[BoxLayout.png]]