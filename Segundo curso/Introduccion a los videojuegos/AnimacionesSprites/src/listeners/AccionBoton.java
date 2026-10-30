package listeners;

import presenter.IPresenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener común de los botones. Mira el actionCommand del botón pulsado
 * y avisa al presenter. La vista no decide qué hacer: solo avisa.
 */
public class AccionBoton implements ActionListener {
    // Comandos de los botones (public static para usarlos desde ControlPanel)
    public static final String PARAR = "BTN_PARAR";
    public static final String INICIAR = "BTN_INICIAR";

    private final IPresenter presenter;

    public AccionBoton(IPresenter presenter){
        this.presenter=presenter;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        switch (e.getActionCommand()){
            case PARAR -> presenter.stop();
            case INICIAR -> presenter.play();
        }
    }
}
