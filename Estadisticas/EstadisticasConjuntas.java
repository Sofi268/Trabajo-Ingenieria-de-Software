package Estadisticas;

public class EstadisticasConjuntas extends Estadistica {
    private Estadistica oro;
    private Estadistica pueblo;
    private Estadistica iglesia;
    private Estadistica ejercito;
    
    public EstadisticasConjuntas() {
        oro = new Estadistica();
        pueblo = new Estadistica();
        iglesia = new Estadistica();
        ejercito = new Estadistica();
    }
    
    public void modificarOro(int cantidad) throws NivelExcedidoException, NivelInvalidoException {
        oro.ajustarNivel(cantidad);
    }
    
    public void modificarPueblo(int cantidad) throws NivelExcedidoException, NivelInvalidoException {
        pueblo.ajustarNivel(cantidad);
    }
    
    public void modificarIglesia(int cantidad) throws NivelExcedidoException, NivelInvalidoException {
        iglesia.ajustarNivel(cantidad);
    }
    
    public void modificarEjercito(int cantidad) throws NivelExcedidoException, NivelInvalidoException {
        ejercito.ajustarNivel(cantidad);
    }
}
