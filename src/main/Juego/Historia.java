package Juego;

import Cartas.Carta;
import Cartas.Opcion;
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
	
	public void llenarCartas(){
	    
		for (int i = 0; i < cartas.length; i++) {
	   		// Crear una nueva instancia de Carta y asignarla al arreglo en la posición i
	   		cartas[i] = new Carta();
	   		// Establecer la descripción de la carta
	   		cartas[i].setDescripcion("Informacion de la carta");
	        // Crear las opciones A y B
	        Opcion opcionA = new Opcion();
	        opcionA.setInformacion("Esta es la opción A");
	        opcionA.setNiveles(new int[]{10, 0, 0, -10});
	        Opcion opcionB = new Opcion();
	        opcionB.setInformacion("Esta es la opción B");
	        opcionB.setNiveles(new int[]{-10, 0, 20, 0});


	        // Asignar las opciones a la carta en la posición i
	        cartas[i].setOpcionA(opcionA);
	        cartas[i].setOpcionB(opcionB);
	        }
	}
	
	public void aumentarAnio(int anios) {
		anioActual += anios;
	}
	
	public int getAnioActual() {
		return anioActual;
	}
	
	public Carta jugarCarta(int muertes) {
		return getCartaActual(muertes);
	}
	
	public Carta getCartaActual(int muertes) {
		return cartas[anioActual - (muertes*5)];
	}
	
	public Carta getCartaX(int i) {
		return cartas[i];
	}
	
	public void elegirOpcionDeCarta(int muertes, String opcionElegida, Personaje personaje) throws NivelExcedidoException, NivelInvalidoException {
	    Carta cartaActual = getCartaActual(muertes);
	    cartaActual.elegirOpcion(personaje, cartaActual.getOpciones()[0].getNiveles(), cartaActual.getOpciones()[1].getNiveles(), opcionElegida);
	   
	}

	public Carta[] getCartas() {
		return cartas;
	}
	
}
