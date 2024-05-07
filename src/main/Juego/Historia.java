package Juego;

import Cartas.Carta;
import Cartas.Opcion;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import com.google.gson.*;
import com.google.gson.JsonObject;
import java.io.FileReader;


public class Historia {
	private static final int ANIO_INICIAL = 0;
	private static final int ANIO_FINAL = 5; //SE MODIFICO PARCIALMENTE

	private int anioActual;
	private Carta[] cartas;
	
	public Historia() {
		anioActual = ANIO_INICIAL;
		cartas = new Carta[ANIO_FINAL];
	}
	
	public void llenarCartas(){
		Gson gson = new Gson(); //obj para parsear json
		
        try (FileReader reader = new FileReader("resources/Data/cartas.json")) {
        	JsonArray jsonArray = gson.fromJson(reader, JsonArray.class); //array con datos del json
        	 for (int i = 0; i < jsonArray.size()-1; i++) {
        		 JsonObject jsonObject = jsonArray.get(i).getAsJsonObject(); //obj con datos de la carta
        		 //obtengo nombre y descripción de la carta
                 String nombre = jsonObject.get("nombre").getAsString();
                 String descripcion = jsonObject.get("descripcion").getAsString();
               
                 JsonObject opcionAJson = jsonObject.getAsJsonObject("opcionA");//obj con datos de opcion A
                 //obtengo datos(descripcion y niveles) de opcion A
                 String descripcionOpcionA = opcionAJson.get("descripcion").getAsString();
                 int ejercitoOpcionA = opcionAJson.get("ejercito").getAsInt();
                 int iglesiaOpcionA = opcionAJson.get("iglesia").getAsInt();
                 int oroOpcionA = opcionAJson.get("oro").getAsInt();
                 int puebloOpcionA = opcionAJson.get("pueblo").getAsInt();

                 JsonObject opcionBJson = jsonObject.getAsJsonObject("opcionB");//obj con datos de opcion B
                 //obtengo datos(descripcion y niveles) de opcion B
                 String descripcionOpcionB = opcionBJson.get("descripcion").getAsString();
                 int ejercitoOpcionB = opcionBJson.get("ejercito").getAsInt();
                 int iglesiaOpcionB = opcionBJson.get("iglesia").getAsInt();
                 int oroOpcionB = opcionBJson.get("oro").getAsInt();
                 int puebloOpcionB = opcionBJson.get("pueblo").getAsInt();
                 
                 // Crear una nueva instancia de Carta y asignarla al arreglo en la posición i
                 cartas[i] = new Carta();
                 // Establecer la descripción y nombre del personaje de la carta
                 cartas[i].setDescripcion(descripcion);
                 cartas[i].setNombre(nombre);
                 // Crear las opciones A y B
                 Opcion opcionA = new Opcion();
                 opcionA.setInformacion(descripcionOpcionA);
                 opcionA.setNiveles(new int[]{ejercitoOpcionA, iglesiaOpcionA, oroOpcionA, puebloOpcionA});
                 Opcion opcionB = new Opcion();
                 opcionB.setInformacion(descripcionOpcionB);
                 opcionB.setNiveles(new int[]{ejercitoOpcionB, iglesiaOpcionB, oroOpcionB, puebloOpcionB});
                 // Asignar las opciones a la carta en la posición i
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
	
	public Carta jugarCarta(int muertes) {
		return getCartaActual(muertes);
	}
	
	public Carta getCartaActual(int muertes) {
		return cartas[anioActual - (muertes*5)];
	}
	
	public Carta getCartaX(int i) {
		return cartas[i];
	}
	
	public void elegirOpcionDeCarta(int muertes, String opcionElegida, Personaje personaje) throws NivelExcedidoException, NivelInvalidoException {
	    Carta cartaActual = getCartaActual(muertes);
	    cartaActual.elegirOpcion(personaje, cartaActual.getOpciones()[0].getNiveles(), cartaActual.getOpciones()[1].getNiveles(), opcionElegida);
	   
	}
	
}
