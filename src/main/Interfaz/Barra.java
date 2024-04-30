package Interfaz;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Barra {

	private Rectangle borde;
	private Rectangle relleno;
	
	public Barra(String estadistica, double inicio) {
		borde = new Rectangle(35 ,100);
		relleno = new Rectangle(35 ,inicio);
		
		borde.setFill(null);
		borde.setStroke(Color.WHITE);
		setRellenoColor(estadistica);
	}
	
	public void setRellenoColor(String estadistica) {
		switch(estadistica) {
			case "oro":
				relleno.setFill(Color.web("D4AF37"));
				break;
			case "pueblo":
				relleno.setFill(Color.web("82CF20"));
				break;
			case "iglesia":
				relleno.setFill(Color.web("DE5D2D"));
				break;
			case "ejercito":
				relleno.setFill(Color.web("3085F1"));
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
                	double nuevaAlturaBarra = Math.min(relleno.getHeight() + cambioPorFrame, nuevaAltura);
                    double nuevaYBarra = relleno.getY() - (nuevaAlturaBarra - relleno.getHeight()); // Ajustar la posición Y
                    relleno.setHeight(nuevaAlturaBarra);
                    relleno.setY(nuevaYBarra);
                }
                //decremento
                if (relleno.getHeight() > nuevaAltura && totalCambio < 0) {
                	System.out.println(relleno.getHeight());
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
