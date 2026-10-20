package mapeoAnotacionesJSON_XML;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "CasaXML")
public class Casa {
	@JsonProperty("personas") // Para JSON
	@JsonInclude(JsonInclude.Include.NON_EMPTY) // Para JSON, no incluir si está vacío
	@JacksonXmlProperty(localName = "persona") //para XML
	@JacksonXmlElementWrapper(useWrapping = false) // Para XML, sin envolver la lista
	private ArrayList<Persona> personas;

	@JsonProperty("direccion")
	@JacksonXmlProperty(localName = "direccion")   
	private String direccion;
	
	public Casa(ArrayList<Persona> personas, String direccion) {		
		this.personas = personas;
		this.direccion = direccion;
	}

	public ArrayList<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(ArrayList<Persona> personas) {
		this.personas = personas;
	}

	public Casa(ArrayList<Persona> personas) {
		this.personas = personas;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Casa() {
	}

	@Override
	public String toString() {
		return "Casa [personas=" + personas + ", direccion=" + direccion + "]";
	}
	
}
