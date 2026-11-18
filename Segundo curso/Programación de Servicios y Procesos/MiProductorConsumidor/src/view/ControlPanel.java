package view;

import config.Config;
import config.WaitMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Panel de la izquierda: ajustes de la simulación y botones.
 * Sabe construir un Config con los valores actuales y bloquear o
 * desbloquear sus controles. Todo con GridBagLayout.
 */
public class ControlPanel extends JPanel {
    private int fila;
    private final JSpinner productores;
    private final JSpinner consumidores;
    private final JSpinner recursos;
    private final JButton btnIniciar;
    private final JButton btnPausar;
    private final JButton btnDetener;
    private final JSpinner tiempoProdMin;
    private final JSpinner tiempoProdMax;
    private final JSpinner tiempoConsMin;
    private final JSpinner tiempoConsMax;
    private final JSpinner stockMin;
    private final JSpinner stockMax;
    private final JRadioButton productorFijo;
    private final JRadioButton productorVariable;
    private final JRadioButton consumidorFijo;
    private final JRadioButton consumidorVariable;
    private final ButtonGroup grupoProductor;
    private final ButtonGroup grupoConsumidor;
    private final JCheckBox ilimitadoProducir;
    private final JSpinner minProducir;
    private final JSpinner maxProducir;
    private final JCheckBox ilimitadoConsumir;
    private final JSpinner minConsumir;
    private final JSpinner maxConsumir;
    private final JCheckBox controlStock;
    private final JComboBox<WaitMode> tipoControl;
    private final JCheckBox sincronizacion;

    public ControlPanel() {
        setBorder(BorderFactory.createTitledBorder("Panel de Control"));
        setLayout(new GridBagLayout());
        this.fila = 0;

        btnIniciar = new JButton("Iniciar");
        btnIniciar.setActionCommand("INICIAR");
        btnPausar = new JButton("Pausar");
        btnPausar.setActionCommand("PAUSAR");
        btnDetener = new JButton("Detener");
        btnDetener.setActionCommand("DETENER");

        addBotones();

        productores = new JSpinner(new SpinnerNumberModel(2, 0, 100, 1));
        consumidores = new JSpinner(new SpinnerNumberModel(2, 0, 100, 1));
        recursos = new JSpinner(new SpinnerNumberModel(2, 1, 100, 1));


        addFila("Nº productores", productores);
        addFila("Nº consumidores", consumidores);
        addFila("Nº recursos", recursos);

        tiempoProdMin = new JSpinner(new SpinnerNumberModel(100, 0, 10000, 10));
        tiempoProdMax = new JSpinner(new SpinnerNumberModel(300, 0, 10000, 10));
        tiempoConsMin = new JSpinner(new SpinnerNumberModel(100, 0, 10000, 10));
        tiempoConsMax = new JSpinner(new SpinnerNumberModel(300, 0, 10000, 10));

        stockMin = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        stockMax = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 1));

        addFila("Tiempo producir (ms)", crearPar(tiempoProdMin, tiempoProdMax));
        addFila("Tiempo consumir (ms)", crearPar(tiempoConsMin, tiempoConsMax));
        addFila("Límites de stock", crearPar(stockMin, stockMax));

        productorFijo = new JRadioButton("Fijo", true);
        productorVariable = new JRadioButton("Variable");
        grupoProductor = new ButtonGroup();
        grupoProductor.add(productorFijo);
        grupoProductor.add(productorVariable);

        consumidorFijo = new JRadioButton("Fijo", true);
        consumidorVariable = new JRadioButton("Variable");
        grupoConsumidor = new ButtonGroup();
        grupoConsumidor.add(consumidorFijo);
        grupoConsumidor.add(consumidorVariable);

        addFila("Recurso productores", crearPar(productorFijo, productorVariable));
        addFila("Recurso consumidores", crearPar(consumidorFijo, consumidorVariable));

        ilimitadoProducir = new JCheckBox("Ilimitado");
        ilimitadoProducir.setActionCommand("CAMPOS");
        minProducir = new JSpinner(new SpinnerNumberModel(10, 0, 10000, 1));
        maxProducir = new JSpinner(new SpinnerNumberModel(20, 0, 10000, 1));
        addFila("Recursos a producir", crearPar(crearPar(ilimitadoProducir, minProducir), maxProducir));
        ilimitadoConsumir = new JCheckBox("Ilimitado");
        ilimitadoConsumir.setActionCommand("CAMPOS");
        minConsumir = new JSpinner(new SpinnerNumberModel(10, 0, 10000, 1));
        maxConsumir = new JSpinner(new SpinnerNumberModel(20, 0, 10000, 1));
        addFila("Recursos a consumir", crearPar(crearPar(ilimitadoConsumir, minConsumir), maxConsumir));

        controlStock = new JCheckBox("", true);
        controlStock.setActionCommand("CAMPOS");
        addFila("Control de stock", controlStock);

        tipoControl = new JComboBox<>(WaitMode.values());
        addFila("Tipo de control", tipoControl);

        sincronizacion = new JCheckBox("", true);
        addFila("Sincronización", sincronizacion);
        actualizarCampos();
    }

    private void addBotones() {
        JPanel panelBotones = new JPanel(new GridBagLayout());
        GridBagConstraints cb = new GridBagConstraints();
        cb.gridy = 0;
        cb.insets = new Insets(2, 2, 2, 2);

        cb.gridx = 0;
        panelBotones.add(btnIniciar, cb);
        cb.gridx = 1;
        panelBotones.add(btnPausar, cb);
        cb.gridx = 2;
        panelBotones.add(btnDetener, cb);


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

    public Config getConfig() {
        return new Config(
                (int) productores.getValue(),        // numProductores
                (int) consumidores.getValue(),       // numConsumidores
                (int) recursos.getValue(),                      // numRecursos
                productorFijo.isSelected(),          // productoresFijos
                consumidorFijo.isSelected(),                            // consumidoresFijos
                (int) tiempoProdMin.getValue(),      // tiempoProducirMin
                (int) tiempoProdMax.getValue(),                            // tiempoProducirMax
                (int) tiempoConsMin.getValue(),                            // tiempoConsumirMin
                (int) tiempoConsMax.getValue(),                            // tiempoConsumirMax
                ilimitadoProducir.isSelected(),      // producirIlimitado
                (int) minProducir.getValue(),                            // producirMin
                (int) maxProducir.getValue(),                            // producirMax
                ilimitadoConsumir.isSelected(),                            // consumirIlimitado
                (int) minConsumir.getValue(),                            // consumirMin
                (int) maxConsumir.getValue(),                            // consumirMax
                controlStock.isSelected(),                            // controlStock
                (WaitMode) tipoControl.getSelectedItem(), // modoEspera
                (int) stockMin.getValue(),                            // stockMin
                (int) stockMax.getValue(),                            // stockMax
                sincronizacion.isSelected()                             // sincronizado
        );
    }

    public void setListener(ActionListener listener) {
        btnIniciar.addActionListener(listener);
        btnPausar.addActionListener(listener);
        btnDetener.addActionListener(listener);
        ilimitadoProducir.addActionListener(listener);
        ilimitadoConsumir.addActionListener(listener);
        controlStock.addActionListener(listener);
    }

    public void setAjustesEditables(boolean editables) {
        productores.setEnabled(editables);
        consumidores.setEnabled(editables);
        recursos.setEnabled(editables);
        tiempoProdMin.setEnabled(editables);
        tiempoProdMax.setEnabled(editables);
        tiempoConsMin.setEnabled(editables);
        tiempoConsMax.setEnabled(editables);
        stockMin.setEnabled(editables);
        stockMax.setEnabled(editables);
        productorFijo.setEnabled(editables);
        productorVariable.setEnabled(editables);
        consumidorFijo.setEnabled(editables);
        consumidorVariable.setEnabled(editables);
        ilimitadoProducir.setEnabled(editables);
        minProducir.setEnabled(editables);
        maxProducir.setEnabled(editables);
        ilimitadoConsumir.setEnabled(editables);
        minConsumir.setEnabled(editables);
        maxConsumir.setEnabled(editables);
        controlStock.setEnabled(editables);
        tipoControl.setEnabled(editables);
        sincronizacion.setEnabled(editables);
        if(editables){
            actualizarCampos();
        }
    }
    public void actualizarCampos(){
        boolean rangoProducir = !ilimitadoProducir.isSelected();
        minProducir.setEnabled(rangoProducir);
        maxProducir.setEnabled(rangoProducir);

        boolean rangoConsumir = !ilimitadoConsumir.isSelected();
        minConsumir.setEnabled(rangoConsumir);
        maxConsumir.setEnabled(rangoConsumir);

        boolean rangoStock = controlStock.isSelected();
        stockMin.setEnabled(rangoStock);
        stockMax.setEnabled(rangoStock);
        tipoControl.setEnabled(rangoStock);


    }
}