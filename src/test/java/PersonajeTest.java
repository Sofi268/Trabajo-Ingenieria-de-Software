import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import Juego.Personaje;

@ExtendWith(MockitoExtension.class)
public class PersonajeTest {
	
	@Mock
	Personaje personaje;
	
	@BeforeEach
	void setup() {
		personaje = Mockito.spy(new Personaje());
	}
	
	@Test
	@DisplayName("DADO un nuevo personaje, ENTONCES se crea con 0 años")
	public void testCrearPersonaje() {
		// Establece el valor esperado en 0
		int expected = 0;
		
		// Establece el valor resultante como los anios del personaje
		int result = personaje.getAnios();
		
		// Verifica que el personaje se haya creado y tenga 0 anios
        Assertions.assertEquals(expected, result); 
    }

    @Test
    @DisplayName("DADO un personaje con 10 años, CUANDO se aumenta 1 año, ENTONCES la edad del personaje es 11")
    public void testAumentarAnios() {  
    	// Establece el valor esperado en 11
    	int expected = 11;
    	
    	// Coloca los anios del personaje en 10
        personaje.setAnios(10);
        
        // Verifica que se llame correctamente al metodo setAnios
        verify(personaje).setAnios(10);
        
        // Aumenta un anio
        personaje.aumentarAnios(1);
        
        // Establece el resultado en los anios actual del personaje 
        int result = personaje.getAnios();
        
        // Verifica que los anios ahora sean 11
        Assertions.assertEquals(expected, result);
    }
}
