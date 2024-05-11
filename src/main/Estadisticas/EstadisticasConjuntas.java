package Estadisticas;

import java.util.HashMap;

public class EstadisticasConjuntas extends Estadistica {
    private Estadistica agua;
    private Estadistica aire;
    private Estadistica tierra;
    private Estadistica fuego;
    
    public EstadisticasConjuntas() {
        agua = new Estadistica();
        aire = new Estadistica();
        tierra = new Estadistica();
        fuego = new Estadistica();
    }
    
    public void modificarAgua(int cantidad) throws NivelExcedidoException, NivelInvalidoException {
        agua.ajustarNivel(cantidad);
    }
    
    public void modificarAire(int cantidad) throws NivelExcedidoException, NivelInvalidoException {
        aire.ajustarNivel(cantidad);
    }
    
    public void modificarTierra(int cantidad) throws NivelExcedidoException, NivelInvalidoException {
        tierra.ajustarNivel(cantidad);
    }
    
    public void modificarFuego(int cantidad) throws NivelExcedidoException, NivelInvalidoException {
        fuego.ajustarNivel(cantidad);
    }
    
    public HashMap<String, Integer> getEstadisticasNiveles () {
    	HashMap<String, Integer> estadisticasNiveles = new HashMap<String, Integer>();
    	estadisticasNiveles.put("agua", agua.getNivelActual());
    	estadisticasNiveles.put("aire", aire.getNivelActual());
    	estadisticasNiveles.put("tierra", tierra.getNivelActual());
    	estadisticasNiveles.put("fuego", fuego.getNivelActual());
    	return estadisticasNiveles;
    }
    
}
