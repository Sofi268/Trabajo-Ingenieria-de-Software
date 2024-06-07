package Interfaz;

import java.util.HashMap;

import Observer_Estadisticas.Observer;

public class ConjuntoBarras implements Observer{

	private Barra barraTierra;
	private Barra barraAgua;
	private Barra barraFuego;
	private Barra barraAire;
	private double alturaInicial;
	
	public ConjuntoBarras(double inicio) {
		barraTierra = new Barra("tierra" ,inicio);
		barraAgua = new Barra("agua" ,inicio);
		barraFuego = new Barra("fuego" ,inicio);
		barraAire = new Barra("aire" ,inicio);
		alturaInicial = inicio;
	}
	
	public Barra getBarraTierra() {
		return barraTierra;
	}

	public Barra getBarraAgua() {
		return barraAgua;
	}

	public Barra getBarraFuego() {
		return barraFuego;
	}

	public Barra getBarraAire() {
		return barraAire;
	}
	
	//separación del 6% entre barras
	public void setBarrasLayoutX(double initialX, double screenWidth) {
		barraTierra.setBarraLayoutX(initialX);
		barraAgua.setBarraLayoutX(initialX + screenWidth* 0.06);
		barraFuego.setBarraLayoutX(initialX + screenWidth* 0.12);
		barraAire.setBarraLayoutX(initialX + screenWidth * 0.18);
	}
	
	public void setBarrasLayoutY(double y) {
		double posRelleno = y + alturaInicial; // Posición Y para barra relleno, para estar al inferior de barra borde
		barraTierra.setBarraLayoutY(y, posRelleno);
		barraAgua.setBarraLayoutY(y, posRelleno);
		barraFuego.setBarraLayoutY(y, posRelleno);
		barraAire.setBarraLayoutY(y, posRelleno);
	}
	
	public void update(HashMap<String, Integer> estadisticas) {
		nuevasAlturas(
				estadisticas.get("tierra"),
				estadisticas.get("agua"),
				estadisticas.get("fuego"),
				estadisticas.get("aire")
		);
	}
	
	// cambio de alturas en todas las barras
	public void nuevasAlturas(double tierra, double agua, double fuego, double aire) {
		barraTierra.animacionCambioAltura(tierra);
		barraAgua.animacionCambioAltura(agua);
		barraFuego.animacionCambioAltura(fuego);
		barraAire.animacionCambioAltura(aire);
	}
	
	public void resetBarras() {
		barraTierra.setBarraAltura(alturaInicial);
		barraFuego.setBarraAltura(alturaInicial);
		barraAgua.setBarraAltura(alturaInicial);
		barraAire.setBarraAltura(alturaInicial);
	}
}
