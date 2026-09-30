public class Mirador extends RecursoParque{
    private int altitud;
    public Mirador(String nombre, String ubicacion, int altitud) {
        super(nombre, ubicacion);
        this.altitud=altitud;
    }

    @Override
    public void enseniarInformacion() {
        System.out.printf("""
                Nombre: &s
                Ubicación: &s
                Altitud: &d
                """,nombre,ubicacion,altitud);
    }
}
