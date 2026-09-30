import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParqueNatural {
    private final String id;
    private String nombre;
    private boolean abierto;
    private final Map<String,Actividad> actividades;
    private final List<RecursoParque> recursos;

    public ParqueNatural(String id,String nombre){
        this.id=id;
        this.nombre=nombre;
        this.abierto=true;
        this.actividades=new HashMap<>();
        this.recursos=new ArrayList<>();
    }
    public RecursoParque getRecurso(int posicion){
        return recursos.get(posicion);
    }
    public Actividad buscarActividad(String codigo){
        return actividades.get(codigo);
    }
    public boolean eliminarArctividad(String codigo){
       if(validarActividad(codigo)) return false;
       return true;
    }
    private boolean validarActividad(String codigo){
        if(actividades.containsKey(codigo))return true;
        return false;
    }
}
