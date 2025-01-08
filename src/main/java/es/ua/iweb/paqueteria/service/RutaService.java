package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.entity.RutaEntity;
import es.ua.iweb.paqueteria.repository.RutaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RutaService {
    private final RutaRepository rutaRepository;

    public RutaEntity addRuta(RutaEntity ruta) { return this.rutaRepository.save(ruta); }

    public List<RutaEntity> getAllRutas() { return rutaRepository.findAll(); }

    public List<RutaEntity> getRutasByReoartidor(Integer repartidorId){ return rutaRepository.findByRepartidorId(repartidorId); }

    public List<RutaEntity> getRutasByFecha(Date fecha){ return rutaRepository.findByFecha(fecha); }
}
