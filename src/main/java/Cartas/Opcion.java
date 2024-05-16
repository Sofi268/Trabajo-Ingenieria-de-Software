package Cartas;

import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Juego.Personaje;

public class Opcion {
	
	private String informacion;
	private int niveles[];
	
	public Opcion() {
		informacion = null;
		niveles = null;
	}
	
	public void modificarEstadisticas(Personaje personaje) throws NivelExcedidoException, NivelInvalidoException {
		personaje.getEstadisticas().modificarTierra(niveles[0]);
		personaje.getEstadisticas().modificarAgua(niveles[1]);
		personaje.getEstadisticas().modificarFuego(niveles[2]);
		personaje.getEstadisticas().modificarAire(niveles[3]);
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
}
