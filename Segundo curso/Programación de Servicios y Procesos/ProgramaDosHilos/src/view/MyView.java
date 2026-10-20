package view;

import presenter.IPresenter;
import listeners.AccionBoton; // 1. Añadimos la importación de tu clase

import javax.swing.*;
import java.awt.*;

// La vista hereda de JFrame e implementa el contrato IView
public class MyView extends JFrame implements IView {

    private IPresenter presenter;
    private JLabel resultLabel;
    private JButton startButton;

    // El constructor contiene tu diseño GridBagLayout
    public MyView() {
        super("Layout 100% GridBagLayout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // CONTENEDOR PRINCIPAL
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // ---------------------------------------------------------
        // 1. PANEL CP (Anida su propio GridBagLayout interno)
        // ---------------------------------------------------------
        JPanel cp = new JPanel(new GridBagLayout());
        cp.setBorder(BorderFactory.createTitledBorder("CP (JPanel)"));
        cp.setBackground(new Color(220, 220, 220));

        // Añadir CP al contenedor principal
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.weightx = 0.3; // 30% del ancho de la ventana
        gc.weighty = 0.4; // 40% del alto de la ventana
        gc.fill = GridBagConstraints.BOTH;
        p.add(cp, gc);

        // ---------------------------------------------------------
        // 2. PANEL DP con JTable (También usa GridBagLayout)
        // ---------------------------------------------------------
        JPanel dp = new JPanel(new GridBagLayout());
        dp.setBorder(BorderFactory.createTitledBorder("DP (Contiene JTable)"));

        GridBagConstraints gcDp = new GridBagConstraints();
        gcDp.gridx = 0;
        gcDp.gridy = 0;
        gcDp.weightx = 1.0;
        gcDp.weighty = 1.0;
        gcDp.fill = GridBagConstraints.BOTH;

        JTable tabla = new JTable(5, 3);
        dp.add(new JScrollPane(tabla), gcDp);

        // Añadir DP al contenedor principal
        gc.gridx = 0;
        gc.gridy = 1;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.weightx = 0.3;
        gc.weighty = 0.6; // 60% del alto restante
        gc.fill = GridBagConstraints.BOTH;
        p.add(dp, gc);

        // ---------------------------------------------------------
        // 3. VIEWER PANEL (AQUÍ INTEGRAMOS EL SIMULADOR)
        // ---------------------------------------------------------
        JPanel viewerPanel = new JPanel();
        viewerPanel.setBorder(BorderFactory.createTitledBorder("Viewer Panel (JPanel)"));
        viewerPanel.setBackground(Color.WHITE);

        // Inicializamos los componentes del simulador
        startButton = new JButton("Iniciar Simulación");
        resultLabel = new JLabel("Resultado: Esperando...");

        // 2. ELIMINAMOS AQUÍ LA CLASE ANÓNIMA QUE TENÍAS.
        // La asignación del listener se mueve al método setPresenter().

        // Añadimos los componentes al viewerPanel
        cp.add(startButton);
        viewerPanel.add(resultLabel);

        // Añadir viewerPanel al contenedor principal
        gc.gridx = 1;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 2; // Ocupa las dos filas de la izquierda
        gc.weightx = 0.7; // 70% del ancho de la ventana
        gc.weighty = 1.0;
        gc.fill = GridBagConstraints.BOTH;
        p.add(viewerPanel, gc);

        // Añadimos el contenedor principal al JFrame
        add(p);
    }

    // ---------------------------------------------------------
    // MÉTODOS DE LA INTERFAZ IVIEW
    // ---------------------------------------------------------

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;

        // 3. CONECTAMOS EL BOTÓN AQUÍ
        // Como aquí el 'presenter' ya ha sido inyectado y no es null, se lo podemos pasar a tu clase.
        // Le asignamos el ActionCommand explícito que espera el 'switch' de tu AccionBoton.
        startButton.setActionCommand("Simulando");
        startButton.addActionListener(new AccionBoton(this.presenter, this.resultLabel));
    }

    @Override
    public void updateCount(int currentCount) {
        // Actualizamos la UI de forma segura usando Runnable (sin lambda)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                resultLabel.setText("Resultado final: " + currentCount);
            }
        });
    }
}