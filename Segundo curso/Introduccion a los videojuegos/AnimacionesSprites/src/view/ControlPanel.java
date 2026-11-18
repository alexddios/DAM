package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/** Panel inferior con los botones Iniciar y Parar. */
public class ControlPanel extends JPanel {
    private JButton iniciar;
    private JButton parar;

    public ControlPanel() {
        // 1. El layout de este panel
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5);   // separación alrededor de cada botón

        // 2. Crear los botones
        iniciar = new JButton("Iniciar");
        parar   = new JButton("Parar");

        // 3. Botón Iniciar: fila 0, columna 0
        gc.gridx = 0;
        gc.gridy = 0;
        add(iniciar, gc);

        // 4. Botón Parar: fila 0, columna 1
        gc.gridx = 1;
        gc.gridy = 0;
        add(parar, gc);
    }

    /** Conecta los botones con el listener (la View). Lo llama View.setPresenter. */
    public void setActionListener(ActionListener listener) {
        // 1. Etiquetar cada botón con su comando, para que el listener sepa cuál se pulsó
        iniciar.setActionCommand(View.INICIAR);
        parar.setActionCommand(View.PARAR);

        // 2. El mismo listener escucha a los dos botones
        iniciar.addActionListener(listener);
        parar.addActionListener(listener);
    }

}
