package Test;

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
        int ejercitoActual = estadisticas.getEjercito();
        int iglesiaActual = estadisticas.getIglesia();
        int oroActual = estadisticas.getOro();
        int puebloActual = estadisticas.getPueblo();

        // intenta modificar las estadisticas
        try {
            opcion.modificarEstadisticas(personaje);
        } catch (NivelExcedidoException | NivelInvalidoException e) {
            fail("Excepción: " + e.getMessage());
        }
        
        ejercitoActual += niveles[0];
        iglesiaActual += niveles[1];
        oroActual += niveles[2];
        puebloActual += niveles[3];

        // test
        assertEquals(ejercitoActual, estadisticas.getEjercito()); // verifica que los niveles modificados coincidan con los teoricos
        assertEquals(iglesiaActual, estadisticas.getIglesia());
        assertEquals(oroActual, estadisticas.getOro());
        assertEquals(puebloActual, estadisticas.getPueblo());
    }

}
