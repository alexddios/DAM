package datos;

public class Cliente  {
	
	private int id;
	private String nombre;
	private int viajescontratados; //num viajes contratados
	private double importetotal; //importe total de los viajes realizados

	public Cliente(int id, String nombre, int viajesrealizados, double importetotal) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.viajescontratados = viajesrealizados;
		this.importetotal = importetotal;
	}
	
	public Cliente() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getViajescontratados() {
		return viajescontratados;
	}

	public void setViajescontratados(int viajescontratados) {
		this.viajescontratados = viajescontratados;
	}

	public double getImportetotal() {
		return importetotal;
	}

	public void setImportetotal(double importetotal) {
		this.importetotal = importetotal;
	}

	@Override
	public String toString() {
		return "Cliente: " + id + ", " + nombre + ", " + viajescontratados + ", "
				+ importetotal ;
	}

	
}
