package model;

import config.Config;
import config.Estado;
import config.HiloInfo;
import config.ResourceInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Modelo: crea los recursos y los hilos a partir del Config y permite
 * iniciar, pausar, reanudar y detener la simulación.
 * No conoce ni a la Vista ni al Presenter.
 */
public class Model implements IModel {
    // Se guardan los Thread (para interrupt/join) y también los Runnable
    // (Producer/Consumer) para poder leer sus contadores y su estado.
    private final List<Thread> hilos;
    private final List<Producer> producers;
    private final List<Consumer> consumers;
    private final List<Resource> resources;
    // volatile: lo cambian el hilo de Swing y el vigilante, y lo lee la Vista.
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
        // Cada simulación empieza con recursos y listas nuevos.
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
            // Objetivo propio de cada productor, sorteado dentro del rango.
            int objetivo = config.producirIlimitado() ? Integer.MAX_VALUE : random.nextInt(config.producirMin(),config.producirMax()+1);
            // Fijo: solo su recurso, repartidos en orden con %. Variable: todos.
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
            // daemon: no impide que el programa termine al cerrar la ventana.
            // Tiene que ir ANTES de start().
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
        // Vigilante: detecta cuándo terminan todos y pone FINALIZADO.
        Thread vigilante = new Thread(new Watcher(this));
        vigilante.setDaemon(true);
        vigilante.start();

    }

    /**
     * Detiene la simulación. Primero se marca DETENIDO (para que el vigilante
     * no ponga FINALIZADO), luego se interrumpe a TODOS y después se espera a
     * cada uno con join(). En dos bucles para que terminen en paralelo y no
     * uno detrás de otro.
     */
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
                // No se "traga" la interrupción: se vuelve a marcar el hilo actual.
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

    /** Devuelve una "foto" de cada recurso (record), para que la Vista no toque los Resource reales. */
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

    /** Devuelve una "foto" de cada hilo: nombre, unidades, objetivo y actividad. */
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

    /** Pone el "cartel" de pausa a todos los hilos; cada uno lo mira al empezar su vuelta. */
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

    /** Espera con join() a que terminen todos los hilos. Lo usa el vigilante. */
    public void esperar() throws InterruptedException {
        for (Thread hilo : hilos){
            hilo.join();
        }
    }
    @Override
    public Estado getEstado() {
        return estado;
    }

    /** Solo lo usa el vigilante (mismo paquete); por eso no está en IModel. */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
