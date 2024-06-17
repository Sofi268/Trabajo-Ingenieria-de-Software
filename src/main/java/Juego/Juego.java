package Juego;

import java.util.concurrent.atomic.AtomicBoolean;

import Cartas.Carta;
import Cartas.Opcion;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Interfaz.ConjuntoBarras;
import Interfaz.FlipCarta;
import Interfaz.Icono;
import Strategy_Fondo.FondoStrategy;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
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
	private Group root;
	private Scene escena;
	private Canvas lienzo;
    private Pane interfazCartaPane = new Pane();
    private Pane interfazEstadisticasPane = new Pane();
    private Pane interfazFinal = new Pane();
	private GraphicsContext graficos;
	private Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	private int muertesTotales;
	private boolean opcionElegida = false;
	private String idSiguiente;
	private String idMuerte;
	private double anchoPantalla = screenSize.getWidth();
    private double altoPantalla = screenSize.getHeight();
    
	public Juego(){
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
	
	
	private void comenzarJuego() {
	    muertesTotales = 0;
	    idSiguiente = "contexto_1";
	    personaje = new Personaje(historia.getAnioActual(), muertesTotales); 
	    barrasEstadisticas = new ConjuntoBarras(screenSize.getHeight() * 0.05); // barras al 50%
	    historia.llenarCartas(); 
	    actualizarFondo(); 
	    fondo();
	    fondoCarta();
	    //Se registra el conjunto de barras como observer de personaje
	    personaje.registerObserver(barrasEstadisticas);
	    generarVistaEstadisticas(interfazEstadisticasPane);
	    cartaActual = historia.getCartaPorId(idSiguiente);
	    continuarJugando();
	}

	private void continuarJugando() {
	    int anioActual = historia.getAnioActual();
	    int anioLimite = 200;
	    actualizarFondo();
	    fondo(); // Llama al método fondo() que usa la estrategia seleccionada
	    fondoCarta();
	    opcionElegida = false;
	    if (cartaActual != null && anioActual < anioLimite) {
	    	if(idSiguiente.equals("inicio_2")) {
	    		idSiguiente = idMuerte;
	    
	    	}
	    	if(idSiguiente.equals("final_2") || idSiguiente.equals("final_3")) {
	    		pantallaFinal(interfazFinal);
	    		historia.aumentarAnio(15);
	    	}
	        cartaActual = historia.getCartaPorId(idSiguiente);
	        if(!(cartaActual.getOpcionA().getIdSiguienteMuerte().equals(""))) {
	    		idMuerte = cartaActual.getOpcionA().getIdSiguienteMuerte();
	    	}
	        interfazCartaPane.getChildren().clear();
	        interfazCarta();
	    }
	}

	private void morir() {
		personaje.getEstadisticas().setAgua(1);
		personaje.getEstadisticas().setAire(1);
		personaje.getEstadisticas().setTierra(1);
		personaje.getEstadisticas().setFuego(1);
		muertesTotales++;
		continuarJugando();
	}
	
	private void morirExcesoNivel() {
		if(personaje.getEstadisticas().getFuego() >= 100) {
			idSiguiente = "final_fuego_1";
		} else if(personaje.getEstadisticas().getAgua() >= 100) {
			idSiguiente = "final_agua_1";
		} else if(personaje.getEstadisticas().getAire() >= 100) {
			idSiguiente = "final_aire_1";
		} else if(personaje.getEstadisticas().getTierra() >= 100) {
			idSiguiente = "final_tierra_1";
		}
		morir();
	}
	
	private void morirNivelBajo() {
		idSiguiente = "final_mundo";
		morir();
	}
	
	private void nuevaPartida() {
		interfazFinal.getChildren().clear();
		personaje = null;
	    personaje = new Personaje(historia.getAnioActual(), muertesTotales); 
	    personaje.registerObserver(barrasEstadisticas);
	    barrasEstadisticas.resetBarras();
	    barrasEstadisticas.setBarrasLayoutY(screenSize.getHeight() * 0.03); // reubica Y de barras 
	    historia.aumentarIndiceCartaActual();
	    nuevoAvatar();
	}
	
	private void nuevoAvatar() {
		if(personaje.getElemento().equals("Agua")) {
			idSiguiente = "inicio_agua";
		} else if(personaje.getElemento().equals("Fuego")){
			idSiguiente = "inicio_fuego";
		} else if(personaje.getElemento().equals("Tierra")){
			idSiguiente = "inicio_tierra";
		} else if(personaje.getElemento().equals("Aire")){
			idSiguiente = "inicio_aire";
		}
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
	    root.getChildren().add(interfazFinal);
	}

	public void verInformacion() {
		verAniosHistoria(interfazCartaPane);
	    verAniosPersonaje(interfazCartaPane);
	    verNombrePersonaje(interfazCartaPane);
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
		FondoStrategy estrategia = historia.getSiguienteEstrategia();
	    setFondoStrategy(estrategia);
	}

	private void verAniosHistoria(Pane pane) {
        // Obtiene el año actual desde la instancia de Historia
        String anioActual = String.format("%03d", historia.getAnioActual());

        // Crea el nodo de texto
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Aldrich.ttf"), anchoPantalla * 0.04);
        Text textoAnio = new Text(anioActual);
        textoAnio.setFont(Font.font(customFont.getFamily(), screenSize.getHeight() * 0.04)); 
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
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Aldrich.ttf"), anchoPantalla * 0.04);
        Text textoAnios = new Text(aniosPersonaje + " años");
        textoAnios.setFont(Font.font(customFont.getFamily(), screenSize.getHeight() * 0.04));
        textoAnios.setFill(Color.web("FEE78C")); // Color amarillo hex

        // Posiciona el texto a la derecha
        double x = screenSize.getWidth() - textoAnios.getLayoutBounds().getWidth() - screenSize.getWidth() * 0.025; // Alinea el texto al borde derecho con un margen de 2.5% del ancho de la pantalla
        double y = screenSize.getHeight() * 0.1; // 10% de distancia del borde
        textoAnios.setX(x);
        textoAnios.setY(y);

        // Agrega el nodo de texto al pane
        pane.getChildren().add(textoAnios);
    }
	
	private void verNombrePersonaje(Pane pane) {
        // Obtiene los años del personaje
		String nombrePersonaje = personaje.getNombre();

        // Crea el nodo de texto
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Aldrich.ttf"), anchoPantalla * 0.04);
        Text textoAnios = new Text(nombrePersonaje);
        textoAnios.setFont(Font.font(customFont.getFamily(), screenSize.getHeight() * 0.04));
        textoAnios.setFill(Color.web("FEE78C")); // Color amarillo hex

        // Posiciona el texto a la derecha
        double x = screenSize.getWidth() - textoAnios.getLayoutBounds().getWidth() - screenSize.getWidth() * 0.025; // Alinea el texto al borde derecho con un margen de 2.5% del ancho de la pantalla
        double y = screenSize.getHeight() * 0.9; // 10% de distancia del borde inferior
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

	private void elegirOpcion(Opcion opcion) {
	    if (!opcionElegida) {
	        opcionElegida = true;
	        try {
	            if (opcion.equals(historia.getCartaPorId(cartaActual.getId()).getOpciones()[0])) {
	                historia.elegirOpcionDeCarta("A", personaje, cartaActual.getId());
	                
	            } else if (opcion.equals(historia.getCartaPorId(cartaActual.getId()).getOpciones()[1])) {
	                historia.elegirOpcionDeCarta("B", personaje, cartaActual.getId());
	            }
	            historia.aumentarAnio(historia.getCartaPorId(cartaActual.getId()).getAnios()); // Incrementa el anio de la historia
	            personaje.aumentarAnios(historia.getCartaPorId(cartaActual.getId()).getAnios());
	            idSiguiente = opcion.getIdSiguiente();
	            continuarJugando();
	        } catch (NivelExcedidoException e) {
	            morirExcesoNivel();
	        } catch (NivelInvalidoException e) {
	        	morirNivelBajo();
	        }
	    }
	}

	private void interfazCarta() {
		//----------------------------------------------------------------------------------------------------------------------
        //------------------------------------------Forma base de las cartas----------------------------------------------------
        //----------------------------------------------------------------------------------------------------------------------
		
	    // Calcula el tamaño del cuadrado
	    double ladoCuadrado = Math.min(anchoPantalla, altoPantalla) * 0.45; // Tamaño del cuadrado (45% de la pantalla total)

	    // Calcula la posición inicial del cuadrado
	    double altoCuadrado = altoPantalla * 0.65;
	    double xCuadrado = (anchoPantalla - ladoCuadrado) / 2; // Centra el cuadrado horizontalmente
	    double yCuadrado = altoPantalla - altoCuadrado; // Coloca el cuadrado desde un 65% de altura inferior

	    // Redondea las esquinas un 15%
	    double radioEsquinas = ladoCuadrado * 0.15;
	    
	    // Obtiene el color del cuadrado del fondo
	    String colorFondo = cartaActual.getColorFondo();
	    
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
	    
	    //----------------------------------------------------------------------------------------------------------------------
        //-----------------------------------------------Ajusta el borde de la carta--------------------------------------------
        //----------------------------------------------------------------------------------------------------------------------
	    
	    // Crea un borde para el fondo
	    double borde = anchoPantalla * 0.0015; // Tamanio del borde
	    // Crea el cuadrado del borde 
	    Rectangle bordeCarta = new Rectangle(xCuadrado - borde, yCuadrado - borde, ladoCuadrado + 2 * borde, ladoCuadrado + 2 * borde);
	    bordeCarta.setFill(Color.web("786752"));
	    bordeCarta.setArcWidth(radioEsquinas);
	    bordeCarta.setArcHeight(radioEsquinas);
	    
	    // Crea un Group para agrupar el cuadrado de fondo y el borde dorado
	    Group mazo = new Group(bordeCarta, cuadradoFondo);
	    
	    //----------------------------------------------------------------------------------------------------------------------
        //------------------------------------------------------Ajusta la carta-------------------------------------------------
        //----------------------------------------------------------------------------------------------------------------------

	    // Dibuja el cuadrado de la carta
	    Rectangle cuadrado = new Rectangle(xCuadrado, yCuadrado, ladoCuadrado, ladoCuadrado);
	    cuadrado.setFill(Color.web(colorFondo));
	    cuadrado.setArcWidth(radioEsquinas);
	    cuadrado.setArcHeight(radioEsquinas);
	    
	    //----------------------------------------------------------------------------------------------------------------------
        //-------------------------------------------Ajusta el reverso de la carta----------------------------------------------
        //----------------------------------------------------------------------------------------------------------------------
	    
	    Rectangle reversoCarta = new Rectangle(xCuadrado, yCuadrado, ladoCuadrado, ladoCuadrado);
	    reversoCarta.setFill(new ImagePattern(dorso));
	    reversoCarta.setArcWidth(radioEsquinas);
	    reversoCarta.setArcHeight(radioEsquinas);
	    
	    //----------------------------------------------------------------------------------------------------------------------
        //-------------------------------------------Ajusta la imagen de la carta-----------------------------------------------
        //----------------------------------------------------------------------------------------------------------------------
	    
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
        
        //----------------------------------------------------------------------------------------------------------------------
        //------------------------------------------Ajusta la descripcion de la carta-------------------------------------------
        //----------------------------------------------------------------------------------------------------------------------
        
        // Texto descripcion carta
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Aldrich.ttf"), anchoPantalla * 0.04);
	    TextFlow textoDescripcion = new TextFlow();
	    textoDescripcion.setTextAlignment(TextAlignment.CENTER); // Centra el texto horizontalmente

	    // Crea un Text con el contenido
	    Text text = new Text(cartaActual.getDescripcion());
	    text.setFont(Font.font(customFont.getFamily(), altoPantalla * 0.028));
	    textoDescripcion.getChildren().add(text);
	    
	    //----------------------------------------------------------------------------------------------------------------------
        //------------------------------------------Fondo de las opciones de la carta-------------------------------------------
        //----------------------------------------------------------------------------------------------------------------------
	    
	    // Crea el rectángulo negro con opacidad
	    Rectangle rectanguloSuperiorA = new Rectangle(xCuadrado, yCuadrado, ladoCuadrado, ladoCuadrado);
	    rectanguloSuperiorA.setFill(Color.BLACK);
	    rectanguloSuperiorA.setOpacity(0.5); // Ajusta la opacidad según sea necesario
	    
	    // Crea el rectángulo negro con opacidad
	    Rectangle rectanguloSuperiorB = new Rectangle(xCuadrado, yCuadrado, ladoCuadrado, ladoCuadrado);
	    rectanguloSuperiorB.setFill(Color.BLACK);
	    rectanguloSuperiorB.setOpacity(0.5); // Ajusta la opacidad según sea necesario

	    // Crea un Shape para la forma de la carta
	    Rectangle formaCarta = new Rectangle(xCuadrado, yCuadrado, ladoCuadrado, ladoCuadrado);
	    formaCarta.setArcWidth(radioEsquinas);
	    formaCarta.setArcHeight(radioEsquinas);

	    double alturaLimite = altoPantalla * 0.45; // 75% de la altura de la pantalla

	    // Crea el rectángulo de límite centrado y extendido a lo ancho de la pantalla
	    Rectangle formaLimiteA = new Rectangle(0, 0, anchoPantalla, alturaLimite);
	    formaLimiteA.setRotate(5);
	    
	    // Crea el rectángulo de límite centrado y extendido a lo ancho de la pantalla
	    Rectangle formaLimiteB = new Rectangle(0, 0, anchoPantalla, alturaLimite);
	    formaLimiteB.setRotate(355);
	    
	    Shape recorteCartaA = Shape.intersect(formaCarta, formaLimiteA);
	    Shape recorteCartaB = Shape.intersect(formaCarta, formaLimiteB);
	    
	    rectanguloSuperiorA.setClip(recorteCartaA);
	    rectanguloSuperiorA.setX(xCuadrado);
	    
	    rectanguloSuperiorB.setClip(recorteCartaB);
	    rectanguloSuperiorB.setX(xCuadrado);
        
        Group grupoCarta;
        
        rectanguloSuperiorA.setVisible(false);
        rectanguloSuperiorB.setVisible(false);
        
        //----------------------------------------------------------------------------------------------------------------------
        //----------------------------------Determina la carta segun contexto u elegir.-----------------------------------------
        //----------------------------------------------------------------------------------------------------------------------
        
        if (cartaActual.getTipoDeCarta().equals("contexto")) {
        	Image fondoContexto = imageView.getImage();
        	cuadrado.setFill(new ImagePattern(fondoContexto));
        	grupoCarta = new Group(cuadrado, textoDescripcion);
        }else {
        	// Agrupa la imagen y el fondo
            grupoCarta = new Group(cuadrado, imageView, rectanguloSuperiorA, rectanguloSuperiorB);
        }   

        //----------------------------------------------------------------------------------------------------------------------
        //------------------------------------------Texto de las opciones de la carta-------------------------------------------
        //----------------------------------------------------------------------------------------------------------------------
        
	    Text texto = new Text();
	    texto.setFill(Color.web("FEE78C")); // Color del texto
	    texto.setFont(Font.font(customFont.getFamily(), 16));

	    // Posicionamiento del texto
	    texto.setLayoutY(yCuadrado + ladoCuadrado * 0.2); // Coloca el texto a un 22.5% del borde superior
	    texto.setTextAlignment(TextAlignment.CENTER); //

	    // Establece un ancho fijo
	    texto.setWrappingWidth(ladoCuadrado * 0.5);
	    
	    // Crea una carta con su frente y atrás
	    Group carta = new Group(reversoCarta, grupoCarta);
	    
	    //----------------------------------------------------------------------------------------------------------------------
        //-----------------------------------------------Realiza un flip de la carta--------------------------------------------
        //----------------------------------------------------------------------------------------------------------------------
        
        FlipCarta flipCarta = new FlipCarta(carta, reversoCarta, grupoCarta);
	    flipCarta.flip();
        
	    //----------------------------------------------------------------------------------------------------------------------
        //-----------------------------------------------Maneja eventos del mouse-----------------------------------------------
	    //----------------------------------------------------------------------------------------------------------------------
	    
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
	   

	    // Coloca las propiedades de desplazamiento en el texto de las opciones
	    texto.translateXProperty().bind(grupoCarta.translateXProperty());
	    texto.translateYProperty().bind(grupoCarta.translateYProperty().subtract(ladoCuadrado * 0.1));
	    
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
	                texto.setText("");
	                rectanguloSuperiorA.setVisible(false);
                    rectanguloSuperiorB.setVisible(false);
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

	                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.0025), rotacionKeyValue, translateXKeyValue, translateYKeyValue);

	                Timeline timeline = new Timeline(keyFrame);
	                timeline.play();

	                if (angulo >= 1) {
	                	texto.setLayoutX(xCuadrado + ladoCuadrado * 0.3);
	                    texto.setText(cartaActual.getOpciones()[0].getInformacion());
	                    rectanguloSuperiorA.setVisible(false);
	                    rectanguloSuperiorB.setVisible(true);
	                } else if (angulo < -1) {
	                	texto.setLayoutX(xCuadrado + + ladoCuadrado * 0.2);
	                    texto.setText(cartaActual.getOpciones()[1].getInformacion());
	                    rectanguloSuperiorA.setVisible(true);
	                    rectanguloSuperiorB.setVisible(false);
	                } else {
	                	texto.setText("");
	                	
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
	    
	    //----------------------------------------------------------------------------------------------------------------------
        //---------------------------------------------Ajustes de visualizacion-------------------------------------------------
	    //----------------------------------------------------------------------------------------------------------------------
	    
	    // Variables para calcular la posición del texto y el ancho máximo dependiendo del tipo de carta
	    double maxAnchoTexto;
	    double xDescripcion;
	    double yDescripcion;
	    int numLineas = (int) Math.ceil(textoDescripcion.prefWidth(-1) / (anchoPantalla * 0.32));

	    Group cartaConTexto;
	    
	    if (cartaActual.getTipoDeCarta().equals("contexto")) {
	        // Establece que el ancho máximo del texto sea 1% menos que el ancho del cuadrado
	        maxAnchoTexto = ladoCuadrado * 0.95;
	        textoDescripcion.setPrefWidth(maxAnchoTexto);
	        textoDescripcion.setLineSpacing(altoPantalla * 0.01);
	        // Ajusta la posición vertical inicial dependiendo del número de líneas
	        numLineas = (int) Math.ceil(textoDescripcion.prefWidth(-1) / (ladoCuadrado * 0.32));
	        yDescripcion = yCuadrado + ladoCuadrado/2 - (numLineas * altoPantalla * 0.038) / 2 - altoPantalla * 0.035;
	        text.setFill(Color.BLACK);
	        cartaConTexto = carta;
	    } else {
	        // Establece que como máximo el ancho sea del 32% del ancho de la pantalla
	        maxAnchoTexto = anchoPantalla * 0.32;
	        textoDescripcion.setPrefWidth(maxAnchoTexto);
	        // Ajusta la posición vertical inicial dependiendo del número de líneas
	        yDescripcion = (altoPantalla * 0.52 - (numLineas * altoPantalla * 0.028)) / 2;
	        text.setFill(Color.WHITE);
	        cartaConTexto = new Group(carta, textoDescripcion);
	    }

	    // Centra horizontalmente el texto
	    xDescripcion = (anchoPantalla - maxAnchoTexto) / 2;
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
	    interfazCartaPane.getChildren().add(mazo);
        interfazCartaPane.getChildren().add(cartaConTexto);
        interfazCartaPane.getChildren().add(texto);
        
	    verInformacion();
	}

	
	public void pantallaFinal(Pane pane) {
        // Define el color de fondo
        String colorFondo = "04042C";
        Rectangle fondo = new Rectangle(anchoPantalla, altoPantalla);
        fondo.setFill(Color.web(colorFondo));

        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Aldrich.ttf"), anchoPantalla * 0.04);

        // Crear y configurar los textos
        Text elTexto = new Text("El");
        elTexto.setFont(Font.font(customFont.getFamily(), anchoPantalla * 0.04));
        elTexto.setFill(Color.web("FEEFB3"));
        elTexto.setLayoutX(anchoPantalla * 0.35);
        elTexto.setLayoutY(altoPantalla * 0.3);

        Text avatarTexto = new Text("Avatar");
        avatarTexto.setFont(Font.font(customFont.getFamily(), anchoPantalla * 0.08));
        avatarTexto.setFill(Color.web("FEEFB3"));
        avatarTexto.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        avatarTexto.setLayoutX(anchoPantalla * 0.5 - avatarTexto.getLayoutBounds().getWidth() / 2);
        avatarTexto.setLayoutY(altoPantalla * 0.45);

        Text haMuertoTexto = new Text("ha muerto");
        haMuertoTexto.setFont(Font.font(customFont.getFamily(), anchoPantalla * 0.04));
        haMuertoTexto.setFill(Color.web("FEEFB3"));
        haMuertoTexto.setLayoutX(anchoPantalla * 0.5);
        haMuertoTexto.setLayoutY(altoPantalla * 0.55);

        // Crea y configura el rectángulo negro
        Rectangle rectanguloNegro = new Rectangle(anchoPantalla, altoPantalla * 0.2);
        rectanguloNegro.setFill(Color.web("000000"));
        rectanguloNegro.setLayoutY(altoPantalla - rectanguloNegro.getHeight() - altoPantalla * 0.1);

        // Crea y configura el borde del rectángulo negro
        Rectangle bordeRectangulo = new Rectangle(anchoPantalla, altoPantalla * 0.225);
        bordeRectangulo.setFill(Color.web("1A130A"));

        // Calcula la diferencia en altura entre el borde y el rectángulo negro
        double diferenciaAltura = rectanguloNegro.getHeight() - bordeRectangulo.getHeight();

        // Calcula la posición vertical del borde para que esté centrado respecto al rectángulo negro
        bordeRectangulo.setLayoutY(rectanguloNegro.getLayoutY() + diferenciaAltura / 2);

        // Carga y configura el icono
        Icono iconoMuerte = new Icono("/Iconos/death.png", anchoPantalla * 0.015, anchoPantalla * 0.015);
        iconoMuerte.setX((anchoPantalla - iconoMuerte.getWidth()) / 2); // Centra horizontalmente
        iconoMuerte.setY(rectanguloNegro.getLayoutY() + rectanguloNegro.getHeight() * 0.15); // 15% desde el borde superior del rectángulo negro

        Text nombrePersonajeTexto = new Text(personaje.getNombre());
        nombrePersonajeTexto.setFont(Font.font(customFont.getFamily(), anchoPantalla * 0.015));
        nombrePersonajeTexto.setFill(Color.web("FEEFB3"));
        nombrePersonajeTexto.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        nombrePersonajeTexto.setLayoutX(anchoPantalla * 0.5 - nombrePersonajeTexto.getLayoutBounds().getWidth() / 2);
        nombrePersonajeTexto.setLayoutY(altoPantalla - rectanguloNegro.getHeight() / 2 - altoPantalla * 0.085);  
        
        String anioInicial = String.format("%03d", personaje.getAnioInicial());
        int anioDeMuerte = personaje.getAnios() + personaje.getAnioInicial();
        String anios = String.format("%03d", anioDeMuerte);
        Text aniosTotales = new Text(anioInicial + " - " + anios);
        aniosTotales.setFont(Font.font(customFont.getFamily(), anchoPantalla * 0.0125));
        aniosTotales.setFill(Color.web("FEEFB3"));
        aniosTotales.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        aniosTotales.setLayoutX(anchoPantalla * 0.5 - aniosTotales.getLayoutBounds().getWidth() / 2);
        aniosTotales.setLayoutY(altoPantalla - rectanguloNegro.getHeight() / 2 - altoPantalla * 0.03);
        
        // Crea el texto "Continuar"
        Text continuarTexto = new Text("CONTINUAR");
        continuarTexto.setFont(Font.font(customFont.getFamily(), anchoPantalla * 0.012));
        continuarTexto.setFill(Color.web("FFFFFF"));
        continuarTexto.setTextAlignment(TextAlignment.CENTER);

        // Calcula la posición Y del texto para que esté centrado verticalmente respecto al rectángulo
        double continuarTextX = anchoPantalla * 0.95 - continuarTexto.getLayoutBounds().getWidth();

        // Calcula la posición Y del texto para que esté centrado verticalmente respecto al rectángulo
        double continuarTextY = rectanguloNegro.getLayoutY() + rectanguloNegro.getHeight() / 2 + continuarTexto.getLayoutBounds().getHeight() / 2;

        // Establece la posición del texto "Continuar"
        continuarTexto.setLayoutX(continuarTextX);
        continuarTexto.setLayoutY(continuarTextY);

        // Tamaño del borde (0.5% del tamaño de la pantalla)
        double bordeSize = screenSize.getWidth() * 0.001;

        // Crea el círculo blanco con borde
        Circle circle = new Circle(10);
        circle.setFill(Color.web("#FFFFFF", 0.5)); // Relleno blanco con opacidad del 50%
        circle.setStroke(Color.web("#FFFFFF")); // Borde blanco
        circle.setStrokeWidth(bordeSize); // Ancho del borde
        circle.setStrokeType(StrokeType.OUTSIDE); // Tipo de trazo fuera del círculo
        circle.setOpacity(0.5); // Opacidad del círculo al 50%

        // Crea el símbolo ">"
        Text arrow = new Text(">");
        arrow.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
        arrow.setFill(Color.web("#54D42B")); // Color verde hexadecimal

        // Calcula la posición X del símbolo ">" para que esté centrado horizontalmente en el círculo
        double arrowX = circle.getCenterX() - arrow.getLayoutBounds().getWidth() / 2;

        // Calcula la posición Y del símbolo ">" para que esté centrado verticalmente en el círculo
        double arrowY = circle.getCenterY() + arrow.getLayoutBounds().getHeight() / 4; // Desplazamiento para centrarlo correctamente verticalmente

        // Establecer la posición del símbolo ">"
        arrow.setX(arrowX);
        arrow.setY(arrowY);

        // Agrupa el círculo y el símbolo ">" en un nodo de grupo
        Group boton = new Group(circle, arrow);

        // Calcula la posición X del botón para que esté a 1% a la izquierda del texto
        double botonX = continuarTextX - continuarTexto.getLayoutBounds().getWidth() * 0.01 - circle.getRadius() * 2;

        // Calcula la posición Y del botón para que esté centrado verticalmente
        double botonY = continuarTextY - circle.getRadius() / 2;

        // Establecer la posición del botón
        boton.setLayoutX(botonX);
        boton.setLayoutY(botonY);
        
        Group continuar = new Group(boton, continuarTexto);

        // Agrega todos los elementos al Pane
        pane.getChildren().addAll(fondo, bordeRectangulo, rectanguloNegro, elTexto, avatarTexto, haMuertoTexto, iconoMuerte.getImageView(), nombrePersonajeTexto, aniosTotales, continuar);
        
        // Animacion de entrada de los textos con fade
        TranslateTransition translateEl = new TranslateTransition(Duration.seconds(0.5), elTexto);
        FadeTransition fadeEl = new FadeTransition(Duration.seconds(0.5), elTexto);
        translateEl.setFromX(250);
        translateEl.setToX(0); // Destino en X relativo a la posición original
        fadeEl.setFromValue(0); // Opacidad inicial
        fadeEl.setToValue(1); // Opacidad final

        TranslateTransition translateAvatar = new TranslateTransition(Duration.seconds(0.5), avatarTexto);
        FadeTransition fadeAvatar = new FadeTransition(Duration.seconds(0.5), avatarTexto);
        translateAvatar.setFromX(-250);
        translateAvatar.setToX(0); // Destino en X relativo a la posición original
        fadeAvatar.setFromValue(0); // Opacidad inicial
        fadeAvatar.setToValue(1); // Opacidad final

        TranslateTransition translateHaMuerto = new TranslateTransition(Duration.seconds(0.5), haMuertoTexto);
        FadeTransition fadeHaMuerto = new FadeTransition(Duration.seconds(0.5), haMuertoTexto);
        translateHaMuerto.setFromX(250); 
        translateHaMuerto.setToX(0); // Destino en X relativo a la posición original
        fadeHaMuerto.setFromValue(0); // Opacidad inicial
        fadeHaMuerto.setToValue(1); // Opacidad final

        // Animación de entrada del grupo "Continuar" con fade
        TranslateTransition translateBoton = new TranslateTransition(Duration.seconds(0.5), continuar);
        FadeTransition fadeBoton = new FadeTransition(Duration.seconds(0.5), continuar);
        translateBoton.setFromX(250);
        translateBoton.setToX(0); // Destino en X relativo a la posición original
        fadeBoton.setFromValue(0); // Opacidad inicial
        fadeBoton.setToValue(1); // Opacidad final

        // Animacion de entrada de los textos con fade
        TranslateTransition translateIcon = new TranslateTransition(Duration.seconds(0.5), iconoMuerte.getImageView());
        FadeTransition fadeIcon = new FadeTransition(Duration.seconds(0.5), iconoMuerte.getImageView());
        translateIcon.setFromY(150);
        translateIcon.setToY(0); // Destino en Y relativo a la posición original
        fadeIcon.setFromValue(0); // Opacidad inicial
        fadeIcon.setToValue(1); // Opacidad final

        TranslateTransition translateNombreAvatar = new TranslateTransition(Duration.seconds(0.5), nombrePersonajeTexto);
        FadeTransition fadeNombreAvatar = new FadeTransition(Duration.seconds(0.5), nombrePersonajeTexto);
        translateNombreAvatar.setFromY(50);
        translateNombreAvatar.setToY(0); // Destino en Y relativo a la posición original
        fadeNombreAvatar.setFromValue(0); // Opacidad inicial
        fadeNombreAvatar.setToValue(1); // Opacidad final

        TranslateTransition translateAniosTotales = new TranslateTransition(Duration.seconds(0.5), aniosTotales);
        FadeTransition fadeAniosTotales = new FadeTransition(Duration.seconds(0.5), aniosTotales);
        translateAniosTotales.setFromY(50); 
        translateAniosTotales.setToY(0); // Destino en Y relativo a la posición original
        fadeAniosTotales.setFromValue(0); // Opacidad inicial
        fadeAniosTotales.setToValue(1); // Opacidad final
        
        // Quita la visibilidad inicial
        elTexto.setOpacity(0);
        avatarTexto.setOpacity(0);
        haMuertoTexto.setOpacity(0);
        nombrePersonajeTexto.setOpacity(0);
        aniosTotales.setOpacity(0);
        continuar.setOpacity(0);
        
        // Animación paralela de los textos "El" y "Icono"
        ParallelTransition parallelElIcon = new ParallelTransition();
        parallelElIcon.getChildren().addAll(translateEl, fadeEl, translateIcon, fadeIcon);

        // Animación paralela de los textos "Avatar", "Nombre de Avatar" y "Continuar"
        ParallelTransition parallelAvatarNombreContinuar = new ParallelTransition();
        parallelAvatarNombreContinuar.getChildren().addAll(translateAvatar, fadeAvatar, translateNombreAvatar, fadeNombreAvatar, translateBoton, fadeBoton);

        // Animación paralela de los textos "Ha muerto" y "Años Totales"
        ParallelTransition parallelHaMuertoAnios = new ParallelTransition();
        parallelHaMuertoAnios.getChildren().addAll(translateHaMuerto, fadeHaMuerto, translateAniosTotales, fadeAniosTotales);

        // Animación secuencial de las tres paralelas
        SequentialTransition sequential = new SequentialTransition();
        sequential.getChildren().addAll(parallelElIcon, parallelAvatarNombreContinuar, parallelHaMuertoAnios);

        // Iniciar la animación secuencial
        sequential.play();
        
        historia.aumentarMuertes();
        
        continuar.setOnMouseClicked(event -> {
            nuevaPartida();
        });

    }
	
}