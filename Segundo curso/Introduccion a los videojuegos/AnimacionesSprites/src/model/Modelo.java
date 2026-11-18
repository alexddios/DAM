package model;

import assets.Catalogo;
import assets.HojaInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modelo: guarda las animaciones y su estado (qué hoja usan, en qué frame van, a qué velocidad).
 * No carga ni guarda imágenes: de cada hoja solo necesita sus pasos, que saca del Catalogo.
 */
public class Modelo implements IModel{
    private final Catalogo catalogo;                 // datos de las hojas; se recibe desde fuera (inyección)
    private List<SpriteAnimation> animaciones;       // animaciones en orden de creación

    /** @param catalogo catálogo de hojas ya rellenado; lo crea Main y se lo pasa al modelo */
    public Modelo(Catalogo catalogo){
        this.catalogo = catalogo;
        this.animaciones = new ArrayList<>();
    }

    /**
     * Crea una animación que usa la hoja id y la añade a la lista.
     * Del catálogo solo toma los pasos de la hoja; la animación guarda el id para que la vista sepa qué pintar.
     * @throws IllegalArgumentException si el catálogo no tiene ninguna hoja con ese id
     */
    @Override
    public void crearAnimacion(String id, int ms){
        HojaInfo info = catalogo.get(id);   // si el id no está registrado, el propio catálogo lanza la excepción
        if(info == null){                   // protección extra: con el Catalogo actual nunca llega a cumplirse
            throw new IllegalArgumentException("La hoja con el id "+ id + " no existe");
        }
        SpriteAnimation animation = new SpriteAnimation(id, info.pasos(), ms);
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
