package cl.devopcitos.alertasismos.services;

import cl.devopcitos.alertasismos.models.Sismo;
import cl.devopcitos.alertasismos.repositories.SismoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SismoServiceTest {

    @InjectMocks
    private SismoService sismoService;

    @Mock
    private SismoRepository sismoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSismoById() {
        Sismo mockSismo = new Sismo(1L, "Perú", null, 15.0, 7.0);
        when(sismoRepository.findById(1L)).thenReturn(Optional.of(mockSismo));

        Sismo result = sismoService.getSismoById(1L);

        assertEquals("Perú", result.getLocalidad());
        assertEquals(7.0, result.getMagnitud());
    }

    @Test
    public void testDeleteSismo() {
        when(sismoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(sismoRepository).deleteById(1L);

        boolean result = sismoService.deleteSismo(1L);

        assertTrue(result);
        verify(sismoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteNonExistentSismo() {
        when(sismoRepository.existsById(1L)).thenReturn(false);

        boolean result = sismoService.deleteSismo(1L);

        assertEquals(false, result);
    }
}
