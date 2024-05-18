package Strategy_Fondo;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public interface FondoStrategy {
    void dibujarFondo(GraphicsContext graficos, Rectangle2D screenSize);
    void dibujarFondoCarta(GraphicsContext graficos, Rectangle2D screenSize);
}
