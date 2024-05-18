
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import Cartas.Opcion;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Estadisticas.EstadisticasConjuntas;
import Juego.Personaje;

public class OpcionTest {
	
	@Test
	public void testConstructor() {
        Opcion opcion = new Opcion();
        
        // test
        assertNull(opcion.getInformacion()); // Verifica que la información sea nula
        assertNull(opcion.getNiveles()); // Verifica que los niveles sean nulos
    }

    @Test
    public void testModificarEstadisticas() {
        Personaje personaje = new Personaje();
        EstadisticasConjuntas estadisticas = personaje.getEstadisticas();
        
        // coloca valores arbitrarios en las estadisticas
        int[] niveles = {10, 20, -10, -20};
        
        // crea una opcion
        Opcion opcion = new Opcion();
        opcion.setNiveles(niveles);

        // obtiene los niveles iniciales de estadisticas
        int aguaInicial = estadisticas.getAgua();
        int aireInicial = estadisticas.getAire();
        int fuegoInicial = estadisticas.getFuego();
        int tierraInicial = estadisticas.getTierra();

        // intenta modificar las estadisticas
        try {
            opcion.modificarEstadisticas(personaje);
        } catch (NivelExcedidoException | NivelInvalidoException e) {
            fail("Excepción: " + e.getMessage());
        }

        // test
        assertEquals(tierraInicial + niveles[0], estadisticas.getTierra()); // verifica que los niveles modificados coincidan con los teoricos
        assertEquals(aguaInicial + niveles[1], estadisticas.getAgua());
        assertEquals(fuegoInicial + niveles[2], estadisticas.getFuego());
        assertEquals(aireInicial + niveles[3], estadisticas.getAire());
    }

}
