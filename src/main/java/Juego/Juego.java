package Juego;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import Cartas.Carta;
import Cartas.Opcion;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Interfaz.ConjuntoBarras;
import Interfaz.FlipCarta;
import Interfaz.Icono;
import Strategy_Fondo.FondoAgua;
import Strategy_Fondo.FondoAire;
import Strategy_Fondo.FondoFuego;
import Strategy_Fondo.FondoStrategy;
import Strategy_Fondo.FondoTierra;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Juego extends Application{
	
	private Historia historia;
	private Personaje personaje;
	private Carta cartaActual;
	private ConjuntoBarras barrasEstadisticas;
	private FondoStrategy fondoStrategy;
	private FondoAire fondoAire;
	private FondoAgua fondoAgua;
	private FondoTierra fondoTierra;
	private FondoFuego fondoFuego;
	private Group root;
	private Scene escena;
	private Canvas lienzo;
    private Pane interfazCartaPane = new Pane();
    private Pane interfazEstadisticasPane = new Pane();
	private GraphicsContext graficos;
	private Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	
	private int muertesTotales;
	
	public Juego(){
		fondoAire = new FondoAire();
        fondoAgua = new FondoAgua();
        fondoTierra = new FondoTierra();
        fondoFuego = new FondoFuego();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage ventana) throws Exception {
		
		// Inicializa atributos
		historia = new Historia();
		personaje = null;
		
		interfazPrincipal();
		comenzarJuego();
		
		ventana.setScene(escena);
		ventana.setTitle("Avatar");
		ventana.show();
		
	}
	
	
	private void comenzarJuego(){
		muertesTotales = 0;
	    personaje = new Personaje();
	    barrasEstadisticas = new ConjuntoBarras(screenSize.getHeight()*0.05); // barras al 50%
	    actualizarFondo();
	    fondo();
        fondoCarta();
	    historia.llenarCartas(); 
	    generarVistaEstadisticas(interfazEstadisticasPane);
	    continuarJugando();
	}

	private void continuarJugando() {
	    int anioActual = historia.getAnioActual();
	    int anioLimite = 200;
	    actualizarFondo();
	    fondo();
        fondoCarta();
	    if(historia.getCartaActual() != null && anioActual < anioLimite) {
	        cartaActual = historia.jugarCarta();
	        interfazCartaPane.getChildren().clear();
	        interfazCarta();
	    }
	}


	private void morir() {
		historia.aumentarAnio(15);
		muertesTotales++;
		personaje = null;
	    personaje = new Personaje(); 
	    barrasEstadisticas.resetBarras();
	    barrasEstadisticas.setBarrasLayoutY(screenSize.getHeight() * 0.03); // reubica Y de barras 
	    historia.aumentarIndiceCartaActual();
	    continuarJugando();
	}
	

	private void interfazPrincipal() {
	    root = new Group();
	    escena = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
	    escena.setCamera(new PerspectiveCamera());
	    lienzo = new Canvas(screenSize.getWidth(), screenSize.getHeight());
	    root.getChildren().add(lienzo);
	    graficos = lienzo.getGraphicsContext2D();
	    root.getChildren().add(interfazCartaPane);
	    root.getChildren().add(interfazEstadisticasPane);
	}

	public void verAnios() {
		verAniosHistoria(interfazCartaPane);
	    verAniosPersonaje(interfazCartaPane);
	}
	
	private void setFondoStrategy(FondoStrategy fondoStrategy) {
        this.fondoStrategy = fondoStrategy;
    }
	
	private void fondo() {
		fondoStrategy.dibujarFondo(graficos, screenSize);
	}
	
	private void fondoCarta() {
		fondoStrategy.dibujarFondoCarta(graficos, screenSize);
	}
	
	private void actualizarFondo() {
		int aux = muertesTotales % 4;
		switch(aux) {
		case 0:
			setFondoStrategy(fondoAire);
			break;
		case 1:
			setFondoStrategy(fondoAgua);
			break;
		case 2:
			setFondoStrategy(fondoTierra);
			break;
		case 3:
			setFondoStrategy(fondoFuego);
			break;
		default:
			setFondoStrategy(fondoAire);
			break;
		}
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
	
	public void generarVistaEstadisticas(Pane pane) {
		//barras de estadisticas
		barrasEstadisticas.setBarrasLayoutX(screenSize.getWidth() * 0.40, screenSize.getWidth());
		barrasEstadisticas.setBarrasLayoutY(screenSize.getHeight() * 0.03);
		
		pane.getChildren().add(barrasEstadisticas.getBarraTierra().getBorde());
		pane.getChildren().add(barrasEstadisticas.getBarraTierra().getRelleno());
		pane.getChildren().add(barrasEstadisticas.getBarraAgua().getBorde());
		pane.getChildren().add(barrasEstadisticas.getBarraAgua().getRelleno());
		pane.getChildren().add(barrasEstadisticas.getBarraFuego().getBorde());
		pane.getChildren().add(barrasEstadisticas.getBarraFuego().getRelleno());
		pane.getChildren().add(barrasEstadisticas.getBarraAire().getBorde());
		pane.getChildren().add(barrasEstadisticas.getBarraAire().getRelleno());
		
		//Iconos de estadisticas
		Icono iconoTierra = new Icono("/Iconos/tierra.png", screenSize.getWidth() * 0.025, screenSize.getWidth() * 0.025);
		iconoTierra.setX(screenSize.getWidth() * 0.40);
        iconoTierra.setY(screenSize.getHeight() * 0.14 );
        
        Icono iconoAgua = new Icono("/Iconos/agua.png", screenSize.getWidth() * 0.025, screenSize.getWidth() * 0.025);
		iconoAgua.setX(screenSize.getWidth() * 0.46);
        iconoAgua.setY(screenSize.getHeight() * 0.14 );
        
        Icono iconoFuego = new Icono("/Iconos/fuego.png", screenSize.getWidth() * 0.025, screenSize.getWidth() * 0.025);
		iconoFuego.setX(screenSize.getWidth() * 0.52);
        iconoFuego.setY(screenSize.getHeight() * 0.14 );
        
        Icono iconoAire = new Icono("/Iconos/aire.png", screenSize.getWidth() * 0.025, screenSize.getWidth() * 0.025);
		iconoAire.setX(screenSize.getWidth() * 0.58);
        iconoAire.setY(screenSize.getHeight() * 0.14 ); 
        
		pane.getChildren().add(iconoTierra.getImageView());
		pane.getChildren().add(iconoAgua.getImageView());
		pane.getChildren().add(iconoFuego.getImageView());
		pane.getChildren().add(iconoAire.getImageView());
	}
	
	public void actualizarEstadisticas() {
		//hashmap con niveles actuales de estadisticas
		HashMap<String, Integer> niveles = personaje.getNiveles();
		barrasEstadisticas.nuevasAlturas(
				niveles.get("tierra"),
				niveles.get("agua"),
				niveles.get("fuego"),
				niveles.get("aire"));
		System.out.println("--Actualizacion de estadisticas--");
	}

	private void elegirOpcion(Opcion opcion) {
	    try {
	        if (opcion.equals(historia.getCartaActual().getOpciones()[0])) {
	            historia.elegirOpcionDeCarta("A", personaje);
	        } else if (opcion.equals(historia.getCartaActual().getOpciones()[1])) {
	            historia.elegirOpcionDeCarta("B", personaje);
	        }
	        historia.aumentarAnio(historia.getCartaActual().getAnios()); // Incrementar el año de la historia
	        personaje.aumentarAnios(historia.getCartaActual().getAnios());
	        actualizarEstadisticas();
	        historia.aumentarIndiceCartaActual();
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
	    String colorHexCuadrado = "B50204";
	    
	    // Dibuja el cuadrado de fondo
	    Rectangle cuadradoFondo = new Rectangle(xCuadrado, yCuadrado, ladoCuadrado, ladoCuadrado);

	    // Importa la imagen del reverso
	    Image dorso = new Image("/Fondos/dorsoCarta.png");

	    // Crea un ImageView con la imagen del fondo y ajusta su tamanio al cuadrado
	    ImageView imageViewFondo = new ImageView(dorso);
	    imageViewFondo.setFitWidth(ladoCuadrado);
	    imageViewFondo.setFitHeight(ladoCuadrado);

	    // Define el relleno del cuadrado como la imagen
	    cuadradoFondo.setFill(new ImagePattern(dorso));
	    
	    // Curva las esquinas
	    cuadradoFondo.setArcWidth(radioEsquinas);
	    cuadradoFondo.setArcHeight(radioEsquinas);
	    
	    // Crea un borde para el fondo
	    double borde = anchoPantalla * 0.0015; // Tamanio del borde
	    // Crea el cuadrado del borde 
	    Rectangle bordeCarta = new Rectangle(xCuadrado - borde, yCuadrado - borde, ladoCuadrado + 2 * borde, ladoCuadrado + 2 * borde);
	    bordeCarta.setFill(Color.web("786752"));
	    bordeCarta.setArcWidth(radioEsquinas);
	    bordeCarta.setArcHeight(radioEsquinas);
	    
	    // Crea un Group para agrupar el cuadrado de fondo y el borde dorado
	    Group mazo = new Group(bordeCarta, cuadradoFondo);

	    // Dibuja el cuadrado de la carta
	    Rectangle cuadrado = new Rectangle(xCuadrado, yCuadrado, ladoCuadrado, ladoCuadrado);
	    cuadrado.setFill(Color.web(colorHexCuadrado));
	    cuadrado.setArcWidth(radioEsquinas);
	    cuadrado.setArcHeight(radioEsquinas);

	    Rectangle reversoCarta = new Rectangle(xCuadrado, yCuadrado, ladoCuadrado, ladoCuadrado);
	    reversoCarta.setFill(new ImagePattern(dorso));
	    reversoCarta.setArcWidth(radioEsquinas);
	    reversoCarta.setArcHeight(radioEsquinas);
	    
	    // Crea un ImageView para la imagen de la carta
        Image imagenCarta = cartaActual.getFondo();
        ImageView imageView = new ImageView(imagenCarta);

        // Establece el tamaño y la posición de la imagen
        double anchoImagen = ladoCuadrado * 1.2;
        double altoImagen = ladoCuadrado * 1.2;
        double xImagen = (anchoPantalla - anchoImagen) / 2;
        double yImagen = ((altoPantalla - altoImagen) / 2) * 1.13;

        imageView.setFitWidth(anchoImagen);
        imageView.setFitHeight(altoImagen);
        imageView.setLayoutX(xImagen);
        imageView.setLayoutY(yImagen);
        
        // Agrupa la imagen y el fondo
        Group grupoCarta = new Group(cuadrado, imageView);

	    Text texto = new Text();
	    texto.setFill(Color.WHITE); // Color del texto
	    texto.setFont(Font.font("Arial", FontWeight.BOLD, 16));

	    // Posicionamiento del texto
	    texto.setLayoutX(xCuadrado + ladoCuadrado * 0.15);
	    texto.setLayoutY(yCuadrado + ladoCuadrado * 0.225); // Coloca el texto a un 22.5% del borde superior
	    texto.setTextAlignment(TextAlignment.CENTER); //

	    // Establece un ancho fijo
	    texto.setWrappingWidth(ladoCuadrado * 0.7);

	    // Calcula la posición horizontal para centrar el rectángulo en la carta
	    double xRectangulo = (anchoPantalla - ladoCuadrado) / 2;

	    // Crea una carta con su frente y atrás
	    Group carta = new Group(reversoCarta, grupoCarta);

	    // Realiza un flip de la carta
	    FlipCarta flipCarta = new FlipCarta(carta, reversoCarta, grupoCarta);
	    flipCarta.flip();

	    // Aplica la rotación al grupo que contiene el cuadrado y la imagen
	    Rotate rotacionGrupo = new Rotate();
	    rotacionGrupo.setPivotX(xCuadrado + ladoCuadrado / 2);
	    rotacionGrupo.setPivotY(yCuadrado + ladoCuadrado);
	    grupoCarta.getTransforms().add(rotacionGrupo);

	    // Condiciones para activar el movimiento
	    AtomicBoolean movimientoHabilitado = new AtomicBoolean(false);
	    AtomicBoolean movimientoActivado = new AtomicBoolean(false);

	    // Temporizador para habilitar el movimiento después de medio segundo de aparición de la carta
	    Timeline temporizador = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> movimientoHabilitado.set(true)));
	    temporizador.setDelay(Duration.seconds(0.5));
	    temporizador.setCycleCount(1); // Lo ejecuta solo la primera vez

	    // Inicializa la posición inicial del mouse en el centro de la pantalla
	    double centroX = escena.getWidth() / 2;
	    double centroY = escena.getHeight() / 2;

	    // Maneja los eventos con el mouse
	    manejarEventosMouse(grupoCarta, rotacionGrupo, escena, cartaActual, centroX, centroY, ladoCuadrado, anchoPantalla, altoPantalla, movimientoHabilitado, movimientoActivado, temporizador, texto);

	    // Coloca las propiedades de desplazamiento en el texto de las opciones
	    texto.translateXProperty().bind(grupoCarta.translateXProperty());
	    texto.translateYProperty().bind(grupoCarta.translateYProperty().subtract(ladoCuadrado * 0.1));

	    // Texto descripcion carta
	    TextFlow textoDescripcion = new TextFlow();
	    textoDescripcion.setTextAlignment(TextAlignment.CENTER); // Centra el texto horizontalmente

	    // Crea un Text con el contenido
	    Text text = new Text(cartaActual.getDescripcion());
	    text.setFont(Font.font("Rockwell", FontWeight.NORMAL, altoPantalla * 0.028));
	    text.setFill(Color.WHITE);
	    textoDescripcion.getChildren().add(text);

	    // Calcula el número de lineas de texto basado en el tamaño del contenedor
	    int numLineas = (int) Math.ceil(textoDescripcion.prefWidth(-1) / (anchoPantalla * 0.32));

	    // Calcula la posición del texto
	    double xDescripcion = (anchoPantalla - (anchoPantalla * 0.32)) / 2; // Centra horizontalmente el texto

	    // Ajusta la posición vertical inicial dependiendo del número de líneas
	    double yDescripcion = (altoPantalla * 0.52 - (numLineas * altoPantalla * 0.028)) / 2;

	    textoDescripcion.setPrefWidth(anchoPantalla * 0.32); // Establece que como máximo el ancho sea del 32%
	    textoDescripcion.setTextAlignment(TextAlignment.CENTER); // Centra el texto horizontalmente
	    textoDescripcion.setLayoutX(xDescripcion);
	    textoDescripcion.setLayoutY(yDescripcion);
        
        // Texto nombre personaje en carta carta
	    Text textoNombrePersonaje = new Text(cartaActual.getNombre());
	    textoNombrePersonaje.setFont(Font.font("Rockwell", altoPantalla * 0.028));
	    textoNombrePersonaje.setFill(Color.WHITE);
	    double xPersonaje = (anchoPantalla - textoNombrePersonaje.getBoundsInLocal().getWidth()) / 2;
        double yPersonaje = altoPantalla * 0.86; 
        textoNombrePersonaje.setX(xPersonaje);
        textoNombrePersonaje.setY(yPersonaje);
        textoNombrePersonaje.setTextAlignment(TextAlignment.CENTER);
        
	    // Agrega los elementos de la interfaz de la carta al Pane
	    interfazCartaPane.getChildren().add(textoNombrePersonaje);
	    interfazCartaPane.getChildren().add(textoDescripcion);
	    interfazCartaPane.getChildren().add(mazo);
        interfazCartaPane.getChildren().add(carta);
        interfazCartaPane.getChildren().add(texto);
        
	    verAnios();
	}
	
	private void manejarEventosMouse(Group grupoCarta, Rotate rotacionGrupo, Scene escena, Carta cartaActual, double centroX, double centroY, double ladoCuadrado, double anchoPantalla, double altoPantalla, AtomicBoolean movimientoHabilitado, AtomicBoolean movimientoActivado, Timeline temporizador, Text texto) {
	    escena.setOnMouseMoved(event -> {
	        double mouseX = event.getSceneX();
	        double mouseY = event.getSceneY();

	        double difX = mouseX - centroX;
	        double difY = mouseY - centroY;

	        boolean mouseSobreCuadrado = Math.abs(difX) < Math.abs(ladoCuadrado/2) && Math.abs(difY) < Math.abs(ladoCuadrado/2);

	        if ((rotacionGrupo.getAngle() == 0) && mouseSobreCuadrado && movimientoHabilitado.get()) {
	            movimientoActivado.set(true);
	        }

	        if ((rotacionGrupo.getAngle() == 0) && !(mouseSobreCuadrado) && movimientoHabilitado.get()) {
	            movimientoActivado.set(false);
	        }

	        if (movimientoActivado.get()) {
	            if (Math.abs(difX) >= (Math.abs(anchoPantalla - centroX) * 0.95) || Math.abs(difY) >= (Math.abs(altoPantalla - centroY) * 0.95)) {
	                KeyValue rotacionKeyValue = new KeyValue(rotacionGrupo.angleProperty(), 0);
	                KeyValue translateXKeyValue = new KeyValue(grupoCarta.translateXProperty(), 0);
	                KeyValue translateYKeyValue = new KeyValue(grupoCarta.translateYProperty(), 0);

	                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.3), rotacionKeyValue, translateXKeyValue, translateYKeyValue);

	                Timeline timeline = new Timeline(keyFrame);
	                timeline.play();
	            } else {
	                double angulo = difX / 10;
	                double desplazamientoY = difY * 0.03;

	                if (angulo > 20) {
	                    angulo = 20;
	                } else if (angulo < -20) {
	                    angulo = -20;
	                }

	                KeyValue rotacionKeyValue = new KeyValue(rotacionGrupo.angleProperty(), angulo);
	                KeyValue translateXKeyValue = new KeyValue(grupoCarta.translateXProperty(), angulo * 2.5);
	                KeyValue translateYKeyValue = new KeyValue(grupoCarta.translateYProperty(), desplazamientoY);

	                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.025), rotacionKeyValue, translateXKeyValue, translateYKeyValue);

	                Timeline timeline = new Timeline(keyFrame);
	                timeline.play();

	                if (angulo >= 1) {
	                    texto.setText(cartaActual.getOpciones()[0].getInformacion());
	                } else if (angulo < -1) {
	                    texto.setText(cartaActual.getOpciones()[1].getInformacion());
	                }
	            }
	        }
	    });

	    temporizador.play();

	    escena.setOnMouseClicked(event -> {
	        movimientoActivado.set(false);
	        double angulo = rotacionGrupo.getAngle();
	        if (angulo >= 1) {
	            TranslateTransition transicion = new TranslateTransition(Duration.seconds(0.8), grupoCarta);

	            transicion.setToY(screenSize.getHeight() + grupoCarta.getBoundsInParent().getHeight());

	            transicion.setOnFinished(finishedEvent -> {
	                elegirOpcion(cartaActual.getOpcionA());
	            });

	            transicion.play();
	        } else if (angulo < -1) {
	            TranslateTransition transicion = new TranslateTransition(Duration.seconds(0.8), grupoCarta);

	            transicion.setToY(screenSize.getHeight() + grupoCarta.getBoundsInParent().getHeight());

	            transicion.setOnFinished(finishedEvent -> {
	                elegirOpcion(cartaActual.getOpcionB());
	            });

	            transicion.play();
	        }
	    });
	}

	
}