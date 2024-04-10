package Cartas;

import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Juego.Personaje;

public class Opcion {
	
	private String informacion;
	
	public Opcion() {
		informacion = null;
	}
	
	public void modificarEstadisticas(Personaje personaje, int niveles[]) throws NivelExcedidoException, NivelInvalidoException {
		personaje.getEstadisticas().modificarEjercito(niveles[0]);
		personaje.getEstadisticas().modificarIglesia(niveles[1]);
		personaje.getEstadisticas().modificarOro(niveles[2]);
		personaje.getEstadisticas().modificarPueblo(niveles[3]);
	}
	
	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}
	
	public String getInformacion() {
		return informacion;
	}
}
