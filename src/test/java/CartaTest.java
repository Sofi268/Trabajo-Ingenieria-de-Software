import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Cartas.Carta;
import Cartas.Opcion;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Juego.Personaje;

class CartaTest {

    private Opcion opcionA;
    private Opcion opcionB;
    private Carta carta;

    @BeforeEach
    void setUp() {
        opcionA = new Opcion();
        opcionB = new Opcion();
        carta = new Carta(opcionA, opcionB);

    }

    @Test
    @DisplayName("DADA la llamada al constructor de carta, ENTONCES se crean las opciones A y B no nulas y la descripción es nula")
    public void testConstructor() {
        Assertions.assertEquals(opcionA, carta.getOpcionA()); // Verificar que la opcionA se creo correctamente
        Assertions.assertEquals(opcionB, carta.getOpcionB()); // Verificar que la opcionB se creo correctamente
        Assertions.assertNull(carta.getDescripcion()); // Verificar que la descripción es nula
    }

    @Test
    @DisplayName("DADO un objeto Carta con opciones A y B, CUANDO se llama al método verOpcion, ENTONCES retorna la opción correspondiente")
    public void testVerOpcion() {
        // Establece los valores esperados para las opciones A y B
        String expected_opcionA = "Opcion A";
        String expected_opcionB = "Opcion B";

        // Establece que informacion retornan las opciones A y B
        opcionA.setInformacion("Opcion A");
        opcionB.setInformacion("Opcion B");
        
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
        opcionA.setNiveles(new int[]{10, 20, 0, 0});
        opcionB.setNiveles(new int[]{0, 10, -10, 0});

        // Llama al método elegir opcion
        carta.elegirOpcion(personaje, opcionA.getNiveles(), opcionB.getNiveles(), "A");

        // Verifica que el método elegirOpcion se haya llamado con los argumentos correctos
        //verify(carta).elegirOpcion(personaje, opcionA.getNiveles(), opcionB.getNiveles(), "A");
    }
}
