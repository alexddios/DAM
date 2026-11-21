package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private final JButton iniciar;
    private final JButton pausar;
    private final JButton parar;

    public ControlPanel(){
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5);   // separación alrededor de cada botón

        iniciar = new JButton("Iniciar");
        pausar = new JButton("Pausar");
        parar = new JButton("Parar");

        gc.gridx = 0;
        gc.gridy = 0;
        add(iniciar,gc);

        gc.gridx = 1;
        add(pausar,gc);

        gc.gridx = 2;
        add(parar,gc);
    }
    public void setActionListener(ActionListener listener) {
        // 1. Etiquetar cada botón con su comando, para que el listener sepa cuál se pulsó
        iniciar.setActionCommand(View.INICIAR);
        pausar.setActionCommand(View.PAUSAR);
        parar.setActionCommand(View.PARAR);

        // 2. El mismo listener escucha a los tres botones
        iniciar.addActionListener(listener);
        pausar.addActionListener(listener);
        parar.addActionListener(listener);
    }
}
