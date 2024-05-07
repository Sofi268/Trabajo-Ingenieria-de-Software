package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import Cartas.Carta;
import Cartas.Opcion;

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
}
