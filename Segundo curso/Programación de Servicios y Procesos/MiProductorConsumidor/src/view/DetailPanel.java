package view;

import config.Estado;
import config.HiloInfo;
import config.ResourceInfo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel de la derecha: estado, totales y detalle de recursos e hilos.
 * actualizar() solo se llama desde el hilo de Swing, a través de PaintTask.
 */
public class DetailPanel extends JPanel {
    private final JLabel lblEstado;
    private final JLabel lblStockTotal;
    private final JLabel lblTotalProducido;
    private final JLabel lblTotalConsumido;
    private final JTextArea areaDetalle;

    public DetailPanel(){
        setBorder(BorderFactory.createTitledBorder("Panel de Detalles"));
        setLayout(new GridBagLayout());
        lblEstado = new JLabel();

        GridBagConstraints c = new GridBagConstraints();
        c.gridx =0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        add(lblEstado,c);
        lblStockTotal = new JLabel();
        c.gridy = 1;
        add(lblStockTotal,c);
        lblTotalProducido = new JLabel();
        c.gridy = 2;
        add(lblTotalProducido,c);
        lblTotalConsumido = new JLabel();
        c.gridy = 3;
        c.weighty = 0;
        add(lblTotalConsumido,c);
        areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(areaDetalle);
        c.gridy = 4;
        c.fill = GridBagConstraints.BOTH;
        c.weighty =1;
        add(jScrollPane,c);}
    public void actualizar(Estado estado,
                           int stockTotal,
                           int producido,
                           int consumido,
                           List<ResourceInfo> resources,
                           List<HiloInfo> hilos){
        this.lblEstado.setText(String.format("Estado: %s",estado));
        this.lblStockTotal.setText(String.format("Total: %d",stockTotal));
        this.lblTotalProducido.setText(String.format("Producido: %d",producido));
        this.lblTotalConsumido.setText(String.format("Consumido: %d",consumido));
        StringBuilder sb = new StringBuilder();
        sb.append("RECURSOS\n");
        for (ResourceInfo r: resources){
            sb.append(String.format("""
                    Nombre: %s
                    Stock: %d
                    Esperado %d
                    """,r.nombre(),r.stock(),r.stockEsperado()));
            if(r.descuadre()){
                sb.append("DESCUADRE\n");
            }
            if(r.fueraDeLimites()){
                sb.append("FUERA DE LÍMITES\n");
            }
        }
        sb.append("\nHILOS\n");
        for (HiloInfo h : hilos) {
            sb.append(String.format("%s: %d", h.nombre(), h.unidades()));  // trozo 1, sin salto
            if (h.objetivo() != -1) {
                sb.append(String.format("/%d",h.objetivo()));                  // trozo 2, opcional
            }
            sb.append(String.format("  %s",h.estadoHilo()));                     // trozo 3
            sb.append("\n");                                                // fin de línea
        }
        areaDetalle.setText(sb.toString());
    }
}
