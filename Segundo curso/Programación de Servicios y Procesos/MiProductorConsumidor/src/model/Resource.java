package model;

import config.WaitMode;

public class Resource {
    private volatile int stock;
    private final int minimo;
    private final int maximo;
    private final String nombre;
    private final boolean controlStock;
    private final WaitMode modoEspera;
    private final boolean sincronizado;
    private int producidos;
    private int consumidos;


    public Resource(int minimo,
                    int maximo,
                    String nombre,
                    boolean controlStock,
                    WaitMode modoEspera,
                    boolean sincronizado) {
        this.maximo = maximo;
        this.minimo = minimo;
        this.stock = minimo;
        this.nombre = nombre;
        this.controlStock = controlStock;
        this.modoEspera = modoEspera;
        this.sincronizado = sincronizado;
        this.producidos = 0;
        this.consumidos = 0;
    }

    public void producir() throws InterruptedException {
        if (!sincronizado) {
            producirSinSync();
        } else if (modoEspera == WaitMode.SLEEP) {
            producirSleep();
        } else {
            producirWait();
        }
    }

    private synchronized void producirWait() throws InterruptedException {
        while (controlStock && stock >= maximo) {
            wait();
        }
        stock++;
        producidos++;
        notifyAll();
    }

    private void producirSleep() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!controlStock || stock < maximo) {
                    stock++;
                    producidos++;
                    return;
                }
            }
            Thread.sleep(50);
        }
    }

    private void producirSinSync() throws InterruptedException {
        while (controlStock && stock >= maximo) {   // comprobar SIN candado
            Thread.sleep(50);
        }
        int leido = stock;          // 1. leer
        leido = leido + 1;          // 2. calcular
        stock = leido;              // 3. escribir
        contarProduccion();         // el contador SÍ protegido
    }

    private synchronized void contarProduccion() {
        producidos++;
    }

    public void consumir() throws InterruptedException {
       if (!sincronizado){
           consumirSinSync();
       } else if (modoEspera == WaitMode.SLEEP) {
            consumirSleep();
        } else {
            consumirWait();
        }
    }
    private void consumirSinSync() throws InterruptedException{
        while (controlStock && stock <= minimo) {
            Thread.sleep(50);
        }
        int leido = stock;
        leido = leido-1;
        stock = leido;
        contarConsumicion();
    }
    private synchronized void contarConsumicion(){
        consumidos++;
    }
    private synchronized void consumirWait() throws InterruptedException {
        while (controlStock && stock <= minimo) {
            wait();
        }
        stock--;
        consumidos++;
        notifyAll();
    }

    private void consumirSleep() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!controlStock || stock > minimo) {
                    stock--;
                    consumidos++;
                    return;
                }
            }
            Thread.sleep(50);
        }
    }

    public synchronized int getStock() {
        return stock;
    }

    public String getNombre() {
        return nombre;
    }
    public synchronized int getStockEsperado(){
        return  minimo+producidos-consumidos;
    }
    public boolean hayDescuadre(){
        return getStockEsperado() != stock;
    }
    public boolean fueraDeLimites(){
        return stock < minimo || stock >maximo;
    }
}
