package es.ua.iweb.paqueteria.service;
import es.ua.iweb.paqueteria.entity.RutaEntity;
import es.ua.iweb.paqueteria.repository.RutaRepository;
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
class RutaServiceTest {

    @Mock
    private RutaRepository rutaRepository;

    @InjectMocks
    private RutaService rutaService;


    @Test
    void testCrearRuta() {
        // Arrange
        RutaEntity newRuta = RutaEntity.builder()
                .id(1)
                .fecha(new Date())
                .build();

        when(rutaRepository.save(newRuta)).thenReturn(newRuta);

        // Act
        RutaEntity result = rutaService.addRuta(newRuta);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(rutaRepository, times(1)).save(newRuta);
    }

    @Test
    void testGetListadoRutas() {
        // Arrange
        List<RutaEntity> rutas = Arrays.asList(
                RutaEntity.builder().id(1).build(),
                RutaEntity.builder().id(2).build()
        );

        when(rutaRepository.findAll()).thenReturn(rutas);

        // Act
        List<RutaEntity> result = rutaService.getAllRutas();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rutaRepository, times(1)).findAll();
    }

    @Test
    void testListadoRutasPorRepartidor() {
        // Arrange
        Integer repartidorId = 10;
        List<RutaEntity> rutas = Arrays.asList(
                RutaEntity.builder().id(1).build(),
                RutaEntity.builder().id(2).build()
        );

        when(rutaRepository.findByRepartidorId(repartidorId)).thenReturn(rutas);

        // Act
        List<RutaEntity> result = rutaService.getRutasByReoartidor(repartidorId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rutaRepository, times(1)).findByRepartidorId(repartidorId);
    }

    @Test
    void testListadoRutasPorFecha() {
        // Arrange
        Date fecha = new Date();
        List<RutaEntity> rutas = Arrays.asList(
                RutaEntity.builder().id(1).build(),
                RutaEntity.builder().id(2).build()
        );

        when(rutaRepository.findByFecha(fecha)).thenReturn(rutas);

        // Act
        List<RutaEntity> result = rutaService.getRutasByFecha(fecha);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rutaRepository, times(1)).findByFecha(fecha);
    }

    @Test
    void testListadoRutasVacioPorFecha() {
        // Arrange
        Date fecha = new Date();

        when(rutaRepository.findByFecha(fecha)).thenReturn(List.of());

        // Act
        List<RutaEntity> result = rutaService.getRutasByFecha(fecha);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(rutaRepository, times(1)).findByFecha(fecha);
    }

    @Test
    void testListadoRutasPorRangoFechas() {
        // Arrange
        Date fechaInicio = new Date(1672531200000L); // 1 Ene 2023
        Date fechaFinal = new Date(1675209600000L); // 1 Feb 2023

        List<RutaEntity> rutas = Arrays.asList(
                RutaEntity.builder().id(1).build(),
                RutaEntity.builder().id(2).build()
        );

        when(rutaRepository.findByFechaRango(fechaInicio, fechaFinal)).thenReturn(rutas);

        // Act
        List<RutaEntity> result = rutaService.getRutasByRangoFechas(fechaInicio, fechaFinal);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rutaRepository, times(1)).findByFechaRango(fechaInicio, fechaFinal);
    }

    @Test
    void testListadoRutasVacioPorRangoFechas() {
        // Arrange
        Date fechaInicio = new Date(1672531200000L); // 1 Ene 2023
        Date fechaFinal = new Date(1675209600000L); // 1 Feb 2023

        when(rutaRepository.findByFechaRango(fechaInicio, fechaFinal)).thenReturn(List.of());

        // Act
        List<RutaEntity> result = rutaService.getRutasByRangoFechas(fechaInicio, fechaFinal);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(rutaRepository, times(1)).findByFechaRango(fechaInicio, fechaFinal);
    }
}

