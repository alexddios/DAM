package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private final JTextField textoNombre;
    private final JTextField textoScore;
    private final JButton getScores;

    public ControlPanel() {
        setLayout(new GridBagLayout());

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
    public void setActionListener(ActionListener listener){
        getScores.addActionListener(listener);
        textoScore.addActionListener(listener);
    }
}
