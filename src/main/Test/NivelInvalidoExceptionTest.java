package Test;

import static org.junit.Assert.assertThrows;
import Estadisticas.Estadistica;
import Estadisticas.Estadistica.NivelInvalidoException;


import org.junit.jupiter.api.Test;

class NivelInvalidoExceptionTest {

	@Test
	void testNivelInvalidoException() {
		Estadistica estad = new Estadistica();
		assertThrows(NivelInvalidoException.class, () -> estad.ajustarNivel(-50));
	}
}
