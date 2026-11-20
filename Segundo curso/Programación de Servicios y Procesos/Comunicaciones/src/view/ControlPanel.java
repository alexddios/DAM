package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Panel inferior: campos de nombre y puntuación en una fila y el botón "Get Scores" debajo.
 * Cada componente que genera eventos lleva un actionCommand fijo ("get" y "enviar"),
 * porque el de un JTextField por defecto sería el texto que haya escrito el usuario.
 */
public class ControlPanel extends JPanel {
    private final JTextField textoNombre;
    private final JTextField textoScore;
    private final JButton getScores;

    public ControlPanel() {
        setLayout(new GridBagLayout());

        // Columnas ajustadas para que todo quepa en los 300 px de la ventana:
        // si no cabe, GridBagLayout encoge los campos a su tamaño mínimo (casi cero).
        textoNombre = new JTextField(7);
        textoScore = new JTextField(4);
        textoScore.setActionCommand("enviar");

        getScores = new JButton("Get Scores");
        getScores.setActionCommand("get");
        JPanel filaDatos = crearPar(
                crearPar(new JLabel("Nombre:"),textoNombre),
                crearPar(new JLabel("Puntuación:"),textoScore)
        );
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        add(filaDatos,c);

        c.gridy = 1;
        add(getScores,c);
    }

    /** Devuelve un panel con dos componentes uno al lado del otro (se puede anidar). */
    private JPanel crearPar(JComponent izquierda, JComponent derecha) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.insets = new Insets(2, 2, 2, 2);
        c.gridx = 0;
        panel.add(izquierda, c);
        c.gridx = 1;
        panel.add(derecha, c);
        return panel;
    }
    public String getNombre(){
        return this.textoNombre.getText();
    }
    public String getPuntuacion(){
        return textoScore.getText();
    }
    /** El botón y el Enter del campo de la puntuación avisan al mismo listener (la View). */
    public void setActionListener(ActionListener listener){
        getScores.addActionListener(listener);
        textoScore.addActionListener(listener);
    }
}
