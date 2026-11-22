package view;

import config.Config;
import config.Estado;

public class DataTask implements Runnable{
    private final DataPanel dataPanel;
    private final Estado estado;
    private final int pasos;
    private final int fps;
    private final Config config;

    public DataTask(DataPanel dataPanel, Estado estado, int pasos, int fps, Config config) {
        this.dataPanel = dataPanel;
        this.estado = estado;
        this.pasos = pasos;
        this.fps = fps;
        this.config = config;
    }

    @Override
    public void run() {
        dataPanel.actualizar(estado,pasos,fps,config);
    }
}
