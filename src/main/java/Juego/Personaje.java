package Juego;
import java.io.FileReader;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Estadisticas.EstadisticasConjuntas;

public class Personaje {
	private int anios;
	private EstadisticasConjuntas estadisticas;
	private int anioInicial;
	private String nombre;
	private String elemento;
	private int numeroDeAvatar;
	
	
	public Personaje() {
		anios = 0;
		estadisticas = new EstadisticasConjuntas();
		anioInicial = 0;
		numeroDeAvatar = 0;
		configurarPersonaje(numeroDeAvatar);
	}
	
	public Personaje(int anioInicial, int numeroDeAvatar) {
		anios = 0;
		estadisticas = new EstadisticasConjuntas();
		this.anioInicial = anioInicial;
		this.numeroDeAvatar = numeroDeAvatar;
		configurarPersonaje(numeroDeAvatar);
	}
	
	private void configurarPersonaje(int i) {
		Gson gson = new Gson(); //obj para parsear json
		try (FileReader reader = new FileReader("src/main/resources/Data/avatars.json")) {
	    	JsonArray jsonArray = gson.fromJson(reader, JsonArray.class); //array con datos del json
	    	JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
	    	String nombre = jsonObject.get("nombre").getAsString();
	    	String elemento = jsonObject.get("elemento").getAsString();
	    	setNombre(nombre);
	    	setElemento(elemento);
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void setAnios(int anios) {
		this.anios = anios;
	}
	
	public int getAnios() {
		return anios;
	}
	
	public EstadisticasConjuntas getEstadisticas() {
		return estadisticas;
	}
	
	public void aumentarAnios(int aniosPasados) {
		anios += aniosPasados;
	}
	
	public HashMap<String, Integer> getNiveles(){
		return estadisticas.getEstadisticasNiveles();
	}

	public int getAnioInicial() {
		return anioInicial;
	}

	public void setAnioInicial(int anioInicial) {
		this.anioInicial = anioInicial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getElemento() {
		return elemento;
	}

	public void setElemento(String elemento) {
		this.elemento = elemento;
	}
	
}
