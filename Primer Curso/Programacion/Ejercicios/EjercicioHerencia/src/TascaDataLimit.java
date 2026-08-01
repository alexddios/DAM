public class TascaDataLimit extends Tasca {
    private String dataLimit;
    public TascaDataLimit(String dataLimit, String titol, String descripcio){
        super(titol,descripcio);
        this.dataLimit=dataLimit;
    }
    @Override
    public void informacio(){
        System.out.println(titol+" "+descripcio);
    }

    @Override
    public void particular(){
        System.out.println(dataLimit);
    }
}
