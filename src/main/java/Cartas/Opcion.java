package Cartas;

import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Juego.Personaje;

public class Opcion {
	
	private String informacion;
	private int niveles[];
	private String idSiguiente;
	
	public Opcion() {
		informacion = null;
		this.niveles = new int[4];
	}
	
	public void modificarEstadisticas(Personaje personaje) throws NivelExcedidoException, NivelInvalidoException {
		personaje.modificarEstadisticas(niveles);
	}
	
	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}
	
	public String getInformacion() {
		return informacion;
	}
	
	public int[] getNiveles() {
		return niveles;
	}
	
	public void setNiveles(int niveles[]) {
		this.niveles = niveles;
	}

	public String getIdSiguiente() {
		return idSiguiente;
	}

	public void setIdSiguiente(String idSiguiente) {
		this.idSiguiente = idSiguiente;
	}
}