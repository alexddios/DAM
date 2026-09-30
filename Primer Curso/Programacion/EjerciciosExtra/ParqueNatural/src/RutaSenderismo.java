public class RutaSenderismo extends RecursoParque{
    private double distanciakm;
    private String dificultad;

    public RutaSenderismo(String nombre,String ubicacion,double distanciakm,String dificultad){
        super(nombre,ubicacion);
        this.distanciakm=distanciakm;
        this.dificultad=dificultad;
    }

    @Override
    public void enseniarInformacion() {
        System.out.printf("""
                Nombre: &s
                Ubicacion: &s
                Distancia: &d
                Dificultad: &s
                """,nombre,ubicacion,distanciakm,dificultad);
    }
}
