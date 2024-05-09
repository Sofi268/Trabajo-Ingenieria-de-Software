package Interfaz;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Barra {

    private Rectangle borde;
    private Rectangle relleno;
    private ImageView iconoView;

    public Barra(String estadistica, double inicio, ImageView icono) {
        borde = new Rectangle(35, 100);
        relleno = new Rectangle(35, inicio);
        iconoView = icono;

        borde.setFill(null);
        borde.setStroke(Color.WHITE);
        setRellenoColor(estadistica);
        setBarraLayoutX(0); // Posición predeterminada
        setBarraLayoutY(0, 0); // Posición predeterminada

        applyMask(); // Aplicar máscara del icono a la barra
    }

    public void setRellenoColor(String estadistica) {
        switch (estadistica) {
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
        applyMask(); // Aplicar máscara después de cambiar la altura
    }

    public double getBarraAltura() {
        return relleno.getHeight();
    }

    public void setBarraLayoutX(double x) {
        borde.setX(x);
        relleno.setX(x);
        iconoView.setX(x); // Ajustar la posición X del icono
    }

    public void setBarraLayoutY(double bordeY, double rellenoY) {
        borde.setY(bordeY);
        relleno.setY(rellenoY);
        iconoView.setY(rellenoY); // Ajustar la posición Y del icono
    }

    public Rectangle getBorde() {
        return borde;
    }

    public Rectangle getRelleno() {
        return relleno;
    }

    public void animacionCambioAltura(double nuevaAltura) {
        Duration duration = Duration.seconds(0.5);
        int fps = 60; // frames por segundo
        double totalCambio = nuevaAltura - relleno.getHeight();
        double cambioPorFrame = totalCambio / (duration.toMillis() / 1000 * fps);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.0 / fps), e -> {
                    // incremento
                    if (relleno.getHeight() < nuevaAltura && totalCambio > 0) {
                        double nuevaAlturaBarra = Math.min(relleno.getHeight() + cambioPorFrame, nuevaAltura);
                        relleno.setHeight(nuevaAlturaBarra);
                        applyMask(); // Aplicar máscara después de cambiar la altura
                    }
                    // decremento
                    if (relleno.getHeight() > nuevaAltura && totalCambio < 0) {
                        double nuevaAlturaBarra = Math.max(relleno.getHeight() + cambioPorFrame, nuevaAltura);
                        relleno.setHeight(nuevaAlturaBarra);
                        applyMask(); // Aplicar máscara después de cambiar la altura
                    }
                })
        );

        timeline.setCycleCount((int) (duration.toMillis() / 1000 * fps));
        timeline.play();
    }

    private void applyMask() {
        Image icono = iconoView.getImage();
        Shape mascara = new Rectangle(iconoView.getFitWidth(), iconoView.getFitHeight()); // Crear una máscara de rectángulo inicialmente del mismo tamaño que la imagen
        mascara = Shape.subtract(mascara, new ImageView(icono)); // Substraer la forma del icono de la máscara
        mascara.setLayoutX(iconoView.getX());
        mascara.setLayoutY(iconoView.getY());
        relleno.setClip(mascara); // Aplicar la máscara a la barra
    }
}
