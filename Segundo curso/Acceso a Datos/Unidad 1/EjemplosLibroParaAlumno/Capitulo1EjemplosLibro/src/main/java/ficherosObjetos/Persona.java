package ficherosObjetos;


import java.io.Serializable;

public class Persona implements Serializable{
	private String nombre;
	private int edad;
	
	public Persona(String nombre,int edad)	{
	  this.nombre=nombre;
	  this.edad=edad;	
	 }
	public Persona() {
	  this.nombre=null;	  
	 }	
	public void setNombre(String nom){nombre=nom;}
	public void setEdad(int ed){edad=ed;}
	
	public String getNombre(){return nombre;}
	public int getEdad(){return edad;}	
	
	public String toString() {
		return "Nombre: " + nombre +", edad: " + edad;
	}
}//fin Persona
