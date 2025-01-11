package es.ua.iweb.paqueteria.service;
import es.ua.iweb.paqueteria.dto.PedidoRequest;
import es.ua.iweb.paqueteria.entity.PedidoEntity;
import es.ua.iweb.paqueteria.repository.PedidoRepository;
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
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void testDevolverListadoPedidos() {
        // Preparar
        List<PedidoEntity> pedidos = Arrays.asList(
                PedidoEntity.builder().id(1).observaciones("Pedido 1").build(),
                PedidoEntity.builder().id(2).observaciones("Pedido 2").build()
        );

        when(pedidoRepository.findAll()).thenReturn(pedidos);

        // Ejecutar
        List<PedidoRequest> result = pedidoService.getAllPedidos();

        // Comprobar
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pedido 1", result.get(0).getObservaciones());
        assertEquals("Pedido 2", result.get(1).getObservaciones());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    void testGetPedidosPorRepartidor() {
        // Preparar
        Integer repartidorId = 10;
        List<PedidoEntity> pedidos = Arrays.asList(
                PedidoEntity.builder().id(1).observaciones("Pedido 1").build(),
                PedidoEntity.builder().id(2).observaciones("Pedido 2").build()
        );

        when(pedidoRepository.findByRepartidorId(repartidorId)).thenReturn(pedidos);

        // Ejecutar
        List<PedidoEntity> result = pedidoService.getPedidosByRepartidor(repartidorId);

        // Comprobar
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pedido 1", result.get(0).getObservaciones());
        verify(pedidoRepository, times(1)).findByRepartidorId(repartidorId);
    }


    @Test
    void testListadoPedidosPorRuta() {
        // Preparar
        Integer rutaId = 5;
        List<PedidoEntity> pedidos = Arrays.asList(
                PedidoEntity.builder().id(1).observaciones("Pedido 1").build(),
                PedidoEntity.builder().id(2).observaciones("Pedido 2").build()
        );

        when(pedidoRepository.findByRutaId(rutaId)).thenReturn(pedidos);

        // Ejecutar
        List<PedidoEntity> result = pedidoService.getPedidosByRutaId(rutaId);

        // Comprobar
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pedido 1", result.get(0).getObservaciones());
        verify(pedidoRepository, times(1)).findByRutaId(rutaId);
    }

    @Test
    void testListadoVacioPedidosPorRuta() {
        // Preparar
        Integer rutaId = 5;

        when(pedidoRepository.findByRutaId(rutaId)).thenReturn(List.of());

        // Ejecutar
        List<PedidoEntity> result = pedidoService.getPedidosByRutaId(rutaId);

        // Comprobar
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(pedidoRepository, times(1)).findByRutaId(rutaId);
    }
}
