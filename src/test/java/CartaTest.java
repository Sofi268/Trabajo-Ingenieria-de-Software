import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import Cartas.Carta;
import Cartas.Opcion;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Juego.Personaje;

@ExtendWith(MockitoExtension.class)
public class CartaTest {

    @Mock
    private Carta carta;
	private Opcion opcionA;
	private Opcion opcionB;
	
	@BeforeEach
	void setup() {
		carta = Mockito.spy(new Carta()); // Crea una instancia de la clase carta
		opcionA = mock(Opcion.class); // Crea un mock de la opcionA
	    opcionB = mock(Opcion.class); // Crea un mock de la opcionB
	}

    @Test
    @DisplayName("DADA la llamada al constructor de carta, ENTONCES se crean las opciones A y B no nulas y la descripción es nula")
    public void testConstructor() {
        Assertions.assertNotNull(carta.getOpcionA()); // Verifica que se cree la opcionA no nula
        Assertions.assertNotNull(carta.getOpcionB()); // Verifica que se cree la opcionB no nula
        Assertions.assertNull(carta.getDescripcion()); // Verifica que la descripcion no sea nula
    }

    @Test
    @DisplayName("DADO un objeto Carta con opciones A y B, CUANDO se llama al método verOpcion, ENTONCES retorna la opción correspondiente")
    public void testVerOpcion() {
    	// Establece los valores esperados para las opciones A y B
        String expected_opcionA = "Opcion A";
        String expected_opcionB = "Opcion B";

        // Establece que informacion retornan las opciones A y B
        when(opcionA.getInformacion()).thenReturn("Opcion A");
        when(opcionB.getInformacion()).thenReturn("Opcion B");

        // Setea las opciones A y B en las cartas
        carta.setOpcionA(opcionA);
        carta.setOpcionB(opcionB);

        // Establece los valores resultantes, siendo estos las descripciones de las opciones mostradas por las cartas
        String result_opcionA = carta.verOpcion(opcionA);
        String result_opcionB = carta.verOpcion(opcionB);

        // test
        Assertions.assertEquals(expected_opcionA, result_opcionA); // Verifica que verOpcion() retorna la opción A
        Assertions.assertEquals(expected_opcionB, result_opcionB); // Verifica que verOpcion() retorna la opción B
    }

    @Test
    @DisplayName("DADO un objeto Carta y valores de opciones A y B, ENTONCES la llamada al metodo elegirOpcion se realiza con los argumentos corrspondientes")
    public void testElegirOpcion() throws NivelExcedidoException, NivelInvalidoException {
    	Personaje personaje = new Personaje(); // Crea el personaje
        
        // Establece que valores de niveles retornan las opciones A y B
        when(opcionA.getNiveles()).thenReturn(new int[]{10, 20, 0, 0});
        when(opcionB.getNiveles()).thenReturn(new int[]{0, 10, -10, 0});

        // LLama al metodo elegir opcion
        carta.elegirOpcion(personaje, opcionA.getNiveles(), opcionB.getNiveles(), "A");

        // Verifica que el método elegirOpcion se haya llamado con los argumentos correctos
        verify(carta).elegirOpcion(personaje, opcionA.getNiveles(), opcionB.getNiveles(), "A");
    }
}
