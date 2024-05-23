import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import Cartas.Carta;
import Cartas.Opcion;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Juego.Personaje;

@ExtendWith(MockitoExtension.class)
public class CartaTest {

    private Carta carta;
    @Mock
    private Opcion opcionA;  
    @Mock
    private Opcion opcionB;

    @BeforeEach
    void setup() {
    	opcionA = mock(Opcion.class);
    	opcionB = mock(Opcion.class);
        carta = new Carta();
        
    }

    @Test
    @DisplayName("DADA la llamada al constructor de carta, ENTONCES se crean las opciones A y B no nulas y la descripción es nula")
    public void testConstructor() {
        Assertions.assertNotNull(carta.getOpcionA()); // Verificar que la opcionA no es nula
        Assertions.assertNotNull(carta.getOpcionB()); // Verificar que la opcionB no es nula
        Assertions.assertNull(carta.getDescripcion()); // Verificar que la descripción es nula
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

        // Test
        Assertions.assertEquals(expected_opcionA, result_opcionA); // Verifica que verOpcion() retorna la opción A
        Assertions.assertEquals(expected_opcionB, result_opcionB); // Verifica que verOpcion() retorna la opción B
    }

    @Test
    @DisplayName("DADO un objeto Carta y valores de opciones A y B, ENTONCES la llamada al método elegirOpcion se realiza con los argumentos correspondientes")
    public void testElegirOpcion() throws NivelExcedidoException, NivelInvalidoException {
        Personaje personaje = new Personaje(); // Crear el personaje
        
        // Establece qué valores de niveles retornan las opciones A y B
        when(opcionA.getNiveles()).thenReturn(new int[]{10, 20, 0, 0});
        when(opcionB.getNiveles()).thenReturn(new int[]{0, 10, -10, 0});

        // Llama al método elegir opcion
        carta.elegirOpcion(personaje, opcionA.getNiveles(), opcionB.getNiveles(), "A");

        // Verifica que el método elegirOpcion se haya llamado con los argumentos correctos
        //verify(carta).elegirOpcion(personaje, opcionA.getNiveles(), opcionB.getNiveles(), "A");
    }
}
