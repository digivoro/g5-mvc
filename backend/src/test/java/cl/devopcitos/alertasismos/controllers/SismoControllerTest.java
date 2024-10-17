package cl.devopcitos.alertasismos.controllers;

import cl.devopcitos.alertasismos.models.Sismo;
import cl.devopcitos.alertasismos.services.SismoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SismoControllerTest {

    @InjectMocks
    private SismoController sismoController;

    @Mock
    private SismoService sismoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSismos() {
        List<Sismo> mockSismos = Arrays.asList(new Sismo("Chile", null, 10.0, 5.5));
        when(sismoService.getAllSismos()).thenReturn(mockSismos);

        List<Sismo> result = sismoController.getAllSismos();

        assertEquals(1, result.size());
        assertEquals("Chile", result.get(0).getLocalidad());
    }

    @Test
    public void testGetSismoById() {
        Sismo mockSismo = new Sismo(1L, "Perú", null, 15.0, 6.5);
        when(sismoService.getSismoById(1L)).thenReturn(mockSismo);

        Sismo result = sismoController.oneSismo(1L);

        assertEquals("Perú", result.getLocalidad());
        assertEquals(6.5, result.getMagnitud());
    }
}
