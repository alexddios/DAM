package model;

import config.Config;

import java.util.Random;

public class Fire {
    private  int sparksPercentage = 40;
    private  int coolPontsPercentage = 30;
    private  int cooling = 1;

    private final int width;
    private final int height;
    private final int [][] temperatures;

    private final Random random = new Random();

    public Fire(int width, int height) {
        this.width = width;
        this.height = height;
        temperatures = new int[height][width];
    }

    public void sparksGeneration(){
        int base = height -1;
        for (int x = 0; x < width; x++) {
            if (random.nextInt(100)<sparksPercentage){
                temperatures[base][x] = 255;
            }
        }
    }
    public void coolPointsGeneration(){
        int base = height -1;
        for (int x = 0; x < width; x++) {
            if (random.nextInt(100)< coolPontsPercentage){
                temperatures[base][x] = 0;
            }
        }
    }
    public void updateFlames(){
        // Cada celda toma la media de las 3 de debajo (y+1) y de sí misma: el fuego sube
        for (int y = 0; y < height - 1; y++){
            for (int x = 0; x < width; x++) {
                int actual = temperatures[y][x];
                int left   = temperatures[y+1][Math.max(x-1, 0)];        // evita salir por la izquierda
                int center = temperatures[y+1][x];
                int right  = temperatures[y+1][Math.min(x+1, width-1)]; // evita salir por la derecha

                // Se enfría un poco al subir, sin bajar de 0
                temperatures[y][x] = Math.max(0, (actual + left + center + right) / 4- random.nextInt(cooling +1));
            }
        }
    }
    public void restartTemperatures(){
        for (int i = 0; i < temperatures.length; i++) {
            for (int j = 0; j < temperatures[0].length; j++) {
                temperatures[i][j]=0;
            }
        }
    }

    public int[][] getTemperatures() {
        return temperatures;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public void setAtributos(Config config){
        this.sparksPercentage = config.chispas();
        this.coolPontsPercentage = config.puntosFrios();
        this.cooling = config.enfriamiento();
    }

    public int getSparksPercentage() {
        return sparksPercentage;
    }

    public int getCoolPontsPercentage() {
        return coolPontsPercentage;
    }

    public int getCooling() {
        return cooling;
    }
}