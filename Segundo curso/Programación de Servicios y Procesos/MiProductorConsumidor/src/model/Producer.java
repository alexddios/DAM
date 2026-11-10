package model;

import config.EstadoHilo;

import java.util.List;
import java.util.Random;

public class Producer implements Runnable{
    private final List<Resource> resources;
    private final int tiempoMin;
    private final int tiempoMax;
    private final Random random;
    private volatile int contador;
    private volatile boolean pausado;
    private final int objetivo;
    private volatile EstadoHilo estadoHilo;
    private final String nombre;


    public Producer(List<Resource> resources,int tiempoMin, int tiempoMax, int objetivo, String nombre) {
        this.resources = resources;
        random = new Random();
        this.tiempoMin = tiempoMin;
        this.tiempoMax = tiempoMax;
        this.contador=0;
        this.pausado = false;
        this.objetivo =objetivo;
        this.estadoHilo = EstadoHilo.PAUSA;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try{
            while (contador < objetivo){
                while (pausado) {
                    this.estadoHilo = EstadoHilo.PAUSA;
                    Thread.sleep(50);

                }
                this.estadoHilo = EstadoHilo.TRABAJANDO;
                Thread.sleep(random.nextInt(tiempoMin,tiempoMax+1));
                this.estadoHilo = EstadoHilo.ESPERANDO;

                Resource elegido = resources.get(random.nextInt(resources.size()));
                elegido.producir();
                this.contador++;
//                System.out.printf("%s -> %s: %d %n",Thread.currentThread().getName(),elegido.getNombre(),elegido.getStock());

            }
            this.estadoHilo = EstadoHilo.ACABADO;
            System.out.printf("%s ha terminado %n",Thread.currentThread().getName());
        }catch (InterruptedException e){
            System.out.printf("%s ha terminado %n",Thread.currentThread().getName());
            this.estadoHilo=EstadoHilo.ACABADO;
        }
    }
    public int getContador(){
        return contador;
    }
    public void pausar(){
        this.pausado = true;
    }
    public void reanudar(){
        this.pausado = false;
    }
    public EstadoHilo getEstadoHilo(){
        return estadoHilo;
    }
    public String getNombre(){
        return this.nombre;
    }

    public int getObjetivo() {
        return objetivo == Integer.MAX_VALUE ? -1 : objetivo ;
    }
}
