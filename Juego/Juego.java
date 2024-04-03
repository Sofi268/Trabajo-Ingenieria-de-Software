package Juego;


public class Juego {
	
	int muertes;
	Historia historia;
	Personaje personaje;
	
	public Juego(){
		muertes = 0;
		historia = new Historia();
		personaje = null;
	}
	
	public void comenzarJuego(String opcion) {
		personaje = new Personaje();
		continuarJugando();
	}
	
	public void continuarJugando() {
		historia.jugarCarta(muertes); //muestra carta
		//elige 1 opcion de las cartas
		//bucle de cartas 
		// captura excepcion si supera uno de los dos niveles y termina el juego
		morir();
		
	}
	private void morir() {
		muertes += 1;
		personaje = null;
	}
	
	public static void main(String[] args) {
		

	}

}
