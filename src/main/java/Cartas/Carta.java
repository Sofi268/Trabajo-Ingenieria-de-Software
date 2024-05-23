package Cartas;

import javafx.scene.image.Image;

import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Juego.Personaje;

public class Carta {
	
	private Opcion opcionA;
	private Opcion opcionB;
	private String descripcion;
	private String nombre;
	private int anios;
	private Image fondo;
	private String colorFondo;

	public Carta() {
		opcionA = new Opcion();
		opcionB = new Opcion();
		descripcion = null;
		nombre = null;
		fondo = null;
		colorFondo = null;
	}
	
	public Carta(Opcion opcionA, Opcion opcionB) {
        this.opcionA = opcionA;
        this.opcionB = opcionB;
        descripcion = null;
        nombre = null;
        fondo = null;
        colorFondo = null;
    }
	
	public void elegirOpcion(Personaje personaje, int[] opcionA, int[] opcionB, String opcion) throws NivelExcedidoException, NivelInvalidoException {
		if(opcion.equals("A")) {
			this.opcionA.modificarEstadisticas(personaje);
		}
		else if(opcion.equals("B")){
			this.opcionB.modificarEstadisticas(personaje);
		}
		
	}
	
	public String verOpcion(Opcion opcion) {
		if(opcion == this.opcionA) {
			return opcionA.getInformacion();
		}
		else{
			return opcionB.getInformacion();
		}
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public Opcion[] getOpciones() {
		Opcion[] opciones = {opcionA, opcionB};
		return opciones;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Image getFondo() {
		return fondo;
	}
	
	public void setFondoCarta(Image imagenFondo) {
		fondo = imagenFondo;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getAnios() {
		return anios;
	}

	public void setAnios(int anios) {
		this.anios = anios;
	}

	public void setOpcionA(Opcion opcionA) {
        this.opcionA = opcionA;
    }
    
    // Método para obtener la opción A
    public Opcion getOpcionA() {
        return opcionA;
    }
    
    // Método para establecer la opción B
    public void setOpcionB(Opcion opcionB) {
        this.opcionB = opcionB;
    }
    
    // Método para obtener la opción B
    public Opcion getOpcionB() {
        return opcionB;
    }

	public String getColorFondo() {
		return colorFondo;
	}

	public void setColorFondo(String colorFondo) {
		this.colorFondo = colorFondo;
	}
}
