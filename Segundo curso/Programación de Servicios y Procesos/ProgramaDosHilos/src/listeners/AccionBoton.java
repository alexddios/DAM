package listeners;

import presenter.IPresenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AccionBoton implements ActionListener {
    private IPresenter presenter;
    private JLabel resultLabel;


    public AccionBoton(IPresenter presenter, JLabel resultLabel) {
        this.presenter = presenter;
        this.resultLabel=resultLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtenemos el "comando" del botón que ha sido pulsado
        String comando = e.getActionCommand();

        // Evaluamos qué botón se pulsó
        switch (comando) {
            case "Simulando":
                if (presenter != null) {
                    resultLabel.setText("Simulando...");
                    presenter.onSimulationStarted();
                }
                break;

            default:
                System.out.println("Botón no reconocido");
                break;
        }
    }
}