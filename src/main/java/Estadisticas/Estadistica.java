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
        nivelActual += cantidad;
        if (nivelActual > NIVEL_MAXIMO) {
            throw new NivelExcedidoException();
        }
        if (nivelActual < NIVEL_MINIMO) {
            throw new NivelInvalidoException();
        }
    }
    
    public static class NivelExcedidoException extends Exception {
        private static final long serialVersionUID = 1L;

        public NivelExcedidoException() {
            super();
        }
    }
    
    public static class NivelInvalidoException extends Exception {
        private static final long serialVersionUID = 1L;

        public NivelInvalidoException() {
            super();
        }
    }
}
