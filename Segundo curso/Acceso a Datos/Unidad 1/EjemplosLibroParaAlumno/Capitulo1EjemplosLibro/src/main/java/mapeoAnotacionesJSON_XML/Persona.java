package mapeoAnotacionesJSON_XML;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonPropertyOrder ({"edad", "nombre"})
public class Persona {
	@JsonProperty("nombre")
	@JacksonXmlProperty(localName = "nombre")
	private String nombre;

	@JsonProperty("edad")
	@JacksonXmlProperty(localName = "edad")
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
