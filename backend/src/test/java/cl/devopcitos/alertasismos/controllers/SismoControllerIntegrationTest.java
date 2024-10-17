package cl.devopcitos.alertasismos.controllers;

import cl.devopcitos.alertasismos.models.Sismo;
import cl.devopcitos.alertasismos.services.SismoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class SismoControllerIntegrationTest {

    @Autowired
    private SismoController sismoController;

    @Autowired
    private SismoService sismoService;

    @Test
    public void testCreateAndRetrieveSismo() {
        Sismo newSismo = new Sismo("Argentina", null, 12.0, 5.0);
        sismoService.createSismo(newSismo);

        Sismo result = sismoController.oneSismo(newSismo.getId());

        assertEquals("Argentina", result.getLocalidad());
    }

    @Test
    public void testDeleteSismoIntegration() {
        Sismo sismo = new Sismo("Perú", null, 8.0, 4.5);
        sismoService.createSismo(sismo);

        boolean result = sismoService.deleteSismo(sismo.getId());

        assertFalse(sismoService.getSismoById(sismo.getId()) != null);
        assertEquals(true, result);
    }
}
