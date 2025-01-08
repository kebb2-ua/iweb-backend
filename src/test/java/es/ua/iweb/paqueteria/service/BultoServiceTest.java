package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.entity.BultoEntity;
import es.ua.iweb.paqueteria.repository.BultoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BultoServiceTest {

    @Mock
    private BultoRepository bultoRepository;

    @InjectMocks
    private BultoService bultoService;


    @Test
    void testCrearBulto() {
        BultoEntity newBulto = BultoEntity.builder()
                .id(1)
                .descripcion("Bulto de prueba")
                .build();

        when(bultoRepository.save(newBulto)).thenReturn(newBulto);

        BultoEntity result = bultoService.addBulto(newBulto);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(bultoRepository, times(1)).save(newBulto);
    }

    @Test
    void testListaBultos() {
        // Arrange
        Integer pedidoId = 10;
        List<BultoEntity> bultos = Arrays.asList(
                BultoEntity.builder().id(1).descripcion("Bulto 1").build(),
                BultoEntity.builder().id(2).descripcion("Bulto 2").build()
        );

        when(bultoRepository.findByPedidoId(pedidoId)).thenReturn(bultos);

        // Act
        List<BultoEntity> result = bultoService.getBultosByPedidoId(pedidoId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Bulto 1", result.get(0).getDescripcion());
        verify(bultoRepository, times(1)).findByPedidoId(pedidoId);
    }

    @Test
    void testListaBultosVacia() {
        // Arrange
        Integer pedidoId = 20;

        when(bultoRepository.findByPedidoId(pedidoId)).thenReturn(List.of());

        // Act
        List<BultoEntity> result = bultoService.getBultosByPedidoId(pedidoId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bultoRepository, times(1)).findByPedidoId(pedidoId);
    }
}
