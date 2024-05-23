package Interfaz;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class FlipCarta extends StackPane {
    private Group carta;
    private Rectangle reversoCarta;
    private Group frenteCarta;
    private boolean isFrontShowing;

    public FlipCarta(Group carta, Rectangle reversoCarta, Group frenteCarta) {
        this.reversoCarta = reversoCarta;
        this.frenteCarta = frenteCarta;
        this.carta = carta;
        this.getChildren().addAll(carta);
        
        this.isFrontShowing = false;

        // Establece la visibilidad inicial de las caras
        reversoCarta.setVisible(true);
        frenteCarta.setVisible(false);

        
    }

    public void flip() {
        RotateTransition rotar = new RotateTransition(Duration.millis(250), carta);
        rotar.setAxis(Rotate.Y_AXIS);
        rotar.setInterpolator(Interpolator.LINEAR);
        rotar.setCycleCount(1);

        if (!isFrontShowing) {
            rotar.setFromAngle(180);
            rotar.setToAngle(270);
            rotar.setOnFinished(event -> {
                frenteCarta.setVisible(true);
                reversoCarta.setVisible(false);
                RotateTransition rotar2 = new RotateTransition(Duration.millis(250), carta);
                rotar2.setAxis(Rotate.Y_AXIS);
                rotar2.setFromAngle(270);
                rotar2.setToAngle(360);
                rotar2.setInterpolator(Interpolator.LINEAR);
                rotar2.setCycleCount(1);
                rotar2.setOnFinished(event2 -> {
                    carta.setRotate(0);
                    isFrontShowing = true;
                });
                rotar2.play();
            });
        } else {
            rotar.setFromAngle(-180);
            rotar.setToAngle(-270);
            rotar.setOnFinished(event -> {
                reversoCarta.setVisible(true);
                frenteCarta.setVisible(false);
                RotateTransition rotar2 = new RotateTransition(Duration.millis(250), carta);
                rotar2.setAxis(Rotate.Y_AXIS);
                rotar2.setFromAngle(-270);
                rotar2.setToAngle(-360);
                rotar2.setInterpolator(Interpolator.LINEAR);
                rotar2.setCycleCount(1);
                rotar2.setOnFinished(event2 -> {
                    carta.setRotate(0); // Restablece la rotación
                    isFrontShowing = false;
                });
                rotar2.play();
            });
        }

        rotar.play();
    }
}
