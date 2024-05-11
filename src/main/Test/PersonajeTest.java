package Test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import Juego.Personaje;

public class PersonajeTest {
	
	@Test
	public void testCrearPersonaje() {
        Personaje personaje = new Personaje();
        assertEquals(0, personaje.getAnios()); // Verifica que el personaje se haya creado y tenga 0 anios
    }

    @Test
    // Test que verifica que se aumenten correctamente los anios del personaje
    public void testAumentarAnios() {    
        Personaje personaje = new Personaje();
        personaje.setAnios(10); // Coloca inicialmente los anios en 10
        personaje.aumentarAnios(1); // Aumenta uno los anios
        assertEquals(11, personaje.getAnios()); // Verifica que los anios ahora sean 11
    }
    
    @Test
    public void testSetGetAnios() {
        Personaje personaje = new Personaje();
        personaje.setAnios(10);
        assertEquals(10, personaje.getAnios()); // Verifica que los anios se setteen correctamente
    }
}
