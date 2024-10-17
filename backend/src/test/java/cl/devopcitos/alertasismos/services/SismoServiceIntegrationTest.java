package cl.devopcitos.alertasismos.services;

import cl.devopcitos.alertasismos.models.Sismo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SismoServiceIntegrationTest {

    @Autowired
    private SismoService sismoService;

    @Test
    public void testAddAndRetrieveSismo() {
        Sismo newSismo = new Sismo("Chile", null, 20.0, 6.0);
        sismoService.createSismo(newSismo);

        Sismo result = sismoService.getSismoById(newSismo.getId());

        assertEquals("Chile", result.getLocalidad());
        assertEquals(6.0, result.getMagnitud());
    }

    @Test
    public void testDeleteSismo() {
        Sismo newSismo = new Sismo("Brasil", null, 25.0, 7.0);
        sismoService.createSismo(newSismo);

        boolean deleted = sismoService.deleteSismo(newSismo.getId());
        Sismo result = sismoService.getSismoById(newSismo.getId());

        assertTrue(deleted);
        assertNull(result);
    }

    @Test
    public void testUpdateSismo() {
        Sismo newSismo = new Sismo("Mexico", null, 18.0, 5.5);
        sismoService.createSismo(newSismo);

        newSismo.setMagnitud(6.0);
        sismoService.updateSismo(newSismo.getId(), newSismo);

        Sismo updatedSismo = sismoService.getSismoById(newSismo.getId());

        assertEquals(6.0, updatedSismo.getMagnitud());
    }
}
