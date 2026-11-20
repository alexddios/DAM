package view;

import javax.swing.*;
import java.awt.*;

/**
 * Panel superior: área de texto de solo lectura donde se ven las puntuaciones y los mensajes.
 * Va dentro de un JScrollPane para poder desplazarse si el texto no cabe.
 */
public class DataPanel extends JPanel {
    private final JTextArea area;

    /** El scroll ocupa todo el panel: fill BOTH y pesos 1 para que se estire en las dos direcciones. */
    public DataPanel(){
        setLayout(new GridBagLayout());

        this.area = new JTextArea();
        area.setEditable(false);
        JScrollPane pane = new JScrollPane(area);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx =1;
        c.weighty =1;

        add(pane,c);
    }
    /** Sustituye todo el contenido (setText y no append, para no repetir la lista). */
    public void ponerTexto(String texto){
        this.area.setText(texto);
    }
}
