package view;

import config.Config;
import config.WaitMode;
import presenter.IPresenter;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de control con todos los ajustes de la simulación, distribuido como en la pizarra:
 * botones arriba, ajustes en la columna izquierda y límites / sincronización a la derecha.
 */
public class ControlPanel extends JPanel {

    /** La clase que hace lo que piden los botones. */
    private final ControlListener listener = new ControlListener(this);

    // ---------- Botones ----------
    private final JButton startButton = new JButton("▶ Iniciar");
    private final JButton pauseButton = new JButton("❚❚ Pausa");
    private final JButton stopButton = new JButton("■ Detener");

    // ---------- Columna izquierda ----------
    private final JTextField producers = field("2");
    private final JTextField consumers = field("2");
    private final JTextField resourceTypes = field("1");

    private final JRadioButton producerTypeFixed = new JRadioButton("Fijo");
    private final JRadioButton producerTypeVariable = new JRadioButton("Variable", true);
    private final JRadioButton consumerTypeFixed = new JRadioButton("Fijo");
    private final JRadioButton consumerTypeVariable = new JRadioButton("Variable", true);

    private final JTextField produceTimeMin = field("200");
    private final JTextField produceTimeMax = field("600");
    private final JTextField consumeTimeMin = field("300");
    private final JTextField consumeTimeMax = field("800");

    private final JRadioButton produceUnlimited = new JRadioButton("Ilimitados", true);
    private final JRadioButton produceLimited = new JRadioButton();
    private final JTextField produceMin = field("10");
    private final JTextField produceMax = field("20");

    private final JRadioButton consumeUnlimited = new JRadioButton("Ilimitados", true);
    private final JRadioButton consumeLimited = new JRadioButton();
    private final JTextField consumeMin = field("10");
    private final JTextField consumeMax = field("20");

    private final JRadioButton stockControlYes = new JRadioButton("Sí", true);
    private final JRadioButton stockControlNo = new JRadioButton("No");

    private final JRadioButton modeSleep = new JRadioButton("sleep");
    private final JRadioButton modeWaitNotify = new JRadioButton("wait-notifyAll", true);

    // ---------- Columna derecha ----------
    private final JTextField stockMin = field("0");
    private final JTextField stockMax = field("10");

    private final JRadioButton syncYes = new JRadioButton("Sí", true);
    private final JRadioButton syncNo = new JRadioButton("No");

    private final JLabel message = new JLabel(" ");

    // Último estado pintado (para no repetir trabajo cada 100 ms)
    private boolean lastActive = true;
    private boolean lastPaused = true;

    public ControlPanel() {
        setBorder(BorderFactory.createTitledBorder("Panel de control"));
        setLayout(new BorderLayout(0, 6));

        add(buildButtons(), BorderLayout.NORTH);
        add(buildColumns(), BorderLayout.CENTER);
        add(message, BorderLayout.SOUTH);

        // Grupos de opciones: dentro de cada grupo solo puede haber una marcada
        group(producerTypeFixed, producerTypeVariable);
        group(consumerTypeFixed, consumerTypeVariable);
        group(produceUnlimited, produceLimited);
        group(consumeUnlimited, consumeLimited);
        group(stockControlYes, stockControlNo);
        group(modeSleep, modeWaitNotify);
        group(syncYes, syncNo);

        // Todos los botones los atiende el ControlListener (clase aparte).
        // El action command le dice qué botón se ha pulsado.
        listen(startButton, ControlListener.START);
        listen(pauseButton, ControlListener.PAUSE);
        listen(stopButton, ControlListener.STOP);

        // Al cambiar una opción, se activan / desactivan los campos que dependen de ella
        for (JRadioButton rb : new JRadioButton[]{produceUnlimited, produceLimited,
                consumeUnlimited, consumeLimited, stockControlYes, stockControlNo}) {
            listen(rb, ControlListener.OPTION);
        }

        setStatus(false, false);
    }

    // =====================================================================
    // CONSTRUCCIÓN DE LA INTERFAZ
    // =====================================================================

    private JPanel buildButtons() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        p.add(startButton);
        p.add(pauseButton);
        p.add(stopButton);
        return p;
    }

    private JPanel buildColumns() {
        // Columna izquierda
        JPanel left = new JPanel(new GridBagLayout());
        int row = 0;
        addRow(left, row++, "Nº productores", line(producers));
        addRow(left, row++, "Nº consumidores", line(consumers));
        addRow(left, row++, "Nº tipos de recurso", line(resourceTypes));
        addRow(left, row++, "Tipo recurso productores",
                line(producerTypeFixed, new JLabel("/"), producerTypeVariable));
        addRow(left, row++, "Tipo recurso consumidores",
                line(consumerTypeFixed, new JLabel("/"), consumerTypeVariable));
        addRow(left, row++, "Tiempo producir (ms)", range(produceTimeMin, "-", produceTimeMax));
        addRow(left, row++, "Tiempo consumir (ms)", range(consumeTimeMin, "-", consumeTimeMax));
        addRow(left, row++, "Recursos a producir",
                line(produceUnlimited, new JLabel("/"), produceLimited, range(produceMin, "-", produceMax)));
        addRow(left, row++, "Recursos a consumir",
                line(consumeUnlimited, new JLabel("/"), consumeLimited, range(consumeMin, "-", consumeMax)));
        addRow(left, row++, "Control stock recursos", line(stockControlYes, new JLabel("/"), stockControlNo));
        addRow(left, row++, "Tipo control stock", line(modeSleep, new JLabel("/"), modeWaitNotify));

        // Línea separadora (en la pizarra separaba las dos columnas)
        GridBagConstraints sep = new GridBagConstraints();
        sep.gridy = row++;
        sep.gridx = 0;
        sep.gridwidth = 2;
        sep.fill = GridBagConstraints.HORIZONTAL;
        sep.insets = new Insets(10, 0, 10, 0);
        left.add(new JSeparator(SwingConstants.HORIZONTAL), sep);

        // Segundo bloque (la columna derecha de la pizarra), en la misma cuadrícula para que se alinee
        addRow(left, row++, "Límite de recursos", range(stockMin, "/", stockMax));
        addRow(left, row++, "Sincronización producir/consumir", line(syncYes, new JLabel("/"), syncNo));

        // Relleno: si la ventana es alta, el espacio sobrante queda abajo y los campos arriba
        GridBagConstraints filler = new GridBagConstraints();
        filler.gridy = row;
        filler.weighty = 1;
        left.add(Box.createGlue(), filler);

        return left;
    }

    /** Una fila: etiqueta a la izquierda y el componente a la derecha. */
    private void addRow(JPanel panel, int row, String text, JComponent component) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = row;
        c.insets = new Insets(3, 4, 3, 8);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        JLabel label = new JLabel(text);
        // Margen a la derecha: con la escala de pantalla de Windows, Java a veces
        // calcula el texto unos píxeles más corto y lo corta con "..."
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        panel.add(label, c);

        c.gridx = 1;
        c.weightx = 1;
        panel.add(component, c);
    }

    /** [mín] - [máx] */
    private JPanel range(JTextField min, String separator, JTextField max) {
        return line(min, new JLabel(separator), max);
    }

    /** Varios componentes seguidos en la misma línea. */
    private JPanel line(JComponent... components) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        for (JComponent comp : components) p.add(comp);
        return p;
    }

    private static JTextField field(String text) {
        JTextField f = new JTextField(text, 5);
        f.setHorizontalAlignment(JTextField.RIGHT);
        return f;
    }

    private static void group(JRadioButton a, JRadioButton b) {
        ButtonGroup g = new ButtonGroup();
        g.add(a);
        g.add(b);
    }

    /** Le pone al botón su action command y le asigna el ControlListener. */
    private void listen(AbstractButton button, String command) {
        button.setActionCommand(command);
        button.addActionListener(listener);
    }

    // =====================================================================
    // ESTADO
    // =====================================================================

    public void setPresenter(IPresenter presenter) {
        listener.setPresenter(presenter);
    }

    /** ¿La pantalla muestra la simulación en pausa? (el botón Iniciar sirve para reanudar) */
    boolean isPausedShown() {
        return lastPaused;
    }

    void showOk(String text) {
        message.setForeground(new Color(0, 130, 70));
        message.setText(text);
    }

    void showError(String text) {
        message.setForeground(Color.RED);
        message.setText(text);
    }

    /** Lo llama la View en cada refresco: activa o bloquea botones y campos. */
    public void setStatus(boolean active, boolean paused) {
        if (active == lastActive && paused == lastPaused) return;
        lastActive = active;
        lastPaused = paused;

        startButton.setText(paused ? "▶ Reanudar" : "▶ Iniciar");
        startButton.setEnabled(!active || paused);
        pauseButton.setEnabled(active && !paused);
        stopButton.setEnabled(active);
        updateEnabled();
    }

    /** Los ajustes solo se pueden tocar con la simulación parada. */
    void updateEnabled() {
        boolean editable = !lastActive;

        for (JComponent comp : new JComponent[]{producers, consumers, resourceTypes,
                producerTypeFixed, producerTypeVariable, consumerTypeFixed, consumerTypeVariable,
                produceTimeMin, produceTimeMax, consumeTimeMin, consumeTimeMax,
                produceUnlimited, produceLimited, consumeUnlimited, consumeLimited,
                stockControlYes, stockControlNo, syncYes, syncNo}) {
            comp.setEnabled(editable);
        }

        boolean produceRange = editable && produceLimited.isSelected();
        produceMin.setEnabled(produceRange);
        produceMax.setEnabled(produceRange);

        boolean consumeRange = editable && consumeLimited.isSelected();
        consumeMin.setEnabled(consumeRange);
        consumeMax.setEnabled(consumeRange);

        // El tipo de control y los límites solo tienen sentido si hay control de stock
        boolean control = editable && stockControlYes.isSelected();
        modeSleep.setEnabled(control);
        modeWaitNotify.setEnabled(control);
        stockMin.setEnabled(control);
        stockMax.setEnabled(control);
    }

    // =====================================================================
    // LEER Y VALIDAR LOS CAMPOS (lo usa el ControlListener al pulsar Iniciar)
    // =====================================================================

    /** Lee todos los campos. Si alguno no es válido lanza una excepción y no se arranca nada. */
    Config readConfig() {
        int nProducers = read(producers, 0, "Nº productores");
        int nConsumers = read(consumers, 0, "Nº consumidores");
        if (nProducers + nConsumers == 0) {
            throw new IllegalArgumentException("Tiene que haber al menos un productor o un consumidor");
        }
        int nTypes = read(resourceTypes, 1, "Nº tipos de recurso");
        boolean pFixed = producerTypeFixed.isSelected();
        boolean cFixed = consumerTypeFixed.isSelected();

        int ptMin = read(produceTimeMin, 0, "Tiempo producir mín");
        int ptMax = read(produceTimeMax, ptMin, "Tiempo producir máx");
        int ctMin = read(consumeTimeMin, 0, "Tiempo consumir mín");
        int ctMax = read(consumeTimeMax, ctMin, "Tiempo consumir máx");

        boolean pUnlimited = produceUnlimited.isSelected();
        int pMin = pUnlimited ? 0 : read(produceMin, 1, "Recursos a producir mín");
        int pMax = pUnlimited ? 0 : read(produceMax, pMin, "Recursos a producir máx");

        boolean cUnlimited = consumeUnlimited.isSelected();
        int cMin = cUnlimited ? 0 : read(consumeMin, 1, "Recursos a consumir mín");
        int cMax = cUnlimited ? 0 : read(consumeMax, cMin, "Recursos a consumir máx");

        boolean control = stockControlYes.isSelected();
        WaitMode mode = modeSleep.isSelected() ? WaitMode.SLEEP : WaitMode.WAIT_NOTIFY;
        int sMin = control ? read(stockMin, 0, "Límite de recursos mín") : 0;
        int sMax = control ? read(stockMax, sMin + 1, "Límite de recursos máx") : 0;

        boolean sync = syncYes.isSelected();

        return new Config(nProducers, nConsumers, nTypes, pFixed, cFixed,
                ptMin, ptMax, ctMin, ctMax,
                pUnlimited, pMin, pMax,
                cUnlimited, cMin, cMax,
                control, mode, sMin, sMax,
                sync);
    }

    /** Convierte el texto a entero y comprueba el mínimo. */
    private int read(JTextField field, int min, String name) {
        int value;
        try {
            value = Integer.parseInt(field.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + ": debe ser un número entero");
        }
        if (value < min) {
            throw new IllegalArgumentException(name + ": debe ser al menos " + min);
        }
        return value;
    }
}
