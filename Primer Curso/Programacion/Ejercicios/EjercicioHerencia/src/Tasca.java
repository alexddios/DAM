public class Tasca {
    protected String titol;
    protected String descripcio;

    public Tasca(String titol,String descripcio){
        this.titol=titol;
        this.descripcio=descripcio;

    }

    public void informacio(){
        System.out.println(titol+" "+descripcio);
    }

    void particular(){
        System.out.println("No té particularitat");
    }

}
