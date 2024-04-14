package Cartas;

import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Juego.Personaje;

public class Carta extends Opcion{
	
	private Opcion opcionA;
	private Opcion opcionB;
	private String descripcion;
	
	public Carta() {
		opcionA = new Opcion();
		opcionB = new Opcion();
		descripcion = null;
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
}
