package ficherosJSON;

public class Empleado {

	private int id;
	private String apellido;
	private int dep;
	private double salario;

	public Empleado() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Empleado(int id, String apellido, int dep, double salario) {
		super();
		this.id = id;
		this.apellido = apellido;
		this.dep = dep;
		this.salario = salario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getDep() {
		return dep;
	}

	public void setDep(int dep) {
		this.dep = dep;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	@Override
	public String toString() {		
		return String.format("Id: %d, Apellido: %s, Departamento: %d, Salario: %,9.2f", id, apellido, dep,
				salario);
	}

}
