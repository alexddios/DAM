package view;

import config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private int fila;
    private final JButton iniciar;
    private final JButton pausar;
    private final JButton parar;

    private final JSlider chispas;
    private final JSlider puntosFrios;
    private final JSlider enfriamiento;
    private final JButton aplicar;

    public ControlPanel(){
        setBorder(BorderFactory.createTitledBorder("Panel de Control"));
        setLayout(new GridBagLayout());
        this.fila =0;

        iniciar = new JButton("▶ Iniciar");
        pausar = new JButton("⏸ Pausar");
        parar = new JButton("⏹ Parar");

        addBotones();

        chispas = new JSlider(Config.CHISPAS_MIN,Config.CHISPAS_MAX,40);
        puntosFrios = new JSlider(Config.PUNTO_FRIO_MIN,Config.PUNTO_FRIO_MAX,30);
        enfriamiento = new JSlider(Config.ENFRIAMIENTO_MIN,Config.ENFRIAMIENTO_MAX,1);

        chispas.setMajorTickSpacing(10);
        puntosFrios.setMajorTickSpacing(10);
        enfriamiento.setMajorTickSpacing(2);

        chispas.setPaintTicks(true);
        puntosFrios.setPaintTicks(true);
        enfriamiento.setPaintTicks(true);

        chispas.setPaintLabels(true);
        puntosFrios.setPaintLabels(true);
        enfriamiento.setPaintLabels(true);

        addFila("Chispas (%)",chispas);
        addFila("Puntos fríos",puntosFrios);
        addFila("Enfriamiento",enfriamiento);

        aplicar = new JButton("Aplicar");

        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0;
        gb.gridy = fila;
        gb.gridwidth = 2;
        add(aplicar,gb);
        fila++;
    }
    private void addBotones() {
        JPanel panelBotones = new JPanel(new GridBagLayout());
        GridBagConstraints cb = new GridBagConstraints();
        cb.gridy = 0;
        cb.insets = new Insets(2, 2, 2, 2);

        cb.gridx = 0;
        panelBotones.add(iniciar, cb);
        cb.gridx = 1;
        panelBotones.add(pausar, cb);
        cb.gridx = 2;
        panelBotones.add(parar, cb);


        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = fila;
        c.gridwidth = 2;
        add(panelBotones, c);
        fila++;
    }
    private void addFila(String texto, JComponent componente) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = fila;
        c.insets = new Insets(2, 4, 2, 4);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        add(new JLabel(texto), c);

        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(componente, c);

        fila++;
    }

    public void setActionListener(ActionListener listener) {
        // 1. Etiquetar cada botón con su comando, para que el listener sepa cuál se pulsó
        iniciar.setActionCommand(View.INICIAR);
        pausar.setActionCommand(View.PAUSAR);
        parar.setActionCommand(View.PARAR);
        aplicar.setActionCommand(View.APLICAR);

        // 2. El mismo listener escucha a los cuatro botones
        iniciar.addActionListener(listener);
        pausar.addActionListener(listener);
        parar.addActionListener(listener);
        aplicar.addActionListener(listener);
    }
    public Config getConfig(){
        return new Config(chispas.getValue(),puntosFrios.getValue(),enfriamiento.getValue());
    }

}
