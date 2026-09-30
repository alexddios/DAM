
import java.util.HashMap;
import java.util.Map;

public class Actividad {
    private final String codigo;
    private String titulo;
    private int plazasMaximas;
    private final Map<String,Visitante> visitantes;

    public Actividad(String codigo, String titulo, int plazasMaximas){
        this.codigo=codigo;
        this.titulo=titulo;
        this.plazasMaximas=plazasMaximas;
        this.visitantes=new HashMap<>();
    }
    public boolean reservar(Visitante v){
        if(comprobarVisitante(v)) return false;
        visitantes.put(v.getDni(),v);
        return true;
    }
    private boolean comprobarVisitante(Visitante v){
        if(visitantes.containsKey(v.getDni())) return true;
        if (visitantes.size() <= plazasMaximas) return true;
        return false;
    }
}
