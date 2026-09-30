public abstract class RecursoParque {
    protected String nombre;
    protected String ubicacion;

    public RecursoParque(String nombre,String ubicacion){
        this.nombre=nombre;
        this.ubicacion=ubicacion;
    }
    public abstract void enseniarInformacion();
}
