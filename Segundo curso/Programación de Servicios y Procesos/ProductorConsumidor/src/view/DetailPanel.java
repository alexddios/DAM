package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/** Panel grande con el resumen, cada recurso y una línea por hilo. */
public class DetailPanel extends JPanel {

    private final JTextArea area = new JTextArea();

    public DetailPanel() {
        setBorder(BorderFactory.createTitledBorder("Datos"));
        setLayout(new BorderLayout());
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    public void update(String state, int stock, int produced, int consumed,
                       List<String> resources, List<String> threads) {
        StringBuilder sb = new StringBuilder();
        sb.append("Estado:            ").append(state).append('\n');
        sb.append("Stock total:       ").append(stock).append('\n');
        sb.append("Total producido:   ").append(produced).append('\n');
        sb.append("Total consumido:   ").append(consumed).append('\n');

        sb.append("\nRECURSOS\n");
        for (String line : resources) sb.append("  ").append(line).append('\n');

        sb.append("\nHILOS\n");
        for (String line : threads) sb.append("  ").append(line).append('\n');

        // Solo cambiamos el texto si es distinto, para no mover el scroll sin motivo
        String text = sb.toString();
        if (!text.equals(area.getText())) {
            area.setText(text);
        }
    }
}
