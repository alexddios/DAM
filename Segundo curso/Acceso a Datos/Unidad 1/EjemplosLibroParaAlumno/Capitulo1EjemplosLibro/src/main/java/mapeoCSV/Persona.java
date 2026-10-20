package mapeoCSV;

import com.opencsv.bean.CsvBindByName;

public class Persona {
	
	@CsvBindByName(column = "Nombre de la Persona") // Mapea este campo al CSV	    
	private String nombre;	   
	
	@CsvBindByName(column = "Edad de la Persona") // Mapea este campo al CSV
	private int edad;
	
	public Persona() {
	}

	// Constructor
	public Persona(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}

	// Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
	

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", edad=" + edad + "]";
	}
	
	
}
