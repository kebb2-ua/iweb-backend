package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.entity.TarifaEntity;
import es.ua.iweb.paqueteria.repository.TarifaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarifaServiceTest {

    @Mock
    private TarifaRepository tarifaRepository;

    @InjectMocks
    private TarifaService tarifaService;

    private TarifaEntity tarifa1;
    private TarifaEntity tarifa2;

    @BeforeEach
    void setUp() {
        tarifa1 = TarifaEntity.builder()
                .id(1)
                .peso(5.5f)
                .distancia(100.0f)
                .peligroso(false)
                .build();

        tarifa2 = TarifaEntity.builder()
                .id(2)
                .peso(2.0f)
                .distancia(50.0f)
                .peligroso(true)
                .build();
    }

    @Test
    void testCrearTarifa() {
        when(tarifaRepository.save(any(TarifaEntity.class))).thenReturn(tarifa1);

        TarifaEntity resultado = tarifaService.createTarifa(tarifa1);

        assertNotNull(resultado);
        assertEquals(5.5f, resultado.getPeso());
        assertEquals(100.0f, resultado.getDistancia());
        assertFalse(resultado.getPeligroso());
        verify(tarifaRepository, times(1)).save(tarifa1);
    }

    @Test
    void testObtenerTodasLasTarifas() {
        when(tarifaRepository.findAll()).thenReturn(Arrays.asList(tarifa1, tarifa2));

        List<TarifaEntity> resultado = tarifaService.getAllTarifas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(tarifaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorPeligroso() {
        when(tarifaRepository.findByPeligroso(true)).thenReturn(List.of(tarifa2));

        List<TarifaEntity> resultado = tarifaService.getByPeligroso(true);

        assertFalse(resultado.isEmpty());
        assertEquals(2, resultado.get(0).getId());
        verify(tarifaRepository, times(1)).findByPeligroso(true);
    }
}
