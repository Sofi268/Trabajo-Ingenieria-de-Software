
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import Estadisticas.Estadistica;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;


public class EstadisticasTest {
	
	@Test
    public void testConstructor() {
        final int NIVEL_INICIAL = 50;
        Estadistica estadistica = new Estadistica();
        assertEquals(NIVEL_INICIAL, estadistica.getNivelActual()); // Verifica que la estadistica se cree con el nivel esperado
    }
	
    @Test
    public void testAjustarNivel() throws NivelExcedidoException, NivelInvalidoException {
        Estadistica estadistica = new Estadistica();
        
        // Prueba Ajustar la estadistica en un rango normal
        estadistica.ajustarNivel(10);
        assertEquals(60, estadistica.getNivelActual());
        
        // Prueba Exceder el nivel permitido
        assertThrows(NivelExcedidoException.class, () -> estadistica.ajustarNivel(150));
        
        // Prueba Colocar una estadistica mas abajo de lo permitido
        assertThrows(NivelInvalidoException.class, () -> estadistica.ajustarNivel(-70));
    }
}
