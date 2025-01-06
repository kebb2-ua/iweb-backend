package es.ua.iweb.paqueteria.service;
import es.ua.iweb.paqueteria.entity.PedidoEntity;
import es.ua.iweb.paqueteria.repository.PedidoRepository;
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
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void testAddPedido() {
        // Arrange
        PedidoEntity newPedido = PedidoEntity.builder()
                .id(1)
                .precio(100.5f)
                .observaciones("Pedido urgente")
                .build();

        when(pedidoRepository.save(newPedido)).thenReturn(newPedido);

        // Act
        PedidoEntity result = pedidoService.addPedido(newPedido);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(100.5f, result.getPrecio());
        verify(pedidoRepository, times(1)).save(newPedido);
    }

    @Test
    void testDevolverListadoPedidos() {
        // Arrange
        List<PedidoEntity> pedidos = Arrays.asList(
                PedidoEntity.builder().id(1).observaciones("Pedido 1").build(),
                PedidoEntity.builder().id(2).observaciones("Pedido 2").build()
        );

        when(pedidoRepository.findAll()).thenReturn(pedidos);

        // Act
        List<PedidoEntity> result = pedidoService.getAllPedidos();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pedido 1", result.get(0).getObservaciones());
        assertEquals("Pedido 2", result.get(1).getObservaciones());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    void testGetPedidosPorRepartidor() {
        // Arrange
        Integer repartidorId = 10;
        List<PedidoEntity> pedidos = Arrays.asList(
                PedidoEntity.builder().id(1).observaciones("Pedido 1").build(),
                PedidoEntity.builder().id(2).observaciones("Pedido 2").build()
        );

        when(pedidoRepository.findByRepartidorId(repartidorId)).thenReturn(pedidos);

        // Act
        List<PedidoEntity> result = pedidoService.getPedidosByRepartidor(repartidorId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pedido 1", result.get(0).getObservaciones());
        verify(pedidoRepository, times(1)).findByRepartidorId(repartidorId);
    }

    @Test
    void testListadoPedidosRangoDeFechas() {
        // Arrange
        Date fechaInicio = new Date(1672531200000L); // 1 Ene 2023
        Date fechaFin = new Date(1675209600000L);   // 1 Feb 2023

        List<PedidoEntity> pedidos = Arrays.asList(
                PedidoEntity.builder().id(1).observaciones("Pedido 1").build(),
                PedidoEntity.builder().id(2).observaciones("Pedido 2").build()
        );

        when(pedidoRepository.findByFechaRango(fechaInicio, fechaFin)).thenReturn(pedidos);

        // Act
        List<PedidoEntity> result = pedidoService.getPedidosByFechaRango(fechaInicio, fechaFin);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pedido 1", result.get(0).getObservaciones());
        verify(pedidoRepository, times(1)).findByFechaRango(fechaInicio, fechaFin);
    }

    @Test
    void testListadoVacioPedidosRangoDeFechas() {
        // Arrange
        Date fechaInicio = new Date(1672531200000L); // 1 Ene 2023
        Date fechaFin = new Date(1675209600000L);   // 1 Feb 2023

        when(pedidoRepository.findByFechaRango(fechaInicio, fechaFin)).thenReturn(List.of());

        // Act
        List<PedidoEntity> result = pedidoService.getPedidosByFechaRango(fechaInicio, fechaFin);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(pedidoRepository, times(1)).findByFechaRango(fechaInicio, fechaFin);
    }

    @Test
    void testListadoPedidosPorRuta() {
        // Arrange
        Integer rutaId = 5;
        List<PedidoEntity> pedidos = Arrays.asList(
                PedidoEntity.builder().id(1).observaciones("Pedido 1").build(),
                PedidoEntity.builder().id(2).observaciones("Pedido 2").build()
        );

        when(pedidoRepository.findByRutaId(rutaId)).thenReturn(pedidos);

        // Act
        List<PedidoEntity> result = pedidoService.getPedidosByRutaId(rutaId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pedido 1", result.get(0).getObservaciones());
        verify(pedidoRepository, times(1)).findByRutaId(rutaId);
    }

    @Test
    void testListadoVacioPedidosPorRuta() {
        // Arrange
        Integer rutaId = 5;

        when(pedidoRepository.findByRutaId(rutaId)).thenReturn(List.of());

        // Act
        List<PedidoEntity> result = pedidoService.getPedidosByRutaId(rutaId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(pedidoRepository, times(1)).findByRutaId(rutaId);
    }
}
