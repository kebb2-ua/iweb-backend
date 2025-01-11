package es.ua.iweb.paqueteria.service;
import es.ua.iweb.paqueteria.entity.RutaEntity;
import es.ua.iweb.paqueteria.repository.RutaRepository;
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
        // Preparar
        RutaEntity newRuta = RutaEntity.builder()
                .id(1)
                .origen("Madrid")
                .destino("Valencia")
                .fecha(new Date())
                .build();

        when(rutaRepository.save(newRuta)).thenReturn(newRuta);

        // Probar
        RutaEntity result = rutaService.addRuta(newRuta);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Madrid", result.getOrigen());
        assertEquals("Valencia", result.getDestino());
        assertEquals(new Date(), result.getFecha());
        verify(rutaRepository, times(1)).save(newRuta);
    }

    @Test
    void testGetListadoRutas() {
        // Preparar
        List<RutaEntity> rutas = Arrays.asList(
                RutaEntity.builder().id(1).origen("Madrid").destino("Valencia").build(),
                RutaEntity.builder().id(2).origen("Barcelona").destino("Sevilla").build()
        );

        when(rutaRepository.findAll()).thenReturn(rutas);

        // Probar
        List<RutaEntity> result = rutaService.getAllRutas();

        // Comprobar
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rutaRepository, times(1)).findAll();
    }

    @Test
    void testListadoRutasPorRepartidor() {
        // Preparar
        Integer repartidorId = 10;
        List<RutaEntity> rutas = Arrays.asList(
                RutaEntity.builder().id(1).origen("Madrid").destino("Valencia").build(),
                RutaEntity.builder().id(2).origen("Barcelona").destino("Sevilla").build()
        );

        when(rutaRepository.findByRepartidorId(repartidorId)).thenReturn(rutas);

        // Probar
        List<RutaEntity> result = rutaService.getRutasByRepartidor(repartidorId);

        // Comprobar
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rutaRepository, times(1)).findByRepartidorId(repartidorId);
    }

    @Test
    void testListadoRutasPorFecha() {
        // Preparar
        Date fecha = new Date();
        List<RutaEntity> rutas = Arrays.asList(
                RutaEntity.builder().id(1).fecha(fecha).origen("Madrid").destino("Valencia").build(),
                RutaEntity.builder().id(2).fecha(fecha).origen("Barcelona").destino("Sevilla").build()
        );

        when(rutaRepository.findByFecha(fecha)).thenReturn(rutas);

        // Probar
        List<RutaEntity> result = rutaService.getRutasByFecha(fecha);

        // Comprobar
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rutaRepository, times(1)).findByFecha(fecha);
    }

    @Test
    void testListadoRutasVacioPorFecha() {
        // Preparar
        Date fecha = new Date();

        when(rutaRepository.findByFecha(fecha)).thenReturn(List.of());

        // Probar
        List<RutaEntity> result = rutaService.getRutasByFecha(fecha);

        // Comprobar
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(rutaRepository, times(1)).findByFecha(fecha);
    }

    @Test
    void testListadoRutasPorOrigen() {
        // Preparar
        String origen = "Madrid";

        List<RutaEntity> rutas = Arrays.asList(
                RutaEntity.builder().id(1).origen("Madrid").destino("Valencia").build(),
                RutaEntity.builder().id(2).origen("Madrid").destino("Barcelona").build()
        );

        when(rutaRepository.findByOrigen(origen)).thenReturn(rutas);

        // Probar
        List<RutaEntity> result = rutaService.getRutasByCiudadOrigen(origen);

        // Comprobar
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rutaRepository, times(1)).findByOrigen(origen);
    }

    @Test
    void testListadoRutasPorDestino() {
        // Preparar
        String destino = "Valencia";

        List<RutaEntity> rutas = Arrays.asList(
                RutaEntity.builder().id(1).origen("Madrid").destino("Valencia").build(),
                RutaEntity.builder().id(2).origen("Sevilla").destino("Valencia").build()
        );

        when(rutaRepository.findByDestino(destino)).thenReturn(rutas);

        // Probar
        List<RutaEntity> result = rutaService.getRutasByCiudadDestino(destino);

        // Comprobar
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rutaRepository, times(1)).findByDestino(destino);
    }
}

