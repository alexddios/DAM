package datos;

import java.io.Serializable;

public class Reserva implements Serializable{

	private static final long serialVersionUID = 1L;
	int idviaje;           //id del viaje
	int idcliente;         //id del cliente
	int plazas;            //numero de plazas
	
	//constructores y metodos get y set
	public Reserva() {
		super();
	
	}

	public Reserva(int idviaje, int idcliente, int plazas) {
		super();
		this.idviaje = idviaje;
		this.idcliente = idcliente;
		this.plazas = plazas;
	}

	public int getIdviaje() {
		return idviaje;
	}

	public void setIdviaje(int idviaje) {
		this.idviaje = idviaje;
	}

	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	@Override
	public String toString() {
		return "Reserva [idviaje=" + idviaje + ", idcliente=" + idcliente + ", plazas=" + plazas + "]";
	}

	

}
