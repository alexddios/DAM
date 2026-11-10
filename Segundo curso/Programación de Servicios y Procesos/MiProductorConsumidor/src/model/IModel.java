package model;

import config.Config;
import config.Estado;
import config.HiloInfo;
import config.ResourceInfo;

import java.util.List;

public interface IModel {
    void iniciar(Config config);
    void reanudar();
    void detener();
    void pausar();
    int getContadorProd();
    int getContadorCon();
    int getStock();
    Estado getEstado();
    List<ResourceInfo> getResources();
    List<HiloInfo> getHilos();
}
