package model;

import config.Estado;

public interface IModel {
    void update();
    void start();
    int[][] getTemperatures();
    void iniciar();
    void pausar();
    void parar();
    Estado getEstado();
    int getPasos();
}