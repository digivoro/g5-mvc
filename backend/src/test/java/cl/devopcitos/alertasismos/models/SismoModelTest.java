package cl.devopcitos.alertasismos.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SismoModelTest {

    @Test
    public void testCreateSismo() {
        // Crear un nuevo objeto Sismo
        Sismo sismo = new Sismo("México", null, 20.0, 6.0);

        // Verificar que los valores sean correctos
        assertEquals("México", sismo.getLocalidad());
        assertEquals(6.0, sismo.getMagnitud());
    }

    @Test
    public void testSettersAndGetters() {
        Sismo sismo = new Sismo("Chile", null, 15.0, 5.5);
        sismo.setLocalidad("Argentina");
        sismo.setMagnitud(7.0);

        assertEquals("Argentina", sismo.getLocalidad());
        assertEquals(7.0, sismo.getMagnitud());
    }
}
