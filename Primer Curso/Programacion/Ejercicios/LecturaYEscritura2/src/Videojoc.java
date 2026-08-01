public class Videojoc {
    private String titol;
    private String genere;
    private int hores;

    public Videojoc(String titol,String genere,int hores){
        this.titol=titol;
        this.genere=genere;
        this.hores=hores;
    }

    public String lineaCSV(){
        return String.format("%s,%s,%d", this.titol, this.genere, this.hores);
    }

    public static Videojoc objecteCSV(String linea){
        String[] parts = linea.split(",");
        return new Videojoc(parts[0],parts[1],Integer.parseInt(parts[2]));
    }

    public String getTitol() {
        return titol;
    }

    public String getGenere() {
        return genere;
    }

    public int getHores() {
        return hores;
    }
}
