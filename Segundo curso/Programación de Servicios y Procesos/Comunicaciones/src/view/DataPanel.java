package view;

import javax.swing.*;
import java.awt.*;

public class DataPanel extends JPanel {
    private final JTextArea area;

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
    public void ponerTexto(String texto){
        this.area.setText(texto);
    }
}
