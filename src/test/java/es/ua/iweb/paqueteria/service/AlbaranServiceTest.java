package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.entity.AlbaranEntity;
import es.ua.iweb.paqueteria.entity.UserEntity;
import es.ua.iweb.paqueteria.repository.AlbaranRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbaranServiceTest {

    @Mock
    private AlbaranRepository albaranRepository;

    @InjectMocks
    private AlbaranService albaranService;

    private AlbaranEntity albaran1;
    private AlbaranEntity albaran2;

    @BeforeEach
    void setUp() {
        UserEntity emisor = new UserEntity();
        emisor.setId(1);
        UserEntity receptor = new UserEntity();
        receptor.setId(2);

        albaran1 = AlbaranEntity.builder()
                .id(1)
                .numAlbaran(1001)
                .fechaEmision(new Date())
                .fechaEntrega(new Date())
                .emisor(emisor)
                .receptor(receptor)
                .build();

        albaran2 = AlbaranEntity.builder()
                .id(2)
                .numAlbaran(1002)
                .fechaEmision(new Date())
                .fechaEntrega(new Date())
                .emisor(emisor)
                .receptor(receptor)
                .build();
    }

    @Test
    void testCrearAlbaran() {
        when(albaranRepository.save(any(AlbaranEntity.class))).thenReturn(albaran1);

        AlbaranEntity resultado = albaranService.createAlbaran(albaran1);

        assertNotNull(resultado);
        assertEquals(1001, resultado.getNumAlbaran());
        verify(albaranRepository, times(1)).save(albaran1);
    }

    @Test
    void testObtenerTodosLosAlbaranes() {
        when(albaranRepository.findAll()).thenReturn(Arrays.asList(albaran1, albaran2));

        List<AlbaranEntity> resultado = albaranService.getAllAlbaranes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(albaranRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorNumAlbaran() {
        when(albaranRepository.findByNumAlbaran(1001)).thenReturn(List.of(albaran1));

        List<AlbaranEntity> resultado = albaranService.getByNumAlbaran(1001);

        assertFalse(resultado.isEmpty());
        assertEquals(1001, resultado.get(0).getNumAlbaran());
        verify(albaranRepository, times(1)).findByNumAlbaran(1001);
    }

    @Test
    void testObtenerAlbaranesOrdenados() {
        when(albaranRepository.findAllByOrderByFechaEmisionAsc()).thenReturn(Arrays.asList(albaran1, albaran2));

        List<AlbaranEntity> resultado = albaranService.getSortedByFechaEmision();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(albaranRepository, times(1)).findAllByOrderByFechaEmisionAsc();
    }
}
