package Juego;
import java.util.HashMap;

import Estadisticas.EstadisticasConjuntas;

public class Personaje {
	private int anios;
	private EstadisticasConjuntas estadisticas;
	
	public Personaje() {
		anios = 0;
		estadisticas = new EstadisticasConjuntas();
	}
	
	public void setAnios(int anios) {
		this.anios = anios;
	}
	
	public int getAnios() {
		return anios;
	}
	
	public EstadisticasConjuntas getEstadisticas() {
		return estadisticas;
	}
	
	public void aumentarAnios(int aniosPasados) {
		anios += aniosPasados;
	}
	
	public HashMap<String, Integer> getNiveles(){
		return estadisticas.getEstadisticasNiveles();
	}
	
}
