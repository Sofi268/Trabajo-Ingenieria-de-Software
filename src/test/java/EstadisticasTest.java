import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import Estadisticas.Estadistica;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;

@ExtendWith(MockitoExtension.class)
public class EstadisticasTest {

    @Mock
    private Estadistica estadistica;
    private final int NIVEL_INICIAL = 50;

    @BeforeEach
    void setup() {
        estadistica = Mockito.spy(new Estadistica()); // Crea una instancia de la clase estadistica
    }

    @Test
    @DisplayName("DADA la construccion de la estadistica, ENTONCES inicializa con el nivel inicial en 50")
    public void testConstructor() {
    	// Establece el nivel esperado como el nivel inicial
        int expected = NIVEL_INICIAL;

        // Establece el valor resultante como el nivelActual de estadistica
        int result = estadistica.getNivelActual();

        Assertions.assertEquals(expected, result); // Verifica que el constructor de esstadistica asigne el valor esperado
    }

    @Test
    @DisplayName("DADA una estadistica, CUANDO se llama a ajustarNivel con los limites normales, ENTONCES ajusta el nivel correctamente")
    public void testAjustarNivel1() throws NivelExcedidoException, NivelInvalidoException {
    	// Establece el valor esperado en 60
        int expected = 60;

        // Ajusta el nivel de la estadistica en 10
        estadistica.ajustarNivel(10);

        // Verifica que el método se llame con el valor esperado
        verify(estadistica).ajustarNivel(10);

        // Establece el valor resultante en el valor que tiene la estadistica en en el momento actual
        int result = estadistica.getNivelActual();
        Assertions.assertEquals(expected, result); // Verifica que el valor se haya ajustado correctamente
    }

    @Test
    @DisplayName("DADA una estadistica, CUANDO se llama a ajustarNivel excediendo el limite, ENTONCES lanza NivelExcedidoException")
    public void testAjustarNivel2() throws NivelExcedidoException, NivelInvalidoException {
        // Verifica que el método lance la excepción esperada
        Assertions.assertThrows(NivelExcedidoException.class, () -> estadistica.ajustarNivel(150));
    }

    @Test
    @DisplayName("DADA una estadistica, CUANDO se llama ajusta el nivel abajo de cero, ENTONCES lanza NivelInvalidoException")
    public void testAjustarNivel3() throws NivelExcedidoException, NivelInvalidoException {
        // Verifica que el método lance la excepción esperada
        Assertions.assertThrows(NivelInvalidoException.class, () -> estadistica.ajustarNivel(-70));
    }
}
