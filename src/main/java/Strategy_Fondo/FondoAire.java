package Strategy_Fondo;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class FondoAire implements FondoStrategy{

	@Override
	public void dibujarFondo(GraphicsContext graficos, Rectangle2D screenSize) {
		double anchoPantalla = screenSize.getWidth();
	    double altoPantalla = screenSize.getHeight();
	    
	    Image imagen = new Image("/Fondos/aire.png");
	    
	    double x = (anchoPantalla - imagen.getWidth()) / 2;
	    double y = (altoPantalla - imagen.getHeight()) / 2;
	    
	    graficos.drawImage(imagen, x, y);
	}

	@Override
	public void dibujarFondoCarta(GraphicsContext graficos, Rectangle2D screenSize) {
		// Configura el tamaño de la pantalla
	    double anchoPantalla = screenSize.getWidth();
	    double altoPantalla = screenSize.getHeight();
	    
	    // Crea el rectángulo central
	    double anchoRectanguloCentral = anchoPantalla * 0.35; // Establece un ancho del 35%
	    double altoRectanguloCentral = altoPantalla;          // Establece el alto igual al alto de la pantalla
	    double xCentral = (anchoPantalla - anchoRectanguloCentral) / 2; // Centra el rectángulo
	    double yCentral = 0; // Coloca el rectángulo donde inicia la pantalla
	    
	    // Color bordo en hexadecimal
	    String colorHexCentral = "859AA0"; 
	    
	    // Dibuja el rectángulo central en el lienzo
	    graficos.setFill(Color.web(colorHexCentral));
	    graficos.fillRect(xCentral, yCentral, anchoRectanguloCentral, altoRectanguloCentral);
	    
	    // Calcula el ancho de los rectángulos superior e inferior
	    double anchoRectangulos = anchoRectanguloCentral;
	    
	    // Crea el rectángulo oscuro en la parte superior
	    double altoRectanguloSuperior = altoPantalla * 0.20; // Establece un 20% del alto de la pantalla
	    double xSuperior = (anchoPantalla - anchoRectangulos) / 2; // Centra el rectángulo
	    double ySuperior = 0; // Comienza desde el borde superior de la pantalla
	    
	    // Color bordo oscuro en hexadecimal
	    String colorHexSuperior = "617074"; 
	    
	    // Dibuja el rectángulo superior en el lienzo
	    graficos.setFill(Color.web(colorHexSuperior));
	    graficos.fillRect(xSuperior, ySuperior, anchoRectangulos, altoRectanguloSuperior);
	    
	    // Crea el rectángulo oscuro en la parte inferior
	    double altoRectanguloInferior = altoPantalla * 0.10; // Establece un 10% del alto de la pantalla
	    double xInferior = (anchoPantalla - anchoRectangulos) / 2; // Centra el rectángulo
	    double yInferior = altoPantalla - altoRectanguloInferior; // Comienza desde la parte inferior de la pantalla
	    
	    // Dibuja el rectángulo inferior en el lienzo
	    graficos.setFill(Color.web(colorHexSuperior));
	    graficos.fillRect(xInferior, yInferior, anchoRectangulos, altoRectanguloInferior);
	}

}
