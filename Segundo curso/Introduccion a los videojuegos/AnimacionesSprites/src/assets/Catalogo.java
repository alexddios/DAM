package assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Catálogo de hojas de sprites: qué hojas existen y cómo son (id → ruta, columnas, filas, pasos).
 * Solo guarda datos, ninguna imagen. Lo rellena Main y se lo pasa (inyección) al modelo,
 * que saca de aquí los pasos, y a la vista, que carga y recorta las imágenes.
 */
public class Catalogo {
    private final Map<String,HojaInfo> assets;   // id de la hoja → sus datos

    public Catalogo(){
        this.assets = new HashMap<>();
    }

    /**
     * Añade una hoja al catálogo.
     * @throws IllegalArgumentException si ya hay una hoja con ese id (put la sobrescribiría sin avisar)
     */
    public void registrar(String id, String ruta, int columnas, int filas, int pasos){
        if (assets.containsKey(id)){
            throw new IllegalArgumentException("Ya existe una hoja con este id: "+id);
        }
        HojaInfo h = new HojaInfo(ruta, columnas, filas, pasos);
        assets.put(id,h);
    }

    /**
     * Datos de la hoja con ese id.
     * @throws IllegalArgumentException si no hay ninguna hoja registrada con ese id
     */
    public HojaInfo get(String id){
        if(!assets.containsKey(id)){
            throw new IllegalArgumentException("La hoja con el id "+ id + " no existe");
        }
        return assets.get(id);
    }

    /** Todos los ids registrados (una copia: cambiarla no afecta al catálogo). La vista los recorre para cargar las hojas. */
    public ArrayList<String> getAssetIds() {
        return new ArrayList<String>(this.assets.keySet());
    }
}
