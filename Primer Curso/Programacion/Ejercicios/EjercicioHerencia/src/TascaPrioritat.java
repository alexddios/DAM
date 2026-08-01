public class TascaPrioritat extends Tasca {
    private String prioritat;

    public TascaPrioritat(String prioritat, String titol, String descripcio){
        super(titol,descripcio);
        this.prioritat=prioritat;
    }
    @Override
    public void informacio(){
        System.out.println(titol+" "+descripcio);
    }
    @Override
    public void particular(){
        System.out.println(prioritat);
    }
}
