package view;

import config.Config;
import presenter.IPresenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Lo que hacen los botones del panel de control.
 * Todos los botones y opciones usan este mismo listener; para saber cuál se ha pulsado
 * miramos su "action command", un texto que le ponemos a cada botón con setActionCommand().
 */
public class ControlListener implements ActionListener {

    // Los textos que identifican cada botón
    public static final String START = "START";
    public static final String PAUSE = "PAUSE";
    public static final String STOP = "STOP";
    public static final String OPTION = "OPTION";   // una opción que activa / desactiva campos

    private final ControlPanel panel;
    private IPresenter presenter;

    public ControlListener(ControlPanel panel) {
        this.panel = panel;
    }

    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (OPTION.equals(command)) {
            panel.updateEnabled();
            return;
        }

        if (presenter == null) return;

        if (START.equals(command)) {
            start();
        } else if (PAUSE.equals(command)) {
            presenter.onPause();
        } else if (STOP.equals(command)) {
            presenter.onStop();
        }
    }

    /** Iniciar: si estaba en pausa reanuda; si no, lee los ajustes y arranca. */
    private void start() {
        if (panel.isPausedShown()) {
            presenter.onResume();
            return;
        }

        try {
            Config config = panel.readConfig();
            presenter.onStart(config);
            panel.showOk("Simulación iniciada");
        } catch (IllegalArgumentException ex) {
            panel.showError(ex.getMessage());
        }
    }
}
