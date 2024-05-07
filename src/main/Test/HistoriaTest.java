package Test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Juego.Historia;

public class HistoriaTest {
	
	 @Test
	    public void testConstructor() {
	        final int ANIO_INICIAL = 0;
	        final int ANIO_FINAL = 200;
	        Historia historia = new Historia();
	        
	        // test
	        assertEquals(ANIO_INICIAL, historia.getAnioActual()); // Verifica que el año actual se haya inicializado correctamente
	        assertEquals(ANIO_FINAL, historia.getCartas().length); // Verifica que el arreglo de cartas sea igual a los anios
	    }

    @Test
    public void testAumentarAnio() {
        // crea una historia y define que los anios aumenten 5
        Historia historia = new Historia();
        final int ANIOS_AUMENTO = 5;
        historia.aumentarAnio(ANIOS_AUMENTO);// aumenta 5 anios
        
        // test
        assertEquals(ANIOS_AUMENTO, historia.getAnioActual()); // Verifica que haya 5 anios de hstoria
    }
}
