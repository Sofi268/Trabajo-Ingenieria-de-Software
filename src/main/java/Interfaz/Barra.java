package Interfaz;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.util.Duration;

public class Barra {

	private Rectangle borde;
	private Rectangle relleno;

	private Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	
	public Barra(String estadistica, double inicio) {
		borde = new Rectangle(screenSize.getWidth()*0.025 , screenSize.getHeight()*0.1);
		relleno = new Rectangle(screenSize.getWidth()*0.025 ,inicio);
		borde.setFill(null);
		borde.setStroke(Color.WHITE);
		setRellenoColor(estadistica);
	}
	
	public void setRellenoColor(String estadistica) {
		switch(estadistica) {
			case "tierra":
				relleno.setFill(Color.web("1d6314"));
				break;
			case "agua":
				relleno.setFill(Color.web("0d286e"));
				break;
			case "fuego":
				relleno.setFill(Color.web("970a08"));
				break;
			case "aire":
				relleno.setFill(Color.web("3294ae"));
				break;
		}
	}
	
	public void setBarraAltura(double altura) {
		relleno.setHeight(altura);
	}
	
	public double getBarraAltura() {
		return relleno.getHeight();
	}
	
	//ambas barras con mismo x
	public void setBarraLayoutX(double x) {
		borde.setLayoutX(x);
		relleno.setLayoutX(x);
	}
	
	public void setBarraLayoutY(double bordeY, double rellenoY) {
		borde.setY(bordeY);
		relleno.setY(rellenoY);
	}
	
	public Rectangle getBorde() {
		return borde;
	}
	
	public Rectangle getRelleno() {
		return relleno;
	}
	
	public void animacionCambioAltura(double nuevaAltura) {
		Duration duration = Duration.seconds(0.5);
        int fps = 60; //frames per second
        double totalCambio = nuevaAltura - relleno.getHeight();
        double cambioPorFrame = totalCambio / (duration.toMillis() / 1000 * fps);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1.0 / fps), e -> {
            	//incremento
                if (relleno.getHeight() < nuevaAltura && totalCambio > 0) {
                	double alturaRelativa = nuevaAltura * 0.001 * screenSize.getHeight();
                	double nuevaAlturaBarra = Math.min(relleno.getHeight() + cambioPorFrame, alturaRelativa);
                    double nuevaYBarra = relleno.getY() - (nuevaAlturaBarra - relleno.getHeight()); // Ajustar la posición Y
                    relleno.setHeight(nuevaAlturaBarra);
                    relleno.setY(nuevaYBarra);
                }
                //decremento
                if (relleno.getHeight() > nuevaAltura && totalCambio < 0) {
                	double nuevaAlturaBarra = Math.max(relleno.getHeight() + cambioPorFrame, nuevaAltura);
                    double nuevaYBarra = relleno.getY() + (relleno.getHeight() - nuevaAlturaBarra); // Ajustar la posición Y
                    relleno.setHeight(nuevaAlturaBarra);
                    relleno.setY(nuevaYBarra);
                }
            })
        );
        timeline.setCycleCount((int) (duration.toMillis() / 1000 * fps));
        timeline.play();
	}
}