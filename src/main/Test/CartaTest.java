package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

import Cartas.Carta;
import Cartas.Opcion;
import Estadisticas.Estadistica.NivelExcedidoException;
import Estadisticas.Estadistica.NivelInvalidoException;
import Juego.Personaje;

public class CartaTest {

    @Test
    public void testConstructor() {
        Carta carta = new Carta();
        
        //test
        assertNotNull(carta.getOpcionA()); // Verifica que se cree la opcionA no nula
        assertNotNull(carta.getOpcionB()); // Verifica que se cree la opcionB no nula
        assertNull(carta.getDescripcion()); // Verifica que la descripcion no sea nula
    }

    @Test
    public void testVerOpcion() {
        Carta carta = new Carta();
        Opcion opcionA = new Opcion();
        opcionA.setInformacion("Opción A");
        Opcion opcionB = new Opcion();
        opcionB.setInformacion("Opción B");
        carta.setOpcionA(opcionA);
        carta.setOpcionB(opcionB);

        // test
        assertEquals("Opción A", carta.verOpcion(opcionA)); // Verifica que verOpcion() retorna la opción A
        assertEquals("Opción B", carta.verOpcion(opcionB)); // Verifica que verOpcion() retorna la opción B
    }
    
    public void testElegirOpcion() {
        Carta carta = new Carta();
        Personaje personaje = new Personaje();
        // define un nivel inicial de las estadisticas
        int nivelInicial = personaje.getEstadisticas().getNivelActual();
        
        // coloca valores arbitrarios en las opciones
        int[] valoresOpcionA = {10, 20, 0, 0};
        int[] valoresOpcionB = {0, 10, -10, 0};  

        // intenta elegir una opcion
        try {
            carta.elegirOpcion(personaje, valoresOpcionA, valoresOpcionB, "A"); // Elige la opcion A
        } catch (NivelExcedidoException | NivelInvalidoException e) {
            fail("Excepcion: " + e.getMessage());
        }
        
        // crea un entero para ver el nivel despues de elegir la opcion
        int nivelModificado = personaje.getEstadisticas().getNivelActual();
        assertEquals(nivelInicial + valoresOpcionA[0], nivelModificado); // Verifica que las estadisticas hayan cambiado conforme a la opcion
    }

}
