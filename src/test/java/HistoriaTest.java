import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import Cartas.Carta;
import Juego.Historia;

@ExtendWith(MockitoExtension.class)
public class HistoriaTest {
	
	@Spy
    private Historia historia = new Historia();

    private final int ANIO_INICIAL = 0;
    private final int ANIO_FINAL = 670;

    @BeforeEach
    void setup() {
        historia = Mockito.spy(new Historia());
    }

	
	 @Test
	 @DisplayName("DADA una instancia de Historia, ENTONCES su constructor inicializa los años en 5 y las cartas")
	    public void testConstructor() {
		 	// Establece los niveles esperados como el anio inicial y final
		 	int expected_inicial = ANIO_INICIAL;
		 	int expected_final = ANIO_FINAL;
		 	
		 	// Establece los valores resultantes 
		 	int result_incial = historia.getAnioActual();
		 	int result_final = historia.getCartas().length*10;
		 	
	        Assertions.assertEquals(expected_inicial, result_incial); // Verifica que el año actual se haya inicializado correctamente
	        Assertions.assertEquals(expected_final, result_final); // Verifica que el arreglo de cartas sea igual a los anios
	    }

    @Test
    @DisplayName("DADO una instancia de Historia, CUANDO se aumentan los años, ENTONCES el año actual se incrementa correctamente")
    public void testAumentarAnio() {
    	
        // Crea una historia y define que los anios aumenten 10
    	final int ANIOS_AUMENTO = 10;
    	
    	// Define el valor esperado como los anios que aumentan
    	int expected = ANIOS_AUMENTO;
        
    	// Aumenta 5 anios
        historia.aumentarAnio(ANIOS_AUMENTO);
        
        // Establece el valor resultante como el que tiene la historia en ese momento
        int result = historia.getAnioActual();
        
        Assertions.assertEquals(expected, result); // Verifica que haya 10 anios de historia
    }
    
    @Test
    @DisplayName("DADO que se tiene una instancia de Historia, CUANDO se llenan las cartas, ENTONCES las cartas se completan correctamente con los datos del JSON")
    public void testLlenarCartas() {
    	// Llama al metodo llenar cartas de historia
    	historia.llenarCartas();
    	
    	// Obtiene la carta 6 de historia
    	Carta cartas[] = historia.getCartas();
    	Carta carta = cartas[5];
    	
    	// Establece los niveles esperados
    	int expected_nivelesOpcionA[] = {-20, 0, 20, 0};
    	int expected_nivelesOpcionB[] = {10, 0, -10, 0};
    	
    	// Establece los resultantes como los obtenidos de las opciones
    	int result_nivelesOpcionA[] = carta.getOpcionA().getNiveles();
    	int result_nivelesOpcionB[] = carta.getOpcionB().getNiveles();
    	
    	// Verifica que los niveles esperados coincidan con los resultantes
    	Assertions.assertArrayEquals(expected_nivelesOpcionA, result_nivelesOpcionA);
    	Assertions.assertArrayEquals(expected_nivelesOpcionB, result_nivelesOpcionB);
    	
    }
}
