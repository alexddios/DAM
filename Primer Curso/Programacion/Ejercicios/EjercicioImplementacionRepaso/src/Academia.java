import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Academia {
    private final String codi;
    private  String nom;

    private final List<Curs> cursos;
    private final List<Instrument> instruments;
    private final Map<String,Profesor> professors;

    public Academia(String codi, String nom) {
        this.codi = codi;
        this.nom = nom;
        this.cursos= new ArrayList<>();
        this.instruments= new ArrayList<>();
        this.professors= new HashMap<>();
    }
    public boolean afegirPiano(String codi, String nom, double preuLloguer, int numTecles, boolean esElectronic){
        if(!validarInstrument(codi,nom,preuLloguer)) return false;
        if(numTecles<=0) return false;

        Piano p = new Piano(codi, nom, preuLloguer, numTecles, esElectronic);
        return this.instruments.add(p);

    }
    public boolean afegirGuitarra(String codi,String nom, double preuLloguer,String tipusCordes){
        if(!validarInstrument(codi,nom,preuLloguer)) return false;
        if(tipusCordes == null || tipusCordes.isEmpty()) return false;

        Guitarra g = new Guitarra(codi, nom, preuLloguer, tipusCordes);
        return this.instruments.add(g);
    }
    public boolean afegirCurs(String codi, String nom, Nivell nivell){
        if(!validarCurs(codi,nom,nivell)) return false;

        Curs c = new Curs(codi,nom,nivell);
        return this.cursos.add(c);
    }

    public boolean afegirProfesor(String nom,String dni,String especialitat){
        if(!validarProfesor(nom, dni, especialitat)) return false;

        Profesor p = new Profesor(nom, dni, especialitat);
        professors.put(dni,p);
        return true;
    }

    public Instrument getInstrument(int posicio){
        if(posicio<0 || posicio>=instruments.size()) return null;
        return instruments.get(posicio);
    }
    public Profesor getProfesor(String dni){
        if(!this.professors.containsKey(dni)){
            System.out.printf("No se ha encontrado al  profesor con el dni %s",dni);
            return null;
        }
        return this.professors.get(dni);

    }
    public boolean eliminarProfessor(String dni){
        if(!this.professors.containsKey(dni)){
            System.out.printf("No se ha encontrado al  profesor con el dni %s",dni);
            return false;
        }
        this.professors.remove(dni);
        return true;
    }
    private boolean validarInstrument(String codi,String nom, double preuLloguer){
        if(codi==null || codi.isEmpty() || nom == null || nom.isEmpty()) return false;
        if (preuLloguer<=0) return false;
        for(Instrument i : this.instruments){
            if(i.getCodi().equals(codi)){
                return false;
            }
        }
        return true;
    }
    private boolean validarCurs(String codi,String nom,Nivell nivell){
        if(codi==null || codi.isEmpty() || nom == null || nom.isEmpty() || nivell == null) return false;
        for(Curs c : this.cursos){
            if(c.getCodi().equals(codi)){
                return false;
            }
        }
        return true;
    }
    private boolean validarProfesor(String nom,String dni,String especialitat){
        if(nom == null || nom.isEmpty() || dni == null || dni.isEmpty() || especialitat==null || especialitat.isEmpty()) return false;
        if(this.professors.containsKey(dni)) return false;
        return true;
    }
}
