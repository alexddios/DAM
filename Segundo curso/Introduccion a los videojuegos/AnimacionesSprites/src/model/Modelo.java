package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modelo: guarda las hojas de sprites (por id) y las animaciones creadas a partir de ellas.
 */
public class Modelo implements IModel{
    private Map<String ,SpriteSheet> hojas;          // id → hoja (cada hoja se carga una sola vez)
    private List<SpriteAnimation> animaciones;       // animaciones en orden de creación

    public Modelo(){
        this.hojas = new HashMap<>();
        this.animaciones = new ArrayList<>();
    }

    /**
     * Carga una hoja y la guarda con su id.
     * @throws IllegalArgumentException si ya hay una hoja con ese id (put la sobrescribiría sin avisar)
     */
    @Override
    public void loadSS(String id, String ruta, int columnas, int filas,int pasos){
        if (hojas.containsKey(id)){
            throw new IllegalArgumentException("Ya existe una hoja con este id: "+id);
        }
        SpriteSheet hoja = new SpriteSheet(ruta, columnas, filas, pasos);
        hojas.put(id,hoja);
    }

    /**
     * Crea una animación que usa la hoja id y la añade a la lista.
     * @throws IllegalArgumentException si no hay ninguna hoja con ese id
     */
    @Override
    public void crearAnimacion(String id, int ms){
        if(!hojas.containsKey(id)){
            throw new IllegalArgumentException("La hoja con el id "+ id + " no existe");
        }
        SpriteAnimation animation = new SpriteAnimation(hojas.get(id),ms);
        animaciones.add(animation);
    }

    /**
     * Devuelve la lista de animaciones.
     * Ojo: las animaciones deben crearse todas antes de arrancar la vista, porque su hilo
     * recorre esta lista y añadir elementos mientras tanto lanzaría ConcurrentModificationException.
     */
    @Override
    public List<SpriteAnimation> getAnimaciones() {
        return animaciones;
    }

}
