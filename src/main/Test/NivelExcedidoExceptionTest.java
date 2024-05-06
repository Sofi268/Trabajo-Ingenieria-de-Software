package Test;

import static org.junit.Assert.assertThrows;
import Estadisticas.Estadistica;
import Estadisticas.Estadistica.NivelExcedidoException;

import org.junit.jupiter.api.Test;

class NivelExcedidoExceptionTest {

	@Test
	void testNivelExcedidoException() {
		Estadistica estad = new Estadistica();
		assertThrows(NivelExcedidoException.class, () -> estad.ajustarNivel(150));
	}
	
}
