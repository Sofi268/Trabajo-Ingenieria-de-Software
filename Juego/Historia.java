package Juego;

import Cartas.Carta;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;

public class Historia {
	private static final int ANIO_INICIAL = 0;
	private static final int ANIO_FINAL = 200;

	private int anioActual;
	private Carta[] cartas;
	
	public Historia() {
		anioActual = ANIO_INICIAL;
		cartas = new Carta[ANIO_FINAL];
	}
	
	// ejemplo de carta
	public void cartaUno(Personaje personaje) throws NivelExcedidoException, NivelInvalidoException {
		cartas[0].getOpciones()[0].setInformacion("Esta es la opcion A");
		cartas[0].getOpciones()[0].modificarEstadisticas(personaje, new int[]{0, 10, 20, 0});
		cartas[0].getOpciones()[1].setInformacion("Esta es la opcion B");
		cartas[0].getOpciones()[1].modificarEstadisticas(personaje, new int[]{10, -10, 0, 0});
		cartas[0].setDescripcion("Informacion de la carta");
		
	}
	
	public void aumentarAnio(int anios) {
		anioActual += anios;
	}
	
	public int getAnioActual() {
		return anioActual;
	}
	
	public Carta jugarCarta(int muertes) {
		return cartas[anioActual - (muertes*5)];
	}
	
}
