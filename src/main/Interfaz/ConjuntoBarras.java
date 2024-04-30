package Interfaz;

public class ConjuntoBarras {

	private Barra barraOro;
	private Barra barraPueblo;
	private Barra barraIglesia;
	private Barra barraEjercito;
	private double alturaInicial;
	
	public ConjuntoBarras(double inicio) {
		barraOro = new Barra("oro" ,inicio);
		barraPueblo = new Barra("pueblo" ,inicio);
		barraIglesia = new Barra("iglesia" ,inicio);
		barraEjercito = new Barra("ejercito" ,inicio);
		alturaInicial = inicio;
	}
	
	public Barra getBarraOro() {
		return barraOro;
	}

	public Barra getBarraPueblo() {
		return barraPueblo;
	}

	public Barra getBarraIglesia() {
		return barraIglesia;
	}

	public Barra getBarraEjercito() {
		return barraEjercito;
	}
	
	//separación del 6% entre barras
	public void setBarrasLayoutX(double initialX, double screenWidth) {
		barraOro.setBarraLayoutX(initialX);
		barraPueblo.setBarraLayoutX(initialX + screenWidth* 0.06);
		barraIglesia.setBarraLayoutX(initialX + screenWidth* 0.12);
		barraEjercito.setBarraLayoutX(initialX + screenWidth * 0.18);
	}
	
	public void setBarrasLayoutY(double y) {
		double posRelleno = y + alturaInicial; // Posición Y para barra relleno, para estar al inferior de barra borde
		barraOro.setBarraLayoutY(y, posRelleno);
		barraPueblo.setBarraLayoutY(y, posRelleno);
		barraIglesia.setBarraLayoutY(y, posRelleno);
		barraEjercito.setBarraLayoutY(y, posRelleno);
	}
	
	// cambio de alturas en todas las barras
	public void nuevasAlturas(double oro, double pueblo, double iglesia, double ejercito) {
		barraOro.animacionCambioAltura(oro);
		barraPueblo.animacionCambioAltura(pueblo);
		barraIglesia.animacionCambioAltura(iglesia);
		barraEjercito.animacionCambioAltura(ejercito);
	}
	
	public void resetBarras() {
		barraOro.setBarraAltura(alturaInicial);
		barraIglesia.setBarraAltura(alturaInicial);
		barraPueblo.setBarraAltura(alturaInicial);
		barraEjercito.setBarraAltura(alturaInicial);
	}

}
