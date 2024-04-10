package Juego;


import Cartas.Carta;
import Cartas.Opcion;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Juego extends Application{
	
	private int muertes;
	private Historia historia;
	private Personaje personaje;
	private Carta cartaActual;
	private Group root;
	private Scene escena;
	private Canvas lienzo;
    private Pane interfazCartaPane = new Pane();
	private GraphicsContext graficos;
	Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	
	
	
	public Juego(){
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage ventana) throws Exception {
		
		// Inicializa atributos
		muertes = 0;
		historia = new Historia();
		personaje = null;
		
		interfazPrincipal();
		comenzarJuego();


		fondoTierra();
		fondoCartaTierra();
		ventana.setScene(escena);
		ventana.setTitle("Avatar");
		ventana.show();
		
	}
	
	
	private void comenzarJuego(){
	    personaje = new Personaje();
	    historia.llenarCartas(); // Debes pasar el objeto personaje al método llenarCartas().
	    continuarJugando();
	    
	}

	private void continuarJugando() {
	    int anioActual = historia.getAnioActual();
	    int anioLimite = 200 + muertes * 5;
	    if(historia.getCartaActual(muertes) != null && anioActual < anioLimite) {
	        historia.jugarCarta(muertes); 
	        cartaActual = historia.getCartaActual(muertes); 
	        interfazCartaPane.getChildren().clear();
	        interfazCarta();
	    }
	}


	private void morir() {
		historia.aumentarAnio(5);
		personaje = null;
	    personaje = new Personaje(); 
	    continuarJugando();
	}
	

	private void interfazPrincipal() {
	    root = new Group();
	    escena = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
	    lienzo = new Canvas(screenSize.getWidth(), screenSize.getHeight());
	    root.getChildren().add(lienzo);
	    graficos = lienzo.getGraphicsContext2D();
	    root.getChildren().add(interfazCartaPane);

	}

	public void verAnios() {
		verAniosHistoria(interfazCartaPane);
	    verAniosPersonaje(interfazCartaPane);
	}
	
	private void fondoTierra() {
	    double anchoPantalla = screenSize.getWidth();
	    double altoPantalla = screenSize.getHeight();
	    
	    Image imagen = new Image("\\resources\\Fondos\\tierra.png");
	    
	    double x = (anchoPantalla - imagen.getWidth()) / 2;
	    double y = (altoPantalla - imagen.getHeight()) / 2;
	    
	    graficos.drawImage(imagen, x, y);
	}

	
	private void fondoCartaTierra() {
	    // Configura el tamaño de la pantalla
	    double anchoPantalla = screenSize.getWidth();
	    double altoPantalla = screenSize.getHeight();
	    
	    // Crea el rectángulo central
	    double anchoRectanguloCentral = anchoPantalla * 0.35; // Establece un ancho del 35%
	    double altoRectanguloCentral = altoPantalla;          // Establece el alto igual al alto de la pantalla
	    double xCentral = (anchoPantalla - anchoRectanguloCentral) / 2; // Centra el rectángulo
	    double yCentral = 0; // Coloca el rectángulo donde inicia la pantalla
	    
	    // Color marrón en hexadecimal
	    String colorHexCentral = "542f12"; 
	    
	    // Dibuja el rectángulo central en el lienzo
	    graficos.setFill(Color.web(colorHexCentral));
	    graficos.fillRect(xCentral, yCentral, anchoRectanguloCentral, altoRectanguloCentral);
	    
	    // Calcula el ancho de los rectángulos superior e inferior
	    double anchoRectangulos = anchoRectanguloCentral;
	    
	    // Crea el rectángulo oscuro en la parte superior
	    double altoRectanguloSuperior = altoPantalla * 0.15; // Establece un 15% del alto de la pantalla
	    double xSuperior = (anchoPantalla - anchoRectangulos) / 2; // Centra el rectángulo
	    double ySuperior = 0; // Comienza desde el borde superior de la pantalla
	    
	    // Color marrón oscuro en hexadecimal
	    String colorHexSuperior = "48280f"; 
	    
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


	private void verAniosHistoria(Pane pane) {
        // Obtiene el año actual desde la instancia de Historia
        int anioActual = historia.getAnioActual();

        // Crea el nodo de texto
        Text textoAnio = new Text("Año actual: " + anioActual);
        textoAnio.setFont(Font.font("Rockwell", screenSize.getHeight() * 0.05)); 
        textoAnio.setFill(Color.web("FEE78C")); // CColor en hex

        // Posiciona el texto 
        double x = screenSize.getWidth() * 0.025; // 2.5% de distancia del borde 
        double y = screenSize.getHeight() * 0.1; // 10% de distancia del borde
        textoAnio.setX(x);
        textoAnio.setY(y);

        // Agrega el nodo de texto al pane
        pane.getChildren().add(textoAnio);
    }
	
	private void verAniosPersonaje(Pane pane) {
        // Obtiene los años del personaje
        int aniosPersonaje = personaje.getAnios();

        // Crea el nodo de texto
        Text textoAnios = new Text("Años transcurridos: " + aniosPersonaje);
        textoAnios.setFont(Font.font("Rockwell", screenSize.getHeight() * 0.05));
        textoAnios.setFill(Color.web("FEE78C")); // Color amarillo hex

        // Posiciona el texto a la derecha
        double x = screenSize.getWidth() - textoAnios.getLayoutBounds().getWidth() - screenSize.getWidth() * 0.025; // Alinea el texto al borde derecho con un margen de 2.5% del ancho de la pantalla
        double y = screenSize.getHeight() * 0.1; // 10% de distancia del borde
        textoAnios.setX(x);
        textoAnios.setY(y);

        // Agrega el nodo de texto al pane
        pane.getChildren().add(textoAnios);
    }
	

	private void elegirOpcion(Opcion opcion) {
	    try {
	        if (opcion.equals(historia.getCartaActual(muertes).getOpciones()[0])) {
	            historia.elegirOpcionDeCarta(muertes, "A", personaje);
	        } else if (opcion.equals(historia.getCartaActual(muertes).getOpciones()[1])) {
	            historia.elegirOpcionDeCarta(muertes, "B", personaje);
	        }
	        historia.aumentarAnio(1); // Incrementar el año de la historia
	        personaje.aumentarAnios();
	        continuarJugando();
	        
	    } catch (NivelExcedidoException | NivelInvalidoException e) {
	    	System.out.println("Excepcion");
	        morir();
	    }
	}
	
	
	private void interfazCarta() {
	    // Configura el tamaño de la pantalla
	    double anchoPantalla = screenSize.getWidth();
	    double altoPantalla = screenSize.getHeight();

	    // Calcula el tamaño del cuadrado
	    double ladoCuadrado = Math.min(anchoPantalla, altoPantalla) * 0.45; // Tamaño del cuadrado (45% de la pantalla total)

	    // Calcula la posición inicial del cuadrado
	    double altoCuadrado = altoPantalla * 0.65;
	    double xCuadrado = (anchoPantalla - ladoCuadrado) / 2; // Centra el cuadrado horizontalmente
	    double yCuadrado = altoPantalla - altoCuadrado; // Coloca el cuadrado desde un 65% de altura inferior

	    // Redondea las esquinas un 15%
	    double radioEsquinas = ladoCuadrado * 0.15;

	    // Color del cuadrado en hexadecimal
	    String colorHexCuadradoFondo = "CBAD49";
	    String colorHexCuadrado = "B50204";

	    // Dibuja el cuadrado de fondo
	    Rectangle cuadradoFondo = new Rectangle(xCuadrado, yCuadrado, ladoCuadrado, ladoCuadrado);
	    cuadradoFondo.setFill(Color.web(colorHexCuadradoFondo));
	    cuadradoFondo.setArcWidth(radioEsquinas);
	    cuadradoFondo.setArcHeight(radioEsquinas);

	    // Dibuja el cuadrado de la carta
	    Rectangle cuadrado = new Rectangle(xCuadrado, yCuadrado, ladoCuadrado, ladoCuadrado);
	    cuadrado.setFill(Color.web(colorHexCuadrado));
	    cuadrado.setArcWidth(radioEsquinas);
	    cuadrado.setArcHeight(radioEsquinas);

	    Rectangle cuadradoAtras = new Rectangle(xCuadrado, yCuadrado, ladoCuadrado, ladoCuadrado);
	    cuadradoAtras.setFill(Color.web("000000"));
	    cuadradoAtras.setArcWidth(radioEsquinas);
	    cuadradoAtras.setArcHeight(radioEsquinas);

	    Text texto = new Text();
	    texto.setFill(Color.WHITE); // Color del texto
	    texto.setFont(Font.font("Arial", FontWeight.BOLD, 16));

	    // Posicionamiento del texto
	    texto.setLayoutX(xCuadrado + ladoCuadrado * 0.15);
	    texto.setLayoutY(yCuadrado + ladoCuadrado * 0.225); // Coloca el texto a un 22.5% del borde superior
	    texto.setTextAlignment(TextAlignment.CENTER); //

	    // Establece un ancho fijo
	    texto.setWrappingWidth(ladoCuadrado * 0.7);

	    
	    // Flip animación al inicio
	    RotateTransition rotarDeAtrasHaciaAdelante = new RotateTransition(Duration.seconds(0.3), cuadradoAtras);
	    rotarDeAtrasHaciaAdelante.setAxis(Rotate.Y_AXIS);
	    rotarDeAtrasHaciaAdelante.setFromAngle(0); // Gira desde la posición normal
	    rotarDeAtrasHaciaAdelante.setToAngle(-90); // Gira hacia adelante
	    rotarDeAtrasHaciaAdelante.setOnFinished(event -> cuadradoAtras.setVisible(false));

	    RotateTransition rotarDeAdelanteHaciaAtras = new RotateTransition(Duration.seconds(0.3), cuadrado);
	    rotarDeAdelanteHaciaAtras.setAxis(Rotate.Y_AXIS);
	    rotarDeAdelanteHaciaAtras.setFromAngle(90); // Gira desde la posición invertida
	    rotarDeAdelanteHaciaAtras.setToAngle(0); // Gira hacia atrás
	    rotarDeAdelanteHaciaAtras.setOnFinished(event -> cuadrado.setVisible(true));

	    SequentialTransition flipAnimation = new SequentialTransition(
	            new PauseTransition(Duration.seconds(0.3)),
	            rotarDeAtrasHaciaAdelante,
	            rotarDeAdelanteHaciaAtras
	    );
	    flipAnimation.play();
	    
	    
	    // Aplica la rotación al cuadrado
	    Rotate rotacion = new Rotate();
	    rotacion.setPivotX(xCuadrado + ladoCuadrado / 2); // Establece el punto de rotación X en la mitad del ancho del cuadrado
	    rotacion.setPivotY(yCuadrado + ladoCuadrado); // Establece el punto de rotación Y en la parte inferior del cuadrado
	    cuadrado.getTransforms().add(rotacion);

	    // Eventos según el mouse
	    cuadrado.setOnMousePressed(event -> {
	        cuadrado.setUserData(event.getSceneX());
	    });

	    cuadrado.setOnMouseDragged(event -> {
	        double posicionAnteriorMouseX = (double) cuadrado.getUserData();
	        double mouseX = event.getSceneX();

	        // Calcula la diferencia entre la posición anterior y la posición actual del mouse
	        double difX = mouseX - posicionAnteriorMouseX;

	        // Calcula el ángulo de rotación usando la diferencia
	        double angulo = difX / 10; // Aumenta el ángulo según la distancia

	        // Comprobación para que el ángulo no sea mayor a 20°
	        double rotacionTotal = rotacion.getAngle() + angulo;
	        if (rotacionTotal > 20) {
	            angulo = 20 - rotacion.getAngle();
	        } else if (rotacionTotal < -20) {
	            angulo = -20 - rotacion.getAngle();
	        }

	        // Ajusta el ángulo según el ángulo calculado
	        rotacion.setAngle(rotacion.getAngle() + angulo);

	        // Agrega un desplazamiento según la rotación
	        double desplazamiento = angulo * 2.5;
	        cuadrado.setTranslateX(cuadrado.getTranslateX() + desplazamiento);

	        // Actualiza la posición del mouse 
	        cuadrado.setUserData(mouseX);
	        
	        // Elige el texto segun la rotacion
	        if (angulo >= 1) {
	            texto.setText("         " + cartaActual.getOpciones()[0].getInformacion());
	        } else if (angulo < -1) {
	            texto.setText(cartaActual.getOpciones()[1].getInformacion() + "         ");
	        }
	    });

	    cuadrado.setOnMouseReleased(event -> {
	        double angulo = rotacion.getAngle();
	        // Si la rotación no es de ±20 grados, vuelve a 0
	        if (angulo != 20 && angulo != -20) {
	            Timeline timeline = new Timeline(
	                    new KeyFrame(Duration.seconds(0.1), new KeyValue(rotacion.angleProperty(), 0)),
	                    new KeyFrame(Duration.seconds(0.1), new KeyValue(cuadrado.translateXProperty(), 0))
	                    
	            );
	            texto.setText("");
	            timeline.play();
	        }
	        // Si la rotación está en ±20 grados, cae hacia afuera
	        else if (angulo == 20) {
	            TranslateTransition transicion = new TranslateTransition(Duration.seconds(0.8), cuadrado);
	            transicion.setToY(screenSize.getHeight() + cuadrado.getHeight());
	            transicion.setOnFinished(finishedEvent -> {
	                elegirOpcion(cartaActual.getOpcionA());
	            });
	            transicion.play();
	        } else if (angulo == -20) {
	            TranslateTransition transicion = new TranslateTransition(Duration.seconds(0.8), cuadrado);
	            transicion.setToY(screenSize.getHeight() + cuadrado.getHeight());
	            transicion.setOnFinished(finishedEvent -> {
	                elegirOpcion(cartaActual.getOpcionB());
	            });
	            transicion.play();
	        }
	    });
	    
	    texto.translateXProperty().bind(cuadrado.translateXProperty());
	    texto.translateYProperty().bind(cuadrado.translateYProperty().subtract(ladoCuadrado * 0.1));

	    // Agregar los elementos de la interfaz de la carta al Pane
	    interfazCartaPane.getChildren().add(cuadradoFondo);
	    interfazCartaPane.getChildren().add(cuadrado);
	    interfazCartaPane.getChildren().add(cuadradoAtras);
	    interfazCartaPane.getChildren().add(texto);

	    verAnios();

	}


}


