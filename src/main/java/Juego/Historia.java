package Juego;

import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Cartas.Carta;
import Cartas.Opcion;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Strategy_Fondo.FondoAgua;
import Strategy_Fondo.FondoAire;
import Strategy_Fondo.FondoFuego;
import Strategy_Fondo.FondoStrategy;
import Strategy_Fondo.FondoTierra;
import javafx.scene.image.Image;


public class Historia {
	private static final int ANIO_INICIAL = 0;

	private int anioActual;
	private int indiceCartaActual = 0;
	private Carta[] cartas;
	private List<FondoStrategy> ciclosElemento;
	private int muertesTotales;
	
	public Historia() {
		anioActual = ANIO_INICIAL;
		cartas = new Carta[85];//SE MODIFICO PARCIALMENTE SEGUN CARTAS DEFINIDAS
		ciclosElemento = new ArrayList<>();
        ciclosElemento.add(new FondoAire());
        ciclosElemento.add(new FondoAgua());
        ciclosElemento.add(new FondoTierra());
        ciclosElemento.add(new FondoFuego());
        muertesTotales = 0;
	}
	
	public void llenarCartas(){
		Gson gson = new Gson(); //obj para parsear json
		
        try (FileReader reader = new FileReader("src/main/resources/Data/cartas.json")) {
        	JsonArray jsonArray = gson.fromJson(reader, JsonArray.class); //array con datos del json
        	 for (int i = 0; i < jsonArray.size()-1; i++) {
        		 JsonObject jsonObject = jsonArray.get(i).getAsJsonObject(); //obj con datos de la carta
        		 
        		 // Obtiene nombre, descripción y anios de la carta
                 String nombre = jsonObject.get("nombre").getAsString();
                 String idCarta = jsonObject.get("id").getAsString();
                 String descripcion = jsonObject.get("descripcion").getAsString();
                 String tipoDeCarta = jsonObject.get("tipo").getAsString();
                 int anios = jsonObject.get("anios").getAsInt();
                 String rutaImagen = jsonObject.get("imagen").getAsString();
                 InputStream inputStream = getClass().getResourceAsStream(rutaImagen);
                 if (inputStream == null) {
                     throw new IllegalArgumentException("Recurso no encontrado: " + rutaImagen);
                 }
                 Image fondo = new Image(inputStream);
               
                 String colorFondo = jsonObject.get("color").getAsString();
                 
                 JsonObject opcionAJson = jsonObject.getAsJsonObject("opcionA");//obj con datos de opcion A
                 // Obtiene(descripcion y niveles) de opcion A
                 String descripcionOpcionA = opcionAJson.get("descripcion").getAsString();
                 int tierraOpcionA = opcionAJson.get("tierra").getAsInt();
                 int aguaOpcionA = opcionAJson.get("agua").getAsInt();
                 int fuegoOpcionA = opcionAJson.get("fuego").getAsInt();
                 int aireOpcionA = opcionAJson.get("aire").getAsInt();
                 String idSiguienteOpcionA = opcionAJson.get("cartaSiguiente").getAsString();
                 String idMuerteA = opcionAJson.get("cartaSiguienteMuerte").getAsString();
                 
                 JsonObject opcionBJson = jsonObject.getAsJsonObject("opcionB");//obj con datos de opcion B
                 // Obtiene datos(descripcion y niveles) de opcion B
                 String descripcionOpcionB = opcionBJson.get("descripcion").getAsString();
                 int tierraOpcionB = opcionBJson.get("tierra").getAsInt();
                 int aguaOpcionB = opcionBJson.get("agua").getAsInt();
                 int fuegoOpcionB = opcionBJson.get("fuego").getAsInt();
                 int aireOpcionB = opcionBJson.get("aire").getAsInt();
                 String idSiguienteOpcionB = opcionBJson.get("cartaSiguiente").getAsString();
                 String idMuerteB = opcionBJson.get("cartaSiguienteMuerte").getAsString();
                 
                 // Crea una nueva instancia de Carta y la asina al arreglo en la posición i
                 cartas[i] = new Carta();
                 // Establece la descripción y nombre del personaje de la carta
                 cartas[i].setDescripcion(descripcion);
                 cartas[i].setNombre(nombre);
                 cartas[i].setAnios(anios);
                 cartas[i].setFondoCarta(fondo);
                 cartas[i].setColorFondo(colorFondo);
                 cartas[i].setTipoDeCarta(tipoDeCarta);
                 cartas[i].setId(idCarta);
                 // Crea las opciones A y B
                 Opcion opcionA = new Opcion();
                 opcionA.setInformacion(descripcionOpcionA);
                 opcionA.setNiveles(new int[]{tierraOpcionA, aguaOpcionA, fuegoOpcionA, aireOpcionA});
                 opcionA.setIdSiguiente(idSiguienteOpcionA);
                 opcionA.setIdSiguiente(idSiguienteOpcionA);
                 opcionA.setIdSiguienteMuerte(idMuerteA);
                 Opcion opcionB = new Opcion();
                 opcionB.setInformacion(descripcionOpcionB);
                 opcionB.setNiveles(new int[]{tierraOpcionB, aguaOpcionB, fuegoOpcionB, aireOpcionB});
                 opcionB.setIdSiguiente(idSiguienteOpcionB);
                 opcionB.setIdSiguienteMuerte(idMuerteB);
                 // Asigna las opciones a la carta en la posición i
     	         cartas[i].setOpcionA(opcionA);
     	         cartas[i].setOpcionB(opcionB);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void aumentarAnio(int anios) {
		anioActual += anios;
	}
	
	public int getAnioActual() {
		return anioActual;
	}
	
	public Carta jugarCarta() {
		return getCartaActual();
	}
	
	public Carta getCartaActual() {
		return cartas[indiceCartaActual];
	}
	
	public Carta getCartaX(int i) {
		return cartas[i];
	}
	
	public Carta getCartaPorId(String idBuscado) {
        for (Carta carta : cartas) {
            if (carta.getId().equals(idBuscado)) {
                return carta;
            }
        }
        return getCartaActual();
    }
	
	public void aumentarIndiceCartaActual() {
		indiceCartaActual++;
	}
	
	public void elegirOpcionDeCarta(String opcionElegida, Personaje personaje, String idBuscado) throws NivelExcedidoException, NivelInvalidoException {
	    Carta cartaActual = getCartaPorId(idBuscado);
	    cartaActual.elegirOpcion(personaje, cartaActual.getOpciones()[0].getNiveles(), cartaActual.getOpciones()[1].getNiveles(), opcionElegida);
	   
	}

	public Carta[] getCartas() {
		return cartas;
	}
	
	public FondoStrategy getSiguienteEstrategia() {
	    FondoStrategy estrategia = ciclosElemento.get(muertesTotales % ciclosElemento.size());
	    return estrategia;
	}
	
	public void aumentarMuertes() {
		muertesTotales++;
	}
	
}
