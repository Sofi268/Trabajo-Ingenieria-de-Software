import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import Cartas.Opcion;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Estadisticas.EstadisticasConjuntas;
import Juego.Personaje;

@ExtendWith(MockitoExtension.class)
public class OpcionTest {
	
	@Mock
	private Opcion opcion;
	
	@BeforeEach
	void setup() {
		opcion = Mockito.spy(new Opcion());
	}
	
	
	@Test
	@DisplayName("DADO un objeto Opcion, ENTONCES su constructor inicializa los niveles en [0, 0, 0, 0] y la información nula")
	public void testConstructor() {
		// Establece los niveles esperados
		int[] expected = {0, 0, 0, 0};
		
		// Establece los niveles resultantes
		int[] result = opcion.getNiveles();
		
        Assertions.assertNull(opcion.getInformacion()); // Verifica que la información sea nula
        Assertions.assertArrayEquals(expected, result); // Verifica que los niveles esperados coincidan con los resultantes
    }

    @Test
    @DisplayName("DADO un Personaje y una Opcion con niveles específicos, CUANDO se modifican las estadísticas del personaje, ENTONCES estas se actualizan correctamente")
    public void testModificarEstadisticas() throws NivelExcedidoException, NivelInvalidoException {
        Personaje personaje = new Personaje();
        EstadisticasConjuntas estadisticas = personaje.getEstadisticas();
        
        // Establece valores de niveles arbitrarios
        int[] niveles = {10, 20, -10, -20};
  
        // Obtiene los niveles iniciales de estadisticas
        int aguaInicial = estadisticas.getAgua();
        int aireInicial = estadisticas.getAire();
        int fuegoInicial = estadisticas.getFuego();
        int tierraInicial = estadisticas.getTierra();
        
        // Establece los niveles esperados
        int expected_tierra = tierraInicial + niveles[0];
        int expected_agua = aguaInicial + niveles[1];
        int expected_fuego = fuegoInicial + niveles[2];
        int expected_aire = aireInicial + niveles[3];

        // Establece los niveles de la opcion
        opcion.setNiveles(niveles);
        
        // Modifica las estadisticas
        opcion.modificarEstadisticas(personaje);
       
        // Verifica que el llamado al metodo se haga como corresponde
        verify(opcion).modificarEstadisticas(personaje);
        
        // Establece los niveles resultantes como los valores de las estadisticas luego de ser modificados
        int result_tierra = estadisticas.getTierra();
        int result_agua = estadisticas.getAgua();
        int result_fuego = estadisticas.getFuego();
        int result_aire = estadisticas.getAire();
        
        // Verifica que los niveles resultantes coincidan con los esperados
        Assertions.assertEquals(expected_tierra, result_tierra); 
        Assertions.assertEquals(expected_agua, result_agua);
        Assertions.assertEquals(expected_fuego, result_fuego);
        Assertions.assertEquals(expected_aire, result_aire);
    }

}
