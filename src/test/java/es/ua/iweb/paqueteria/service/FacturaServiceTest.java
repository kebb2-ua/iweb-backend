package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.entity.FacturaEntity;
import es.ua.iweb.paqueteria.entity.UserEntity;
import es.ua.iweb.paqueteria.repository.FacturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private FacturaService facturaService;

    private FacturaEntity factura1;
    private FacturaEntity factura2;

    @BeforeEach
    void setUp() {
        UserEntity emisor = new UserEntity();
        emisor.setId(1);
        UserEntity receptor = new UserEntity();
        receptor.setId(2);

        factura1 = FacturaEntity.builder()
                .id(1)
                .numFactura(2001)
                .fechaEmision(new Date())
                .emisor(emisor)
                .receptor(receptor)
                .build();

        factura2 = FacturaEntity.builder()
                .id(2)
                .numFactura(2002)
                .fechaEmision(new Date())
                .emisor(emisor)
                .receptor(receptor)
                .build();
    }

    @Test
    void testCrearFactura() {
        when(facturaRepository.save(any(FacturaEntity.class))).thenReturn(factura1);

        FacturaEntity resultado = facturaService.createFactura(factura1);

        assertNotNull(resultado);
        assertEquals(2001, resultado.getNumFactura());
        verify(facturaRepository, times(1)).save(factura1);
    }

    @Test
    void testObtenerTodasLasFacturas() {
        when(facturaRepository.findAll()).thenReturn(Arrays.asList(factura1, factura2));

        List<FacturaEntity> resultado = facturaService.getAllFacturas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(facturaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorNumFactura() {
        when(facturaRepository.findByNumFactura(2001)).thenReturn(List.of(factura1));

        List<FacturaEntity> resultado = facturaService.getByNumFactura(2001);

        assertFalse(resultado.isEmpty());
        assertEquals(2001, resultado.get(0).getNumFactura());
        verify(facturaRepository, times(1)).findByNumFactura(2001);
    }
}
