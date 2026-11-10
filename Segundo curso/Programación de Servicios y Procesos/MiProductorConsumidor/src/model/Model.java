package model;

import config.Config;
import config.Estado;
import config.HiloInfo;
import config.ResourceInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model implements IModel {
    private final List<Thread> hilos;
    private final List<Producer> producers;
    private final List<Consumer> consumers;
    private final List<Resource> resources;
    private volatile Estado estado;
    private final Random random;


    public Model(){
        this.hilos = new ArrayList<>();
        this.producers = new ArrayList<>();
        this.consumers = new ArrayList<>();
        this.resources = new ArrayList<>();
        this.estado = Estado.PARADO;
        this.random = new Random();
    }
    @Override
    public void iniciar(Config config){
        estado= Estado.EN_MARCHA;
        resources.clear();
        for (int i = 0; i < config.numRecursos(); i++) {
            String nombre = String.format("R%d",i+1);
            resources.add(new Resource(config.stockMin(),
                    config.stockMax(),
                    nombre,config.controlStock(),
                    config.modoEspera(),
                    config.sincronizado()));
        }
        producers.clear();
        consumers.clear();
        hilos.clear();
        for (int i = 0; i < config.numProductores(); i++) {
            String nombre = String.format("Productor-%d",i+1);
            int objetivo = config.producirIlimitado() ? Integer.MAX_VALUE : random.nextInt(config.producirMin(),config.producirMax()+1);
            List<Resource> misRecursos = config.productoresFijos() ? List.of(resources.get(i% config.numRecursos())) : resources;
            Producer producer = new Producer(
                   misRecursos ,
                    config.tiempoProducirMin(),
                    config.tiempoProducirMax(),
                    objetivo,
                    nombre);
            producers.add(producer);
            Thread productor = new Thread(producer,nombre);
            hilos.add(productor);
            productor.setDaemon(true);
            productor.start();
        }
        for (int i = 0; i < config.numConsumidores(); i++) {
            String nombre = String.format("Consumidor-%d",i+1);
            int objetivo = config.consumirIlimitado() ? Integer.MAX_VALUE :
                    random.nextInt(config.consumirMin(),config.consumirMax()+1);
            List<Resource> misRecursos = config.consumidoresFijos() ? List.of(resources.get(i% config.numRecursos())) : resources;
            Consumer consumer = new Consumer(
                    misRecursos,
                    config.tiempoConsumirMin(),
                    config.tiempoConsumirMax(),
                    objetivo,
                    nombre);
            consumers.add(consumer);
            Thread consumidor = new Thread(consumer,nombre);
            hilos.add(consumidor);
            consumidor.setDaemon(true);
            consumidor.start();
        }
        Thread vigilante = new Thread(new Watcher(this));
        vigilante.setDaemon(true);
        vigilante.start();

    }
    @Override
    public void detener() {
        estado = Estado.DETENIDO;
        for (Thread hilo : hilos){
            hilo.interrupt();
        }
        for (Thread hilo : hilos){
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    @Override
    public int getContadorProd(){
        int res = 0;
        for (Producer p : producers){
           res += p.getContador();
        }
        return res;
    }
    @Override
    public int getContadorCon(){
        int res =0;
        for (Consumer c : consumers){
            res += c.getContador();
        }
        return res;
    }
    @Override
    public int getStock(){
        int res = 0;
        for (Resource r : resources){
            res += r.getStock();
        }
        return res;
    }
    @Override
    public List<ResourceInfo> getResources(){
        List<ResourceInfo> res = new ArrayList<>();
        for (Resource r : resources){
            res.add(new ResourceInfo(
                    r.getNombre(),
                    r.getStock(),
                    r.getStockEsperado(),
                    r.hayDescuadre(),
                    r.fueraDeLimites()
            ));
        }
        return res;
    }
    @Override
    public List<HiloInfo> getHilos(){
        List<HiloInfo> res = new ArrayList<>();
        for (Producer p : producers){
            res.add(new HiloInfo(
                    p.getNombre(),
                    p.getContador(),
                    p.getObjetivo(),
                    p.getEstadoHilo()
            ));
        }
        for (Consumer c : consumers){
            res.add(new HiloInfo(
               c.getNombre(),
               c.getContador(),
               c.getObjetivo(),
               c.getEstadoHilo()
            ));
        }
        return  res;
    }
    @Override
    public void pausar(){
        this.estado = Estado.EN_PAUSA;
        for (Producer p: producers){
            p.pausar();
        }
        for (Consumer c: consumers){
            c.pausar();
        }
    }
    @Override
    public void reanudar(){
        estado= Estado.EN_MARCHA;
        for (Producer p: producers){
            p.reanudar();
        }
        for (Consumer c: consumers){
            c.reanudar();
        }
    }

    public void esperar() throws InterruptedException {
        for (Thread hilo : hilos){
            hilo.join();
        }
    }
    @Override
    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
