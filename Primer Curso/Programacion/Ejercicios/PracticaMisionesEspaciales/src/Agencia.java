import java.util.ArrayList;

public class Agencia {
    private final String nom;
    private final ArrayList<Missio> missio;

    public Agencia(String nom){
        this.nom=nom;
        this.missio=new ArrayList<>();
    }
    public boolean afegirMissio(Missio missio){
        for (Missio value : this.missio) {
            if (value.equals(missio)) {
                System.out.println("Ja hi ha aquesta missió");
                return false;
            }

        }
        this.missio.add(missio);
        return true;
    }
    public Missio cercarMissio(String codi){
        for (Missio value : this.missio) {
            if (value.codi().equals(codi)) {
                return value;
            }
        }
        return null;
    }
    public boolean llevarMissio(String codi){
        for(int i=0; i<this.missio.size(); i++){
            if(this.missio.get(i).codi().equals(codi)){
                if (this.missio.get(i).estat() == EstatMissio.EN_CURS) {
                    return false;
                }
                this.missio.remove(i);
                return true;
            }
        }
        return false;
    }
}
