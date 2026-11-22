package view;

import config.Config;
import config.Estado;

import javax.swing.*;
import java.awt.*;

public class DataPanel extends JPanel {
    private int fila;
    private final JLabel estadoLabel;
    private final JLabel pasosLabel;
    private final JLabel fpsLabel;
    private final JLabel chispasLabel;
    private final JLabel puntosFriosLabel;
    private final JLabel enfriamientoLabel;

    public DataPanel(){
        setBorder(BorderFactory.createTitledBorder("Panel de Datos"));
        setLayout(new GridBagLayout());
        this.fila = 0;

        estadoLabel = new JLabel();
        pasosLabel = new JLabel();
        fpsLabel = new JLabel();
        chispasLabel = new JLabel();
        puntosFriosLabel = new JLabel();
        enfriamientoLabel = new JLabel();
        addFila("Estado: ", estadoLabel);
        addFila("Pasos: ", pasosLabel);
        addFila("FPS: ", fpsLabel);
        addFila("Chispas: ", chispasLabel);
        addFila("Puntos fríos: ", puntosFriosLabel);
        addFila("Enfriamiento: ", enfriamientoLabel);

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
    public void actualizar(Estado estado, int pasos, int fps, Config config){
        estadoLabel.setText(estado.toString());
        pasosLabel.setText(String.valueOf(pasos));
        fpsLabel.setText(String.valueOf(fps));
        chispasLabel.setText(String.format("%d%%",config.chispas()));
        puntosFriosLabel.setText(String.valueOf(config.puntosFrios()));
        enfriamientoLabel.setText(String.valueOf(config.enfriamiento()));
    }
}
