package Interfaz;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Icono {
	private Image imagen;
	private ImageView imagenView;
	
	public Icono(String imagenPath, double width, double heigth) {
		imagen = new Image(imagenPath);
		imagenView = new ImageView(imagen);
		setWidth(width);
		setHeigth(heigth);
	}
	
	public void setWidth(double width) {
		imagenView.setFitWidth(width);
	}
	
	public void setHeigth(double height) {
		imagenView.setFitHeight(height);
	}
	
	public void setX(double x) {
		imagenView.setX(x);
	}
	
	public void setY(double y) {
		imagenView.setY(y);
	}
	
	public ImageView getImageView() {
		return imagenView;
	}
	
}
