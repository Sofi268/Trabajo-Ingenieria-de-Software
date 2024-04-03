package Estadisticas;

public class Estadistica {
	private static final int NIVEL_MAXIMO = 100;
	private static final int NIVEL_MINIMO = 0;
	private static final int NIVEL_INICIAL = 50;

    private int nivelActual;

    public Estadistica() {
        nivelActual = NIVEL_INICIAL;
    }
    
    public void setNivelActual(int nivel) {
    	nivelActual = nivel;
    }
    
    public int getNivelActual() {
    	return nivelActual;
    }

    public void ajustarNivel(int cantidad) throws NivelExcedidoException, NivelInvalidoException {
        int nuevoNivel = nivelActual + cantidad;
        if (nuevoNivel > NIVEL_MAXIMO) {
            throw new NivelExcedidoException();
        }
        if (nuevoNivel < NIVEL_MINIMO) {
            throw new NivelInvalidoException();
        }
        nivelActual = nuevoNivel;
    }
    
    public class NivelExcedidoException extends Exception {
        public NivelExcedidoException() {
            super();
        }
    }
    
    public class NivelInvalidoException extends Exception {
        public NivelInvalidoException() {
            super();
        }
    }
}
